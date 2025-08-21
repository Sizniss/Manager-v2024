package kr.sizniss.manager.guis.shopgui;

import kr.sizniss.manager.Files;
import org.bukkit.*;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class SubWeaponShopGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();


    public SubWeaponShopGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "상점 - 보조 무기"); // 인벤토리 생성

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

        // 회색 색유리 판
        ItemStack grayPane = variables.getGrayPane().clone();


        // 캐시
        ItemStack cash = new ItemStack(Material.MAP);
        ItemMeta cashMeta = cash.getItemMeta();
        cashMeta.setDisplayName("§f" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Cash")) + " 캐시");
        cash.setItemMeta(cashMeta);

        // 골드
        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName("§e" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Gold")) + " 골드");
        gold.setItemMeta(goldMeta);

        // 다이아몬드
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setDisplayName("§b" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond")) + " 다이아몬드");
        diamond.setItemMeta(diamondMeta);

        // 에메랄드
        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName("§a" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald")) + " 에메랄드");
        emerald.setItemMeta(emeraldMeta);


        /*
        ArrayList<String> weaponList = variables.getSubWeaponName();
        ArrayList<String> weaponMoney = new ArrayList<String>();
        ArrayList<Integer> weaponPrice = new ArrayList<Integer>();

        weaponList.remove("HK_USP");

        // Gold, Emerald, Diamond
        weaponMoney.add("Gold"); // Desert Eagle
        weaponPrice.add(200000);
        weaponMoney.add("Gold"); // Beretta M93R
        weaponPrice.add(200000);
        weaponMoney.add("Emerald"); // M79 소드오프
        weaponPrice.add(2000);
        weaponMoney.add("Diamond"); // 수리검
        weaponPrice.add(2000);
        weaponMoney.add("Emerald"); // 덕 풋
        weaponPrice.add(2000);
        weaponMoney.add("Diamond"); // 테이저건
        weaponPrice.add(2000);
        weaponMoney.add("Gold"); // Double Barrel 소드오프
        weaponPrice.add(200000);
        weaponMoney.add("Diamond"); // M950
        weaponPrice.add(2000);
        weaponMoney.add("Emerald"); // 토치
        weaponPrice.add(2000);
         */


        inventory.setItem(1, head);

        inventory.setItem(3, cash);
        inventory.setItem(5, gold);
        inventory.setItem(6, diamond);
        inventory.setItem(7, emerald);

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, whitePane);
        }
        inventory.setItem(18, whitePane);
        inventory.setItem(22, whitePane);
        inventory.setItem(26, whitePane);
        inventory.setItem(27, whitePane);
        inventory.setItem(31, whitePane);
        inventory.setItem(35, whitePane);
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, whitePane);
        }

        inventory.setItem(0, blackPane);
        inventory.setItem(2, blackPane);
        inventory.setItem(4, blackPane);
        inventory.setItem(8, blackPane);
        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new SubWeaponShopGUI(player, targetPlayer); // SubWeaponShopGUI 호출
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
                if (name == null) {
                    return;
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
