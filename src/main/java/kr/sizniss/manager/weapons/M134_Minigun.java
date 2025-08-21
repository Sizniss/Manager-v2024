package kr.sizniss.manager.weapons;

import dselon.crackshotdouble.customevents.WeaponKnockbackEntityEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class M134_Minigun implements Listener {

    double knockback = -1;


    @EventHandler
    private void WeaponKnockbackEntityEvent(WeaponKnockbackEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("M134_Minigun")) {
            int weaponLevel = 0;
            if (weaponTitle.contains("(+1)")) {
                weaponLevel = 1;
            } else if (weaponTitle.contains("(+2)")) {
                weaponLevel = 2;
            } else {
                return;
            }

            Player attacker = event.getPlayer();
            if (!(event.getVictim() instanceof LivingEntity)) {
                return;
            }
            LivingEntity victim = event.getVictim();
            if (attacker.getLocation().distance(victim.getLocation()) < 7) {
                event.setKnockback(knockback);
            }
        }
    }
}

