package org.lz.workflow.basic;
/**
 * @author lz
 */
public interface Task {
    /**
     * @return task node name
     */
    String getName();

    /**
     * @return task id
     */
    Long getId();

    /**
     * @return task type
     */
    TaskType getType();
}
