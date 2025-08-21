package kr.sizniss.manager.abilities;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.*;

public class Reversal implements Listener {

    public int cooldown = 25;
    public int duration = 5;
    public int castingTime = 2;
    public HashMap<Player, Boolean> isCasting = new HashMap<Player, Boolean>();
    public HashMap<Player, Double> accumulatedDamage = new HashMap<Player, Double>();


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 1.5f, 2.0f);

        isCasting.put(player, true); // 시전 시작
        accumulatedDamage.put(player, 0.0);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
                if (isCasting.containsKey(player)) { // 플레이어가 시전 중일 경우
                    isCasting.remove(player);

                    int absorptionLevel = (int) Math.floor(accumulatedDamage.get(player) / 4 * 1.25);

                    int durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
                    int plusDuration = durationStat;
                    methods.potionApply(player, PotionEffectType.ABSORPTION, duration + plusDuration, absorptionLevel + 10);
                }
            }
        }, castingTime * 20);


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

        if (item.getItemMeta().getDisplayName().contains("반전")) {
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

            if (indexItem.getItemMeta().getDisplayName().contains("반전")) {
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

        if (item.getItemMeta().getDisplayName().contains("반전")) {
            useAbility(player, item, slot);
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) { // 피해자 엔티티의 엔티티 타입이 플레이어가 아닐 경우
            return;
        }
        Player player = (Player) event.getEntity();

        if (isCasting.containsKey(player)) { // 플레이어가 시전 중일 경우
            double currentAccumulatedDamage = accumulatedDamage.get(player);
            accumulatedDamage.put(player, currentAccumulatedDamage + event.getDamage()); // 데미지 누적
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        if (event.getVictim().getType() != EntityType.PLAYER) { // 피해자 엔티티의 엔티티 타입이 플레이어가 아닐 경우
            return;
        }
        Player player = (Player) event.getVictim();

        if (isCasting.containsKey(player)) { // 플레이어가 시전 중일 경우
            double currentAccumulatedDamage = accumulatedDamage.get(player);
            accumulatedDamage.put(player, currentAccumulatedDamage + event.getDamage()); // 데미지 누적
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void CustomDamageEntityEvent(CustomDamageEntityEvent event) {
        if (event.getVictim().getType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getVictim();

        if (isCasting.containsKey(player)) { // 플레이어가 시전 중일 경우
            double currentAccumulatedDamage = accumulatedDamage.get(player);
            accumulatedDamage.put(player, currentAccumulatedDamage + event.getDamage()); // 데미지 누적
        }
    }


    @EventHandler
    private void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (isCasting.containsKey(player)) { // 플레이어가 시전 중일 경우
            isCasting.remove(player); // 시전 종료
        }
    }
}
