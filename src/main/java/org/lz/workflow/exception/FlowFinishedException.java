package org.lz.workflow.exception;

/**
 * @author lz
 */
public class FlowFinishedException extends BaseFlowException {
    public FlowFinishedException() {
        super("Flow is finished.");
    }

    public FlowFinishedException(String message) {
        super(message);
    }
}
