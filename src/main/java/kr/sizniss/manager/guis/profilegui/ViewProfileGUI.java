package kr.sizniss.manager.guis.profilegui;

import kr.sizniss.manager.Files;
import kr.sizniss.manager.guis.ProfileGUI;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class ViewProfileGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;
    private int page;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();


    public ViewProfileGUI(Player player, OfflinePlayer targetPlayer) {
        new ViewProfileGUI(player, targetPlayer, 0);
    }

    public ViewProfileGUI(Player player, OfflinePlayer targetPlayer, int page) {
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.page = page;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "프로필 - 조회 (Page." + (page + 1) + ")"); // 인벤토리 생성

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


        // 머리
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        ArrayList<ItemStack> heads = new ArrayList<ItemStack>();
        for (Player onlinePlayer : onlinePlayers) {
            if (onlinePlayer.equals(targetPlayer)) continue;

            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
            SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
            itemMeta.setDisplayName("§f" + onlinePlayer.getName());
            itemMeta.setOwner(onlinePlayer.getName());
            item.setItemMeta(itemMeta);

            heads.add(item);
        }
        int playerCount = heads.size();


        inventory.setItem(1, head);

        for (int i = 10; i < 45; i++) {
            inventory.setItem(i, whitePane);
        }

        for (int i = 9; i < 9 + (playerCount - 36 * page); i++) {
            inventory.setItem(i, heads.get(i - 9));
        }

        for (int i = 44; i >= (9 + (playerCount - 36 * page) >= 9 ? 9 + (playerCount - 36 * page) : 9); i--) {
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
                } else if (name.contains("이전")) {
                    if (page > 0) new ViewProfileGUI(player, targetPlayer, page - 1); // ViewProfileGUI 호출
                } else if (name.contains("다음")) {
                    new ViewProfileGUI(player, targetPlayer, page + 1); // ViewProfileGUI 호출
                } else if (name.equals("§f")) {
                    return;
                }

                int slot = event.getSlot();
                if (45 > slot && slot >= 9) {
                    OfflinePlayer shown = Bukkit.getOfflinePlayer(name.replace("§f", ""));

                    new ProfileGUI((Player) targetPlayer, shown);

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
