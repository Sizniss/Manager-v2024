package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.methods;

public class Riot_Shield implements Listener {

    double duration = 1;
    int level = 6;


    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("진압_방패")) {
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

            methods.potionApply(victim, PotionEffectType.POISON, duration, level);
            methods.potionApply(victim, PotionEffectType.REGENERATION, duration, level);
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) { // 데미지 사유가 엔티티 공격이 아닐 경우
            return;
        }

        if (!(event.getDamager() instanceof LivingEntity)) {
            return;
        }
        LivingEntity attacker = (LivingEntity) event.getDamager();

        if (event.getEntity().getType() != EntityType.PLAYER) { // 피해자가 플레이어가 아닐 경우
            return;
        }
        Player victim = (Player) event.getEntity();

        ItemStack weapon = victim.getItemInHand();
        if (weapon == null
                || weapon.getType() != Material.SHIELD
                || weapon.getItemMeta() == null
                || weapon.getItemMeta().getDisplayName() == null) {
            return;
        }

        // 진압 방패 혹은 전기 방패를 들고 있을 경우
        if (weapon.getItemMeta().getDisplayName().contains("진압 방패")
                || weapon.getItemMeta().getDisplayName().contains("전기 방패")) {

            if (victim.isBlocking() && !methods.isBackstab(attacker, victim)) { // 막고 있고, 정면 피해를 받을 경우

                // 사냥터 방패 피해 감소 적용
                if (attacker.getType() != EntityType.PLAYER) {
                    double currentHealth = victim.getHealth();
                    victim.setHealth(Math.max(0, currentHealth - event.getDamage() * 0.4));
                }

                CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
                CraftLivingEntity craftAttacker = (CraftLivingEntity) attacker;
                EntityLiving attackerHandle = craftAttacker.getHandle();

                // CustomDamageEntityEvent 이벤트 호출
                CustomDamageEntityEvent customDamageEntityEvent = new CustomDamageEntityEvent(victim, attacker, 1);
                Bukkit.getPluginManager().callEvent(customDamageEntityEvent);

                if (!event.isCancelled()) {
                    attackerHandle.damageEntity(DamageSource.b(craftVictim.getHandle()), 1f);

                    // 전기 방패를 들고 있을 경우
                    if (weapon.getItemMeta().getDisplayName().contains("전기 방패")) {
                        methods.potionApply(attacker, PotionEffectType.POISON, duration, level);
                        methods.potionApply(attacker, PotionEffectType.REGENERATION, duration, level);
                    }
                }
            }
        }

    }
}
