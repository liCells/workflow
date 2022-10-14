package org.lz.workflow.service;

import org.lz.workflow.basic.Flow;
import org.lz.workflow.basic.FlowCommonEnum;
import org.lz.workflow.basic.FlowState;
import org.lz.workflow.domain.FlowDesign;
import org.lz.workflow.event.EventPublisher;
import org.lz.workflow.event.StartFlowEvent;
import org.lz.workflow.mapper.FlowMapper;
import org.lz.workflow.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public Flow startFlow(String symbol) {
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }
        FlowDesign flowDesign = flowDesignService.getBySymbol(symbol);
        return startFlow(new Flow(symbol, flowDesign.getVersion(), flowDesign.getName()));
    }

    @Transactional
    public Flow startFlow(String symbol, Integer version) {
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }
        if (version == null || version < 1) {
            throw new IllegalArgumentException("version is empty.");
        }
        FlowDesign flowDesign = flowDesignService.getBySymbolAndVersion(symbol, version);
        if (flowDesign == null) {
            throw new IllegalArgumentException("The version symbol is not exists.");
        }
        return startFlow(new Flow(symbol, version, flowDesign.getName()));
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
        Long id = getIdAndIncr(FlowCommonEnum.FLOW_KEY);
        flow.setId(id);
        // Get flow design.
        FlowDesign flowDesign = flowDesignService.getBySymbol(flow.getSymbol());
        flow.setFlowDesignId(flowDesign.getId());

        flow.setStartTime(LocalDateTime.now());
        flow.setVersion(flowDesign.getVersion());

        // Store data to flow_running & flow_history.
        flowMapper.insertToRunning(flow);
        flow.setState(FlowState.RUNNING);
        flowMapper.insertToHistory(flow);

        // Publish start event.
        eventPublisher.startFlow(new StartFlowEvent(flow));

        return flow;
    }

    public Flow deleteFlow(String flowId) {
        // TODO delete from database.
        // delete data of flow_running & flow_history & flow_running_task & flow_history_task
        return null;
    }

    public Flow destroyFlow(String flowId) {
        // TODO delete and update data from database.
        // 1. delete data of flow_running & flow_running_task
        // 2. update data of flow_history & flow_history_task
        return null;
    }

    public Flow getFlow(String flowId) {
        // TODO get from database.
        return null;
    }
}
