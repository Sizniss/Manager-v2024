package kr.sizniss.manager.guis;

import kr.sizniss.manager.Files;
import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
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
import java.util.ArrayList;

import static kr.sizniss.manager.Manager.*;

public class BoxGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();


    public BoxGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 36, "상자"); // 인벤토리 생성

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


        // 일반 무기 상자
        ItemStack normalWeaponBox = variables.getNormalWeaponBox().clone();
        int currentNormalWeaponBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, "Normal Weapon Box");
        if (currentNormalWeaponBoxAmount >= 127) {
            normalWeaponBox.setAmount(127);
        } else if (127 > currentNormalWeaponBoxAmount && currentNormalWeaponBoxAmount > 0) {
            normalWeaponBox.setAmount(currentNormalWeaponBoxAmount);
        } else {
            normalWeaponBox.setType(Material.STAINED_GLASS);
            normalWeaponBox.setDurability(Short.parseShort("8"));
        }
        ItemMeta normalWeaponBoxMeta = normalWeaponBox.getItemMeta();
        String normalWeaponBoxTitle = "§f일반 무기 상자";
        normalWeaponBoxMeta.setDisplayName(currentNormalWeaponBoxAmount > 0 ? normalWeaponBoxTitle : normalWeaponBoxTitle.replace("§f", "§7"));
        if (currentNormalWeaponBoxAmount > 0) {
            ArrayList<String> normalWeaponBoxLore = (ArrayList<String>) normalWeaponBoxMeta.getLore();
            normalWeaponBoxLore.add("");
            normalWeaponBoxLore.add(" §f※ 사용");
            normalWeaponBoxLore.add("");
            normalWeaponBoxLore.add(" §e→ §f<L-Click>: §71개 개봉");
            normalWeaponBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 개봉");
            normalWeaponBoxLore.add("");
            normalWeaponBoxMeta.setLore(normalWeaponBoxLore);
        }
        normalWeaponBox.setItemMeta(normalWeaponBoxMeta);

        // 특별 무기 상자
        ItemStack specialWeaponBox = variables.getSpecialWeaponBox().clone();
        int currentSpecialWeaponBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, "Special Weapon Box");
        if (currentSpecialWeaponBoxAmount >= 127) {
            specialWeaponBox.setAmount(127);
        } else if (127 > currentSpecialWeaponBoxAmount && currentSpecialWeaponBoxAmount > 0) {
            specialWeaponBox.setAmount(currentSpecialWeaponBoxAmount);
        } else {
            specialWeaponBox.setType(Material.STAINED_GLASS);
            specialWeaponBox.setDurability(Short.parseShort("8"));
        }
        ItemMeta specialWeaponBoxMeta = specialWeaponBox.getItemMeta();
        String specialWeaponBoxTitle = "§f특별 무기 상자";
        specialWeaponBoxMeta.setDisplayName(currentSpecialWeaponBoxAmount > 0 ? specialWeaponBoxTitle : specialWeaponBoxTitle.replace("§f", "§7"));
        if (currentSpecialWeaponBoxAmount > 0) {
            ArrayList<String> specialWeaponBoxLore = (ArrayList<String>) specialWeaponBoxMeta.getLore();
            specialWeaponBoxLore.add("");
            specialWeaponBoxLore.add(" §f※ 사용");
            specialWeaponBoxLore.add("");
            specialWeaponBoxLore.add(" §e→ §f<L-Click>: §71개 개봉");
            specialWeaponBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 개봉");
            specialWeaponBoxLore.add("");
            specialWeaponBoxMeta.setLore(specialWeaponBoxLore);
        }
        specialWeaponBox.setItemMeta(specialWeaponBoxMeta);

        // 고급 무기 상자
        ItemStack premiumWeaponBox = variables.getPremiumWeaponBox().clone();
        int currentPremiumWeaponBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, "Premium Weapon Box");
        if (currentPremiumWeaponBoxAmount >= 127) {
            premiumWeaponBox.setAmount(127);
        } else if (127 > currentPremiumWeaponBoxAmount && currentPremiumWeaponBoxAmount > 0) {
            premiumWeaponBox.setAmount(currentPremiumWeaponBoxAmount);
        } else {
            premiumWeaponBox.setType(Material.STAINED_GLASS);
            premiumWeaponBox.setDurability(Short.parseShort("8"));
        }
        ItemMeta premiumWeaponBoxMeta = premiumWeaponBox.getItemMeta();
        String premiumWeaponBoxTitle = "§f고급 무기 상자";
        premiumWeaponBoxMeta.setDisplayName(currentPremiumWeaponBoxAmount > 0 ? premiumWeaponBoxTitle : premiumWeaponBoxTitle.replace("§f", "§7"));
        ArrayList<String> premiumWeaponBoxLore = (ArrayList<String>) premiumWeaponBoxMeta.getLore();
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §f※ 기타");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §e→ §e금 §f주 무기 §7확정 획득§f: §7" + kr.sizniss.data.Files.getBoxOpenCount(targetPlayer, "Premium Weapon Box") + "§f/§7" + variables.getMaxBoxOpenCount());
        premiumWeaponBoxLore.add(" §e→ §e금 §f주 무기§7를 모두 소지하고 있을");
        premiumWeaponBoxLore.add("   §7경우, §e금 §f조각(x4) §7획득");
        premiumWeaponBoxLore.add(" §e→ §e금 §f주 무기 §7혹은 §e금 §f조각(x4) §7획득");
        premiumWeaponBoxLore.add("   §7시 개봉 횟수가 초기화됩니다.");
        premiumWeaponBoxLore.add("");
        if (currentPremiumWeaponBoxAmount > 0) {
            premiumWeaponBoxLore.add("");
            premiumWeaponBoxLore.add(" §f※ 사용");
            premiumWeaponBoxLore.add("");
            premiumWeaponBoxLore.add(" §e→ §f<L-Click>: §71개 개봉");
            premiumWeaponBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 개봉");
            premiumWeaponBoxLore.add("");
        }
        premiumWeaponBoxMeta.setLore(premiumWeaponBoxLore);
        premiumWeaponBox.setItemMeta(premiumWeaponBoxMeta);

        // 일반 자산 상자
        ItemStack normalMoneyBox = variables.getNormalMoneyBox().clone();
        int currentNormalMoneyBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, "Normal Money Box");
        if (currentNormalMoneyBoxAmount >= 127) {
            normalMoneyBox.setAmount(127);
        } else if (127 > currentNormalMoneyBoxAmount && currentNormalMoneyBoxAmount > 0) {
            normalMoneyBox.setAmount(currentNormalMoneyBoxAmount);
        } else {
            normalMoneyBox.setType(Material.STAINED_GLASS);
            normalMoneyBox.setDurability(Short.parseShort("8"));
        }
        ItemMeta normalMoneyBoxMeta = normalMoneyBox.getItemMeta();
        String normalMoneyBoxTitle = "§f일반 자산 상자";
        normalMoneyBoxMeta.setDisplayName(currentNormalMoneyBoxAmount > 0 ? normalMoneyBoxTitle : normalMoneyBoxTitle.replace("§f", "§7"));
        if (currentNormalMoneyBoxAmount > 0) {
            ArrayList<String> normalMoneyBoxLore = (ArrayList<String>) normalMoneyBoxMeta.getLore();
            normalMoneyBoxLore.add("");
            normalMoneyBoxLore.add(" §f※ 사용");
            normalMoneyBoxLore.add("");
            normalMoneyBoxLore.add(" §e→ §f<L-Click>: §71개 개봉");
            normalMoneyBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 개봉");
            normalMoneyBoxLore.add("");
            normalMoneyBoxMeta.setLore(normalMoneyBoxLore);
        }
        normalMoneyBox.setItemMeta(normalMoneyBoxMeta);

        // 고급 자산 상자
        ItemStack premiumMoneyBox = variables.getPremiumMoneyBox().clone();
        int currentPremiumMoneyBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, "Premium Money Box");
        if (currentPremiumMoneyBoxAmount >= 127) {
            premiumMoneyBox.setAmount(127);
        } else if (127 > currentPremiumMoneyBoxAmount && currentPremiumMoneyBoxAmount > 0) {
            premiumMoneyBox.setAmount(currentPremiumMoneyBoxAmount);
        } else {
            premiumMoneyBox.setType(Material.STAINED_GLASS);
            premiumMoneyBox.setDurability(Short.parseShort("8"));
        }
        ItemMeta premiumMoneyBoxMeta = premiumMoneyBox.getItemMeta();
        String premiumMoneyBoxTitle = "§f고급 자산 상자";
        premiumMoneyBoxMeta.setDisplayName(currentPremiumMoneyBoxAmount > 0 ? premiumMoneyBoxTitle : premiumMoneyBoxTitle.replace("§f", "§7"));
        if (currentPremiumMoneyBoxAmount > 0) {
            ArrayList<String> premiumMoneyBoxLore = (ArrayList<String>) premiumMoneyBoxMeta.getLore();
            premiumMoneyBoxLore.add("");
            premiumMoneyBoxLore.add(" §f※ 사용");
            premiumMoneyBoxLore.add("");
            premiumMoneyBoxLore.add(" §e→ §f<L-Click>: §71개 개봉");
            premiumMoneyBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 개봉");
            premiumMoneyBoxLore.add("");
            premiumMoneyBoxMeta.setLore(premiumMoneyBoxLore);
        }
        premiumMoneyBox.setItemMeta(premiumMoneyBoxMeta);

        // 마인리스트 상자
        ItemStack minelistBox = variables.getMinelistBox().clone();
        int currentMinelistBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, "Minelist Box");
        if (currentMinelistBoxAmount >= 127) {
            minelistBox.setAmount(127);
        } else if (127 > currentMinelistBoxAmount && currentMinelistBoxAmount > 0) {
            minelistBox.setAmount(currentMinelistBoxAmount);
        } else {
            minelistBox.setType(Material.STAINED_GLASS);
            minelistBox.setDurability(Short.parseShort("8"));
        }
        ItemMeta minelistBoxMeta = minelistBox.getItemMeta();
        String minelistBoxTitle = "§f마인리스트 상자";
        minelistBoxMeta.setDisplayName(currentMinelistBoxAmount > 0 ? minelistBoxTitle : minelistBoxTitle.replace("§f", "§7"));
        if (currentMinelistBoxAmount > 0) {
            ArrayList<String> minelistBoxLore = (ArrayList<String>) minelistBoxMeta.getLore();
            minelistBoxLore.add("");
            minelistBoxLore.add(" §f※ 사용");
            minelistBoxLore.add("");
            minelistBoxLore.add(" §e→ §f<L-Click>: §71개 개봉");
            minelistBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 개봉");
            minelistBoxLore.add("");
            minelistBoxMeta.setLore(minelistBoxLore);
        }
        minelistBox.setItemMeta(minelistBoxMeta);


        inventory.setItem(1, head);

        inventory.setItem(12, normalWeaponBox);
        inventory.setItem(13, specialWeaponBox);
        inventory.setItem(14, premiumWeaponBox);
        inventory.setItem(21, normalMoneyBox);
        inventory.setItem(22, premiumMoneyBox);
        inventory.setItem(23, minelistBox);

        inventory.setItem(10, whitePane);
        inventory.setItem(11, whitePane);
        inventory.setItem(15, whitePane);
        inventory.setItem(16, whitePane);
        inventory.setItem(19, whitePane);
        inventory.setItem(20, whitePane);
        inventory.setItem(24, whitePane);
        inventory.setItem(25, whitePane);

        inventory.setItem(0, blackPane);
        for (int i = 2; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        inventory.setItem(9, blackPane);
        inventory.setItem(17, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(26, blackPane);
        for (int i = 27; i < 36; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new BoxGUI(player, targetPlayer); // BoxGUI 호출
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
                } else if (name.contains("일반 무기 상자")) {
                    String box = "Normal Weapon Box";
                    String boxDisplay = name;

                    int currentBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                    if (currentBoxAmount > 0) { // 상자를 소지하고 있을 경우
                        int amount = event.isShiftClick() ? currentBoxAmount >= 10 ? 10 : currentBoxAmount : 1;

                        for (int i = 0; i < amount; i++) {
                            kr.sizniss.data.Files.subtractBox(targetPlayer, box, 1); // 상자 개수 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + boxDisplay + "§f§l'을(를) 개봉하였습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Open " + box + "]");

                            int randomNumber = (int) (Math.random() * 100);
                            if (55 > randomNumber) { // 동 등급 조각
                                String pieceDisplay = "§6동 §f조각";
                                String pieceGrade = "Bronze";
                                int pieceAmount = 1;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (90 > randomNumber && randomNumber >= 55) { // 은 등급 조각
                                String pieceDisplay = "§f은 조각";
                                String pieceGrade = "Silver";
                                int pieceAmount = 1;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (96 > randomNumber && randomNumber >= 90) { // 동 등급 주 무기
                                int randomCount = (int) (Math.random() * variables.getBronzeMainWeaponName().size());
                                String weapon = variables.getBronzeMainWeaponName().get(randomCount);

                                if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) { // 무기를 소지하고 있지 않을 경우
                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");

                                    if (methods.isNewUser(targetPlayer)) {
                                        ((Player) targetPlayer).sendMessage("");
                                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l무기를 획득하였습니다!");
                                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§e/장비§f§l' 명령어를 통해 무기를 장착하실 수 있습니다!");
                                        ((Player) targetPlayer).sendTitle("§e/장비", "§f무기를 장착하실 수 있습니다!", 5, 100, 5);
                                    }

                                } else { // 무기를 소지하고 있을 경우
                                    String pieceDisplay = "§6동 §f조각";
                                    String pieceGrade = "Bronze";
                                    int pieceAmount = 2;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }
                            } else if (randomNumber >= 96) { // 은 등급 주 무기
                                int randomCount = (int) (Math.random() * variables.getSilverMainWeaponName().size());
                                String weapon = variables.getSilverMainWeaponName().get(randomCount);

                                if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) { // 무기를 소지하고 있지 않을 경우
                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");

                                    if (methods.isNewUser(targetPlayer)) {
                                        ((Player) targetPlayer).sendMessage("");
                                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l무기를 획득하였습니다!");
                                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§e/장비§f§l' 명령어를 통해 무기를 장착하실 수 있습니다!");
                                        ((Player) targetPlayer).sendTitle("§e/장비", "§f무기를 장착하실 수 있습니다!", 5, 100, 5);
                                    }

                                } else { // 무기를 소지하고 있을 경우
                                    String pieceDisplay = "§f은 조각";
                                    String pieceGrade = "Silver";
                                    int pieceAmount = 2;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }
                            }
                        }

                        new BoxGUI(player, targetPlayer); // BoxGUI 호출
                    }
                } else if (name.contains("특별 무기 상자")) {
                    String box = "Special Weapon Box";
                    String boxDisplay = name;

                    int currentBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                    if (currentBoxAmount > 0) { // 상자를 소지하고 있을 경우
                        int amount = event.isShiftClick() ? currentBoxAmount >= 10 ? 10 : currentBoxAmount : 1;

                        for (int i = 0; i < amount; i++) {
                            kr.sizniss.data.Files.subtractBox(targetPlayer, box, 1); // 상자 개수 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + boxDisplay + "§f§l'을(를) 개봉하였습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Open " + box + "]");

                            int randomNumber = (int) (Math.random() * 100);
                            if (31 > randomNumber) { // 동 등급 조각
                                String pieceDisplay = "§6동 §f조각";
                                String pieceGrade = "Bronze";
                                int pieceAmount = 50;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (53 > randomNumber && randomNumber >= 31) { // 은 등급 조각
                                String pieceDisplay = "§f은 조각";
                                String pieceGrade = "Silver";
                                int pieceAmount = 50;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (65 > randomNumber && randomNumber >= 53) { // 금 등급 조각
                                String pieceDisplay = "§e금 §f조각";
                                String pieceGrade = "Gold";
                                int pieceAmount = 5;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (89 > randomNumber && randomNumber >= 65) { // 동 등급 주 무기
                                int randomCount = (int) (Math.random() * variables.getBronzeMainWeaponName().size());
                                String weapon = variables.getBronzeMainWeaponName().get(randomCount);

                                if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) { // 무기를 소지하고 있지 않을 경우
                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");
                                } else { // 무기를 소지하고 있을 경우
                                    String pieceDisplay = "§6동 §f조각";
                                    String pieceGrade = "Bronze";
                                    int pieceAmount = 100;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }
                            } else if (96 > randomNumber && randomNumber >= 89) { // 은 등급 주 무기
                                int randomCount = (int) (Math.random() * variables.getSilverMainWeaponName().size());
                                String weapon = variables.getSilverMainWeaponName().get(randomCount);

                                if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) { // 무기를 소지하고 있지 않을 경우
                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");
                                } else { // 무기를 소지하고 있을 경우
                                    String pieceDisplay = "§f은 조각";
                                    String pieceGrade = "Silver";
                                    int pieceAmount = 100;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }
                            } else if (randomNumber >= 96) { // 금 등급 주 무기
                                ArrayList<String> weapons = new ArrayList<String>();
                                for (String weapon : variables.getGoldMainWeaponName()) {
                                    if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) {
                                        weapons.add(weapon);
                                    }
                                }

                                // 금 무기를 모두 소지하고 있을 경우
                                if (weapons.isEmpty()) {
                                    String pieceDisplay = "§e금 §f조각";
                                    String pieceGrade = "Gold";
                                    int pieceAmount = 10;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }

                                // 금 무기를 모두 소지하고 있지 않을 경우
                                else {
                                    int randomCount = (int) (Math.random() * weapons.size());
                                    String weapon = weapons.get(randomCount);

                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");

                                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                        onlinePlayer.sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 '" + boxDisplay + "§f§l'에서 '§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다!");
                                    }
                                }
                            }
                        }

                        new BoxGUI(player, targetPlayer); // BoxGUI 호출
                    }
                } else if (name.contains("고급 무기 상자")) {
                    String box = "Premium Weapon Box";
                    String boxDisplay = name;

                    int currentBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                    if (currentBoxAmount > 0) { // 상자를 소지하고 있을 경우
                        int amount = event.isShiftClick() ? currentBoxAmount >= 10 ? 10 : currentBoxAmount : 1;

                        for (int i = 0; i < amount; i++) {
                            kr.sizniss.data.Files.subtractBox(targetPlayer, box, 1); // 상자 개수 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + boxDisplay + "§f§l'을(를) 개봉하였습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Open " + box + "]");
                            
                            kr.sizniss.data.Files.addBoxOpenCount(targetPlayer, box, 1); // 상자 개봉 횟수 추가

                            int currentBoxOpenCount = kr.sizniss.data.Files.getBoxOpenCount(targetPlayer, box);
                            int maxBoxOpenCount = variables.getMaxBoxOpenCount();

                            int randomNumber = currentBoxOpenCount >= maxBoxOpenCount ? Manager.methods.getRandomInteger(97, 100) : (int) (Math.random() * 100);
                            if (35 > randomNumber) { // 동 등급 조각
                                String pieceDisplay = "§6동 §f조각";
                                String pieceGrade = "Bronze";
                                int pieceAmount = 2;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (60 > randomNumber && randomNumber >= 35) { // 은 등급 조각
                                String pieceDisplay = "§f은 조각";
                                String pieceGrade = "Silver";
                                int pieceAmount = 2;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (75 > randomNumber && randomNumber >= 60) { // 금 등급 조각
                                String pieceDisplay = "§e금 §f조각";
                                String pieceGrade = "Gold";
                                int pieceAmount = 2;

                                kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                            } else if (90 > randomNumber && randomNumber >= 75) { // 동 등급 주 무기
                                int randomCount = (int) (Math.random() * variables.getBronzeMainWeaponName().size());
                                String weapon = variables.getBronzeMainWeaponName().get(randomCount);

                                if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) { // 무기를 소지하고 있지 않을 경우
                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");
                                } else { // 무기를 소지하고 있을 경우
                                    String pieceDisplay = "§6동 §f조각";
                                    String pieceGrade = "Bronze";
                                    int pieceAmount = 4;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }
                            } else if (97 > randomNumber && randomNumber >= 90) { // 은 등급 주 무기
                                int randomCount = (int) (Math.random() * variables.getSilverMainWeaponName().size());
                                String weapon = variables.getSilverMainWeaponName().get(randomCount);

                                if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) { // 무기를 소지하고 있지 않을 경우
                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");
                                } else { // 무기를 소지하고 있을 경우
                                    String pieceDisplay = "§f은 조각";
                                    String pieceGrade = "Silver";
                                    int pieceAmount = 4;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }
                            } else if (randomNumber >= 97) { // 금 등급 주 무기
                                ArrayList<String> weapons = new ArrayList<String>();
                                for (String weapon : variables.getGoldMainWeaponName()) {
                                    if (!kr.sizniss.data.Files.getMainWeapon(targetPlayer, weapon)) {
                                        weapons.add(weapon);
                                    }
                                }

                                kr.sizniss.data.Files.setBoxOpenCount(targetPlayer, box, 0); // 상자 개봉 횟수 초기화

                                // 금 무기를 모두 소지하고 있을 경우
                                if (weapons.isEmpty()) {
                                    String pieceDisplay = "§e금 §f조각";
                                    String pieceGrade = "Gold";
                                    int pieceAmount = 4;

                                    kr.sizniss.data.Files.addPiece(targetPlayer, pieceGrade, pieceAmount); // 조각 추가

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + pieceDisplay + "(x" + pieceAmount + ")§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + pieceGrade + " piece(x" + pieceAmount + ")]");
                                }

                                // 금 무기를 모두 소지하고 있지 않을 경우
                                else {
                                    int randomCount = (int) (Math.random() * weapons.size());
                                    String weapon = weapons.get(randomCount);

                                    kr.sizniss.data.Files.setMainWeapon(targetPlayer, weapon, true); // 무기 지급

                                    ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다.");
                                    Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + weapon.replace("_", " ") + "]");

                                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                        onlinePlayer.sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 '" + boxDisplay + "§f§l'에서 '§f" + weapon.replace("_", " ") + "§f§l'을(를) 획득하셨습니다!");
                                    }
                                }
                            }
                        }

                        new BoxGUI(player, targetPlayer); // BoxGUI 호출
                    }
                } else if (name.contains("일반 자산 상자")) {
                    String box = "Normal Money Box";
                    String boxDisplay = name;

                    int currentBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                    if (currentBoxAmount > 0) { // 상자를 소지하고 있을 경우
                        int amount = event.isShiftClick() ? currentBoxAmount >= 10 ? 10 : currentBoxAmount : 1;

                        for (int i = 0; i < amount; i++) {
                            kr.sizniss.data.Files.subtractBox(targetPlayer, box, 1); // 상자 개수 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + boxDisplay + "§f§l'을(를) 개봉하였습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Open " + box + "]");

                            int gold = (int) (Math.random() * 10000) + 1;

                            kr.sizniss.data.Files.addMoney(targetPlayer, "Gold", gold); // 자산 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§e" + new DecimalFormat("###,###").format(gold) + " 골드§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + new DecimalFormat("###,###").format(gold) + " gold]");
                        }

                        new BoxGUI(player, targetPlayer); // BoxGUI 호출
                    }
                } else if (name.contains("고급 자산 상자")) {
                    String box = "Premium Money Box";
                    String boxDisplay = name;

                    int currentBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                    if (currentBoxAmount > 0) { // 상자를 소지하고 있을 경우
                        int amount = event.isShiftClick() ? currentBoxAmount >= 10 ? 10 : currentBoxAmount : 1;

                        for (int i = 0; i < amount; i++) {
                            kr.sizniss.data.Files.subtractBox(targetPlayer, box, 1); // 상자 개수 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + boxDisplay + "§f§l'을(를) 개봉하였습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Open " + box + "]");

                            int diamond = (int) (Math.random() * 1000) + 1;

                            kr.sizniss.data.Files.addMoney(targetPlayer, "Diamond", diamond); // 자산 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§b" + new DecimalFormat("###,###").format(diamond) + " 다이아몬드§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + new DecimalFormat("###,###").format(diamond) + " diamond]");

                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                onlinePlayer.sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 '" + boxDisplay + "§f§l'에서 '§b" + new DecimalFormat("###,###").format(diamond) + " 다이아몬드§f§l'을(를) 획득하셨습니다!");
                            }
                        }

                        new BoxGUI(player, targetPlayer); // BoxGUI 호출
                    }
                } else if (name.contains("마인리스트 상자")) {
                    String box = "Minelist Box";
                    String boxDisplay = name;

                    int currentBoxAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                    if (currentBoxAmount > 0) { // 상자를 소지하고 있을 경우
                        int amount = event.isShiftClick() ? currentBoxAmount >= 10 ? 10 : currentBoxAmount : 1;

                        for (int i = 0; i < amount; i++) {
                            kr.sizniss.data.Files.subtractBox(targetPlayer, box, 1); // 상자 개수 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + boxDisplay + "§f§l'을(를) 개봉하였습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Open " + box + "]");

                            int emerald = 200;

                            kr.sizniss.data.Files.addMoney(targetPlayer, "Emerald", emerald); // 자산 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§a" + emerald + " 에메랄드§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + emerald + " emerald]");

                            int randomNumber = (int) (Math.random() * 100);
                            if (90 > randomNumber) { // 일반 자산 상자
                                String product = "Normal Money Box";
                                String productDisplay = "§f일반 자산 상자";

                                kr.sizniss.data.Files.addBox(targetPlayer, product, 1); // 상자 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + product + "]");
                            } else if (randomNumber >= 90) { // 고급 자산 상자
                                String product = "Premium Money Box";
                                String productDisplay = "§f고급 자산 상자";

                                kr.sizniss.data.Files.addBox(targetPlayer, product, 1); // 상자 추가

                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Get " + product + "]");

                                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                    onlinePlayer.sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 '" + boxDisplay + "§f§l'에서 '§f" + productDisplay + "§f§l'을(를) 획득하셨습니다!");
                                }
                            }
                        }

                        new BoxGUI(player, targetPlayer); // BoxGUI 호출
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
