package kr.sizniss.manager.abilities;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Manager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static kr.sizniss.manager.Manager.variables;

public class BioBomb implements Listener {

    public int cooldown = 25;
    public int amount = 1;
    public double knockback = 1.5;


    // 생체 폭탄 사용
    @EventHandler
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("생체_폭탄")) {

            Player player = event.getPlayer();
            int slot = player.getInventory().first(Material.SLIME_BALL);
            ItemStack item = player.getInventory().getItem(slot);

            final String type = variables.getSelectedType().get(player);

            // Methods.potionApply(player, PotionEffectType.SPEED, 5, 1); // 속도 증가 효과 부여

            // 재사용 대기 시간 진행
            int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
            int minusCooldown = cooldownStat * 2;
            int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
            int plusAmount = amountStat;
            Manager.methods.cooldown(player, item, slot, cooldown - minusCooldown, amount + plusAmount);
        }
    }

    // 충격 피격
    @EventHandler
    private void BioBombHitEvent(WeaponDamageEntityEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("생체_폭탄")) {

            if (event.getVictim().getType() != EntityType.PLAYER) { // 피해자가 플레이어가 아닌 경우
                return; // 함수 반환
            }

            Player victim = (Player) event.getVictim();

            if (SelonZombieSurvival.manager.getHumanList().contains(victim)) { // 피해자가 인간일 경우

                event.setCancelled(true);
                Location location = event.getDamager().getLocation();
                location.setY(location.getY() - 1);
                Manager.methods.applyKnockback(location, victim, knockback);

            }

        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("생체_폭탄")) {

            Player attacker = event.getPlayer();

            if (event.getVictim().getType() != EntityType.PLAYER) {
                return;
            }
            Player victim = (Player) event.getVictim();

            if (SelonZombieSurvival.manager.getZombieList().contains(attacker)) {
                event.setCancelled(false);
            }
        }
    }
    
}
