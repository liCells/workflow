package org.lz.workflow.service;

import com.google.gson.Gson;
import org.lz.workflow.domain.Variable;
import org.lz.workflow.mapper.VariableMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
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

    public Variable getVariable(Long taskId, String key) {
        return getVariable(taskId, key, true);
    }

    public Variable getVariable(Long taskId, String key, boolean isRunning) {
        return variableMapper.getVariable(taskId, key, isRunning);
    }

    public Variable getGlobalVariable(Long flowId, String key) {
        return getGlobalVariable(flowId, key, true);
    }

    public Variable getGlobalVariable(Long flowId, String key, boolean isRunning) {
        return variableMapper.getGlobalVariable(flowId, key, isRunning);
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
            variableMapper.saveTaskVariables(taskId, flowId, convertToVariables(vars), isRunning);
            return;
        } else {
            Map<String, Object> updateVars = new HashMap<>(vars);
            for (String key : keys) {
                if (!updateVars.containsKey(key)) {
                    updateVars.remove(key);
                }
            }
            if (isRunning) {
                variableMapper.updateRunningTaskVariables(taskId, flowId, convertToVariables(updateVars));
            }
            variableMapper.updateHistoryTaskVariables(taskId, flowId, convertToVariables(updateVars));
            keys.forEach(vars::remove);
        }
        if (vars.isEmpty()) {
            return;
        }
        variableMapper.saveTaskVariables(taskId, flowId, convertToVariables(variables), isRunning);
    }

    protected void deleteVariablesByFlowId(Long flowId, boolean isRunning) {
        variableMapper.deleteVariablesByFlowId(flowId, isRunning);
    }

    protected void deleteVariablesByTaskId(Long taskId, boolean isRunning) {
        variableMapper.deleteVariablesByTaskId(taskId, isRunning);
    }

    private List<Variable> convertToVariables(Map<String, Object> variables) {
        List<Variable> vars = new LinkedList<>();
        Gson gson = new Gson();
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            Variable variable = new Variable();
            variable.setName(entry.getKey());
            switch (entry.getValue().getClass().getSimpleName()) {
                case "String":
                case "Integer":
                case "Long":
                case "Double":
                case "Boolean":
                    variable.setType(entry.getValue().getClass().getSimpleName());
                    variable.setVal(gson.toJson(entry.getValue()));
                    break;
                default:
                    variable.setType(entry.getValue().getClass().getCanonicalName());
                    variable.setVal(gson.toJson(entry.getValue()));
                    break;
            }
            vars.add(variable);
        }
        return vars;
    }
}
