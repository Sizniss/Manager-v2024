package kr.sizniss.manager;

import com.google.gson.JsonElement;
import com.shampaggon.crackshot.CSUtility;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.guis.*;
import kr.sizniss.manager.guis.equipmentgui.ClassGUI;
import kr.sizniss.manager.guis.equipmentgui.TypeGUI;
import kr.sizniss.manager.maps.HuntingGround;
import net.dv8tion.jda.api.JDA;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static kr.sizniss.manager.Manager.*;

public class Commands {

    String serverTitle = Files.getServerTitle();


    public boolean Test(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.getName().equals("S_Sasisa")) {
            return false;
        }

        sender.sendMessage(serverTitle + " §fTest!");
        /*
        if (args.length > 1) {
            kr.sizniss.data.Files.setMainWeaponLevel((Player) sender, args[0], Integer.parseInt(args[1]));
            sender.sendMessage(args[0] + ": " + args[1]);
        }
         */
        // World world = ((Player)sender).getWorld();
        // world.playSound(((Player)sender).getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.5f, 0.0f);
        // new RuleGUI((Player) sender);
        // sender.sendMessage(new CSUtility().generateWeapon("Barret_M99").getItemMeta().getDisplayName());
        // Bukkit.getConsoleSender().sendMessage(new CSUtility().generateWeapon("Barret_M99").getItemMeta().getDisplayName());
        // ((Player) sender).getInventory().setItem(0, new CSUtility().generateWeapon("가시_방패"));
        // sender.sendMessage(serverTitle + " §fIP: " + Bukkit.getPlayer(args[0]).getAddress().getHostName());

        /*
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            sender.sendMessage(serverTitle);
            sender.sendMessage(serverTitle + " §f§l모든 플레이어 '§fMP5§f§l' 무기 +2 강화 작업 시작");

            String weapon = "MP5";
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                int level = kr.sizniss.data.Files.getMainWeaponLevel(player, weapon);
                if (level == 1) {
                    kr.sizniss.data.Files.addPiece(player, "Bronze", 100);
                    kr.sizniss.data.Files.addMoney(player, "Emerald", 1000);
                }
                else if (level == 2
                        || level == 3) {
                    kr.sizniss.data.Files.addPiece(player, "Bronze", 300);
                    kr.sizniss.data.Files.addMoney(player, "Emerald", 3000);

                    continue;
                }

                kr.sizniss.data.Files.setMainWeaponLevel(player, weapon, 2); // MP5 +2 강화

                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": " + weapon.replace("_", " ") + " weapon upgrade]");
            }

            sender.sendMessage(serverTitle + " §f§l모든 플레이어 '§fMP5§f§l' 무기 +2 강화 작업 완료");
        });
         */

        /*
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            sender.sendMessage(serverTitle);
            sender.sendMessage("§f중복 무기 시스템 개편 보상 지급 사항: {");
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                int goldPieceAmount = kr.sizniss.data.Files.getPiece(player, "Gold");

                ArrayList<String> weapons = variables.getGoldMainWeaponName();
                for (String weapon : weapons) {
                    int level = kr.sizniss.data.Files.getMainWeaponLevel(player, weapon);
                    if (level == 1) {
                        goldPieceAmount += 50;
                    }
                }

                if (goldPieceAmount > 0) {
                    String product = "Premium Weapon Box";
                    int premiumWeaponBoxAmount = (int) (goldPieceAmount * 2 / 10.0);

                    int prevAmount = kr.sizniss.data.Files.getBox(player, product);
                    kr.sizniss.data.Files.addBox(player, product, premiumWeaponBoxAmount); // 상자 추가
                    int newAmount = kr.sizniss.data.Files.getBox(player, product);

                    sender.sendMessage(" §6" + player.getName() + "§f: 고급 무기 상자 §7" + prevAmount + " §f→ §7" + newAmount + " §f(§a+" + premiumWeaponBoxAmount + "§f), §e금 §f조각 §7" + goldPieceAmount);
                    if (premiumWeaponBoxAmount > 0) {
                        Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Add " + product + "(x" + premiumWeaponBoxAmount + ")]");
                    }
                }
            }
            sender.sendMessage("§f}");
        });
         */

        /*
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            kr.sizniss.data.Files.addUserNameList(player, player.getName());
        }
         */
        
        /*
        if (Files.getMinelistUser().contains(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
            Files.removeMinelistUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
        } else {
            Files.addMinelistUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
        }
         */

        /*
        sender.sendMessage(serverTitle);
        sender.sendMessage(serverTitle + " §f무기 강화 조가");

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            for (String weapon : variables.getGoldMainWeaponName()) {
                int weaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(player, weapon);
                if (weaponLevel == 1) {
                    sender.sendMessage(serverTitle + " §fUser: §6" + player.getName() + " §f|| " + weapon + ": §7" + weaponLevel + " §f→ §7" + weaponLevel);
                }
            }
        }
         */

        /*
        if (args.length > 1) {
            Player player = Bukkit.getPlayer(args[0]);
            int value = Integer.valueOf(args[1]);
            Files.setEventCount(player, value);
        }
         */

        /*
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            sender.sendMessage(serverTitle);
            sender.sendMessage(serverTitle + " §f병과 포인트 변경 사항 적용");

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                for (String kind : variables.getClassName()) {
                    int remainingPoint = methods.getClassRemainingPoint(player, kind);

                    if (remainingPoint < 0
                            || kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_보유_개수") >= 2) {
                        int prevRemainingPoint = remainingPoint;

                        // 능력치 초기화
                        kr.sizniss.data.Files.setClassStatus(player, kind, "최대_체력", 0);
                        kr.sizniss.data.Files.setClassStatus(player, kind, "재생", 0);
                        kr.sizniss.data.Files.setClassStatus(player, kind, "이동_속도", 0);
                        kr.sizniss.data.Files.setClassStatus(player, kind, "아이템_보유_개수", 0);
                        switch (kind) {
                            case "보병":
                            case "포병":
                            case "교란병":
                            case "의무병":
                            case "기갑병":
                            case "보급병":
                                kr.sizniss.data.Files.setClassStatus(player, kind, "추가_탄창", 0);
                                break;
                        }

                        int newRemainingPoint = methods.getClassRemainingPoint(player, kind);

                        sender.sendMessage(serverTitle + " §fUser: §6" + player.getName() + " §f|| " + kind + ": §7" + prevRemainingPoint + " §f→ §7" + newRemainingPoint);
                        Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Reset " + kind + " kind status]");
                    }
                }
            }

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                String kind = "보병";

                kr.sizniss.data.Files.removeClassStatus(player, kind, "아이템_지속_시간");

                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Remove " + kind + " kind's item duration status]");
            }

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                String kind = "교란병";

                kr.sizniss.data.Files.removeClassStatus(player, kind, "아이템_지속_시간");

                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Remove " + kind + " kind's item duration status]");
            }

            sender.sendMessage(serverTitle + " §f병과 포인트 변경 사항 적용 완료");


            sender.sendMessage(serverTitle);
            sender.sendMessage(serverTitle + " §f타입 포인트 변경 사항 적용");

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                for (String type : variables.getTypeName()) {
                    int remainingPoint = methods.getTypeRemainingPoint(player, type);

                    // if (remainingPoint < 0) { // 잔여 포인트가 0 미만일 경우
                    if (remainingPoint < 0) {
                        int prevRemainingPoint = remainingPoint;

                        // 능력치 초기화
                        kr.sizniss.data.Files.setTypeStatus(player, type, "최대_체력", 0);
                        kr.sizniss.data.Files.setTypeStatus(player, type, "재생", 0);
                        kr.sizniss.data.Files.setTypeStatus(player, type, "이동_속도", 0);
                        kr.sizniss.data.Files.setTypeStatus(player, type, "점프_강화", 0);
                        kr.sizniss.data.Files.setTypeStatus(player, type, "도약_재사용_대기_시간", 0);
                        kr.sizniss.data.Files.setTypeStatus(player, type, "능력_재사용_대기_시간", 0);
                        switch (type) {
                            case "버서커":
                            case "바드":
                            case "나이트":
                            case "워리어":
                            case "로그":
                            case "어쌔신":
                                kr.sizniss.data.Files.setTypeStatus(player, type, "능력_지속_시간", 0);
                                break;
                            case "네크로맨서":
                            case "레인저":
                            case "버스터":
                            case "서머너":
                                kr.sizniss.data.Files.setTypeStatus(player, type, "능력_사용_횟수", 0);
                                break;
                            case "헌터":
                                kr.sizniss.data.Files.setTypeStatus(player, type, "능력_효과_범위", 0);
                                break;
                        }

                        int newRemainingPoint = methods.getTypeRemainingPoint(player, type);

                        sender.sendMessage(serverTitle + " §fUser: §6" + player.getName() + " §f|| " + type + ": §7" + prevRemainingPoint + " §f→ §7" + newRemainingPoint);
                        Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Reset " + type + " type status]");
                    }
                }
            }

            sender.sendMessage(serverTitle + " §f타입 포인트 변경 사항 적용 완료");
        });
         */

        /*
        sender.sendMessage(serverTitle);
        sender.sendMessage(serverTitle + " §f첫 접속 지원금 지급");

        int firstConnectionReward = 50000;

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            int prevGold = kr.sizniss.data.Files.getMoney(player, "Gold");

            kr.sizniss.data.Files.addMoney(player, "Gold", firstConnectionReward); // 자산 지급

            int newGold = kr.sizniss.data.Files.getMoney(player, "Gold");

            sender.sendMessage(serverTitle + " §fUser: §6" + player.getName() + " §f|| §fGold: §7" + new DecimalFormat("###,###").format(prevGold) + " §f→ §7" + new DecimalFormat("###,###").format(newGold) + " §f(§a+" + new DecimalFormat("###,###").format(firstConnectionReward) + "§f)");
            Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Get first connection reward]");
        }
         */

        /*
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            sender.sendMessage(serverTitle);
            sender.sendMessage(serverTitle + " §f병과 포인트 변경 사항 적용");

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                String kind = "보병";

                kr.sizniss.data.Files.removeClassStatus(player, kind, "아이템_지속_시간");

                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Remove " + kind + " kind's item duration status]");
            }

            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                String kind = "교란병";

                kr.sizniss.data.Files.removeClassStatus(player, kind, "아이템_지속_시간");

                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Remove " + kind + " kind's item duration status]");
            }

            sender.sendMessage(serverTitle + " §f병과 포인트 변경 사항 적용 완료");
        });
         */

        return true;
    }

    public boolean ConvertDefault(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        // 점화 초기화
        player.setFireTicks(0);

        // 포션 효과 초기화
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }


        // 체력 조정
        int health = 20;
        if (!player.isDead()) {
            player.setHealth(health);
        }
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);


        // 이동 속도 조정
        float moveSpeed = 0.2f;
        player.setWalkSpeed(moveSpeed);


        // 넉백 저항 조정
        double knockbackResistance = 0.0;
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockbackResistance);

        return true;
    }

    public boolean ConvertHuman(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Inventory inventory = player.getInventory();

        final String kind = variables.getSelectedClass().get(player);

        // 점화 초기화
        player.setFireTicks(0);

        // 포션 효과 초기화
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }


        // 포션 효과 부여
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            methods.passiveApply(player);
        }, 1L);

        // 신입 유저 지원 메시지
        int newUserLevel = methods.getNewUserLevel(player);
        if (newUserLevel >= 1) {
            player.sendMessage(serverTitle);
            player.sendMessage(serverTitle + " §e§l신입 유저 §f§l지원 시스템 발동");
            player.sendMessage(serverTitle + " §f§l추가 지속 효과:");
            player.sendMessage(serverTitle + " §f- §b+생명력 강화(Lv.1)");
        }
        if (newUserLevel >= 2) {
            player.sendMessage(serverTitle + " §f- §b+재생(Lv.1)");
        }
        if (newUserLevel >= 3) {
            player.sendMessage(serverTitle + " §f- §b+속도 증가(Lv.1)");
        }


        // 체력 조정
        double maxHealth = methods.getMaxHealth(player);
        double health = maxHealth;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        if (!player.isDead()) {
            player.setHealth(health);
        }


        // 이동 속도 조정
        float moveSpeed = methods.getMoveSpeed(player);
        player.setWalkSpeed(moveSpeed);


        // 넉백 저항 조정
        double knockbackResistance = 0.0;
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockbackResistance);


        // 주 무기 지급
        String selectedMainWeaponName = variables.getSelectedMainWeapon().get(player);
        ItemStack mainWeapon;
        int mainWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(player, selectedMainWeaponName);
        if (mainWeaponLevel == 0) {
            mainWeapon = new CSUtility().generateWeapon(selectedMainWeaponName).clone();
        } else {
            mainWeapon = new CSUtility().generateWeapon("(+" + mainWeaponLevel + ")_" + selectedMainWeaponName).clone();
        }
        if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) {
            // 장전된 탄약 제거
            ItemMeta mainWeaponMeta = mainWeapon.getItemMeta();
            String mainWeaponName = mainWeaponMeta.getDisplayName();
            mainWeaponMeta.setDisplayName(mainWeaponName.replace(
                    "«" + mainWeaponName.split("«")[1].split("»")[0] + "»",
                    "«" + "0" + "»"
            ));
            mainWeapon.setItemMeta(mainWeaponMeta);
        }
        inventory.setItem(0, mainWeapon);

        // 보조 무기 지급
        String selectedSubWeaponName = variables.getSelectedSubWeapon().get(player);
        ItemStack subWeapon;
        int subWeaponLevel = kr.sizniss.data.Files.getSubWeaponLevel(player, selectedSubWeaponName);
        if (subWeaponLevel== 0) {
            subWeapon = new CSUtility().generateWeapon(selectedSubWeaponName).clone();
        } else {
            subWeapon = new CSUtility().generateWeapon("(+" + subWeaponLevel + ")_" + selectedSubWeaponName).clone();
        }
        inventory.setItem(1, subWeapon);

        // 근접 무기 지급
        String selectedMeleeWeaponName = variables.getSelectedMeleeWeapon().get(player);
        ItemStack meleeWeapon;
        int meleeWeaponLevel = kr.sizniss.data.Files.getMeleeWeaponLevel(player, selectedMeleeWeaponName);
        if (meleeWeaponLevel== 0) {
            meleeWeapon = new CSUtility().generateWeapon(selectedMeleeWeaponName).clone();
        } else {
            meleeWeapon = new CSUtility().generateWeapon("(+" + meleeWeaponLevel + ")_" + selectedMeleeWeaponName).clone();
        }
        inventory.setItem(2, meleeWeapon);


        // 탄약 지급
        if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY) {
            ItemStack ammo = variables.getAmmo().clone();
            ItemStack[] extraAmmo = { ammo.clone(), ammo.clone() };

            int ammoAmount = methods.getAmmoAmount(player);
            if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY) {
                ammoAmount -= methods.getPowderChamberSize(selectedMainWeaponName, mainWeaponLevel);
            }
            int[] extraAmount = { 0, 0 };
            if (ammoAmount > 127) {
                extraAmount[0] = ammoAmount - 127;
                if (extraAmount[0] > 127) {
                    extraAmount[1] = extraAmount[0] - 127;
                    extraAmount[0] = 127;
                }
                ammoAmount = 127;
            }

            ammo.setAmount(ammoAmount);
            extraAmmo[0].setAmount(extraAmount[0]);
            extraAmmo[1].setAmount(extraAmount[1]);

            player.getInventory().setItem(8, ammo);
            player.getInventory().setItem(7, extraAmmo[0]);
            player.getInventory().setItem(6, extraAmmo[1]);
        }


        // 사약 지급
        if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY
                && (SelonZombieSurvival.manager.getTimer() == 0 || SelonZombieSurvival.manager.getTimer() > 5)) {
            ItemStack poison = variables.getPoison().clone();
            inventory.setItem(4, poison);

            methods.cooldown(player, poison, 4, 2);
        }

        return true;
    }

    public boolean ConvertZombie(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();

        final String type = variables.getSelectedType().get(player);

        // 점화 초기화
        player.setFireTicks(0);

        // 포션 효과 초기화
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }


        // 포션 효과 부여
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            methods.passiveApply(player);
        }, 1L);

        // 신입 유저 지원 메시지
        int newUserLevel = methods.getNewUserLevel(player);
        if (newUserLevel >= 1) {
            player.sendMessage(serverTitle);
            player.sendMessage(serverTitle + " §e§l신입 유저 §f§l지원 시스템 발동");
            player.sendMessage(serverTitle + " §f§l추가 지속 효과:");
            player.sendMessage(serverTitle + " §f- §b+생명력 강화(Lv.5)");
        }
        if (newUserLevel >= 2) {
            player.sendMessage(serverTitle + " §f- §b+재생(Lv.1)");
        }
        if (newUserLevel >= 3) {
            player.sendMessage(serverTitle + " §f- §b+속도 증가(Lv.1)");
        }


        // 체력 조정
        double maxHealth = methods.getMaxHealth(player);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        if (!player.isDead()) {
            player.setHealth(maxHealth);
        }


        // 이동 속도 조정
        float moveSpeed = methods.getMoveSpeed(player);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(moveSpeed);


        // 넉백 저항 조정
        double knockbackResistance = 0.0;
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockbackResistance);


        // 갈퀴 지급
        ItemStack rake = variables.getRake().clone();
        inventory.setItem(0, rake);


        // 주 능력 지급
        ItemStack ability = methods.getTypeItem(type).clone();
        int itemAmount = methods.getTypeItemAmount(player);
        inventory.setItem(2, ability);
        methods.cooldown(player, ability, 2, variables.getAbilityCooldown().get(player) - 1, itemAmount);

        // 보조 능력 지급
        ItemStack skill = variables.getLeap().clone();
        inventory.setItem(3, skill);
        methods.cooldown(player, skill, 3, variables.getSkillCooldown().get(player) - 1);


        // 변이 효과 적용
        methods.variationApply(player);

        // 변이 조회기 지급
        ItemStack variationViewer = methods.getVariationViewer(player);
        inventory.setItem(8, variationViewer);

        // 변이 진행
        int variationDelay = variables.getVariationDelay().get(player);
        methods.variationProgress(player, variationViewer, 8, variationDelay - 1);

        return true;
    }

    public boolean ConvertHost(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();

        // 투구 변경
        ItemStack helmet = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("1"));
        helmet.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName("§c숙주");
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        helmet.setItemMeta(helmetMeta);
        inventory.setHelmet(helmet);

        return true;
    }

    public boolean ConvertHero(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();

        // 포션 효과 초기화
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }


        // 포션 효과 부여
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            methods.passiveApply(player);
        }, 1L);


        // 투구 변경
        ItemStack helmet = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("15"));
        helmet.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName("§9영웅");
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        helmet.setItemMeta(helmetMeta);
        inventory.setHelmet(helmet);

        return true;
    }

    public boolean ConvertCarrier(CommandSender sender, Command command, String label, String[] args) {
        return ConvertHost(sender, command, label, args);
    }

    public boolean AcquiredSupply(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        // 탄약 지급
        String selectedMainWeaponName = variables.getSelectedMainWeapon().get(player);
        int mainWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(player, selectedMainWeaponName);

        int ammoAmount = methods.getAmmoAmount(selectedMainWeaponName, mainWeaponLevel, 0.5);
        methods.addAmmoAmount(player, ammoAmount);

        // 병과 아이템 지급
        String kind = variables.getSelectedClass().get(player);
        ItemStack item = methods.getClassItem(kind).clone();

        int itemAmount = methods.getClassItemAmount(kind);

        if (player.getInventory().getItem(4) != null
                && player.getInventory().getItem(4).getType() != Material.AIR) {
            itemAmount += player.getInventory().getItem(4).getAmount();
        }
        if (itemAmount >= 64) {
            itemAmount = 64;
        }

        item.setAmount(itemAmount);

        player.getInventory().setItem(4, item);

        return true;
    }

    public boolean RewardWin(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;

        int roundTime = manager.getRoundTime();
        int timer = manager.getTimer();
        int winGoldReward = variables.getWinGoldReward() * (roundTime - timer);
        int winEmeraldReward = (roundTime - timer) < (roundTime/2) ? variables.getWinEmeraldReward() : variables.getWinEmeraldReward() + 1;

        // 신입 유저 보상 지원
        if (methods.isNewUser(player)) {
            int newUserLevel = methods.getNewUserLevel(player);

            winGoldReward += winGoldReward * (newUserLevel + 1);
            winEmeraldReward += winEmeraldReward * (newUserLevel + 1);
        }

        kr.sizniss.data.Files.addRecord(player, "Win", 1);
        kr.sizniss.data.Files.addMoney(player, "Gold", winGoldReward);
        kr.sizniss.data.Files.addMoney(player, "Emerald", winEmeraldReward);

        player.sendMessage(serverTitle + " §f§l라운드에서 승리하였습니다. [ §e§l+" + winGoldReward + "G§f§l, §a§l+" + winEmeraldReward + "E §f§l]");

        return true;
    }

    public boolean RewardLose(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;

        int roundTime = manager.getRoundTime();
        int timer = manager.getTimer();
        int loseGoldReward = variables.getLoseGoldReward() * (roundTime - timer);
        int loseEmeraldReward = (roundTime - timer) < (roundTime/2) ? variables.getLoseEmeraldReward() : variables.getLoseEmeraldReward() + 1;

        // 신입 유저 보상 지원
        if (methods.isNewUser(player)) {
            int newUserLevel = methods.getNewUserLevel(player);

            loseGoldReward += loseGoldReward * (newUserLevel + 1);
            loseEmeraldReward += loseEmeraldReward * (newUserLevel + 1);
        }

        kr.sizniss.data.Files.addRecord(player, "Lose", 1);
        kr.sizniss.data.Files.addMoney(player, "Gold", loseGoldReward);
        kr.sizniss.data.Files.addMoney(player, "Emerald", loseEmeraldReward);

        player.sendMessage(serverTitle + " §f§l라운드에서 패배하였습니다. [ §e§l+" + loseGoldReward + "G§f§l, §a§l+" + loseEmeraldReward + "E §f§l]");

        return true;
    }

    public boolean RewardKill(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        int killReward = variables.getKillReward();

        kr.sizniss.data.Files.addRecord(player, "Kill", 1);

        if (SelonZombieSurvival.manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우
            if (SelonZombieSurvival.manager.getCarrierList().contains(player)) { // 플레이어가 보균자일 경우
                return false;
            }

            killReward -= killReward * (50.0 / 100);
            kr.sizniss.data.Files.addMoney(player, "Gold", killReward);

            // 신입 유저 보상 지원
            if (methods.isNewUser(player)) {
                int newUserLevel = methods.getNewUserLevel(player);

                killReward += killReward * (newUserLevel + 1);
            }

            player.sendMessage(serverTitle + " §c§l좀비§f§l를 처치하였습니다. [ §e§l+" + killReward + "G §f§l]");
        }
        else if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
            killReward += killReward * (50.0/100);
            kr.sizniss.data.Files.addMoney(player, "Gold", killReward);

            // 신입 유저 보상 지원
            if (methods.isNewUser(player)) {
                int newUserLevel = methods.getNewUserLevel(player);

                killReward += killReward * (newUserLevel + 1);
            }

            player.sendMessage(serverTitle + " §9§l인간§f§l을 처치하였습니다. [ §e§l+" + killReward + "G §f§l]");
        }

        return true;
    }

    public boolean RewardCure(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        int cureReward = variables.getCureReward();

        // 신입 유저 보상 지원
        if (methods.isNewUser(player)) {
            int newUserLevel = methods.getNewUserLevel(player);

            cureReward += cureReward * (newUserLevel + 1);
        }

        kr.sizniss.data.Files.addRecord(player, "Cure", 1);
        kr.sizniss.data.Files.addMoney(player, "Gold", cureReward);

        player.sendMessage(serverTitle + " §c§l좀비§f§l를 치유하였습니다. [ §e§l+" + cureReward + "G §f§l]");

        return true;
    }

    public boolean RewardDeath(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        int deathReward = variables.getDeathReward();

        // 신입 유저 보상 지원
        if (methods.isNewUser(player)) {
            int newUserLevel = methods.getNewUserLevel(player);

            deathReward += deathReward * (newUserLevel + 1);
        }

        kr.sizniss.data.Files.addRecord(player, "Death", 1);
        if (deathReward > 0) {
            kr.sizniss.data.Files.addMoney(player, "Gold", deathReward);
        }

        return true;
    }

    public boolean EscapePenalty(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        int escapePenalty = variables.getEscapePenalty();

        kr.sizniss.data.Files.addRecord(player, "Escape", 1);
        if (methods.getNewUserLevel(player) == 0) {
            kr.sizniss.data.Files.subtractMoney(player, "Gold", escapePenalty);
        }

        return true;
    }


    public boolean Minelist(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        if (!sender.getName().equals("CONSOLE")) {
            return false;
        }


        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

        boolean isEmpty = true;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer == targetPlayer) {
                isEmpty = false;
                break;
            }
        }

        if (isEmpty) { // 대상 플레이어가 없을 경우
            sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

            return false; // 함수 반환
        }

        kr.sizniss.data.Files.addBox(targetPlayer, "Minelist Box", 1); // 상자 추가

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String minelistDate = formatter.format(Files.getMinelistDate());
        String currentDate = formatter.format(new Date());
        if (!minelistDate.equals(currentDate)) { // 같은 날이 아닐 경우
            Files.setMinelistUser(new ArrayList<UUID>()); // 마인리스트 추천 유저 목록 초기화
            Files.setMinelistDate(new Date()); // 날짜 갱신
        }
        Files.addMinelistUser(targetPlayer.getUniqueId()); // 마인리스트 추천 목록 추가

        return true;
    }

    public boolean UnBan(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            String targetPlayerName = args[0];

            Bukkit.getBanList(BanList.Type.NAME).pardon(targetPlayerName);

            sender.sendMessage("§f플레이어 " + targetPlayerName + "의 차단을 해제하였습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Unban " + targetPlayerName + "]");

        } else {
            sender.sendMessage("§c사용법: /unban [플레이어]");
        }

        return true;
    }

    public boolean KickAll(CommandSender sender, Command command, String label, String[] args) {
        String reason;
        if (args.length > 0) {
            reason = args[0];
            for (int i = 1; i < args.length; i++) {
                reason = reason + " " + args[i];
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.equals(sender)) { // 플레이어가 명령자일 경우
                    continue;
                }

                player.kickPlayer(reason);
            }

            sender.sendMessage("§f모든 플레이어를 추방하였습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Kicked all players from the game: '" + reason + "']");
        } else {
            reason = "Kicked by an operator.";

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.equals(sender)) { // 플레이어가 명령자일 경우
                    continue;
                }

                player.kickPlayer(reason);
            }

            sender.sendMessage("§f모든 플레이어를 추방하였습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Kicked all players from the game]");
        }

        return true;
    }

    public boolean TPAll(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("You must specify which player you wish to perform this action on.");

            return false;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.equals(sender)) { // 플레이어가 명령자일 경우
                continue;
            }

            player.teleport((Player) sender);
        }

        sender.sendMessage("§f모든 플레이어를 " + sender.getName() + "에게로 순간이동시켰습니다");
        Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Teleported all players to " + sender.getName() + "]");
        
        return true;
    }

    public boolean GM(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            GameMode gameMode;

            if (args.length > 1) {
                Player targetPlayer = Bukkit.getPlayer(args[1]);
                if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                    sender.sendMessage("§f플레이어 '" + args[1] + "'을(를) 찾을 수 없습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Player '" + args[1] + "' cannot be found");

                    return false; // 함수 반환
                }

                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("Survival")) {
                    gameMode = GameMode.SURVIVAL;
                    targetPlayer.sendMessage("§f게임 모드가 서바이벌 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s game mode to Survival Mode]");
                } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("Creative")) {
                    gameMode = GameMode.CREATIVE;
                    targetPlayer.sendMessage("§f게임 모드가 크리에이티브 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s game mode to Creative Mode]");
                } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("Adventure")) {
                    gameMode = GameMode.ADVENTURE;
                    targetPlayer.sendMessage("§f게임 모드가 모험 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s game mode to Adventure Mode]");
                } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("Spectator")) {
                    gameMode = GameMode.SPECTATOR;
                    targetPlayer.sendMessage("§f게임 모드가 관전 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s game mode to Spectator Mode]");
                } else {
                    gameMode = GameMode.SURVIVAL;
                    targetPlayer.sendMessage("§f게임 모드가 서바이벌 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s game mode to Survival Mode]");
                }

                targetPlayer.setGameMode(gameMode);
            } else {
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("Survival")) {
                    gameMode = GameMode.SURVIVAL;
                    sender.sendMessage("§f게임 모드가 서바이벌 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own game mode to Survival Mode]");
                } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("Creative")) {
                    gameMode = GameMode.CREATIVE;
                    sender.sendMessage("§f게임 모드가 크리에이티브 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own game mode to Creative Mode]");
                } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("Adventure")) {
                    gameMode = GameMode.ADVENTURE;
                    sender.sendMessage("§f게임 모드가 모험 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own game mode to Adventure Mode]");
                } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("Spectator")) {
                    gameMode = GameMode.SPECTATOR;
                    sender.sendMessage("§f게임 모드가 관전 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own game mode to Spectator Mode]");
                } else {
                    gameMode = GameMode.SURVIVAL;
                    sender.sendMessage("§f게임 모드가 서바이벌 모드로 갱신되었습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own game mode to Survival Mode]");
                }

                ((Player) sender).setGameMode(gameMode);
            }
        } else {
            sender.sendMessage("§c사용법: /gm <모드> [플레이어]");
        }

        return true;
    }

    public boolean Spawn(CommandSender sender, Command command, String label, String[] args) {
        Location spawnLocation = Bukkit.getWorld("world").getSpawnLocation();

        if (args.length > 0) {

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                sender.sendMessage("§f플레이어 '" + args[0] + "'을(를) 찾을 수 없습니다");
                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[0] + "' cannot be found]");

                return false; // 함수 반환
            }

            targetPlayer.teleport(spawnLocation);
            targetPlayer.sendMessage("§f스폰으로 이동하였습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": " + targetPlayer.getName() + " Move to spawn location]");

        } else {

            Player player = (Player) sender;

            player.teleport(spawnLocation);
            player.sendMessage("§f스폰으로 이동하였습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": " + player.getName() + " Move to spawn location]");

        }

        return true;
    }

    public boolean Heal(CommandSender sender, Command command, String label, String[] args) {
        Double maxHealth;

        if (args.length > 0) {

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                sender.sendMessage("§f플레이어 '" + args[0] + "'을(를) 찾을 수 없습니다");
                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[0] + "' cannot be found]");

                return false; // 함수 반환
            }

            maxHealth = targetPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            targetPlayer.sendMessage("§f회복되었습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Heal " + args[0] + "]");

            // 포션 효과 초기화
            for (PotionEffect effect : targetPlayer.getActivePotionEffects()) {
                targetPlayer.removePotionEffect(effect.getType());
            }
            if (!targetPlayer.isDead()) {
                targetPlayer.setHealth(maxHealth); // 체력 초기화
            }

        } else {

            maxHealth = ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            sender.sendMessage("§f회복되었습니다");
            Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Heal own]");

            // 포션 효과 초기화
            for (PotionEffect effect : ((Player) sender).getActivePotionEffects()) {
                ((Player) sender).removePotionEffect(effect.getType());
            }
            if (!((Player) sender).isDead()) {
                ((Player) sender).setHealth(maxHealth); // 체력 초기화
            }

        }

        return true;
    }

    public boolean MaxHealth(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Double amount;

            if (args.length > 1) {
                amount = Double.parseDouble(args[0]);
                if (amount.isNaN()) { // 값이 숫자가 아닐 경우
                    sender.sendMessage("§f'" + args[0] + "'은(는) 올바른 숫자가 아닙니다.");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Player '" + args[0] + "' is not a valid number]");

                    return false; // 함수 반환
                }

                Player targetPlayer = Bukkit.getPlayer(args[1]);
                if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                    sender.sendMessage("§f플레이어 '" + args[1] + "'을(를) 찾을 수 없습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[1] + "' cannot be found]");

                    return false; // 함수 반환
                }

                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s max health to " + amount + "]");
                targetPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
            } else {
                amount = Double.parseDouble(args[0]);
                if (amount.isNaN()) { // 값이 숫자가 아닐 경우
                    sender.sendMessage("§f'" + args[0] + "'은(는) 올바른 숫자가 아닙니다.");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[0] + "' is not a valid number]");

                    return false; // 함수 반환
                }

                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own max health to " + amount + "]");
                ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
            }
        } else {
            sender.sendMessage("§c사용법: /maxhealth <양> [플레이어]");
        }

        return true;
    }

    public boolean Health(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Double amount;
            Double maxHealth;

            if (args.length > 1) {
                amount = Double.parseDouble(args[0]);
                if (amount.isNaN()) { // 값이 숫자가 아닐 경우
                    sender.sendMessage("§f'" + args[0] + "'은(는) 올바른 숫자가 아닙니다.");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Player '" + args[0] + "' is not a valid number]");

                    return false; // 함수 반환
                }

                Player targetPlayer = Bukkit.getPlayer(args[1]);
                if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                    sender.sendMessage("§f플레이어 '" + args[1] + "'을(를) 찾을 수 없습니다");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[1] + "' cannot be found]");

                    return false; // 함수 반환
                }

                maxHealth = targetPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                if (amount > maxHealth) { // 값이 최대 체력보다 클 경우
                    amount = maxHealth; // 값을 최대 체력으로 변경
                }

                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + args[1] + "'s health to " + amount + "]");
                if (!targetPlayer.isDead()) {
                    targetPlayer.setHealth(amount);
                }
            } else {
                amount = Double.parseDouble(args[0]);
                if (amount.isNaN()) { // 값이 숫자가 아닐 경우
                    sender.sendMessage("§f'" + args[0] + "'은(는) 올바른 숫자가 아닙니다.");
                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[0] + "' is not a valid number]");

                    return false; // 함수 반환
                }

                maxHealth = ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                if (amount > maxHealth) { // 값이 최대 체력보다 클 경우
                    amount = maxHealth; // 값을 최대 체력으로 변경
                }

                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set own health to " + amount + "]");
                if (!((Player) sender).isDead()) {
                    ((Player) sender).setHealth(amount);
                }
            }
        } else {
            sender.sendMessage("§c사용법: /maxhealth <양> [플레이어]");
        }

        return true;
    }

    public boolean ChatOp(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                sender.sendMessage("§f플레이어 '" + args[0] + "'을(를) 찾을 수 없습니다");
                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[0] + "' cannot be found]");

                return false; // 함수 반환
            }

            if (args.length > 1) {

                String chat = args[1];
                for (int i = 2; i < args.length; i++) {
                    chat = chat + " " + args[i];
                }

                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": " + args[0] + " chats '" + chat + "']");
                if (targetPlayer.isOp()) {
                    Bukkit.dispatchCommand(targetPlayer, chat); // 명령어 실행
                } else {
                    targetPlayer.setOp(true);
                    Bukkit.dispatchCommand(targetPlayer, chat); // 명령어 실행
                    targetPlayer.setOp(false);
                }

            } else {
                sender.sendMessage("§c사용법: /chatop [플레이어] <명령어>");
            }

        } else {
            sender.sendMessage("§c사용법: /chatop [플레이어] <명령어>");
        }

        return true;
    }

    public boolean Vanish(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<Player> vanishPlayers = variables.getVanishPlayers();

        if (args.length > 0) {

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) { // 대상 플레이어가 없을 경우
                sender.sendMessage("§f플레이어 '" + args[0] + "'을(를) 찾을 수 없습니다");
                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": '" + args[0] + "' cannot be found]");

                return false; // 함수 반환
            }


            if (vanishPlayers.contains(targetPlayer)) {
                variables.getVanishPlayers().remove(targetPlayer);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.equals(targetPlayer) || onlinePlayer.isOp()) { // 대상이 자신이거나 오피일 경우
                        continue;
                    }

                    onlinePlayer.showPlayer(plugin, targetPlayer); // 플레이어 드러내기
                }

                targetPlayer.setGameMode(GameMode.ADVENTURE); // 게임 모드 변경

                targetPlayer.sendMessage("§f다른 플레이어로부터 모습을 드러냈습니다");
                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Show own from other players]");
            } else {
                variables.getVanishPlayers().add(targetPlayer);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.equals(targetPlayer) || onlinePlayer.isOp()) { // 대상이 자신이거나 오피일 경우
                        continue;
                    }

                    onlinePlayer.hidePlayer(plugin, targetPlayer); // 플레이어 감추기
                }

                targetPlayer.setGameMode(GameMode.SPECTATOR); // 게임 모드 변경

                targetPlayer.sendMessage("§f다른 플레이어로부터 감춰졌습니다");
                Bukkit.getConsoleSender().sendMessage("[" + targetPlayer.getName() + ": Hide own from other players]");
            }

        } else {
            Player player = (Player) sender;

            if (vanishPlayers.contains(player)) {
                variables.getVanishPlayers().remove(player);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.equals(player) || onlinePlayer.isOp()) { // 대상이 자신이거나 오피일 경우
                        continue;
                    }

                    onlinePlayer.showPlayer(plugin, player); // 플레이어 드러내기
                }

                player.setGameMode(GameMode.ADVENTURE); // 게임 모드 변경

                sender.sendMessage("§f다른 플레이어로부터 모습을 드러냈습니다");
                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Show own from other players]");
            } else {
                variables.getVanishPlayers().add(player);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.equals(player) || onlinePlayer.isOp()) { // 대상이 자신이거나 오피일 경우
                        continue;
                    }

                    onlinePlayer.hidePlayer(plugin, player); // 플레이어 감추기
                }

                player.setGameMode(GameMode.SPECTATOR); // 게임 모드 변경

                sender.sendMessage("§f다른 플레이어로부터 감춰졌습니다");
                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Hide own from other players]");
            }
        }

        return true;
    }


    public boolean Donation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            OfflinePlayer targetPlayer = null;
            if (args.length > 1) {
                targetPlayer = Bukkit.getOfflinePlayer(args[1]);

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[1] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }
            }

            if (args[0].equalsIgnoreCase("Info")) {
                if (args.length > 1) {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                    if (Files.getDonationPlayerList().get(targetPlayer.getUniqueId().toString()) == null) { // 후원 내역이 없을 경우
                        sender.sendMessage(" §fNameList: §7" + targetPlayer.getName());
                        sender.sendMessage(" §fAmount: §7" + 0);
                        sender.sendMessage(" §fCash: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Cash")));
                    } else { // 후원 내역이 있을 경우
                        sender.sendMessage(" §fNameList: §7" + Files.getDonationNameList(targetPlayer));
                        sender.sendMessage(" §fAmount: §7" + new DecimalFormat("###,###").format(Files.getDonationAmount(targetPlayer)));
                        sender.sendMessage(" §fCash: §7" + new DecimalFormat("###,###").format(kr.sizniss.data.Files.getMoney(targetPlayer, "Cash")));
                    }

                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": View server donation ledger]");
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §f§l/Donation Info §e§l<Player>§7§l: §f플레이어의 후원 내역을 확인합니다.");
                }
            } else if (args[0].equalsIgnoreCase("Add")) {
                if (args.length > 1) {
                    if (args.length > 2) {
                        int amount = Integer.parseInt(args[2]);

                        int prevAmount = Files.getDonationAmount(targetPlayer);
                        int prevCash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");

                        Files.addDonationBreakdown(new Date(), targetPlayer, amount); // 후원 내역 추가

                        // 후원 장부 수정
                        Files.addDonationTotalAmount(amount);
                        if (!Files.getDonationName(targetPlayer).equals(targetPlayer.getName())) { // 후원 장부 이름과 플레이어 이름이 일치하지 않을 경우
                            Files.setDonationName(targetPlayer, targetPlayer.getName());
                            Files.addDonationNameList(targetPlayer, targetPlayer.getName());
                        }
                        Files.addDonationAmount(targetPlayer, amount);

                        kr.sizniss.data.Files.addMoney(targetPlayer, "Cash", amount); // 자산 추가

                        int newAmount = Files.getDonationAmount(targetPlayer);
                        int newCash = kr.sizniss.data.Files.getMoney(targetPlayer, "Cash");

                        sender.sendMessage("");
                        sender.sendMessage(" " + serverTitle);
                        sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                        sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                        sender.sendMessage(" §fNameList: §7" + Files.getDonationNameList(targetPlayer));
                        sender.sendMessage(" §fAmount: §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§a+" + new DecimalFormat("###,###").format(amount) + "§f)");
                        sender.sendMessage(" §fCash: §7" + new DecimalFormat("###,###").format(prevCash) + " §f→ §7" + new DecimalFormat("###,###").format(newCash) + " §f(§a+" + new DecimalFormat("###,###").format(amount) + "§f)");

                        Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": " + targetPlayer.getName() + " donates " + new DecimalFormat("###,###").format(amount) + " won to the server]");

                        if (prevAmount < 100000 && newAmount >= 100000) { // 100,000 이상 후원금이 누적되었을 경우
                            String prefix = PermissionsEx.getUser(targetPlayer.getName()).getPrefix().replace("&", "§");
                            if (prefix.equals("User")) { // 유저일 경우
                                String color = "Magenta";
                                kr.sizniss.data.Files.setPrefixColor(targetPlayer, color, true); // 코디 지급
                                kr.sizniss.data.Files.setOptionCodi(targetPlayer, "PrefixColor", color); // 코디 설정

                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + targetPlayer.getName() + " group set vip"); // VIP 그룹 변경
                            }
                        }
                    } else {
                        sender.sendMessage("");
                        sender.sendMessage(" " + serverTitle);
                        sender.sendMessage(" §f§l/Donation Add §e§l<Player> <Amount>§7§l: §f플레이어의 후원 내역을 추가합니다.");
                    }
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §f§l/Donation Add §e§l<Player> <Amount>§7§l: §f플레이어의 후원 내역을 추가합니다.");
                }
            }
        } else {
            sender.sendMessage("");
            sender.sendMessage(" " + serverTitle);
            sender.sendMessage(" §f§l/Donation Info §e§l<Player>§7§l: §f플레이어의 후원 내역을 확인합니다.");
            sender.sendMessage(" §f§l/Donation Add §e§l<Player> <Amount>§7§l: §f플레이어의 후원 내역을 추가합니다.");
        }

        return true;
    }

    public boolean Var(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("Money")) {
                if (args.length > 1) {

                    String money = null;
                    if (args[1].equalsIgnoreCase("Cash")) {
                        money = "Cash";
                    } else if (args[1].equalsIgnoreCase("Gold")) {
                        money = "Gold";
                    } else if (args[1].equalsIgnoreCase("Diamond")) {
                        money = "Diamond";
                    } else if (args[1].equalsIgnoreCase("Emerald")) {
                        money = "Emerald";
                    } else {
                        sender.sendMessage(serverTitle + " §f존재하지 않는 자산입니다!");

                        return false;
                    }

                    OfflinePlayer targetPlayer = null;
                    if (args.length > 3) {
                        if (args.length > 1) {
                            targetPlayer = Bukkit.getOfflinePlayer(args[3]);

                            boolean isEmpty = true;
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (offlinePlayer == targetPlayer) {
                                    isEmpty = false;
                                    break;
                                }
                            }

                            if (isEmpty) { // 대상 플레이어가 없을 경우
                                sender.sendMessage(serverTitle + " §f플레이어 '§6" + args[3] + "§f'을(를) 찾을 수 없습니다!");

                                return false; // 함수 반환
                            }
                        }
                    }

                    if (args.length > 2) {
                        if (args[2].equalsIgnoreCase("Info")) {
                            if (args.length > 3) {
                                int amount = kr.sizniss.data.Files.getMoney(targetPlayer, money);

                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                sender.sendMessage(" §f" + money + ": §7" + new DecimalFormat("###,###").format(amount));

                                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": View " + targetPlayer.getName() + "'s " + money.toLowerCase() + "]");
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Money §e<Money> §fInfo §e<Player>§7: §f플레이어의 자산 값을 확인합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Set")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getMoney(targetPlayer, money);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.setMoney(targetPlayer, money, amount); // 자산 설정

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + money + ": §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §a" + new DecimalFormat("###,###").format(amount));

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + targetPlayer.getName() + "'s " + money.toLowerCase() + " to " + amount + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Money §e<Money> §fSet §e<Player> <Amount>§7: §f플레이어의 자산 값을 설정합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Money §e<Money> §fSet §e<Player> <Amount>§7: §f플레이어의 자산 값을 설정합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Add")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getMoney(targetPlayer, money);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.addMoney(targetPlayer, money, amount); // 자산 추가

                                    int newAmount = kr.sizniss.data.Files.getMoney(targetPlayer, money);

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + money + ": §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§a+" + new DecimalFormat("###,###").format(amount) + "§f)");

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Add " + amount + " to " + targetPlayer.getName() + "'s " + money.toLowerCase() + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Money §e<Money> §fAdd §e<Player> <Amount>§7: §f플레이어의 자산 값을 추가합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Money §e<Money> §fAdd §e<Player> <Amount>§7: §f플레이어의 자산 값을 추가합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Subtract")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getMoney(targetPlayer, money);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.subtractMoney(targetPlayer, money, amount); // 자산 차감

                                    int newAmount = kr.sizniss.data.Files.getMoney(targetPlayer, money);

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + money + ": §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§c-" + new DecimalFormat("###,###").format(amount) + "§f)");

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Subtract " + amount + " from " + targetPlayer.getName() + "'s " + money.toLowerCase() + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Money §e<Money> §fSubtract §e<Player> <Amount>§7: §f플레이어의 자산 값을 차감합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Money §e<Money> §fSubtract §e<Player> <Amount>§7: §f플레이어의 자산 값을 차감합니다.");
                            }
                        }
                    } else {
                        sender.sendMessage("");
                        sender.sendMessage(" " + serverTitle);
                        sender.sendMessage(" §f/Var Money §e<Money> §fInfo §e<Player>§7: §f플레이어의 자산 값을 확인합니다.");
                        sender.sendMessage(" §f/Var Money §e<Money> §fSet §e<Player> <Amount>§7: §f플레이어의 자산 값을 설정합니다.");
                        sender.sendMessage(" §f/Var Money §e<Money> §fAdd §e<Player> <Amount>§7: §f플레이어의 자산 값을 추가합니다.");
                        sender.sendMessage(" §f/Var Money §e<Money> §fSubtract §e<Player> <Amount>§7: §f플레이어의 자산 값을 차감합니다.");
                    }
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §f/Var Money §e<Money> §fInfo §e<Player>§7: §f플레이어의 자산 값을 확인합니다.");
                    sender.sendMessage(" §f/Var Money §e<Money> §fSet §e<Player> <Amount>§7: §f플레이어의 자산 값을 설정합니다.");
                    sender.sendMessage(" §f/Var Money §e<Money> §fAdd §e<Player> <Amount>§7: §f플레이어의 자산 값을 추가합니다.");
                    sender.sendMessage(" §f/Var Money §e<Money> §fSubtract §e<Player> <Amount>§7: §f플레이어의 자산 값을 차감합니다.");
                }
            }
            else if (args[0].equalsIgnoreCase("Box")) {
                if (args.length > 1) {

                    String box = null;
                    if (args[1].equalsIgnoreCase("Normal_Weapon_Box")) {
                        box = "Normal Weapon Box";
                    } else if (args[1].equalsIgnoreCase("Special_Weapon_Box")) {
                        box = "Special Weapon Box";
                    } else if (args[1].equalsIgnoreCase("Premium_Weapon_Box")) {
                        box = "Premium Weapon Box";
                    } else if (args[1].equalsIgnoreCase("Normal_Money_Box")) {
                        box = "Normal Money Box";
                    } else if (args[1].equalsIgnoreCase("Premium_Money_Box")) {
                        box = "Premium Money Box";
                    } else if (args[1].equalsIgnoreCase("Minelist_Box")) {
                        box = "Minelist Box";
                    } else {
                        sender.sendMessage(serverTitle + " §f존재하지 않는 상자입니다!");

                        return false;
                    }

                    OfflinePlayer targetPlayer = null;
                    if (args.length > 3) {
                        if (args.length > 1) {
                            targetPlayer = Bukkit.getOfflinePlayer(args[3]);

                            boolean isEmpty = true;
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (offlinePlayer == targetPlayer) {
                                    isEmpty = false;
                                    break;
                                }
                            }

                            if (isEmpty) { // 대상 플레이어가 없을 경우
                                sender.sendMessage(serverTitle + " §f플레이어 '§6" + args[3] + "§f'을(를) 찾을 수 없습니다!");

                                return false; // 함수 반환
                            }
                        }
                    }

                    if (args.length > 2) {
                        if (args[2].equalsIgnoreCase("Info")) {
                            if (args.length > 3) {
                                int amount = kr.sizniss.data.Files.getBox(targetPlayer, box);

                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                sender.sendMessage(" §f" + box + ": §7" + new DecimalFormat("###,###").format(amount));

                                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": View " + targetPlayer.getName() + "'s " + box + "]");
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Box §e<Box> §fInfo §e<Player>§7: §f플레이어의 상자 값을 확인합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Set")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.setBox(targetPlayer, box, amount); // 상자 설정

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + box + ": §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §a" + new DecimalFormat("###,###").format(amount));

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + targetPlayer.getName() + "'s " + box + " to " + amount + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Box §e<Box> §fSet §e<Player> <Amount>§7: §f플레이어의 상자 값을 설정합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Box §e<Box> §fSet §e<Player> <Amount>§7: §f플레이어의 상자 값을 설정합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Add")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.addBox(targetPlayer, box, amount); // 상자 추가

                                    int newAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + box + ": §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§a+" + new DecimalFormat("###,###").format(amount) + "§f)");

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Add " + amount + " to " + targetPlayer.getName() + "'s " + box + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Box §e<Box> §fAdd §e<Player> <Amount>§7: §f플레이어의 상자 값을 추가합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Box §e<Box> §fAdd §e<Player> <Amount>§7: §f플레이어의 상자 값을 추가합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Subtract")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.subtractBox(targetPlayer, box, amount); // 상자 차감

                                    int newAmount = kr.sizniss.data.Files.getBox(targetPlayer, box);

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + box + ": §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§c-" + new DecimalFormat("###,###").format(amount) + "§f)");

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Subtract " + amount + " from " + targetPlayer.getName() + "'s " + box + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Box §e<Box> §fSubtract §e<Player> <Amount>§7: §f플레이어의 상자 값을 차감합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Box §e<Box> §fSubtract §e<Player> <Amount>§7: §f플레이어의 상자 값을 차감합니다.");
                            }
                        }
                    } else {
                        sender.sendMessage("");
                        sender.sendMessage(" " + serverTitle);
                        sender.sendMessage(" §f/Var Box §e<Box> §fInfo §e<Player>§7: §f플레이어의 상자 값을 확인합니다.");
                        sender.sendMessage(" §f/Var Box §e<Box> §fSet §e<Player> <Amount>§7: §f플레이어의 상자 값을 설정합니다.");
                        sender.sendMessage(" §f/Var Box §e<Box> §fAdd §e<Player> <Amount>§7: §f플레이어의 상자 값을 추가합니다.");
                        sender.sendMessage(" §f/Var Box §e<Box> §fSubtract §e<Player> <Amount>§7: §f플레이어의 상자 값을 차감합니다.");
                    }
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §f/Var Box §e<Box> §fInfo §e<Player>§7: §f플레이어의 상자 값을 확인합니다.");
                    sender.sendMessage(" §f/Var Box §e<Box> §fSet §e<Player> <Amount>§7: §f플레이어의 상자 값을 설정합니다.");
                    sender.sendMessage(" §f/Var Box §e<Box> §fAdd §e<Player> <Amount>§7: §f플레이어의 상자 값을 추가합니다.");
                    sender.sendMessage(" §f/Var Box §e<Box> §fSubtract §e<Player> <Amount>§7: §f플레이어의 상자 값을 차감합니다.");
                }
            }
            else if (args[0].equalsIgnoreCase("Piece")) {
                if (args.length > 1) {

                    String grade = null;
                    if (args[1].equalsIgnoreCase("Bronze")) {
                        grade = "Bronze";
                    } else if (args[1].equalsIgnoreCase("Silver")) {
                        grade = "Silver";
                    } else if (args[1].equalsIgnoreCase("Gold")) {
                        grade = "Gold";
                    } else {
                        sender.sendMessage(serverTitle + " §f존재하지 않는 조각입니다!");

                        return false;
                    }

                    OfflinePlayer targetPlayer = null;
                    if (args.length > 3) {
                        if (args.length > 1) {
                            targetPlayer = Bukkit.getOfflinePlayer(args[3]);

                            boolean isEmpty = true;
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (offlinePlayer == targetPlayer) {
                                    isEmpty = false;
                                    break;
                                }
                            }

                            if (isEmpty) { // 대상 플레이어가 없을 경우
                                sender.sendMessage(serverTitle + " §f플레이어 '§6" + args[3] + "§f'을(를) 찾을 수 없습니다!");

                                return false; // 함수 반환
                            }
                        }
                    }

                    if (args.length > 2) {
                        if (args[2].equalsIgnoreCase("Info")) {
                            if (args.length > 3) {
                                int amount = kr.sizniss.data.Files.getPiece(targetPlayer, grade);

                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                sender.sendMessage(" §f" + grade + " piece: §7" + new DecimalFormat("###,###").format(amount));

                                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": View " + targetPlayer.getName() + "'s " + grade.toLowerCase() + " piece]");
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Piece §e<Grade> §fInfo §e<Player>§7: §f플레이어의 조각 값을 확인합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Set")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getPiece(targetPlayer, grade);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.setPiece(targetPlayer, grade, amount); // 조각 설정

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + grade + " piece: §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §a" + new DecimalFormat("###,###").format(amount));

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + targetPlayer.getName() + "'s " + grade.toLowerCase() + " piece to " + amount + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Piece §e<Grade> §fSet §e<Player> <Amount>§7: §f플레이어의 조각 값을 설정합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Piece §e<Grade> §fSet §e<Player> <Amount>§7: §f플레이어의 조각 값을 설정합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Add")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getPiece(targetPlayer, grade);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.addPiece(targetPlayer, grade, amount); // 조각 추가

                                    int newAmount = kr.sizniss.data.Files.getPiece(targetPlayer, grade);

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + grade + " piece: §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§a+" + new DecimalFormat("###,###").format(amount) + "§f)");

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Add " + amount + " to " + targetPlayer.getName() + "'s " + grade.toLowerCase() + " piece]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Piece §e<Grade> §fAdd §e<Player> <Amount>§7: §f플레이어의 조각 값을 추가합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Piece §e<Grade> §fAdd §e<Player> <Amount>§7: §f플레이어의 조각 값을 추가합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Subtract")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    int prevAmount = kr.sizniss.data.Files.getPiece(targetPlayer, grade);
                                    int amount = Integer.parseInt(args[4]);

                                    kr.sizniss.data.Files.subtractPiece(targetPlayer, grade, amount); // 조각 차감

                                    int newAmount = kr.sizniss.data.Files.getPiece(targetPlayer, grade);

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + grade + " piece: §7" + new DecimalFormat("###,###").format(prevAmount) + " §f→ §7" + new DecimalFormat("###,###").format(newAmount) + " §f(§c-" + new DecimalFormat("###,###").format(amount) + "§f)");

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Subtract " + amount + " from " + targetPlayer.getName() + "'s " + grade.toLowerCase() + " piece]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Piece §e<Grade> §fSubtract §e<Player> <Amount>§7: §f플레이어의 조각 값을 차감합니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Piece §e<Grade> §fSubtract §e<Player> <Amount>§7: §f플레이어의 조각 값을 차감합니다.");
                            }
                        }
                    } else {
                        sender.sendMessage("");
                        sender.sendMessage(" " + serverTitle);
                        sender.sendMessage(" §f/Var Piece §e<Grade> §fInfo §e<Player>§7: §f플레이어의 조각 값을 확인합니다.");
                        sender.sendMessage(" §f/Var Piece §e<Grade> §fSet §e<Player> <Amount>§7: §f플레이어의 조각 값을 설정합니다.");
                        sender.sendMessage(" §f/Var Piece §e<Grade> §fAdd §e<Player> <Amount>§7: §f플레이어의 조각 값을 추가합니다.");
                        sender.sendMessage(" §f/Var Piece §e<Grade> §fSubtract §e<Player> <Amount>§7: §f플레이어의 조각 값을 차감합니다.");
                    }
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §f/Var Piece §e<Grade> §fInfo §e<Player>§7: §f플레이어의 조각 값을 확인합니다.");
                    sender.sendMessage(" §f/Var Piece §e<Grade> §fSet §e<Player> <Amount>§7: §f플레이어의 조각 값을 설정합니다.");
                    sender.sendMessage(" §f/Var Piece §e<Grade> §fAdd §e<Player> <Amount>§7: §f플레이어의 조각 값을 추가합니다.");
                    sender.sendMessage(" §f/Var Piece §e<Grade> §fSubtract §e<Player> <Amount>§7: §f플레이어의 조각 값을 차감합니다.");
                }
            }
            else if (args[0].equalsIgnoreCase("Weapon")) {
                if (args.length > 1) {

                    String weapon = null;
                    if (args[1].equalsIgnoreCase("Main")) {
                        weapon = "Main";
                    } else if (args[1].equalsIgnoreCase("Sub")) {
                        weapon = "Sub";
                    } else if (args[1].equalsIgnoreCase("Melee")) {
                        weapon = "Melee";
                    } else {
                        sender.sendMessage(serverTitle + " §f존재하지 않는 무기입니다!");

                        return false;
                    }

                    OfflinePlayer targetPlayer = null;
                    if (args.length > 3) {
                        if (args.length > 1) {
                            targetPlayer = Bukkit.getOfflinePlayer(args[3]);

                            boolean isEmpty = true;
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (offlinePlayer == targetPlayer) {
                                    isEmpty = false;
                                    break;
                                }
                            }

                            if (isEmpty) { // 대상 플레이어가 없을 경우
                                sender.sendMessage(serverTitle + " §f플레이어 '§6" + args[3] + "§f'을(를) 찾을 수 없습니다!");

                                return false; // 함수 반환
                            }
                        }
                    }

                    if (args.length > 2) {
                        if (args[2].equalsIgnoreCase("Info")) {
                            if (args.length > 3) {
                                String selectedWeapon = kr.sizniss.data.Files.getOptionWeapon(targetPlayer, weapon);

                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                sender.sendMessage(" §f" + weapon + ": §7" + selectedWeapon.replace("_", " "));

                                Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": View " + targetPlayer.getName() + "'s " + weapon.toLowerCase() + "]");
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Weapon §e<Weapon> §fInfo §e<Player>§7: §f플레이어의 장착된 무기를 확인합니다.");
                            }
                        } else if (args[2].equalsIgnoreCase("Set")) {
                            if (args.length > 3) {
                                if (args.length > 4) {
                                    String prevSelectedWeapon = kr.sizniss.data.Files.getOptionWeapon(targetPlayer, weapon);
                                    String selectedWeapon = args[4];

                                    kr.sizniss.data.Files.setOptionWeapon(targetPlayer, weapon, selectedWeapon); // 무기 설정

                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §fUUID: §7" + targetPlayer.getUniqueId().toString());
                                    sender.sendMessage(" §fName: §6" + targetPlayer.getName());
                                    sender.sendMessage(" §f" + weapon + ": §7" + prevSelectedWeapon + " §f→ §a" + selectedWeapon);

                                    Bukkit.getConsoleSender().sendMessage("[" + sender.getName() + ": Set " + targetPlayer.getName() + "'s " + weapon.toLowerCase() + " to " + selectedWeapon.replace("_", " ") + "]");
                                } else {
                                    sender.sendMessage("");
                                    sender.sendMessage(" " + serverTitle);
                                    sender.sendMessage(" §f/Var Weapon §e<Weapon> §fSet §e<Player> <Weapon>§7: §f플레이어에게 무기를 장착시킵니다.");
                                }
                            } else {
                                sender.sendMessage("");
                                sender.sendMessage(" " + serverTitle);
                                sender.sendMessage(" §f/Var Weapon §e<Weapon> §fSet §e<Player> <Weapon>§7: §f플레이어에게 무기를 장착시킵니다.");
                            }
                        }
                    } else {
                        sender.sendMessage("");
                        sender.sendMessage(" " + serverTitle);
                        sender.sendMessage(" §f/Var Weapon §e<Weapon> §fInfo §e<Player>§7: §f플레이어의 장착된 무기를 확인합니다.");
                        sender.sendMessage(" §f/Var Weapon §e<Weapon> §fSet §e<Player> <Weapon>§7: §f플레이어에게 무기를 장착시킵니다.");
                    }
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(" " + serverTitle);
                    sender.sendMessage(" §f/Var Weapon §e<Weapon> §fInfo §e<Player>§7: §f플레이어의 장착된 무기를 확인합니다.");
                    sender.sendMessage(" §f/Var Weapon §e<Weapon> §fSet §e<Player> <Weapon>§7: §f플레이어에게 무기를 장착시킵니다.");
                }
            }
        } else {
            sender.sendMessage("");
            sender.sendMessage(" " + serverTitle);
            sender.sendMessage(" §f/Var Money §e<Money> §fInfo §e<Player>§7: §f플레이어의 자산 값을 확인합니다.");
            sender.sendMessage(" §f/Var Money §e<Money> §fSet §e<Player> <Amount>§7: §f플레이어의 자산 값을 설정합니다.");
            sender.sendMessage(" §f/Var Money §e<Money> §fAdd §e<Player> <Amount>§7: §f플레이어의 자산 값을 추가합니다.");
            sender.sendMessage(" §f/Var Money §e<Money> §fSubtract §e<Player> <Amount>§7: §f플레이어의 자산 값을 차감합니다.");
            sender.sendMessage(" §f/Var Box §e<Box> §fInfo §e<Player>§7: §f플레이어의 상자 값을 확인합니다.");
            sender.sendMessage(" §f/Var Box §e<Box> §fSet §e<Player> <Amount>§7: §f플레이어의 상자 값을 설정합니다.");
            sender.sendMessage(" §f/Var Box §e<Box> §fAdd §e<Player> <Amount>§7: §f플레이어의 상자 값을 추가합니다.");
            sender.sendMessage(" §f/Var Box §e<Box> §fSubtract §e<Player> <Amount>§7: §f플레이어의 상자 값을 차감합니다.");
            sender.sendMessage(" §f/Var Piece §e<Grade> §fInfo §e<Player>§7: §f플레이어의 조각 값을 확인합니다.");
            sender.sendMessage(" §f/Var Piece §e<Grade> §fSet §e<Player> <Amount>§7: §f플레이어의 조각 값을 설정합니다.");
            sender.sendMessage(" §f/Var Piece §e<Grade> §fAdd §e<Player> <Amount>§7: §f플레이어의 조각 값을 추가합니다.");
            sender.sendMessage(" §f/Var Piece §e<Grade> §fSubtract §e<Player> <Amount>§7: §f플레이어의 조각 값을 차감합니다.");
            sender.sendMessage(" §f/Var Weapon §e<Weapon> §fInfo §e<Player>§7: §f플레이어의 장착된 무기를 확인합니다.");
            sender.sendMessage(" §f/Var Weapon §e<Weapon> §fSet §e<Player> <Weapon>§7: §f플레이어에게 무기를 장착시킵니다.");
        }

        return true;
    }

    public boolean BroadcastRecordLog(CommandSender sender, Command command, String label, String[] args) {
        boolean initRecord = false;

        if (args.length > 0) {
            initRecord = Boolean.parseBoolean(args[0]);
        }

        // 통계
        methods.broadcastRecordLog(); // 통계 전송

        // 통계 초기화
        if (initRecord) {
            Files.setRecordDate(new Date());
            // 진영 승리 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordTeamRatio().entrySet()) {
                String key = entry.getKey();

                Files.setRecordTeamRatio(key, 0);
            }
            // 맵 승리 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordMapRatio().entrySet()) {
                String key = entry.getKey();

                Files.setRecordMapRatio(key, "Human", 0);
                Files.setRecordMapRatio(key, "Zombie", 0);
            }
            // 병과 플레이 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordClassCount().entrySet()) {
                String key = entry.getKey();

                Files.setRecordClassCount(key, 0);
            }
            // 타입 플레이 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordTypeCount().entrySet()) {
                String key = entry.getKey();

                Files.setRecordTypeCount(key, 0);
            }
            // 주 무기 플레이 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordMainWeaponCount().entrySet()) {
                String key = entry.getKey();

                Files.setRecordMainWeaponCount(key, 0);
            }
            // 보조 무기 플레이 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordSubWeaponCount().entrySet()) {
                String key = entry.getKey();

                Files.setRecordSubWeaponCount(key, 0);
            }
            // 근접 무기 플레이 횟수
            for (Map.Entry<String, JsonElement> entry : Files.getRecordMeleeWeaponCount().entrySet()) {
                String key = entry.getKey();

                Files.setRecordMeleeWeaponCount(key, 0);
            }
        }

        return true;
    }


    public boolean Whisper(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) { // 타겟 플레이어가 없을 경우
                sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                return false;
            }

            if (sender == targetPlayer) {
                sender.sendMessage(serverTitle + " §f§l자기 자신에게는 귓속말을 보낼 수 없습니다!");

                return false;
            }

            if (args.length > 1) {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message = message + args[i] + " ";
                }
                if (sender.isOp()) {
                    message = message.replace("&", "§");
                } else {
                    message = message.replace("§", "&");
                }

                String address = "§7§l[ §f" + sender.getName() + " §7§l-> §f" + targetPlayer.getName() + " §7§l] §7";

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (!receiver.equals(sender) && !receiver.equals(targetPlayer) && !receiver.isOp()) {
                        continue;
                    }

                    receiver.sendMessage(address + message);
                    receiver.playSound(receiver.getLocation(), Sound.BLOCK_LAVA_POP, 2.0f, 1.0f);
                }

                Bukkit.getConsoleSender().sendMessage(address + message);

                // 채팅 로그 전송
                JDA jda = variables.getJda();
                jda.getTextChannelById("1099973707058925588").sendMessage("[ " + sender.getName() + " -> " + targetPlayer.getName() + " ] " + message).queue();
            } else {
                sender.sendMessage("");
                sender.sendMessage(" " + serverTitle);
                sender.sendMessage(" §f§l/Whisper §e§l<Player> <Message>§7§l: §f플레이어에게 귓속말을 전송합니다.");
            }
        } else {
            sender.sendMessage("");
            sender.sendMessage(" " + serverTitle);
            sender.sendMessage(" §f§l/Whisper §e§l<Player> <Message>§7§l: §f플레이어에게 귓속말을 전송합니다.");
        }

        return true;
    }

    public boolean Class(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            if (sender.isOp()) { // 플레이어가 오피일 경우

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }

                new ClassGUI((Player) sender, targetPlayer);

            } else { // 플레이어가 오피가 아닐 경우

                new ClassGUI((Player) sender, (OfflinePlayer) sender);

            }

        } else {

            new ClassGUI((Player) sender, (OfflinePlayer) sender);

        }

        return true;
    }

    public boolean Type(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            if (sender.isOp()) { // 플레이어가 오피일 경우

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }

                new TypeGUI((Player) sender, targetPlayer);

            } else { // 플레이어가 오피가 아닐 경우

                new TypeGUI((Player) sender, (OfflinePlayer) sender);

            }

        } else {

            new TypeGUI((Player) sender, (OfflinePlayer) sender);

        }

        return true;
    }

    public boolean Game(CommandSender sender, Command command, String label, String[] args) {
        new GameGUI((Player) sender);

        return true;
    }

    public boolean Shop(CommandSender sender, Command command, String label, String[] args) {
        new ShopGUI((Player) sender, (OfflinePlayer) sender);

        return true;
    }

    public boolean Box(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            if (sender.isOp()) { // 플레이어가 오피일 경우

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }

                new BoxGUI((Player) sender, targetPlayer);

            } else { // 플레이어가 오피가 아닐 경우

                new BoxGUI((Player) sender, (OfflinePlayer) sender);
                
            }

        } else {

            new BoxGUI((Player) sender, (OfflinePlayer) sender);

        }

        return true;
    }

    public boolean Equipment(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            if (sender.isOp()) { // 플레이어가 오피일 경우

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }

                new EquipmentGUI((Player) sender, targetPlayer);

            } else { // 플레이어가 오피가 아닐 경우

                new EquipmentGUI((Player) sender, (OfflinePlayer) sender);
                
            }

        } else {

            new EquipmentGUI((Player) sender, (OfflinePlayer) sender);

        }

        return true;
    }

    public boolean Codi(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            if (sender.isOp()) { // 플레이어가 오피일 경우

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }

                new CodiGUI((Player) sender, targetPlayer);

            } else { // 플레이어가 오피가 아닐 경우

                new CodiGUI((Player) sender, (OfflinePlayer) sender);
                
            }

        } else {

            new CodiGUI((Player) sender, (OfflinePlayer) sender);

        }

        return true;
    }

    public boolean Profile(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            if (sender.isOp()) { // 플레이어가 오피일 경우

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]); // 렉 유발 코드

                boolean isEmpty = true;
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer == targetPlayer) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty) { // 대상 플레이어가 없을 경우
                    sender.sendMessage(serverTitle + " §f§l플레이어 '§6§l" + args[0] + "§f§l'을(를) 찾을 수 없습니다!");

                    return false; // 함수 반환
                }

                new ProfileGUI((Player) sender, targetPlayer);

            } else { // 플레이어가 오피가 아닐 경우

                new ProfileGUI((Player) sender, (OfflinePlayer) sender);

            }

        } else {

            new ProfileGUI((Player) sender, (OfflinePlayer) sender);

        }

        return true;
    }

    public boolean Link(CommandSender sender, Command command, String label, String[] args) {
        new LinkGUI((Player) sender);

        return true;
    }

    public boolean Rule(CommandSender sender, Command command, String label, String[] args) {
        new RuleGUI((Player) sender);

        return true;
    }

    public boolean Tutorial(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
        if (manager.getPlayerList().contains(player)) {
            sender.sendMessage(serverTitle + " §f§l게임에 참여하고 있을 땐, §e§l튜토리얼§f§l로 이동할 수 없습니다!");

            return false;
        }

        if (HuntingGround.playerList.contains(player)) {
            sender.sendMessage(serverTitle + " §f§l사냥터에 계실 땐, §e§l튜토리얼§f§l로 이동할 수 없습니다!");

            return false;
        }

        player.teleport(new Location(Bukkit.getWorld("world"), 1779.5, 27, -441.5, 180, 0)); // 튜토리얼로 이동

        sender.sendMessage(serverTitle + " §e§l튜토리얼§f§l로 이동하였습니다!");

        return true;
    }
}
