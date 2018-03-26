package net.byteplex.ByteplexCore;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.byteplex.ByteplexCore.handlers.ChatHandler;
import net.byteplex.ByteplexCore.hooks.DynmapHandler;
import net.byteplex.ByteplexCore.hooks.WorldEditHandler;
import net.byteplex.ByteplexCore.hooks.WorldGuardHandler;
import net.byteplex.ByteplexCore.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ByteplexCore extends JavaPlugin implements Listener {
    HashMap<String, List<Player>> queues = new HashMap<>();

    Block playersSign = null;

    // Fired when the server enables the plugin
    @Override
    public void onEnable() {
        //Jordan was here
        getLogger().info("Hi there!");

        // plugin dependencies setup
        // setup worldedit connection
        if (WorldEditHandler.setupWorldEdit()) {
            getLogger().info("Successfully Enabled WorldEdit support!");
        } else {
            getLogger().info("Failed to enable WorldEdit support!");
        }

        // setup worldguard connection
        if (WorldGuardHandler.setupWorldGuard()) {
            getLogger().info("Successfully Enabled WorldGuard support!");
        } else {
            getLogger().info("Failed to enable WorldGuard support!");
        }

        // setup dynmap connection
        if (DynmapHandler.setupDynmap()) {
            getLogger().info("Successfully Enabled Dynmap support!");
        } else {
            getLogger().info("Failed to enable Dynmap support!");
        }

        // setup mysql
        try {
            MySQLHandler.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // after mysql connected we can load guilds
        Gang.loadGuilds();

        // setup commands
        CommandHandler ch = new CommandHandler();
        ch.registerCommands();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChatHandler(), this);

        // was used for a prototype on old world
        playersSign = getServer().getWorlds().get(0).getBlockAt(-9, 72, 145);

        NodeHandler.addNode(new Node(null, "Refinery", "Some description", NodeType.REFINERY, "factory",
                new Location(getServer().getWorlds().get(0), -290.0, 67, 81), new Location(getServer().getWorlds().get(0), -281, 68, 71), new Location(getServer().getWorlds().get(0), -287, 69, 85)));
    }

    // fired when the server stops and disables all plugin
    @Override
    public void onDisable() {

        // close mysql connection
        try {
            MySQLHandler.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getLogger().info("Goodbye world!");
    }

    // used for displaying a title/subtitle to the player when they join.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.sendTitle(ChatColor.AQUA + "BytePlex: " + ChatColor.GOLD + "Corporation Conquest", ChatColor.WHITE + "Enjoy your stay! *IN ACTIVE DEVELOPMENT*", 10, 120, 20);
    }

    // Used for updating marker position. Probably not efficient, as it checks every time someone moves.
    //   Dynmap updates slowly anyways, so priority is set to low.
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMoveMarker(PlayerMoveEvent e) {
        MarkerHandler.checkPlayerMarker(e.getPlayer());
    }

    /*
    // used for checking if player is currently standing on the pad. needs to be adopted to Node battle system
    @EventHandler
    public void onPlayerMovePads(PlayerMoveEvent e) {
        Location midPad = new Location(e.getPlayer().getWorld(), -8.5, 71, 141.5);
        Location L = e.getTo();

        if (L.distanceSquared(midPad) < distanceAllowed) {
            if (!(active.contains(e.getPlayer()))) {
                if (active.size() < maxPlayers) {

                    // particles
                    e.getPlayer().spawnParticle(Particle.HEART, L, 10);
                    // e.getPlayer().playEffect(L, Effect.BOW_FIRE, Effect.Type.SOUND);
                    e.getPlayer().getWorld().playSound(L, Sound.BLOCK_NOTE_XYLOPHONE, 1F, 1.2F);

                    // game logic
                    active.add(e.getPlayer());
                    displayActiveContents();
                } else {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "This team is full!");
                }
            }

        } else {
            if (active.contains(e.getPlayer())) {
                // particles
                e.getPlayer().spawnParticle(Particle.CLOUD, L, 10);
                // e.getPlayer().playEffect(L, Effect.EXTINGUISH, Effect.Type.SOUND);
                e.getPlayer().getWorld().playSound(L, Sound.BLOCK_FIRE_EXTINGUISH, 0.5F, 1F);

                // game logic
                active.remove(e.getPlayer());
                displayActiveContents();
            }
        }
    }
    */

    @EventHandler
    public void onRegionEnter(RegionEnterEvent event) {
        ProtectedRegion region = event.getRegion();

        Player player = event.getPlayer();
        Location L = player.getLocation();

        // change this so that it is dynamic for nodes
        if (region.getId().equalsIgnoreCase("node1_attack")) {
            Node node = NodeHandler.getNode("node1");

            Gang gang = Gang.getGuild(player.getUniqueId());
            if (node.getHolder() != gang || gang == null) {
                // player is not in defending guild

                // data
                node.addQueue(true, event.getPlayer());
                // effects
                ParticleKit.drawSquare(Particle.VILLAGER_ANGRY, player);
                player.getWorld().playSound(L, Sound.BLOCK_NOTE_XYLOPHONE, 1F, 1.2F);

                // chat
                node.checkQueuing(this);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.NODE, "You have been added to the " + ChatColor.RED + "attackers" + ChatColor.WHITE + " queue."));
                //displayBattleQueueInfo(node);
            } else {
                // player is in defending guild
                event.setCancelled(true);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.NODE, "You can't attack your own node!"));
            }
        } else if (region.getId().equalsIgnoreCase("node1_defend")) {
            Node node = NodeHandler.getNode("node1");

            Gang gang = Gang.getGuild(player.getUniqueId());
            if (node.getHolder() == gang || gang == null) {
                // player is in defending guild

                // data
                node.addQueue(false, event.getPlayer());
                // effects
                ParticleKit.drawSquare(Particle.HEART, player);
                player.getWorld().playSound(L, Sound.BLOCK_NOTE_XYLOPHONE, 1F, 1.2F);

                // chat
                node.checkQueuing(this);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.NODE, "You have been added to the " + ChatColor.BLUE + "defenders" + ChatColor.WHITE + " queue."));
                //displayBattleQueueInfo(node);
            } else {
                // player is not in defending guild
                event.setCancelled(true);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.NODE, "You are not a member of the defenders: " + gang.getName() + "."));
            }

        }
    }

    @EventHandler
    void onRegionLeave(RegionLeaveEvent event) {
        ProtectedRegion region = event.getRegion();
        Player player = event.getPlayer();
        Location L = player.getLocation();

        // change this so that it is dynamic for nodes
        if (region.getId().equalsIgnoreCase("node1_attack")) {
            // data
            Node node = NodeHandler.getNode("node1");
            node.removeQueue(true, event.getPlayer());

            // effects
            ParticleKit.drawSquare(Particle.CLOUD, player);
            player.getWorld().playSound(L, Sound.BLOCK_FIRE_EXTINGUISH, 0.5F, 1F);

            // chat
            node.checkQueuing(this);
            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.NODE, "You have left the " + ChatColor.RED + "attackers" + ChatColor.WHITE + " queue."));
            //displayBattleQueueInfo(node);
        } else if (region.getId().equalsIgnoreCase("node1_defend")) {
            Node node = NodeHandler.getNode("node1");
            node.removeQueue(false, event.getPlayer());

            // effects
            ParticleKit.drawSquare(Particle.CLOUD, player);
            player.getWorld().playSound(L, Sound.BLOCK_FIRE_EXTINGUISH, 0.5F, 1F);

            // chat
            node.checkQueuing(this);
            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.NODE, "You have left the " + ChatColor.BLUE + "defenders" + ChatColor.WHITE + " queue."));
            //displayBattleQueueInfo(node);
        }
    }

    // retired, made the badly offset table.
    // scrapped due to lack of a proper way to calculate text display width
    public void displayBattleQueueInfo(Node node) {
        int maxLines = 3;

        // defining chat strings for defenders
        String[] defenderLines;
        List<Player> defenders = node.getQueuedDefenders();

        // if there are no defenders
        if (defenders.size() == 0) {
            defenderLines = new String[1];

            defenderLines[0] = ChatColor.GRAY + " empty ";
            // if the number of defenders is less than or equal to maxLines
        } else if (defenders.size() < maxLines) {
            defenderLines = new String[defenders.size()];

            for (int i = 0; i < defenders.size(); i++) {
                Player player = defenders.get(i);
                defenderLines[i] = ChatColor.BLUE + " - " + player.getName() + " ";
            }
            // if the number of defenders is greater than maxLines
        } else {
            defenderLines = new String[maxLines];

            for (int i = 0; i < maxLines; i++) {
                Player player = defenders.get(i);
                defenderLines[i] = ChatColor.BLUE + " - " + player.getName() + " ";
            }
            defenderLines[3] = ChatColor.BLUE + " +" + (defenders.size() - maxLines - 1) + " more";
        }

        // defining the chat Strings for attackers
        String[] attackerLines;
        List<Player> attackers = node.getQueuedAttackers();

        // if there are no attackers
        if (attackers.size() == 0) {
            attackerLines = new String[1];

            attackerLines[0] = ChatColor.GRAY + " empty ";
            // if the number of attackers is less than or equal to maxLines
        } else if (attackers.size() < maxLines) {
            attackerLines = new String[attackers.size()];

            for (int i = 0; i < attackers.size(); i++) {
                Player player = attackers.get(i);
                attackerLines[i] = ChatColor.RED + " - " + player.getName() + " ";
            }
            // if the number of attackers is greater than maxLines
        } else {
            attackerLines = new String[maxLines];

            for (int i = 0; i < maxLines; i++) {
                Player player = attackers.get(i);
                attackerLines[i] = ChatColor.RED + " - " + player.getName();
            }
            attackerLines[3] = ChatColor.RED + " +" + (attackers.size() - maxLines - 1) + " more";
        }

        // drawing the actual table
        int totalLength = 0;
        if (attackerLines.length > defenderLines.length) {
            totalLength = attackerLines.length;
        } else if (defenderLines.length > attackerLines.length) {
            totalLength = defenderLines.length;
        } else if (defenderLines.length == attackerLines.length) {
            totalLength = attackerLines.length;
        }

        //
        this.getServer().broadcastMessage(ChatColor.BLUE + " Defenders " + ChatColor.DARK_GRAY + " vs " + ChatColor.RED + " Attackers ");
        this.getServer().broadcastMessage(ChatColor.DARK_GRAY + "------------------------");
        for (int i = 0; i < totalLength; i++) {
            this.getServer().broadcastMessage(defenderLines[i] + ChatColor.DARK_GRAY + "|" + ChatColor.RESET + attackerLines[i]);
        }
    }

    /*
    // used for pad system to display who is currently on the pad. needs to be adopted to Node battle system
    public void displayActiveContents() {
        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(ChatColor.RED + "     Players Ready");
        Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.DARK_RED + "-------------------");

        if (active.size() == 0) {
            Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "empty");
        } else {
            for (int i = 0; i < active.size(); i++) {
                Player p = active.get(i);
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + (i + 1) + ": " + p.getName());
            }
        }
        Bukkit.getServer().broadcastMessage("");

        // playersSign.setType(Material.DIRT); works
        if (playersSign.getType() == Material.WALL_SIGN || playersSign.getType() == Material.SIGN_POST) {
            Sign s = (Sign) playersSign.getState();

            // clear the lines
            for (int i = 0; i <= 3; i++) {
                s.setLine(i, "");
            }

            // set the lines to what we want
            for (int i = 0; i < active.size(); i++) {
                Player p = active.get(i);
                s.setLine(i, ChatColor.RED + p.getName());
            }

            // you have to update the blockstate so that it updates changes with the parent Block
            s.update();
        }
    }
    */
    // Chainmail cleaner
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        List<ItemStack> items = e.getDrops();

        boolean cleaned = false;
        for (ItemStack item : items) {
            Material m = item.getType();
            if (m.equals(Material.CHAINMAIL_HELMET) || m.equals(Material.CHAINMAIL_CHESTPLATE) || m.equals(Material.CHAINMAIL_LEGGINGS) || m.equals(Material.CHAINMAIL_BOOTS)) {
                item.setAmount(0);
                cleaned = true;
            }
        }
        if (cleaned) {
            p.getServer().broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Chainmail cleaned!"));
        }
    }

}

