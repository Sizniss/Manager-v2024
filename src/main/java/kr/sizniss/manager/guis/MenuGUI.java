package kr.sizniss.manager.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static kr.sizniss.manager.Manager.variables;

public class MenuGUI {

    private Player player;


    public MenuGUI(Player player) {
        this.player = player;

        createGUI(); // GUI 생성
    }


    // GUI 생성 함수
    private void createGUI() {
        Inventory inventory = player.getInventory(); // 인벤토리 생성

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();


        // 게임
        ItemStack game = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta gameMeta = game.getItemMeta();
        gameMeta.setDisplayName("§f게임");
        game.setItemMeta(gameMeta);

        // 상점
        ItemStack shop = new ItemStack(Material.BEACON);
        ItemMeta shopMeta = shop.getItemMeta();
        shopMeta.setDisplayName("§f상점");
        shop.setItemMeta(shopMeta);

        // 상자함
        ItemStack box = new ItemStack(Material.ENDER_CHEST);
        ItemMeta boxMeta = box.getItemMeta();
        boxMeta.setDisplayName("§f상자");
        box.setItemMeta(boxMeta);

        // 장비
        ItemStack equipment = new ItemStack(Material.CHEST);
        ItemMeta equipmentMeta = equipment.getItemMeta();
        equipmentMeta.setDisplayName("§f장비");
        equipment.setItemMeta(equipmentMeta);

        // 코디
        ItemStack codi = new ItemStack(Material.JUKEBOX);
        ItemMeta codiMeta = codi.getItemMeta();
        codiMeta.setDisplayName("§f코디");
        codi.setItemMeta(codiMeta);

        // 프로필
        ItemStack profile = new ItemStack(Material.WATCH);
        ItemMeta profileMeta = profile.getItemMeta();
        profileMeta.setDisplayName("§f프로필");
        profile.setItemMeta(profileMeta);

        // 링크
        ItemStack link = new ItemStack(Material.COMPASS);
        ItemMeta linkMeta = link.getItemMeta();
        linkMeta.setDisplayName("§f링크");
        link.setItemMeta(linkMeta);

        // 규칙
        ItemStack rule = new ItemStack(Material.BOOK);
        ItemMeta ruleMeta = rule.getItemMeta();
        ruleMeta.setDisplayName("§f규칙");
        rule.setItemMeta(ruleMeta);


        inventory.setItem(18, game);
        inventory.setItem(20, shop);
        inventory.setItem(21, box);
        inventory.setItem(22, equipment);
        inventory.setItem(23, codi);
        inventory.setItem(24, profile);
        inventory.setItem(25, link);
        inventory.setItem(26, rule);

        inventory.setItem(19, whitePane);

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, blackPane);
        }
        for (int i = 27; i < 36; i++) {
            inventory.setItem(i, blackPane);
        }
    }


    // 이벤트 리스너 클래스
    public static class Event implements Listener {

        // 인벤토리 클릭 이벤트
        @EventHandler
        private void InventoryClickEvent(InventoryClickEvent event) {
            if (event.getClickedInventory() == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = player.getInventory();
            if (event.getClickedInventory().equals(inventory)) {

                if (event.getCurrentItem().getType() != Material.AIR) {

                    String name = event.getCurrentItem().getItemMeta().getDisplayName();
                    if (name == null) {
                        return;
                    } else if (name.contains("게임")) {
                        event.setCancelled(true);
                        new GameGUI(player); // GameGUI 호출
                    } else if (name.contains("상점")) {
                        event.setCancelled(true);
                        new ShopGUI(player, player); // ShopGUI 호출
                    } else if (name.contains("상자")) {
                        event.setCancelled(true);
                        new BoxGUI(player, player); // BoxGUI 호출
                    } else if (name.contains("장비")) {
                        event.setCancelled(true);
                        new EquipmentGUI(player, player); // EquipmentGUI 호출
                    } else if (name.contains("코디")) {
                        event.setCancelled(true);
                        new CodiGUI(player, player); // CodiGUI 호출
                    } else if (name.contains("프로필")) {
                        event.setCancelled(true);
                        new ProfileGUI(player, player); // ProfileGUI 호출
                    } else if (name.contains("링크")) {
                        event.setCancelled(true);
                        new LinkGUI(player); // LinkGUI 호출
                    } else if (name.contains("규칙")) {
                        event.setCancelled(true);
                        new RuleGUI(player); // RuleGUI 호출
                    } else if (name.equalsIgnoreCase("§f")) {
                        event.setCancelled(true);
                    }

                }
            }
        }

        @EventHandler
        public void onDisable(PluginDisableEvent event) {
            // Plugin shutdown logic
            for (Player player : Bukkit.getOnlinePlayers()) { // 온라인 플레이어
                player.closeInventory(); // 인벤토리 닫기
            }
        }

    }

}
