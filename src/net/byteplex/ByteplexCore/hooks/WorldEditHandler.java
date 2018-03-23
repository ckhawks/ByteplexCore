package net.byteplex.ByteplexCore.hooks;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class WorldEditHandler {

    private static WorldEditPlugin worldEdit = null;
    private static boolean worldEditSupport = false;

    public static boolean setupWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin))
            return false;

        worldEditSupport = true;
        worldEdit = (WorldEditPlugin) plugin;
        getServer().getConsoleSender().sendMessage("enabled WorldEdit support!");
        return true;
    }

    public static WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    public static boolean isWorldEditSupport() {
        return worldEditSupport;
    }
}
