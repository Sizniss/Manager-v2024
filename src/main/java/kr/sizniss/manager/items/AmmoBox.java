package kr.sizniss.manager.items;

import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.CSUtility;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Files;
import kr.sizniss.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.*;

public class AmmoBox implements Listener {

    private String serverTitle = Files.getServerTitle();

    HashMap<Player, Boolean> isUsed = new HashMap<Player, Boolean>();

    private void useAbility1(Player player, ItemStack item, int slot) {
        if (isUsed.containsKey(player)) {
            return;
        }
        isUsed.put(player, true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            isUsed.remove(player);
        }, 10L);

        String selectedMainWeaponName = variables.getSelectedMainWeapon().get(player);
        ItemStack mainWeapon;
        int mainWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(player, selectedMainWeaponName);

        // 탄약 차감
        boolean successSubtractAmmoAmount = methods.subtractAmmoAmount(player, methods.getPowderChamberSize(selectedMainWeaponName, mainWeaponLevel));
        if (successSubtractAmmoAmount) {
            // 주 무기 즉시 재장전
            if (mainWeaponLevel == 0) {
                mainWeapon = new CSUtility().generateWeapon(selectedMainWeaponName).clone();
            } else {
                mainWeapon = new CSUtility().generateWeapon("(+" + mainWeaponLevel + ")_" + selectedMainWeaponName).clone();
            }
            player.getInventory().setItem(0, mainWeapon);
        }

        // 보조 무기 즉시 재장전
        String selectedSubWeaponName = variables.getSelectedSubWeapon().get(player);
        ItemStack subWeapon = new CSUtility().generateWeapon(selectedSubWeaponName).clone();
        player.getInventory().setItem(1, subWeapon);

        // 근접 무기 즉시 재장전
        CSDirector crackshot = ((CSDirector) Bukkit.getPluginManager().getPlugin("Crackshot")).plugin;
        String selectedMeleeWeapon = variables.getSelectedMeleeWeapon().get(player);
        ItemStack meleeWeapon = new CSUtility().generateWeapon(selectedMeleeWeapon).clone();
        player.getInventory().clear(2);
        player.getInventory().setItem(2, meleeWeapon);
        player.setMetadata(selectedMeleeWeapon + "shootDelay" + 2 + "true", new FixedMetadataValue(crackshot, true));
        crackshot.csminion.tempVars(player, selectedMeleeWeapon + "shootDelay" + 2 + "true", 1L);

        // 아이템 소모
        Manager.methods.consume(player, item, slot, 1);
    }

    private void useAbility2(Player player, Player target, ItemStack item, int slot) {
        // 병과 아이템 지급
        String kind = variables.getSelectedClass().get(target);
        ItemStack kindItem = methods.getClassItem(kind).clone();

        int kindItemAmount = methods.getClassItemAmount(kind);

        if (target.getInventory().getItem(4) != null
                && target.getInventory().getItem(4).getType() != Material.AIR) {
            kindItemAmount += target.getInventory().getItem(4).getAmount();
        }
        if (kindItemAmount >= 64) {
            kindItemAmount = 64;
        }

        kindItem.setAmount(kindItemAmount);

        target.getInventory().setItem(4, kindItem);

        // 지급 메시지 전송
        player.sendMessage(serverTitle + " §9§l" + player.getName() + " §2§l--［〈∇〉］--▶ §9§l" + target.getName());
        target.sendMessage(serverTitle + " §9§l" + player.getName() + " §2§l--［〈∇〉］--▶ §9§l" + target.getName());

        // 아이템 소모
        Manager.methods.consume(player, item, slot, 2);
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

        if (item.getItemMeta().getDisplayName().contains("탄약 박스")) {
            useAbility1(player, item, slot);
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

            if (indexItem.getItemMeta().getDisplayName().contains("탄약 박스")) {
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

        if (item.getItemMeta().getDisplayName().contains("탄약 박스")) {
            useAbility1(player, item, slot);
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    private void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack item = player.getInventory().getItem(slot);

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 표시 이름이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName().contains("탄약 박스")) {
            if (methods.getPlayerInSight(player, 4) == null) {
                return;
            }
            Player target = methods.getPlayerInSight(player, 4);

            if (!SelonZombieSurvival.manager.getHumanList().contains(target)) {
                return;
            }

            if (item.getAmount() > 1) {
                useAbility2(player, target, item, slot);
            }
            else if (item.getAmount() == 1) {
                useAbility2(player, target, item, slot);

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }, 0L);
            }
        }
    }

}
