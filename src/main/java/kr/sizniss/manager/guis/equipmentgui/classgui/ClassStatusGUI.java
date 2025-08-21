package kr.sizniss.manager.guis.equipmentgui.classgui;

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

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class ClassStatusGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;
    private String kind;

    private int task; // 갱신 테스크


    public ClassStatusGUI(Player player, OfflinePlayer targetPlayer, String kind) {
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.kind = kind;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "장비 - 병과 - 능력치 분배"); // 인벤토리 생성

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

        // 하얀색 양털
        ItemStack whiteWool = new ItemStack(Material.WOOL, 1, Short.parseShort("0"));
        ItemMeta whiteWoolMeta = whiteWool.getItemMeta();
        whiteWoolMeta.setDisplayName("§f");
        whiteWool.setItemMeta(whiteWoolMeta);

        // 연두색 양털
        ItemStack greenWool = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
        ItemMeta greenWoolMeta = greenWool.getItemMeta();
        greenWoolMeta.setDisplayName("§f");
        greenWool.setItemMeta(greenWoolMeta);


        // 병과
        ItemStack kind = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("0"));
        ItemMeta kindMeta = kind.getItemMeta();
        kindMeta.setDisplayName("§f" + this.kind);
        ArrayList<Integer> experience = variables.getClassExperience();
        // int playCount = kr.sizniss.data.Files.getClassPlayCount(targetPlayer, this.kind);
        int playCount = 0;
        for (String kinds : variables.getClassName()) {
            playCount += kr.sizniss.data.Files.getClassPlayCount(player, kinds);
        }
        int level = Manager.methods.getClassLevel(targetPlayer, this.kind);
        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, this.kind);
        ArrayList<String> kindLore = new ArrayList<String>();
        kindLore.add("");
        kindLore.add("§f레벨: §7" + level + "§f/§7" + experience.size());
        if (level < experience.size()) {
            kindLore.add("§f경험치: §7" + (playCount - Manager.methods.getSumElement(experience, level)) + "§f/§7" + experience.get(level));
        } else {
            kindLore.add("§f경험치: §7MAX");
        }
        kindLore.add("");
        kindLore.add("§f잔여 포인트: §7" + remainingPoint);
        kindMeta.setLore(kindLore);
        kind.setItemMeta(kindMeta);

        // 능력치 초기화
        ItemStack resetStatus = new ItemStack(Material.BARRIER);
        ItemMeta resetStatusMeta = resetStatus.getItemMeta();
        resetStatusMeta.setDisplayName("§c능력치 초기화");
        ArrayList<String> resetStatusLore = new ArrayList<String>();
        resetStatusLore.add("");
        resetStatusLore.add("§f비용: §e" + new DecimalFormat("###,###").format((level - remainingPoint) * 2500) + " 골드");
        resetStatusMeta.setLore(resetStatusLore);
        resetStatus.setItemMeta(resetStatusMeta);

        // 최대 체력
        ItemStack maxHealth = new ItemStack(Material.LEATHER);
        ItemMeta maxHealthMeta = maxHealth.getItemMeta();
        maxHealthMeta.setDisplayName("§f최대 체력");
        int maxHealthLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "최대_체력");
        if (maxHealthLevel < 5) {
            ArrayList<String> maxHealthLore = new ArrayList<String>();
            maxHealthLore.add("");
            maxHealthLore.add("§f비용: §7" + 1 + " 포인트");
            maxHealthMeta.setLore(maxHealthLore);
        }
        maxHealth.setItemMeta(maxHealthMeta);
        for (int i = 0; i < maxHealthLevel; i++) {
            inventory.setItem(38 - (i * 9), greenWool);
        }
        for (int i = 4; i >= maxHealthLevel; i--) {
            inventory.setItem(38 - (i * 9), whiteWool);
        }

        // 재생
        ItemStack regeneration = new ItemStack(Material.INK_SACK, 1, Short.parseShort("1"));
        ItemMeta regenerationMeta = regeneration.getItemMeta();
        regenerationMeta.setDisplayName("§f재생");
        int regenerationLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "재생");
        if (regenerationLevel < 2) {
            ArrayList<String> regenerationLore = new ArrayList<String>();
            regenerationLore.add("");
            regenerationLore.add("§f비용: §7" + 1 + " 포인트");
            regenerationMeta.setLore(regenerationLore);
        }
        regeneration.setItemMeta(regenerationMeta);
        for (int i = 0; i < regenerationLevel; i++) {
            inventory.setItem(39 - (i * 9), greenWool);
        }
        for (int i = 2; i >= regenerationLevel; i--) {
            inventory.setItem(39 - (i * 9), whiteWool);
        }
        for (int i = 3; i < 30; i += 9) {
            inventory.setItem(i, grayPane);
        }

        // 이동 속도
        ItemStack moveSpeed = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta moveSpeedMeta = moveSpeed.getItemMeta();
        moveSpeedMeta.setDisplayName("§f이동 속도");
        int moveSpeedLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "이동_속도");
        if (moveSpeedLevel < 5) {
            ArrayList<String> moveSpeedLore = new ArrayList<String>();
            moveSpeedLore.add("");
            moveSpeedLore.add("§f비용: §7" + 1 + " 포인트");
            moveSpeedMeta.setLore(moveSpeedLore);
        }
        moveSpeed.setItemMeta(moveSpeedMeta);
        for (int i = 0; i < moveSpeedLevel; i++) {
            inventory.setItem(40 - (i * 9), greenWool);
        }
        for (int i = 4; i >= moveSpeedLevel; i--) {
            inventory.setItem(40 - (i * 9), whiteWool);
        }

        // 아이템 보유 개수
        ItemStack itemAmount = new ItemStack(Material.PRISMARINE_CRYSTALS);
        ItemMeta itemAmountMeta = itemAmount.getItemMeta();
        itemAmountMeta.setDisplayName("§f아이템 보유 개수");
        int itemAmountLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "아이템_보유_개수");
        if (itemAmountLevel < 2) {
            ArrayList<String> itemAmountLore = new ArrayList<String>();
            itemAmountLore.add("");
            itemAmountLore.add("§f비용: §7" + 2 + " 포인트");
            itemAmountMeta.setLore(itemAmountLore);
        }
        itemAmount.setItemMeta(itemAmountMeta);
        for (int i = 0; i < itemAmountLevel ; i++) {
            inventory.setItem(41 - (i * 9), greenWool);
        }
        for (int i = 1; i >= itemAmountLevel ; i--) {
            inventory.setItem(41 - (i * 9), whiteWool);
        }
        for (int i = 5; i < 41; i += 9) {
            inventory.setItem(i, grayPane);
        }

        ItemStack hiddenStat = new ItemStack(Material.COMPASS);
        ItemMeta hiddenStatMeta;
        int hiddenStatLevel;
        ArrayList<String> hiddenStatLore;
        switch (this.kind) {
            /*
            case "보병":
            case "교란병":
                // 아이템 지속 시간
                hiddenStat = new ItemStack(Material.COMPASS);
                hiddenStatMeta = hiddenStat.getItemMeta();
                hiddenStatMeta.setDisplayName("§f아이템 지속 시간");
                hiddenStatLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "아이템_지속_시간");
                if (hiddenStatLevel < 5) {
                    hiddenStatLore = new ArrayList<String>();
                    hiddenStatLore.add("");
                    hiddenStatLore.add("§f비용: §7" + 1 + " 포인트");
                    hiddenStatMeta.setLore(hiddenStatLore);
                }
                hiddenStat.setItemMeta(hiddenStatMeta);
                for (int i = 0; i < hiddenStatLevel ; i++) {
                    inventory.setItem(42 - (i * 9), greenWool);
                }
                for (int i = 4; i >= hiddenStatLevel ; i--) {
                    inventory.setItem(42 - (i * 9), whiteWool);
                }
                break;
             */
            case "보병":
            case "포병":
            case "교란병":
            case "의무병":
            case "기갑병":
            case "보급병":
            case "공병":
                // 추가 탄창
                hiddenStat = new ItemStack(Material.IRON_INGOT);
                hiddenStatMeta = hiddenStat.getItemMeta();
                hiddenStatMeta.setDisplayName("§f추가 탄창");
                hiddenStatLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "추가_탄창");
                if (hiddenStatLevel < 1) {
                    hiddenStatLore = new ArrayList<String>();
                    hiddenStatLore.add("");
                    hiddenStatLore.add("§f비용: §7" + 2 + " 포인트");
                    hiddenStatMeta.setLore(hiddenStatLore);
                }
                hiddenStat.setItemMeta(hiddenStatMeta);
                for (int i = 0; i < hiddenStatLevel ; i++) {
                    inventory.setItem(42 - (i * 9), greenWool);
                }
                for (int i = 1; i >= hiddenStatLevel ; i--) {
                    inventory.setItem(42 - (i * 9), whiteWool);
                }
                for (int i = 6; i < 42; i += 9) {
                    inventory.setItem(i, grayPane);
                }
                break;
                /*
            case "기갑병":
                // 아이템 효과 레벨
                hiddenStat = new ItemStack(Material.EXP_BOTTLE);
                hiddenStatMeta = hiddenStat.getItemMeta();
                hiddenStatMeta.setDisplayName("§f아이템 효과 레벨");
                hiddenStatLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, this.kind, "아이템_효과_레벨");
                if (hiddenStatLevel < 2) {
                    hiddenStatLore = new ArrayList<String>();
                    hiddenStatLore.add("");
                    hiddenStatLore.add("§f비용: §7" + 1 + " 포인트");
                    hiddenStatMeta.setLore(hiddenStatLore);
                }
                hiddenStat.setItemMeta(hiddenStatMeta);
                for (int i = 0; i < hiddenStatLevel ; i++) {
                    inventory.setItem(42 - (i * 9), greenWool);
                }
                for (int i = 2; i >= hiddenStatLevel ; i--) {
                    inventory.setItem(42 - (i * 9), whiteWool);
                }
                for (int i = 6; i < 33; i += 9) {
                    inventory.setItem(i, grayPane);
                }
                break;
                 */
        }


        inventory.setItem(9, head);

        inventory.setItem(27, kind);
        inventory.setItem(36, resetStatus);

        inventory.setItem(47, maxHealth);
        inventory.setItem(48, regeneration);
        inventory.setItem(49, moveSpeed);
        inventory.setItem(50, itemAmount);
        inventory.setItem(51, hiddenStat);

        for (int i = 1; i < 54; i += 9) {
            inventory.setItem(i, whitePane);
        }
        for (int i = 7; i < 54; i += 9) {
            inventory.setItem(i, whitePane);
        }

        inventory.setItem(0, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(45, blackPane);
        for (int i = 8; i < 54; i += 9) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new ClassStatusGUI(player, targetPlayer, this.kind); // ClassStatusGUI 호출
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
                if (name.contains("능력치 초기화")) {

                    int level = Manager.methods.getClassLevel(targetPlayer, kind);
                    int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);

                    if (level > remainingPoint) { // 병과 레벨이 잔여 포인트보다 높을 경우

                        int currentGold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = (level - remainingPoint) * 2500;

                        if (currentGold >= price) { // 소지하고 있는 골드가 비용보다 클 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 골드 차감

                            // 능력치 초기화
                            kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "최대_체력", 0);
                            kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "재생", 0);
                            kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "이동_속도", 0);
                            kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "아이템_보유_개수", 0);
                            switch (kind) {
                                /*
                                case "보병":
                                case "교란병":
                                    kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "아이템_지속_시간", 0);
                                    break;
                                 */
                                case "보병":
                                case "포병":
                                case "교란병":
                                case "의무병":
                                case "기갑병":
                                case "보급병":
                                case "공병":
                                    kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "추가_탄창", 0);
                                    break;
                                    /*
                                case "기갑병":
                                    kr.sizniss.data.Files.setClassStatus(targetPlayer, kind, "아이템_효과_레벨", 0);
                                    break;
                                     */
                            }

                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Reset " + kind + " class status]");

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                else if (name.contains("최대 체력")) {
                    int maxHealthLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "최대_체력");
                    if (maxHealthLevel < 5) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "최대_체력", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                else if (name.contains("재생")) {
                    int regenerationLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "재생");
                    if (regenerationLevel < 2) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "재생", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                else if (name.contains("이동 속도")) {
                    int moveSpeedLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "이동_속도");
                    if (moveSpeedLevel < 5) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "이동_속도", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                else if (name.contains("아이템 보유 개수")) {
                    int itemAmountLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "아이템_보유_개수");
                    if (itemAmountLevel < 1) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 2) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "아이템_보유_개수", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                /*
                else if (name.contains("아이템 지속 시간")) {
                    int durationLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "아이템_지속_시간");
                    if (durationLevel < 5) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "아이템_지속_시간", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                 */
                else if (name.contains("추가 탄창")) {
                    int ammoAmountLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "추가_탄창");
                    if (ammoAmountLevel < 1) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 2) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "추가_탄창", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                /*
                else if (name.contains("아이템 효과 레벨")) {
                    int effectLevel = kr.sizniss.data.Files.getClassStatus(targetPlayer, kind, "아이템_효과_레벨");
                    if (effectLevel < 2) {
                        int remainingPoint = Manager.methods.getClassRemainingPoint(targetPlayer, kind);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addClassStatus(targetPlayer, kind, "아이템_효과_레벨", 1); // 능력치 분배

                            new ClassStatusGUI(player, targetPlayer, kind); // ClassStatusGUI 호출
                        }
                    }
                }
                 */

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
