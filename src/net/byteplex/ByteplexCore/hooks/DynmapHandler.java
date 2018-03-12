package net.byteplex.ByteplexCore.hooks;

import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.MarkerAPI;

import static org.bukkit.Bukkit.getServer;

public class DynmapHandler {

    private static Plugin dynmap;
    private static DynmapAPI dynapi;
    private static MarkerAPI markerapi;

    public static boolean setupDynmap() {
        dynmap = getServer().getPluginManager().getPlugin("dynmap");
        dynapi = (DynmapAPI) dynmap;
        markerapi = dynapi.getMarkerAPI();
        return true;
    }

    public static DynmapAPI getDynapi() {
        return dynapi;
    }

    public static MarkerAPI getMarkerapi() {
        return markerapi;
    }

    public static Plugin getDynmap() {
        return dynmap;
    }
}
