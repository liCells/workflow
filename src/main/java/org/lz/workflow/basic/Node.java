package org.lz.workflow.basic;

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

    Node getNextTaskNode();
}
