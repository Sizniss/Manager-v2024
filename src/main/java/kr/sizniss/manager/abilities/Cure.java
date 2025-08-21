package kr.sizniss.manager.abilities;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.Attr;

import java.util.List;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.variables;

public class Cure implements Listener {

    public int cooldown = 25;
    public int duration = 7;
    public int level = 6;
    public double radius = 10.0;

    public double percent = 10;


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.5f, 1.0f);

        int durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
        int plusDuration = durationStat;

        List<Entity> nearByEntities = player.getNearbyEntities(radius, radius, radius);
        nearByEntities.add(player);
        for (Entity target : nearByEntities) {
            if (target.getType() != EntityType.PLAYER) { // 타겟의 엔티티 타입이 플레이어가 아닐 경우
                continue;
            }
            Player targetPlayer = (Player) target;

            if (!SelonZombieSurvival.manager.getZombieList().contains(targetPlayer)) { // 타겟이 좀비가 아닐 경우
                continue;
            }

            for (Player zombie : SelonZombieSurvival.manager.getZombieList()) {
                zombie.spawnParticle(Particle.HEART, new Location(targetPlayer.getWorld(), targetPlayer.getLocation().getX(), targetPlayer.getLocation().getY() + 2.5, targetPlayer.getLocation().getZ()), 10);
            }

            double maxHealth = targetPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double currentHealth = targetPlayer.getHealth();
            if (!targetPlayer.isDead()) {
                targetPlayer.setHealth(Math.min(maxHealth, currentHealth + (maxHealth * (percent / 100))));
            }
            methods.potionApply(targetPlayer, PotionEffectType.REGENERATION, duration + plusDuration, level);
        }

        // 재사용 대기 시간 진행
        int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
        int minusCooldown = cooldownStat * 2;
        methods.cooldown(player, item, slot, cooldown - minusCooldown);
    }


    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 함수 반환
        }

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return; // 함수 반환
        }

        Player player = event.getPlayer();
        ItemStack item = null;
        int slot = player.getInventory().getHeldItemSlot();

        if (event.hasItem()) {
            item = event.getItem();
        } else if (player.getItemInHand() != null) {
            item = player.getItemInHand();
        }

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 표시 이름이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName().contains("치유")) {
            useAbility(player, item, slot);
        }
    }


    @EventHandler
    private void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = null;
        int slot;

        for (int i = 0; i < 9; i++) {
            Inventory inventory = player.getInventory();
            ItemStack indexItem = inventory.getItem(i);

            if (indexItem == null || indexItem.getItemMeta().getDisplayName() == null) {
                continue;
            }

            if (indexItem.getItemMeta().getDisplayName().contains("치유")) {
                item = indexItem;
                break;
            }
        }

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        slot = player.getInventory().first(item);

        if (item.getItemMeta().getDisplayName().contains("치유")) {
            useAbility(player, item, slot);
        }
    }

}
