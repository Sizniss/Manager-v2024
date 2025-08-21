package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import dselon.selonzombiesurvival.Manager;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.methods;

public class RPG_7 implements Listener {

    double slowDuration = 2;
    int slowLevel = 1;

    double jumpDuration = 1.5;
    int jumpLevel = 250;

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("RPG-7")) {

            if (!(event.getVictim() instanceof LivingEntity)) {
                return;
            }
            LivingEntity victim = (LivingEntity) event.getVictim();

            Manager manager = SelonZombieSurvival.manager;
            if (manager.getHumanList().contains(victim)) { // 피해자가 인간일 경우
                return;
            }

            methods.potionApply(victim, PotionEffectType.SLOW, slowDuration, slowLevel); // 구속 효과 부여

            
            int weaponLevel = 0;
            if (weaponTitle.contains("(+1)")) {
                weaponLevel = 1;
            } else if (weaponTitle.contains("(+2)")) {
                weaponLevel = 2;
            } else {
                return;
            }
            
            methods.potionApply(victim, PotionEffectType.JUMP, jumpDuration, jumpLevel); // 점프 강화 효과 부여
        }
    }
}
