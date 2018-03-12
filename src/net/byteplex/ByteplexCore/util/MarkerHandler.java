package net.byteplex.ByteplexCore.util;

import net.byteplex.ByteplexCore.Node;
import net.byteplex.ByteplexCore.hooks.DynmapHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarkerHandler {

    static Map<Player, Marker> playerMarkers = new HashMap<>();
    static Map<Node, Marker> nodeMarkers = new HashMap<>();

    // TODO add once nodes r in place, argument would be list of nodes
    public static void setupMarkers(ArrayList<Node> nodes){
        MarkerAPI m = DynmapHandler.getMarkerapi();
        MarkerSet poi = m.getMarkerSet("Points of Interest");


        for(Node node : nodes){
            MarkerIcon ico = m.getMarkerIcon(node.getMarkerIcon());
            Location loc = node.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            Marker marker = poi.createMarker(null, node.getName(), true, loc.getWorld().getName(), x, y, z, ico, true);
            nodeMarkers.put(node, marker);
        }
    }

    public static void addNodeMarker(Node node){
        MarkerAPI m = DynmapHandler.getMarkerapi();
        MarkerSet poi = m.getMarkerSet("Points of Interest");

        MarkerIcon ico = m.getMarkerIcon(node.getMarkerIcon());
        Location loc = node.getLocation();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        Marker marker = poi.createMarker(null, node.getName(), true, loc.getWorld().getName(), x, y, z, ico, true);
        nodeMarkers.put(node, marker);
    }

    // this shouldn't get used too often...
    public static void removeNodeMarker(Node node){
        nodeMarkers.remove(node);
    }

    // will need to be expanded when we add different markers from just "Bandit" (perhaps we expand this to like a PlayerMarker class that contains data of like why they have the marker and for how long and shit
    public static void addPlayerMarker(Player player){
        MarkerAPI m = DynmapHandler.getMarkerapi();
        MarkerSet set = m.getMarkerSet("Points of Interest");
        MarkerIcon ico = m.getMarkerIcon("skull");
        Location loc = player.getLocation();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        Marker marker = set.createMarker(null, "Bandit", true, loc.getWorld().getName(), x, y, z, ico, true);
        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Added marker id:'" + marker.getMarkerID() + "' (" + marker.getLabel() + ") to set '" + set.getMarkerSetID() + "'"));
        playerMarkers.put(player, marker);
    }

    // checks if player has a marker attached to them, and if they do, it moves it to correct location (the player's current position)
    public static void checkPlayerMarker(Player player){
        if(playerMarkers.containsKey(player)){
            Marker m = playerMarkers.get(player);
            Location loc = player.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            m.setLocation(player.getWorld().getName(), x, y, z);
        }
    }

    public static void removePlayerMarker(Player player){
        playerMarkers.remove(player);
    }
}