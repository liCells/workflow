package org.lz.workflow.exception;

/**
 * @author lz
 */
public class FlowRunningException extends BaseFlowException {
    public FlowRunningException() {
        super("Flow is running.");
    }

    public FlowRunningException(String message) {
        super(message);
    }
}
