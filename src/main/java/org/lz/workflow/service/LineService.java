package org.lz.workflow.service;

import org.lz.workflow.domain.map.Line;
import org.springframework.stereotype.Service;

/**
 * @author lz
 */
@Service
public class LineService {

    /**
     * Parse the conditions on the line
     * @return true if the condition is met
     */
    public boolean checkLine(Line line) {
        // TODO parse condition
        return true;
    }
}
