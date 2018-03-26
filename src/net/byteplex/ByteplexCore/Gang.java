package net.byteplex.ByteplexCore;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexCore.util.MySQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Gang {

    // serialize
    private String name;
    private String tag;
    private UUID leader;
    private List<GangMember> members = new ArrayList<GangMember>();
    private List<Player> invitedPlayers = new ArrayList<>();

    private int money;
    private Map<Material, Integer> resources = new HashMap<Material, Integer>();

    private ItemStack decor1;
    private ItemStack decor2;

    // don't need to serialize
    //// nothing yet
    static List<Gang> gangs = new ArrayList<>();

    public Gang(String name, String tag, UUID leader) {
        this.name = name;
        this.tag = tag;
        this.leader = leader;

        this.money = 1000;
        GangMember m = new GangMember(leader, 255);
        this.addMember(m);
        this.resources.put(Material.COBBLESTONE, 100);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public List<GangMember> getMembers() {
        return members;
    }

    public void addMember(GangMember member) {
        this.members.add(member);

        // usually will be null when guilds are loaded in on server start
        if (Bukkit.getPlayer(member.getUniqueUI()) != null) {
            Bukkit.getPlayer(member.getUniqueUI()).sendMessage(ChatFormat.formatExclaim(ChatLevel.GANG, "You are now a member of " + this.getName()));
        }
    }

    public int getMoney() {
        return money;
    }

    // can be used for adding or subtracting
    public void changeMoney(int money) {
        this.money = this.money + money;
    }

    public Map<Material, Integer> getResources() {
        return resources;
    }

    public String exportResources() {
        return resources.toString();
    }

    public int checkResource(Material material) {
        return resources.get(material);
    }

    public void changeResource(Material material, int i) {
        resources.replace(material, i + resources.get(material));
    }

    public ItemStack getDecor1() {
        return decor1;
    }

    public void setDecor1(Material decor1) {
        this.decor1 = new ItemStack(decor1);
    }

    public void setDecor1(ItemStack decor1) {
        this.decor1 = decor1;
    }

    public ItemStack getDecor2() {
        return decor2;
    }

    public void setDecor2(Material decor2) {
        this.decor2 = new ItemStack(decor2);
    }

    public void setDecor2(ItemStack decor2) {
        this.decor2 = decor2;
    }

    public static void addGuild(Gang gang) {
        gangs.add(gang);

        // add gang to database
        try {
            MySQLHandler.doPostQuery("INSERT INTO gangs (gangid, gangname, gangtag, gangleader) VALUES ('" + (gangs.size() - 1) + "', '" + gang.getName() + "', '" + gang.getTag() + "', '" + gang.getLeader().toString() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadGuilds() {
        try {
            ResultSet result = MySQLHandler.doGetQuery("SELECT * FROM gangs;");

            if (result != null) {
                int i = 0;
                while (result.next()) {
                    String name, tag, leader;
                    int id;
                    UUID leaderUUID;

                    name = result.getString("gangname");
                    tag = result.getString("gangtag");
                    leader = result.getString("gangleader");
                    id = result.getInt("gangid");
                    leaderUUID = UUID.fromString(leader.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                            "$1-$2-$3-$4-$5")
                    );

                    Bukkit.getLogger().info(ChatFormat.formatExclaim(ChatLevel.SERVER, "Loaded gang [" + tag + "] " + name + " (" + id + "), owned by " + Bukkit.getOfflinePlayer(leaderUUID).getName() + " (" + leader + ")."));
                    Bukkit.getLogger().info(ChatFormat.formatExclaim(ChatLevel.SERVER, "Database ID=" + id + " Arraylist ID=" + i));
                    gangs.add(new Gang(name, tag, leaderUUID));
                    i++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disbandGuild(Gang gang) {
        gangs.remove(gang);
    }

    public static Gang getGuild(UUID uuid) {
        for (Gang g : gangs) {
            for (GangMember m : g.getMembers()) {
                if (m.getUniqueUI() == uuid) {
                    return g;
                }
            }
        }
        return null;
    }

    public static Gang getGuildFromTag(String tag){
        for(Gang g : gangs){
            if(ChatColor.stripColor(g.getTag()).equalsIgnoreCase(tag)){
                return g;
            }
        }
        return null;
    }

    public static boolean nameExists(String name) {
        for (int i = 0; i < gangs.size(); i++){
            if(gangs.get(i).getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public static boolean tagExists(String tag) {
        for (int i = 0; i < gangs.size(); i++){
            if(gangs.get(i).getTag().equalsIgnoreCase(tag)){
                return true;
            }
        }
        return false;
    }

    public List<Player> getInvitedPlayers() {
        return invitedPlayers;
    }

    public boolean addInvitedPlayer(Player player){
        if(invitedPlayers.contains(player)){
            return false;
        } else {
            invitedPlayers.add(player);
            return true;
        }
    }

    public void removeInvitedPlayer(Player player){
        invitedPlayers.remove(player);
    }
}
