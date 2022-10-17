package org.lz.workflow.service;

import org.lz.workflow.domain.map.Line;
import org.springframework.stereotype.Service;

/**
 * @author lz
 */
@Service
public class LineService {

    /**
     * Parse the expression on the line
     * @return true if the expression is met
     */
    public boolean checkLine(Line line) {
        // TODO parse expression
        return true;
    }
}
