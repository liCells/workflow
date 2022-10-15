package org.lz.workflow.event;

/**
 * @author lz
 */
public class DestroyFlowEvent implements FlowEvent {
    private final Long flowId;

    public DestroyFlowEvent(Long flowId) {
        this.flowId = flowId;
    }

    @Override
    public Long getFlowId() {
        return flowId;
    }
}
