package org.lz.workflow.exception;

public class BaseFlowException extends RuntimeException {
    public BaseFlowException() {
        super();
    }

    public BaseFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseFlowException(String message) {
        super(message);
    }

    public BaseFlowException(Throwable throwable) {
        super(throwable);
    }
}
