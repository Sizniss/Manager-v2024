package kr.sizniss.manager.abilities;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.*;

public class Hide implements Listener {

    public int cooldown = 25;
    public int duration = 4;
    public double consumedHealth = 50;


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.5f, 0.0f);

        double currentHealth = player.getHealth();
        if (!player.isDead()) {
            player.setHealth(currentHealth - consumedHealth > 0 ? currentHealth - consumedHealth : 1);
        }

        int durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
        int plusDuration = durationStat;
        methods.potionApply(player, PotionEffectType.INVISIBILITY, duration + plusDuration, 1); // 투명화 효과 부여
        methods.potionApply(player, PotionEffectType.SLOW, duration + plusDuration, 2); // 구속 효과 부여

        player.getInventory().setHelmet(new ItemStack(Material.AIR)); // 투구 제거

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
                ItemStack helmet = new ItemStack(Material.SKULL_ITEM);
                if (SelonZombieSurvival.manager.getHostList().contains(player)
                        || SelonZombieSurvival.manager.getCarrierList().contains(player)) { // 플레이어가 숙주 혹은 보균자일 경우
                    helmet.setDurability(Short.parseShort("1"));
                } else { // 플레이어가 숙주 및 보균자가 아닐 경우
                    helmet.setDurability(Short.parseShort("0"));
                }
                helmet.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
                ItemMeta helmetMeta = helmet.getItemMeta();
                helmetMeta.setDisplayName("§c숙주");
                helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                helmet.setItemMeta(helmetMeta);
                player.getInventory().setHelmet(helmet); // 투구 장착
            }
        }, duration * 20 + plusDuration * 20);


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

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName().contains("은신")) {
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

            if (indexItem.getItemMeta().getDisplayName().contains("은신")) {
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

        if (item.getItemMeta().getDisplayName().contains("은신")) {
            useAbility(player, item, slot);
        }
    }

}
