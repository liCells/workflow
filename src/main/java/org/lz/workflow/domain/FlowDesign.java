package org.lz.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lz
 */
@TableName("flow_design")
public class FlowDesign {
    private Integer id;
    private String name;
    private String symbol;
    private String version;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FlowDesign(Integer id, String name, String symbol, String version, String description) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.version = version;
        this.description = description;
    }

    public FlowDesign() {
    }
}
