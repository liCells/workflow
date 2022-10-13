package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.basic.cache.NodeMap;
import org.lz.workflow.domain.map.Line;
import org.lz.workflow.domain.map.UserTaskNode;
import org.lz.workflow.domain.running.RunningTask;
import org.lz.workflow.event.StartFlowEvent;
import org.lz.workflow.mapper.FlowTaskMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

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
        // Get id.
        Long id = flowCommonService.getIdAndIncr(FlowCommonEnum.TASK_KEY);

        // Init running task.
        final RunningTask task = new RunningTask(id, startFlowEvent.getFlowId(), startFlowEvent.getFlowSymbol());

        HashMap<String, Node> nodeHashMap = NodeMap.get(startFlowEvent.getFlowSymbol(), startFlowEvent.getFlowVersion());

        // Get next task node, and save to task.
        for (Node nextLine : NodeMap.getNextNodes(nodeHashMap, NodeType.START.getName())) {
            Line line = (Line) nextLine;
            if (lineService.checkLine(line)) {
                Node nextTaskNode = NodeMap.getNextNodes(nodeHashMap, line.getSymbol()).get(0);
                if (nextTaskNode instanceof UserTaskNode) {
                    UserTaskNode userTaskNode = (UserTaskNode) nextTaskNode;
                    task.setName(nextTaskNode.getName());
                    task.setNodeSymbol(userTaskNode.getSymbol());
                    task.setType(userTaskNode.getType().getName());
                    // TODO determine whether parsing is required
                    task.setExecutor(userTaskNode.getExecutor());
                    break;
                }
                // TODO other node type
            }
        }
        save(task);
    }

}
