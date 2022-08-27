package org.lz.workflow.domain;

import java.time.LocalDate;

/**
 * @author lz
 */
public class UserTask {
    private Long id;
    private Long flowId;
    private String name;
    private String flowSymbol;
    private String nodeId;
    private LocalDate startTime;
    private String executor;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlowSymbol() {
        return flowSymbol;
    }

    public void setFlowSymbol(String flowSymbol) {
        this.flowSymbol = flowSymbol;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserTask(Long id, Long flowId, String name, String flowSymbol, String nodeId, LocalDate startTime, String executor, String type) {
        this.id = id;
        this.flowId = flowId;
        this.name = name;
        this.flowSymbol = flowSymbol;
        this.nodeId = nodeId;
        this.startTime = startTime;
        this.executor = executor;
        this.type = type;
    }

    public UserTask() {
    }
}
