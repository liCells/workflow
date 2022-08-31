package org.lz.workflow.basic;

/**
 * @author lz
 */
public enum NodeType {
    START("START"),
    END("END"),
    USUAL_TASK("USUAL_TASK"),
    PARALLEL_TASK("PARALLEL_TASK"),
    SERIAL_TASK("SERIAL_TASK"),
    SINGLE_ENDED("SINGLE_ENDED"),
    DOUBLE_ENDED("DOUBLE_ENDED"),
    AUTO_TASK("AUTO_TASK"),
    TIMER_TASK("TIMER_TASK"),
    NULL("NULL");

    private final String name;

    NodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static NodeType getByValue(String value) {
        for (NodeType type : values()) {
            if (type.getName().equals(value)) {
                return type;
            }
        }
        return NULL;
    }

}
