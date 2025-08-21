package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponShootEvent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import static kr.sizniss.manager.Manager.plugin;

public class Hwaryongpo implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();

        if (weaponTitle.contains("화룡포")) {
            Player shooter = event.getPlayer();
            Location location = shooter.getEyeLocation();

            location.add(location.getDirection().multiply(2));
            new BukkitRunnable() {
                int tick = 0;

                @Override
                public void run() {
                    tick++;
                    if (tick > 8) {
                        cancel();

                    }
                    shooter.getWorld().spawnParticle(Particle.FLAME, location.add(location.getDirection().multiply(1)), 150, 0.85, 0.7, 0.85, 0);
                }

            }.runTaskTimer(plugin,0,1);
        }
    }
}
