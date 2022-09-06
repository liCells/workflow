package org.lz.workflow.basic.cache;

import org.lz.workflow.basic.Node;

import java.util.HashMap;

/**
 * @author lz
 */
public class NodeMap {
    private final static HashMap<String, HashMap<String, Node>> NODE_MAP = new HashMap<>();

    public static HashMap<String, Node> get(String symbol, int version) {
        return NODE_MAP.get(symbol + "::" + version);
    }

    public static void put(String symbol, int version, HashMap<String, Node> nodeMap) {
        NODE_MAP.put(symbol + "::" + version, nodeMap);
    }
}
