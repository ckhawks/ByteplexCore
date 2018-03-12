package net.byteplex.ByteplexCore.hooks;


import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class WorldGuardHandler {

    private static WorldGuardPlugin worldGuard = null;
    private static boolean worldGuardSupport = false;

    public static boolean setupWorldGuard(){
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin))
            return false;

        worldGuardSupport = true;
        worldGuard = (WorldGuardPlugin) plugin;
        getServer().getConsoleSender().sendMessage("enabled WorldEdit support!");
        return true;
    }

    public static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    public static boolean isWorldGuardSupport() {
        return worldGuardSupport;
    }
}
