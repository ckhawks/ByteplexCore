package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.util.MySQLHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DbCommand extends BaseCommand {

    public DbCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Your UUID: " + player.getUniqueId().toString());
            MySQLHandler.printAllGangs();
        }
        return true;
    }
}
