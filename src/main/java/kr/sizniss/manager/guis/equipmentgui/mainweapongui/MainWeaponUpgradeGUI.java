package kr.sizniss.manager.guis.equipmentgui.mainweapongui;

import com.shampaggon.crackshot.CSUtility;
import kr.sizniss.manager.Files;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class MainWeaponUpgradeGUI {

    Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;
    private String weapon;

    private int task; // 갱신 테스크


    private String serverTitle = Files.getServerTitle();


    public MainWeaponUpgradeGUI(Player player, OfflinePlayer targetPlayer, String weapon) {
        this.player = player;
        this.targetPlayer = targetPlayer;
        this.weapon = weapon;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    private void createGUI() {
        inventory = Bukkit.createInventory(player, 27, "장비 - 주 무기 - 강화"); // 인벤토리 생성

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

        // 회색 색유리
        ItemStack grayGlass = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("7"));

        // 이전
        ItemStack prevPage = variables.getPrevPage().clone();

        // 다음
        ItemStack nextPage = variables.getNextPage().clone();


        // 에메랄드
        ItemStack emerald = new ItemStack(Material.EMERALD, 1);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName("§a" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald")) + " 에메랄드");
        emerald.setItemMeta(emeraldMeta);


        // 조각
        int bronzeAmount = kr.sizniss.data.Files.getPiece(targetPlayer, "Bronze");
        ItemStack bronzePiece = new ItemStack(Material.SPIDER_EYE);
        ItemMeta bronzePieceMeta = bronzePiece.getItemMeta();
        bronzePieceMeta.setDisplayName("§6" + bronzeAmount + " 동 조각");
        bronzePiece.setItemMeta(bronzePieceMeta);

        int silverAmount = kr.sizniss.data.Files.getPiece(targetPlayer, "Silver");
        ItemStack silverPiece = new ItemStack(Material.GHAST_TEAR);
        ItemMeta silverPieceMeta = silverPiece.getItemMeta();
        silverPieceMeta.setDisplayName("§f" + silverAmount + " 은 조각");
        silverPiece.setItemMeta(silverPieceMeta);

        int goldAmount = kr.sizniss.data.Files.getPiece(targetPlayer, "Gold");
        ItemStack goldPiece = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta goldPieceMeta = goldPiece.getItemMeta();
        goldPieceMeta.setDisplayName("§e" + goldAmount + " 금 조각");
        goldPiece.setItemMeta(goldPieceMeta);

        // 무기
        int weaponNumber = 0;
        for (int i = 0; i < variables.getMainWeaponName().size(); i++) {
            if (variables.getMainWeaponName().get(i).equals(weapon)) {
                break;
            }
            weaponNumber++;
        }

        int currentWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(targetPlayer, weapon);
        int maxWeaponLevel = variables.getMainWeaponMaxLevel().get(weaponNumber);

        ItemStack[] weaponItem = new ItemStack[maxWeaponLevel + 1];
        ItemMeta[] weaponMeta = new ItemMeta[maxWeaponLevel + 1];
        for (int i = 0; i <= maxWeaponLevel; i++) {
            if (i == 0) {
                weaponItem[i] = new CSUtility().generateWeapon(weapon);
            } else {
                weaponItem[i] = new CSUtility().generateWeapon("(+" + i + ")_" + weapon);
            }

            weaponMeta[i] = weaponItem[i].getItemMeta();
            if (i == currentWeaponLevel + 1) {
                int currentAmount = kr.sizniss.data.Files.getPiece(targetPlayer, maxWeaponLevel == 3 ? "Bronze" : maxWeaponLevel == 2 ? "Silver" : "Gold");
                int maxAmount; if (maxWeaponLevel >= 2) { maxAmount = (3 - (maxWeaponLevel - i)) * 100; } else { maxAmount = 50; }
                int count = 25;
                int currentExperience = kr.sizniss.data.Files.getMainWeaponPlayCount(targetPlayer, weapon) - (maxWeaponLevel == 3 ? (currentWeaponLevel == 2 ? count * 3 : currentWeaponLevel == 1 ? count : 0) : maxWeaponLevel == 2 ? (currentWeaponLevel == 1 ? count * 2 : 0) : 0);
                int maxExperience = (3 - (maxWeaponLevel - i)) * count;

                int price = (3 - (maxWeaponLevel - i)) * 1000;

                ArrayList<String> weaponLore = (ArrayList<String>) weaponMeta[i].getLore();
                weaponLore.add("");
                weaponLore.add("");
                weaponLore.add(" §f※ 강화");
                weaponLore.add("");
                weaponLore.add(" §e→ §f조건: §f경험치(§7" + currentExperience + "§f/§7" + maxExperience + "§f)");
                weaponLore.add("");
                if (maxWeaponLevel == 3) {
                    weaponLore.add(" §e→ §f비용: §6" + maxAmount + " 동 조각§f,");
                } else if (maxWeaponLevel == 2) {
                    weaponLore.add(" §e→ §f비용: §f" + maxAmount + " 은 조각§f,");
                } else {
                    weaponLore.add(" §e→ §f비용: §e" + maxAmount + " 금 조각§f,");
                }
                weaponLore.add("       §0. §a" + new DecimalFormat("###,###").format(price) + " 에메랄드");

                weaponMeta[i].setLore(weaponLore);
            } else if (i <= currentWeaponLevel) {
                weaponMeta[i].addEnchant(Enchantment.LUCK, 1, true);
                weaponMeta[i].addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            weaponItem[i].setItemMeta(weaponMeta[i]);
        }


        inventory.setItem(1, head);

        inventory.setItem(3, emerald);

        inventory.setItem(5, bronzePiece);
        inventory.setItem(6, silverPiece);
        inventory.setItem(7, goldPiece);

        if (maxWeaponLevel == 3) {
            inventory.setItem(10, weaponItem[0]);
            inventory.setItem(11, nextPage);
            inventory.setItem(12, weaponItem[1]);
            inventory.setItem(13, nextPage);
            inventory.setItem(14, weaponItem[2]);
            inventory.setItem(15, nextPage);
            inventory.setItem(16, weaponItem[3]);
        } else if (maxWeaponLevel == 2) {
            inventory.setItem(10, grayGlass);
            inventory.setItem(11, grayPane);
            inventory.setItem(12, weaponItem[0]);
            inventory.setItem(13, nextPage);
            inventory.setItem(14, weaponItem[1]);
            inventory.setItem(15, nextPage);
            inventory.setItem(16, weaponItem[2]);
        } else if (maxWeaponLevel == 1) {
            inventory.setItem(10, grayGlass);
            inventory.setItem(11, grayPane);
            inventory.setItem(12, grayGlass);
            inventory.setItem(13, grayPane);
            inventory.setItem(14, weaponItem[0]);
            inventory.setItem(15, nextPage);
            inventory.setItem(16, weaponItem[1]);
        }

        inventory.setItem(0, blackPane);
        inventory.setItem(2, blackPane);
        inventory.setItem(4, blackPane);
        inventory.setItem(8, blackPane);
        inventory.setItem(9, blackPane);
        inventory.setItem(17, blackPane);
        for (int i = 18; i < 27; i++) {
            inventory.setItem(i, blackPane);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new MainWeaponUpgradeGUI(player, targetPlayer, weapon); // MainWeaponUpgradeGUI 호출
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

                int weaponNumber = 0;
                for (int i = 0; i < variables.getMainWeaponName().size(); i++) {
                    if (variables.getMainWeaponName().get(i).equals(weapon)) {
                        break;
                    }
                    weaponNumber++;
                }

                int currentWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(targetPlayer, weapon);
                int maxWeaponLevel = variables.getMainWeaponMaxLevel().get(weaponNumber);

                int currentAmount = kr.sizniss.data.Files.getPiece(targetPlayer, maxWeaponLevel == 3 ? "Bronze" : maxWeaponLevel == 2 ? "Silver" : "Gold");
                int maxAmount; if (maxWeaponLevel >= 2) { maxAmount = (3 - (maxWeaponLevel - (currentWeaponLevel + 1))) * 100; } else { maxAmount = 50; }
                int count = 25;
                int currentExperience = kr.sizniss.data.Files.getMainWeaponPlayCount(targetPlayer, weapon) - (maxWeaponLevel == 3 ? (currentWeaponLevel == 2 ? count * 3 : currentWeaponLevel == 1 ? count : 0) : maxWeaponLevel == 2 ? (currentWeaponLevel == 1 ? count * 2 : 0) : 0);
                int maxExperience = (3 - (maxWeaponLevel - (currentWeaponLevel + 1))) * count;

                int emerald = kr.sizniss.data.Files.getMoney(targetPlayer, "Emerald");
                int price = (3 - (maxWeaponLevel - (currentWeaponLevel + 1))) * 1000;

                int slot = event.getSlot();
                if (slot == 12) {
                    if (maxWeaponLevel == 3 && currentWeaponLevel == 0) {
                        if (currentAmount >= maxAmount && currentExperience >= maxExperience && emerald >= price) {
                            kr.sizniss.data.Files.subtractPiece(targetPlayer, "Bronze", maxAmount);
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price);

                            kr.sizniss.data.Files.addMainWeaponLevel(targetPlayer, weapon, 1);

                            World world = ((Player) targetPlayer).getWorld();
                            world.playSound(((Player) targetPlayer).getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 0.0f);

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l' 무기를 강화하였습니다. [ §a§l-" + price + "E§f§l, §6§l-" + maxAmount + "BP §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": " + weapon.replace("_", " ") + " weapon upgrade]");

                            new MainWeaponUpgradeGUI(player, targetPlayer, weapon); // MainWeaponUpgradeGUI 호출
                        }
                    }
                } else if (slot == 14) {
                    if (maxWeaponLevel == 3 && currentWeaponLevel == 1 || maxWeaponLevel == 2 && currentWeaponLevel == 0) {
                        if (currentAmount >= maxAmount && currentExperience >= maxExperience && emerald >= price) {
                            kr.sizniss.data.Files.subtractPiece(targetPlayer, maxWeaponLevel == 3 ? "Bronze" : "Silver", maxAmount);
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price);

                            kr.sizniss.data.Files.addMainWeaponLevel(targetPlayer, weapon, 1);

                            World world = ((Player) targetPlayer).getWorld();
                            world.playSound(((Player) targetPlayer).getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 0.0f);

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l' 무기를 강화하였습니다. [ §a§l-" + price + "E§f§l, §" + (maxWeaponLevel == 3 ? "6" : "f") + "§l-" + maxAmount + (maxWeaponLevel == 3 ? "B" : "S") + "P §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": " + weapon.replace("_", " ") + " weapon upgrade]");

                            new MainWeaponUpgradeGUI(player, targetPlayer, weapon); // MainWeaponUpgradeGUI 호출
                        }
                    }
                } else if (slot == 16) {
                    if (maxWeaponLevel == 3 && currentWeaponLevel == 2 || maxWeaponLevel == 2 && currentWeaponLevel == 1 || maxWeaponLevel == 1 && currentWeaponLevel == 0) {
                        if (currentAmount >= maxAmount && currentExperience >= maxExperience && emerald >= price) {
                            kr.sizniss.data.Files.subtractPiece(targetPlayer, maxWeaponLevel == 3 ? "Bronze" : maxWeaponLevel == 2 ? "Silver" : "Gold", maxAmount);
                            kr.sizniss.data.Files.subtractMoney(targetPlayer, "Emerald", price);

                            kr.sizniss.data.Files.addMainWeaponLevel(targetPlayer, weapon, 1);

                            World world = ((Player) targetPlayer).getWorld();
                            world.playSound(((Player) targetPlayer).getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 0.0f);

                            ((Player) targetPlayer).sendMessage(serverTitle + " §f§l'§f" + weapon.replace("_", " ") + "§f§l' 무기를 강화하였습니다. [ §a§l-" + price + "E§f§l, §" + (maxWeaponLevel == 3 ? "6" : maxWeaponLevel == 2 ? "f" : "e") + "§l-" + maxAmount + (maxWeaponLevel == 3 ? "B" : maxWeaponLevel == 2 ? "S" : "G") + "P §f§l]");
                            Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": " + weapon.replace("_", " ") + " weapon upgrade]");

                            new MainWeaponUpgradeGUI(player, targetPlayer, weapon); // MainWeaponUpgradeGUI 호출
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
