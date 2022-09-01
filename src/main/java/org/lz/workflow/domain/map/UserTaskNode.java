package org.lz.workflow.domain.map;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.basic.TaskNode;
import org.lz.workflow.utils.StringUtil;

import java.util.List;

/**
 * @author lz
 */
public class UserTaskNode implements TaskNode {
    private Integer id;
    private String name;
    private NodeType type;
    private String symbol;
    private String description;
    private String executor;
    private List<Node> go;

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

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public List<Node> getGo() {
        return go;
    }

    public void setGo(List<Node> go) {
        this.go = go;
    }

    public UserTaskNode(Integer id, String name, NodeType type, String symbol, String description, String executor, List<Node> go) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.symbol = symbol;
        this.description = description;
        this.executor = executor;
        this.go = go;
    }

    public UserTaskNode() {
    }

    @Override
    public void inspect() {
        if (StringUtil.isEmpty(name)) {
            this.name = "User Task Node";
        }
        if (StringUtil.isEmpty(symbol)) {
            this.symbol = StringUtil.getRandomString();
        }
        if (go == null || go.isEmpty()) {
            throw new IllegalArgumentException("go is empty.");
        }
        for (Node node : go) {
            if (node.getType() == NodeType.SINGLE_ENDED && node.getType() == NodeType.DOUBLE_ENDED) {
                throw new IllegalArgumentException("User task node's `go` is not single-ended or double-ended.");
            }
            node.inspect();
        }
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"type\":\"" + type + '"' +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"description\":\"" + description + '"' +
                ", \"executor\":\"" + executor + '"' +
                ", \"go\":" + go +
                '}';
    }

    @Override
    public Node getNextTaskNode() {
        return null;
    }
}
