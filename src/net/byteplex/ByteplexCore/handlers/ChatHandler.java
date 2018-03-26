package net.byteplex.ByteplexCore.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.byteplex.ByteplexCore.Gang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatHandler implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Gang g = Gang.getGuild(e.getPlayer().getUniqueId());
        e.setCancelled(true);
        Set<Player> recip = e.getRecipients();
        String m = "";
        if(g != null){
            // TODO make it so that tag shows blue/green for friendly and red for hostile
            m = "[" + g.getTag() + "] " + e.getPlayer().getName() + ": " + e.getMessage();
        } else {
            m = "[" + ChatColor.DARK_GRAY + "loner" + ChatColor.WHITE + "] " + ChatColor.DARK_GRAY + e.getPlayer().getName() + ChatColor.WHITE + ": " + ChatColor.GRAY + e.getMessage();
        }

        for(Player p : recip){
            p.sendMessage(m);
        }

    }
}
