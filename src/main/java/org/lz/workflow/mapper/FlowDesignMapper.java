package org.lz.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lz.workflow.domain.FlowDesign;

/**
 * @author lz
 */
@Mapper
public interface FlowDesignMapper extends BaseMapper<FlowDesign> {
    FlowDesign getBySymbol(String symbol);

    int getCountBySymbolAndVersion(@Param("symbol") String symbol, @Param("version") Integer version);
}
