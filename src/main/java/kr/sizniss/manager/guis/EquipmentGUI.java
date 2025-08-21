package kr.sizniss.manager.guis;

import com.shampaggon.crackshot.CSUtility;
import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
import kr.sizniss.manager.guis.equipmentgui.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class EquipmentGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크

    public EquipmentGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 36, "장비"); // 인벤토리 생성

        // 플레이어 머리
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName("§f" + targetPlayer.getName());
        headMeta.setOwner(targetPlayer.getName());
        head.setItemMeta(headMeta);

        // 호퍼 - 주 무기
        ItemStack mainWeaponHopper = new ItemStack(Material.HOPPER);
        ItemMeta mainWeaponHopperMeta = mainWeaponHopper.getItemMeta();
        mainWeaponHopperMeta.setDisplayName("§f주 무기");
        mainWeaponHopper.setItemMeta(mainWeaponHopperMeta);

        // 호퍼 - 보조 무기
        ItemStack subWeaponHopper = new ItemStack(Material.HOPPER);
        ItemMeta subWeaponHopperMeta = subWeaponHopper.getItemMeta();
        subWeaponHopperMeta.setDisplayName("§f보조 무기");
        subWeaponHopper.setItemMeta(subWeaponHopperMeta);

        // 호퍼 - 근접 무기
        ItemStack meleeWeaponHopper = new ItemStack(Material.HOPPER);
        ItemMeta meleeWeaponHopperMeta = meleeWeaponHopper.getItemMeta();
        meleeWeaponHopperMeta.setDisplayName("§f근접 무기");
        meleeWeaponHopper.setItemMeta(meleeWeaponHopperMeta);

        // 호퍼 - 병과
        ItemStack kindHopper = new ItemStack(Material.HOPPER);
        ItemMeta kindHopperMeta = kindHopper.getItemMeta();
        kindHopperMeta.setDisplayName("§f병과");
        kindHopper.setItemMeta(kindHopperMeta);

        // 호퍼 - 타입
        ItemStack typeHopper = new ItemStack(Material.HOPPER);
        ItemMeta typeHopperMeta = typeHopper.getItemMeta();
        typeHopperMeta.setDisplayName("§f타입");
        typeHopper.setItemMeta(typeHopperMeta);

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();

        // 회색 색유리 판
        ItemStack grayPane = variables.getGrayPane().clone();


        // 주 무기
        ItemStack mainWeapon;
        String selectedMainWeaponName = kr.sizniss.data.Files.getOptionWeapon(targetPlayer, "Main");
        int mainWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(targetPlayer, selectedMainWeaponName);
        if (mainWeaponLevel == 0) {
            mainWeapon = new CSUtility().generateWeapon(selectedMainWeaponName).clone();
        } else {
            mainWeapon = new CSUtility().generateWeapon("(+" + mainWeaponLevel + ")_" + selectedMainWeaponName).clone();
        }

        // 보조 무기
        ItemStack subWeapon;
        String selectedSubWeaponName = kr.sizniss.data.Files.getOptionWeapon(targetPlayer, "Sub");
        int subWeaponLevel = kr.sizniss.data.Files.getSubWeaponLevel(targetPlayer, selectedSubWeaponName);
        if (subWeaponLevel == 0) {
            subWeapon = new CSUtility().generateWeapon(selectedSubWeaponName).clone();
        } else {
            subWeapon = new CSUtility().generateWeapon("(+" + subWeaponLevel + ")_" + selectedSubWeaponName).clone();
        }

        // 근접 무기
        ItemStack meleeWeapon;
        String selectedMeleeWeaponName = kr.sizniss.data.Files.getOptionWeapon(targetPlayer, "Melee");
        int meleeWeaponLevel = kr.sizniss.data.Files.getMeleeWeaponLevel(targetPlayer, selectedMeleeWeaponName);
        if (meleeWeaponLevel == 0) {
            meleeWeapon = new CSUtility().generateWeapon(selectedMeleeWeaponName).clone();
        } else {
            meleeWeapon = new CSUtility().generateWeapon("(+" + meleeWeaponLevel + ")_" + selectedMeleeWeaponName).clone();
        }

        // 병과
        ItemStack kind = Manager.methods.getClass(targetPlayer, kr.sizniss.data.Files.getOptionClass(targetPlayer)).clone();

        // 타입
        ItemStack type = Manager.methods.getType(targetPlayer, kr.sizniss.data.Files.getOptionType(targetPlayer)).clone();


        inventory.setItem(1, head);

        inventory.setItem(10, mainWeaponHopper);
        inventory.setItem(11, subWeaponHopper);
        inventory.setItem(12, meleeWeaponHopper);
        inventory.setItem(14, kindHopper);
        inventory.setItem(16, typeHopper);

        inventory.setItem(19, mainWeapon);
        inventory.setItem(20, subWeapon);
        inventory.setItem(21, meleeWeapon);
        inventory.setItem(23, kind);
        inventory.setItem(25, type);

        inventory.setItem(13, grayPane);
        inventory.setItem(15, grayPane);
        inventory.setItem(22, grayPane);
        inventory.setItem(24, grayPane);

        inventory.setItem(0, blackPane);
        for(int i = 2; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(9, blackPane);
        inventory.setItem(17, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(26, blackPane);
        for(int i = 27; i < 36; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
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

                if (!player.equals(event.getWhoClicked())) { // 플레이어와 인벤토리 클릭자가 다를 경우
                    return; // 함수 반환
                }

                if (event.getCurrentItem().getType() != Material.AIR) {

                    int slot = event.getSlot();
                    if (slot == 19) {
                        new MainWeaponGUI(player, targetPlayer); // MainWeaponGUI 호출
                    } else if (slot == 20) {
                        new SubWeaponGUI(player, targetPlayer); // SubWeaponGUI 호출
                    } else if (slot == 21) {
                        new MeleeWeaponGUI(player, targetPlayer); // MeleeWeaponGUI 호출
                    } else if (slot == 23) {
                        new ClassGUI(player, targetPlayer); // ClassGUI 호출
                    } else if (slot == 25) {
                        new TypeGUI(player, targetPlayer); // TypeGUI 호출
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
