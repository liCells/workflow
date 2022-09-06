package org.lz.workflow.domain.map;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.utils.StringUtil;

import java.util.List;

/**
 * @author lz
 */
public class Line implements Node {
    private Integer id;
    private String name;
    private NodeType type;
    private String symbol;
    private boolean defaultSelected;
    private String condition;
    private String description;
    private String go;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isDefaultSelected() {
        return defaultSelected;
    }

    public void setDefaultSelected(boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public Line(Integer id, String name, NodeType type, String symbol, boolean defaultSelected, String condition, String description, String go) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.symbol = symbol;
        this.defaultSelected = defaultSelected;
        this.condition = condition;
        this.description = description;
        this.go = go;
    }

    public Line() {
    }

    @Override
    public void inspect() {
        if (StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty.");
        }
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }
        if (StringUtil.isNotEmpty(condition)) {
            // TODO inspect condition
        }
        if (go == null) {
            throw new IllegalArgumentException(String.format("{%s}'s go is null.", name));
        }
    }

    @Override
    public List<Node> getNextTaskNode() {
        // TODO get next task node in NodeMap
        return null;
    }

    @Override
    public List<Node> getNextNode() {
        // TODO get next node in NodeMap
        return null;
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"type\":\"" + type + '"' +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"defaultSelected\":" + defaultSelected +
                ", \"condition\":\"" + (condition == null ? "" : condition) + '"' +
                ", \"description\":\"" + (description == null ? "" : description) + '"' +
                ", \"go\":" + go +
                '}';
    }
}
