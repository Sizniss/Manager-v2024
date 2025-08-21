package kr.sizniss.manager;

import com.shampaggon.crackshot.CSUtility;
import kr.ohurjon.InterDiscord.JDA.JDAThread;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;

public class Variables {

    private String serverTitle; // 서버 이름
    private String serverMotd; // 서버 제목

    private JDA jda; // JDA

    private ArrayList<Player> vanishPlayers;
    private ArrayList<Player> cpsPlayers;

    private HashMap<Player, Double> chatDelayTime; // 채팅 지연 시간

    private int newUserJoinLogDelayTime; // 신규 유저 접속 로그 지연 시간
    private boolean isNewUserJoinLogDelayed; // 신규 유저 접속 로그 지연 여부

    private ArrayList<String> tipList; // 조언

    private HashMap<Player, String> selectedClass; // 선택된 병과
    private HashMap<Player, String> selectedType; // 선택된 타입
    private HashMap<Player, String> selectedMainWeapon; // 선택된 주 무기
    private HashMap<Player, String> selectedSubWeapon; // 선택된 보조 무기
    private HashMap<Player, String> selectedMeleeWeapon; // 선택된 근접 무기

    private HashMap<Player, ArrayList<PotionEffect>> appliedPotionEffects;

    private HashMap<Player, Integer> abilityCooldown; // 능력 재사용 대기 시간
    private HashMap<Player, Integer> skillCooldown; // 도약 재사용 대기 시간

    private int variationTime; // 변이 시간
    private HashMap<Player, Integer> variationDelay; // 변이 지연
    private HashMap<Player, Integer> variationLevel; // 변이 레벨

    private int carrierVariationLevel; // 보균자 변이 시작 레벨
    
    private int neededKillCount; // 필요 킬 수
    private HashMap<Player, Integer> killCount; // 킬 카운트
    private HashMap<Player, Integer> deathCount; // 데스 카운트

    private HashMap<Player, Integer> acquiredSupplyCount;

    private int winGoldReward; // 승리 골드 보상
    private int winEmeraldReward; // 승리 에메랄드 보상
    private int loseGoldReward; // 패배 골드 보상
    private int loseEmeraldReward; // 패배 에메랄드 보상
    private int killReward; // 킬 보상
    private int cureReward; // 치유 보상
    private int deathReward; // 데스 보상
    private int escapePenalty; // 탈주 패널티
    private int errorReward; // 오류 보상
    private int jumpMapReward; // 점프맵 보상
    private int huntingGroundGoldReward; // 사냥터 골드 보상
    private int huntingGroundEmeraldReward; // 사냥터 에메랄드 보상
    private int huntingGroundDiamondReward; // 사냥터 다이아몬드 보상
    private int newUserBonusReward; // 신입 유저 플레이 보상

    private ArrayList<Integer> classExperience; // 병과 경험치
    private ArrayList<Integer> typeExperience; // 타입 경험치

    private ArrayList<String> className; // 병과 이름
    private ArrayList<String> typeName; // 타입 이름
    private ArrayList<String> mainWeaponName; // 주 무기
    private ArrayList<String> subWeaponName; // 보조 무기
    private ArrayList<String> meleeWeaponName; // 근접 무기

    private ArrayList<String> bronzeMainWeaponName; // 동 주 무기
    private ArrayList<String> silverMainWeaponName; // 은 주 무기
    private ArrayList<String> goldMainWeaponName; // 금 주 무기

    private ArrayList<Integer> mainWeaponMaxLevel; // 주 무기 최대 레벨

    private ItemStack poison; // 사약
    private ItemStack liquidMedicine; // 물약
    private ItemStack bomb; // 폭탄
    private ItemStack waterMedicine; // 수약
    private ItemStack remedy; // 치료제
    private ItemStack elixir; // 영약
    private ItemStack ammoBox; // 탄약 박스
    private ItemStack lime; // 끈끈이

    private ItemStack leap; // 도약
    private ItemStack runaway; // 폭주
    private ItemStack cure; // 치유
    private ItemStack strengthen; // 강화
    private ItemStack undying; // 불사
    private ItemStack reversal; // 반전
    private ItemStack flight; // 비행
    private ItemStack hide; // 은신
    private ItemStack shock; // 충격
    private ItemStack bioBomb; // 생체 폭탄
    private ItemStack pulling; // 풀링
    private ItemStack clairvoyance; // 투시

    private ItemStack rake; // 갈퀴
    
    private ItemStack ammo; // 탄창

    private ItemStack healthBoost; // 생명력 강화
    private ItemStack regeneration; // 재생
    private ItemStack damageResistance; // 저항
    private ItemStack speed; // 속도 증가
    private ItemStack jump; // 점프 강화

    private ItemStack diamond500ExchangeTicket; // 500 다이아몬드 교환권
    private ItemStack diamond5500ExchangeTicket; // 5,500 다이아몬드 교환권
    private ItemStack emerald1000ExchangeTicket; // 1,000 에메랄드 교환권
    private ItemStack gold5000ExchangeTicket; // 5,000 골드 교환권
    private ItemStack gold55000ExchangeTicket; // 55,000 골드 교환권
    private ItemStack normalWeaponBox; // 일반 무기 상자
    private ItemStack specialWeaponBox; // 특별 무기 상자
    private ItemStack premiumWeaponBox; // 고급 무기 상자
    private ItemStack normalMoneyBox; // 일반 자산 상자
    private ItemStack premiumMoneyBox; // 고급 자산 상자
    private ItemStack minelistBox; // 마인리스트 상자
    private ItemStack greenBorder; // 연두색 테두리
    private ItemStack skyblueBorder; // 하늘색 테두리
    private ItemStack pinkBorder; // 분홍색 테두리
    private ItemStack magentaBorder; // 자홍색 테두리
    private ItemStack yellowBorder; // 노란색 테두리
    private ItemStack greenPrefix; // 연두색 칭호
    private ItemStack skybluePrefix; // 하늘색 칭호
    private ItemStack pinkPrefix; // 분홍색 칭호
    private ItemStack magentaPrefix; // 자홍색 칭호
    private ItemStack yellowPrefix; // 노란색 칭호
    private ItemStack greenNick; // 연두색 닉네임
    private ItemStack skyblueNick; // 하늘색 닉네임
    private ItemStack pinkNick; // 분홍색 닉네임
    private ItemStack magentaNick; // 자홍색 닉네임
    private ItemStack yellowNick; // 노란색 닉네임
    private ItemStack greenChat; // 연두색 채팅
    private ItemStack skyblueChat; // 하늘색 채팅
    private ItemStack pinkChat; // 분홍색 채팅
    private ItemStack magentaChat; // 자홍색 채팅
    private ItemStack yellowChat; // 노란색 채팅
    private ItemStack eventHold; // 이벤트 개최

    private int maxBoxOpenCount; // 최대 개봉 횟수

    private ItemStack blackPane; // 검은색 색유리 판
    private ItemStack whitePane; // 하얀색 색유리 판
    private ItemStack grayPane; // 회색 색유리 판
    private ItemStack bluePane; // 파란색 색유리 판
    private ItemStack redPane; // 빨간색 색유리 판

    private ItemStack prevPage; // 이전 페이지
    private ItemStack nextPage; // 다음 페이지


    public Variables() {
        serverTitle = Files.getServerTitle();
        serverMotd = Files.getServerMotd();

        jda = new JDAThread("NzYxOTQ4ODYxNDMzNTExOTQ2.X3iCLA.r8YMOmjlnX5m9lDfvJHXuot1C8A").getJDA();
        jda.getPresence().setActivity(Activity.playing("시즈니스 온라인"));

        vanishPlayers = new ArrayList<Player>();
        cpsPlayers = new ArrayList<Player>();

        chatDelayTime = new HashMap<Player, Double>();

        newUserJoinLogDelayTime = 300;
        isNewUserJoinLogDelayed = false;

        tipList = new ArrayList<String>();
        tipList.add("§e조각§f은 무기를 §n강화§f하는데 사용됩니다.");
        tipList.add("§9방패§f로 막고 있는 플레이어는 §n뒤§f에서 공격할 경우 더 큰 피해를 입힐 수 있습니다.");
        tipList.add("§9무기§f는 §e버리기 키(Q키)§f로 재장전을 할 수 있습니다.");
        tipList.add("§9병과 아이템§f은 §e손 바꾸기 키(F키)§f를 누르는 것으로도 사용할 수 있습니다.");
        tipList.add("§c타입 능력§f은 §e버리기 키(Q키)§f를 누르는 것으로도 사용할 수 있습니다.");
        tipList.add("§c도약 능력§f은 §e손 바꾸기 키(F키)§f를 누르는 것으로도 사용할 수 있습니다.");
        tipList.add("§9영웅§f은 자신을 제외한 생존자가 모두 사망할 경우 패배합니다.");
        tipList.add("§9무기 강화§f는 §n'장비 창 → 주 무기 창 → 무기 우클릭'§f으로\n강화 창에 진입할 수 있습니다.");
        tipList.add("§9병과 능력치 분배§f는 §n'장비 창 → 병과 창 → 병과 우클릭'§f으로\n능력치 분배 창에 진입할 수 있습니다.");
        tipList.add("§c타입 능력치 분배§f는 §n'장비 창 → 타입 창 → 타입 우클릭'§f으로\n능력치 분배 창에 진입할 수 있습니다.");
        tipList.add("§f같은 팀 인간 앞에 위치할 경우, §9총§f의 §n탄이 막힐 수 있으니 §f주의하십시오.");

        selectedClass = new HashMap<Player, String>();
        selectedType = new HashMap<Player, String>();
        selectedMainWeapon = new HashMap<Player, String>();
        selectedSubWeapon = new HashMap<Player, String>();
        selectedMeleeWeapon = new HashMap<Player, String>();

        abilityCooldown = new HashMap<Player, Integer>();
        skillCooldown = new HashMap<Player, Integer>();

        variationTime = 15;
        variationDelay = new HashMap<Player, Integer>();
        variationLevel = new HashMap<Player, Integer>();

        carrierVariationLevel = 6;

        neededKillCount = 2;

        killCount = new HashMap<Player, Integer>();
        deathCount = new HashMap<Player, Integer>();

        acquiredSupplyCount = new HashMap<Player, Integer>();

        // 보상
        winGoldReward = 10;
        winEmeraldReward = 7;
        loseGoldReward = 8;
        loseEmeraldReward = 5;
        killReward = 50;
        cureReward = 50;
        deathReward = 0;
        escapePenalty = 3000;
        errorReward = 300;
        jumpMapReward = 200;
        huntingGroundGoldReward = 20;
        huntingGroundEmeraldReward = 1;
        huntingGroundDiamondReward = 2;
        newUserBonusReward = 4;

        // 병과 경험치 크기
        classExperience = new ArrayList<Integer>();
        classExperience.add(5);
        classExperience.add(10);
        classExperience.add(15);
        classExperience.add(20);
        classExperience.add(25);
        classExperience.add(1000);
        classExperience.add(2000);
        classExperience.add(3000);
        classExperience.add(4000);
        classExperience.add(5000);

        // 타입 경험치 크기
        typeExperience = new ArrayList<Integer>();
        typeExperience.add(5);
        typeExperience.add(10);
        typeExperience.add(15);
        typeExperience.add(20);
        typeExperience.add(25);
        typeExperience.add(30);
        typeExperience.add(35);
        typeExperience.add(40);
        typeExperience.add(1000);
        typeExperience.add(2000);
        typeExperience.add(3000);
        typeExperience.add(4000);
        typeExperience.add(5000);
        typeExperience.add(6000);
        typeExperience.add(7000);


        // 병과
        className = new ArrayList<String>();
        className.add("보병");
        className.add("포병");
        className.add("교란병");
        className.add("의무병");
        className.add("기갑병");
        className.add("보급병");
        className.add("공병");

        // 타입
        typeName = new ArrayList<String>();
        typeName.add("버서커");
        typeName.add("바드");
        typeName.add("나이트");
        typeName.add("네크로맨서");
        typeName.add("워리어");
        typeName.add("로그");
        typeName.add("어쌔신");
        typeName.add("레인저");
        typeName.add("버스터");
        typeName.add("서머너");
        typeName.add("헌터");

        // 주 무기
        mainWeaponName = new ArrayList<String>();
        mainWeaponName.add("MP5");
        mainWeaponName.add("P90");
        mainWeaponName.add("KRISS_Vector");
        mainWeaponName.add("AR-57");
        mainWeaponName.add("M1928_Thompson");
        mainWeaponName.add("M4A1");
        mainWeaponName.add("AK-47");
        mainWeaponName.add("M16A4");
        mainWeaponName.add("AUG");
        mainWeaponName.add("Steyr_Scout");
        mainWeaponName.add("AWP");
        mainWeaponName.add("AWM");
        mainWeaponName.add("SG550");
        mainWeaponName.add("Kar98k");
        mainWeaponName.add("SPAS-12");
        mainWeaponName.add("USAS-12");
        mainWeaponName.add("AA-12");
        mainWeaponName.add("Triple_Barrel");
        mainWeaponName.add("M1887_Winchester");
        mainWeaponName.add("M249");
        mainWeaponName.add("MG3");
        mainWeaponName.add("PKM");
        mainWeaponName.add("M134_Minigun");
        mainWeaponName.add("석궁");
        mainWeaponName.add("RPG-7");
        mainWeaponName.add("진압_방패");
        mainWeaponName.add("화염_방사기");
        mainWeaponName.add("Barret_M99");
        mainWeaponName.add("화룡포");
        mainWeaponName.add("혈적자");
        mainWeaponName.add("전기톱");
        mainWeaponName.add("M32_MGL");
        mainWeaponName.add("신기전");
        mainWeaponName.add("MK_11_SWS");
        mainWeaponName.add("VSS_Vintorez");
        mainWeaponName.add("가시_방패");
        mainWeaponName.add("물대포");
        mainWeaponName.add("C4");
        mainWeaponName.add("AEA_Zeus_2");
        mainWeaponName.add("DP-12");
        mainWeaponName.add("시한폭탄");
        mainWeaponName.add("SSW40");

        // 보조 무기
        subWeaponName = new ArrayList<String>();
        subWeaponName.add("HK_USP");
        subWeaponName.add("Desert_Eagle");
        subWeaponName.add("Beretta_M93R");
        subWeaponName.add("M79_소드오프");
        subWeaponName.add("수리검");
        subWeaponName.add("덕_풋");
        subWeaponName.add("테이저건");
        subWeaponName.add("Double_Barrel_소드오프");
        subWeaponName.add("M950");
        subWeaponName.add("토치");
        subWeaponName.add("MK_13_EGLM");

        // 근접 무기
        meleeWeaponName = new ArrayList<String>();
        meleeWeaponName.add("씰_나이프");
        meleeWeaponName.add("빠루");
        meleeWeaponName.add("쿠장");
        meleeWeaponName.add("부채");
        meleeWeaponName.add("로즈_나이프");
        meleeWeaponName.add("해머");
        meleeWeaponName.add("카타나");
        meleeWeaponName.add("테이저_나이프");
        meleeWeaponName.add("청룡도");
        meleeWeaponName.add("클레이모어");
        meleeWeaponName.add("워해머");


        // 동 주 무기
        bronzeMainWeaponName = new ArrayList<String>();
        bronzeMainWeaponName.add("MP5");
        bronzeMainWeaponName.add("P90");
        bronzeMainWeaponName.add("KRISS_Vector");
        bronzeMainWeaponName.add("AR-57");
        bronzeMainWeaponName.add("M1928_Thompson");
        bronzeMainWeaponName.add("M4A1");
        bronzeMainWeaponName.add("AK-47");
        bronzeMainWeaponName.add("M16A4");
        bronzeMainWeaponName.add("Steyr_Scout");
        bronzeMainWeaponName.add("AWP");
        bronzeMainWeaponName.add("AWM");
        bronzeMainWeaponName.add("Kar98k");
        bronzeMainWeaponName.add("SPAS-12");
        bronzeMainWeaponName.add("Triple_Barrel");
        bronzeMainWeaponName.add("M1887_Winchester");

        // 은 주 무기
        silverMainWeaponName = new ArrayList<String>();
        silverMainWeaponName.add("AUG");
        silverMainWeaponName.add("SG550");
        silverMainWeaponName.add("USAS-12");
        silverMainWeaponName.add("AA-12");
        silverMainWeaponName.add("M249");
        silverMainWeaponName.add("MG3");
        silverMainWeaponName.add("PKM");
        silverMainWeaponName.add("M134_Minigun");
        silverMainWeaponName.add("석궁");
        silverMainWeaponName.add("RPG-7");
        silverMainWeaponName.add("진압_방패");
        silverMainWeaponName.add("화염_방사기");
        silverMainWeaponName.add("VSS_Vintorez");
        silverMainWeaponName.add("물대포");
        silverMainWeaponName.add("DP-12");

        // 금 주 무기
        goldMainWeaponName = new ArrayList<String>();
        goldMainWeaponName.add("Barret_M99");
        goldMainWeaponName.add("화룡포");
        goldMainWeaponName.add("혈적자");
        goldMainWeaponName.add("전기톱");
        goldMainWeaponName.add("M32_MGL");
        goldMainWeaponName.add("신기전");
        goldMainWeaponName.add("MK_11_SWS");
        goldMainWeaponName.add("가시_방패");
        goldMainWeaponName.add("C4");
        goldMainWeaponName.add("AEA_Zeus_2");
        goldMainWeaponName.add("시한폭탄");
        goldMainWeaponName.add("SSW40");


        // 주 무기 최대 레벨
        mainWeaponMaxLevel = new ArrayList<Integer>();
        mainWeaponMaxLevel.add(3); // MP5
        mainWeaponMaxLevel.add(3); // P90
        mainWeaponMaxLevel.add(3); // KRISS Vector
        mainWeaponMaxLevel.add(3); // AR-57
        mainWeaponMaxLevel.add(3); // M1928 Thompson
        mainWeaponMaxLevel.add(3); // M4A1
        mainWeaponMaxLevel.add(3); // AK-47
        mainWeaponMaxLevel.add(3); // M16A4
        mainWeaponMaxLevel.add(2); // AUG
        mainWeaponMaxLevel.add(3); // Steyr Scout
        mainWeaponMaxLevel.add(3); // AWP
        mainWeaponMaxLevel.add(3); // AWM
        mainWeaponMaxLevel.add(2); // SG550
        mainWeaponMaxLevel.add(3); // Kar98k
        mainWeaponMaxLevel.add(3); // SPAS-12
        mainWeaponMaxLevel.add(2); // USAS-12
        mainWeaponMaxLevel.add(2); // AA-12
        mainWeaponMaxLevel.add(3); // Triple Barrel
        mainWeaponMaxLevel.add(3); // M1887 Winchester
        mainWeaponMaxLevel.add(2); // M249
        mainWeaponMaxLevel.add(2); // MG3
        mainWeaponMaxLevel.add(2); // PKM
        mainWeaponMaxLevel.add(2); // M134 Minigun
        mainWeaponMaxLevel.add(2); // 석궁
        mainWeaponMaxLevel.add(2); // RPG-7
        mainWeaponMaxLevel.add(2); // 진압 방패
        mainWeaponMaxLevel.add(2); // 화염 방사기
        mainWeaponMaxLevel.add(1); // Barret M99
        mainWeaponMaxLevel.add(1); // 화룡포
        mainWeaponMaxLevel.add(1); // 혈적자
        mainWeaponMaxLevel.add(1); // 전기톱
        mainWeaponMaxLevel.add(1); // M32 MGL
        mainWeaponMaxLevel.add(1); // 신기전
        mainWeaponMaxLevel.add(1); // MK.11 SWS
        mainWeaponMaxLevel.add(2); // VSS Vintorez
        mainWeaponMaxLevel.add(1); // 가시 방패
        mainWeaponMaxLevel.add(2); // 물대포
        mainWeaponMaxLevel.add(1); // C4
        mainWeaponMaxLevel.add(1); // AEA Zeus 2
        mainWeaponMaxLevel.add(2); // DP-12
        mainWeaponMaxLevel.add(1); // 시한폭탄
        mainWeaponMaxLevel.add(1); // SSW40


        poison = new ItemStack(Material.DEAD_BUSH);
        ItemMeta poisonMeta = poison.getItemMeta();
        poisonMeta.setDisplayName("§f사약");
        poison.setItemMeta(poisonMeta);

        liquidMedicine = new ItemStack(Material.RED_MUSHROOM);
        ItemMeta liquidMedicineMeta = liquidMedicine.getItemMeta();
        liquidMedicineMeta.setDisplayName("§f물약");
        liquidMedicine.setItemMeta(liquidMedicineMeta);

        bomb = new CSUtility().generateWeapon("폭탄");

        waterMedicine = new ItemStack(Material.RED_ROSE, 1, Short.parseShort("1"));
        ItemMeta waterMedicineMeta = waterMedicine.getItemMeta();
        waterMedicineMeta.setDisplayName("§f수약");
        waterMedicine.setItemMeta(waterMedicineMeta);

        remedy = new ItemStack(Material.BROWN_MUSHROOM);
        ItemMeta remedyMeta = remedy.getItemMeta();
        remedyMeta.setDisplayName("§f치료제");
        remedy.setItemMeta(remedyMeta);

        elixir = new ItemStack(Material.YELLOW_FLOWER);
        ItemMeta elixirMeta = elixir.getItemMeta();
        elixirMeta.setDisplayName("§f영약");
        elixir.setItemMeta(elixirMeta);

        ammoBox = new ItemStack(Material.RED_ROSE, 1, Short.parseShort("3"));
        ItemMeta ammoBoxMeta = ammoBox.getItemMeta();
        ammoBoxMeta.setDisplayName("§f탄약 박스");
        ammoBox.setItemMeta(ammoBoxMeta);

        lime = new CSUtility().generateWeapon("끈끈이");


        leap = new ItemStack(Material.RAW_FISH, 1, Short.parseShort("3"));
        ItemMeta leapMeta = leap.getItemMeta();
        leapMeta.setDisplayName("§f도약");
        leap.setItemMeta(leapMeta);

        runaway = new ItemStack(Material.FERMENTED_SPIDER_EYE);
        ItemMeta runawayMeta = runaway.getItemMeta();
        runawayMeta.setDisplayName("§f폭주");
        runaway.setItemMeta(runawayMeta);

        cure = new ItemStack(Material.SPECKLED_MELON);
        ItemMeta cureMeta = cure.getItemMeta();
        cureMeta.setDisplayName("§f치유");
        cure.setItemMeta(cureMeta);

        strengthen = new ItemStack(Material.CLAY_BRICK);
        ItemMeta strengthenMeta = strengthen.getItemMeta();
        strengthenMeta.setDisplayName("§f강화");
        strengthen.setItemMeta(strengthenMeta);

        undying = new ItemStack(Material.TOTEM);
        ItemMeta undyingMeta = undying.getItemMeta();
        undyingMeta.setDisplayName("§f불사");
        undying.setItemMeta(undyingMeta);

        reversal = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta reversalMeta = reversal.getItemMeta();
        reversalMeta.setDisplayName("§f반전");
        reversal.setItemMeta(reversalMeta);

        flight = new ItemStack(Material.ELYTRA);
        ItemMeta flightMeta = flight.getItemMeta();
        flightMeta.setDisplayName("§f비행");
        flight.setItemMeta(flightMeta);

        hide = new ItemStack(Material.STRING);
        ItemMeta hideMeta = hide.getItemMeta();
        hideMeta.setDisplayName("§f은신");
        hide.setItemMeta(hideMeta);

        shock = new CSUtility().generateWeapon("충격");

        bioBomb = new CSUtility().generateWeapon("생체_폭탄");

        pulling = new ItemStack(Material.CAULDRON_ITEM);
        ItemMeta pullingMeta = pulling.getItemMeta();
        pullingMeta.setDisplayName("§f풀링");
        pulling.setItemMeta(pullingMeta);

        clairvoyance = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta clairvoyanceMeta = clairvoyance.getItemMeta();
        clairvoyanceMeta.setDisplayName("§f투시");
        clairvoyance.setItemMeta(clairvoyanceMeta);


        rake = new ItemStack(Material.NETHER_STALK);
        ItemMeta rakeMeta = rake.getItemMeta();
        rakeMeta.setDisplayName("§f갈퀴");
        rake.setItemMeta(rakeMeta);


        ammo = new ItemStack(Material.IRON_INGOT);
        ItemMeta ammoMeta = ammo.getItemMeta();
        ammoMeta.setDisplayName("§f탄창");
        ammo.setItemMeta(ammoMeta);


        healthBoost = new ItemStack(Material.LEATHER);
        ItemMeta healthBoostMeta = healthBoost.getItemMeta();
        healthBoostMeta.setDisplayName("§f생명력 강화");
        healthBoost.setItemMeta(healthBoostMeta);

        regeneration = new ItemStack(Material.INK_SACK, 1, Short.parseShort("1"));
        ItemMeta regenerationMeta = regeneration.getItemMeta();
        regenerationMeta.setDisplayName("§f재생");
        regeneration.setItemMeta(regenerationMeta);

        damageResistance = new ItemStack(Material.RABBIT_HIDE);
        ItemMeta damageResistanceMeta = damageResistance.getItemMeta();
        damageResistanceMeta.setDisplayName("§f저항");
        damageResistance.setItemMeta(damageResistanceMeta);

        speed = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta speedMeta = speed.getItemMeta();
        speedMeta.setDisplayName("§f속도 증가");
        speed.setItemMeta(speedMeta);

        jump = new ItemStack(Material.FEATHER);
        ItemMeta jumpMeta = jump.getItemMeta();
        jumpMeta.setDisplayName("§f점프 강화");
        jump.setItemMeta(jumpMeta);


        diamond500ExchangeTicket = new ItemStack(Material.DIAMOND);
        ItemMeta diamond500Meta = diamond500ExchangeTicket.getItemMeta();
        diamond500Meta.setDisplayName("§b500 다이아몬드 §f교환권");
        diamond500ExchangeTicket.setItemMeta(diamond500Meta);

        diamond5500ExchangeTicket = new ItemStack(Material.DIAMOND);
        ItemMeta diamond5500ExchangeTicketMeta = diamond5500ExchangeTicket.getItemMeta();
        diamond5500ExchangeTicketMeta.setDisplayName("§b5,500 다이아몬드 §f교환권");
        diamond5500ExchangeTicket.setItemMeta(diamond5500ExchangeTicketMeta);

        emerald1000ExchangeTicket = new ItemStack(Material.EMERALD);
        ItemMeta emerald1000ExchangeTicketMeta = emerald1000ExchangeTicket.getItemMeta();
        emerald1000ExchangeTicketMeta.setDisplayName("§a1,000 에메랄드 §f교환권");
        emerald1000ExchangeTicket.setItemMeta(emerald1000ExchangeTicketMeta);

        gold5000ExchangeTicket = new ItemStack(Material.GOLD_INGOT);
        ItemMeta gold5000ExchangeTicketMeta = gold5000ExchangeTicket.getItemMeta();
        gold5000ExchangeTicketMeta.setDisplayName("§e5,000 골드 §f교환권");
        gold5000ExchangeTicket.setItemMeta(gold5000ExchangeTicketMeta);

        gold55000ExchangeTicket = new ItemStack(Material.GOLD_INGOT);
        ItemMeta gold55000ExchangeTicketMeta = gold55000ExchangeTicket.getItemMeta();
        gold55000ExchangeTicketMeta.setDisplayName("§e55,000 골드 §f교환권");
        gold55000ExchangeTicket.setItemMeta(gold55000ExchangeTicketMeta);

        normalWeaponBox = new ItemStack(Material.CHEST);
        ItemMeta normalWeaponBoxMeta = normalWeaponBox.getItemMeta();
        normalWeaponBoxMeta.setDisplayName("§f일반 무기 상자");
        ArrayList<String> normalWeaponBoxLore = new ArrayList<String>();
        normalWeaponBoxLore.add("§e신입 유저 추천");
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add(" §f※ 구성");
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add(" §e→ §f획득 가능한 상품:");
        normalWeaponBoxLore.add("   §f- §6동 §f조각 (§755%§f)");
        normalWeaponBoxLore.add("   §f- §f은 §f조각 (§735%§f)");
        normalWeaponBoxLore.add("   §f- §6동 §f주 무기 (§76%§f)");
        normalWeaponBoxLore.add("   §f- §f은 §f주 무기 (§74%§f)");
        normalWeaponBoxLore.add("");
        normalWeaponBoxMeta.setLore(normalWeaponBoxLore);
        normalWeaponBox.setItemMeta(normalWeaponBoxMeta);

        specialWeaponBox = new ItemStack(Material.ENDER_CHEST);
        ItemMeta specialWeaponBoxMeta = specialWeaponBox.getItemMeta();
        specialWeaponBoxMeta.setDisplayName("§f특별 무기 상자");
        ArrayList<String> specialWeaponBoxLore = new ArrayList<String>();
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add(" §f※ 구성");
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add(" §e→ §f획득 가능한 상품:");
        specialWeaponBoxLore.add("   §f- §6동 §f조각 (§731%§f)");
        specialWeaponBoxLore.add("   §f- §f은 §f조각 (§722%§f)");
        specialWeaponBoxLore.add("   §f- §e금 §f조각 (§712%§f)");
        specialWeaponBoxLore.add("   §f- §6동 §f주 무기 (§724%§f)");
        specialWeaponBoxLore.add("   §f- §f은 §f주 무기 (§77%§f)");
        specialWeaponBoxLore.add("   §f- §e금 §f주 무기 (§74%§f)");
        specialWeaponBoxLore.add("");
        specialWeaponBoxMeta.setLore(specialWeaponBoxLore);
        specialWeaponBox.setItemMeta(specialWeaponBoxMeta);

        premiumWeaponBox = new ItemStack(Material.ENDER_CHEST);
        ItemMeta premiumWeaponBoxMeta = premiumWeaponBox.getItemMeta();
        premiumWeaponBoxMeta.setDisplayName("§f고급 무기 상자");
        ArrayList<String> premiumWeaponBoxLore = new ArrayList<String>();
        premiumWeaponBoxLore.add("§b후원자 추천");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §f※ 구성");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §e→ §f획득 가능한 상품:");
        premiumWeaponBoxLore.add("   §f- §6동 §f조각 (§735%§f)");
        premiumWeaponBoxLore.add("   §f- §f은 §f조각 (§725%§f)");
        premiumWeaponBoxLore.add("   §f- §e금 §f조각 (§715%§f)");
        premiumWeaponBoxLore.add("   §f- §6동 §f주 무기 (§715%§f)");
        premiumWeaponBoxLore.add("   §f- §f은 §f주 무기 (§77%§f)");
        premiumWeaponBoxLore.add("   §f- §e금 §f주 무기 (§73%§f)");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxMeta.setLore(premiumWeaponBoxLore);
        premiumWeaponBox.setItemMeta(premiumWeaponBoxMeta);

        normalMoneyBox = new ItemStack(Material.CHEST);
        ItemMeta normalMoneyBoxMeta = normalMoneyBox.getItemMeta();
        normalMoneyBoxMeta.setDisplayName("§f일반 자산 상자");
        ArrayList<String> normalMoneyBoxLore = new ArrayList<String>();
        normalMoneyBoxLore.add("");
        normalMoneyBoxLore.add(" §f※ 구성");
        normalMoneyBoxLore.add("");
        normalMoneyBoxLore.add(" §e→ §f획득 가능한 상품:");
        normalMoneyBoxLore.add("   §f- §e1 ~ 10,000 골드");
        normalMoneyBoxLore.add("");
        normalMoneyBoxMeta.setLore(normalMoneyBoxLore);
        normalMoneyBox.setItemMeta(normalMoneyBoxMeta);
        
        premiumMoneyBox = new ItemStack(Material.ENDER_CHEST);
        ItemMeta premiumMoneyBoxMeta = premiumMoneyBox.getItemMeta();
        premiumMoneyBoxMeta.setDisplayName("§f고급 자산 상자");
        ArrayList<String> premiumMoneyBoxLore = new ArrayList<String>();
        premiumMoneyBoxLore.add("");
        premiumMoneyBoxLore.add(" §f※ 구성");
        premiumMoneyBoxLore.add("");
        premiumMoneyBoxLore.add(" §e→ §f획득 가능한 상품:");
        premiumMoneyBoxLore.add("   §f- §b1 ~ 1,000 다이아몬드");
        premiumMoneyBoxLore.add("");
        premiumMoneyBoxMeta.setLore(premiumMoneyBoxLore);
        premiumMoneyBox.setItemMeta(premiumMoneyBoxMeta);

        minelistBox = new ItemStack(Material.CHEST);
        ItemMeta minelistBoxMeta = minelistBox.getItemMeta();
        minelistBoxMeta.setDisplayName("§f마인리스트 상자");
        ArrayList<String> minelistBoxLore = new ArrayList<String>();
        minelistBoxLore.add("");
        minelistBoxLore.add(" §f※ 구성");
        minelistBoxLore.add("");
        minelistBoxLore.add(" §e→ §f구성 상품:");
        minelistBoxLore.add("   §f- §a200 에메랄드");
        minelistBoxLore.add("");
        minelistBoxLore.add(" §e→ §f획득 가능한 상품:");
        minelistBoxLore.add("   §f- §f일반 자산 상자 (§790%§f)");
        minelistBoxLore.add("   §f- §f고급 자산 상자 (§710%§f)");
        minelistBoxLore.add("");
        minelistBoxMeta.setLore(minelistBoxLore);
        minelistBox.setItemMeta(minelistBoxMeta);

        greenBorder = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
        ItemMeta greenBorderMeta = greenBorder.getItemMeta();
        greenBorderMeta.setDisplayName("§a연두색 §f테두리");
        greenBorder.setItemMeta(greenBorderMeta);

        skyblueBorder = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
        ItemMeta skyblueBorderMeta = skyblueBorder.getItemMeta();
        skyblueBorderMeta.setDisplayName("§b하늘색 §f테두리");
        skyblueBorder.setItemMeta(skyblueBorderMeta);

        pinkBorder = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
        ItemMeta pinkBorderMeta = pinkBorder.getItemMeta();
        pinkBorderMeta.setDisplayName("§c분홍색 §f테두리");
        pinkBorder.setItemMeta(pinkBorderMeta);

        magentaBorder = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
        ItemMeta magentaBorderMeta = magentaBorder.getItemMeta();
        magentaBorderMeta.setDisplayName("§d자홍색 §f테두리");
        magentaBorder.setItemMeta(magentaBorderMeta);

        yellowBorder = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
        ItemMeta yellowBorderMeta = yellowBorder.getItemMeta();
        yellowBorderMeta.setDisplayName("§e노란색 §f테두리");
        yellowBorder.setItemMeta(yellowBorderMeta);

        greenPrefix = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
        ItemMeta greenPrefixMeta = greenPrefix.getItemMeta();
        greenPrefixMeta.setDisplayName("§a연두색 §f칭호");
        greenPrefix.setItemMeta(greenPrefixMeta);

        skybluePrefix = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
        ItemMeta skybluePrefixMeta = skybluePrefix.getItemMeta();
        skybluePrefixMeta.setDisplayName("§b하늘색 §f칭호");
        skybluePrefix.setItemMeta(skybluePrefixMeta);

        pinkPrefix = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
        ItemMeta pinkPrefixMeta = pinkPrefix.getItemMeta();
        pinkPrefixMeta.setDisplayName("§c분홍색 §f칭호");
        pinkPrefix.setItemMeta(pinkPrefixMeta);

        magentaPrefix = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
        ItemMeta magentaPrefixMeta = magentaPrefix.getItemMeta();
        magentaPrefixMeta.setDisplayName("§d자홍색 §f칭호");
        magentaPrefix.setItemMeta(magentaPrefixMeta);

        yellowPrefix = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
        ItemMeta yellowPrefixMeta = yellowPrefix.getItemMeta();
        yellowPrefixMeta.setDisplayName("§e노란색 §f칭호");
        yellowPrefix.setItemMeta(yellowPrefixMeta);

        greenNick = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
        ItemMeta greenNickMeta = greenNick.getItemMeta();
        greenNickMeta.setDisplayName("§a연두색 §f닉네임");
        greenNick.setItemMeta(greenNickMeta);

        skyblueNick = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
        ItemMeta skyblueNickMeta = skyblueNick.getItemMeta();
        skyblueNickMeta.setDisplayName("§b하늘색 §f닉네임");
        skyblueNick.setItemMeta(skyblueNickMeta);

        pinkNick = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
        ItemMeta pinkNickMeta = pinkNick.getItemMeta();
        pinkNickMeta.setDisplayName("§c분홍색 §f닉네임");
        pinkNick.setItemMeta(pinkNickMeta);

        magentaNick = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
        ItemMeta magentaNickMeta = magentaNick.getItemMeta();
        magentaNickMeta.setDisplayName("§d자홍색 §f닉네임");
        magentaNick.setItemMeta(magentaNickMeta);

        yellowNick = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
        ItemMeta yellowNickMeta = yellowNick.getItemMeta();
        yellowNickMeta.setDisplayName("§e노란색 §f닉네임");
        yellowNick.setItemMeta(yellowNickMeta);

        greenChat = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
        ItemMeta greenChatMeta = greenChat.getItemMeta();
        greenChatMeta.setDisplayName("§a연두색 §f채팅");
        greenChat.setItemMeta(greenChatMeta);

        skyblueChat = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
        ItemMeta skyblueChatMeta = skyblueChat.getItemMeta();
        skyblueChatMeta.setDisplayName("§b하늘색 §f채팅");
        skyblueChat.setItemMeta(skyblueChatMeta);

        pinkChat = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
        ItemMeta pinkChatMeta = pinkChat.getItemMeta();
        pinkChatMeta.setDisplayName("§c분홍색 §f채팅");
        pinkChat.setItemMeta(pinkChatMeta);

        magentaChat = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
        ItemMeta magentaChatMeta = magentaChat.getItemMeta();
        magentaChatMeta.setDisplayName("§d자홍색 §f채팅");
        magentaChat.setItemMeta(magentaChatMeta);

        yellowChat = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
        ItemMeta yellowChatMeta = yellowChat.getItemMeta();
        yellowChatMeta.setDisplayName("§e노란색 §f채팅");
        yellowChat.setItemMeta(yellowChatMeta);

        eventHold = new ItemStack(Material.NETHER_STAR);
        ItemMeta eventHoldMeta = eventHold.getItemMeta();
        eventHoldMeta.setDisplayName("§f이벤트 개최");
        ArrayList<String> eventHoldLore = new ArrayList<String>();
        eventHoldLore.add("");
        eventHoldLore.add(" §f※ 상품");
        eventHoldLore.add("");
        eventHoldLore.add(" §e→ §7현재 게임에 참여한 모든");
        eventHoldLore.add("   §7유저들에게 고급 무기 상자를");
        eventHoldLore.add("   §7지급합니다.");
        eventHoldLore.add("");
        eventHoldMeta.setLore(eventHoldLore);
        eventHold.setItemMeta(eventHoldMeta);


        maxBoxOpenCount = 40;


        blackPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, Short.parseShort("15"));
        ItemMeta blackPaneMeta = blackPane.getItemMeta();
        blackPaneMeta.setDisplayName("§f");
        blackPane.setItemMeta(blackPaneMeta);

        whitePane = new ItemStack(Material.STAINED_GLASS_PANE, 1, Short.parseShort("0"));
        ItemMeta whitePaneMeta = whitePane.getItemMeta();
        whitePaneMeta.setDisplayName("§f");
        whitePane.setItemMeta(whitePaneMeta);

        grayPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, Short.parseShort("7"));
        ItemMeta grayPaneMeta = grayPane.getItemMeta();
        grayPaneMeta.setDisplayName("§f");
        grayPane.setItemMeta(grayPaneMeta);

        bluePane = new ItemStack(Material.STAINED_GLASS_PANE, 1, Short.parseShort("11"));
        ItemMeta bluePaneMeta = bluePane.getItemMeta();
        bluePaneMeta.setDisplayName("§f");
        bluePane.setItemMeta(bluePaneMeta);

        redPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, Short.parseShort("14"));
        ItemMeta redPaneMeta = redPane.getItemMeta();
        redPaneMeta.setDisplayName("§f");
        redPane.setItemMeta(redPaneMeta);


        prevPage = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
        SkullMeta prevPageMeta = (SkullMeta) prevPage.getItemMeta();
        prevPageMeta.setDisplayName("§f이전");
        prevPageMeta.setOwner("MHF_ArrowLeft");
        prevPage.setItemMeta(prevPageMeta);

        nextPage = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
        SkullMeta nextPageMeta = (SkullMeta) nextPage.getItemMeta();
        nextPageMeta.setDisplayName("§f다음");
        nextPageMeta.setOwner("MHF_ArrowRight");
        nextPage.setItemMeta(nextPageMeta);
    }


    public String getServerTitle() {
        return serverTitle;
    }
    public void setServerTitle(String serverTitle) {
        this.serverTitle = serverTitle;
    }
    public String getServerMotd() {
        return serverMotd;
    }
    public void setServerMotd(String serverMotd) {
        this.serverMotd = serverMotd;
    }

    public JDA getJda() {
        return jda;
    }
    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public ArrayList<Player> getVanishPlayers() {
        return vanishPlayers;
    }
    public void setVanishPlayers(ArrayList<Player> vanishPlayers) {
        this.vanishPlayers = vanishPlayers;
    }
    public ArrayList<Player> getCpsPlayers() {
        return cpsPlayers;
    }
    public void setCpsPlayers(ArrayList<Player> cpsPlayers) {
        this.cpsPlayers = cpsPlayers;
    }

    public HashMap<Player, Double> getChatDelayTime() {
        return chatDelayTime;
    }
    public void setChatDelayTime(HashMap<Player, Double> chatDelayTime) {
        this.chatDelayTime = chatDelayTime;
    }

    public int getNewUserJoinLogDelayTime() {
        return newUserJoinLogDelayTime;
    }
    public void setNewUserJoinLogDelayTime(int newUserJoinLogDelayTime) {
        this.newUserJoinLogDelayTime = newUserJoinLogDelayTime;
    }
    public boolean isNewUserJoinLogDelayed() {
        return isNewUserJoinLogDelayed;
    }
    public void setNewUserJoinLogDelayed(boolean newUserJoinLogDelayed) {
        isNewUserJoinLogDelayed = newUserJoinLogDelayed;
    }

    public ArrayList<String> getTipList() {
        return tipList;
    }
    public void setTipList(ArrayList<String> tipList) {
        this.tipList = tipList;
    }

    public HashMap<Player, String> getSelectedClass() {
        return selectedClass;
    }
    public void setSelectedClass(HashMap<Player, String> selectedClass) {
        this.selectedClass = selectedClass;
    }
    public HashMap<Player, String> getSelectedType() {
        return selectedType;
    }
    public void setSelectedType(HashMap<Player, String> selectedType) {
        this.selectedType = selectedType;
    }
    public HashMap<Player, String> getSelectedMainWeapon() {
        return selectedMainWeapon;
    }
    public void setSelectedMainWeapon(HashMap<Player, String> selectedMainWeapon) {
        this.selectedMainWeapon = selectedMainWeapon;
    }
    public HashMap<Player, String> getSelectedSubWeapon() {
        return selectedSubWeapon;
    }
    public void setSelectedSubWeapon(HashMap<Player, String> selectedSubWeapon) {
        this.selectedSubWeapon = selectedSubWeapon;
    }
    public HashMap<Player, String> getSelectedMeleeWeapon() {
        return selectedMeleeWeapon;
    }
    public void setSelectedMeleeWeapon(HashMap<Player, String> selectedMeleeWeapon) {
        this.selectedMeleeWeapon = selectedMeleeWeapon;
    }

    public HashMap<Player, ArrayList<PotionEffect>> getAppliedPotionEffects() {
        return appliedPotionEffects;
    }
    public void setAppliedPotionEffects(HashMap<Player, ArrayList<PotionEffect>> appliedPotionEffects) {
        this.appliedPotionEffects = appliedPotionEffects;
    }

    public HashMap<Player, Integer> getAbilityCooldown() {
        return abilityCooldown;
    }
    public void setAbilityCooldown(HashMap<Player, Integer> abilityCooldown) {
        this.abilityCooldown = abilityCooldown;
    }

    public HashMap<Player, Integer> getSkillCooldown() {
        return skillCooldown;
    }
    public void setSkillCooldown(HashMap<Player, Integer> skillCooldown) {
        this.skillCooldown = skillCooldown;
    }

    public int getVariationTime() { return variationTime; }
    public void setVariationTime(int variationTime) { this.variationTime = variationTime; }
    public HashMap<Player, Integer> getVariationDelay() {
        return variationDelay;
    }
    public void setVariationDelay(HashMap<Player, Integer> variationDelay) {
        this.variationDelay = variationDelay;
    }
    public HashMap<Player, Integer> getVariationLevel() {
        return variationLevel;
    }
    public void setVariationLevel(HashMap<Player, Integer> variationLevel) {
        this.variationLevel = variationLevel;
    }

    public int getCarrierVariationLevel() {
        return carrierVariationLevel;
    }
    public void setCarrierVariationLevel(int carrierVariationLevel) {
        this.carrierVariationLevel = carrierVariationLevel;
    }

    public int getNeededKillCount() {
        return neededKillCount;
    }
    public void setNeededKillCount(int neededKillCount) {
        this.neededKillCount = neededKillCount;
    }

    public HashMap<Player, Integer> getKillCount() {
        return killCount;
    }
    public void setKillCount(HashMap<Player, Integer> killCount) {
        this.killCount = killCount;
    }
    public HashMap<Player, Integer> getDeathCount() {
        return deathCount;
    }
    public void setDeathCount(HashMap<Player, Integer> deathCount) {
        this.deathCount = deathCount;
    }

    public HashMap<Player, Integer> getAcquiredSupplyCount() {
        return acquiredSupplyCount;
    }
    public void setAcquiredSupplyCount(HashMap<Player, Integer> acquiredSupplyCount) {
        this.acquiredSupplyCount = acquiredSupplyCount;
    }

    public int getWinGoldReward() {
        return winGoldReward;
    }
    public void setWinGoldReward(int winGoldReward) {
        this.winGoldReward = winGoldReward;
    }
    public int getWinEmeraldReward() {
        return winEmeraldReward;
    }
    public void setWinEmeraldReward(int winEmeraldReward) {
        this.winEmeraldReward = winEmeraldReward;
    }
    public int getLoseGoldReward() {
        return loseGoldReward;
    }
    public void setLoseGoldReward(int loseGoldReward) {
        this.loseGoldReward = loseGoldReward;
    }
    public int getLoseEmeraldReward() {
        return loseEmeraldReward;
    }
    public void setLoseEmeraldReward(int loseEmeraldReward) {
        this.loseEmeraldReward = loseEmeraldReward;
    }
    public int getKillReward() {
        return killReward;
    }
    public void setKillReward(int killReward) {
        this.killReward = killReward;
    }
    public int getCureReward() {
        return cureReward;
    }
    public void setCureReward(int cureReward) {
        this.cureReward = cureReward;
    }
    public int getDeathReward() {
        return deathReward;
    }
    public void setDeathReward(int deathReward) {
        this.deathReward = deathReward;
    }
    public int getEscapePenalty() {
        return escapePenalty;
    }
    public void setEscapePenalty(int escapePenalty) {
        this.escapePenalty = escapePenalty;
    }
    public int getErrorReward() {
        return errorReward;
    }
    public void setErrorReward(int errorReward) {
        this.errorReward = errorReward;
    }
    public int getJumpMapReward() {
        return jumpMapReward;
    }
    public void setJumpMapReward(int jumpMapReward) {
        this.jumpMapReward = jumpMapReward;
    }
    public int getHuntingGroundGoldReward() {
        return huntingGroundGoldReward;
    }
    public void setHuntingGroundGoldReward(int huntingGroundGoldReward) {
        this.huntingGroundGoldReward = huntingGroundGoldReward;
    }
    public int getHuntingGroundEmeraldReward() {
        return huntingGroundEmeraldReward;
    }
    public void setHuntingGroundEmeraldReward(int huntingGroundEmeraldReward) {
        this.huntingGroundEmeraldReward = huntingGroundEmeraldReward;
    }
    public int getHuntingGroundDiamondReward() {
        return huntingGroundDiamondReward;
    }
    public void setHuntingGroundDiamondReward(int huntingGroundDiamondReward) {
        this.huntingGroundDiamondReward = huntingGroundDiamondReward;
    }
    public int getNewUserBonusReward() {
        return newUserBonusReward;
    }
    public void setNewUserBonusReward(int newUserBonusReward) {
        this.newUserBonusReward = newUserBonusReward;
    }

    public ArrayList<Integer> getClassExperience() {
        return classExperience;
    }
    public void setClassExperience(ArrayList<Integer> classExperience) {
        this.classExperience = classExperience;
    }
    public ArrayList<Integer> getTypeExperience() {
        return typeExperience;
    }
    public void setTypeExperience(ArrayList<Integer> typeExperience) {
        this.typeExperience = typeExperience;
    }

    public ArrayList<String> getClassName() {
        return className;
    }
    public void setClassName(ArrayList<String> className) {
        this.className = className;
    }
    public ArrayList<String> getTypeName() {
        return typeName;
    }
    public void setTypeName(ArrayList<String> typeName) {
        this.typeName = typeName;
    }
    public ArrayList<String> getMainWeaponName() {
        return mainWeaponName;
    }
    public void setMainWeaponName(ArrayList<String> mainWeaponName) {
        this.mainWeaponName = mainWeaponName;
    }
    public ArrayList<String> getSubWeaponName() {
        return subWeaponName;
    }
    public void setSubWeaponName(ArrayList<String> subWeaponName) {
        this.subWeaponName = subWeaponName;
    }
    public ArrayList<String> getMeleeWeaponName() {
        return meleeWeaponName;
    }
    public void setMeleeWeaponName(ArrayList<String> meleeWeaponName) {
        this.meleeWeaponName = meleeWeaponName;
    }

    public ArrayList<String> getBronzeMainWeaponName() {
        return bronzeMainWeaponName;
    }
    public void setBronzeMainWeaponName(ArrayList<String> bronzeMainWeaponName) {
        this.bronzeMainWeaponName = bronzeMainWeaponName;
    }
    public ArrayList<String> getSilverMainWeaponName() {
        return silverMainWeaponName;
    }
    public void setSilverMainWeaponName(ArrayList<String> silverMainWeaponName) {
        this.silverMainWeaponName = silverMainWeaponName;
    }
    public ArrayList<String> getGoldMainWeaponName() {
        return goldMainWeaponName;
    }
    public void setGoldMainWeaponName(ArrayList<String> goldMainWeaponName) {
        this.goldMainWeaponName = goldMainWeaponName;
    }

    public ArrayList<Integer> getMainWeaponMaxLevel() {
        return mainWeaponMaxLevel;
    }
    public void setMainWeaponMaxLevel(ArrayList<Integer> mainWeaponMaxLevel) {
        this.mainWeaponMaxLevel = mainWeaponMaxLevel;
    }

    public ItemStack getPoison() {
        return poison;
    }
    public void setPoison(ItemStack poison) {
        this.poison = poison;
    }
    public ItemStack getLiquidMedicine() {
        return liquidMedicine;
    }
    public void setLiquidMedicine(ItemStack liquidMedicine) {
        this.liquidMedicine = liquidMedicine;
    }
    public ItemStack getBomb() {
        return bomb;
    }
    public void setBomb(ItemStack bomb) {
        this.bomb = bomb;
    }
    public ItemStack getWaterMedicine() {
        return waterMedicine;
    }
    public void setWaterMedicine(ItemStack waterMedicine) {
        this.waterMedicine = waterMedicine;
    }
    public ItemStack getRemedy() {
        return remedy;
    }
    public void setRemedy(ItemStack remedy) {
        this.remedy = remedy;
    }
    public ItemStack getElixir() {
        return elixir;
    }
    public void setElixir(ItemStack elixir) {
        this.elixir = elixir;
    }
    public ItemStack getAmmoBox() {
        return ammoBox;
    }
    public void setAmmoBox(ItemStack ammoBox) {
        this.ammoBox = ammoBox;
    }
    public ItemStack getLime() {
        return lime;
    }
    public void setLime(ItemStack lime) {
        this.lime = lime;
    }

    public ItemStack getLeap() {
        return leap;
    }
    public void setLeap(ItemStack leap) {
        this.leap = leap;
    }
    public ItemStack getRunaway() {
        return runaway;
    }
    public void setRunaway(ItemStack runaway) {
        this.runaway = runaway;
    }
    public ItemStack getCure() {
        return cure;
    }
    public void setCure(ItemStack cure) {
        this.cure = cure;
    }
    public ItemStack getStrengthen() {
        return strengthen;
    }
    public void setStrengthen(ItemStack strengthen) {
        this.strengthen = strengthen;
    }
    public ItemStack getUndying() {
        return undying;
    }
    public void setUndying(ItemStack undying) {
        this.undying = undying;
    }
    public ItemStack getReversal() {
        return reversal;
    }
    public void setReversal(ItemStack reversal) {
        this.reversal = reversal;
    }
    public ItemStack getFlight() {
        return flight;
    }
    public void setFlight(ItemStack flight) {
        this.flight = flight;
    }
    public ItemStack getHide() {
        return hide;
    }
    public void setHide(ItemStack hide) {
        this.hide = hide;
    }
    public ItemStack getShock() {
        return shock;
    }
    public void setShock(ItemStack shock) {
        this.shock = shock;
    }
    public ItemStack getBioBomb() {
        return bioBomb;
    }
    public void setBioBomb(ItemStack bioBomb) {
        this.bioBomb = bioBomb;
    }
    public ItemStack getPulling() {
        return pulling;
    }
    public void setPulling(ItemStack pulling) {
        this.pulling = pulling;
    }
    public ItemStack getClairvoyance() {
        return clairvoyance;
    }
    public void setClairvoyance(ItemStack clairvoyance) {
        this.clairvoyance = clairvoyance;
    }

    public ItemStack getRake() {
        return rake;
    }
    public void setRake(ItemStack rake) {
        this.rake = rake;
    }

    public ItemStack getAmmo() {
        return ammo;
    }
    public void setAmmo(ItemStack ammo) {
        this.ammo = ammo;
    }

    public ItemStack getHealthBoost() {
        return healthBoost;
    }
    public void setHealthBoost(ItemStack healthBoost) {
        this.healthBoost = healthBoost;
    }
    public ItemStack getRegeneration() {
        return regeneration;
    }
    public void setRegeneration(ItemStack regeneration) {
        this.regeneration = regeneration;
    }
    public ItemStack getDamageResistance() {
        return damageResistance;
    }
    public void setDamageResistance(ItemStack damageResistance) {
        this.damageResistance = damageResistance;
    }
    public ItemStack getSpeed() {
        return speed;
    }
    public void setSpeed(ItemStack speed) {
        this.speed = speed;
    }
    public ItemStack getJump() {
        return jump;
    }
    public void setJump(ItemStack jump) {
        this.jump = jump;
    }

    public ItemStack getDiamond500ExchangeTicket() {
        return diamond500ExchangeTicket;
    }
    public void setDiamond500ExchangeTicket(ItemStack diamond500ExchangeTicket) {
        this.diamond500ExchangeTicket = diamond500ExchangeTicket;
    }
    public ItemStack getDiamond5500ExchangeTicket() {
        return diamond5500ExchangeTicket;
    }
    public void setDiamond5500ExchangeTicket(ItemStack diamond5500ExchangeTicket) {
        this.diamond5500ExchangeTicket = diamond5500ExchangeTicket;
    }
    public ItemStack getEmerald1000ExchangeTicket() {
        return emerald1000ExchangeTicket;
    }
    public void setEmerald1000ExchangeTicket(ItemStack emerald1000ExchangeTicket) {
        this.emerald1000ExchangeTicket = emerald1000ExchangeTicket;
    }
    public ItemStack getGold5000ExchangeTicket() {
        return gold5000ExchangeTicket;
    }
    public void setGold5000ExchangeTicket(ItemStack gold5000ExchangeTicket) {
        this.gold5000ExchangeTicket = gold5000ExchangeTicket;
    }
    public ItemStack getGold55000ExchangeTicket() {
        return gold55000ExchangeTicket;
    }
    public void setGold55000ExchangeTicket(ItemStack gold55000ExchangeTicket) {
        this.gold55000ExchangeTicket = gold55000ExchangeTicket;
    }
    public ItemStack getNormalWeaponBox() {
        return normalWeaponBox;
    }
    public void setNormalWeaponBox(ItemStack normalWeaponBox) {
        this.normalWeaponBox = normalWeaponBox;
    }
    public ItemStack getSpecialWeaponBox() {
        return specialWeaponBox;
    }
    public void setSpecialWeaponBox(ItemStack specialWeaponBox) {
        this.specialWeaponBox = specialWeaponBox;
    }
    public ItemStack getPremiumWeaponBox() {
        return premiumWeaponBox;
    }
    public void setPremiumWeaponBox(ItemStack premiumWeaponBox) {
        this.premiumWeaponBox = premiumWeaponBox;
    }
    public ItemStack getNormalMoneyBox() {
        return normalMoneyBox;
    }
    public void setNormalMoneyBox(ItemStack normalMoneyBox) {
        this.normalMoneyBox = normalMoneyBox;
    }
    public ItemStack getPremiumMoneyBox() {
        return premiumMoneyBox;
    }
    public void setPremiumMoneyBox(ItemStack premiumMoneyBox) {
        this.premiumMoneyBox = premiumMoneyBox;
    }
    public ItemStack getMinelistBox() {
        return minelistBox;
    }
    public void setMinelistBox(ItemStack minelistBox) {
        this.minelistBox = minelistBox;
    }
    public ItemStack getGreenBorder() {
        return greenBorder;
    }
    public void setGreenBorder(ItemStack greenBorder) {
        this.greenBorder = greenBorder;
    }
    public ItemStack getSkyblueBorder() {
        return skyblueBorder;
    }
    public void setSkyblueBorder(ItemStack skyblueBorder) {
        this.skyblueBorder = skyblueBorder;
    }
    public ItemStack getPinkBorder() {
        return pinkBorder;
    }
    public void setPinkBorder(ItemStack pinkBorder) {
        this.pinkBorder = pinkBorder;
    }
    public ItemStack getMagentaBorder() {
        return magentaBorder;
    }
    public void setMagentaBorder(ItemStack magentaBorder) {
        this.magentaBorder = magentaBorder;
    }
    public ItemStack getYellowBorder() {
        return yellowBorder;
    }
    public void setYellowBorder(ItemStack yellowBorder) {
        this.yellowBorder = yellowBorder;
    }
    public ItemStack getGreenPrefix() {
        return greenPrefix;
    }
    public void setGreenPrefix(ItemStack greenPrefix) {
        this.greenPrefix = greenPrefix;
    }
    public ItemStack getSkybluePrefix() {
        return skybluePrefix;
    }
    public void setSkybluePrefix(ItemStack skybluePrefix) {
        this.skybluePrefix = skybluePrefix;
    }
    public ItemStack getPinkPrefix() {
        return pinkPrefix;
    }
    public void setPinkPrefix(ItemStack pinkPrefix) {
        this.pinkPrefix = pinkPrefix;
    }
    public ItemStack getMagentaPrefix() {
        return magentaPrefix;
    }
    public void setMagentaPrefix(ItemStack magentaPrefix) {
        this.magentaPrefix = magentaPrefix;
    }
    public ItemStack getYellowPrefix() {
        return yellowPrefix;
    }
    public void setYellowPrefix(ItemStack yellowPrefix) {
        this.yellowPrefix = yellowPrefix;
    }
    public ItemStack getGreenNick() {
        return greenNick;
    }
    public void setGreenNick(ItemStack greenNick) {
        this.greenNick = greenNick;
    }
    public ItemStack getSkyblueNick() {
        return skyblueNick;
    }
    public void setSkyblueNick(ItemStack skyblueNick) {
        this.skyblueNick = skyblueNick;
    }
    public ItemStack getPinkNick() {
        return pinkNick;
    }
    public void setPinkNick(ItemStack pinkNick) {
        this.pinkNick = pinkNick;
    }
    public ItemStack getMagentaNick() {
        return magentaNick;
    }
    public void setMagentaNick(ItemStack magentaNick) {
        this.magentaNick = magentaNick;
    }
    public ItemStack getYellowNick() {
        return yellowNick;
    }
    public void setYellowNick(ItemStack yellowNick) {
        this.yellowNick = yellowNick;
    }
    public ItemStack getGreenChat() {
        return greenChat;
    }
    public void setGreenChat(ItemStack greenChat) {
        this.greenChat = greenChat;
    }
    public ItemStack getSkyblueChat() {
        return skyblueChat;
    }
    public void setSkyblueChat(ItemStack skyblueChat) {
        this.skyblueChat = skyblueChat;
    }
    public ItemStack getPinkChat() {
        return pinkChat;
    }
    public void setPinkChat(ItemStack pinkChat) {
        this.pinkChat = pinkChat;
    }
    public ItemStack getMagentaChat() {
        return magentaChat;
    }
    public void setMagentaChat(ItemStack magentaChat) {
        this.magentaChat = magentaChat;
    }
    public ItemStack getYellowChat() {
        return yellowChat;
    }
    public void setYellowChat(ItemStack yellowChat) {
        this.yellowChat = yellowChat;
    }
    public ItemStack getEventHold() {
        return eventHold;
    }
    public void setEventHold(ItemStack eventHold) {
        this.eventHold = eventHold;
    }

    public int getMaxBoxOpenCount() {
        return maxBoxOpenCount;
    }
    public void setMaxBoxOpenCount(int maxBoxOpenCount) {
        this.maxBoxOpenCount = maxBoxOpenCount;
    }

    public ItemStack getBlackPane() {
        return blackPane;
    }
    public void setBlackPane(ItemStack blackPane) {
        this.blackPane = blackPane;
    }
    public ItemStack getWhitePane() {
        return whitePane;
    }
    public void setWhitePane(ItemStack whitePane) {
        this.whitePane = whitePane;
    }
    public ItemStack getGrayPane() {
        return grayPane;
    }
    public void setGrayPane(ItemStack grayPane) {
        this.grayPane = grayPane;
    }
    public ItemStack getBluePane() {
        return bluePane;
    }
    public void setBluePane(ItemStack bluePane) {
        this.bluePane = bluePane;
    }
    public ItemStack getRedPane() {
        return redPane;
    }
    public void setRedPane(ItemStack redPane) {
        this.redPane = redPane;
    }

    public ItemStack getPrevPage() {
        return prevPage;
    }
    public void setPrevPage(ItemStack prevPage) {
        this.prevPage = prevPage;
    }
    public ItemStack getNextPage() {
        return nextPage;
    }
    public void setNextPage(ItemStack nextPage) {
        this.nextPage = nextPage;
    }
}
