package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StrikeCommand extends BaseCommand {

    public StrikeCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
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
        }
        return true;
    }
}
