package net.byteplex.ByteplexCore;

import net.byteplex.ByteplexCore.util.MarkerHandler;

import java.util.ArrayList;

public class NodeHandler {

    static private ArrayList<Node> nodes = new ArrayList<>(); // TODO change this to not an array pl0x.

    public static void loadNodes(){
        // TODO load node data from database, populate nodes array
        // requires loading gang data first ? maybe ? they kinda depend on each other

        MarkerHandler.setupMarkers(nodes);
    }

    public static void addNode(Node node){
        nodes.add(node);
    }

    public static Node getNode(String identifier){
        for(Node node : nodes){
            if(node.getIdentifier().equalsIgnoreCase(identifier)){
                return node;
            }
        }
        return null;
    }

    public static int getNextFreeId(){
        return nodes.size() + 1;
    }
}
