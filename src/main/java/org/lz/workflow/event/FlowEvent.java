package org.lz.workflow.event;

import java.util.Map;

/**
 * @author lz
 */
public interface FlowEvent {
    Long getFlowId();

    default String getFlowSymbol() {
        throw new UnsupportedOperationException();
    }

    default Integer getFlowVersion() {
        throw new UnsupportedOperationException();
    }

    default Map<String, Object> getVariables() {
        throw new UnsupportedOperationException();
    }
}
