package org.lz.workflow.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lz
 */
public interface VariableMapper {

    void saveTaskVariables(@Param("taskId") Long taskId, @Param("flowId") Long flowId, @Param("variables") Map<String, Object> variables, @Param("isRunning") boolean isRunning);

    void deleteVariablesByTaskId(@Param("taskId") Long taskId, @Param("isRunning") boolean isRunning);

    void deleteVariablesByFlowId(@Param("flowId") Long flowId, @Param("isRunning") boolean isRunning);

    List<String> selectVariables(@Param("taskId") Long taskId, @Param("flowId") Long flowId, @Param("names") Set<String> names, @Param("isRunning") boolean isRunning);

    void updateRunningTaskVariables(@Param("taskId") Long taskId, @Param("flowId") Long flowId, @Param("variables") Map<String, Object> updateVars);

    void updateHistoryTaskVariables(@Param("taskId") Long taskId, @Param("flowId") Long flowId, @Param("variables") Map<String, Object> updateVars);
}
