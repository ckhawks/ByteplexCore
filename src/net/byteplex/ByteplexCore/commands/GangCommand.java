package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.ByteplexCore;
import net.byteplex.ByteplexCore.Gang;
import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexCore.util.IconMenu;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GangCommand extends BaseCommand {

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
}
