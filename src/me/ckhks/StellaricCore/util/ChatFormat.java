package me.ckhks.StellaricCore.util;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ChatFormat {

    private static String serverName = "BytePlex";

    public static String formatExclaim(int chatLevel, String message){
        String prefix = "";

        switch(chatLevel){
            case ChatLevel.ERROR:
                prefix = "" + ChatColor.DARK_RED + " ERROR " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
            case ChatLevel.ERRORCRITICAL:
                prefix = "" + ChatColor.DARK_RED + ChatColor.BOLD + " ERROR " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
            case ChatLevel.NOTICE:
                prefix = "" + ChatColor.WHITE + " NOTICE " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
            case ChatLevel.INFO:
                prefix = "" + ChatColor.GREEN + " INFO " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
            case ChatLevel.SERVER:
                prefix = " " + ChatColor.BLUE + serverName + " " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
            case ChatLevel.GANG:
                prefix = "" + ChatColor.DARK_GREEN + " GANG " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
            case ChatLevel.NODE:
                prefix = "" + ChatColor.GRAY + " NODE " + ChatColor.RESET
                        + ChatColor.GRAY + "»»";
                break;
        }

        return prefix + ChatColor.RESET + " " + message;
    }
}
