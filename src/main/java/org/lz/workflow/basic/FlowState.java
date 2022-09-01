package org.lz.workflow.basic;

/**
 * @author lz
 */
public enum FlowState {
    RUNNING("RUNNING"),
    FINISHED("FINISHED"),
    DESTROYED("DESTROYED");
    private final String name;

    FlowState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
