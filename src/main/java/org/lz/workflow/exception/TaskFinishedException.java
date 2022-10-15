package org.lz.workflow.exception;

/**
 * @author lz
 */
public class TaskFinishedException extends BaseFlowException {
    public TaskFinishedException() {
        super("Task is ended.");
    }

    public TaskFinishedException(String message) {
        super(message);
    }
}
