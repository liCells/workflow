package org.lz.workflow.basic.cache;

import org.lz.workflow.basic.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    public static List<Node> getNextNodes(String symbol, int version, String nodeSymbol) {
        return getNextNodes(symbol, version, nodeSymbol, false);
    }

    public static List<Node> getNextNodes(String symbol, int version, String nodeSymbol, boolean isTask) {
        HashMap<String, Node> nodeHashMap = get(symbol, version);
        return getNextNodes(nodeHashMap, nodeSymbol, isTask);
    }

    public static List<Node> getNextNodes(HashMap<String, Node> nodeHashMap, String nodeSymbol) {
        return getNextNodes(nodeHashMap, nodeSymbol, false);
    }

    public static List<Node> getNextNodes(HashMap<String, Node> nodeHashMap, String nodeSymbol, boolean isTask) {
        Node node = nodeHashMap.get(nodeSymbol);

        Objects.requireNonNull(node, "Node not found.");

        if (isTask) {
            return node.getNextNodes(nodeHashMap, true);
        }
        return node.getNextNodes(nodeHashMap);
    }
}
