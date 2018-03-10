package me.ckhks.StellaricCore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Guild {

    // serialize
    private String name;
    private String tag;
    private UUID leader;
    private List<GuildMember> members = new ArrayList<GuildMember>();

    private int money;
    private Map<Material, Integer> resources = new HashMap<Material, Integer>();

    private ItemStack decor1;
    private ItemStack decor2;

    // don't need to serialize
    //// nothing yet
    static List<Guild> guilds = new ArrayList<>();

    public Guild(String name, String tag, UUID leader){
        this.name = name;
        this.tag = tag;
        this.leader = leader;

        this.money = 1000;
        GuildMember m = new GuildMember(leader, 255);
        this.addMember(m);
        this.resources.put(Material.COBBLESTONE, 100);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
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

    public List<GuildMember> getMembers() {
        return members;
    }

    public void addMember(GuildMember member){
        this.members.add(member);
        Bukkit.getPlayer(member.getUniqueUI()).sendMessage("You are now a member of " + this.getName());
    }

    public int getMoney() {
        return money;
    }

    // can be used for adding or subtracting
    public void changeMoney(int money) {
        this.money = this.money + money;
    }

    public Map<Material, Integer> getResources(){
        return resources;
    }

    public String exportResources(){
        return resources.toString();
    }

    public int checkResource(Material material){
        return resources.get(material);
    }

    public void changeResource(Material material, int i){
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

    public static void addGuild(Guild guild){
        guilds.add(guild);
    }

    public static void disbandGuild(Guild guild){
        guilds.remove(guild);
    }

    public static Guild getGuild(UUID uuid){
        for(Guild g : guilds){
            for(GuildMember m : g.getMembers()){
                if(m.getUniqueUI() == uuid){
                    return g;
                }
            }
        }
        return null;
    }


}
