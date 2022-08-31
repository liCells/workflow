package org.lz.workflow.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

import java.util.ArrayList;
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
            JsonObject jsonObject = gson.fromJson(flowDesign.getSimpleJson(), JsonObject.class);
            Node nodeMap = build(jsonObject);
            NodeMap.put(flowDesign.getSymbol(), flowDesign.getVersion(), nodeMap);
        }
    }

    public static Node build(JsonObject json) {
        JsonElement type = json.get("type");
        if (NodeType.START.getName().equals(type.getAsString())) {
            return parse(json);
        } else {
            throw new IllegalArgumentException("The wrong workflow json.");
        }
    }

    public static Node parse(JsonElement json) {
        JsonObject jsonObj = json.getAsJsonObject();
        String type = jsonObj.get("type").getAsString();
        switch (NodeType.getByValue(type)) {
            case START: {
                JsonArray go = jsonObj.get("go").getAsJsonArray();
                jsonObj.remove("go");
                StartNode startNode = gson.fromJson(jsonObj, StartNode.class);
                List<Node> nodes = new ArrayList<>();
                for (JsonElement element : go) {
                    nodes.add(parse(element));
                }
                startNode.setGo(nodes);
                return startNode;
            }
            case END: {
                return gson.fromJson(jsonObj, EndNode.class);
            }
            case USUAL_TASK: {
                JsonArray go = jsonObj.get("go").getAsJsonArray();
                jsonObj.remove("go");
                UserTaskNode userTaskNode = gson.fromJson(jsonObj, UserTaskNode.class);
                List<Node> nodes = new ArrayList<>();
                for (JsonElement element : go) {
                    nodes.add(parse(element));
                }
                userTaskNode.setGo(nodes);
                return userTaskNode;
            }
            case PARALLEL_TASK:
                // TODO
                break;
            case SERIAL_TASK:
                // TODO
                break;
            case SINGLE_ENDED: {
                JsonObject go = jsonObj.get("go").getAsJsonObject();
                jsonObj.remove("go");
                Line line = gson.fromJson(jsonObj, Line.class);
                line.setGo(parse(go));
                return line;
            }
            case DOUBLE_ENDED:
                // TODO
                break;
            case AUTO_TASK:
                // TODO
                break;
            case TIMER_TASK:
                // TODO
                break;
            default:
                throw new IllegalArgumentException("The wrong workflow json.");
        }
        return null;
    }

}
