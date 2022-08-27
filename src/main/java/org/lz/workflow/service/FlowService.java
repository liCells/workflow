package org.lz.workflow.service;

import org.lz.workflow.basic.Flow;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.domain.FlowDesign;
import org.lz.workflow.event.EventPublisher;
import org.lz.workflow.event.StartFlowEvent;
import org.lz.workflow.mapper.FlowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * manage running and finished flows
 *
 * @author lz
 */
@Service
public class FlowService {

    private final FlowCommonService flowCommonService;
    private final FlowDesignService flowDesignService;
    private final EventPublisher eventPublisher;
    private final FlowMapper flowMapper;

    public FlowService(FlowCommonService flowCommonService, FlowDesignService flowDesignService, EventPublisher eventPublisher, FlowMapper flowMapper) {
        this.flowCommonService = flowCommonService;
        this.flowDesignService = flowDesignService;
        this.eventPublisher = eventPublisher;
        this.flowMapper = flowMapper;
    }

    @Transactional
    public Flow startFlow(String symbol) {
        return startFlow(new Flow(symbol));
    }

    /**
     * Start flow.
     * Store data to `running` and `history`.
     * Flow id from `flow_common` table.
     *
     * @param flow flow specific parameters
     * @return completed flow
     */
    private Flow startFlow(Flow flow) {
        // Get flow id.
        Long id = flowCommonService.getIdAndIncr(FlowCommonEnum.FLOW_KEY);
        flow.setId(id);
        // Get flow design.
        FlowDesign flowDesign = flowDesignService.getBySymbol(flow.getSymbol());
        flow.setFlowDesignId(flowDesign.getId());

        flow.setStartTime(LocalDate.now());

        // Store data to flow_running & flow_history.
        flowMapper.insertToRunning(flow);
        flowMapper.insertToHistory(flow);

        // Publish start event.
        eventPublisher.startFlow(new StartFlowEvent(flow));

        return flow;
    }

    public Flow deleteFlow(String flowId) {
        // TODO delete from database.
        // delete data of flow_running & flow_history
        return null;
    }

    public Flow destroyFlow(String flowId) {
        // TODO delete and update data from database.
        // 1. delete data of flow_running
        // 2. update data of flow_history
        return null;
    }

    public Flow getFlow(String flowId) {
        // TODO get from database.
        return null;
    }
}
