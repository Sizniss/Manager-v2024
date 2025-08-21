package kr.sizniss.manager.weapons;

import dselon.crackshotdouble.customevents.WeaponKnockbackEntityEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Chainsaw implements Listener {

    double knockback = 1;


    @EventHandler
    private void WeaponKnockbackEntityEvent(WeaponKnockbackEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("전기톱")) {
            int weaponLevel = 0;
            if (weaponTitle.contains("(+1)")) {
                weaponLevel = 1;
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

