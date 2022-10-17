package org.lz.workflow.exception;

/**
 * @author lz
 */
public class ExpressionException extends BaseFlowException {
    public ExpressionException() {
        super("Expression is wrong.");
    }

    public ExpressionException(String message) {
        super(message);
    }

    public ExpressionException(Throwable throwable) {
        super(throwable);
    }
}
