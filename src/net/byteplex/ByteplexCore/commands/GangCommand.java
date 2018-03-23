package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.ByteplexCore;
import net.byteplex.ByteplexCore.ByteplexPlayer;
import net.byteplex.ByteplexCore.Gang;
import net.byteplex.ByteplexCore.GangMember;
import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexCore.util.IconMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class GangCommand extends BaseCommand implements Listener {

    public GangCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                //  label 0      1   2     3
                // /guild create tag squad boys
                if (args[0].equalsIgnoreCase("create")) {
                    if(ByteplexPlayer.getPlayer(player.getUniqueId()) == null){
                        if (args.length >= 3) {

                            String name = args[2];
                            if (args.length > 3) {
                                for (int i = 3; i < args.length; i++) {
                                    name = name + " " + args[i];
                                }
                            }
                            name = name.trim();

                            if(Gang.tagExists(args[1])){
                                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "There is already a guild with the tag " + args[1]));
                            } else if(Gang.nameExists(name)) {
                                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "There is already a guild with the name " + name));
                            } else {
                                Gang g = new Gang(name, args[1], player.getUniqueId());
                                g.setDecor1(Material.BEDROCK);
                                g.setDecor2(Material.QUARTZ);
                                Gang.addGuild(g);
                                ByteplexPlayer.addPlayer(new ByteplexPlayer(g, player));
                                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has been created."));
                            }
                        }
                    } else {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are already in " + Gang.getGuild(player.getUniqueId()).getName() + "!"));
                    }
                } else if (args[0].equalsIgnoreCase("disband")) {
                    Gang g = Gang.getGuild(player.getUniqueId());
                    if (g != null) {
                        for(GangMember gm : g.getMembers()){
                            ByteplexPlayer.removePlayer(Bukkit.getPlayer(gm.getUniqueUI()));
                        }
                        Gang.disbandGuild(g);
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has been disbanded."));
                    } else {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are not in a gang!"));
                    }
                } else if (args[0].equalsIgnoreCase("cobble")) { // doesn't work
                    Gang g = Gang.getGuild(player.getUniqueId());
                    try {
                        if (g != null) {
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, g.getName() + " has " + g.checkResource(Material.COBBLESTONE) + " cobblestone."));
                        } else {
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are not in a gang!"));
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
                            //it.remove();
                        }
                    } else {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are not in a gang!"));
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    Gang g = Gang.getGuild(player.getUniqueId());
                    if(g != null) {
                        if (args[1].equalsIgnoreCase("decor1")) {
                            materialSelector(player, 1);
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Please select Decor Material 1."));
                        } else if (args[1].equalsIgnoreCase("decor2")) {
                            materialSelector(player, 2);
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "Please select Decor Material 2."));
                        }
                    } else {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are not in a gang!"));
                    }
                } else if (args[0].equalsIgnoreCase("invite")){
                    Gang g = Gang.getGuild(player.getUniqueId());
                    if(g != null){
                        if(args.length > 1){
                            Player playee = Bukkit.getPlayer(args[1]);
                            if(playee != null){
                                g.addInvitedPlayer(playee);
                            }
                        }
                    } else {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are not in a gang!"));
                    }
                } else if (args[0].equalsIgnoreCase("join")){
                    Gang g = Gang.getGuild(player.getUniqueId());
                    if(g != null) {
                        player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You are already in a gang!"));
                    } else {
                        if(args.length > 1){
                            Gang g2 = Gang.getGuildFromTag(args[1]);
                            if(g2 != null){
                                if(g2.getInvitedPlayers().contains(player)){
                                    g2.addMember(new GangMember(player.getUniqueId(), 20));
                                    ByteplexPlayer.addPlayer(new ByteplexPlayer(g2, player));
                                    g2.removeInvitedPlayer(player);
                                }
                            } else {
                                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "Gang not found from specified tag."));
                            }

                        } else {
                            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERROR, "You must provide a gang tag!"));
                        }

                    }
                }
            }
        }
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
        }, ByteplexCore.getPlugin(ByteplexCore.class))
                .setOption(0, new ItemStack(Material.BEDROCK, 1), "Bedrock", "this is a description")
                .setOption(1, new ItemStack(Material.LAPIS_BLOCK, 1), "Lapis Lazuli Block", "description 2")
                .setOption(2, new ItemStack(Material.QUARTZ_BLOCK, 1), "Quartz Block", "description 2")
                .setOption(3, new ItemStack(Material.PRISMARINE, 1), "Prismarine Block", "description 2")
                .setOption(4, new ItemStack(Material.CONCRETE, 1), "White Concrete Block", "description 2");
        menu.open(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();
        Gang gang = Gang.getGuild(uuid);

        if(gang == null){
            player.sendMessage("You are not in a gang yet! We recommend joining one to get started.");
        } else {
            ByteplexPlayer.addPlayer(new ByteplexPlayer(gang, player));
            player.sendMessage("You have been loaded to " + gang.getName());
        }
    }
}
