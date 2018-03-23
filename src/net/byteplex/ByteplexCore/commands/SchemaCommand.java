package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.ByteplexCore;
import net.byteplex.ByteplexCore.hooks.WorldEditHandler;
import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexCore.util.Schematic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SchemaCommand extends BaseCommand {
    private ByteplexCore plugin = (ByteplexCore) ByteplexCore.getPlugin(ByteplexCore.class);

    public SchemaCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!WorldEditHandler.isWorldEditSupport()) {
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.ERRORCRITICAL, "WorldEdit not loaded."));
                return true;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("load")) {
                    Schematic.paste(plugin, args[1], player.getLocation());
                    player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Pasted " + args[1] + " at " + player.getLocation().toString()));
                } else if (args[0].equalsIgnoreCase("save")) {
                    Schematic.save(plugin, player, args[1]);
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
        }
        return true;
    }
}
