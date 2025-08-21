package kr.sizniss.manager.maps;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import dselon.selonzombiesurvival.Manager;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import dselon.selonzombiesurvival.customevents.TimerEvent;
import kr.sizniss.manager.Files;
import kr.sizniss.manager.guis.huntinggroundgui.MainWeaponGUI;
import kr.sizniss.manager.guis.huntinggroundgui.MeleeWeaponGUI;
import kr.sizniss.manager.guis.huntinggroundgui.SubWeaponGUI;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

import static kr.sizniss.manager.Manager.*;

public class HuntingGround implements Listener {

    private String serverTitle = Files.getServerTitle();

    World world = Bukkit.getWorld("world");

    Location lobbyLocation = new Location(world, -108.5, 47, -18.5, 0, 0);

    Location weaponButtonLocation = new Location(world, -750, 47, 969);
    Location[] ammoBlockLocation = {
            new Location(world, -799, 46, 950),
            new Location(world, -789, 45, 911)
    };
    Location[] mapLocation = {
            new Location(world, -745, 68, 986),
            new Location(world, -844, 41, 887)
    };
    Location[] spawnLocation = {
            new Location(world, -750.5, 46, 891.5),
            new Location(world, -795.5, 44, 893.5),
            new Location(world, -836.5, 45, 981.5),
            new Location(world, -807.5, 46, 984.5)
    };

    public static boolean isStarted = false;
    public static int task;
    public static ArrayList<LivingEntity> monsterList = new ArrayList<LivingEntity>();
    public static HashMap<LivingEntity, Player> finalAttackerMap = new HashMap<LivingEntity, Player>();

    public static ArrayList<Player> playerList = new ArrayList<Player>();
    public static HashMap<Player, Integer> weaponKindMap = new HashMap<Player, Integer>();
    public static HashMap<Player, String> weaponNameMap = new HashMap<Player, String>();
    public static HashMap<Player, Integer> killCountMap = new HashMap<Player, Integer>();
    public static HashMap<Player, ArrayList<ItemStack>> itemMap = new HashMap<Player, ArrayList<ItemStack>>();


    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();

        if (event.getClickedBlock().getLocation().equals(weaponButtonLocation)) {
            switch (weaponKindMap.get(player)) {
                case 0:
                    new MainWeaponGUI(player, player);
                    break;
                case 1:
                    new SubWeaponGUI(player, player);
                    break;
                case 2:
                    new MeleeWeaponGUI(player, player);
                    break;
            }
        }
        for (int i = 0; i < ammoBlockLocation.length; i++) {
            if (event.getClickedBlock().getLocation().equals(ammoBlockLocation[i])) {
                if (weaponNameMap.containsKey(player)) {
                    event.getClickedBlock().setType(Material.AIR);

                    // 탄약 지급
                    String weaponName = weaponNameMap.get(player);
                    int mainWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(player, weaponName);

                    int ammoAmount = methods.getAmmoAmount(weaponName, mainWeaponLevel, 0.5);
                    methods.addAmmoAmount(player, ammoAmount);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        if (!isStarted) {
                            return;
                        }

                        event.getClickedBlock().setType(Material.BEACON);
                    }, (60 / playerList.size()) * 20L);
                }
            }
        }
    }

    @EventHandler
    private void EntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ZOMBIE
                && event.getEntityType() != EntityType.SKELETON
                && event.getEntityType() != EntityType.CREEPER
                && event.getEntityType() != EntityType.WITHER_SKELETON) {
            return;
        }

        if (monsterList.contains(event.getEntity())) {
            monsterList.remove(event.getEntity());
        }

        Player player = null;
        if (event.getEntity().getKiller() != null) {
            player = event.getEntity().getKiller();
        }
        else if (finalAttackerMap.containsKey(event.getEntity())) {
            player = finalAttackerMap.get(event.getEntity());
        }

        if (player == null) {
            return;
        }

        if (!playerList.contains(player)) {
            return;
        }

        int goldReward = variables.getHuntingGroundGoldReward();
        int emeraldReward = variables.getHuntingGroundEmeraldReward();
        int diamondReward = variables.getHuntingGroundDiamondReward();

        int killCount = killCountMap.get(player);
        killCount += 1;
        killCountMap.put(player, killCount);
        if (killCount != 0) {

            if (killCount % 5 == 0) {
                kr.sizniss.data.Files.addMoney(player, "Gold", goldReward); // 보상 지급

                player.sendMessage(serverTitle + " §e§l몬스터§f§l를 5마리 처치하셨습니다! [ §e§l+" + goldReward + "G §f§l]");
                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Kill 5 monsters]");
            }

            if (killCount % 100 == 0) {
                kr.sizniss.data.Files.addMoney(player, "Emerald", emeraldReward); // 보상 지급

                player.sendMessage(serverTitle + " §e§l몬스터§f§l를 100마리 처치하셨습니다! [ §a§l+" + emeraldReward + "E §f§l]");
                Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Kill 100 monsters]");
            }

        }
        if (event.getEntityType() == EntityType.WITHER_SKELETON) {
            kr.sizniss.data.Files.addMoney(player, "Diamond", diamondReward); // 보상 지급

            player.sendMessage(serverTitle + " §e§l보스 몬스터§f§l를 처치하셨습니다! [ §b§l+" + diamondReward + "D §f§l]");
            Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Kill a boss monsters]");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        /*
        if (event.isCancelled()) {
            return;
        }

        Player attacker = event.getPlayer();

        if (!(event.getVictim() instanceof LivingEntity)) {
            return;
        }
        LivingEntity victim = (LivingEntity) event.getVictim();

        if (playerList.contains(attacker)) {
            finalAttackerMap.put(victim, attacker);

            double damage = event.getDamage();
            event.setCancelled(true);

            CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
            CraftLivingEntity craftAttacker = (CraftLivingEntity) attacker;
            EntityLiving victimHandle = craftVictim.getHandle();

            victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
            victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (damage - 1));
        }
         */

        Player attacker = event.getPlayer();

        if (!(event.getVictim() instanceof LivingEntity)) {
            return;
        }
        LivingEntity victim = (LivingEntity) event.getVictim();

        if (playerList.contains(attacker)) {
            finalAttackerMap.put(victim, attacker);
        }
    }

    @EventHandler
    private void EntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        if (monsterList.contains(event.getTarget())) {
            event.setTarget(playerList.get(methods.getRandomInteger(0, playerList.size())));
        }
    }



    // 플레이어 데이터 백업 함수
    private void backupPlayerData(Player player) {
        itemMap.put(player, new ArrayList<ItemStack>()); // 인벤토리 백업
        for (int i = 0; i < 41; i++) {
            itemMap.get(player).add(player.getInventory().getItem(i));
        }
    }

    // 플레이어 데이터 복구 함수
    private void restorePlayerData(Player player) {
        for (int i = 0; i < 41; i++) { // 인벤토리 복구
            player.getInventory().setItem(i, itemMap.get(player).get(i));
        }
    }

    // 사냥터 게임 시작
    private void GameStart() {
        isStarted = true;

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (!isStarted) {
                return;
            }

            if (monsterList.size() >= 5000) {
                return;
            }

            for (int j = 0; j < playerList.size(); j++) {
                for (int i = 0; i < spawnLocation.length; i++) {
                    LivingEntity entity;
                    Zombie zombie;
                    Skeleton skeleton;
                    Creeper creeper;
                    WitherSkeleton witherSkeleton;
                    int randomNumber = methods.getRandomInteger(0, 1000);
                    if (0 <= randomNumber && randomNumber < 650) {
                        zombie = (Zombie) world.spawnEntity(spawnLocation[i], EntityType.ZOMBIE);
                        entity = zombie;

                        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                        entity.setHealth(20);
                        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.34);
                    } else if (650 <= randomNumber && randomNumber < 850) {
                        skeleton = (Skeleton) world.spawnEntity(spawnLocation[i], EntityType.SKELETON);
                        entity = skeleton;

                        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
                        entity.setHealth(10);
                        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.34);
                    } else if (850 <= randomNumber && randomNumber < 999) {
                        creeper = (Creeper) world.spawnEntity(spawnLocation[i], EntityType.CREEPER);
                        entity = creeper;

                        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                        entity.setHealth(20);
                        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.34);
                    } else {
                        witherSkeleton = (WitherSkeleton) world.spawnEntity(spawnLocation[i], EntityType.WITHER_SKELETON);
                        entity = witherSkeleton;

                        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2000);
                        entity.setHealth(2000);
                        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.34);
                        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                    }
                    ((Creature) entity).setTarget(playerList.get(methods.getRandomInteger(0, playerList.size())));
                    entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(500);
                    entity.setMaximumNoDamageTicks(0); // 피해 지연 시간 제거

                    monsterList.add(entity);
                }
            }
        }, 40L, 40L);
        finalAttackerMap = new HashMap<LivingEntity, Player>();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!isStarted) {
                return;
            }

            for (int i = 0; i < ammoBlockLocation.length; i++) {
                ammoBlockLocation[i].getBlock().setType(Material.BEACON);
            }
        }, (60 / playerList.size()) * 20L);
    }

    // 사냥터 게임 종료
    private void GameStop() {
        isStarted = false;

        Bukkit.getScheduler().cancelTask(task);

        // 소환된 모든 엔티티 제거
        for (LivingEntity entity : monsterList) {
            entity.remove();
        }
        monsterList = new ArrayList<LivingEntity>();
        finalAttackerMap = new HashMap<LivingEntity, Player>();

        for (int i = 0; i < ammoBlockLocation.length; i++) {
            ammoBlockLocation[i].getBlock().setType(Material.AIR);
        }
    }



    @EventHandler
    private void PluginEnableEvent(PluginEnableEvent event) {
        if (event.getPlugin().getName().equals("Manager")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (methods.isBetweenLocations(player.getLocation(), mapLocation[0], mapLocation[1])) {
                    playerList.add(player);
                    weaponKindMap.put(player, 0);
                    killCountMap.put(player, 0);

                    backupPlayerData(player); // 플레이어 데이터 백업
                    for (int i = 0; i < 9; i++) {
                        player.getInventory().setItem(i, new ItemStack(Material.AIR));
                    }

                    if (!isStarted) {
                        GameStart();
                    }
                }
            }
        }
    }

    @EventHandler
    private void PluginDisableEvent(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("Manager")) {
            for (Player player : playerList) {
                restorePlayerData(player); // 플레이어 데이터 복구
            }

            playerList = new ArrayList<Player>();
            weaponKindMap = new HashMap<Player, Integer>();
            weaponNameMap = new HashMap<Player, String>();
            killCountMap = new HashMap<Player, Integer>();

            if (playerList.isEmpty()) { // 사냥터 안이 비었을 경우
                GameStop();
            }
        }
    }



    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        /*
        if (methods.isOnBlockLocation(player, new Location(world, -750, 45, 958))) {
            player.teleport(lobbyLocation);

            playerList.remove(player);
            weaponKindMap.remove(player);
            weaponNameMap.remove(player);
            killCountMap.remove(player);

            restorePlayerData(player); // 플레이어 데이터 복구

            if (playerList.isEmpty()) { // 사냥터 안이 비었을 경우
                GameStop();
            }
        }
         */
        if (methods.isOnBlockLocation(player, new Location(world, -750, 45, 963))) {
            if (playerList.contains(player)) {
                return;
            }

            playerList.add(player);
            weaponKindMap.put(player, 0);
            killCountMap.put(player, 0);

            backupPlayerData(player); // 플레이어 데이터 백업
            for (int i = 0; i < 9; i++) {
                player.getInventory().setItem(i, new ItemStack(Material.AIR));
            }

            if (!isStarted) {
                GameStart();
            }
        }
    }

    @EventHandler
    private void TimerEvent(TimerEvent event) {
        int timer = event.getTimer();
        ArrayList<Player> selonZSPlayerList = SelonZombieSurvival.manager.getPlayerList();

        if (SelonZombieSurvival.manager.getGameProgress() == Manager.GameProgress.WAITING
                && timer == 0
                && selonZSPlayerList.size() >= 2) {

            ArrayList<Player> playerListClone = new ArrayList<>(playerList);
            for (Player player : playerListClone) {
                if (selonZSPlayerList.contains(player)) {
                    playerList.remove(player);
                    weaponKindMap.remove(player);
                    weaponNameMap.remove(player);
                    killCountMap.remove(player);

                    restorePlayerData(player); // 플레이어 데이터 복구

                    if (playerList.isEmpty()) { // 사냥터 안이 비었을 경우
                        GameStop();
                    }
                }
            }

        }
    }
    
    @EventHandler
    private void PlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        
        if (playerList.contains(player)) {
            playerList.remove(player);
            weaponKindMap.remove(player);
            weaponNameMap.remove(player);
            killCountMap.remove(player);

            restorePlayerData(player); // 플레이어 데이터 복구

            if (playerList.isEmpty()) { // 사냥터 안이 비었을 경우
                GameStop();
            }
        }
    }

    @EventHandler
    private void PlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (playerList.contains(player)) {
            playerList.remove(player);
            weaponKindMap.remove(player);
            weaponNameMap.remove(player);
            killCountMap.remove(player);

            restorePlayerData(player); // 플레이어 데이터 복구

            if (playerList.isEmpty()) { // 사냥터 안이 비었을 경우
                GameStop();
            }
        }
    }


    @EventHandler
    private void EntityCombustEvent(EntityCombustEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        event.setCancelled(true);
    }



    @EventHandler
    private void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (playerList.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void EntityPickupItemEvent(EntityPickupItemEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();

        if (playerList.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void InventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked().getType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getWhoClicked();

        if (playerList.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void InventoryDragEvent(InventoryDragEvent event) {
        if (event.getWhoClicked().getType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getWhoClicked();

        if (playerList.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (playerList.contains(player)) {
            event.setCancelled(true);
        }
    }

}
