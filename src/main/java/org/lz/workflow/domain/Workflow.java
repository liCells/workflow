package org.lz.workflow.domain;

import org.lz.workflow.basic.Node;
import org.lz.workflow.utils.StringUtil;

/**
 * @author lz
 */
public class Workflow {
    private String name;
    private String description;
    private Integer version;
    private String symbol;
    private String id;
    private Node map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Node getMap() {
        return map;
    }

    public void setMap(Node map) {
        this.map = map;
    }

    public Workflow(String name, String description, Integer version, String symbol, String id, Node map) {
        if (StringUtil.isEmpty(id)) {
            throw new IllegalArgumentException("id is empty.");
        }
        if (StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty.");
        }
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }

        this.id = id;
        this.symbol = symbol;
        this.version = version;
        this.description = description;
        this.map = map;
    }
}
