package org.lz.workflow.mapper;

import org.lz.workflow.basic.Flow;

/**
 * @author lz
 */
public interface FlowMapper {
    // TODO implement
    void insertToRunning(Flow flow);

    // TODO implement
    void insertToHistory(Flow flow);
}
