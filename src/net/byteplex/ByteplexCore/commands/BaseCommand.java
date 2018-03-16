package net.byteplex.ByteplexCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand extends Command {

    protected BaseCommand(String name) {
        super(name);
    }

}
