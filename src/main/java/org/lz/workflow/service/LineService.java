package org.lz.workflow.service;

import org.lz.workflow.domain.map.Line;
import org.lz.workflow.domain.running.RunningTask;
import org.lz.workflow.helper.ExpressionHelper;
import org.lz.workflow.utils.StringUtil;
import org.springframework.stereotype.Service;

/**
 * @author lz
 */
@Service
public class LineService {

    private final ExpressionHelper expressionHelper;

    public LineService(ExpressionHelper expressionHelper) {
        this.expressionHelper = expressionHelper;
    }

    /**
     * Parse the expression on the line
     *
     * @return true if the expression is met
     */
    public boolean checkLine(RunningTask runningTask, Line line) {
        if (StringUtil.isEmpty(line.getExpression())) {
            return true;
        }
        // TODO parse expression
        return expressionHelper.parse(runningTask, line.getExpression());
    }
}
