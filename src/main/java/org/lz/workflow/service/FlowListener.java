package org.lz.workflow.service;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.cache.NodeMap;
import org.lz.workflow.domain.running.RunningTask;
import org.lz.workflow.event.DeleteFlowEvent;
import org.lz.workflow.event.DestroyFlowEvent;
import org.lz.workflow.event.StartFlowEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author lz
 */
@Component
public class FlowListener {
    private final FlowTaskService flowTaskService;

    public FlowListener(FlowTaskService flowTaskService) {
        this.flowTaskService = flowTaskService;
    }

    @Transactional
    @EventListener(StartFlowEvent.class)
    public void startFlow(StartFlowEvent event) {
        HashMap<String, Node> nodeHashMap = NodeMap.get(event.getFlowSymbol(), event.getFlowVersion());

        Objects.requireNonNull(nodeHashMap, "Flow not exist.");

        // Init running task.
        final RunningTask task = new RunningTask(event);

        flowTaskService.complete(nodeHashMap, task, true);
    }

    @Transactional
    @EventListener(DeleteFlowEvent.class)
    public void deleteTask(DeleteFlowEvent event) {
        flowTaskService.deleteRunning(event.getFlowId());
        flowTaskService.deleteHistory(event.getFlowId());
    }

    @Transactional
    @EventListener(DestroyFlowEvent.class)
    public void deleteRunningTask(DestroyFlowEvent event) {
        flowTaskService.deleteRunning(event.getFlowId());
        flowTaskService.destroyHistory(event.getFlowId());
    }
}
