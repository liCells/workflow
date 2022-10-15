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
import org.lz.workflow.event.StartFlowEvent;
import org.lz.workflow.mapper.FlowTaskMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 */
@Service
public class FlowTaskService extends ServiceImpl<FlowTaskMapper, RunningTask> {
    private final FlowCommonService flowCommonService;

    private final LineService lineService;

    public FlowTaskService(FlowCommonService flowCommonService, LineService lineService) {
        this.flowCommonService = flowCommonService;
        this.lineService = lineService;
    }

    @Transactional
    @EventListener(StartFlowEvent.class)
    public void startFlow(StartFlowEvent startFlowEvent) {
        HashMap<String, Node> nodeHashMap = NodeMap.get(startFlowEvent.getFlowSymbol(), startFlowEvent.getFlowVersion());

        Objects.requireNonNull(nodeHashMap, "Flow not exist.");

        // Init running task.
        final RunningTask task = new RunningTask(startFlowEvent);

        complete(nodeHashMap, task, true);
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

    private void complete(HashMap<String, Node> nodeHashMap, RunningTask runningTask, boolean isStart) {
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
            baseMapper.deleteRunningVariables(taskId);
        }

        if (isEnd) {
            int runningTaskCount = baseMapper.selectRunningTaskCountByFlowId(runningTask.getFlowId());
            if (runningTaskCount < 1) {
                // If all task is end, so end flow.
                baseMapper.deleteRunningFlow(runningTask.getFlowId());
                baseMapper.endFlow(runningTask.getFlowId());
            }
        }
    }

    private void saveTask(RunningTask runningTask) {
        HistoryTask historyTask = new HistoryTask(runningTask);
        saveHistoryTask(historyTask);
        saveRunningTask(runningTask);

        saveTaskVariables(runningTask);
    }

    private void saveTaskVariables(RunningTask runningTask) {
        saveRunningTaskVariables(runningTask.getId(), runningTask.getFlowId(), runningTask.getVariables());
        saveHistoryTaskVariables(runningTask.getId(), runningTask.getFlowId(), runningTask.getVariables());
    }

    private void saveRunningTask(RunningTask task) {
        baseMapper.saveRunningTask(task);
    }

    private void saveHistoryTask(HistoryTask task) {
        baseMapper.saveHistoryTask(task);
    }

    private void saveRunningTaskVariables(Long taskId, Long flowId, Map<String, Object> variables) {
        baseMapper.saveRunningTaskVariables(taskId, flowId, variables);
    }

    private void saveHistoryTaskVariables(Long taskId, Long flowId, Map<String, Object> variables) {
        baseMapper.saveHistoryTaskVariables(taskId, flowId, variables);
    }
}
