package org.lz.workflow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lz.workflow.basic.Node;
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
    public FlowDesign get(String symbol) {
        FlowDesign flowDesign = baseMapper.getBySymbol(symbol);

        Objects.requireNonNull(flowDesign, "The flow is not exist.");

        return flowDesign;
    }

    /**
     * Returns specify the version of flowDesign to which the symbol is mapped.
     *
     * @param symbol the symbol of the flowDesign.
     * @param version the version of the flowDesign.
     * @return specify the version of flowDesign to which.
     */
    public FlowDesign get(String symbol, int version) {
        return baseMapper.getBySymbolAndVersion(symbol, version);
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
            throw new NullPointerException("flowDesign is null.");
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

        int count = getCountBySymbolAndVersion(flowDesign.getSymbol(), flowDesign.getVersion());
        if (count != 0) {
            throw new IllegalArgumentException("Symbol and version are already exists.");
        }

        baseMapper.insert(flowDesign);
    }

    public int getCountBySymbolAndVersion(String symbol, Integer version) {
        return baseMapper.getCountBySymbolAndVersion(symbol, version);
    }

    /**
     * inspect workflow structure.
     *
     * @param map the map reference to inspect workflow structure.
     * @throws IllegalArgumentException if the workflow structure is null or illegal.
     */
    private void inspectJson(List<Node> map) {
        if (map == null) {
            throw new IllegalArgumentException("The workflow json is null.");
        }
        for (Node node : map) {
            node.inspect();
        }
    }
}
