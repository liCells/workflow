package org.lz.workflow.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.lz.workflow.basic.Node;
import org.lz.workflow.basic.NodeType;
import org.lz.workflow.basic.cache.NodeMap;
import org.lz.workflow.domain.FlowDesign;
import org.lz.workflow.domain.map.EndNode;
import org.lz.workflow.domain.map.Line;
import org.lz.workflow.domain.map.StartNode;
import org.lz.workflow.domain.map.UserTaskNode;
import org.lz.workflow.service.FlowDesignService;

import java.util.HashMap;
import java.util.List;

/**
 * @author lz
 */
public class LoadNodeMapHelper {
    private final FlowDesignService flowDesignService;
    private static final Gson gson = new Gson();

    public LoadNodeMapHelper(FlowDesignService flowDesignService) {
        this.flowDesignService = flowDesignService;
    }

    public void load() {
        List<FlowDesign> flowDesigns = flowDesignService.selectAll();
        if (flowDesigns == null || flowDesigns.isEmpty()) {
            return;
        }
        for (FlowDesign flowDesign : flowDesigns) {
            JsonArray jsonElements = gson.fromJson(flowDesign.getSimpleJson(), JsonArray.class);
            HashMap<String, Node> nodeMap = build(jsonElements);
            NodeMap.put(flowDesign.getSymbol(), flowDesign.getVersion(), nodeMap);
        }
    }

    public static HashMap<String, Node> build(JsonArray json) {
        HashMap<String, Node> nodeMap = new HashMap<>();
        json.forEach(node -> {
            Node obj = parse(node.getAsJsonObject());
            if (obj instanceof StartNode) {
                nodeMap.put(NodeType.START.getName(), obj);
            }
            nodeMap.put(obj.getSymbol(), obj);
        });
        return nodeMap;
    }

    public static Node parse(JsonObject json) {
        String type = json.get("type").getAsString();
        switch (NodeType.getByValue(type)) {
            case START: {
                return gson.fromJson(json, StartNode.class);
            }
            case END: {
                return gson.fromJson(json, EndNode.class);
            }
            case USUAL_TASK: {
                return gson.fromJson(json, UserTaskNode.class);
            }
            case SINGLE_ENDED: {
                return gson.fromJson(json, Line.class);
            }
            case PARALLEL_TASK:
                // TODO
            case SERIAL_TASK:
                // TODO
            case DOUBLE_ENDED:
                // TODO
            case AUTO_TASK:
                // TODO
            case TIMER_TASK:
                // TODO
            default:
                throw new IllegalArgumentException("The wrong json.");
        }
    }

}
