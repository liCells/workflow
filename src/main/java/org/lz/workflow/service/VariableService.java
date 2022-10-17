package org.lz.workflow.service;

import org.lz.workflow.mapper.VariableMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lz
 */
@Service
public class VariableService {
    private final VariableMapper variableMapper;

    public VariableService(VariableMapper variableMapper) {
        this.variableMapper = variableMapper;
    }

    protected void saveVariables(Long taskId, Long flowId, Map<String, Object> variables) {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        if (flowId == null) {
            throw new IllegalArgumentException("flowId is empty.");
        }
        saveVariables(taskId, flowId, variables, true);
        saveVariables(taskId, flowId, variables, false);
    }

    protected void saveVariables(Long taskId, Long flowId, Map<String, Object> variables, boolean isRunning) {
        Map<String, Object> vars = new HashMap<>(variables);
        // Get whether it exists
        List<String> keys = variableMapper.selectVariables(taskId, flowId, vars.keySet(), isRunning);
        if (keys.isEmpty()) {
            variableMapper.saveTaskVariables(taskId, flowId, vars, isRunning);
            return;
        } else {
            Map<String, Object> updateVars = new HashMap<>(vars);
            for (String key : keys) {
                if (!updateVars.containsKey(key)) {
                    updateVars.remove(key);
                }
            }
            if (isRunning) {
                variableMapper.updateRunningTaskVariables(taskId, flowId, updateVars);
            }
            variableMapper.updateHistoryTaskVariables(taskId, flowId, updateVars);
            keys.forEach(vars::remove);
        }
        if (vars.isEmpty()) {
            return;
        }
        variableMapper.saveTaskVariables(taskId, flowId, vars, isRunning);
    }

    protected void deleteVariablesByFlowId(Long flowId, boolean isRunning) {
        variableMapper.deleteVariablesByFlowId(flowId, isRunning);
    }

    protected void deleteVariablesByTaskId(Long taskId, boolean isRunning) {
        variableMapper.deleteVariablesByTaskId(taskId, isRunning);
    }
}
