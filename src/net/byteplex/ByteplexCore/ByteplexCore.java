package net.byteplex.ByteplexCore;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.byteplex.ByteplexCore.hooks.DynmapHandler;
import net.byteplex.ByteplexCore.hooks.WorldEditHandler;
import net.byteplex.ByteplexCore.hooks.WorldGuardHandler;
import net.byteplex.ByteplexCore.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;
import java.util.*;

public class ByteplexCore extends JavaPlugin implements Listener {
    HashMap<String, List<Player>> queues = new HashMap<>();
    int distanceAllowed = 7;
    int maxPlayers = 4;
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

        // Register our command "kit" (set an instance of your command class as executor)
        this.getCommand("kit").setExecutor(new CommandKit());

        getServer().getPluginManager().registerEvents(this, this);

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

    // custom inventory for custom inventory interaction menu prototype
    public static Inventory myInventory = Bukkit.createInventory(null, 9, "Interaction Menu");

    static {
        myInventory.setItem(0, new ItemStack(Material.DIRT, 1));
        myInventory.setItem(8, new ItemStack(Material.COOKIE, 1));
    }

    // event listener for custom inventory interaction menu prototype
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals(myInventory.getName())) {
            if (clicked.getType() == Material.COOKIE) {
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().addItem(new ItemStack(Material.COOKIE, 1));
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, ChatColor.YELLOW + "You get a cookie!"));
            } else if (clicked.getType() == Material.DIRT) {
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, ChatColor.BLUE + "You found the secret button, " + ChatColor.RED + player.getName() + ChatColor.BLUE + "!"));
            }

        }
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("strike")) {
                if (args.length == 0) {
                    Block b = player.getTargetBlock(null, 5);
                    Location loc = b.getLocation();
                    loc.getWorld().strikeLightning(loc);
                } else if (args.length == 1) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    target.getLocation().getWorld().strikeLightning(target.getLocation());
                } else {
                    player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "Incorrect usage - /strike <target>"));
                }

                // Here we need to give items to our player
                //player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
            } else if (label.equalsIgnoreCase("menu")) {
                player.openInventory(myInventory);
                //} else if (label.equalsIgnoreCase("pad")) {
                //    displayActiveContents();
            } else if (label.equalsIgnoreCase("setdist")) {
                if (args.length == 1) {
                    try {
                        distanceAllowed = Integer.parseInt(args[0]);
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Distance changed to " + distanceAllowed));
                        return true;
                    } catch (Exception e) {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You didn't specify an integer!"));
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (label.equalsIgnoreCase("setplayers")) {
                if (args.length == 1) {
                    try {
                        maxPlayers = Integer.parseInt(args[0]);
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Players changed to " + maxPlayers));
                        return true;
                    } catch (Exception e) {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You didn't specify an integer!"));
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (label.equalsIgnoreCase("schema")) {
                if (!WorldEditHandler.isWorldEditSupport()) {
                    player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERRORCRITICAL, "WorldEdit not loaded."));
                    return true;
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("load")) {
                        Schematic.paste(this, args[1], player.getLocation());
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Pasted " + args[1] + " at " + player.getLocation().toString()));
                    } else if (args[0].equalsIgnoreCase("save")) {
                        Schematic.save(this, player, args[1]);
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Saved " + player.getLocation().toString() + " as " + args[1]));
                    } else if (args[0].equalsIgnoreCase("fix")) {
                        Schematic.fixSelection(player);
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Placeholder blocks swapped with decor1/2."));
                    } else {
                        return false;
                    }
                } else {
                    player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "Incorrect usage - /schema <load/save/fix> <label>"));
                    return false;
                }
            } else if (label.equalsIgnoreCase("gang")) {
                if (args.length >= 1) {
                    //  label 0      1   2     3
                    // /guild create tag squad boys
                    if (args[0].equalsIgnoreCase("create")) {
                        if (args.length >= 3) {

                            String name = args[2];
                            if (args.length > 3) {
                                for (int i = 3; i < args.length; i++) {
                                    name = name + " " + args[i];
                                }
                            }
                            name = name.trim();
                            Gang g = new Gang(name, args[1], player.getUniqueId());
                            g.setDecor1(Material.BEDROCK);
                            g.setDecor2(Material.QUARTZ);
                            Gang.addGuild(g);
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has been created."));
                        }
                    } else if (args[0].equalsIgnoreCase("disband")) {
                        Gang g = Gang.getGuild(player.getUniqueId());
                        if (g != null) {
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has been disbanded."));
                            Gang.disbandGuild(g);
                        }
                    } else if (args[0].equalsIgnoreCase("cobble")) { // doesn't work
                        Gang g = Gang.getGuild(player.getUniqueId());
                        try {
                            if (g != null) {
                                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has " + g.checkResource(Material.COBBLESTONE) + " cobblestone."));
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("check")) {
                        Gang g = Gang.getGuild(player.getUniqueId());
                        if (g != null) {
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getTag() + " " + player.getName() + " of " + g.getName()));
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has $" + g.getMoney() + " in the bank."));

                            // display resources
                            HashMap<Material, Integer> res = (HashMap) g.getResources();
                            Iterator it = res.entrySet().iterator();
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Current Resources"));
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, pair.getKey().toString() + ": " + pair.getValue()));
                                it.remove();
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("decor1")) {
                            materialSelector(player, 1);
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Please select Decor Material 1."));
                        } else if (args[1].equalsIgnoreCase("decor2")) {
                            materialSelector(player, 2);
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Please select Decor Material 2."));
                        }
                    }
                }
            } else if (label.equalsIgnoreCase("bandit")) {
                MarkerHandler.addPlayerMarker(player);
            } else if (label.equalsIgnoreCase("fog")) {
                int amplifier = 1; // default amp value
                if (args.length >= 1) {
                    try {
                        amplifier = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You did not specify a number!"));
                    }
                }
                // PotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, Color color)
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, amplifier, false, false, null));
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Added prototype fog to " + player.getName()));
            } else if (label.equalsIgnoreCase("db")) {
                MySQLHandler.test();
            }
        } else {
            sender.sendMessage("Player commands only!");
        }
        // If the player (or console) uses our command correct, we can return true
        return true;
    }

    public void materialSelector(Player player, int which) {
        IconMenu menu = new IconMenu("Choose material " + which, 27, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                ItemStack is = null;

                switch (event.getName()) {
                    case "Bedrock":
                        is = new ItemStack(Material.BEDROCK);
                        break;
                    case "Lapis Lazuli Block":
                        is = new ItemStack(Material.LAPIS_BLOCK);
                        break;
                    case "Quartz Block":
                        is = new ItemStack(Material.QUARTZ_BLOCK);
                        break;
                    case "Prismarine Block":
                        is = new ItemStack(Material.PRISMARINE);
                        break;
                    case "White Concrete Block":
                        is = new ItemStack(Material.CONCRETE);
                        break;
                }

                if (which == 1) {
                    Gang.getGuild(player.getUniqueId()).setDecor1(is);
                    player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Decor Material 1 set."));
                } else if (which == 2) {
                    Gang.getGuild(player.getUniqueId()).setDecor2(is);
                    player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Decor Material 2 set."));
                }
                event.setWillClose(true);
            }
        }, this)
                .setOption(0, new ItemStack(Material.BEDROCK, 1), "Bedrock", "this is a description")
                .setOption(1, new ItemStack(Material.LAPIS_BLOCK, 1), "Lapis Lazuli Block", "description 2")
                .setOption(2, new ItemStack(Material.QUARTZ_BLOCK, 1), "Quartz Block", "description 2")
                .setOption(3, new ItemStack(Material.PRISMARINE, 1), "Prismarine Block", "description 2")
                .setOption(4, new ItemStack(Material.CONCRETE, 1), "White Concrete Block", "description 2");
        menu.open(player);
    }
}

