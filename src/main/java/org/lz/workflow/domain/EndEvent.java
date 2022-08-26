package org.lz.workflow.domain;

import org.lz.workflow.basic.Event;
import org.lz.workflow.basic.EventType;

/**
 * @author lz
 */
public class EndEvent implements Event {
    private Integer id;
    private Integer flowDesignId;
    private String name;
    private String symbol;
    private final EventType type = EventType.END;
    private Integer order;
    private String description;

    public EventType getType() {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
