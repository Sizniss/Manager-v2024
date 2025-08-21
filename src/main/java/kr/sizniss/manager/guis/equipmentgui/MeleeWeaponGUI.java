package kr.sizniss.manager.guis.equipmentgui;

import com.shampaggon.crackshot.CSUtility;
import kr.sizniss.manager.guis.EquipmentGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class MeleeWeaponGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;
    private int page;

    private int task; // 갱신 테스크


    public MeleeWeaponGUI(Player player, OfflinePlayer targetPlayer) {
        new MeleeWeaponGUI(player, targetPlayer, 0);
    }

    public MeleeWeaponGUI(Player player, OfflinePlayer targetPlayer, int page) {
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.page = page;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "장비 - 근접 무기 (Page." + (page + 1) + ")"); // 인벤토리 생성

        // 플레이어 머리
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName("§f" + targetPlayer.getName());
        headMeta.setOwner(targetPlayer.getName());
        head.setItemMeta(headMeta);

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();

        // 이전
        ItemStack prevPage = variables.getPrevPage().clone();

        // 다음
        ItemStack nextPage = variables.getNextPage().clone();


        // 무기
        ArrayList<ItemStack> weapons = new ArrayList<ItemStack>();
        for (String weaponName : variables.getMeleeWeaponName()) {
            ItemStack weapon = null;

            int weaponLevel = kr.sizniss.data.Files.getMeleeWeaponLevel(targetPlayer, weaponName);
            if (weaponLevel == 0) { // 무기 레벨이 0일 경우
                weapon = new CSUtility().generateWeapon(weaponName).clone();
            } else { // 무기 레벨이 0이 아닌 경우
                weapon = new CSUtility().generateWeapon("(+" + weaponLevel + ")_" + weaponName).clone();
            }

            ItemMeta weaponMeta = weapon.getItemMeta();

            if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, weaponName)) { // 무기를 소지하고 있지 않을 경우
                weaponMeta.setDisplayName(weaponMeta.getDisplayName().replace("§f", "§7"));
                weapon.setType(Material.STAINED_GLASS);
                weapon.setDurability(Short.parseShort("8"));
            }

            if (kr.sizniss.data.Files.getOptionWeapon(targetPlayer, "Melee").equals(weaponName)) { // 현재 무기가 장착된 무기일 경우
                weaponMeta.setDisplayName(weaponMeta.getDisplayName() + " §5선택됨");
                weaponMeta.addEnchant(Enchantment.LUCK, 1, true);
                weaponMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            weapon.setItemMeta(weaponMeta);

            weapons.add(weapon);
        }
        int weaponCount = weapons.size();


        inventory.setItem(1, head);

        for (int i = 10; i < 45; i++) {
            inventory.setItem(i, whitePane);
        }

        for (int i = 9; i < 9 + (weaponCount - 36 * page); i++) {
            inventory.setItem(i, weapons.get(i + (36 * page) - 9));
        }

        for (int i = 44; i >= (9 + (weaponCount - 36 * page) >= 9 ? 9 + (weaponCount - 36 * page) : 9); i--) {
            inventory.setItem(i, whitePane);
        }

        inventory.setItem(45, prevPage);
        inventory.setItem(53, nextPage);

        inventory.setItem(0, blackPane);
        for (int i = 2; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        for(int i = 46; i < 53; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new MeleeWeaponGUI(player, targetPlayer, page); // MeleeWeaponGUI 호출
        }, 20L);
    }


    // 이벤트 리스너 클래스
    private class Event implements Listener {

        // 인벤토리 클릭 이벤트
        @EventHandler
        private void InventoryClickEvent(InventoryClickEvent event) {
            if (event.getClickedInventory() == null) {
                return;
            }

            if (event.getClickedInventory().equals(inventory)) {
                event.setCancelled(true);

                String name = event.getCurrentItem().getItemMeta().getDisplayName();
                if (name == null) {
                    return;
                } else if (name.contains("이전")) {
                    if (page > 0) new MeleeWeaponGUI(player, targetPlayer, page - 1);
                } else if (name.contains("다음")) {
                    new MeleeWeaponGUI(player, targetPlayer, page + 1);
                } else if (name.equals("§f")) {
                    return;
                }

                if (!targetPlayer.equals(event.getWhoClicked())) { // 타겟 플레이어와 인벤토리 클릭자가 다를 경우
                    return; // 함수 반환
                }

                int slot = event.getSlot();
                if (45 > slot && slot >= 9) {

                    String weaponName = variables.getMeleeWeaponName().get(slot + (36 * page) - 9);

                    if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, weaponName)) {
                        kr.sizniss.data.Files.setOptionWeapon(targetPlayer, "Melee", weaponName);
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    }

                }

            }
        }

        // 인벤토리 닫기 이벤트
        @EventHandler
        private void InventoryCloseEvent(InventoryCloseEvent event) {
            if (event.getInventory().equals(inventory)) {
                Bukkit.getScheduler().cancelTask(task); // GUI 갱신 종료
                HandlerList.unregisterAll(this); // 이벤트 등록 해제
            }
        }

    }
}
