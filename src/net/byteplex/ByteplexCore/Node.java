package net.byteplex.ByteplexCore;


import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Gang holder;
    private String name;
    private String description;
    private NodeType type;

    private String markerIcon;

    private Location location;

    // do not serialize
    private List<Player> defendQueue;
    private List<Player> attackQueue;

    //
    public Node(Gang holder, String name, String description, int nodeType, String markerIcon, Location location){
        this.holder = holder;
        this.name = name;
        this.description = description;
        this.type = type;
        this.markerIcon = markerIcon;
        this.location = location;

        // do not need to be loaded on server start, should be empty at server start
        defendQueue = new ArrayList<>();
        attackQueue = new ArrayList<>();
    }

    public Gang getHolder() {
        return holder;
    }

    public void setHolder(Gang holder) {
        this.holder = holder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getMarkerIcon() {
        return markerIcon;
    }

    public void setMarkerIcon(String markerIcon) {
        this.markerIcon = markerIcon;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(World world, double x, double y, double z) {
        this.location = new Location(world, x, y, z);
    }

    public void addQueue(boolean attacker, Player player){
        if(attacker) {
            if(!(attackQueue.contains(player))){
                attackQueue.add(player);
            } else {
                // error, player already in queue
            }
        } else {
            if(!(defendQueue.contains(player))){
                defendQueue.add(player);
            } else {
                // error, player already in queue
            }
        }
    }

    public void removeQueue(boolean attacker, Player player){
        if(attacker){
            if(attackQueue.contains(player)){
                attackQueue.remove(player);
            } else {
                // error, player not even in queue to begin with
            }
        } else {
            if(defendQueue.contains(player)){
                defendQueue.remove(player);
            } else {
                // error, player not even in queue to begin with
            }
        }
    }
}
