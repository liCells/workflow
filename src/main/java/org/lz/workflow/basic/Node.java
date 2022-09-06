package org.lz.workflow.basic;

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

    List<Node> getNextTaskNode();

    List<Node> getNextNode();
}
