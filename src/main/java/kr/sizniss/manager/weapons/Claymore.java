package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class Claymore implements Listener {
    int amount = 10;

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("클레이모어")) {
            Player attacker = event.getPlayer();

            double currentHealth = attacker.getHealth();
            double maxHealth = attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            attacker.setHealth(Math.min(currentHealth + amount, maxHealth)); // 체력 회복
        }
    }
}

