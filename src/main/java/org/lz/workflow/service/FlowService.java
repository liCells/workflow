package org.lz.workflow.service;

import org.lz.workflow.basic.Flow;

/**
 * manage running and finished flows
 *
 * @author lz
 */
public class FlowService {
    public Flow startFlow(Flow flow) {
        // TODO save to database.
        // flow_running & flow_history
        return flow;
    }

    public Flow deleteFlow(String flowId) {
        // TODO delete from database.
        // delete data of flow_running & flow_history
        return null;
    }

    public Flow destroyFlow(String flowId) {
        // TODO delete and update data from database.
        // 1. delete data of flow_running
        // 2. update data of flow_history
        return null;
    }

    public Flow getFlow(String flowId) {
        // TODO get from database.
        return null;
    }
}
