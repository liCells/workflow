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
public class UserTaskNode implements TaskNode {
    private Integer id;
    private String name;
    private NodeType type;
    private String symbol;
    private String description;
    private String executor;
    private List<String> go;
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

    public UserTaskNode(Integer id, String name, NodeType type, String symbol, String description, String executor, List<String> go) {
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
            throw new IllegalArgumentException(String.format("go is empty. Symbol is %s.", symbol));
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

    public List<Node> getNextTaskNodes(HashMap<String, Node> nodeHashMap) {
        if (go == null) throw new IllegalArgumentException(String.format("Next node is null. Symbol is %s.", symbol));
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
            throw new IllegalArgumentException(String.format("Not found the next task node. Symbol is %s.", symbol));
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
            Node node = nodeHashMap.get(nextNodeKey);
            nextNodes.add(node);
        }
        return nextNodes;
    }
}
