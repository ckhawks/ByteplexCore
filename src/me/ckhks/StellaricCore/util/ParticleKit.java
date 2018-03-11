package me.ckhks.StellaricCore.util;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleKit {

    static Plugin main;

    public static void setupParticleKit(Plugin plugin){
        main = plugin;
    }

    // TODO reimplement particlaric into here, make it easy to pick a particle type to play
    // basically just make this a player particle database thing.
    // separate into particle types: object/gameplay, player effect, player trail, arrow trail
    // enums are fun I think? don't think they will work well for this in case I want need a particle that has special data (such as speed or direction or...
    //   if I want a specific shape but a different actual particle (flame instead of smoke or something))

    public static void drawSquare(Particle particle, Player player){
        new BukkitRunnable() {
            Location loc = player.getLocation();
            //Vector direction = loc.getDirection();
            double rotX = loc.getYaw();
            double r = 2;
            double t = 0;

            public void run() {
                double depth = -.15;

                // posZ = 0 or 360
                // negX = 90 or -270
                // negZ = 180 or -180
                // posX = 270 or -90
                // front right corner of player
                {
                    double x = -Math.sin(Math.toRadians(rotX + 45));
                    double y = depth;
                    double z = Math.cos(Math.toRadians(rotX + 45));
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    loc.subtract(x, y, z);
                    t = t + 1;
                    if (t > 2) {
                        this.cancel();
                    }
                }

                // front left corner
                {
                    double x = -Math.sin(Math.toRadians(rotX - 45));
                    double y = depth;
                    double z = Math.cos(Math.toRadians(rotX - 45));
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    loc.subtract(x, y, z);
                }

                // back right corner
                {
                    double x = Math.sin(Math.toRadians(rotX - 45));
                    double y = depth;
                    double z = -Math.cos(Math.toRadians(rotX - 45));
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    loc.subtract(x, y, z);
                }

                // back left corner of player
                {
                    double x = Math.sin(Math.toRadians(rotX + 45));
                    double y = depth;
                    double z = -Math.cos(Math.toRadians(rotX + 45));
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
            }
        }.runTaskTimer(main, 0, 1);
    }
}
