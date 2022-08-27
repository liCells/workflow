package org.lz.workflow.event;

import org.lz.workflow.basic.Flow;

/**
 * @author lz
 */
public class StartFlowEvent implements FlowEvent {
    private final Flow flow;

    @Override
    public Long getFlowId() {
        return flow.getId();
    }

    @Override
    public String getFlowSymbol() {
        return flow.getSymbol();
    }

    public StartFlowEvent(Flow flow) {
        this.flow = flow;
    }
}
