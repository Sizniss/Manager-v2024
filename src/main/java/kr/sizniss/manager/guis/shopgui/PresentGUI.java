package kr.sizniss.manager.guis.shopgui;

import kr.sizniss.manager.Files;
import kr.sizniss.manager.guis.ShopGUI;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class PresentGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;
    private String box;
    private int amount;
    private int page;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();

    private int normalWeaponBoxPrice = 1000; // 골드
    private int specialWeaponBoxPrice = 100000; // 골드
    private int premiumWeaponBoxPrice = 100; // 캐시


    public PresentGUI(Player player, OfflinePlayer targetPlayer, String box, int amount) {
        new PresentGUI(player, targetPlayer, box, amount, 0);
    }

    public PresentGUI(Player player, OfflinePlayer targetPlayer, String box, int amount, int page) {
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.box = box;
        this.amount = amount;
        this.page = page;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "상점 - 선물 (Page." + (page + 1) + ")"); // 인벤토리 생성

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


        // 상자
        ItemStack boxItem = null;
        if (box.equals("Normal Weapon Box")) {
            boxItem = variables.getNormalWeaponBox().clone();
            boxItem.setAmount(amount);
        } else if (box.equals("Special Weapon Box")) {
            boxItem = variables.getSpecialWeaponBox().clone();
            boxItem.setAmount(amount);
        } else if (box.equals("Premium Weapon Box")) {
            boxItem = variables.getPremiumWeaponBox().clone();
            boxItem.setAmount(amount);
        }

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

        inventory.setItem(7, boxItem);

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
        for (int i = 2; i < 7; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(8, blackPane);
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
                    if (page > 0) new PresentGUI(player, targetPlayer, box, amount, page - 1); // PresentGUI 호출
                } else if (name.contains("다음")) {
                    new PresentGUI(player, targetPlayer, box, amount, page + 1); // PresentGUI 호출
                } else if (name.equals("§f")) {
                    return;
                }

                int slot = event.getSlot();
                if (45 > slot && slot >= 9) {
                    OfflinePlayer receiver = Bukkit.getOfflinePlayer(name.replace("§f", ""));

                    String product = box;

                    if (product.equals("Normal Weapon Box")) { // 일반 무기 상자
                        String productDisplay = "§f일반 무기 상자";
                        
                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = normalWeaponBoxPrice * amount;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.addBox(receiver, product, amount); // 상자 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §6§l" + receiver.getName() + "§f§l님에게 §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 선물하였습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Present " + product + "(x" + amount + ") to " + receiver.getName() + "]");

                            ((Player) receiver).sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 선물을 보냈습니다!");
                            ((Player) receiver).sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + receiver.getName() + ": Get " + product + "(x" + amount + ")]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        }
                        
                    } else if (product.equals("Special Weapon Box")) { // 특별 무기 상자
                        String productDisplay = "§f특별 무기 상자";

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = specialWeaponBoxPrice * amount;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.addBox(receiver, product, amount); // 상자 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §6§l" + receiver.getName() + "§f§l님에게 §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 선물하였습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Present " + product + "(x" + amount + ") to " + receiver.getName() + "]");

                            ((Player) receiver).sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 선물을 보냈습니다!");
                            ((Player) receiver).sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + receiver.getName() + ": Get " + product + "(x" + amount + ")]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        }

                    } else if (product.equals("Premium Weapon Box")) { // 고급 무기 상자
                        String productDisplay = "§f고급 무기 상자";

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = premiumWeaponBoxPrice * amount;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.addBox(receiver, product, amount); // 상자 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §6§l" + receiver.getName() + "§f§l님에게 §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 선물하였습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Present " + product + "(x" + amount + ") to " + receiver.getName() + "]");

                            ((Player) receiver).sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 선물을 보냈습니다!");
                            ((Player) receiver).sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + receiver.getName() + ": Get " + product + "(x" + amount + ")]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
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
