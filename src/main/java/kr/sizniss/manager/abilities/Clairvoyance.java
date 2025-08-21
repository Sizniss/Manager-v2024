package kr.sizniss.manager.abilities;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Manager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
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

import java.util.List;

import static kr.sizniss.manager.Manager.variables;

public class Clairvoyance implements Listener {

    public int cooldown = 25;
    public int duration = 5;
    public double radius = 30.0;


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 1.5f, 1.25f);

        int radiusStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_효과_범위");
        double plusRadius = radiusStat * 10;
        List<Entity> nearByEntities = player.getNearbyEntities(radius + plusRadius, radius + plusRadius, radius + plusRadius);
        nearByEntities.add(player);
        double closestDistanceSquared = Double.MAX_VALUE;
        Player closestPlayer = null;
        for (Entity target : nearByEntities) {
            // 타겟의 엔티티 타입이 플레이어가 아닐 경우
            if (target.getType() != EntityType.PLAYER) {
                continue;
            }
            Player targetPlayer = (Player) target;

            // 타겟이 인간이 아닐 경우
            if (!SelonZombieSurvival.manager.getHumanList().contains(targetPlayer)) {
                continue;
            }

            // 타겟이 영웅일 경우
            if (SelonZombieSurvival.manager.getHeroList().contains(targetPlayer)) {
                continue;
            }

            /*
            // 타겟에게 투명화 효과가 적용되어 있을 경우
            if (targetPlayer.getPotionEffect(PotionEffectType.INVISIBILITY) != null
                    && targetPlayer.getPotionEffect(PotionEffectType.INVISIBILITY).getDuration() > 0) {
                continue;
            }
             */

            // 가장 가까이 있는 플레이어 구하기
            double distanceSquared = player.getLocation().distanceSquared(targetPlayer.getLocation());
            if (distanceSquared < closestDistanceSquared) {
                closestDistanceSquared = distanceSquared;
                closestPlayer = targetPlayer;
            }
        }

        // 가장 가까이에 있는 플레이어가 존재할 경우
        if (closestPlayer != null) {
            // 타겟에게 투명화 효과가 적용되어 있을 경우
            if (closestPlayer.getPotionEffect(PotionEffectType.INVISIBILITY) != null
                    && closestPlayer.getPotionEffect(PotionEffectType.INVISIBILITY).getDuration() > 0) {

                Manager.methods.potionApply(closestPlayer, PotionEffectType.GLOWING, 0.25, 1);

            }
            // 타겟에게 투명화 효과가 적용되어 있지 않을 경우
            else {

                Manager.methods.potionApply(closestPlayer, PotionEffectType.GLOWING, duration, 1);

            }
        }
        /*
        // 가장 가까이에 있는 플레이어가 존재할 경우
        if (closestPlayer != null) {
            Manager.methods.potionApply(closestPlayer, PotionEffectType.GLOWING, duration, 1);
        }
         */

        // 재사용 대기 시간 진행
        int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
        int minusCooldown = cooldownStat * 2;
        Manager.methods.cooldown(player, item, slot, cooldown - minusCooldown);
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

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName().contains("투시")) {
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

            if (indexItem.getItemMeta().getDisplayName().contains("투시")) {
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

        if (item.getItemMeta().getDisplayName().contains("투시")) {
            useAbility(player, item, slot);
        }
    }

}
