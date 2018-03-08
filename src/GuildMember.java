import java.util.UUID;

public class GuildMember {

    // serialize
    private UUID uuid;
    private int role;

    public GuildMember(UUID uuid, int role){
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUniqueUI(){
        return this.uuid;
    }

    public int getRole(){
        return this.role;
    }

    // could change this to use a GuildRole object
    public void setRole(int role){
        this.role = role;
    }
}
