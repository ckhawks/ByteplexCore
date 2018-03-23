package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.util.MarkerHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanditCommand extends BaseCommand {

    public BanditCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            MarkerHandler.addPlayerMarker(player);
        }
        return true;
    }
}
