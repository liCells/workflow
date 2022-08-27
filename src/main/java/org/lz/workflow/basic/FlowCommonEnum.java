package org.lz.workflow.basic;

/**
 * @author lz
 */
public enum FlowCommonEnum {
    FLOW_KEY("flow_id");
    private final String symbol;

    FlowCommonEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
