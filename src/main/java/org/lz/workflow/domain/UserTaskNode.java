package org.lz.workflow.domain;

import org.lz.workflow.basic.RunningTask;
import org.lz.workflow.basic.TaskType;

/**
 * @author lz
 */
public class UserTaskNode implements RunningTask {
    private Integer id;
    private Integer flowDesignId;
    private String name;
    private String symbol;
    private TaskType type;
    private Integer order;
    private String description;
    private String flowId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowDesignId() {
        return flowDesignId;
    }

    public void setFlowDesignId(Integer flowDesignId) {
        this.flowDesignId = flowDesignId;
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

    public void setType(TaskType type) {
        this.type = type;
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

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return this.flowId;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }

    public TaskType getType() {
        return this.type;
    }
}
