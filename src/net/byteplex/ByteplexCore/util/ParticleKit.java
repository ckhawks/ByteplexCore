package net.byteplex.ByteplexCore.util;


import net.byteplex.ByteplexCore.ByteplexCore;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleKit {

    static Plugin main = ByteplexCore.getPlugin(ByteplexCore.class);

    public static final int SQUARECORNERS = 1;
    public static final int FLOWER = 2;
    public static final int TWISTINGBOX = 3;

    // TODO reimplement particlaric into here, make it easy to pick a particle type to play
    // basically just make this a player particle database thing.
    // separate into particle types: object/gameplay, player effect, player trail, arrow trail
    // enums are fun I think? don't think they will work well for this in case I want need a particle that has special data (such as speed or direction or...
    //   if I want a specific shape but a different actual particle (flame instead of smoke or something))

    public static void drawParticle(Particle particle, Player player, int particleType, Integer count) {

        switch (particleType) {
            case ParticleKit.SQUARECORNERS:
                drawSquare(particle, player);
                break;
            case ParticleKit.FLOWER:
                draw8Flower(particle, player, count);
                break;
            case ParticleKit.TWISTINGBOX:

        }

    }

    public static void drawSquare(Particle particle, Player player) {
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

    // Particle.DRIP_WATER
    public static void draw8Flower(Particle particle, Player player, Integer count) {
        if (count == null) {
            count = 8;
        }

        // TODO implement count
        new BukkitRunnable() {
            Location loc = player.getLocation();

            double t1 = 0;
            double t2 = (2 * Math.PI) / 3;
            double t3 = (4 * Math.PI) / 3;
            double r = 2;

            public void run() {
                {
                    t1 = t1 + Math.PI / 64;
                    double x = r * Math.cos(4 * t1) * Math.cos(t1);
                    double y = 1.5;
                    double z = r * Math.cos(4 * t1) * Math.sin(t1);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    //ParticleEffect.DRIP_WATER.display(loc, 0, 0, 0, 0, 1);
                    loc.subtract(x, y, z);
                    if (t1 > Math.PI * 2) {
                        this.cancel();
                    }
                }
                {
                    t2 = t2 + Math.PI / 64;
                    double x = r * Math.cos(4 * t2) * Math.cos(t2);
                    double y = 1.5;
                    double z = r * Math.cos(4 * t2) * Math.sin(t2);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    //ParticleEffect.DRIP_WATER.display(loc, 0, 0, 0, 0, 1);
                    loc.subtract(x, y, z);
                }
                {
                    t3 = t3 + Math.PI / 64;
                    double x = r * Math.cos(4 * t3) * Math.cos(t3);
                    double y = 1.5;
                    double z = r * Math.cos(4 * t3) * Math.sin(t3);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0);
                    //ParticleEffect.DRIP_WATER.display(loc, 0, 0, 0, 0, 1);
                    loc.subtract(x, y, z);
                }
            }

        }.runTaskTimer(main, 0, 1);
    }

    // Particle.FLAME
    public static void drawTwistingSquare(Particle particle, Player player) {
        new BukkitRunnable() {
            Location loc = player.getLocation();
            double side = 2;
            double t = 2.5;
            double theta = 0;

            public void run() {
                t = t - .1;
                for (double i = 0; i < 1; i = i + .1) {
                    double xt = (side * i) - 1;
                    double y = t;
                    double zt = side / 2;

                    double x = xt * Math.cos(theta) - zt * Math.sin(theta);
                    double z = xt * Math.sin(theta) + zt * Math.cos(theta);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                for (double i = 0; i < 1; i = i + .1) {
                    double xt = (side * i) - 1;
                    double y = t;
                    double zt = -side / 2;
                    double x = xt * Math.cos(theta) - zt * Math.sin(theta);
                    double z = xt * Math.sin(theta) + zt * Math.cos(theta);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                for (double i = 0; i < 1; i = i + .1) {
                    double xt = side / 2;
                    double y = t;
                    double zt = (side * i) - 1;
                    double x = xt * Math.cos(theta) - zt * Math.sin(theta);
                    double z = xt * Math.sin(theta) + zt * Math.cos(theta);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                for (double i = 0; i < 1; i = i + .1) {
                    double xt = -side / 2;
                    double y = t;
                    double zt = (side * i) - 1;
                    double x = xt * Math.cos(theta) - zt * Math.sin(theta);
                    double z = xt * Math.sin(theta) + zt * Math.cos(theta);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }

                if (t < 0) {
                    this.cancel();
                }
                theta = theta + ((double) (Math.PI / 2) / 25);
            }

        }.runTaskTimer(main, 0, 1);
    }

    // Particle.FLAME
    public static void drawBoxWalls(Particle particle, Player player) {
        new BukkitRunnable() {
            Location loc = player.getLocation();
            double side = 2;
            double t = 2.5;

            public void run() {
                for (double i = 0; i < 1; i = i + .1) {
                    double x = (side * i) - 1;
                    double y = t;
                    double z = side / 2;
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                for (double i = 0; i < 1; i = i + .1) {
                    double x = (side * i) - 1;
                    double y = t;
                    double z = -side / 2;
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                for (double i = 0; i < 1; i = i + .1) {
                    double x = side / 2;
                    double y = t;
                    double z = (side * i) - 1;
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                for (double i = 0; i < 1; i = i + .1) {
                    double x = -side / 2;
                    double y = t;
                    double z = (side * i) - 1;
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }

                if (t < 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(main, 0, 1);
    }
}
