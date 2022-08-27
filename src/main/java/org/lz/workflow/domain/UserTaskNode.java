package org.lz.workflow.domain;

import org.lz.workflow.basic.RunningTask;
import org.lz.workflow.basic.TaskType;

/**
 * @author lz
 */
public class UserTaskNode implements RunningTask {
    private String id;
    private Integer flowDesignId;
    private String name;
    private String symbol;
    private TaskType type;
    private Integer order;
    private String description;
    private String flowId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFlowDesignId() {
        return flowDesignId;
    }

    public void setFlowDesignId(Integer flowDesignId) {
        this.flowDesignId = flowDesignId;
    }

    @Override
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

    @Override
    public TaskType getType() {
        return type;
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

    @Override
    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public UserTaskNode(String id, Integer flowDesignId, String name, String symbol, TaskType type, Integer order, String description, String flowId) {
        this.id = id;
        this.flowDesignId = flowDesignId;
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.order = order;
        this.description = description;
        this.flowId = flowId;
    }

    public UserTaskNode() {
    }
}
