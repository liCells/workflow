package org.lz.workflow.mapper;

import org.apache.ibatis.annotations.Param;
import org.lz.workflow.basic.Flow;

/**
 * @author lz
 */
public interface FlowMapper {
    void insertToRunning(Flow flow);

    void insertToHistory(Flow flow);

    String getState(Long flowId);

    Flow get(@Param("flowId") Long flowId, @Param("isRunning") boolean isRunning);
}
