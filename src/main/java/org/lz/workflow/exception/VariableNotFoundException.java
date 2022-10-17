package org.lz.workflow.exception;

/**
 * @author lz
 */
public class VariableNotFoundException extends ExpressionException {
    public VariableNotFoundException() {
        super("Variable not found.");
    }

    public VariableNotFoundException(String message) {
        super(message);
    }
}
