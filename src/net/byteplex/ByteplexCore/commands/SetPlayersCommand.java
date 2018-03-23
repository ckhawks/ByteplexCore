package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPlayersCommand extends BaseCommand {
    int maxPlayers = 4;


    public SetPlayersCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
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
        }
        return true;
    }
}
