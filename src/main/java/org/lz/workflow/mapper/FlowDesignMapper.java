package org.lz.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lz.workflow.domain.FlowDesign;

/**
 * @author lz
 */
public interface FlowDesignMapper extends BaseMapper<FlowDesign> {
    FlowDesign getBySymbol(String symbol);
}
