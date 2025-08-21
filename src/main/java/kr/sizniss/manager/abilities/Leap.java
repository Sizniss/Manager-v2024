package kr.sizniss.manager.abilities;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.variables;

public class Leap implements Listener {

    public int cooldown = 20;


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.5f, 2.0f);

        double dX = player.getLocation().getDirection().getX(); dX = dX * 1.2;
        double dY = player.getLocation().getDirection().getY(); dY = dY * 1.2;
        double dZ = player.getLocation().getDirection().getZ(); dZ = dZ * 1.2;
        Location location = new Location(player.getLocation().getWorld(), dX, dY, dZ);
        if (!((player.isSneaking() && player.getLocation().getBlock().getType() == Material.FLOWER_POT) // 화분 위에서 웅크리고 있거나 끈끈이에 피격될 경우가 아닐 경우
                || ((player.getPotionEffect(PotionEffectType.SLOW) != null && player.getPotionEffect(PotionEffectType.SLOW).getAmplifier() >= 7 - 1)
                && player.getPotionEffect(PotionEffectType.JUMP) != null && player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() >= 250 - 1))) {
            Manager.methods.jump(player, location);
        }

        // 재사용 대기 시간 진행
        int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "도약_재사용_대기_시간");
        int minusCooldown = cooldownStat * 2;
        if (type.equals("로그")) { // 타입이 로그일 경우

            int abilityCooldown = new Flight().cooldown;
            int abilityDuration = new Flight().duration;
            int abilityCooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
            int abilityDurationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");

            ItemStack abilityItem = null;
            for (int i = 0; i < 9; i++) {
                Inventory inventory = player.getInventory();
                ItemStack indexItem = inventory.getItem(i);

                if (indexItem == null
                        || indexItem.getItemMeta() == null
                        || indexItem.getItemMeta().getDisplayName() == null) {
                    continue;
                }

                if (indexItem.getItemMeta().getDisplayName().contains("비행")) {
                    abilityItem = indexItem;
                    break;
                }
            }

            // 비행 능력이 있고, 비행 능력이 지속 중일 경우
            if (abilityItem != null
                    && abilityItem.getItemMeta() != null
                    && abilityItem.getItemMeta().getDisplayName() != null
                    && abilityItem.getItemMeta().getDisplayName().contains("비행")
                    && abilityItem.getAmount() >= (abilityCooldown - abilityCooldownStat * 2) - (abilityDuration + abilityDurationStat)
                    && player.getPotionEffect(PotionEffectType.LEVITATION) != null) {
                Manager.methods.cooldown(player, item, slot, (2.5 - (cooldownStat * 0.5)));
            } else {
                boolean isHost = SelonZombieSurvival.manager.getHostList().contains(player)
                        || SelonZombieSurvival.manager.getCarrierList().contains(player);
                Manager.methods.cooldown(player, item, slot, cooldown - minusCooldown - (isHost ? 12 : 0));
            }

        } else { // 타입이 로그가 아닐 경우
            boolean isHost = SelonZombieSurvival.manager.getHostList().contains(player)
                    || SelonZombieSurvival.manager.getCarrierList().contains(player);
            Manager.methods.cooldown(player, item, slot, cooldown - minusCooldown - (isHost ? 12 : 0));
        }
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

        if (item.getItemMeta().getDisplayName().contains("도약")) {
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

            if (indexItem.getItemMeta().getDisplayName().contains("도약")) {
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

        if (item.getItemMeta().getDisplayName().contains("도약")) {
            useAbility(player, item, slot);
        }
    }

}
