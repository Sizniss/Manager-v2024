package kr.sizniss.manager.abilities;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import static kr.sizniss.manager.Manager.*;

public class Undying implements Listener {

    public int cooldown = 25;
    public int amount = 1;

    public double percent = 10;


    @EventHandler(priority = EventPriority.HIGHEST)
    private void EntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) { // 엔티티 타입이 플레이어가 아닐 경우
            return; // 함수 반환
        }
        Player player = (Player) event.getEntity();

        if (!player.getInventory().contains(Material.TOTEM)) { // 플레이어가 불사의 토템을 갖고 있지 않을 경우
            return; // 함수 반환
        }
        
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL
                || event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        int slot = player.getInventory().first(Material.TOTEM);
        ItemStack item = player.getInventory().getItem(slot);

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (!item.getItemMeta().getDisplayName().contains("불사")) { // '불사' 이름이 포함되어 있지 않은 경우
            return; // 함수 반환
        }

        double currentHealth = player.getHealth();
        double currentDamage = event.getFinalDamage();
        if (currentHealth <= currentDamage) { // 현재 체력보다 데미지가 더 클 경우
            event.setDamage(0);
            if (!player.isDead()) {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (percent / 100));
            }

            // 불사의 토템 이펙트 효과
            World world = player.getWorld();
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
            world.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.5f, 1.0f);

            // 재사용 대기 시간 진행
            int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
            int minusCooldown = cooldownStat * 2;
            int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
            int plusAmount = amountStat;
            methods.cooldown(player, item, slot, cooldown - minusCooldown, amount + plusAmount);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) { // 엔티티 타입이 플레이어가 아닐 경우
            return; // 함수 반환
        }
        Player player = (Player) event.getEntity();

        if (!player.getInventory().contains(Material.TOTEM)) { // 플레이어가 불사의 토템을 갖고 있지 않을 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        int slot = player.getInventory().first(Material.TOTEM);
        ItemStack item = player.getInventory().getItem(slot);

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (!item.getItemMeta().getDisplayName().contains("불사")) { // '불사' 이름이 포함되어 있지 않은 경우
            return; // 함수 반환
        }

        double currentHealth = player.getHealth();
        double currentDamage = event.getFinalDamage();
        if (currentHealth <= currentDamage) { // 현재 체력보다 데미지가 더 클 경우
            event.setDamage(0);
            if (!player.isDead()) {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (percent / 100));
            }

            // 불사의 토템 이펙트 효과
            World world = player.getWorld();
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
            world.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.5f, 1.0f);

            // 재사용 대기 시간 진행
            int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
            int minusCooldown = cooldownStat * 2;
            int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
            int plusAmount = amountStat;
            methods.cooldown(player, item, slot, cooldown - minusCooldown, amount + plusAmount);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.getVictim().getType() != EntityType.PLAYER) { // 엔티티 타입이 플레이어가 아닐 경우
            return; // 함수 반환
        }
        Player player = (Player) event.getVictim();

        if (!player.getInventory().contains(Material.TOTEM)) { // 플레이어가 불사의 토템을 갖고 있지 않을 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        int slot = player.getInventory().first(Material.TOTEM);
        ItemStack item = player.getInventory().getItem(slot);

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (!item.getItemMeta().getDisplayName().contains("불사")) { // '불사' 이름이 포함되어 있지 않은 경우
            return; // 함수 반환
        }

        double currentHealth = player.getHealth();
        double currentDamage = event.getDamage();
        if (currentHealth <= currentDamage) { // 현재 체력보다 데미지가 더 클 경우
            event.setDamage(0);
            if (!player.isDead()) {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (percent / 100));
            }

            // 불사의 토템 이펙트 효과
            World world = player.getWorld();
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
            world.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.5f, 1.0f);

            // 재사용 대기 시간 진행
            int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
            int minusCooldown = cooldownStat * 2;
            int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
            int plusAmount = amountStat;
            methods.cooldown(player, item, slot, cooldown - minusCooldown, amount + plusAmount);
        }
    }


    @EventHandler
    private void PlayerDropItemEvent(PlayerDropItemEvent event) {
        if (event.getItemDrop() == null) {
            return;
        }

        if (event.getItemDrop().getItemStack() == null) {
            return;
        }
        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getItemMeta() == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();

        if (meta.getDisplayName() == null) {
            return;
        }

        if (meta.getDisplayName().contains("불사")) {
            Player player = event.getPlayer();
            PlayerInventory inventory = (PlayerInventory) player.getInventory();

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (int i = 0; i < 9; i++) {
                    if (i == inventory.getHeldItemSlot()) {
                        continue;
                    }

                    if (inventory.getItem(i) == null) {
                        continue;
                    }
                    ItemStack slotItem = inventory.getItem(i);

                    if (slotItem.getItemMeta() == null) {
                        continue;
                    }
                    ItemMeta slotMeta = slotItem.getItemMeta();

                    if (slotMeta.getDisplayName() == null) {
                        continue;
                    }

                    if (slotMeta.getDisplayName().contains("불사")) {
                        inventory.setItem(i, new ItemStack(Material.AIR));
                    }
                }
            }, 0L);
        }
    }

}
