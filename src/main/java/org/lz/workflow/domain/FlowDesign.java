package org.lz.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.lz.workflow.basic.Node;
import org.lz.workflow.utils.StringUtil;

/**
 * @author lz
 */
@TableName("flow_design")
public class FlowDesign {
    private Integer id;
    private String name;
    private String symbol;
    private Integer version;
    private String description;
    private String simpleJson;
    private String detailedJson;
    @TableField(exist = false)
    private Node map;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSimpleJson() {
        return simpleJson;
    }

    public void setSimpleJson(String simpleJson) {
        this.simpleJson = simpleJson;
    }

    public String getDetailedJson() {
        return detailedJson;
    }

    public void setDetailedJson(String detailedJson) {
        this.detailedJson = detailedJson;
    }

    public Node getMap() {
        return map;
    }

    public void setMap(Node map) {
        this.map = map;
    }

    public FlowDesign(Integer id, String name, String symbol, Integer version, String description, String simpleJson, String detailedJson, Node map) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.version = version;
        this.description = description;
        this.simpleJson = simpleJson;
        this.detailedJson = detailedJson;
        this.map = map;
    }

    public FlowDesign() {
    }

    public void inspect() {
        if (StringUtil.isEmpty(this.name)) {
            throw new IllegalArgumentException("Name is empty.");
        }
        if (StringUtil.isEmpty(this.symbol)) {
            throw new IllegalArgumentException("Symbol is empty.");
        }
    }
}
