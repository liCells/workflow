package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.domain.FlowDesign;
import org.lz.workflow.mapper.FlowDesignMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author lz
 */
@Service
public class FlowDesignService extends ServiceImpl<FlowDesignMapper, FlowDesign> {

    public List<FlowDesign> selectAll() {
        return baseMapper.selectList(null);
    }

    /**
     * Returns the latest version of flowDesign to which the symbol is mapped.
     *
     * @param symbol the symbol of the flowDesign.
     * @return the latest version of flowDesign to which.
     */
    public FlowDesign getBySymbol(String symbol) {
        FlowDesign flowDesign = baseMapper.getBySymbol(symbol);

        Objects.requireNonNull(flowDesign);

        return flowDesign;
    }

    /**
     * Update when data with the same id and the version exists.
     *
     * @param flowDesign the flowDesign.getMap() reference to inspect for json data,
     *                   flowDesign save to the database.
     * @throws IllegalArgumentException if the flowDesign's symbol and version are empty,
     *          or the flowDesign's symbol and version are already exists.
     * @throws NullPointerException     if the flowDesign is null.
     */
    public void saveWorkflow(FlowDesign flowDesign) {
        if (flowDesign == null) {
            throw new NullPointerException("flowDesign is null");
        }
        flowDesign.inspect();

        if (flowDesign.getVersion() == null || flowDesign.getVersion() < 1) {
            flowDesign.setVersion(1);
        }

        inspectJson(flowDesign.getMap());

        flowDesign.setSimpleJson(flowDesign.getMap().toString());
        flowDesign.setDetailedJson(flowDesign.getMap().toString());

        if (flowDesign.getId() != null) {
            updateById(flowDesign);
            return;
        }

        int count = baseMapper.getCountBySymbolAndVersion(flowDesign.getSymbol(), flowDesign.getVersion());
        if (count != 0) {
            throw new IllegalArgumentException("Symbol and version are already exists");
        }

        baseMapper.insert(flowDesign);
    }

    /**
     * inspect workflow structure.
     *
     * @param map the map reference to inspect workflow structure.
     * @throws IllegalArgumentException if the workflow structure is null or illegal.
     */
    public void inspectJson(Node map) {
        if (map == null) {
            throw new IllegalArgumentException("The workflow json is null");
        }
        if (map.getType() != NodeType.START) {
            throw new IllegalArgumentException("The workflow json must start with a start node.");
        }
        map.inspect();
    }

}
