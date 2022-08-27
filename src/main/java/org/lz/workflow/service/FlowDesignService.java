package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.domain.FlowDesign;
import org.lz.workflow.mapper.FlowDesignMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lz
 */
@Service
public class FlowDesignService extends ServiceImpl<FlowDesignMapper, FlowDesign> {

    public FlowDesign getBySymbol(String symbol) {
        FlowDesign flowDesign = baseMapper.getBySymbol(symbol);

        Objects.requireNonNull(flowDesign);

        return flowDesign;
    }
}
