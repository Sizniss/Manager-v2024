package kr.sizniss.manager.guis;

import com.shampaggon.crackshot.CSUtility;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Files;
import kr.sizniss.manager.guis.shopgui.PresentGUI;
import net.dv8tion.jda.api.JDA;
import org.bukkit.*;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static kr.sizniss.manager.Manager.*;

public class ShopGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();

    private int diamond500ExchangeTicketPrice = 5000; // 캐시
    private int diamond5500ExchangeTicketPrice = 50000; // 캐시
    private int emerald1000ExchangeTicketPrice = 10000; // 캐시
    private int gold5000ExchangeTicketPrice = 500; // 캐시
    private int gold55000ExchangeTicketPrice = 5000; // 캐시

    private int normalWeaponBoxPrice = 1000; // 골드
    private int specialWeaponBoxPrice = 100000; // 골드
    private int premiumWeaponBoxPrice = 100; // 캐시

    private int codiPrice = 5000;// 캐시

    private int greenBorderPrice = 5000; // 캐시
    private int skyblueBorderPrice = 5000; // 캐시
    private int pinkBorderPrice = 5000; // 캐시
    private int magentaBorderPrice = 5000; // 캐시
    private int yellowBorderPrice = 5000; // 캐시
    private int greenPrefixPrice = 5000; // 캐시
    private int skybluePrefixPrice = 5000; // 캐시
    private int pinkPrefixPrice = 5000; // 캐시
    private int magentaPrefixPrice = 5000; // 캐시
    private int yellowPrefixPrice = 5000; // 캐시
    private int greenNickPrice = 5000; // 캐시
    private int skyblueNickPrice = 5000; // 캐시
    private int pinkNickPrice = 5000; // 캐시
    private int magentaNickPrice = 5000; // 캐시
    private int yellowNickPrice = 5000; // 캐시
    private int greenChatPrice = 5000; // 캐시
    private int skyblueChatPrice = 5000; // 캐시
    private int pinkChatPrice = 5000; // 캐시
    private int magentaChatPrice = 5000; // 캐시
    private int yellowChatPrice = 5000; // 캐시

    private int Desert_EaglePrice = 200000; // 골드
    private int Beretta_M93RPrice = 200000; // 골드
    private int M79_SawedOffPrice = 2000; // 에메랄드
    private int ShurikenPrice = 2000; // 다이아몬드
    private int Duck_FootPrice = 2000; // 에메랄드
    private int TaserGunPrice = 2000; // 다이아몬드
    private int Double_Barrel_SawedOffPrice = 200000; // 골드
    private int M950Price = 2000; // 다이아몬드
    private int TorchPrice = 2000; // 에메랄드
    private int MK_13_EGLMPrice = 2000; // 다이아몬드
    
    private int CrowBarPrice = 200000; // 골드
    private int KujangPrice = 2000; // 에메랄드
    private int BuchaePrice = 2000; // 다이아몬드
    private int Rose_KnifePrice = 2000; // 에메랄드
    private int HammerPrice = 200000; // 골드
    private int KatanaPrice = 200000; // 골드
    private int Taser_KnifePrice = 2000; // 에메랄드
    private int CheongRyongDoPrice = 2000; // 다이아몬드
    private int ClaymorePrice = 2000; // 다이아몬드
    private int WarHammerPrice = 2000; // 에메랄드

    private int eventHoldPrice = 100; // 다이아몬드


    public ShopGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "상점"); // 인벤토리 생성

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


        // 캐시
        ItemStack cash = new ItemStack(Material.MAP);
        ItemMeta cashMeta = cash.getItemMeta();
        cashMeta.setDisplayName("§f" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Cash")) + " 캐시");
        cash.setItemMeta(cashMeta);

        // 골드
        ItemStack gold = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName("§e" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Gold")) + " 골드");
        gold.setItemMeta(goldMeta);

        // 다이아몬드
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setDisplayName("§b" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond")) + " 다이아몬드");
        diamond.setItemMeta(diamondMeta);

        // 에메랄드
        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName("§a" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald")) + " 에메랄드");
        emerald.setItemMeta(emeraldMeta);


        // 상품 - 500 다이아몬드 교환권
        ItemStack diamond500ExchangeTicket = variables.getDiamond500ExchangeTicket().clone();
        ItemMeta diamond500ExchangeTicketMeta = diamond500ExchangeTicket.getItemMeta();
        ArrayList<String> diamond500ExchangeTicketLore = new ArrayList<String>();
        diamond500ExchangeTicketLore.add("");
        diamond500ExchangeTicketLore.add(" §f※ 구매");
        diamond500ExchangeTicketLore.add("");
        diamond500ExchangeTicketLore.add(" §e→ §f비용: " + new DecimalFormat("###,###").format(diamond500ExchangeTicketPrice) + " 캐시");
        diamond500ExchangeTicketLore.add("");
        diamond500ExchangeTicketMeta.setLore(diamond500ExchangeTicketLore);
        diamond500ExchangeTicket.setItemMeta(diamond500ExchangeTicketMeta);

        // 상품 - 5,500 다이아몬드 교환권
        ItemStack diamond5500ExchangeTicket = variables.getDiamond5500ExchangeTicket().clone();
        ItemMeta diamond5500ExchangeTicketMeta = diamond5500ExchangeTicket.getItemMeta();
        ArrayList<String> diamond5500ExchangeTicketLore = new ArrayList<String>();
        diamond5500ExchangeTicketLore.add("");
        diamond5500ExchangeTicketLore.add(" §f※ 구매");
        diamond5500ExchangeTicketLore.add("");
        diamond5500ExchangeTicketLore.add(" §e→ §f비용: §8§m55,000§f " + new DecimalFormat("###,###").format(diamond5500ExchangeTicketPrice) + " 캐시");
        diamond5500ExchangeTicketLore.add("");
        diamond5500ExchangeTicketMeta.setLore(diamond5500ExchangeTicketLore);
        diamond5500ExchangeTicket.setItemMeta(diamond5500ExchangeTicketMeta);

        // 상품 - 1,000 에메랄드 교환권
        ItemStack emerald1000ExchangeTicket = variables.getEmerald1000ExchangeTicket().clone();
        ItemMeta emerald1000ExchangeTicketMeta = emerald1000ExchangeTicket.getItemMeta();
        ArrayList<String> emerald1000ExchangeTicketLore = new ArrayList<String>();
        emerald1000ExchangeTicketLore.add("");
        emerald1000ExchangeTicketLore.add(" §f※ 구매");
        emerald1000ExchangeTicketLore.add("");
        emerald1000ExchangeTicketLore.add(" §e→ §f비용: " + new DecimalFormat("###,###").format(emerald1000ExchangeTicketPrice) + " 캐시");
        emerald1000ExchangeTicketLore.add("");
        emerald1000ExchangeTicketMeta.setLore(emerald1000ExchangeTicketLore);
        emerald1000ExchangeTicket.setItemMeta(emerald1000ExchangeTicketMeta);

        // 상품 - 5,000 골드 교환권
        ItemStack gold5000ExchangeTicket = variables.getGold5000ExchangeTicket().clone();
        ItemMeta gold5000ExchangeTicketMeta = gold5000ExchangeTicket.getItemMeta();
        ArrayList<String> gold5000ExchangeTicketLore = new ArrayList<String>();
        gold5000ExchangeTicketLore.add("");
        gold5000ExchangeTicketLore.add(" §f※ 구매");
        gold5000ExchangeTicketLore.add("");
        gold5000ExchangeTicketLore.add(" §e→ §f비용: " + new DecimalFormat("###,###").format(gold5000ExchangeTicketPrice) + " 캐시");
        gold5000ExchangeTicketLore.add("");
        gold5000ExchangeTicketMeta.setLore(gold5000ExchangeTicketLore);
        gold5000ExchangeTicket.setItemMeta(gold5000ExchangeTicketMeta);

        // 상품 - 55,000 골드 교환권
        ItemStack gold55000ExchangeTicket = variables.getGold55000ExchangeTicket().clone();
        ItemMeta gold55000ExchangeTicketMeta = gold55000ExchangeTicket.getItemMeta();
        ArrayList<String> gold55000ExchangeTicketLore = new ArrayList<String>();
        gold55000ExchangeTicketLore.add("");
        gold55000ExchangeTicketLore.add(" §f※ 구매");
        gold55000ExchangeTicketLore.add("");
        gold55000ExchangeTicketLore.add(" §e→ §f비용: §8§m5,500§f " + new DecimalFormat("###,###").format(gold55000ExchangeTicketPrice) + " 캐시");
        gold55000ExchangeTicketLore.add("");
        gold55000ExchangeTicketMeta.setLore(gold55000ExchangeTicketLore);
        gold55000ExchangeTicket.setItemMeta(gold55000ExchangeTicketMeta);

        // 상품 - 일반 무기 상자
        ItemStack normalWeaponBox = variables.getNormalWeaponBox().clone();
        ItemMeta normalWeaponBoxMeta = normalWeaponBox.getItemMeta();
        ArrayList<String> normalWeaponBoxLore = (ArrayList<String>) normalWeaponBoxMeta.getLore();
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add(" §f※ 구매");
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(normalWeaponBoxPrice) + " 골드");
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add(" §f※ 사용");
        normalWeaponBoxLore.add("");
        normalWeaponBoxLore.add(" §e→ §f<L-Click>: §71개 구매");
        normalWeaponBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 구매");
        normalWeaponBoxLore.add(" §e→ §f<R-Click>: §71개 선물");
        normalWeaponBoxLore.add(" §e→ §f<Shift> + <R-Click>: §710개 선물");
        normalWeaponBoxLore.add("");
        normalWeaponBoxMeta.setLore(normalWeaponBoxLore);
        normalWeaponBox.setItemMeta(normalWeaponBoxMeta);

        // 상품 - 특별 무기 상자
        ItemStack specialWeaponBox = variables.getSpecialWeaponBox().clone();
        ItemMeta specialWeaponBoxMeta = specialWeaponBox.getItemMeta();
        ArrayList<String> specialWeaponBoxLore = (ArrayList<String>) specialWeaponBoxMeta.getLore();
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add(" §f※ 구매");
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(specialWeaponBoxPrice) + " 골드");
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add(" §f※ 사용");
        specialWeaponBoxLore.add("");
        specialWeaponBoxLore.add(" §e→ §f<L-Click>: §71개 구매");
        specialWeaponBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 구매");
        specialWeaponBoxLore.add(" §e→ §f<R-Click>: §71개 선물");
        specialWeaponBoxLore.add(" §e→ §f<Shift> + <R-Click>: §710개 선물");
        specialWeaponBoxLore.add("");
        specialWeaponBoxMeta.setLore(specialWeaponBoxLore);
        specialWeaponBox.setItemMeta(specialWeaponBoxMeta);

        // 상품 - 고급 무기 상자
        ItemStack premiumWeaponBox = variables.getPremiumWeaponBox().clone();
        ItemMeta premiumWeaponBoxMeta = premiumWeaponBox.getItemMeta();
        ArrayList<String> premiumWeaponBoxLore = (ArrayList<String>) premiumWeaponBoxMeta.getLore();
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §f※ 구매");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(premiumWeaponBoxPrice) + " 다이아몬드");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §f※ 사용");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxLore.add(" §e→ §f<L-Click>: §71개 구매");
        premiumWeaponBoxLore.add(" §e→ §f<Shift> + <L-Click>: §710개 구매");
        premiumWeaponBoxLore.add(" §e→ §f<R-Click>: §71개 선물");
        premiumWeaponBoxLore.add(" §e→ §f<Shift> + <R-Click>: §710개 선물");
        premiumWeaponBoxLore.add("");
        premiumWeaponBoxMeta.setLore(premiumWeaponBoxLore);
        premiumWeaponBox.setItemMeta(premiumWeaponBoxMeta);

        // 상품 - 코디
        ItemStack greenBorder = variables.getGreenBorder().clone();
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Green")) {
            greenBorder.setType(Material.STAINED_GLASS);
            greenBorder.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta greenBorderMeta = greenBorder.getItemMeta();
            ArrayList<String> greenBorderLore = new ArrayList<String>();
            greenBorderLore.add("");
            greenBorderLore.add(" §f※ 구매");
            greenBorderLore.add("");
            greenBorderLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(greenBorderPrice) + " 캐시");
            greenBorderLore.add("");
            greenBorderMeta.setLore(greenBorderLore);
            greenBorder.setItemMeta(greenBorderMeta);
        }

        ItemStack skyblueBorder = variables.getSkyblueBorder().clone();
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Skyblue")) {
            skyblueBorder.setType(Material.STAINED_GLASS);
            skyblueBorder.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta skyblueBorderMeta = skyblueBorder.getItemMeta();
            ArrayList<String> skyblueBorderLore = new ArrayList<String>();
            skyblueBorderLore.add("");
            skyblueBorderLore.add(" §f※ 구매");
            skyblueBorderLore.add("");
            skyblueBorderLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(skyblueBorderPrice) + " 캐시");
            skyblueBorderLore.add("");
            skyblueBorderMeta.setLore(skyblueBorderLore);
            skyblueBorder.setItemMeta(skyblueBorderMeta);
        }

        ItemStack pinkBorder = variables.getPinkBorder().clone();
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Pink")) {
            pinkBorder.setType(Material.STAINED_GLASS);
            pinkBorder.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta pinkBorderMeta = pinkBorder.getItemMeta();
            ArrayList<String> pinkBorderLore = new ArrayList<String>();
            pinkBorderLore.add("");
            pinkBorderLore.add(" §f※ 구매");
            pinkBorderLore.add("");
            pinkBorderLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(pinkBorderPrice) + " 캐시");
            pinkBorderLore.add("");
            pinkBorderMeta.setLore(pinkBorderLore);
            pinkBorder.setItemMeta(pinkBorderMeta);
        }

        ItemStack magentaBorder = variables.getMagentaBorder().clone();
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Magenta")) {
            magentaBorder.setType(Material.STAINED_GLASS);
            magentaBorder.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta magentaBorderMeta = magentaBorder.getItemMeta();
            ArrayList<String> magentaBorderLore = new ArrayList<String>();
            magentaBorderLore.add("");
            magentaBorderLore.add(" §f※ 구매");
            magentaBorderLore.add("");
            magentaBorderLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(magentaBorderPrice) + " 캐시");
            magentaBorderLore.add("");
            magentaBorderMeta.setLore(magentaBorderLore);
            magentaBorder.setItemMeta(magentaBorderMeta);
        }

        ItemStack yellowBorder = variables.getYellowBorder().clone();
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Yellow")) {
            yellowBorder.setType(Material.STAINED_GLASS);
            yellowBorder.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta yellowBorderMeta = yellowBorder.getItemMeta();
            ArrayList<String> yellowBorderLore = new ArrayList<String>();
            yellowBorderLore.add("");
            yellowBorderLore.add(" §f※ 구매");
            yellowBorderLore.add("");
            yellowBorderLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(yellowBorderPrice) + " 캐시");
            yellowBorderLore.add("");
            yellowBorderMeta.setLore(yellowBorderLore);
            yellowBorder.setItemMeta(yellowBorderMeta);
        }

        ItemStack greenPrefix = variables.getGreenPrefix().clone();
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Green")) {
            greenPrefix.setType(Material.STAINED_GLASS);
            greenPrefix.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta greenPrefixMeta = greenPrefix.getItemMeta();
            ArrayList<String> greenPrefixLore = new ArrayList<String>();
            greenPrefixLore.add("");
            greenPrefixLore.add(" §f※ 구매");
            greenPrefixLore.add("");
            greenPrefixLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(greenPrefixPrice) + " 캐시");
            greenPrefixLore.add("");
            greenPrefixMeta.setLore(greenPrefixLore);
            greenPrefix.setItemMeta(greenPrefixMeta);
        }

        ItemStack skybluePrefix = variables.getSkybluePrefix().clone();
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Skyblue")) {
            skybluePrefix.setType(Material.STAINED_GLASS);
            skybluePrefix.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta skybluePrefixMeta = skybluePrefix.getItemMeta();
            ArrayList<String> skybluePrefixLore = new ArrayList<String>();
            skybluePrefixLore.add("");
            skybluePrefixLore.add(" §f※ 구매");
            skybluePrefixLore.add("");
            skybluePrefixLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(skybluePrefixPrice) + " 캐시");
            skybluePrefixLore.add("");
            skybluePrefixMeta.setLore(skybluePrefixLore);
            skybluePrefix.setItemMeta(skybluePrefixMeta);
        }

        ItemStack pinkPrefix = variables.getPinkPrefix().clone();
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Pink")) {
            pinkPrefix.setType(Material.STAINED_GLASS);
            pinkPrefix.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta pinkPrefixMeta = pinkPrefix.getItemMeta();
            ArrayList<String> pinkPrefixLore = new ArrayList<String>();
            pinkPrefixLore.add("");
            pinkPrefixLore.add(" §f※ 구매");
            pinkPrefixLore.add("");
            pinkPrefixLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(pinkPrefixPrice) + " 캐시");
            pinkPrefixLore.add("");
            pinkPrefixMeta.setLore(pinkPrefixLore);
            pinkPrefix.setItemMeta(pinkPrefixMeta);
        }

        ItemStack magentaPrefix = variables.getMagentaPrefix().clone();
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Magenta")) {
            magentaPrefix.setType(Material.STAINED_GLASS);
            magentaPrefix.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta magentaPrefixMeta = magentaPrefix.getItemMeta();
            ArrayList<String> magentaPrefixLore = new ArrayList<String>();
            magentaPrefixLore.add("");
            magentaPrefixLore.add(" §f※ 구매");
            magentaPrefixLore.add("");
            magentaPrefixLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(magentaPrefixPrice) + " 캐시");
            magentaPrefixLore.add("");
            magentaPrefixMeta.setLore(magentaPrefixLore);
            magentaPrefix.setItemMeta(magentaPrefixMeta);
        }

        ItemStack yellowPrefix = variables.getYellowPrefix().clone();
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Yellow")) {
            yellowPrefix.setType(Material.STAINED_GLASS);
            yellowPrefix.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta yellowPrefixMeta = yellowPrefix.getItemMeta();
            ArrayList<String> yellowPrefixLore = new ArrayList<String>();
            yellowPrefixLore.add("");
            yellowPrefixLore.add(" §f※ 구매");
            yellowPrefixLore.add("");
            yellowPrefixLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(yellowPrefixPrice) + " 캐시");
            yellowPrefixLore.add("");
            yellowPrefixMeta.setLore(yellowPrefixLore);
            yellowPrefix.setItemMeta(yellowPrefixMeta);
        }

        ItemStack greenNick = variables.getGreenNick().clone();
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Green")) {
            greenNick.setType(Material.STAINED_GLASS);
            greenNick.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta greenNickMeta = greenNick.getItemMeta();
            ArrayList<String> greenNickLore = new ArrayList<String>();
            greenNickLore.add("");
            greenNickLore.add(" §f※ 구매");
            greenNickLore.add("");
            greenNickLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(greenNickPrice) + " 캐시");
            greenNickLore.add("");
            greenNickMeta.setLore(greenNickLore);
            greenNick.setItemMeta(greenNickMeta);
        }

        ItemStack skyblueNick = variables.getSkyblueNick().clone();
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Skyblue")) {
            skyblueNick.setType(Material.STAINED_GLASS);
            skyblueNick.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta skyblueNickMeta = skyblueNick.getItemMeta();
            ArrayList<String> skyblueNickLore = new ArrayList<String>();
            skyblueNickLore.add("");
            skyblueNickLore.add(" §f※ 구매");
            skyblueNickLore.add("");
            skyblueNickLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(skyblueNickPrice) + " 캐시");
            skyblueNickLore.add("");
            skyblueNickMeta.setLore(skyblueNickLore);
            skyblueNick.setItemMeta(skyblueNickMeta);
        }

        ItemStack pinkNick = variables.getPinkNick().clone();
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Pink")) {
            pinkNick.setType(Material.STAINED_GLASS);
            pinkNick.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta pinkNickMeta = pinkNick.getItemMeta();
            ArrayList<String> pinkNickLore = new ArrayList<String>();
            pinkNickLore.add("");
            pinkNickLore.add(" §f※ 구매");
            pinkNickLore.add("");
            pinkNickLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(pinkNickPrice) + " 캐시");
            pinkNickLore.add("");
            pinkNickMeta.setLore(pinkNickLore);
            pinkNick.setItemMeta(pinkNickMeta);
        }

        ItemStack magentaNick = variables.getMagentaNick().clone();
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Magenta")) {
            magentaNick.setType(Material.STAINED_GLASS);
            magentaNick.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta magentaNickMeta = magentaNick.getItemMeta();
            ArrayList<String> magentaNickLore = new ArrayList<String>();
            magentaNickLore.add("");
            magentaNickLore.add(" §f※ 구매");
            magentaNickLore.add("");
            magentaNickLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(magentaNickPrice) + " 캐시");
            magentaNickLore.add("");
            magentaNickMeta.setLore(magentaNickLore);
            magentaNick.setItemMeta(magentaNickMeta);
        }

        ItemStack yellowNick = variables.getYellowNick().clone();
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Yellow")) {
            yellowNick.setType(Material.STAINED_GLASS);
            yellowNick.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta yellowNickMeta = yellowNick.getItemMeta();
            ArrayList<String> yellowNickLore = new ArrayList<String>();
            yellowNickLore.add("");
            yellowNickLore.add(" §f※ 구매");
            yellowNickLore.add("");
            yellowNickLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(yellowNickPrice) + " 캐시");
            yellowNickLore.add("");
            yellowNickMeta.setLore(yellowNickLore);
            yellowNick.setItemMeta(yellowNickMeta);
        }

        ItemStack greenChat = variables.getGreenChat().clone();
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Green")) {
            greenChat.setType(Material.STAINED_GLASS);
            greenChat.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta greenChatMeta = greenChat.getItemMeta();
            ArrayList<String> greenChatLore = new ArrayList<String>();
            greenChatLore.add("");
            greenChatLore.add(" §f※ 구매");
            greenChatLore.add("");
            greenChatLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(greenChatPrice) + " 캐시");
            greenChatLore.add("");
            greenChatMeta.setLore(greenChatLore);
            greenChat.setItemMeta(greenChatMeta);
        }

        ItemStack skyblueChat = variables.getSkyblueChat().clone();
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Skyblue")) {
            skyblueChat.setType(Material.STAINED_GLASS);
            skyblueChat.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta skyblueChatMeta = skyblueChat.getItemMeta();
            ArrayList<String> skyblueChatLore = new ArrayList<String>();
            skyblueChatLore.add("");
            skyblueChatLore.add(" §f※ 구매");
            skyblueChatLore.add("");
            skyblueChatLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(skyblueChatPrice) + " 캐시");
            skyblueChatLore.add("");
            skyblueChatMeta.setLore(skyblueChatLore);
            skyblueChat.setItemMeta(skyblueChatMeta);
        }

        ItemStack pinkChat = variables.getPinkChat().clone();
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Pink")) {
            pinkChat.setType(Material.STAINED_GLASS);
            pinkChat.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta pinkChatMeta = pinkChat.getItemMeta();
            ArrayList<String> pinkChatLore = new ArrayList<String>();
            pinkChatLore.add("");
            pinkChatLore.add(" §f※ 구매");
            pinkChatLore.add("");
            pinkChatLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(pinkChatPrice) + " 캐시");
            pinkChatLore.add("");
            pinkChatMeta.setLore(pinkChatLore);
            pinkChat.setItemMeta(pinkChatMeta);
        }

        ItemStack magentaChat = variables.getMagentaChat().clone();
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Magenta")) {
            magentaChat.setType(Material.STAINED_GLASS);
            magentaChat.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta magentaChatMeta = magentaChat.getItemMeta();
            ArrayList<String> magentaChatLore = new ArrayList<String>();
            magentaChatLore.add("");
            magentaChatLore.add(" §f※ 구매");
            magentaChatLore.add("");
            magentaChatLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(magentaChatPrice) + " 캐시");
            magentaChatLore.add("");
            magentaChatMeta.setLore(magentaChatLore);
            magentaChat.setItemMeta(magentaChatMeta);
        }

        ItemStack yellowChat = variables.getYellowChat().clone();
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Yellow")) {
            yellowChat.setType(Material.STAINED_GLASS);
            yellowChat.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta yellowChatMeta = yellowChat.getItemMeta();
            ArrayList<String> yellowChatLore = new ArrayList<String>();
            yellowChatLore.add("");
            yellowChatLore.add(" §f※ 구매");
            yellowChatLore.add("");
            yellowChatLore.add(" §e→ §f비용: §f" + new DecimalFormat("###,###").format(yellowChatPrice) + " 캐시");
            yellowChatLore.add("");
            yellowChatMeta.setLore(yellowChatLore);
            yellowChat.setItemMeta(yellowChatMeta);
        }

        // 상품 - 보조 무기
        ItemStack Desert_Eagle = new CSUtility().generateWeapon("Desert_Eagle").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "Desert_Eagle")) {
            Desert_Eagle.setType(Material.STAINED_GLASS);
            Desert_Eagle.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta Desert_EagleMeta = Desert_Eagle.getItemMeta();
            ArrayList<String> Desert_EagleLore = (ArrayList<String>) Desert_EagleMeta.getLore();
            Desert_EagleLore.add("");
            Desert_EagleLore.add("");
            Desert_EagleLore.add(" §f※ 구매");
            Desert_EagleLore.add("");
            Desert_EagleLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(Desert_EaglePrice) + " 골드");
            Desert_EagleMeta.setLore(Desert_EagleLore);
            Desert_Eagle.setItemMeta(Desert_EagleMeta);
        }

        ItemStack Beretta_M93R = new CSUtility().generateWeapon("Beretta_M93R").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "Beretta_M93R")) {
            Beretta_M93R.setType(Material.STAINED_GLASS);
            Beretta_M93R.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta Beretta_M93RMeta = Beretta_M93R.getItemMeta();
            ArrayList<String> Beretta_M93RLore = (ArrayList<String>) Beretta_M93RMeta.getLore();
            Beretta_M93RLore.add("");
            Beretta_M93RLore.add("");
            Beretta_M93RLore.add(" §f※ 구매");
            Beretta_M93RLore.add("");
            Beretta_M93RLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(Beretta_M93RPrice) + " 골드");
            Beretta_M93RMeta.setLore(Beretta_M93RLore);
            Beretta_M93R.setItemMeta(Beretta_M93RMeta);
        }

        ItemStack M79_SawedOff = new CSUtility().generateWeapon("M79_소드오프").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "M79_소드오프")) {
            M79_SawedOff.setType(Material.STAINED_GLASS);
            M79_SawedOff.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta M79_SawedOffMeta = M79_SawedOff.getItemMeta();
            ArrayList<String> M79_SawedOffLore = (ArrayList<String>) M79_SawedOffMeta.getLore();
            M79_SawedOffLore.add("");
            M79_SawedOffLore.add("");
            M79_SawedOffLore.add(" §f※ 구매");
            M79_SawedOffLore.add("");
            M79_SawedOffLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(M79_SawedOffPrice) + " 에메랄드");
            M79_SawedOffMeta.setLore(M79_SawedOffLore);
            M79_SawedOff.setItemMeta(M79_SawedOffMeta);
        }

        ItemStack Shuriken = new CSUtility().generateWeapon("수리검").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "수리검")) {
            Shuriken.setType(Material.STAINED_GLASS);
            Shuriken.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta ShurikenMeta = Shuriken.getItemMeta();
            ArrayList<String> ShurikenLore = (ArrayList<String>) ShurikenMeta.getLore();
            ShurikenLore.add("");
            ShurikenLore.add("");
            ShurikenLore.add(" §f※ 구매");
            ShurikenLore.add("");
            ShurikenLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(ShurikenPrice) + " 다이아몬드");
            ShurikenMeta.setLore(ShurikenLore);
            Shuriken.setItemMeta(ShurikenMeta);
        }

        ItemStack Duck_Foot = new CSUtility().generateWeapon("덕_풋").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "덕_풋")) {
            Duck_Foot.setType(Material.STAINED_GLASS);
            Duck_Foot.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta Duck_FootMeta = Duck_Foot.getItemMeta();
            ArrayList<String> Duck_FootLore = (ArrayList<String>) Duck_FootMeta.getLore();
            Duck_FootLore.add("");
            Duck_FootLore.add("");
            Duck_FootLore.add(" §f※ 구매");
            Duck_FootLore.add("");
            Duck_FootLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(Duck_FootPrice) + " 에메랄드");
            Duck_FootMeta.setLore(Duck_FootLore);
            Duck_Foot.setItemMeta(Duck_FootMeta);
        }

        ItemStack TaserGun = new CSUtility().generateWeapon("테이저건").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "테이저건")) {
            TaserGun.setType(Material.STAINED_GLASS);
            TaserGun.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta TaserGunMeta = TaserGun.getItemMeta();
            ArrayList<String> TaserGunLore = (ArrayList<String>) TaserGunMeta.getLore();
            TaserGunLore.add("");
            TaserGunLore.add("");
            TaserGunLore.add(" §f※ 구매");
            TaserGunLore.add("");
            TaserGunLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(TaserGunPrice) + " 다이아몬드");
            TaserGunMeta.setLore(TaserGunLore);
            TaserGun.setItemMeta(TaserGunMeta);
        }

        ItemStack Double_Barrel_SawedOff = new CSUtility().generateWeapon("Double_Barrel_소드오프").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "Double_Barrel_소드오프")) {
            Double_Barrel_SawedOff.setType(Material.STAINED_GLASS);
            Double_Barrel_SawedOff.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta Double_Barrel_SawedOffMeta = Double_Barrel_SawedOff.getItemMeta();
            ArrayList<String> Double_Barrel_SawedOffLore = (ArrayList<String>) Double_Barrel_SawedOffMeta.getLore();
            Double_Barrel_SawedOffLore.add("");
            Double_Barrel_SawedOffLore.add("");
            Double_Barrel_SawedOffLore.add(" §f※ 구매");
            Double_Barrel_SawedOffLore.add("");
            Double_Barrel_SawedOffLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(Double_Barrel_SawedOffPrice) + " 골드");
            Double_Barrel_SawedOffMeta.setLore(Double_Barrel_SawedOffLore);
            Double_Barrel_SawedOff.setItemMeta(Double_Barrel_SawedOffMeta);
        }

        ItemStack M950 = new CSUtility().generateWeapon("M950").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "M950")) {
            M950.setType(Material.STAINED_GLASS);
            M950.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta M950Meta = M950.getItemMeta();
            ArrayList<String> M950Lore = (ArrayList<String>) M950Meta.getLore();
            M950Lore.add("");
            M950Lore.add("");
            M950Lore.add(" §f※ 구매");
            M950Lore.add("");
            M950Lore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(M950Price) + " 다이아몬드");
            M950Meta.setLore(M950Lore);
            M950.setItemMeta(M950Meta);
        }

        ItemStack Torch = new CSUtility().generateWeapon("토치").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "토치")) {
            Torch.setType(Material.STAINED_GLASS);
            Torch.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta TorchMeta = Torch.getItemMeta();
            ArrayList<String> TorchLore = (ArrayList<String>) TorchMeta.getLore();
            TorchLore.add("");
            TorchLore.add("");
            TorchLore.add(" §f※ 구매");
            TorchLore.add("");
            TorchLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(TorchPrice) + " 에메랄드");
            TorchMeta.setLore(TorchLore);
            Torch.setItemMeta(TorchMeta);
        }

        ItemStack MK_13_EGLM = new CSUtility().generateWeapon("MK_13_EGLM").clone();
        if (kr.sizniss.data.Files.getSubWeapon(targetPlayer, "MK_13_EGLM")) {
            MK_13_EGLM.setType(Material.STAINED_GLASS);
            MK_13_EGLM.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta MK_13_EGLMMeta = MK_13_EGLM.getItemMeta();
            ArrayList<String> MK_13_EGLMLore = (ArrayList<String>) MK_13_EGLMMeta.getLore();
            MK_13_EGLMLore.add("");
            MK_13_EGLMLore.add("");
            MK_13_EGLMLore.add(" §f※ 구매");
            MK_13_EGLMLore.add("");
            MK_13_EGLMLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(MK_13_EGLMPrice) + " 다이아몬드");
            MK_13_EGLMMeta.setLore(MK_13_EGLMLore);
            MK_13_EGLM.setItemMeta(MK_13_EGLMMeta);
        }

        // 상품 - 근접 무기
        ItemStack CrowBar = new CSUtility().generateWeapon("빠루").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "빠루")) {
            CrowBar.setType(Material.STAINED_GLASS);
            CrowBar.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta CrowBarMeta = CrowBar.getItemMeta();
            ArrayList<String> CrowBarLore = (ArrayList<String>) CrowBarMeta.getLore();
            CrowBarLore.add("");
            CrowBarLore.add("");
            CrowBarLore.add(" §f※ 구매");
            CrowBarLore.add("");
            CrowBarLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(CrowBarPrice) + " 골드");
            CrowBarMeta.setLore(CrowBarLore);
            CrowBar.setItemMeta(CrowBarMeta);
        }

        ItemStack Kujang = new CSUtility().generateWeapon("쿠장").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "쿠장")) {
            Kujang.setType(Material.STAINED_GLASS);
            Kujang.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta KujangMeta = Kujang.getItemMeta();
            ArrayList<String> KujangLore = (ArrayList<String>) KujangMeta.getLore();
            KujangLore.add("");
            KujangLore.add("");
            KujangLore.add(" §f※ 구매");
            KujangLore.add("");
            KujangLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(KujangPrice) + " 에메랄드");
            KujangMeta.setLore(KujangLore);
            Kujang.setItemMeta(KujangMeta);
        }

        ItemStack Buchae = new CSUtility().generateWeapon("부채").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "부채")) {
            Buchae.setType(Material.STAINED_GLASS);
            Buchae.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta BuchaeMeta = Buchae.getItemMeta();
            ArrayList<String> BuchaeLore = (ArrayList<String>) BuchaeMeta.getLore();
            BuchaeLore.add("");
            BuchaeLore.add("");
            BuchaeLore.add(" §f※ 구매");
            BuchaeLore.add("");
            BuchaeLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(BuchaePrice) + " 다이아몬드");
            BuchaeMeta.setLore(BuchaeLore);
            Buchae.setItemMeta(BuchaeMeta);
        }

        ItemStack Rose_Knife = new CSUtility().generateWeapon("로즈_나이프").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "로즈_나이프")) {
            Rose_Knife.setType(Material.STAINED_GLASS);
            Rose_Knife.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta Rose_KnifeMeta = Rose_Knife.getItemMeta();
            ArrayList<String> Rose_KnifeLore = (ArrayList<String>) Rose_KnifeMeta.getLore();
            Rose_KnifeLore.add("");
            Rose_KnifeLore.add("");
            Rose_KnifeLore.add(" §f※ 구매");
            Rose_KnifeLore.add("");
            Rose_KnifeLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(Rose_KnifePrice) + " 에메랄드");
            Rose_KnifeMeta.setLore(Rose_KnifeLore);
            Rose_Knife.setItemMeta(Rose_KnifeMeta);
        }

        ItemStack Hammer = new CSUtility().generateWeapon("해머").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "해머")) {
            Hammer.setType(Material.STAINED_GLASS);
            Hammer.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta HammerMeta = Hammer.getItemMeta();
            ArrayList<String> HammerLore = (ArrayList<String>) HammerMeta.getLore();
            HammerLore.add("");
            HammerLore.add("");
            HammerLore.add(" §f※ 구매");
            HammerLore.add("");
            HammerLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(HammerPrice) + " 골드");
            HammerMeta.setLore(HammerLore);
            Hammer.setItemMeta(HammerMeta);
        }

        ItemStack Katana = new CSUtility().generateWeapon("카타나").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "카타나")) {
            Katana.setType(Material.STAINED_GLASS);
            Katana.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta KatanaMeta = Katana.getItemMeta();
            ArrayList<String> KatanaLore = (ArrayList<String>) KatanaMeta.getLore();
            KatanaLore.add("");
            KatanaLore.add("");
            KatanaLore.add(" §f※ 구매");
            KatanaLore.add("");
            KatanaLore.add(" §e→ §f비용: §e" + new DecimalFormat("###,###").format(KatanaPrice) + " 골드");
            KatanaMeta.setLore(KatanaLore);
            Katana.setItemMeta(KatanaMeta);
        }

        ItemStack Taser_Knife = new CSUtility().generateWeapon("테이저_나이프").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "테이저_나이프")) {
            Taser_Knife.setType(Material.STAINED_GLASS);
            Taser_Knife.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta Taser_KnifeMeta = Taser_Knife.getItemMeta();
            ArrayList<String> Taser_KnifeLore = (ArrayList<String>) Taser_KnifeMeta.getLore();
            Taser_KnifeLore.add("");
            Taser_KnifeLore.add("");
            Taser_KnifeLore.add(" §f※ 구매");
            Taser_KnifeLore.add("");
            Taser_KnifeLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(Taser_KnifePrice) + " 에메랄드");
            Taser_KnifeMeta.setLore(Taser_KnifeLore);
            Taser_Knife.setItemMeta(Taser_KnifeMeta);
        }

        ItemStack CheongRyongDo = new CSUtility().generateWeapon("청룡도").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "청룡도")) {
            CheongRyongDo.setType(Material.STAINED_GLASS);
            CheongRyongDo.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta CheongRyongDoMeta = CheongRyongDo.getItemMeta();
            ArrayList<String> CheongRyongDoLore = (ArrayList<String>) CheongRyongDoMeta.getLore();
            CheongRyongDoLore.add("");
            CheongRyongDoLore.add("");
            CheongRyongDoLore.add(" §f※ 구매");
            CheongRyongDoLore.add("");
            CheongRyongDoLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(CheongRyongDoPrice) + " 다이아몬드");
            CheongRyongDoMeta.setLore(CheongRyongDoLore);
            CheongRyongDo.setItemMeta(CheongRyongDoMeta);
        }

        ItemStack Claymore = new CSUtility().generateWeapon("클레이모어").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "클레이모어")) {
            Claymore.setType(Material.STAINED_GLASS);
            Claymore.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta ClaymoreMeta = Claymore.getItemMeta();
            ArrayList<String> ClaymoreLore = (ArrayList<String>) ClaymoreMeta.getLore();
            ClaymoreLore.add("");
            ClaymoreLore.add("");
            ClaymoreLore.add(" §f※ 구매");
            ClaymoreLore.add("");
            ClaymoreLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(ClaymorePrice) + " 다이아몬드");
            ClaymoreMeta.setLore(ClaymoreLore);
            Claymore.setItemMeta(ClaymoreMeta);
        }

        ItemStack WarHammer = new CSUtility().generateWeapon("워해머").clone();
        if (kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, "워해머")) {
            WarHammer.setType(Material.STAINED_GLASS);
            WarHammer.setDurability(Short.parseShort("8"));
        } else {
            ItemMeta WarHammerMeta = WarHammer.getItemMeta();
            ArrayList<String> WarHammerLore = (ArrayList<String>) WarHammerMeta.getLore();
            WarHammerLore.add("");
            WarHammerLore.add("");
            WarHammerLore.add(" §f※ 구매");
            WarHammerLore.add("");
            WarHammerLore.add(" §e→ §f비용: §a" + new DecimalFormat("###,###").format(WarHammerPrice) + " 에메랄드");
            WarHammerMeta.setLore(WarHammerLore);
            WarHammer.setItemMeta(WarHammerMeta);
        }

        // 상품 - 이벤트 개최
        ArrayList<Player> playingPlayers = (ArrayList<Player>) SelonZombieSurvival.manager.getPlayerList().clone();
        if (!playingPlayers.contains(targetPlayer)) { // 게임 중인 플레이어 목록에 본인이 포함되어 있지 않을 경우
            playingPlayers.add(targetPlayer.getPlayer());
        }
        ItemStack eventHold = variables.getEventHold().clone();
        ItemMeta eventHoldMeta = eventHold.getItemMeta();
        ArrayList<String> eventHoldLore = (ArrayList<String>) eventHoldMeta.getLore();
        eventHoldLore.add("");
        eventHoldLore.add(" §f※ 구매");
        eventHoldLore.add("");
        eventHoldLore.add(" §e→ §f비용: §b" + new DecimalFormat("###,###").format(eventHoldPrice * playingPlayers.size()) + " 다이아몬드");
        eventHoldLore.add("");
        eventHoldMeta.setLore(eventHoldLore);
        eventHold.setItemMeta(eventHoldMeta);


        ArrayList<ItemStack> subWeapons = new ArrayList<ItemStack>();
        subWeapons.add(Desert_Eagle);
        subWeapons.add(Beretta_M93R);
        subWeapons.add(M79_SawedOff);
        subWeapons.add(Shuriken);
        subWeapons.add(Duck_Foot);
        subWeapons.add(TaserGun);
        subWeapons.add(Double_Barrel_SawedOff);
        subWeapons.add(M950);
        subWeapons.add(Torch);
        subWeapons.add(MK_13_EGLM);

        ArrayList<ItemStack> meleeWeapons = new ArrayList<ItemStack>();
        meleeWeapons.add(CrowBar);
        meleeWeapons.add(Kujang);
        meleeWeapons.add(Buchae);
        meleeWeapons.add(Rose_Knife);
        meleeWeapons.add(Hammer);
        meleeWeapons.add(Katana);
        meleeWeapons.add(Taser_Knife);
        meleeWeapons.add(CheongRyongDo);
        meleeWeapons.add(Claymore);
        meleeWeapons.add(WarHammer);

        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int date = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        Random subRandom = new Random(year + month + date);
        Random meleeRandom = new Random(-(year + month + date));
        int subIndex = subRandom.nextInt(subWeapons.size());
        int meleeIndex = meleeRandom.nextInt(meleeWeapons.size());


        inventory.setItem(1, head);

        inventory.setItem(3, cash);
        inventory.setItem(5, gold);
        inventory.setItem(6, diamond);
        inventory.setItem(7, emerald);

        inventory.setItem(19, diamond500ExchangeTicket);
        inventory.setItem(20, diamond5500ExchangeTicket);
        inventory.setItem(21, emerald1000ExchangeTicket);
        inventory.setItem(28, gold5000ExchangeTicket);
        inventory.setItem(29, gold55000ExchangeTicket);
        inventory.setItem(30, eventHold);

        inventory.setItem(23, normalWeaponBox);
        inventory.setItem(24, specialWeaponBox);
        inventory.setItem(25, premiumWeaponBox);

        inventory.setItem(32, grayPane);
        inventory.setItem(33, subWeapons.get(subIndex));
        inventory.setItem(34, meleeWeapons.get(meleeIndex));

        // inventory.setItem(30, grayPane);
        // inventory.setItem(32, grayPane);
        // inventory.setItem(33, grayPane);
        // inventory.setItem(34, grayPane);

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, whitePane);
        }
        inventory.setItem(18, whitePane);
        inventory.setItem(22, whitePane);
        inventory.setItem(26, whitePane);
        inventory.setItem(27, whitePane);
        inventory.setItem(31, whitePane);
        inventory.setItem(35, whitePane);
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, whitePane);
        }

        inventory.setItem(0, blackPane);
        inventory.setItem(2, blackPane);
        inventory.setItem(4, blackPane);
        inventory.setItem(8, blackPane);
        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new ShopGUI(player, targetPlayer); // ShopGUI 호출
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
                } else if (name.contains("§b500 다이아몬드") && name.contains("교환권")) { // 500 다이아몬드 교환권
                    String product = "500 다이아몬드 교환권";
                    String productDisplay = name;

                    int cash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");
                    int price = diamond500ExchangeTicketPrice;
                    int diamond = 500;

                    if (cash >= price) { // 구매 자산이 있을 경우
                        kr.sizniss.data.Files.subtractMoney(targetPlayer, "Cash", price); // 자산 차감
                        kr.sizniss.data.Files.addMoney(targetPlayer, "Diamond", diamond); // 자산 추가

                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §f§l-" + new DecimalFormat("###,###").format(price) + "C §f§l]");
                        Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                        new ShopGUI(player, targetPlayer); // ShopGUI 호출
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l캐시가 부족하여 구매할 수 없습니다!");
                    }
                } else if (name.contains("§b5,500 다이아몬드") && name.contains("교환권")) { // 5,500 다이아몬드 교환권
                    String product = "5,500 다이아몬드 교환권";
                    String productDisplay = name;

                    int cash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");
                    int price = diamond5500ExchangeTicketPrice;
                    int diamond = 5500;

                    if (cash >= price) { // 구매 자산이 있을 경우
                        kr.sizniss.data.Files.subtractMoney(targetPlayer, "Cash", price); // 자산 차감
                        kr.sizniss.data.Files.addMoney(targetPlayer, "Diamond", diamond); // 자산 추가

                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §f§l-" + new DecimalFormat("###,###").format(price) + "C §f§l]");
                        Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                        new ShopGUI(player, targetPlayer); // ShopGUI 호출
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l캐시가 부족하여 구매할 수 없습니다!");
                    }
                } else if (name.contains("§a1,000 에메랄드") && name.contains("교환권")) { // 1,000 에메랄드 교환권
                    String product = "1,000 에메랄드 교환권";
                    String productDisplay = name;

                    int cash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");
                    int price = emerald1000ExchangeTicketPrice;
                    int emerald = 1000;

                    if (cash >= price) { // 구매 자산이 있을 경우
                        kr.sizniss.data.Files.subtractMoney(targetPlayer, "Cash", price); // 자산 차감
                        kr.sizniss.data.Files.addMoney(targetPlayer, "Emerald", emerald); // 자산 추가

                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §f§l-" + new DecimalFormat("###,###").format(price) + "C §f§l]");
                        Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                        new ShopGUI(player, targetPlayer); // ShopGUI 호출
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l캐시가 부족하여 구매할 수 없습니다!");
                    }
                } else if (name.contains("§e5,000 골드") && name.contains("교환권")) { // 5,000 골드 교환권
                    String product = "5,000 골드 교환권";
                    String productDisplay = name;

                    int cash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");
                    int price = gold5000ExchangeTicketPrice;
                    int gold = 5000;

                    if (cash >= price) { // 구매 자산이 있을 경우
                        kr.sizniss.data.Files.subtractMoney(targetPlayer, "Cash", price); // 자산 차감
                        kr.sizniss.data.Files.addMoney(targetPlayer, "Gold", gold); // 자산 추가

                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §f§l-" + new DecimalFormat("###,###").format(price) + "C §f§l]");
                        Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                        new ShopGUI(player, targetPlayer); // ShopGUI 호출
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l캐시가 부족하여 구매할 수 없습니다!");
                    }
                } else if (name.contains("§e55,000 골드") && name.contains("교환권")) { // 55,000 골드 교환권
                    String product = "55,000 골드 교환권";
                    String productDisplay = name;

                    int cash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");
                    int price = gold55000ExchangeTicketPrice;
                    int gold = 55000;

                    if (cash >= price) { // 구매 자산이 있을 경우
                        kr.sizniss.data.Files.subtractMoney(targetPlayer, "Cash", price); // 자산 차감
                        kr.sizniss.data.Files.addMoney(targetPlayer, "Gold", gold); // 자산 추가

                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §f§l-" + new DecimalFormat("###,###").format(price) + "C §f§l]");
                        Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                        new ShopGUI(player, targetPlayer); // ShopGUI 호출
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l캐시가 부족하여 구매할 수 없습니다!");
                    }
                }
                /*
                else if (name.contains("이벤트 개최")) { // 이벤트 개최
                    String product = "이벤트 개최";
                    String productDisplay = name;

                    ArrayList<Player> playingPlayers = (ArrayList<Player>) SelonZombieSurvival.manager.getPlayerList().clone();
                    if (!playingPlayers.contains(targetPlayer)) { // 게임 중인 플레이어 목록에 본인이 포함되어 있지 않을 경우
                        playingPlayers.add(targetPlayer.getPlayer());
                    }
                    int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                    int price = 500;
                    int goal = 5;
                    for (int i = 5; i <= 50; i += 5) {
                        if (i > playingPlayers.size()) {
                            goal = i;
                            price = eventHoldPrice * goal;
                            break;
                        }
                    }

                    if (diamond >= price) { // 구매 자산이 있을 경우
                        kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감

                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                        Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                        String eventProduct = "Premium Weapon Box";
                        String eventProductDisplay = "고급 무기 상자";
                        int eventAmount = 1;

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            onlinePlayer.sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 이벤트를 개최하실 예정입니다!");

                            World world = onlinePlayer.getWorld();
                            Location location = onlinePlayer.getLocation();
                            world.playSound(location, Sound.ENTITY_FIREWORK_SHOOT, 1.0f, 1.0f); // 소리 재생
                        }

                        JDA jda = variables.getJda();
                        String message = "```\n" +
                                "※ 이벤트 개최 예정\n" +
                                "개최자: " + targetPlayer.getName() + "\n" +
                                "목표 인원: " + goal + "\n" +
                                "```";
                        jda.getTextChannelById("533639444587085828").sendMessage(message).queue();


                        for (OfflinePlayer playingPlayer : playingPlayers) {
                            kr.sizniss.data.Files.addBox(playingPlayer, eventProduct, eventAmount); // 상자 추가

                            ((Player) playingPlayer).sendMessage(serverTitle + " §f§l'§f" + eventProductDisplay + "(x" + eventAmount + ")§f§l'을(를) 획득하셨습니다.");
                            Bukkit.getConsoleSender().sendMessage("[" + playingPlayer.getName() + ": Get " + eventProduct + "(x" + eventAmount + ")]");
                        }

                        jda = variables.getJda();
                        message = "```\n" +
                                "※ 이벤트 개최\n" +
                                "개최자: " + targetPlayer.getName() + "\n" +
                                "인원: " + playingPlayers.size() + "\n" +
                                "```";
                        jda.getTextChannelById("533639444587085828").sendMessage(message).queue();

                        new ShopGUI(player, targetPlayer); // ShopGUI 호출
                    }
                    else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                    }
                }
                 */
                else if (name.contains("이벤트 개최")) { // 이벤트 개최
                    String product = "이벤트 개최";
                    String productDisplay = name;

                    ArrayList<Player> playingPlayers = (ArrayList<Player>) SelonZombieSurvival.manager.getPlayerList().clone();
                    if (!playingPlayers.contains(targetPlayer)) { // 게임 중인 플레이어 목록에 본인이 포함되어 있지 않을 경우
                        playingPlayers.add(targetPlayer.getPlayer());
                    }
                    if (playingPlayers.size() >= 4) { // 게임에 참여한 인원 수가 4명 이상일 경우
                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = eventHoldPrice * playingPlayers.size();

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                            String eventProduct = "Premium Weapon Box";
                            String eventProductDisplay = "고급 무기 상자";
                            int eventAmount = 1;

                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                onlinePlayer.sendMessage(serverTitle + " §6§l" + targetPlayer.getName() + "§f§l님께서 이벤트를 개최하셨습니다!");

                                World world = onlinePlayer.getWorld();
                                Location location = onlinePlayer.getLocation();
                                world.playSound(location, Sound.ENTITY_ENDERDRAGON_DEATH, 1.0f, 1.0f); // 소리 재생
                            }

                            for (OfflinePlayer playingPlayer : playingPlayers) {
                                kr.sizniss.data.Files.addBox(playingPlayer, eventProduct, eventAmount); // 상자 추가

                                ((Player) playingPlayer).sendMessage(serverTitle + " §f§l'§f" + eventProductDisplay + "(x" + eventAmount + ")§f§l'을(를) 획득하셨습니다.");
                                Bukkit.getConsoleSender().sendMessage("[" + playingPlayer.getName() + ": Get " + eventProduct + "(x" + eventAmount + ")]");
                            }

                            JDA jda = variables.getJda();
                            String message = "```\n" +
                                    "※ 이벤트 개최\n" +
                                    "개최자: " + targetPlayer.getName() + "\n" +
                                    "인원: " + playingPlayers.size() + "\n" +
                                    "```";
                            jda.getTextChannelById("533639444587085828").sendMessage(message).queue();

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    } else {
                        ((Player) targetPlayer).sendMessage(serverTitle + " §f§l인원 수가 부족하여 §e§l이벤트§f§l를 개최할 수 없습니다!");
                    }
                }
                else if (name.contains("일반 무기 상자")) { // 일반 무기 상자
                    String product = "Normal Weapon Box";
                    String productDisplay = name;

                    int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                    int amount = event.isShiftClick() ? 10 : 1;
                    int price = normalWeaponBoxPrice * amount;

                    if (gold >= price) { // 구매 자산이 있을 경우
                        if (event.isLeftClick()) { // 좌클릭일 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.addBox(targetPlayer, product, amount); // 상자 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "(x" + amount + ")]");

                            if (methods.isNewUser(targetPlayer)) {
                                ((Player) targetPlayer).sendMessage("");
                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l상자를 획득하였습니다!");
                                ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§e/상자§f§l' 명령어를 통해 상자를 개봉하실 수 있습니다!");
                                ((Player) targetPlayer).sendTitle("§e/상자", "§f상자를 개봉하실 수 있습니다!", 5, 100, 5);
                            }

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else if (event.isRightClick()) { // 우클릭일 경우
                            new PresentGUI(player, targetPlayer, product, amount); // PresentGUI 호출
                        }
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                    }
                    
                } else if (name.contains("특별 무기 상자")) { // 특별 무기 상자
                    String product = "Special Weapon Box";
                    String productDisplay = name;

                    int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                    int amount = event.isShiftClick() ? 10 : 1;
                    int price = specialWeaponBoxPrice * amount;

                    if (gold >= price) { // 구매 자산이 있을 경우
                        if (event.isLeftClick()) { // 좌클릭일 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.addBox(targetPlayer, product, amount); // 상자 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "(x" + amount + ")]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else if (event.isRightClick()) { // 우클릭일 경우
                            new PresentGUI(player, targetPlayer, product, amount); // PresentGUI 호출
                        }
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                    }

                } else if (name.contains("고급 무기 상자")) { // 고급 무기 상자
                    String product = "Premium Weapon Box";
                    String productDisplay = name;

                    int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                    int amount = event.isShiftClick() ? 10 : 1;
                    int price = premiumWeaponBoxPrice * amount;

                    if (diamond >= price) { // 구매 자산이 있을 경우
                        if (event.isLeftClick()) { // 좌클릭일 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.addBox(targetPlayer, product, amount); // 상자 추가

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "(x" + amount + ")]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else if (event.isRightClick()) { // 우클릭일 경우
                            new PresentGUI(player, targetPlayer, product, amount); // PresentGUI 호출
                        }
                    } else { // 구매 자산이 없을 경우
                        ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                    }

                } else if (name.contains("테두리")
                        || name.contains("칭호")
                        || name.contains("닉네임")
                        || name.contains("채팅")) { // 코디

                    String color = "";
                    if (name.contains("연두색")) {
                        color = "Green";
                    } else if (name.contains("하늘색")) {
                        color = "Skyblue";
                    } else if (name.contains("분홍색")) {
                        color = "Pink";
                    } else if (name.contains("자홍색")) {
                        color = "Magenta";
                    } else if (name.contains("노란색")) {
                        color = "Yellow";
                    }
                    
                    String codi = "";
                    if (name.contains("테두리")) {
                        codi = "Border";
                    } else if (name.contains("칭호")) {
                        codi = "Prefix";
                    } else if (name.contains("닉네임")) {
                        codi = "Nick";
                    } else if (name.contains("채팅")) {
                        codi = "Chat";
                    }

                    boolean isOwned = false;
                    if (codi.equals("Border")) {
                        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, color)) {
                            isOwned = true;
                        }
                    } else if (codi.equals("Prefix")) {
                        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, color)) {
                            isOwned = true;
                        }
                    } else if (codi.equals("Nick")) {
                        if (kr.sizniss.data.Files.getNickColor(targetPlayer, color)) {
                            isOwned = true;
                        }
                    } else if (codi.equals("Chat")) {
                        if (kr.sizniss.data.Files.getChatColor(targetPlayer, color)) {
                            isOwned = true;
                        }
                    }

                    if (!isOwned) { // 소지하고 있지 않을 경우

                        String product = color + " " + codi.toLowerCase();
                        String productDisplay = name;

                        int cash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");
                        int price = codiPrice;

                        if (cash >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Cash", price); // 자산 차감

                            // 코디 추가
                            if (codi.equals("Border")) {
                                kr.sizniss.data.Files.setBorderColor(targetPlayer, color, true);
                            } else if (codi.equals("Prefix")) {
                                kr.sizniss.data.Files.setPrefixColor(targetPlayer, color, true);
                            } else if (codi.equals("Nick")) {
                                kr.sizniss.data.Files.setNickColor(targetPlayer, color, true);
                            } else if (codi.equals("Chat")) {
                                kr.sizniss.data.Files.setChatColor(targetPlayer, color, true);
                            }

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §f§l-" + new DecimalFormat("###,###").format(price) + "C §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + product + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l캐시가 부족하여 구매할 수 없습니다!");
                        }
                    }

                } else if (name.contains("Desert Eagle")) { // Desert Eagle
                    String product = "Desert_Eagle";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = Desert_EaglePrice;
                        
                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");
                            
                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("Beretta M93R")) { // Beretta M93R
                    String product = "Beretta_M93R";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = Beretta_M93RPrice;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("M79 소드오프")) { // M79 소드오프
                    String product = "M79_소드오프";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = M79_SawedOffPrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("수리검")) { // 수리검
                    String product = "수리검";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = ShurikenPrice;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("덕 풋")) { // 덕 풋
                    String product = "덕_풋";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = Duck_FootPrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("테이저건")) { // 테이저건
                    String product = "테이저건";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = TaserGunPrice;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("Double Barrel 소드오프")) { // Double Barrel 소드오프
                    String product = "Double_Barrel_소드오프";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = Double_Barrel_SawedOffPrice;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("M950")) { // M950
                    String product = "M950";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = M950Price;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("토치")) { // 토치
                    String product = "토치";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = TorchPrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("MK.13 EGLM")) { // MK.13 EGLM
                    String product = "MK_13_EGLM";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getSubWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = MK_13_EGLMPrice;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setSubWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("빠루")) { // 빠루
                    String product = "빠루";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = CrowBarPrice;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("쿠장")) { // 쿠장
                    String product = "쿠장";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = KujangPrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("부채")) { // 부채
                    String product = "부채";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = BuchaePrice;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("로즈 나이프")) { // 로즈 나이프
                    String product = "로즈_나이프";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = Rose_KnifePrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("해머") && !name.contains("워")) { // 해머
                    String product = "해머";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = HammerPrice;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("카타나")) { // 카타나
                    String product = "카타나";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int gold = kr.sizniss.data.Files.getMoney(targetPlayer, "Gold");
                        int price = KatanaPrice;

                        if (gold >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Gold", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §e§l-" + new DecimalFormat("###,###").format(price) + "G §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §e§l골드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("테이저 나이프")) { // 테이저 나이프
                    String product = "테이저_나이프";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = Taser_KnifePrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("청룡도")) { // 청룡도
                    String product = "청룡도";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = CheongRyongDoPrice;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("클레이모어")) { // 클레이모어
                    String product = "클레이모어";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int diamond = kr.sizniss.data.Files.getMoney(targetPlayer, "Diamond");
                        int price = ClaymorePrice;

                        if (diamond >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Diamond", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §b§l-" + new DecimalFormat("###,###").format(price) + "D §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §b§l다이아몬드§f§l가 부족하여 구매할 수 없습니다!");
                        }
                    }
                } else if (name.contains("워해머")) { // 워해머
                    String product = "워해머";
                    String productDisplay = product.replace("_", " ");

                    if (!kr.sizniss.data.Files.getMeleeWeapon(targetPlayer, product)) { // 소지하고 있지 않을 경우

                        int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                        int price = WarHammerPrice;

                        if (emerald >= price) { // 구매 자산이 있을 경우
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price); // 자산 차감
                            kr.sizniss.data.Files.setMeleeWeapon(targetPlayer, product, true); // 무기 지급

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + productDisplay + "§f§l'을(를) 구입하셨습니다. [ §a§l-" + new DecimalFormat("###,###").format(price) + "E §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Buy " + productDisplay + "]");

                            new ShopGUI(player, targetPlayer); // ShopGUI 호출
                        } else { // 구매 자산이 없을 경우
                            ((Player) targetPlayer).sendMessage(serverTitle + " §a§l에메랄드§f§l가 부족하여 구매할 수 없습니다!");
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
