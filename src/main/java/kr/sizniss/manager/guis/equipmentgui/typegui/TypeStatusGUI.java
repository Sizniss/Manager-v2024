package kr.sizniss.manager.guis.equipmentgui.typegui;

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

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class TypeStatusGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;
    private String type;

    private int task; // 갱신 테스크


    public TypeStatusGUI(Player player, OfflinePlayer targetPlayer, String type) {
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.type = type;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "장비 - 타입 - 능력치 분배"); // 인벤토리 생성

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


        // 타입
        ItemStack type = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("2"));
        ItemMeta typeMeta = type.getItemMeta();
        typeMeta.setDisplayName("§f" + this.type);
        ArrayList<Integer> experience = variables.getTypeExperience();
        // int playCount = kr.sizniss.data.Files.getTypePlayCount(targetPlayer, this.type);
        int playCount = 0;
        for (String types : variables.getTypeName()) {
            playCount += kr.sizniss.data.Files.getTypePlayCount(player, types);
        }
        int level = Manager.methods.getTypeLevel(targetPlayer, this.type);
        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, this.type);
        ArrayList<String> typeLore = new ArrayList<String>();
        typeLore.add("");
        typeLore.add("§f레벨: §7" + level + "§f/§7" + experience.size());
        if (level < experience.size()) {
            typeLore.add("§f경험치: §7" + (playCount - Manager.methods.getSumElement(experience, level)) + "§f/§7" + experience.get(level));
        } else {
            typeLore.add("§f경험치: §7MAX");
        }
        typeLore.add("");
        typeLore.add("§f잔여 포인트: §7" + remainingPoint);
        typeMeta.setLore(typeLore);
        type.setItemMeta(typeMeta);

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
        int maxHealthLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "최대_체력");
        if (maxHealthLevel < 5) {
            ArrayList<String> maxHealthLore = new ArrayList<String>();
            maxHealthLore.add("");
            maxHealthLore.add("§f비용: §7" + 1 + " 포인트");
            maxHealthMeta.setLore(maxHealthLore);
        }
        maxHealth.setItemMeta(maxHealthMeta);
        for (int i = 0; i < maxHealthLevel; i++) {
            inventory.setItem(37 - (i * 9), greenWool);
        }
        for (int i = 4; i >= maxHealthLevel; i--) {
            inventory.setItem(37 - (i * 9), whiteWool);
        }

        // 재생
        ItemStack regeneration = new ItemStack(Material.INK_SACK, 1, Short.parseShort("1"));
        ItemMeta regenerationMeta = regeneration.getItemMeta();
        regenerationMeta.setDisplayName("§f재생");
        int regenerationLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "재생");
        if (regenerationLevel < 2) {
            ArrayList<String> regenerationLore = new ArrayList<String>();
            regenerationLore.add("");
            regenerationLore.add("§f비용: §7" + 1 + " 포인트");
            regenerationMeta.setLore(regenerationLore);
        }
        regeneration.setItemMeta(regenerationMeta);
        for (int i = 0; i < regenerationLevel; i++) {
            inventory.setItem(38 - (i * 9), greenWool);
        }
        for (int i = 2; i >= regenerationLevel; i--) {
            inventory.setItem(38 - (i * 9), whiteWool);
        }
        for (int i = 2; i < 29; i += 9) {
            inventory.setItem(i, grayPane);
        }

        // 이동 속도
        ItemStack moveSpeed = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta moveSpeedMeta = moveSpeed.getItemMeta();
        moveSpeedMeta.setDisplayName("§f이동 속도");
        int moveSpeedLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "이동_속도");
        if (moveSpeedLevel < 5) {
            ArrayList<String> moveSpeedLore = new ArrayList<String>();
            moveSpeedLore.add("");
            moveSpeedLore.add("§f비용: §7" + 1 + " 포인트");
            moveSpeedMeta.setLore(moveSpeedLore);
        }
        moveSpeed.setItemMeta(moveSpeedMeta);
        for (int i = 0; i < moveSpeedLevel; i++) {
            inventory.setItem(39 - (i * 9), greenWool);
        }
        for (int i = 4; i >= moveSpeedLevel; i--) {
            inventory.setItem(39 - (i * 9), whiteWool);
        }

        // 점프 강화
        ItemStack jump = new ItemStack(Material.FEATHER);
        ItemMeta jumpMeta = jump.getItemMeta();
        jumpMeta.setDisplayName("§f점프 강화");
        int jumpLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "점프_강화");
        if (jumpLevel < 2) {
            ArrayList<String> jumpLore = new ArrayList<String>();
            jumpLore.add("");
            jumpLore.add("§f비용: §7" + 1 + " 포인트");
            jumpMeta.setLore(jumpLore);
        }
        jump.setItemMeta(jumpMeta);
        for (int i = 0; i < jumpLevel; i++) {
            inventory.setItem(40 - (i * 9), greenWool);
        }
        for (int i = 2; i >= jumpLevel; i--) {
            inventory.setItem(40 - (i * 9), whiteWool);
        }
        for (int i = 4; i < 31; i += 9) {
            inventory.setItem(i, grayPane);
        }

        // 도약 재사용 대기 시간
        ItemStack leapCooldown = new ItemStack(Material.RAW_FISH, 1, Short.parseShort("3"));
        ItemMeta leapCooldownMeta = leapCooldown.getItemMeta();
        leapCooldownMeta.setDisplayName("§f도약 재사용 대기 시간");
        int leapCooldownLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "도약_재사용_대기_시간");
        if (leapCooldownLevel < 2) {
            ArrayList<String> leapCooldownLore = new ArrayList<String>();
            leapCooldownLore.add("");
            leapCooldownLore.add("§f비용: §7" + 1 + " 포인트");
            leapCooldownMeta.setLore(leapCooldownLore);
        }
        leapCooldown.setItemMeta(leapCooldownMeta);
        for (int i = 0; i < leapCooldownLevel; i++) {
            inventory.setItem(41 - (i * 9), greenWool);
        }
        for (int i = 2; i >= leapCooldownLevel; i--) {
            inventory.setItem(41 - (i * 9), whiteWool);
        }
        for (int i = 5; i < 32; i += 9) {
            inventory.setItem(i, grayPane);
        }

        // 능력 재사용 대기 시간
        ItemStack abilityCooldown = new ItemStack(Material.WATCH);
        ItemMeta abilityCooldownMeta = abilityCooldown.getItemMeta();
        abilityCooldownMeta.setDisplayName("§f능력 재사용 대기 시간");
        int abilityCooldownLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "능력_재사용_대기_시간");
        if (abilityCooldownLevel < 5) {
            ArrayList<String> abilityCooldownLore = new ArrayList<String>();
            abilityCooldownLore.add("");
            abilityCooldownLore.add("§f비용: §7" + 1 + " 포인트");
            abilityCooldownMeta.setLore(abilityCooldownLore);
        }
        abilityCooldown.setItemMeta(abilityCooldownMeta);
        for (int i = 0; i < abilityCooldownLevel; i++) {
            inventory.setItem(42 - (i * 9), greenWool);
        }
        for (int i = 4; i >= abilityCooldownLevel; i--) {
            inventory.setItem(42 - (i * 9), whiteWool);
        }

        ItemStack hiddenStat = new ItemStack(Material.COMPASS);
        ItemMeta hiddenStatMeta;
        int hiddenStatLevel;
        ArrayList<String> hiddenStatLore;
        switch (this.type) {
            case "버서커":
            case "바드":
            case "나이트":
            case "워리어":
            case "로그":
            case "어쌔신":
                // 능력 지속 시간
                hiddenStat = new ItemStack(Material.COMPASS);
                hiddenStatMeta = hiddenStat.getItemMeta();
                hiddenStatMeta.setDisplayName("§f능력 지속 시간");
                hiddenStatLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "능력_지속_시간");
                if (hiddenStatLevel < 5) {
                    hiddenStatLore = new ArrayList<String>();
                    hiddenStatLore.add("");
                    hiddenStatLore.add("§f비용: §7" + 1 + " 포인트");
                    hiddenStatMeta.setLore(hiddenStatLore);
                }
                hiddenStat.setItemMeta(hiddenStatMeta);
                for (int i = 0; i < hiddenStatLevel; i++) {
                    inventory.setItem(43 - (i * 9), greenWool);
                }
                for (int i = 4; i >= hiddenStatLevel; i--) {
                    inventory.setItem(43 - (i * 9), whiteWool);
                }
                break;
            case "네크로맨서":
            case "레인저":
            case "버스터":
            case "서머너":
                // 능력 사용 횟수
                hiddenStat = new ItemStack(Material.PRISMARINE_CRYSTALS);
                hiddenStatMeta = hiddenStat.getItemMeta();
                hiddenStatMeta.setDisplayName("§f능력 사용 횟수");
                hiddenStatLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "능력_사용_횟수");
                if (hiddenStatLevel < 2) {
                    hiddenStatLore = new ArrayList<String>();
                    hiddenStatLore.add("");
                    hiddenStatLore.add("§f비용: §7" + 2 + " 포인트");
                    hiddenStatMeta.setLore(hiddenStatLore);
                }
                hiddenStat.setItemMeta(hiddenStatMeta);
                for (int i = 0; i < hiddenStatLevel; i++) {
                    inventory.setItem(43 - (i * 9), greenWool);
                }
                for (int i = 2; i >= hiddenStatLevel; i--) {
                    inventory.setItem(43 - (i * 9), whiteWool);
                }
                for (int i = 7; i < 34; i += 9) {
                    inventory.setItem(i, grayPane);
                }
                break;
            case "헌터":
                // 능력 효과 범위
                hiddenStat = new ItemStack(Material.DOUBLE_PLANT);
                hiddenStatMeta = hiddenStat.getItemMeta();
                hiddenStatMeta.setDisplayName("§f능력 효과 범위");
                hiddenStatLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, this.type, "능력_효과_범위");
                if (hiddenStatLevel < 2) {
                    hiddenStatLore = new ArrayList<String>();
                    hiddenStatLore.add("");
                    hiddenStatLore.add("§f비용: §7" + 1 + " 포인트");
                    hiddenStatMeta.setLore(hiddenStatLore);
                }
                hiddenStat.setItemMeta(hiddenStatMeta);
                for (int i = 0; i < hiddenStatLevel; i++) {
                    inventory.setItem(43 - (i * 9), greenWool);
                }
                for (int i = 2; i >= hiddenStatLevel; i--) {
                    inventory.setItem(43 - (i * 9), whiteWool);
                }
                for (int i = 7; i < 34; i += 9) {
                    inventory.setItem(i, grayPane);
                }
                break;
        }


        inventory.setItem(9, head);

        inventory.setItem(27, type);
        inventory.setItem(36, resetStatus);

        inventory.setItem(46, maxHealth);
        inventory.setItem(47, regeneration);
        inventory.setItem(48, moveSpeed);
        inventory.setItem(49, jump);
        inventory.setItem(50, leapCooldown);
        inventory.setItem(51, abilityCooldown);
        inventory.setItem(52, hiddenStat);

        inventory.setItem(0, blackPane);
        inventory.setItem(18, blackPane);
        inventory.setItem(45, blackPane);
        for (int i = 8; i < 54; i += 9) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new TypeStatusGUI(player, targetPlayer, this.type); // TypeStatusGUI 호출
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

                    int level = Manager.methods.getTypeLevel(targetPlayer, type);
                    int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);

                    if (level > remainingPoint) { // 타입 레벨이 잔여 포인트보다 높을 경우

                        int currentGold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = (level - remainingPoint) * 2500;

                        if (currentGold >= price) { // 소지하고 있는 골드가 비용보다 클 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 골드 차감

                            // 능력치 초기화
                            kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "최대_체력", 0);
                            kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "재생", 0);
                            kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "이동_속도", 0);
                            kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "점프_강화", 0);
                            kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "도약_재사용_대기_시간", 0);
                            kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "능력_재사용_대기_시간", 0);
                            switch (type) {
                                case "버서커":
                                case "바드":
                                case "나이트":
                                case "워리어":
                                case "로그":
                                case "어쌔신":
                                    kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "능력_지속_시간", 0);
                                    break;
                                case "네크로맨서":
                                case "레인저":
                                case "버스터":
                                case "서머너":
                                    kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "능력_사용_횟수", 0);
                                    break;
                                case "헌터":
                                    kr.sizniss.data.Files.setTypeStatus(targetPlayer, type, "능력_효과_범위", 0);
                                    break;
                            }

                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Reset " + type + " type status]");

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("최대 체력")) {
                    int maxHealthLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "최대_체력");
                    if (maxHealthLevel < 5) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "최대_체력", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("재생")) {
                    int regenerationLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "재생");
                    if (regenerationLevel < 2) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "재생", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("이동 속도")) {
                    int moveSpeedLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "이동_속도");
                    if (moveSpeedLevel < 5) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "이동_속도", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("점프 강화")) {
                    int jumpLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "점프_강화");
                    if (jumpLevel < 2) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "점프_강화", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("도약 재사용 대기 시간")) {
                    int leapCooldownLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "도약_재사용_대기_시간");
                    if (leapCooldownLevel < 2) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "도약_재사용_대기_시간", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("능력 재사용 대기 시간")) {
                    int abilityCooldownLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "능력_재사용_대기_시간");
                    if (abilityCooldownLevel < 5) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "능력_재사용_대기_시간", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("능력 지속 시간")) {
                    int abilityDurationLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "능력_지속_시간");
                    if (abilityDurationLevel < 5) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "능력_지속_시간", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("능력 사용 횟수")) {
                    int abilityAmountLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "능력_사용_횟수");
                    if (abilityAmountLevel < 2) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 2) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "능력_사용_횟수", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
                        }
                    }
                } else if (name.contains("능력 효과 범위")) {
                    int abilityRadiusLevel = kr.sizniss.data.Files.getTypeStatus(targetPlayer, type, "능력_효과_범위");
                    if (abilityRadiusLevel < 2) {
                        int remainingPoint = Manager.methods.getTypeRemainingPoint(targetPlayer, type);
                        if (remainingPoint >= 1) {
                            kr.sizniss.data.Files.addTypeStatus(targetPlayer, type, "능력_효과_범위", 1); // 능력치 분배

                            new TypeStatusGUI(player, targetPlayer, type); // TypeStatusGUI 호출
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
