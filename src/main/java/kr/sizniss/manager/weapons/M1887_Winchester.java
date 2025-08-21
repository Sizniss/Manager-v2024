package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class M1887_Winchester implements Listener {

    double chance = 1.0;

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("M1887_Winchester")) {
            int weaponLevel = 0;
            if (weaponTitle.contains("(+1)")) {
                weaponLevel = 1;
            } else if (weaponTitle.contains("(+2)")) {
                weaponLevel = 2;
            } else if (weaponTitle.contains("(+3)")) {
                weaponLevel = 3;
            } else {
                return;
            }

            Player player = event.getPlayer();

            double random = Math.random();
            if (random < chance * 0.01) {
                World world = player.getWorld();
                world.strikeLightningEffect(event.getVictim().getLocation());
                event.setDamage(1000);
            }
        }
    }
}
