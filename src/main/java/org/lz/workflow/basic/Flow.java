package org.lz.workflow.basic;

import org.lz.workflow.domain.FlowDesign;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author lz
 */
public class Flow {
    private Long id;
    private Integer flowDesignId;
    private Integer version;
    private String name;
    private String symbol;
    private LocalDateTime startTime;
    private FlowState state;
    private Map<String, Object> variables;

    public Flow(FlowDesign flowDesign, Map<String, Object> variables) {
        this.symbol = flowDesign.getSymbol();
        this.version = flowDesign.getVersion();
        this.name = flowDesign.getName();
        this.variables = variables;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public FlowState getState() {
        return state;
    }

    public void setState(FlowState state) {
        this.state = state;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
