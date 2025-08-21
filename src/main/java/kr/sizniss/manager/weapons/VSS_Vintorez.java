package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.methods;

public class VSS_Vintorez implements Listener {

    double duration = 1.75;
    int level = 250;

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("VSS_Vintorez")) {
            int weaponLevel = 0;
            if (weaponTitle.contains("(+1)")) {
                weaponLevel = 1;
            } else if (weaponTitle.contains("(+2)")) {
                weaponLevel = 2;
            } else {
                return;
            }

            if (!(event.getVictim() instanceof LivingEntity)) {
                return;
            }
            LivingEntity victim = (LivingEntity) event.getVictim();

            methods.potionApply(victim, PotionEffectType.JUMP, duration, level);
        }
    }
}
