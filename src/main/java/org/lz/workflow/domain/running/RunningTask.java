package org.lz.workflow.domain.running;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.event.StartFlowEvent;

import java.time.LocalDateTime;
import java.util.Map;

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
    @TableField(exist = false)
    private Map<String, Object> variables;
    @TableField(exist = false)
    private Map<String, Object> globalVariables;

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

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public Map<String, Object> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(Map<String, Object> globalVariables) {
        this.globalVariables = globalVariables;
    }

    public RunningTask(Long id, Long flowId, String nodeSymbol, String name, String flowSymbol, String executor, LocalDateTime startTime, String type, Integer version, Map<String, Object> variables, Map<String, Object> globalVariables) {
        this.id = id;
        this.flowId = flowId;
        this.nodeSymbol = nodeSymbol;
        this.name = name;
        this.flowSymbol = flowSymbol;
        this.executor = executor;
        this.startTime = startTime;
        this.type = type;
        this.version = version;
        this.variables = variables;
        this.globalVariables = globalVariables;
    }

    public RunningTask() {
    }

    public RunningTask(StartFlowEvent startFlowEvent) {
        this.flowId = startFlowEvent.getFlowId();
        this.flowSymbol = startFlowEvent.getFlowSymbol();
        this.version = startFlowEvent.getFlowVersion();
        this.nodeSymbol = NodeType.START.getName();
        this.globalVariables = startFlowEvent.getVariables();
        this.startTime = LocalDateTime.now();
    }
}
