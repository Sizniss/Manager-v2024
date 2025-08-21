package kr.sizniss.manager.guis.equipmentgui;

import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
import kr.sizniss.manager.guis.EquipmentGUI;
import kr.sizniss.manager.guis.equipmentgui.classgui.ClassStatusGUI;
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

import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class ClassGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    public ClassGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 45, "장비 - 병과"); // 인벤토리 생성

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


        // 병과
        int classCount = 7;
        ArrayList<ItemStack> classList = new ArrayList<ItemStack>();
        for (int i = 0; i < classCount; i++) {
            String className = variables.getClassName().get(i);

            classList.add(Manager.methods.getClass(targetPlayer, className).clone());

            ItemMeta classMeta = classList.get(i).getItemMeta();
            
            if (kr.sizniss.data.Files.getOptionClass(targetPlayer).equals(className)) { // 현재 병과가 장착되어 있을 경우
                classList.get(i).setDurability(Short.parseShort("1"));
                classMeta.setDisplayName(classMeta.getDisplayName() + " §5선택됨");
            }

            ArrayList<String> classLore = (ArrayList<String>) classMeta.getLore();
            classLore.add("");
            classLore.add(" §f※ 사용");
            classLore.add("");
            classLore.add(" §e→ §f<L-Click>: 병과 선택");
            classLore.add(" §e→ §f<R-Click>: 병과 §b능력치 분배");
            classLore.add("");
            classMeta.setLore(classLore);

            classList.get(i).setItemMeta(classMeta);
        }


        inventory.setItem(1, head);

        inventory.setItem(12, classList.get(0));
        inventory.setItem(13, classList.get(1));
        inventory.setItem(14, classList.get(2));
        inventory.setItem(21, classList.get(3));
        inventory.setItem(22, classList.get(4));
        inventory.setItem(23, classList.get(5));
        inventory.setItem(31, classList.get(6));

        inventory.setItem(10, whitePane);
        inventory.setItem(11, whitePane);
        inventory.setItem(15, whitePane);
        inventory.setItem(16, whitePane);
        inventory.setItem(19, whitePane);
        inventory.setItem(20, whitePane);
        inventory.setItem(24, whitePane);
        inventory.setItem(25, whitePane);
        inventory.setItem(28, whitePane);
        inventory.setItem(29, whitePane);
        inventory.setItem(30, whitePane);
        inventory.setItem(32, whitePane);
        inventory.setItem(33, whitePane);
        inventory.setItem(34, whitePane);

        inventory.setItem(0, blackPane);
        for (int i = 2; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(9, blackPane);
        inventory.setItem(17, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(26, blackPane);
        inventory.setItem(27, blackPane);
        inventory.setItem(35, blackPane);
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new ClassGUI(player, targetPlayer); // ClassGUI 호출
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

                if (!targetPlayer.equals(event.getWhoClicked())) { // 타겟 플레이어와 인벤토리 클릭자가 다를 경우
                    return; // 함수 반환
                }

                String name = event.getCurrentItem().getItemMeta().getDisplayName();
                if (event.isLeftClick()) {
                    
                    if (name.contains("보병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "보병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("포병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "포병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("교란병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "교란병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("의무병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "의무병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("기갑병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "기갑병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("보급병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "보급병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("공병")) {
                        kr.sizniss.data.Files.setOptionClass(targetPlayer, "공병");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    }
                    
                } else if (event.isRightClick()) {
                    
                    if (name.contains("보병")) {
                        new ClassStatusGUI(player, targetPlayer, "보병");
                    } else if (name.contains("포병")) {
                        new ClassStatusGUI(player, targetPlayer, "포병");
                    } else if (name.contains("교란병")) {
                        new ClassStatusGUI(player, targetPlayer, "교란병");
                    } else if (name.contains("의무병")) {
                        new ClassStatusGUI(player, targetPlayer, "의무병");
                    } else if (name.contains("기갑병")) {
                        new ClassStatusGUI(player, targetPlayer, "기갑병");
                    } else if (name.contains("보급병")) {
                        new ClassStatusGUI(player, targetPlayer, "보급병");
                    } else if (name.contains("공병")) {
                        new ClassStatusGUI(player, targetPlayer, "공병");
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
