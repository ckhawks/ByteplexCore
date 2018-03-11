package me.ckhks.StellaricCore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Gang {

    // serialize
    private String name;
    private String tag;
    private UUID leader;
    private List<GangMember> members = new ArrayList<GangMember>();

    private int money;
    private Map<Material, Integer> resources = new HashMap<Material, Integer>();

    private ItemStack decor1;
    private ItemStack decor2;

    // don't need to serialize
    //// nothing yet
    static List<Gang> gangs = new ArrayList<>();

    public Gang(String name, String tag, UUID leader){
        this.name = name;
        this.tag = tag;
        this.leader = leader;

        this.money = 1000;
        GangMember m = new GangMember(leader, 255);
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

    public List<GangMember> getMembers() {
        return members;
    }

    public void addMember(GangMember member){
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

    public static void addGuild(Gang gang){
        gangs.add(gang);
    }

    public static void disbandGuild(Gang gang){
        gangs.remove(gang);
    }

    public static Gang getGuild(UUID uuid){
        for(Gang g : gangs){
            for(GangMember m : g.getMembers()){
                if(m.getUniqueUI() == uuid){
                    return g;
                }
            }
        }
        return null;
    }


}
