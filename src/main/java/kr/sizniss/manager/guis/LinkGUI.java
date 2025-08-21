package kr.sizniss.manager.guis;

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

public class LinkGUI {

    private Inventory inventory;

    private Player player;


    private String serverTitle = Files.getServerTitle();

    private String siteAddress = "http://www.sizniss.kro.kr";
    private String cafeAddress = "https://cafe.naver.com/siznissonline";
    private String discordAddress = "https://discord.gg/VdxHyY9";
    private String[] kakaotalkAddress = {"(오픈채팅방) https://open.kakao.com/o/g2uKmny", "(문의) http://pf.kakao.com/_WGHru"};
    private String minelistAddress = "(마인리스트) https://minelist.kr/servers/1403";
    private String[] supportAddress = {"(컬쳐랜드상품권) https://cgko321.wixsite.com/sizniss/blank-2", "(계좌이체) http://pf.kakao.com/_WGHru"};


    public LinkGUI(Player player) {
        this.player = player;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 36, "링크"); // 인벤토리 생성

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();


        // 사이트
        ItemStack site = new ItemStack(Material.BANNER, 1, Short.parseShort("12"));
        ItemMeta siteMeta = site.getItemMeta();
        siteMeta.setDisplayName("§b사이트");
        ArrayList<String> siteLore = new ArrayList<String>();
        siteLore.add("");
        siteLore.add("§f주소: §7" + siteAddress);
        siteMeta.setLore(siteLore);
        site.setItemMeta(siteMeta);

        // 카페
        ItemStack cafe = new ItemStack(Material.BANNER, 1, Short.parseShort("5"));
        ItemMeta cafeMeta = cafe.getItemMeta();
        cafeMeta.setDisplayName("§d카페");
        ArrayList<String> cafeLore = new ArrayList<String>();
        cafeLore.add("");
        cafeLore.add("§f주소: §7" + cafeAddress);
        cafeMeta.setLore(cafeLore);
        cafe.setItemMeta(cafeMeta);

        // 디스코드
        ItemStack discord = new ItemStack(Material.BANNER, 1, Short.parseShort("7"));
        ItemMeta discordMeta = discord.getItemMeta();
        discordMeta.setDisplayName("§7디스코드");
        ArrayList<String> discordLore = new ArrayList<String>();
        discordLore.add("");
        discordLore.add("§f주소: §7" + discordAddress);
        discordMeta.setLore(discordLore);
        discord.setItemMeta(discordMeta);

        // 카카오톡
        ItemStack kakaotalk = new ItemStack(Material.BANNER, 1, Short.parseShort("11"));
        ItemMeta kakaotalkMeta = kakaotalk.getItemMeta();
        kakaotalkMeta.setDisplayName("§e카카오톡");
        ArrayList<String> kakaotalkLore = new ArrayList<String>();
        kakaotalkLore.add("");
        kakaotalkLore.add("§f주소:");
        for (int i = 0; i < kakaotalkAddress.length; i++) {
            kakaotalkLore.add(" §f- §7" + kakaotalkAddress[i]);
        }
        kakaotalkMeta.setLore(kakaotalkLore);
        kakaotalk.setItemMeta(kakaotalkMeta);

        // 마인리스트
        ItemStack minelist = new ItemStack(Material.BANNER, 1, Short.parseShort("10"));
        ItemMeta minelistMeta = minelist.getItemMeta();
        minelistMeta.setDisplayName("§a마인리스트");
        ArrayList<String> minelistLore = new ArrayList<String>();
        minelistLore.add("");
        minelistLore.add("§f주소: §7" + minelistAddress);
        minelistMeta.setLore(minelistLore);
        minelist.setItemMeta(minelistMeta);

        // 후원
        ItemStack support = new ItemStack(Material.BANNER, 1, Short.parseShort("1"));
        ItemMeta supportMeta = support.getItemMeta();
        supportMeta.setDisplayName("§c후원");
        ArrayList<String> supportLore = new ArrayList<String>();
        supportLore.add("");
        supportLore.add("§f주소:");
        for (int i = 0; i < supportAddress.length; i++) {
            supportLore.add(" §f- §7" + supportAddress[i]);
        }
        supportMeta.setLore(supportLore);
        support.setItemMeta(supportMeta);


        inventory.setItem(11, site);
        inventory.setItem(20, cafe);
        inventory.setItem(13, discord);
        inventory.setItem(22, kakaotalk);
        inventory.setItem(15, minelist);
        inventory.setItem(24, support);

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(9, blackPane);
        inventory.setItem(10, whitePane);
        inventory.setItem(12, whitePane);
        inventory.setItem(14, whitePane);
        inventory.setItem(16, whitePane);
        inventory.setItem(17, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(19, whitePane);
        inventory.setItem(21, whitePane);
        inventory.setItem(23, whitePane);
        inventory.setItem(25, whitePane);
        inventory.setItem(26, blackPane);
        for (int i = 27; i < 36; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory);
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
                    if (name.contains("사이트")) {
                        player.closeInventory(); // 인벤토리 닫기

                        player.sendMessage(serverTitle + " §b§l사이트 §f§l주소: §7" + siteAddress);
                    } else if (name.contains("카페")) {
                        player.closeInventory(); // 인벤토리 닫기

                        player.sendMessage(serverTitle + " §d§l카페 §f§l주소: §7" + cafeAddress);
                    } else if (name.contains("디스코드")) {
                        player.closeInventory(); // 인벤토리 닫기

                        player.sendMessage(serverTitle + " §7§l디스코드 §f§l주소: §7" + discordAddress);
                    } else if (name.contains("카카오톡")) {
                        player.closeInventory(); // 인벤토리 닫기

                        player.sendMessage("");
                        player.sendMessage(" " + serverTitle);
                        player.sendMessage(" §e§l카카오톡 §f§l주소:");
                        for (int i = 0; i < kakaotalkAddress.length; i++) {
                            player.sendMessage(" §f- §7" + kakaotalkAddress[i]);
                        }
                    } else if (name.contains("마인리스트")) {
                        player.closeInventory(); // 인벤토리 닫기

                        player.sendMessage(serverTitle + " §a§l마인리스트 §f§l주소: §7" + minelistAddress);
                    } else if (name.contains("후원")) {
                        player.closeInventory(); // 인벤토리 닫기

                        player.sendMessage("");
                        player.sendMessage(" " + serverTitle);
                        player.sendMessage(" §c§l후원 §f§l주소:");
                        for (int i = 0; i < supportAddress.length; i++) {
                            player.sendMessage(" §f- §7" + supportAddress[i]);
                        }
                    }

                }
            }
        }

        @EventHandler
        private void InventoryCloseEvent(InventoryCloseEvent event) {
            if (event.getInventory().equals(inventory)) {
                HandlerList.unregisterAll(this); // 이벤트 등록 해제
            }
        }

    }

}
