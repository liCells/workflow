package org.lz.workflow.domain.map;

import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;

import java.util.HashMap;
import java.util.List;

/**
 * @author lz
 */
public class EndNode implements Node {
    private Integer id;
    private String name;
    private String symbol;
    private final NodeType type = NodeType.END;
    private String description;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public void inspect() {

    }

    @Override
    public List<Node> getNextNodes(HashMap<String, Node> nodeHashMap, boolean isTask) {
        throw new UnsupportedOperationException("EndNode has no next node");
    }

    @Override
    public String toString() {
        return '{' +
                "\"id\":" + id +
                ", \"name\":\"" + name + '"' +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"type\":\"" + type + '"' +
                ", \"description\":\"" + (description == null ? "" : description) + '"' +
                '}';
    }
}
