package org.lz.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lz.workflow.domain.history.HistoryTask;
import org.lz.workflow.domain.running.RunningTask;

import java.util.Map;

/**
 * @author lz
 */
@Mapper
public interface FlowTaskMapper extends BaseMapper<RunningTask> {

    void saveRunningTask(RunningTask task);

    void saveHistoryTask(HistoryTask task);

    void endHistoryTask(@Param("taskId") Long taskId);

    int selectRunningTaskCountByFlowId(@Param("flowId") Long flowId);

    void endFlow(Long flowId);

    void deleteRunningFlow(Long flowId);

    void saveRunningTaskVariables(@Param("taskId") Long taskId, @Param("flowId") Long flowId, @Param("variables") Map<String, Object> variables);

    void saveHistoryTaskVariables(@Param("taskId") Long taskId, @Param("flowId") Long flowId, @Param("variables") Map<String, Object> variables);

    void deleteRunningVariables(Long taskId);

    void deleteRunningTaskByFlowId(Long flowId);

    void deleteRunningTaskVariablesByFlowId(Long flowId);

    void deleteHistoryTaskByFlowId(Long flowId);

    void deleteHistoryTaskVariablesByFlowId(Long flowId);

    void deleteRunningByFlowId(Long flowId);

    void deleteHistoryByFlowId(Long flowId);

    void endHistoryTaskByFlowId(Long flowId);

    void destroyFlow(Long flowId);
}
