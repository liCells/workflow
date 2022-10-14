package org.lz.workflow.domain.running;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * @author lz
 */
@TableName("flow_running_task")
public class RunningTask {
    private Long id;
    private Long flowId;
    private String nodeSymbol;
    private String name;
    private String flowSymbol;
    private String executor;
    private LocalDateTime startTime;
    private String type;
    private Integer version;

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

    public String getNodeSymbol() {
        return nodeSymbol;
    }

    public void setNodeSymbol(String nodeSymbol) {
        this.nodeSymbol = nodeSymbol;
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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public RunningTask(Long id, Long flowId, String nodeSymbol, String name, String flowSymbol, String executor, LocalDateTime startTime, String type, Integer version) {
        this.id = id;
        this.flowId = flowId;
        this.nodeSymbol = nodeSymbol;
        this.name = name;
        this.flowSymbol = flowSymbol;
        this.executor = executor;
        this.startTime = startTime;
        this.type = type;
        this.version = version;
    }

    public RunningTask() {
    }

    public RunningTask(Long flowId, String flowSymbol, Integer version, String nodeSymbol) {
        this.flowId = flowId;
        this.flowSymbol = flowSymbol;
        this.version = version;
        this.nodeSymbol = nodeSymbol;
        this.startTime = LocalDateTime.now();
    }

    public RunningTask(Long id, Long flowId, String flowSymbol, Integer version) {
        this.id = id;
        this.flowId = flowId;
        this.flowSymbol = flowSymbol;
        this.version = version;
        this.startTime = LocalDateTime.now();
    }
}
