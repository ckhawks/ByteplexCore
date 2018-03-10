import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.DynmapCore;
import org.dynmap.bukkit.DynmapPlugin;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import java.util.*;

public class Main extends JavaPlugin implements Listener {
    List<Player> active = new ArrayList<Player>();
    int distanceAllowed = 7;
    int maxPlayers = 4;
    Block playersSign = null;

    boolean worldEditSupport = false;
    WorldEditPlugin worldEdit = null;

    Plugin dynmap;
    DynmapAPI dynapi;
    MarkerAPI markerapi;

    Map<Player, Marker> markers = new HashMap<>();

    @Override
    public void onEnable(){
        // Fired when the server enables the plugin
            getServer().broadcastMessage("Hi there");
        // Register our command "kit" (set an instance of your command class as executor)
        this.getCommand("kit").setExecutor(new CommandKit());
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        playersSign = getServer().getWorlds().get(0).getBlockAt(-9, 72, 145);
        setupWorldEdit();

        dynmap = getServer().getPluginManager().getPlugin("dynmap");
        dynapi = (DynmapAPI) dynmap;
        markerapi = dynapi.getMarkerAPI();

        getServer().getConsoleSender().sendMessage("enabled dynmap support!");

    }

    private void setupWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin))
            return;

        worldEditSupport = true;
        worldEdit = (WorldEditPlugin) plugin;
        getServer().getConsoleSender().sendMessage("enabled WorldEdit support!");
    }

    @Override
    public void onDisable(){
        // fired when the server stops and disables all plugin
        getServer().broadcastMessage("goodbye world");
    }

    public static Inventory myInventory = Bukkit.createInventory(null, 9, "Yo your mom gay");
    static {
        myInventory.setItem(0, new ItemStack(Material.DIRT, 1));
        myInventory.setItem(8, new ItemStack(Material.COOKIE, 1));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if(inventory.getName().equals(myInventory.getName())){
            if(clicked.getType() == Material.COOKIE){
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().addItem(new ItemStack(Material.COOKIE, 1));
                player.sendMessage(ChatColor.YELLOW + "You get a cookie!");
            } else if(clicked.getType() == Material.DIRT){
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                player.sendMessage(ChatColor.BLUE + "You found the secret button, " + ChatColor.RED + player.getName() + ChatColor.BLUE + "!");
            }
        }
    }

    @EventHandler
    public void onPlayerMovePads(PlayerMoveEvent e){
        Location midPad = new Location(e.getPlayer().getWorld(), -8.5, 71, 141.5);
        Location L = e.getTo();

        if(L.distanceSquared(midPad) < distanceAllowed){
            if(!(active.contains(e.getPlayer()))){
                if(active.size() < maxPlayers){
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
            if(active.contains(e.getPlayer())){
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

    @EventHandler
    public void onPlayerMoveMarker(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(markers.containsKey(p)){
            Marker m = markers.get(p);
            Location loc = p.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            m.setLocation(p.getWorld().getName(), x, y, z);
        }
    }

    public void displayActiveContents(){
        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(ChatColor.RED + "     Players Ready");
        Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.DARK_RED + "-------------------");

        if(active.size() == 0){
            Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "empty");
        } else {
            for(int i = 0; i < active.size(); i++){
                Player p = active.get(i);
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + (i + 1) + ": " + p.getName());
            }
        }
        Bukkit.getServer().broadcastMessage("");

        // playersSign.setType(Material.DIRT); works
        if(playersSign.getType() == Material.WALL_SIGN || playersSign.getType() == Material.SIGN_POST){
            Sign s = (Sign) playersSign.getState();

            // clear the lines
            for(int i = 0; i <= 3; i++){
                s.setLine(i, "");
            }

            // set the lines to what we want
            for(int i = 0; i < active.size(); i++){
                Player p = active.get(i);
                s.setLine(i, ChatColor.RED + p.getName());
            }

            // you have to update the blockstate so that it updates changes with the parent Block
            s.update();
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player  p = e.getEntity();
        List<ItemStack> items = e.getDrops();

        boolean cleaned = false;
        for(ItemStack item : items){
            Material m = item.getType();
            if(m.equals(Material.CHAINMAIL_HELMET) || m.equals(Material.CHAINMAIL_CHESTPLATE) || m.equals(Material.CHAINMAIL_LEGGINGS) || m.equals(Material.CHAINMAIL_BOOTS)){
                item.setAmount(0);
                cleaned = true;
            }
        }
        if(cleaned){
            p.getServer().broadcastMessage("Chainmail cleaned!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(label.equalsIgnoreCase("strike")){
                if(args.length == 0){
                    Block b = player.getTargetBlock(null, 5);
                    Location loc = b.getLocation();
                    loc.getWorld().strikeLightning(loc);
                } else if(args.length == 1){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    target.getLocation().getWorld().strikeLightning(target.getLocation());
                } else {
                    player.sendMessage("you didnt use it right you dingle poop");
                }

                // Here we need to give items to our player
                //player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
            } else if(label.equalsIgnoreCase("menu")){
                player.openInventory(myInventory);
            } else if(label.equalsIgnoreCase("pad")){
                displayActiveContents();
            } else if(label.equalsIgnoreCase("setdist")){
                if(args.length == 1){
                    try {
                        distanceAllowed = Integer.parseInt(args[0]);
                        sender.sendMessage("Distance changed to " + distanceAllowed);
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("You didn't specify an integer!");
                        return false;
                    }
                } else {
                    return false;
                }
            } else if(label.equalsIgnoreCase("setplayers")){
                if(args.length == 1){
                    try {
                        maxPlayers = Integer.parseInt(args[0]);
                        sender.sendMessage("Players changed to " + maxPlayers);
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("You didn't specify an integer!");
                        return false;
                    }
                } else {
                    return false;
                }
            } else if(label.equalsIgnoreCase("schema")){
                if(!worldEditSupport){
                    player.sendMessage("WorldEdit not loaded.");
                    return true;
                }
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("load")){
                        Schematic.paste(this, args[1], player.getLocation());
                        player.sendMessage("Pasted " + args[1] + " at " + player.getLocation().toString());
                    } else if(args[0].equalsIgnoreCase("save")) {
                        Schematic.save(this, player, args[1]);
                        player.sendMessage("Saved " + player.getLocation().toString() + " as " + args[1]);
                    } else if(args[0].equalsIgnoreCase("fix")){
                        Schematic.fixSelection(player);
                    } else {
                        return false;
                    }
                } else {
                    sender.sendMessage("Incorrect usage!");
                    return false;
                }
            } else if(label.equalsIgnoreCase("guild")){
                if(args.length >= 1){
                    //  label 0      1   2     3
                    // /guild create tag squad boys
                    if(args[0].equalsIgnoreCase("create")){
                        if(args.length >= 3){

                            String name = args[2];
                            if(args.length > 3){
                                for(int i = 3; i < args.length; i++){
                                    name = name + " " + args[i];
                                }
                            }
                            name = name.trim();
                            Guild g = new Guild(name, args[1], player.getUniqueId());
                            g.setDecor1(Material.BEDROCK);
                            g.setDecor2(Material.QUARTZ);
                            Guild.addGuild(g);
                            player.sendMessage(g.getName() + " has been created.");
                        }
                    } else if(args[0].equalsIgnoreCase("disband")){
                        Guild g = Guild.getGuild(player.getUniqueId());
                        if (g != null) {
                            player.sendMessage(g.getName() + " has been disbanded.");
                            Guild.disbandGuild(g);
                        }
                    } else if(args[0].equalsIgnoreCase("cobble")){ // doesn't work
                        Guild g = Guild.getGuild(player.getUniqueId());
                        try {
                            if (g != null) {
                                player.sendMessage(g.getName() + " has " + g.checkResource(Material.COBBLESTONE) + " cobblestone.");
                            }
                        } catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    } else if(args[0].equalsIgnoreCase("check")){
                        Guild g = Guild.getGuild(player.getUniqueId());
                        if (g != null) {
                            player.sendMessage(g.getTag() + " " + player.getName() + " of " + g.getName());
                            player.sendMessage(g.getName() + " has $" + g.getMoney() + " in the bank.");

                            // display resources
                            HashMap<Material, Integer> res = (HashMap) g.getResources();
                            Iterator it = res.entrySet().iterator();
                            while(it.hasNext()){
                                Map.Entry pair = (Map.Entry) it.next();
                                player.sendMessage(pair.getKey().toString() + ": " + pair.getValue());
                                it.remove();
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("set")){
                        if(args[1].equalsIgnoreCase("decor1")){
                            materialSelector(player,1);
                        } else if(args[1].equalsIgnoreCase("decor2")){
                            materialSelector(player,2);
                        }
                    }
                }
            } else if(label.equalsIgnoreCase("bandit")){
                MarkerSet set = markerapi.getMarkerSet("Points of Interest");
                MarkerIcon ico = markerapi.getMarkerIcon("skull");
                Location loc = player.getLocation();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();
                Marker m = set.createMarker(null, "Bandit", true, loc.getWorld().getName(), x, y, z, ico, true);
                sender.sendMessage("Added marker id:'" + m.getMarkerID() + "' (" + m.getLabel() + ") to set '" + set.getMarkerSetID() + "'");
                markers.put(player, m);
            }
        }
        // If the player (or console) uses our command correct, we can return true
        return true;
    }

    public void materialSelector(Player player, int which){
        IconMenu menu = new IconMenu("Choose material " + which, 27, new IconMenu.OptionClickEventHandler(){
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                ItemStack is = null;

                switch(event.getName()){
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

                if(which == 1){
                    Guild.getGuild(player.getUniqueId()).setDecor1(is);
                } else if(which == 2){
                    Guild.getGuild(player.getUniqueId()).setDecor2(is);
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

