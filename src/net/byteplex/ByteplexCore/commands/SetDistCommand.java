package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDistCommand extends BaseCommand {
    int distanceAllowed = 7;

    public SetDistCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
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
        }
        return true;
    }
}
