package org.lz.workflow.basic;

/**
 * @author lz
 */
public interface RunningTask extends Task {
    /**
     * @return flow id
     */
    String getFlowId();
}
