package org.lz.workflow.helper;

import com.google.gson.Gson;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import org.lz.workflow.domain.Variable;
import org.lz.workflow.domain.running.RunningTask;
import org.lz.workflow.exception.ExpressionException;
import org.lz.workflow.exception.VariableNotFoundException;
import org.lz.workflow.service.VariableService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author lz
 */
@Component
public class ExpressionHelper {

    private final VariableService variableService;

    public ExpressionHelper(VariableService variableService) {
        this.variableService = variableService;
    }

    /**
     * Task variables are obtained first, followed by global variables.
     *
     * @param runningTask running task
     * @param expression #variableName
     * @return true, otherwise false
     */
    public boolean parse(RunningTask runningTask, String expression) {
        AviatorEvaluatorInstance evaluator = AviatorEvaluator.getInstance();
        Expression expr = evaluator.compile(expression);
        List<String> vars = expr.getVariableNames();
        if (vars.isEmpty()) {
            return (boolean) expr.execute();
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        for (String var : vars) {
            if (runningTask.getVariables().containsKey(var)) {
                hashMap.put(var, runningTask.getVariables().get(var));
                continue;
            }
            if (runningTask.getGlobalVariables().containsKey(var)) {
                hashMap.put(var, runningTask.getGlobalVariables().get(var));
                continue;
            }
            Variable variable = variableService.getVariable(runningTask.getId(), var);
            if (variable != null) {
                hashMap.put(var, convert(variable));
                continue;
            }
            variable = variableService.getGlobalVariable(runningTask.getFlowId(), var);
            if (variable != null) {
                hashMap.put(var, convert(variable));
                continue;
            }
            throw new VariableNotFoundException();
        }
        return (boolean) expr.execute(hashMap);
    }

    private Object convert(Variable variable) {
        switch (variable.getType()) {
            case "String":
                return variable.getVal();
            case "Integer":
                return Integer.valueOf(variable.getVal());
            case "Long":
                return Long.valueOf(variable.getVal());
            case "Double":
                return Double.valueOf(variable.getVal());
            case "Boolean":
                return Boolean.valueOf(variable.getVal());
            default:
                try {
                    Class<?> aClass = Class.forName(Variable.class.getCanonicalName());
                    Gson gson = new Gson();
                    return gson.fromJson(variable.getVal(), aClass);
                } catch (ClassNotFoundException e) {
                    throw new ExpressionException(e);
                }
        }
    }
}
