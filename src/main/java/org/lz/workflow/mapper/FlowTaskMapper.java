package org.lz.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lz.workflow.domain.running.RunningTask;

/**
 * @author lz
 */
@Mapper
public interface FlowTaskMapper extends BaseMapper<RunningTask> {

}
