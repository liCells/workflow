package org.lz.workflow.event;

/**
 * @author lz
 */
public interface TaskEvent extends FlowEvent {
    Long getTaskId();

    String getTaskNode();
}
