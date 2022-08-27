package org.lz.workflow.domain;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;

/**
 * @author lz
 */
public class EndNode implements Node {
    private Integer id;
    private Integer flowDesignId;
    private String name;
    private String symbol;
    private final NodeType type = NodeType.END;
    private String description;

    public NodeType getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowDesignId() {
        return flowDesignId;
    }

    public void setFlowDesignId(Integer flowDesignId) {
        this.flowDesignId = flowDesignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
