package kr.sizniss.manager.items;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Manager;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Remedy implements Listener {

    double damage = 400;

    // 백신 사용
    @EventHandler
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.equals("치료제")) {

            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItem(player.getInventory().first(Material.BROWN_MUSHROOM));
            int slot = player.getInventory().first(Material.BROWN_MUSHROOM);

            // 아이템 소모
            Manager.methods.consume(player, item, slot, 1);
        }
    }


    // 백신 접종
    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.equals("치료제")) {

            if (event.getVictim().getType() != EntityType.PLAYER) { // 피해자가 플레이어가 아닌 경우
                return; // 함수 반환
            }

            Player attacker = event.getPlayer();
            Player victim = (Player) event.getVictim();

            if (SelonZombieSurvival.manager.getHumanList().contains(attacker)) { // 공격자가 인간일 경우
                if (SelonZombieSurvival.manager.getZombieList().contains(victim)) { // 피해자가 좀비일 경우
                    if (SelonZombieSurvival.manager.getHostList().contains(victim)
                            || SelonZombieSurvival.manager.getCarrierList().contains(victim)) { // 피해자가 숙주 혹은 보균자일 경우
                        event.setCancelled(false);
                        event.setDamage(damage);

                        return; // 함수 반환
                    }

                    event.setDamage(0);
                    SelonZombieSurvival.manager.cured(victim, attacker);
                }
            } else { // 공격자가 인간이 아닐 경우
                event.setDamage(0);
                SelonZombieSurvival.manager.cured(victim);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) { // 데미지 사유가 엔티티 공격이 아닐 경우
            return;
        }

        if (event.getDamager().getType() != EntityType.PLAYER) { // 공격자가 플레이어가 아닐 경우
            return;
        }

        Player attacker = (Player) event.getDamager();

        int slot = attacker.getInventory().getHeldItemSlot();
        ItemStack item = attacker.getInventory().getItem(slot);

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName().contains("치료제")) { // 아이템 이름이 '치료제'일 경우

            if (event.getEntity().getType() != EntityType.PLAYER) { // 피해자가 플레이어가 아닌 경우
                return; // 함수 반환
            }
            Player victim = (Player) event.getEntity();

            event.setDamage(0);
            // 공격자가 인간일 경우
            if (SelonZombieSurvival.manager.getHumanList().contains(attacker)) {
                // 피해자가 좀비일 경우
                if (SelonZombieSurvival.manager.getZombieList().contains(victim)) {
                    // 피해자가 숙주 혹은 보균자일 경우
                    if (SelonZombieSurvival.manager.getHostList().contains(victim)
                            || SelonZombieSurvival.manager.getCarrierList().contains(victim)) {
                        // event.setDamage(damage);

                        CraftPlayer craftVictim = (CraftPlayer) victim;
                        CraftPlayer craftAttacker = (CraftPlayer) attacker;
                        EntityPlayer victimHandle = craftVictim.getHandle();
                        EntityPlayer attackerHandle = craftAttacker.getHandle();

                        event.setCancelled(true);
                        victimHandle.damageEntity(DamageSource.b(attackerHandle), 1f);
                        victimHandle.damageEntity(DamageSource.b(attackerHandle), (float) (damage - 1));
                    } else {
                        SelonZombieSurvival.manager.cured(victim, attacker); // 감염자 치유
                    }

                    Manager.methods.consume(attacker, item, slot, 1); // 아이템 소모
                }
            }
            // 공격자가 인간이 아닐 경우
            else {
                SelonZombieSurvival.manager.cured(victim); // 감염자 치유

                Manager.methods.consume(attacker, item, slot, 1); // 아이템 소모
            }

        }
    }

}
