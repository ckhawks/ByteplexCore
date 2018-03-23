package net.byteplex.ByteplexCore;

import org.bukkit.entity.*;
import java.util.*;

public class ByteplexPlayer {

    // players should only be in this arraylist if they are a member of a guild on the server.
    // if they are not a member of a guild, they should not be in this arraylist.
    private static ArrayList<ByteplexPlayer> byteplexPlayers = new ArrayList<>();

    private Gang associatedGang = null;
    private Player associatedPlayer = null;

    public ByteplexPlayer(Gang gang, Player player){
        associatedGang = gang;
        associatedPlayer = player;
    }

    public static Player getPlayer(UUID uuid){
        for(ByteplexPlayer bpp : byteplexPlayers){
            if(bpp.associatedPlayer.getUniqueId() == uuid){
                return bpp.associatedPlayer;
            }
        }
        return null;
    }

    public Gang getAssociatedGang() {
        return associatedGang;
    }

    public void setAssociatedGang(Gang associatedGang) {
        this.associatedGang = associatedGang;
    }

    public static void addPlayer(ByteplexPlayer bpp){
        byteplexPlayers.add(bpp);
    }

    public static void removePlayer(Player player){
        ArrayList<ByteplexPlayer> temp = byteplexPlayers;
        for(ByteplexPlayer bpp : temp){
            if(bpp.associatedPlayer == player){
                byteplexPlayers.remove(bpp);
            }
        }
    }

}
