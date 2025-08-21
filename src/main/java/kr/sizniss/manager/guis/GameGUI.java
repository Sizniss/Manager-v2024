package kr.sizniss.manager.guis;

import dselon.selonzombiesurvival.Manager;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class GameGUI {

    private Inventory inventory;

    private Player player;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();


    public GameGUI(Player player) {
        this.player = player;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 27, "게임"); // 인벤토리 생성

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();


        // 게임
        ItemStack room = new ItemStack(Material.WOOL, 1, Short.parseShort("0"));
        ItemMeta roomMeta = room.getItemMeta();
        roomMeta.setDisplayName("§f게임");
        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
        ArrayList<String> roomLore = new ArrayList<String>();
        roomLore.add("");
        String gameProgress = "";
        switch(manager.getGameProgress()) {
            case IDLE:
                room.setDurability(Short.parseShort("0"));
                gameProgress = "대기 중";
                break;
            case WAITING:
                room.setDurability(Short.parseShort("0"));
                gameProgress = "대기 중";
                break;
            case READY:
                room.setDurability(Short.parseShort("4"));
                gameProgress = "준비 중";
                break;
            case GAME:
                room.setDurability(Short.parseShort("14"));
                gameProgress = "게임 중";
                break;
            case ENDING:
                room.setDurability(Short.parseShort("4"));
                gameProgress = "종료 중";
                break;
            default:
                room.setDurability(Short.parseShort("0"));
                gameProgress = "대기 중";
                break;
        }
        roomLore.add("§f상태: §7" + gameProgress);
        roomLore.add("§f맵: §7" + manager.getMapName());
        roomLore.add("§f인원: §7" + manager.getPlayerList().size());
        roomLore.add("§f플레이어: ");
        if (manager.getPlayerList().size() > 0) {
            if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.IDLE || manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.WAITING) {
                room.setDurability(Short.parseShort("4"));
            }
            for (Player playingPlayer : manager.getPlayerList()) {
                roomLore.add(" §f- " + (playingPlayer.equals(player) ? "§e" : "§7") + playingPlayer.getName());
            }
        } else {
            roomLore.add(" §f- []");
        }
        roomMeta.setLore(roomLore);
        room.setItemMeta(roomMeta);

        // 나가기
        ItemStack exit = new ItemStack(Material.BARRIER, 1);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName("§c나가기");
        exit.setItemMeta(exitMeta);


        inventory.setItem(13, room);
        inventory.setItem(26, exit);

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(9, blackPane);
        inventory.setItem(12, blackPane);
        inventory.setItem(14, blackPane);
        inventory.setItem(17, blackPane);
        for (int i = 18; i < 26; i++) {
            inventory.setItem(i, blackPane);
        }

        inventory.setItem(10, whitePane);
        inventory.setItem(11, whitePane);
        inventory.setItem(15, whitePane);
        inventory.setItem(16, whitePane);

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new GameGUI(player); // GameGUI 호출
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
                    if (name.contains("게임")) {
                        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
                        manager.addPlayer(player); // 게임 입장

                        new GameGUI(player);
                    } else if (name.contains("나가기")) {
                        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
                        if (manager.getGameProgress() != Manager.GameProgress.GAME) { // 게임 중이 아닐 경우
                            manager.removePlayer(player); // 게임 퇴장

                            new GameGUI(player); // GameGUI 호출
                        } else { // 게임 중일 경우
                            player.sendMessage(serverTitle + " §f§l게임이 진행 중일 땐 퇴장하실 수 없습니다!");

                            player.closeInventory();
                        }
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
