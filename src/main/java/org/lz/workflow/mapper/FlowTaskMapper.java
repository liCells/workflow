package org.lz.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lz.workflow.domain.history.HistoryTask;
import org.lz.workflow.domain.running.RunningTask;

/**
 * @author lz
 */
@Mapper
public interface FlowTaskMapper extends BaseMapper<RunningTask> {

    void saveRunningTask(@Param("task") RunningTask task);

    void saveHistoryTask(@Param("task") HistoryTask task);

    void endHistoryTask(@Param("taskId") Long taskId);

    int selectRunningTaskCountByFlowId(@Param("flowId") Long flowId);

    void endFlow(Long flowId);

    void deleteFlow(@Param("flowId") Long flowId, @Param("isRunning") boolean isRunning);

    void deleteTaskByFlowId(@Param("flowId") Long flowId, @Param("isRunning") boolean isRunning);

    void endTaskByFlowId(Long flowId);

    void destroyFlow(Long flowId);
}
