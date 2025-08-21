package kr.sizniss.manager.guis;

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

import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class RuleGUI {

    private Inventory inventory;

    private Player player;


    public RuleGUI(Player player) {
        this.player = player;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "규칙"); // 인벤토리 생성

        // 검은색 색유리 판
        ItemStack blackPane = variables.getBlackPane().clone();

        // 하얀색 색유리 판
        ItemStack whitePane = variables.getWhitePane().clone();

        // 회색 색유리 판
        ItemStack grayPane = variables.getGrayPane().clone();


        // 레일
        ItemStack rail = new ItemStack(Material.RAILS);
        ItemMeta railMeta = rail.getItemMeta();
        railMeta.setDisplayName("§f");
        rail.setItemMeta(railMeta);


        // 서버 규칙
        ItemStack rule = new ItemStack(Material.BOOK);
        ItemMeta ruleMeta = rule.getItemMeta();
        ruleMeta.setDisplayName("§f서버 규칙");
        rule.setItemMeta(ruleMeta);

        // 접속 클라이언트
        ItemStack connectionClient = new ItemStack(Material.MAP);
        ItemMeta connectionClientMeta = connectionClient.getItemMeta();
        connectionClientMeta.setDisplayName("§f1. 접속 클라이언트");
        connectionClient.setItemMeta(connectionClientMeta);

        // 대화
        ItemStack communication = new ItemStack(Material.MAP);
        ItemMeta communicationMeta = connectionClient.getItemMeta();
        communicationMeta.setDisplayName("§f2. 대화");
        communication.setItemMeta(communicationMeta);

        // 플레이
        ItemStack playing = new ItemStack(Material.MAP);
        ItemMeta playingMeta = connectionClient.getItemMeta();
        playingMeta.setDisplayName("§f3. 플레이");
        playing.setItemMeta(playingMeta);

        // 운영
        ItemStack management = new ItemStack(Material.MAP);
        ItemMeta managementMeta = connectionClient.getItemMeta();
        managementMeta.setDisplayName("§f4. 운영");
        management.setItemMeta(managementMeta);


        // 1-1
        ItemStack rule1_1 = new ItemStack(Material.PAPER);
        ItemMeta rule1_1Meta = rule1_1.getItemMeta();
        rule1_1Meta.setDisplayName("§f1-1. 비인가 클라이언트 사용");
        ArrayList<String> rule1_1Lore = new ArrayList<String>();
        rule1_1Lore.add("    §f또는 허용되지 않은 모드 사용");
        rule1_1Lore.add("    §f금지");
        rule1_1Lore.add("");
        rule1_1Lore.add("§7이동 보조, 전투 보조(PVP 보정, 체력");
        rule1_1Lore.add("§7확인), 위치 노출, 자동 동작 등의");
        rule1_1Lore.add("§7기능이 있는 클라이언트 혹은 모드의");
        rule1_1Lore.add("§7사용을 금지한다.");
        rule1_1Lore.add("");
        rule1_1Lore.add("§7(예시: 핵 클라이언트, 매크로");
        rule1_1Lore.add("§7프로그램, SmartMoving 모드, Neat");
        rule1_1Lore.add("§7모드, 미니맵 모드 등)");
        rule1_1Lore.add("");
        rule1_1Meta.setLore(rule1_1Lore);
        rule1_1.setItemMeta(rule1_1Meta);

        // 1-2
        ItemStack rule1_2 = new ItemStack(Material.PAPER);
        ItemMeta rule1_2Meta = rule1_2.getItemMeta();
        rule1_2Meta.setDisplayName("§f1-2. 악의적인 리소스팩 수정 금지");
        ArrayList<String> rule1_2Lore = new ArrayList<String>();
        rule1_2Lore.add("");
        rule1_2Lore.add("§7불공정한 플레이를 유발할 수 있는");
        rule1_2Lore.add("§7리소스팩의 수정을 금지한다.");
        rule1_2Lore.add("");
        rule1_2Lore.add("§7(예시: 블럭에 구멍 뚫기, 입자 수정");
        rule1_2Lore.add("§7등)");
        rule1_2Lore.add("");
        rule1_2Meta.setLore(rule1_2Lore);
        rule1_2.setItemMeta(rule1_2Meta);

        // 1-3
        ItemStack rule1_3 = new ItemStack(Material.PAPER);
        ItemMeta rule1_3Meta = rule1_3.getItemMeta();
        rule1_3Meta.setDisplayName("§f1-3. 서버 취약점 악용 금지");
        ArrayList<String> rule1_3Lore = new ArrayList<String>();
        rule1_3Lore.add("");
        rule1_3Lore.add("§7서버 취약점을 악용하여 불공정한");
        rule1_3Lore.add("§7플레이 유발 혹은 부당한 이익을");
        rule1_3Lore.add("§7취하는 것을 금지한다.");
        rule1_3Lore.add("");
        rule1_3Lore.add("§7(예시: *체인지샷 등)");
        rule1_3Lore.add("");
        rule1_3Lore.add("§7*체인지샷: 무기를 쏜 후 다른 무기로");
        rule1_3Lore.add("         §0. §7변경하면서 발생하는 버그");
        rule1_3Lore.add("         §0. §7현상");
        rule1_3Lore.add("");
        rule1_3Meta.setLore(rule1_3Lore);
        rule1_3.setItemMeta(rule1_3Meta);

        // 2-1
        ItemStack rule2_1 = new ItemStack(Material.PAPER);
        ItemMeta rule2_1Meta = rule2_1.getItemMeta();
        rule2_1Meta.setDisplayName("§f2-1. 부적절한 언어 사용 금지");
        ArrayList<String> rule2_1Lore = new ArrayList<String>();
        rule2_1Lore.add("");
        rule2_1Lore.add("§7비속어, 욕설 등의 언어 사용을");
        rule2_1Lore.add("§7금지한다.");
        rule2_1Lore.add("");
        rule2_1Lore.add("§7귓속말에 대한 언어 사용은 당사자가");
        rule2_1Lore.add("§7처벌을 원할 경우에 한하여 처벌한다.");
        rule2_1Lore.add("");
        rule2_1Meta.setLore(rule2_1Lore);
        rule2_1.setItemMeta(rule2_1Meta);

        // 2-2
        ItemStack rule2_2 = new ItemStack(Material.PAPER);
        ItemMeta rule2_2Meta = rule2_2.getItemMeta();
        rule2_2Meta.setDisplayName("§f2-2. 상대방을 비꼬는 목적으로 특정");
        ArrayList<String> rule2_2Lore = new ArrayList<String>();
        rule2_2Lore.add("   §0. §f단어 사용 금지");
        rule2_2Lore.add("");
        rule2_2Lore.add("§7(예시: ㅋ, ㅎㅎ, ^^, 네네, 응~ 등)");
        rule2_2Lore.add("");
        rule2_2Lore.add("§7당사자가 처벌을 원할 경우 귓속말도");
        rule2_2Lore.add("§7포함하여 처벌한다.");
        rule2_2Lore.add("");
        rule2_2Meta.setLore(rule2_2Lore);
        rule2_2.setItemMeta(rule2_2Meta);

        // 2-3
        ItemStack rule2_3 = new ItemStack(Material.PAPER);
        ItemMeta rule2_3Meta = rule2_3.getItemMeta();
        rule2_3Meta.setDisplayName("§f2-3. 고의적인 도배 금지");
        ArrayList<String> rule2_3Lore = new ArrayList<String>();
        rule2_3Lore.add("");
        rule2_3Lore.add("§7[ 예시 ]");
        rule2_3Lore.add("§7- 같은 내용의 채팅을 3회 이상");
        rule2_3Lore.add("  §7반복하여 입력");
        rule2_3Lore.add("§7- 같은 내용의 명령어를 5회 이상");
        rule2_3Lore.add("  §7반복하여 입력");
        rule2_3Lore.add("§7- 무의미한 내용의 장문");
        rule2_3Lore.add("§7- 불필요한 단타 사용");
        rule2_3Lore.add("§7- ...");
        rule2_3Lore.add("");
        rule2_3Meta.setLore(rule2_3Lore);
        rule2_3.setItemMeta(rule2_3Meta);

        // 2-4
        ItemStack rule2_4 = new ItemStack(Material.PAPER);
        ItemMeta rule2_4Meta = rule2_4.getItemMeta();
        rule2_4Meta.setDisplayName("§f2-4. 유저 간 친목 환경 조성 금지");
        ArrayList<String> rule2_4Lore = new ArrayList<String>();
        rule2_4Lore.add("");
        rule2_4Lore.add("§7타 유저를 배척하는 환경 조성을");
        rule2_4Lore.add("§7유발하는 친목질을 금지한다.");
        rule2_4Lore.add("");
        rule2_4Lore.add("§7(예시: 반말, DM 유도, 이유 없는");
        rule2_4Lore.add("§7태그(귓속말 미포함) 등)");
        rule2_4Lore.add("");
        rule2_4Meta.setLore(rule2_4Lore);
        rule2_4.setItemMeta(rule2_4Meta);

        // 2-5
        ItemStack rule2_5 = new ItemStack(Material.PAPER);
        ItemMeta rule2_5Meta = rule2_5.getItemMeta();
        rule2_5Meta.setDisplayName("§f2-5. 유저 간 분쟁 유도 및 유발 금지");
        ArrayList<String> rule2_5Lore = new ArrayList<String>();
        rule2_5Lore.add("");
        rule2_5Lore.add("§7공격적인 말투, 시비 등을 포함하여");
        rule2_5Lore.add("§7유저 간 분쟁을 금지한다.");
        rule2_5Lore.add("");
        rule2_5Meta.setLore(rule2_5Lore);
        rule2_5.setItemMeta(rule2_5Meta);

        // 2-6
        ItemStack rule2_6 = new ItemStack(Material.PAPER);
        ItemMeta rule2_6Meta = rule2_6.getItemMeta();
        rule2_6Meta.setDisplayName("§f2-6. 특정 (정치)단체 활동 금지");
        ArrayList<String> rule2_6Lore = new ArrayList<String>();
        rule2_6Lore.add("");
        rule2_6Lore.add("§7서버의 운영 취지에 맞지 않는 활동을");
        rule2_6Lore.add("§7금지한다.");
        rule2_6Lore.add("§7");
        rule2_6Meta.setLore(rule2_6Lore);
        rule2_6.setItemMeta(rule2_6Meta);

        // 3-1
        ItemStack rule3_1 = new ItemStack(Material.PAPER);
        ItemMeta rule3_1Meta = rule3_1.getItemMeta();
        rule3_1Meta.setDisplayName("§f3-1. 불공정 플레이 금지");
        ArrayList<String> rule3_1Lore = new ArrayList<String>();
        rule3_1Lore.add("");
        rule3_1Lore.add("§7[ 예시 ]");
        rule3_1Lore.add("§7- 스폰에 있는 좀비를 죽이는 행위");
        rule3_1Lore.add("§7- 좀비(보균자 포함)가 인간을");
        rule3_1Lore.add("  §7감염시킬 수 있음에도 의도적으로");
        rule3_1Lore.add("  §7감염시키지 않는 행위");
        rule3_1Lore.add("§7- 좀비가 돼서 게임을 탈주하거나,");
        rule3_1Lore.add("  §7적극적으로 게임에 임하지 않는");
        rule3_1Lore.add("  §7행위");
        rule3_1Lore.add("§7- 좀비에게 감염되기 전 (감염 보상을");
        rule3_1Lore.add("  §7주지 않기 위해) 의도적으로");
        rule3_1Lore.add("  §7탈주하는 행위");
        rule3_1Lore.add("§7- 게임 종료 직전 상황에만");
        rule3_1Lore.add("  §7반복적으로 게임에 참가하여");
        rule3_1Lore.add("  §7보상만을 획득하는 행위");
        rule3_1Lore.add("§7- *킬작 및 어뷰징과 같은 행위");
        rule3_1Lore.add("§7- ...");
        rule3_1Lore.add("");
        rule3_1Lore.add("§7*킬작: 보상을 빠르게 얻기 위한");
        rule3_1Lore.add("     §0. §7목적으로 상호 합의 하에 서로");
        rule3_1Lore.add("     §0. §7죽이거나 죽어주는 것");
        rule3_1Lore.add("");
        rule3_1Meta.setLore(rule3_1Lore);
        rule3_1.setItemMeta(rule3_1Meta);

        // 3-2
        ItemStack rule3_2 = new ItemStack(Material.PAPER);
        ItemMeta rule3_2Meta = rule3_2.getItemMeta();
        rule3_2Meta.setDisplayName("§f3-2. 거래 사기 금지");
        ArrayList<String> rule3_2Lore = new ArrayList<String>();
        rule3_2Lore.add("");
        rule3_2Lore.add("§7선물 기능을 사용하여 유저 간 거래를");
        rule3_2Lore.add("§7진행할 경우 사기 행위를 금지한다.");
        rule3_2Lore.add("");
        rule3_2Lore.add("§7사기 행위가 발생할 경우에는");
        rule3_2Lore.add("§7가해자에게서 부당 이득을 몰수한다.");
        rule3_2Lore.add("§7(운영진 판단 하에 부당 이득으로");
        rule3_2Lore.add("§7예상되는 금액 이상을 몰수할 수");
        rule3_2Lore.add("§7있다.)");
        rule3_2Lore.add("");
        rule3_2Lore.add("§7사기 행위의 피해자에게는 피해");
        rule3_2Lore.add("§7복구가 불가능하다.");
        rule3_2Lore.add("§7(선물 기능은 거래를 목적으로");
        rule3_2Lore.add("§7만들어진 기능이 아니기 때문이다.)");
        rule3_2Lore.add("");
        rule3_2Meta.setLore(rule3_2Lore);
        rule3_2.setItemMeta(rule3_2Meta);

        // 4-1
        ItemStack rule4_1 = new ItemStack(Material.PAPER);
        ItemMeta rule4_1Meta = rule4_1.getItemMeta();
        rule4_1Meta.setDisplayName("§f4-1. 운영진 사칭 금지");
        ArrayList<String> rule4_1Lore = new ArrayList<String>();
        rule4_1Lore.add("");
        rule4_1Lore.add("§7서버 내외에서 시즈니스 서버의");
        rule4_1Lore.add("§7운영진을 사칭하는 행위를 금지한다.");
        rule4_1Lore.add("");
        rule4_1Meta.setLore(rule4_1Lore);
        rule4_1.setItemMeta(rule4_1Meta);

        // 4-2
        ItemStack rule4_2 = new ItemStack(Material.PAPER);
        ItemMeta rule4_2Meta = rule4_2.getItemMeta();
        rule4_2Meta.setDisplayName("§f4-2. 운영 방해 금지");
        ArrayList<String> rule4_2Lore = new ArrayList<String>();
        rule4_2Lore.add("");
        rule4_2Lore.add("§7운영진의 활동을 방해하거나, 서버에");
        rule4_2Lore.add("§7문제를 발생시키는 행위를 금지한다.");
        rule4_2Lore.add("");
        rule4_2Lore.add("§7타 서버에서 시즈니스 서버의 명의로");
        rule4_2Lore.add("§7피해를 주는 행위를 금지한다.");
        rule4_2Lore.add("");
        rule4_2Lore.add("§7운영진에게 돈, 아이템, 이벤트 등을");
        rule4_2Lore.add("§7구걸하는 행위를 금지한다.");
        rule4_2Lore.add("");
        rule4_2Meta.setLore(rule4_2Lore);
        rule4_2.setItemMeta(rule4_2Meta);

        // 4-3
        ItemStack rule4_3 = new ItemStack(Material.PAPER);
        ItemMeta rule4_3Meta = rule4_3.getItemMeta();
        rule4_3Meta.setDisplayName("§f4-3. 타 서버와 관련된 내용 언급");
        ArrayList<String> rule4_3Lore = new ArrayList<String>();
        rule4_3Lore.add("   §0. §f금지");
        rule4_3Lore.add("");
        rule4_3Lore.add("§7제휴 서버로 지정된 서버에 한해서는");
        rule4_3Lore.add("§7본 규정을 적용하지 않는다.");
        rule4_3Lore.add("");
        rule4_3Meta.setLore(rule4_3Lore);
        rule4_3.setItemMeta(rule4_3Meta);

        // 4-4
        ItemStack rule4_4 = new ItemStack(Material.PAPER);
        ItemMeta rule4_4Meta = rule4_4.getItemMeta();
        rule4_4Meta.setDisplayName("§f4-4. 서버 비하 금지");
        ArrayList<String> rule4_4Lore = new ArrayList<String>();
        rule4_4Lore.add("");
        rule4_4Lore.add("§7서버 내외에서 시즈니스 서버를");
        rule4_4Lore.add("§7비하하는 발언을 금지한다.");
        rule4_4Lore.add("");
        rule4_4Meta.setLore(rule4_4Lore);
        rule4_4.setItemMeta(rule4_4Meta);

        // 4-5
        ItemStack rule4_5 = new ItemStack(Material.PAPER);
        ItemMeta rule4_5Meta = rule4_5.getItemMeta();
        rule4_5Meta.setDisplayName("§f4-5. 예외사항");
        ArrayList<String> rule4_5Lore = new ArrayList<String>();
        rule4_5Lore.add("");
        rule4_5Lore.add("§7운영진의 판단 하에 위 항목에 대한");
        rule4_5Lore.add("§7처벌은 예외될 수 있다.");
        rule4_5Lore.add("");
        rule4_5Lore.add("§7규칙의 항목 외에도 서버에 피해가 갈");
        rule4_5Lore.add("§7수 있는 행위에 대해서 처벌할 수");
        rule4_5Lore.add("§7있다.");
        rule4_5Lore.add("");
        rule4_5Meta.setLore(rule4_5Lore);
        rule4_5.setItemMeta(rule4_5Meta);

        // 4-6
        ItemStack rule4_6 = new ItemStack(Material.PAPER);
        ItemMeta rule4_6Meta = rule4_6.getItemMeta();
        rule4_6Meta.setDisplayName("§f4-6. 규정 적용 판단의 주체");
        ArrayList<String> rule4_6Lore = new ArrayList<String>();
        rule4_6Lore.add("");
        rule4_6Lore.add("§7규정 적용이 되는 상황을 판단하는");
        rule4_6Lore.add("§7것은 운영진이 주체가 된다.");
        rule4_6Lore.add("");
        rule4_6Meta.setLore(rule4_6Lore);
        rule4_6.setItemMeta(rule4_6Meta);


        inventory.setItem(4, rule);

        inventory.setItem(9, connectionClient);
        inventory.setItem(18, communication);
        inventory.setItem(27, playing);
        inventory.setItem(36, management);

        inventory.setItem(11, rule1_1);
        inventory.setItem(12, rule1_2);
        inventory.setItem(13, rule1_3);
        inventory.setItem(20, rule2_1);
        inventory.setItem(21, rule2_2);
        inventory.setItem(22, rule2_3);
        inventory.setItem(23, rule2_4);
        inventory.setItem(24, rule2_5);
        inventory.setItem(25, rule2_6);
        inventory.setItem(29, rule3_1);
        inventory.setItem(30, rule3_2);
        inventory.setItem(38, rule4_1);
        inventory.setItem(39, rule4_2);
        inventory.setItem(40, rule4_3);
        inventory.setItem(41, rule4_4);
        inventory.setItem(42, rule4_5);
        inventory.setItem(43, rule4_6);

        inventory.setItem(14, grayPane);
        inventory.setItem(15, grayPane);
        inventory.setItem(16, grayPane);
        inventory.setItem(31, grayPane);
        inventory.setItem(32, grayPane);
        inventory.setItem(33, grayPane);
        inventory.setItem(34, grayPane);

        for (int i = 10; i < 45; i += 9) {
            inventory.setItem(i, rail);
        }
        for (int i = 17; i < 45; i += 9) {
            inventory.setItem(i, rail);
        }

        for (int i = 0; i < 4; i++) {
            inventory.setItem(i, blackPane);
        }
        for (int i = 5; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        for (int i = 45; i < 54; i++) {
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
