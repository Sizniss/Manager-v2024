package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import kr.sizniss.manager.Methods;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.methods;

public class M4A1 implements Listener {

    double duration = 0.5;
    int level = 10;

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("M4A1")) {
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

            if (!(event.getVictim() instanceof LivingEntity)) {
                return;
            }
            LivingEntity victim = (LivingEntity) event.getVictim();

            methods.potionApply(victim, PotionEffectType.LEVITATION, duration, level);
        }
    }
}
