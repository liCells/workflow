package org.lz.workflow.event;

/**
 * @author lz
 */
public class DeleteFlowEvent implements FlowEvent {
    private final Long flowId;

    public DeleteFlowEvent(Long flowId) {
        this.flowId = flowId;
    }

    @Override
    public Long getFlowId() {
        return flowId;
    }
}
