package kr.sizniss.manager.weapons;

import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import kr.sizniss.manager.maps.HuntingGround;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PathType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
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

public class Thorn_Shield implements Listener {

    double damage = 20;
    double duration = 300;


    @EventHandler(priority = EventPriority.HIGH)
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) { // 데미지 사유가 엔티티 공격이 아닐 경우
            return;
        }

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

        if (weapon.getItemMeta().getDisplayName().contains("가시 방패")) { // 가시 방패를 들고 있을 경우

            if (!(event.getDamager() instanceof LivingEntity)) {
                return;
            }
            LivingEntity attacker = (LivingEntity) event.getDamager();

            if (victim.isBlocking() && !methods.isBackstab(attacker, victim)) { // 막고 있고, 정면 피해를 받을 경우

                // 사냥터 방패 피해 감소 적용
                if (attacker.getType() != EntityType.PLAYER) {
                    double currentHealth = victim.getHealth();
                    victim.setHealth(Math.max(0, currentHealth - event.getDamage() * 0.3));
                }

                boolean isRake = false;
                if (attacker.getType() == EntityType.PLAYER) {
                    int slot = ((Player) attacker).getInventory().getHeldItemSlot();
                    ItemStack item = ((Player) attacker).getInventory().getItem(slot);

                    isRake = true;
                    if (item == null
                            || item.getType() != Material.NETHER_STALK
                            || item.getItemMeta() == null
                            || item.getItemMeta().getDisplayName() == null
                            || !item.getItemMeta().getDisplayName().contains("갈퀴")) {
                        isRake = false;
                    }
                }

                double currentDamage = damage * (isRake ? 1 : 0.1);

                CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
                CraftLivingEntity craftAttacker = (CraftLivingEntity) attacker;
                EntityLiving attackerHandle = craftAttacker.getHandle();

                // CustomDamageEntityEvent 이벤트 호출
                CustomDamageEntityEvent customDamageEntityEvent = new CustomDamageEntityEvent(victim, attacker, currentDamage);
                Bukkit.getPluginManager().callEvent(customDamageEntityEvent);

                if (!customDamageEntityEvent.isCancelled()) {
                    attackerHandle.damageEntity(DamageSource.b(craftVictim.getHandle()), 1f);
                    attackerHandle.damageEntity(DamageSource.b(craftVictim.getHandle()), (float) (customDamageEntityEvent.getDamage() - 1));
                }

                if (weapon.getItemMeta().getDisplayName().contains("플레임")) {
                    attacker.setFireTicks((int) (duration * 20));
                }
            }
        }

    }
}