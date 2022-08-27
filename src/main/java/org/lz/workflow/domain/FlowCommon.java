package org.lz.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.lz.workflow.basic.FlowCommonEnum;

/**
 * @author lz
 */
@TableName("flow_common")
public class FlowCommon {
    @TableId
    private String symbol;
    private String value;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FlowCommon(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public FlowCommon(String symbol) {
        this.symbol = symbol;
    }

    public FlowCommon(FlowCommonEnum flowCommon) {
        this.symbol = flowCommon.getSymbol();
    }

    public FlowCommon() {
    }
}
