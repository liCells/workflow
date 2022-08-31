package org.lz.workflow.basic.cache;

import org.lz.workflow.basic.Node;

import java.util.HashMap;

/**
 * @author lz
 */
public class NodeMap {
    private final static HashMap<String, Node> NODE_MAP = new HashMap<>();

    public static Node get(String symbol, int version) {
        return NODE_MAP.get(symbol + "::" + version);
    }

    public static void put(String symbol, int version, Node node) {
        NODE_MAP.put(symbol + "::" + version, node);
    }
}
