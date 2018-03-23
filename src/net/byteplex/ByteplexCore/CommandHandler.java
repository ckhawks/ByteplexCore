package net.byteplex.ByteplexCore;

import net.byteplex.ByteplexCore.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {
    public Map<String, Command> commands = new HashMap<>();
    private ByteplexCore plugin = (ByteplexCore) ByteplexCore.getPlugin(ByteplexCore.class);

    public void registerCommands() {
        addCommand(new BanditCommand("bandit"));
        addCommand(new DbCommand("db"));
        addCommand(new FogCommand("fog"));
        addCommand(new GangCommand("gang"));
        addCommand(new MenuCommand("menu"));
        addCommand(new SchemaCommand("schema"));
        addCommand(new SetDistCommand("setdist"));
        addCommand(new SetPlayersCommand("setplayers"));
        addCommand(new StrikeCommand("strike"));
    }

    public void addCommand(Command command) {
        commands.put(command.getLabel(), command);
        plugin.getCommand(command.getLabel()).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        label = label.toLowerCase();
        return commands.get(label).execute(sender, label, args);
    }
}
