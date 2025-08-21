package kr.sizniss.manager.guis.equipmentgui;

import kr.sizniss.manager.Methods;
import kr.sizniss.manager.guis.EquipmentGUI;
import kr.sizniss.manager.guis.equipmentgui.typegui.TypeStatusGUI;
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

import static kr.sizniss.manager.Manager.*;

public class TypeGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    public TypeGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 45, "장비 - 타입"); // 인벤토리 생성

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


        // 타입
        int typeCount = 11;
        ArrayList<ItemStack> typeList = new ArrayList<ItemStack>();
        for (int i = 0; i < typeCount; i++) {
            String typeName = variables.getTypeName().get(i);

            typeList.add(methods.getType(targetPlayer, typeName).clone());

            ItemMeta typeMeta = typeList.get(i).getItemMeta();

            if (kr.sizniss.data.Files.getOptionType(targetPlayer).equals(typeName)) { // 현재 타입이 장착되어 있을 경우
                typeList.get(i).setDurability(Short.parseShort("4"));
                typeMeta.setDisplayName(typeMeta.getDisplayName() + " §5선택됨");
            }

            ArrayList<String> typeLore = (ArrayList<String>) typeMeta.getLore();
            typeLore.add("");
            typeLore.add(" §f※ 사용");
            typeLore.add("");
            typeLore.add(" §e→ §f<L-Click>: 타입 선택");
            typeLore.add(" §e→ §f<R-Click>: 타입 §c능력치 분배");
            typeLore.add("");
            typeMeta.setLore(typeLore);

            typeList.get(i).setItemMeta(typeMeta);
        }


        inventory.setItem(1, head);

        inventory.setItem(12, typeList.get(0));
        inventory.setItem(13, typeList.get(1));
        inventory.setItem(14, typeList.get(2));
        inventory.setItem(20, typeList.get(3));
        inventory.setItem(21, typeList.get(4));
        inventory.setItem(22, typeList.get(5));
        inventory.setItem(23, typeList.get(6));
        inventory.setItem(24, typeList.get(7));
        inventory.setItem(30, typeList.get(8));
        inventory.setItem(31, typeList.get(9));
        inventory.setItem(32, typeList.get(10));

        inventory.setItem(10, whitePane);
        inventory.setItem(11, whitePane);
        inventory.setItem(15, whitePane);
        inventory.setItem(16, whitePane);
        inventory.setItem(19, whitePane);
        inventory.setItem(25, whitePane);
        inventory.setItem(28, whitePane);
        inventory.setItem(29, whitePane);
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
            new TypeGUI(player, targetPlayer); // TypeGUI 호출
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

                    if (name.contains("버서커")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "버서커");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("바드")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "바드");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("나이트")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "나이트");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("네크로맨서")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "네크로맨서");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("워리어")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "워리어");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("로그")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "로그");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("어쌔신")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "어쌔신");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("레인저")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "레인저");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("버스터")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "버스터");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("서머너")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "서머너");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    } else if (name.contains("헌터")) {
                        kr.sizniss.data.Files.setOptionType(targetPlayer, "헌터");
                        new EquipmentGUI(player, targetPlayer); // EquipmentGUI 호출
                    }

                } else if (event.isRightClick()) {

                    if (name.contains("버서커")) {
                        new TypeStatusGUI(player, targetPlayer, "버서커");
                    } else if (name.contains("바드")) {
                        new TypeStatusGUI(player, targetPlayer, "바드");
                    } else if (name.contains("나이트")) {
                        new TypeStatusGUI(player, targetPlayer, "나이트");
                    } else if (name.contains("네크로맨서")) {
                        new TypeStatusGUI(player, targetPlayer, "네크로맨서");
                    } else if (name.contains("워리어")) {
                        new TypeStatusGUI(player, targetPlayer, "워리어");
                    } else if (name.contains("로그")) {
                        new TypeStatusGUI(player, targetPlayer, "로그");
                    } else if (name.contains("어쌔신")) {
                        new TypeStatusGUI(player, targetPlayer, "어쌔신");
                    } else if (name.contains("레인저")) {
                        new TypeStatusGUI(player, targetPlayer, "레인저");
                    } else if (name.contains("버스터")) {
                        new TypeStatusGUI(player, targetPlayer, "버스터");
                    } else if (name.contains("서머너")) {
                        new TypeStatusGUI(player, targetPlayer, "서머너");
                    } else if (name.contains("헌터")) {
                        new TypeStatusGUI(player, targetPlayer, "헌터");
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
