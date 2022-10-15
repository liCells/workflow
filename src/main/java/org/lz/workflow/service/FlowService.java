package org.lz.workflow.service;

import org.lz.workflow.basic.Flow;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.basic.FlowState;
import org.lz.workflow.domain.FlowDesign;
import org.lz.workflow.event.DeleteFlowEvent;
import org.lz.workflow.event.DestroyFlowEvent;
import org.lz.workflow.event.EventPublisher;
import org.lz.workflow.event.StartFlowEvent;
import org.lz.workflow.exception.FlowFinishedException;
import org.lz.workflow.exception.FlowRunningException;
import org.lz.workflow.mapper.FlowMapper;
import org.lz.workflow.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * manage running and finished flows
 *
 * @author lz
 */
@Service
public class FlowService extends FlowCommonService {

    private final FlowDesignService flowDesignService;

    private final EventPublisher eventPublisher;

    private final FlowMapper flowMapper;

    public FlowService(FlowDesignService flowDesignService, EventPublisher eventPublisher, FlowMapper flowMapper) {
        this.flowDesignService = flowDesignService;
        this.eventPublisher = eventPublisher;
        this.flowMapper = flowMapper;
    }

    @Transactional
    public Flow startFlow(String symbol, Map<String, Object> variables) {
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }
        FlowDesign flowDesign = flowDesignService.get(symbol);

        return startFlow(new Flow(flowDesign, variables));
    }

    @Transactional
    public Flow startFlow(String symbol) {
        return startFlow(symbol, null);
    }

    @Transactional
    public Flow startFlow(String symbol, int version, Map<String, Object> variables) {
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }
        if (version < 1) {
            throw new IllegalArgumentException("version is empty.");
        }
        FlowDesign flowDesign = flowDesignService.get(symbol, version);
        if (flowDesign == null) {
            throw new IllegalArgumentException("The version symbol is not exists.");
        }
        return startFlow(new Flow(flowDesign, variables));
    }

    @Transactional
    public Flow startFlow(String symbol, int version) {
        return startFlow(symbol, version, null);
    }

    /**
     * Start flow.
     * Store data to `running` and `history`.
     * Flow id from `flow_common` table.
     *
     * @event StartFlowEvent
     * @param flow flow specific parameters
     * @return completed flow
     */
    private Flow startFlow(Flow flow) {
        // Get flow id.
        Long id = getIdAndIncr(FlowCommonEnum.FLOW_KEY);
        flow.setId(id);
        flow.setStartTime(LocalDateTime.now());

        // Store data to flow_running & flow_history.
        flowMapper.insertToRunning(flow);
        flow.setState(FlowState.RUNNING);
        flowMapper.insertToHistory(flow);

        // Publish start event.
        eventPublisher.setFlowEvent(new StartFlowEvent(flow));
        return flow;
    }

    /**
     * Delete running or history flow.
     * If the flow's state is running,
     * then `forceDelete` needs to be true,
     * otherwise an exception is thrown.
     *
     * @event DeleteFlowEvent
     * @exception FlowRunningException if the flow's state is running and `forceDelete` is false.
     * @param flowId flow id
     * @return destroy flow
     */
    @Transactional
    public Flow deleteFlow(Long flowId, boolean forceDelete) {
        if (flowId == null) {
            throw new IllegalArgumentException("flowId is empty.");
        }
        if (!forceDelete && FlowState.RUNNING.getName().equals(flowMapper.getState(flowId))) {
            throw new FlowRunningException();
        }
        Flow flow = getFlow(flowId);
        // delete data of flow_running & flow_history & flow_running_task & flow_history_task
        // & flow_running_variables & flow_history_variables
        eventPublisher.setFlowEvent(new DeleteFlowEvent(flowId));
        return flow;
    }

    /**
     * Destroy running flow.
     * If the flow's state is finished, an exception is thrown.
     *
     * @event DestroyFlowEvent
     * @exception FlowFinishedException if the flow's state is finished.
     * @param flowId flow id
     * @return destroy flow
     */
    @Transactional
    public Flow destroyFlow(Long flowId) {
        if (flowId == null) {
            throw new IllegalArgumentException("flowId is empty.");
        }
        if (!FlowState.RUNNING.getName().equals(flowMapper.getState(flowId))) {
            throw new FlowFinishedException();
        }

        Flow flow = getFlow(flowId);
        // 1. delete data of flow_running & flow_running_task & flow_running_variables
        // 2. update data of flow_history & flow_history_task
        eventPublisher.setFlowEvent(new DestroyFlowEvent(flowId));
        flow.setState(FlowState.DESTROYED);
        return flow;
    }

    /**
     * Get history flow by id.
     *
     * @param flowId flow id
     * @return history flow
     */
    public Flow getFlow(Long flowId) {
        return getFlow(flowId, false);
    }

    /**
     * Get running or history flow by id.
     *
     * @param flowId flow id
     * @param isRunning if true is running
     * @return running or history flow
     */
    public Flow getFlow(Long flowId, boolean isRunning) {
        if (flowId == null) {
            throw new IllegalArgumentException("flowId is empty.");
        }
        Flow flow = flowMapper.get(flowId, isRunning);

        Objects.requireNonNull(flow, "The flow is not exists.");

        return flow;
    }
}
