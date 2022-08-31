package org.lz.workflow.domain.map;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.utils.StringUtil;

import java.util.List;

/**
 * @author lz
 */
public class StartNode implements Node {
    private Integer id;
    private Integer flowDesignId;
    private String name;
    private String symbol;
    private final NodeType type = NodeType.START;
    private String description;

    private List<Node> go;

    public NodeType getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowDesignId() {
        return flowDesignId;
    }

    public void setFlowDesignId(Integer flowDesignId) {
        this.flowDesignId = flowDesignId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Node> getGo() {
        return go;
    }

    public void setGo(List<Node> go) {
        this.go = go;
    }

    @Override
    public void inspect() {
        if (StringUtil.isEmpty(name)) {
            this.name = "Start Node";
        }
        if (StringUtil.isEmpty(symbol)) {
            this.symbol = StringUtil.getRandomString();
        }
        if (go == null || go.isEmpty()) {
            throw new IllegalArgumentException("`go` is empty");
        }
        for (Node node : go) {
            if (node.getType() == NodeType.SINGLE_ENDED && node.getType() == NodeType.DOUBLE_ENDED) {
                throw new IllegalArgumentException("Start node's `go` is not single-ended or double-ended");
            }
            node.inspect();
        }
    }

    public StartNode(Integer id, Integer flowDesignId, String name, String symbol, String description, List<Node> go) {
        this.id = id;
        this.flowDesignId = flowDesignId;
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.go = go;
    }

    public StartNode() {
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\":" + id +
                ", \"flowDesignId\":" + flowDesignId +
                ", \"name\":\"" + name + '"' +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"type\":\"" + type + '"' +
                ", \"description\":\"" + (description == null ? "" : description) + '"' +
                ", \"go\":" + go +
                '}';
    }
}
