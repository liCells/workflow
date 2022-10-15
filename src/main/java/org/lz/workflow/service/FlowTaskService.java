package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.basic.cache.NodeMap;
import org.lz.workflow.domain.history.HistoryTask;
import org.lz.workflow.domain.map.Line;
import org.lz.workflow.domain.map.UserTaskNode;
import org.lz.workflow.domain.running.RunningTask;
import org.lz.workflow.exception.FlowFinishedException;
import org.lz.workflow.exception.TaskFinishedException;
import org.lz.workflow.mapper.FlowTaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 */
@Service
public class FlowTaskService extends ServiceImpl<FlowTaskMapper, RunningTask> {
    private final FlowCommonService flowCommonService;

    private final LineService lineService;

    private final FlowService flowService;

    public FlowTaskService(FlowCommonService flowCommonService, LineService lineService, FlowService flowService) {
        this.flowCommonService = flowCommonService;
        this.lineService = lineService;
        this.flowService = flowService;
    }

    @Transactional
    public void complete(String taskId) {
        complete(taskId, null);
    }

    @Transactional
    public void complete(String taskId, Map<String, Object> variables) {
        RunningTask runningTask = baseMapper.selectById(taskId);

        Objects.requireNonNull(runningTask, "Task not exist.");

        HashMap<String, Node> nodeHashMap = NodeMap.get(runningTask.getFlowSymbol(), runningTask.getVersion());

        runningTask.setVariables(variables);
        complete(nodeHashMap, runningTask, false);
    }

    /**
     * Set variables.
     * If the flow is finished, an exception is thrown.
     *
     * @param taskId          task id
     * @param flowId          flow id
     * @param taskVariables   this parameter can be obtained only on the corresponding task
     * @param globalVariables this parameter can be obtained on all tasks
     * @throws FlowFinishedException if ignoreState is false also the flow is finished
     * @throws TaskFinishedException if ignoreState is false also the task is ended
     */
    @Transactional
    public void setVariables(Long taskId, Long flowId, boolean ignoreState, Map<String, Object> taskVariables, Map<String, Object> globalVariables) {
        if (!ignoreState) {
            try {
                flowService.getFlow(flowId, true);
            } catch (NullPointerException e) {
                throw new FlowFinishedException("Flow is finished or not exist.");
            }
        }
        if (taskId != null) {
            if (!ignoreState) {
                RunningTask task = getById(taskId);
                if (task == null) {
                    throw new TaskFinishedException("Task is finished or not exist.");
                }
            }
            saveTaskVariables(taskId, flowId, taskVariables);
        }
        saveTaskVariables(null, flowId, globalVariables);
    }

    /**
     * Complete task.
     *
     * @param nodeHashMap node map
     * @param runningTask running task
     * @param isStart     is start
     * @throws NullPointerException if the node is not exist
     */
    protected void complete(HashMap<String, Node> nodeHashMap, RunningTask runningTask, boolean isStart) {
        // Get id.
        Long id = flowCommonService.getIdAndIncr(FlowCommonEnum.TASK_KEY);

        // Get complete task id.
        Long taskId = isStart ? id : runningTask.getId();

        // Set primary key.
        runningTask.setId(id);
        runningTask.setStartTime(LocalDateTime.now());

        boolean isEnd = false;
        // Get next task node, and save to task.
        for (Node nextLine : NodeMap.getNextNodes(nodeHashMap, runningTask.getNodeSymbol())) {
            Line line = (Line) nextLine;
            if (lineService.checkLine(line)) {
                Node nextNode = NodeMap.getNextNodes(nodeHashMap, line.getSymbol()).get(0);
                if (nextNode instanceof UserTaskNode) {
                    UserTaskNode userTaskNode = (UserTaskNode) nextNode;
                    runningTask.setName(userTaskNode.getName());
                    runningTask.setNodeSymbol(userTaskNode.getSymbol());
                    runningTask.setType(userTaskNode.getType().getName());
                    // TODO determine whether parsing is required
                    runningTask.setExecutor(userTaskNode.getExecutor());
                    // Save new task.
                    saveTask(runningTask);
                    break;
                }
                if (nextNode.getType() == NodeType.END) {
                    // Mark flow the end.
                    isEnd = true;
                    break;
                }
                // TODO other node type
            }
        }

        if (!isStart) {
            // If is running, so delete running task and variables, and end history task.
            baseMapper.deleteById(taskId);
            baseMapper.endHistoryTask(taskId);
            baseMapper.deleteTaskVariables(taskId, true);
        }

        if (isEnd) {
            int runningTaskCount = baseMapper.selectRunningTaskCountByFlowId(runningTask.getFlowId());
            if (runningTaskCount < 1) {
                // If all task is end, so end flow.
                baseMapper.deleteFlow(runningTask.getFlowId(), true);
                baseMapper.endFlow(runningTask.getFlowId());
            }
        }
    }

    protected void deleteRunning(Long flowId) {
        if (flowId == null) {
            throw new RuntimeException("flowId is empty.");
        }
        baseMapper.deleteByFlowId(flowId, true);
        baseMapper.deleteTaskByFlowId(flowId, true);
        baseMapper.deleteTaskVariablesByFlowId(flowId, true);
    }

    protected void deleteHistory(Long flowId) {
        if (flowId == null) {
            throw new RuntimeException("flowId is empty.");
        }
        baseMapper.deleteByFlowId(flowId, false);
        baseMapper.deleteTaskByFlowId(flowId, false);
        baseMapper.deleteTaskVariablesByFlowId(flowId, false);
    }

    protected void destroyHistory(Long flowId) {
        if (flowId == null) {
            throw new RuntimeException("flowId is empty.");
        }
        baseMapper.destroyFlow(flowId);
        baseMapper.endTaskByFlowId(flowId);
    }

    private void saveTask(RunningTask runningTask) {
        HistoryTask historyTask = new HistoryTask(runningTask);
        saveHistoryTask(historyTask);
        saveRunningTask(runningTask);

        saveTaskVariables(runningTask.getId(), runningTask.getFlowId(), runningTask.getVariables());
        saveTaskVariables(null, runningTask.getFlowId(), runningTask.getGlobalVariables());
    }

    private void saveTaskVariables(Long taskId, Long flowId, Map<String, Object> variables) {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        if (flowId == null) {
            throw new IllegalArgumentException("flowId is empty.");
        }
        saveRunningTaskVariables(taskId, flowId, variables);
        saveHistoryTaskVariables(taskId, flowId, variables);
    }

    private void saveRunningTask(RunningTask task) {
        baseMapper.saveRunningTask(task);
    }

    private void saveHistoryTask(HistoryTask task) {
        baseMapper.saveHistoryTask(task);
    }

    private void saveRunningTaskVariables(Long taskId, Long flowId, Map<String, Object> variables) {
        Map<String, Object> vars = new HashMap<>(variables);
        // Get whether it exists
        List<String> keys = baseMapper.selectTaskVariablesByTaskIdAndNames(taskId, flowId, vars.keySet(), true);
        if (keys.isEmpty()) {
            baseMapper.saveTaskVariables(taskId, flowId, vars, true);
            return;
        } else {
            Map<String, Object> updateVars = new HashMap<>(vars);
            for (String key : keys) {
                if (!updateVars.containsKey(key)) {
                    updateVars.remove(key);
                }
            }
            baseMapper.updateRunningTaskVariables(taskId, flowId, updateVars);
            keys.forEach(vars::remove);
        }
        if (vars.isEmpty()) {
            return;
        }
        baseMapper.saveTaskVariables(taskId, flowId, vars, true);
    }

    private void saveHistoryTaskVariables(Long taskId, Long flowId, Map<String, Object> variables) {
        Map<String, Object> vars = new HashMap<>(variables);
        // Get whether it exists
        List<String> keys = baseMapper.selectTaskVariablesByTaskIdAndNames(taskId, flowId, vars.keySet(), false);
        if (keys.isEmpty()) {
            baseMapper.saveTaskVariables(taskId, flowId, vars, false);
            return;
        } else {
            Map<String, Object> updateVars = new HashMap<>(vars);
            for (String key : keys) {
                if (!updateVars.containsKey(key)) {
                    updateVars.remove(key);
                }
            }
            baseMapper.updateHistoryTaskVariables(taskId, flowId, updateVars);
            keys.forEach(vars::remove);
        }
        if (vars.isEmpty()) {
            return;
        }
        baseMapper.saveTaskVariables(taskId, flowId, vars, false);
    }
}
