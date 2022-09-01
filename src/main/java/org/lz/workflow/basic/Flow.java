package org.lz.workflow.basic;

import java.time.LocalDate;

/**
 * @author lz
 */
public class Flow {
    private Long id;
    private Integer flowDesignId;
    private Integer version;
    private String name;
    private String symbol;
    private LocalDate startTime;
    private FlowState state;

    public Flow(String symbol, Integer version) {
        this.symbol = symbol;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFlowDesignId() {
        return flowDesignId;
    }

    public void setFlowDesignId(Integer flowDesignId) {
        this.flowDesignId = flowDesignId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public FlowState getState() {
        return state;
    }

    public void setState(FlowState state) {
        this.state = state;
    }
}
