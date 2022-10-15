package org.lz.workflow.basic;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author lz
 */
public enum FlowState {
    RUNNING("RUNNING"),
    FINISHED("FINISHED"),
    DESTROYED("DESTROYED");

    @EnumValue
    private final String name;

    FlowState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
