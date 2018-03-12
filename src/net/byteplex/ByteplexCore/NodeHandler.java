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

}
