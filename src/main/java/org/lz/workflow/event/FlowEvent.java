package org.lz.workflow.event;

import java.util.Map;

/**
 * @author lz
 */
public interface FlowEvent {
    Long getFlowId();

    String getFlowSymbol();

    Integer getFlowVersion();

    Map<String, Object> getVariables();
}
