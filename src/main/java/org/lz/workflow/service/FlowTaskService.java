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

import java.util.HashMap;
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
        final RunningTask task = new RunningTask(
                startFlowEvent.getFlowId(),
                startFlowEvent.getFlowSymbol(),
                startFlowEvent.getFlowVersion(),
                NodeType.START.getName()
        );

        complete(nodeHashMap, task, true);
    }

    @Transactional
    public void complete(String taskId) {
        RunningTask runningTask = baseMapper.selectById(taskId);

        Objects.requireNonNull(runningTask, "Task not exist.");

        HashMap<String, Node> nodeHashMap = NodeMap.get(runningTask.getFlowSymbol(), runningTask.getVersion());

        complete(nodeHashMap, runningTask, false);
    }

    private void complete(HashMap<String, Node> nodeHashMap, RunningTask runningTask, boolean isStart) {
        // Get id.
        Long id = flowCommonService.getIdAndIncr(FlowCommonEnum.TASK_KEY);

        final RunningTask task;
        if (isStart) {
            // Set primary key.
            runningTask.setId(id);
            task = runningTask;
        } else {
            // Init running task.
            task = new RunningTask(id, runningTask.getFlowId(), runningTask.getFlowSymbol(), runningTask.getVersion());
        }

        boolean isEnd = false;
        // Get next task node, and save to task.
        for (Node nextLine : NodeMap.getNextNodes(nodeHashMap, runningTask.getNodeSymbol())) {
            Line line = (Line) nextLine;
            if (lineService.checkLine(line)) {
                Node nextNode = NodeMap.getNextNodes(nodeHashMap, line.getSymbol()).get(0);
                if (nextNode instanceof UserTaskNode) {
                    UserTaskNode userTaskNode = (UserTaskNode) nextNode;
                    task.setName(userTaskNode.getName());
                    task.setNodeSymbol(userTaskNode.getSymbol());
                    task.setType(userTaskNode.getType().getName());
                    // TODO determine whether parsing is required
                    task.setExecutor(userTaskNode.getExecutor());
                    // Save new task.
                    saveTask(task);
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
            // If is running, so delete running task and end history task.
            baseMapper.deleteById(runningTask.getId());
            baseMapper.endHistoryTask(runningTask.getId());
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
    }

    private void saveRunningTask(RunningTask task) {
        baseMapper.saveRunningTask(task);
    }

    private void saveHistoryTask(HistoryTask task) {
        baseMapper.saveHistoryTask(task);
    }
}
