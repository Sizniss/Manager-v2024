package kr.sizniss.manager.items;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.*;

public class WaterMedicine implements Listener {

    int duration = 7;
    HashMap<Player, Boolean> isUsed = new HashMap<Player, Boolean>();


    private void useAbility(Player player, ItemStack item, int slot) {
        if (isUsed.containsKey(player)) {
            return;
        }
        isUsed.put(player, true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { isUsed.remove(player); }, 10L);

        final String kind = variables.getSelectedClass().get(player);

        // 능력 발동
        // int durationStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_지속_시간");
        // int plusDuration = durationStat;
        player.removePotionEffect(PotionEffectType.GLOWING);
        methods.potionApply(player, PotionEffectType.INVISIBILITY, duration, 1); // 투명화 효과 부여
        methods.potionApply(player, PotionEffectType.DAMAGE_RESISTANCE, duration, methods.getPotionLevel(player, PotionEffectType.DAMAGE_RESISTANCE) + 1); // 저항 효과 부여
        methods.potionApply(player, PotionEffectType.SPEED, 1.5, 5); // 속도 증가 효과 부여

        player.getInventory().setHelmet(new ItemStack(Material.AIR));

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (SelonZombieSurvival.manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우
                if (player.getPotionEffect(PotionEffectType.INVISIBILITY) == null) {
                    ItemStack helmet;
                    if (SelonZombieSurvival.manager.getHeroList().contains(player)) { // 플레이어가 영웅일 경우
                        helmet = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("15"));
                    } else { // 플레이어가 영웅이 아닐 경우
                        helmet = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("0"));
                    }
                    helmet.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
                    ItemMeta helmetMeta = helmet.getItemMeta();
                    helmetMeta.setDisplayName("§9영웅");
                    helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    helmet.setItemMeta(helmetMeta);
                    player.getInventory().setHelmet(helmet);
                }
            }
        }, duration * 20);


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

        if (item.getItemMeta().getDisplayName().contains("수약")) {
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

            if (indexItem.getItemMeta().getDisplayName().contains("수약")) {
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

        if (item.getItemMeta().getDisplayName().contains("수약")) {
            useAbility(player, item, slot);
        }
    }

}
