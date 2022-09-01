package org.lz.workflow.domain.running;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;

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
    private LocalDate startTime;
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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RunningTask(Long id, Long flowId, String nodeSymbol, String name, String flowSymbol, String executor, LocalDate startTime, String type) {
        this.id = id;
        this.flowId = flowId;
        this.nodeSymbol = nodeSymbol;
        this.name = name;
        this.flowSymbol = flowSymbol;
        this.executor = executor;
        this.startTime = startTime;
        this.type = type;
    }

    public RunningTask() {
    }
}
