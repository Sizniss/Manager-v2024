package kr.sizniss.manager.guis;

import kr.sizniss.manager.guis.equipmentgui.*;
import kr.sizniss.manager.guis.profilegui.ViewProfileGUI;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class ProfileGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    public ProfileGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 36, "프로필" + (player == targetPlayer ? "" : " - 조회 - " + targetPlayer.getName())); // 인벤토리 생성

        // 플레이어 머리
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName("§f" + targetPlayer.getName());
        if (player.isOp()) {
            ArrayList<String> headLore = new ArrayList<String>();
            headLore.add("");
            headLore.add("§fUserName: §7" + kr.sizniss.data.Files.getUserName(targetPlayer));
            headLore.add("§fUserNameList: §7" + kr.sizniss.data.Files.getUserNameList(targetPlayer));
            headLore.add("§fIp: §7" + kr.sizniss.data.Files.getIp(targetPlayer));
            headLore.add("§fIpList: §7" + kr.sizniss.data.Files.getIpList(targetPlayer));
            headMeta.setLore(headLore);
        }
        headMeta.setOwner(targetPlayer.getName());
        head.setItemMeta(headMeta);

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();

        // 조회
        ItemStack view = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta viewMeta = view.getItemMeta();
        viewMeta.setDisplayName("§f조회");
        view.setItemMeta(viewMeta);


        // 전적
        ItemStack record = new ItemStack(Material.BOOK, 1);
        ItemMeta recordMeta = record.getItemMeta();
        recordMeta.setDisplayName("§f전적");
        ArrayList<String> recordLore = new ArrayList<String>();
        recordLore.add("");
        recordLore.add("§f라운드: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Win") + kr.sizniss.data.Files.getRecord(targetPlayer, "Lose")));
        // recordLore.add("§f승리: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Win")));
        // recordLore.add("§f패배: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Lose")));
        recordLore.add("");
        recordLore.add("§f킬: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Kill")));
        recordLore.add("§f치유: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Cure")));
        kr.sizniss.data.Files.getRecord(targetPlayer, "Death"); // recordLore.add("§f데스: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Death")));
        recordLore.add("");
        recordLore.add("§f탈주: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getRecord(targetPlayer, "Escape")));
        recordMeta.setLore(recordLore);
        record.setItemMeta(recordMeta);

        // 경고
        ItemStack warn = new ItemStack(Material.PAPER, 1);
        ItemMeta warnMeta = warn.getItemMeta();
        warnMeta.setDisplayName("§f" + kr.sizniss.warning.Files.getWarnCount(targetPlayer) + " 경고");
        if (kr.sizniss.warning.Files.getTotalWarnCount(targetPlayer) > 0) {
            ArrayList<String> warnLore = new ArrayList<String>();
            warnLore.add("");
            for (int i = 0; i < kr.sizniss.warning.Files.getTotalWarnCount(targetPlayer); i++) {
                if (kr.sizniss.warning.Files.isInvaliedWarn(targetPlayer, i)) {
                    warnLore.add("§f- §7" + i + ": §7§mDate: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(kr.sizniss.warning.Files.getWarnDate(targetPlayer, i)));
                    warnLore.add("   §0. §7§mExecutor: " + kr.sizniss.warning.Files.getWarnExecutor(targetPlayer, i).getName());
                    warnLore.add("   §0. §7§mReason: " + kr.sizniss.warning.Files.getWarnReason(targetPlayer, i));
                } else {
                    warnLore.add("§f- §7" + i + ": §7Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(kr.sizniss.warning.Files.getWarnDate(targetPlayer, i)));
                    warnLore.add("   §0. §7Executor: " + kr.sizniss.warning.Files.getWarnExecutor(targetPlayer, i).getName());
                    warnLore.add("   §0. §7Reason: " + kr.sizniss.warning.Files.getWarnReason(targetPlayer, i));
                }
            }
            warnMeta.setLore(warnLore);
        }
        warn.setItemMeta(warnMeta);

        // 캐시
        ItemStack cash = new ItemStack(Material.MAP, 1);
        ItemMeta cashMeta = cash.getItemMeta();
        cashMeta.setDisplayName("§f" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Cash")) + " 캐시");
        cash.setItemMeta(cashMeta);

        // 골드
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName("§e" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Gold")) + " 골드");
        gold.setItemMeta(goldMeta);

        // 다이아몬드
        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setDisplayName("§b" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond")) + " 다이아몬드");
        diamond.setItemMeta(diamondMeta);

        // 에메랄드
        ItemStack emerald = new ItemStack(Material.EMERALD, 1);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName("§a" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald")) + " 에메랄드");
        emerald.setItemMeta(emeraldMeta);


        inventory.setItem(1, head);
        inventory.setItem(7, player == targetPlayer ? view : blackPane);
        inventory.setItem(11, record);
        inventory.setItem(20, warn);
        inventory.setItem(14, cash);
        inventory.setItem(15, gold);
        inventory.setItem(23, diamond);
        inventory.setItem(24, emerald);

        inventory.setItem(0, blackPane);
        for (int i = 2; i < 7; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(8, blackPane);
        inventory.setItem(9, blackPane);
        inventory.setItem(17, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(26, blackPane);
        for (int i = 27; i < 36; i++) {
            inventory.setItem(i, blackPane);
        }

        inventory.setItem(10, whitePane);
        inventory.setItem(12, whitePane);
        inventory.setItem(13, whitePane);
        inventory.setItem(16, whitePane);
        inventory.setItem(19, whitePane);
        inventory.setItem(21, whitePane);
        inventory.setItem(22, whitePane);
        inventory.setItem(25, whitePane);

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new ProfileGUI(player, targetPlayer); // ProfileGUI 호출
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

                    String name = event.getCurrentItem().getItemMeta().getDisplayName();
                    if (name.contains("조회")) {
                        new ViewProfileGUI(player, player); // ViewProfileGUI 호출
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
