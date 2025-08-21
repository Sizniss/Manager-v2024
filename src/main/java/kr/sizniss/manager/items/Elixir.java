package kr.sizniss.manager.items;

import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.*;

public class Elixir implements Listener {

    int duration = 7;
    int level = 5;
    HashMap<Player, Boolean> isUsed = new HashMap<Player, Boolean>();


    public void useAbility(Player player, ItemStack item, int slot) {
        if (isUsed.containsKey(player)) {
            return;
        }
        isUsed.put(player, true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { isUsed.remove(player); }, 10L);

        final String kind = variables.getSelectedClass().get(player);

        // 능력 발동
        // int levelStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_효과_레벨");
        // int plusLevel = levelStat;
        methods.potionApply(player, PotionEffectType.HEALTH_BOOST, duration, level);

        // 아이템 소모
        methods.consume(player, item, slot, 1);
    }


    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
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

        if (item.getItemMeta().getDisplayName().contains("영약")) {
            useAbility(player, item, slot);
        }
    }


    @EventHandler
    private void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack item = null;
        int slot;

        for (int i = 0; i < 9; i++) {
            Inventory inventory = player.getInventory();
            ItemStack indexItem = inventory.getItem(i);

            if (indexItem == null || indexItem.getItemMeta().getDisplayName() == null) {
                continue;
            }

            if (indexItem.getItemMeta().getDisplayName().contains("영약")) {
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

        if (item.getItemMeta().getDisplayName().contains("영약")) {
            useAbility(player, item, slot);
        }
    }

}
