package net.byteplex.ByteplexCore.util;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.DataException;
import net.byteplex.ByteplexCore.Gang;
import net.byteplex.ByteplexCore.hooks.WorldEditHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("deprecation")
public class Schematic {

    public static void save(Plugin PLUGIN, Player player, String schematicName) {
        try {
            File schematic = new File(PLUGIN.getDataFolder(), "/schematics/" + schematicName);
            File dir = new File(PLUGIN.getDataFolder(), "/schematics/");
            if (!dir.exists())
                dir.mkdirs();

            WorldEditPlugin wep = WorldEditHandler.getWorldEdit();
            WorldEdit we = wep.getWorldEdit();

            LocalPlayer localPlayer = wep.wrapPlayer(player);
            LocalSession localSession = we.getSession(localPlayer);
            ClipboardHolder selection = localSession.getClipboard();
            EditSession editSession = localSession.createEditSession(localPlayer);

            Vector min = selection.getClipboard().getMinimumPoint();
            Vector max = selection.getClipboard().getMaximumPoint();

            editSession.enableQueue();
            CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
            clipboard.copy(editSession);
            SchematicFormat.MCEDIT.save(clipboard, schematic);
            editSession.flushQueue();

            player.sendMessage("Saved schematic!");
        } catch (IOException | DataException ex) {
            ex.printStackTrace();
        } catch (EmptyClipboardException ex) {
            ex.printStackTrace();
        }
    }


    public static void paste(Plugin PLUGIN, String schematicName, Location pasteLoc) {
        try {
            File dir = new File(PLUGIN.getDataFolder(), "/schematics/" + schematicName);

            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), 999999999);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), true);
            editSession.flushQueue();
        } catch (DataException | IOException ex) {
            ex.printStackTrace();
        } catch (MaxChangedBlocksException ex) {
            ex.printStackTrace();
        }
    }

    public static void fixSelection(Player player) {
        try {
            Gang g = Gang.getGuild(player.getUniqueId());
            ItemStack mat1 = g.getDecor1();
            ItemStack mat2 = g.getDecor2();

            WorldEditPlugin wep = WorldEditHandler.getWorldEdit();
            WorldEdit we = wep.getWorldEdit();

            Selection sel = wep.getSelection(player);

            LocalPlayer localPlayer = wep.wrapPlayer(player);
            LocalSession localSession = we.getSession(localPlayer);
            EditSession editSession = localSession.createEditSession(localPlayer);


            Set<BaseBlock> a = new HashSet<BaseBlock>();


            a.add(new BaseBlock(Material.LAPIS_ORE.getId()));
            editSession.replaceBlocks(sel.getRegionSelector().getRegion(), a, new BaseBlock(mat1.getType().getId()));

            Set<BaseBlock> b = new HashSet<BaseBlock>();
            b.add(new BaseBlock(Material.PUMPKIN.getId()));
            editSession.replaceBlocks(sel.getRegionSelector().getRegion(), b, new BaseBlock(mat2.getType().getId()));
        } catch (MaxChangedBlocksException | IncompleteRegionException ex) {
            ex.printStackTrace();
        }
    }

}
