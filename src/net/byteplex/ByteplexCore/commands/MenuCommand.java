package net.byteplex.ByteplexCore.commands;

import net.byteplex.ByteplexCore.ByteplexCore;
import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuCommand extends BaseCommand implements Listener {
    Inventory myInventory;

    public MenuCommand(String name) {
        super(name);
        Bukkit.getServer().getPluginManager().registerEvents(this, ByteplexCore.getPlugin(ByteplexCore.class));
        initInventory();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(myInventory);
        }
        return true;
    }

    // custom inventory for custom inventory interaction menu prototype
    public void initInventory() {
        myInventory = Bukkit.createInventory(null, 9, "Interaction Menu");
        myInventory.setItem(0, new ItemStack(Material.DIRT, 1));
        myInventory.setItem(8, new ItemStack(Material.COOKIE, 1));
    }

    // event listener for custom inventory interaction menu prototype
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals(myInventory.getName())) {
            if (clicked.getType() == Material.COOKIE) {
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().addItem(new ItemStack(Material.COOKIE, 1));
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, ChatColor.YELLOW + "You get a cookie!"));
            } else if (clicked.getType() == Material.DIRT) {
                event.setCancelled(true);
                player.closeInventory();
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, ChatColor.BLUE + "You found the secret button, " + ChatColor.RED + player.getName() + ChatColor.BLUE + "!"));
            }

        }
    }


}
