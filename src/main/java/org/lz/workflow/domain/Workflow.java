package org.lz.workflow.domain;

import org.lz.workflow.basic.Node;
import org.lz.workflow.utils.StringUtil;

/**
 * @author lz
 */
public class Workflow {
    private String name;
    private String description;
    private Integer version;
    private String symbol;
    private String flowDesignId;
    private String flowDesignSymbol;
    private Node map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFlowDesignId() {
        return flowDesignId;
    }

    public void setFlowDesignId(String flowDesignId) {
        this.flowDesignId = flowDesignId;
    }

    public String getFlowDesignSymbol() {
        return flowDesignSymbol;
    }

    public void setFlowDesignSymbol(String flowDesignSymbol) {
        this.flowDesignSymbol = flowDesignSymbol;
    }

    public Node getMap() {
        return map;
    }

    public void setMap(Node map) {
        this.map = map;
    }

    public Workflow(String name, String description, Integer version, String symbol, String flowDesignId, String flowDesignSymbol, Node map) {
        if (StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty");
        }
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty");
        }
        if (StringUtil.isEmpty(flowDesignSymbol)) {
            throw new IllegalArgumentException("flowDesignSymbol is empty");
        }
        if (StringUtil.isEmpty(flowDesignId)) {
            throw new IllegalArgumentException("flowDesignId is empty");
        }

        this.description = description;
        this.version = version;
        this.symbol = symbol;
        this.flowDesignId = flowDesignId;
        this.flowDesignSymbol = flowDesignSymbol;
        this.map = map;
    }
}
