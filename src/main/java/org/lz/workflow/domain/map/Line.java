package org.lz.workflow.domain.map;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.utils.StringUtil;

import java.util.Collections;
import java.util.HashMap;
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
    private String expression;
    private String description;
    private String go;
    private Integer version;

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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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

    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Line(Integer id, String name, NodeType type, String symbol, boolean defaultSelected, String expression, String description, String go) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.symbol = symbol;
        this.defaultSelected = defaultSelected;
        this.expression = expression;
        this.description = description;
        this.go = go;
    }

    public Line() {
    }

    @Override
    public void inspect() {
        if (StringUtil.isEmpty(name)) {
            this.name = "Line";
        }
        if (StringUtil.isEmpty(symbol)) {
            throw new IllegalArgumentException("symbol is empty.");
        }
        if (StringUtil.isNotEmpty(expression)) {
            // TODO inspect expression
        }
        if (go == null) {
            throw new IllegalArgumentException(String.format("%s's go is null. Symbol is %s", name, symbol));
        }
    }

    @Override
    public List<Node> getNextNodes(HashMap<String, Node> nodeHashMap, boolean isTask) {
        Node node = nodeHashMap.get(go);
        if (node == null) {
            throw new IllegalArgumentException(String.format("go %s is not exist. Symbol is %s", go, symbol));
        }
        return Collections.singletonList(node);
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"type\":\"" + type + '"' +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"defaultSelected\":" + defaultSelected +
                ", \"expression\":\"" + (expression == null ? "" : expression) + '"' +
                ", \"description\":\"" + (description == null ? "" : description) + '"' +
                ", \"go\":" + go +
                '}';
    }
}
