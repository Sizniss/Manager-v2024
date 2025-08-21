package kr.sizniss.manager.abilities;

import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.variables;

public class Flight implements Listener {

    public int cooldown = 25;
    public int duration = 5;
    public int level = 1;


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우

            // 비행 중일 경우
            PotionEffectType potionEffectType = PotionEffectType.LEVITATION;
            if (player.getPotionEffect(potionEffectType) != null
                    && player.getPotionEffect(potionEffectType).getAmplifier() == level - 1
                    && player.getPotionEffect(potionEffectType).getDuration() > 0) {
                player.removePotionEffect(potionEffectType); // 비행 종료
            }

            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 1.5f, 0.75f);

        ItemStack skill = variables.getLeap().clone();
        player.getInventory().setItem(3, skill); // 도약 재사용 대기 시간 초기화

        int durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
        int plusDuration = durationStat;
        Manager.methods.potionApply(player, PotionEffectType.LEVITATION, duration + plusDuration, level);

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

        if (item.getItemMeta().getDisplayName().contains("비행")) {
            event.setCancelled(true);
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

            if (indexItem.getItemMeta().getDisplayName().contains("비행")) {
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

        if (item.getItemMeta().getDisplayName().contains("비행")) {
            useAbility(player, item, slot);
        }
    }

}
