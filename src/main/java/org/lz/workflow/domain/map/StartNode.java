package org.lz.workflow.domain.map;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.basic.TaskNode;
import org.lz.workflow.utils.StringUtil;

import java.util.HashMap;
import java.util.LinkedList;
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
    private List<String> go;
    private Integer version;

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

    public List<String> getGo() {
        return go;
    }

    public void setGo(List<String> go) {
        this.go = go;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
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
            throw new IllegalArgumentException("`go` is empty.");
        }
    }

    public StartNode(Integer id, Integer flowDesignId, String name, String symbol, String description, List<String> go) {
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

    public List<Node> getNextTaskNodes(HashMap<String, Node> nodeHashMap) {
        if (go == null) throw new IllegalArgumentException("Next node is null.");
        List<Node> nextNodes = new LinkedList<>();
        for (String nextNodeKey : go) {
            Node node = nodeHashMap.get(nextNodeKey);
            if (node instanceof TaskNode) {
                nextNodes.add(node);
            } else {
                nextNodes.addAll(node.getNextNodes(nodeHashMap, true));
            }
        }
        if (nextNodes.isEmpty()) {
            throw new IllegalArgumentException("Not found the next task node.");
        }
        return nextNodes;
    }

    @Override
    public List<Node> getNextNodes(HashMap<String, Node> nodeHashMap, boolean isTask) {
        if (isTask) {
            return getNextTaskNodes(nodeHashMap);
        }
        List<Node> nextNodes = new LinkedList<>();
        for (String nextNodeKey : go) {
            nextNodes.add(nodeHashMap.get(nextNodeKey));
        }
        if (nextNodes.isEmpty()) {
            throw new IllegalArgumentException("Not found the next node.");
        }
        return nextNodes;
    }
}
