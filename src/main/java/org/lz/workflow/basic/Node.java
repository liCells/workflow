package org.lz.workflow.basic;

import java.util.HashMap;
import java.util.List;

/**
 * @author lz
 */
public interface Node {
    Integer getId();

    String getName();

    NodeType getType();

    String getSymbol();

    String getDescription();

    void inspect();

    void setVersion(Integer version);

    default List<Node> getNextNodes(HashMap<String, Node> nodeHashMap) {
        return getNextNodes(nodeHashMap, false);
    }

    List<Node> getNextNodes(HashMap<String, Node> nodeHashMap, boolean isTask);
}
