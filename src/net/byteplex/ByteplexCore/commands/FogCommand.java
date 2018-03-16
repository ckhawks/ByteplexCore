package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FogCommand extends BaseCommand {

    public FogCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
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
        }
        return true;
    }
}
