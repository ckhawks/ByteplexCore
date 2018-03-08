import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Bukkit.broadcastMessage("Welcome to the server " + event.getPlayer().getName() + "!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        event.getEntity().sendMessage("your mom gay lol");
    }
}
