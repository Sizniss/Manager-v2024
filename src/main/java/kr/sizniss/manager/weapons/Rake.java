package kr.sizniss.manager.weapons;

import kr.sizniss.manager.Manager;
import kr.sizniss.manager.items.Elixir;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.plugin;

public class Rake  implements Listener {

    double damage = 10;
    double reductionRate = 0;
    HashMap<LivingEntity, Boolean> noDamage = new HashMap<LivingEntity, Boolean>();


    @EventHandler
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) { // 데미지 사유가 엔티티 공격이 아닐 경우
            return; // 함수 반환
        }

        if (event.getDamager().getType() != EntityType.PLAYER) { // 공격자가 플레이어가 아닐 경우
            return; // 함수 반환
        }

        Player attacker = (Player) event.getDamager();
        
        if (event.isCancelled()) { // 취소된 이벤트일 경우
            return; // 함수 반환
        }

        int slot = attacker.getInventory().getHeldItemSlot();
        ItemStack item = attacker.getInventory().getItem(slot);

        boolean isRake = true;
        if (item == null
                || item.getType() != Material.NETHER_STALK
                || item.getItemMeta() == null
                || item.getItemMeta().getDisplayName() == null
                || !item.getItemMeta().getDisplayName().contains("갈퀴")) {
            isRake = false;
        }

        double currentHealth = ((LivingEntity) event.getEntity()).getHealth();
        if (isRake) { // 손에 있는 아이템이 갈퀴일 경우

            double currentDamage = damage;

            // 피해자의 공격 피해 무 데미지 설정
            if (noDamage.containsKey(((LivingEntity) event.getEntity()))) {
                event.setCancelled(true);
                return;
            }
            else {
                noDamage.put(((LivingEntity) event.getEntity()), true);

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    noDamage.remove(((LivingEntity) event.getEntity()));
                }, 10);
            }


            reductionRate = 0; // 피해 감소율

            // 공격자에게 투명화 효과가 있을 경우
            if (attacker.getPotionEffect(PotionEffectType.INVISIBILITY) != null
                    && attacker.getPotionEffect(PotionEffectType.INVISIBILITY).getDuration() > 0) {
                reductionRate += 0.2;
            }

            // 피해자에게 저항 효과가 있을 경우
            if (((LivingEntity) event.getEntity()).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                reductionRate += (((LivingEntity) event.getEntity()).getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier() + 1) * 0.2;
            }

            if (event.getEntityType() == EntityType.PLAYER) {
                Player victim = (Player) event.getEntity();

                ItemStack weapon = victim.getItemInHand();
                if (weapon != null
                        && weapon.getType() != Material.AIR
                        && weapon.getItemMeta() != null
                        && weapon.getItemMeta().getDisplayName() != null) {

                    if (weapon.getType() == Material.SHIELD) {

                        if (weapon.getItemMeta().getDisplayName().contains("진압 방패")
                                || weapon.getItemMeta().getDisplayName().contains("전기 방패")) { // 진압 방패를 들고 있을 경우
                            if (victim.isBlocking() && !methods.isBackstab(attacker, victim)) { // 막고 있고, 정면 피해를 받을 경우
                                reductionRate += 0.6;
                            }
                        }
                        else if (weapon.getItemMeta().getDisplayName().contains("가시 방패")) { // 가시 방패를 들고 있을 경우
                            if (victim.isBlocking() && !methods.isBackstab(attacker, victim)) { // 막고 있고, 정면 피해를 받을 경우
                                reductionRate += 0.7;
                            }
                        }

                    }

                }
            }

            reductionRate = Math.min(reductionRate, 0.9);


            currentDamage = currentDamage * (1.0 - reductionRate) > 0 ? currentDamage * (1.0 - reductionRate) : 1;
            if (currentHealth > currentDamage) { // 현재 체력이 데미지보다 클 경우

                // 데미지 가하기
                event.setDamage(0);
                if (!event.getEntity().isDead()) {
                    ((LivingEntity) event.getEntity()).setHealth(currentHealth - currentDamage);
                }

                // 포션 효과 부여
                /*
                int slowDuration = 2;
                int slowLevel = 1;
                ((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.SLOW);
                PotionEffect slowPotion = new PotionEffect(PotionEffectType.SLOW, slowDuration * 20, slowLevel - 1);
                ((LivingEntity) event.getEntity()).addPotionEffect(slowPotion);
                 */

            }
            else { // 현재 체력보다 데미지가 클 경우

                if (event.getEntity().getType() == EntityType.PLAYER) { // 피해자가 플레이어일 경우
                    Player victim = (Player) event.getEntity();

                    int victimSlot = -1;
                    ItemStack victimItem = null;
                    if (victim.getInventory().contains(Material.YELLOW_FLOWER)) {
                        victimSlot = victim.getInventory().first(Material.YELLOW_FLOWER);
                        victimItem = victim.getInventory().getItem(victimSlot);
                    }

                    // 영약이 있을 경우
                    if (victimItem != null
                            && victimItem.getItemMeta() != null
                            && victimItem.getItemMeta().getDisplayName() != null
                            && victimItem.getItemMeta().getDisplayName().contains("영약")) {

                        // 영약 사용
                        new Elixir().useAbility(victim, victimItem, victimSlot);

                        // 데미지 가하기
                        event.setDamage(0);
                        if (!victim.isDead()) {
                            victim.setHealth(victim.getHealth() - currentDamage);
                        }

                    }
                    // 영약이 없을 경우
                    else {
                        methods.preventDeathAndInfected(event, victim, attacker); // 감염
                    }

                }
                else { // 피해자가 플레이어가 아닐 경우
                    event.setCancelled(true);
                    if (!event.getEntity().isDead()) {
                        ((LivingEntity) event.getEntity()).setHealth(0);
                    }
                }

            }

            // 흡혈
            double bloodAmount = currentDamage * 2;
            double attackerCurrentHealth = attacker.getHealth();
            double attackerCurrentMaxHealth = attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            if (!attacker.isDead()) {
                attacker.setHealth(Math.min(attackerCurrentHealth + bloodAmount, attackerCurrentMaxHealth));
            }

        }
        else { // 손에 있는 아이템이 갈퀴가 아닐 경우

            double currentDamage = event.getFinalDamage();

            if (currentHealth <= currentDamage) { // 현재 체력이 데미지보다 작거나 같을 경우

                if (event.getEntity().getType() == EntityType.PLAYER) { // 피해자가 플레이어일 경우
                    Player victim = (Player) event.getEntity();

                    int victimSlot = -1;
                    ItemStack victimItem = null;
                    if (victim.getInventory().contains(Material.YELLOW_FLOWER)) {
                        victimSlot = victim.getInventory().first(Material.YELLOW_FLOWER);
                        victimItem = victim.getInventory().getItem(victimSlot);
                    }

                    // 영약이 있을 경우
                    if (victimItem != null
                            && victimItem.getItemMeta() != null
                            && victimItem.getItemMeta().getDisplayName() != null
                            && victimItem.getItemMeta().getDisplayName().contains("영약")) {

                        // 영약 사용
                        new Elixir().useAbility(victim, victimItem, victimSlot);

                        // 데미지 가하기
                        event.setDamage(0);
                        if (!victim.isDead()) {
                            victim.setHealth(victim.getHealth() - currentDamage);
                        }

                    }
                    // 영약이 없을 경우
                    else {
                        methods.preventDeathAndInfected(event, victim, attacker); // 감염
                    }

                }
                else { // 피해자가 플레이어가 아닐 경우
                    event.setCancelled(true);
                    if (!event.getEntity().isDead()) {
                        ((LivingEntity) event.getEntity()).setHealth(0);
                    }
                }

            }
        }
    }

}
