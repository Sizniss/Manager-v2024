package kr.sizniss.manager.items;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Manager;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class Bomb implements Listener {

    double damage = 500;

    @EventHandler
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.equals("폭탄")) {

            Player player = event.getPlayer();
            int slot = player.getInventory().getHeldItemSlot();
            ItemStack item = player.getInventory().getItem(slot);

            // 아이템 소모
            Manager.methods.consume(player, item, slot, 1);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.equals("폭탄")) {

            Player attacker = event.getPlayer();

            if (event.getVictim().getType() != EntityType.PLAYER) {
                return;
            }
            Player victim = (Player) event.getVictim();

            if (SelonZombieSurvival.manager.getHumanList().contains(attacker) && !SelonZombieSurvival.manager.getHumanList().contains(victim)) {
                event.setCancelled(false);

                // 눈덩이 투사체 피해 무효
                if (event.getDamager().getType() == EntityType.SNOWBALL) {
                    event.setDamage(0);
                }
                else {
                    // event.setDamage(damage);

                    CraftPlayer craftVictim = (CraftPlayer) victim;
                    CraftPlayer craftAttacker = (CraftPlayer) attacker;
                    EntityPlayer victimHandle = craftVictim.getHandle();
                    EntityPlayer attackerHandle = craftAttacker.getHandle();

                    victimHandle.damageEntity(DamageSource.b(attackerHandle), 1f);
                    victimHandle.damageEntity(DamageSource.b(attackerHandle), (float) (damage - 1));
                }
            }
        }
    }

}
