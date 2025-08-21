package kr.sizniss.manager.maps;

import dselon.selonzombiesurvival.Manager;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import dselon.selonzombiesurvival.customevents.RoundEndEvent;
import kr.sizniss.manager.Files;
import kr.sizniss.manager.Methods;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

import static kr.sizniss.manager.Manager.plugin;

public class Hospital implements Listener {

    String serverTitle = Files.getServerTitle();

    String mapName = "병원";
    World world = Bukkit.getWorld("world");
    boolean[] isUsedButtons = { false, false, false, false, false, false, false, false, false, false, false };
    Location[] buttonLocations = {
            new Location(world, -1023, 33, 433),
            new Location(world, -1065, 38, 436),
            new Location(world, -1071, 38, 454),
            new Location(world, -1101, 38, 491),
            new Location(world, -1108, 38, 480),
            new Location(world, -1130, 43, 452),
            new Location(world, -1135, 43, 436),
            new Location(world, -1157, 38, 444),
            new Location(world, -1134, 33, 461),
            new Location(world, -1142, 33, 439),
            new Location(world, -1152, 34, 431)
    };
    Location[] mapLocations = {
            new Location(world, -1111.5, 32, 434.5, 270, 0),
            new Location(world, -1046.5, 32, 434.5, 270, 0),
            new Location(world, -1046.5, 32, 434.5, 270, 0),
            new Location(world, -1082.5, 37, 434.5, 0, 0),
            new Location(world, -1082.5, 37, 434.5, 0, 0),
            new Location(world, -1095.5, 37, 496.5, 90, 0),
            new Location(world, -1095.5, 37, 496.5, 90, 0),
            new Location(world, -1112.5, 42, 434.5, 90, 0),
            new Location(world, -1112.5, 42, 434.5, 90, 0),
            new Location(world, -1138.5, 32, 471.5, 270, 0),
            new Location(world, -1138.5, 32, 471.5, 270, 0),
            new Location(world, -1144.5, 32, 453.5, 180, 0)
    };
    Location[] liftLocations = {
            new Location(world, -1021, 35, 433),
            new Location(world, -1025, 32, 436)
    };
    Location[] escapeLocations = {
            new Location(world, -1154, 33, 436),
            new Location(world, -1150, 34, 428)
    };


    // 플레이어 이동 이벤트
    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Manager manager = SelonZombieSurvival.manager;
        if (manager.getGameProgress() != Manager.GameProgress.GAME
                || !manager.getPlayerList().contains(player)
                || !manager.getMapName().equals(mapName)) {
            return; // 함수 반환
        }

        if (manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우

            // 탈출 지점
            if (kr.sizniss.manager.Manager.methods.isBetweenLocations(player.getLocation(), escapeLocations[0], escapeLocations[1])) {
                if (!manager.getLivingHumanList().contains(player)) {
                    manager.getLivingHumanList().add(player); // 플레이어를 생존한 플레이어 목록에 추가
                }
            } else if (!kr.sizniss.manager.Manager.methods.isBetweenLocations(player.getLocation(), escapeLocations[0], escapeLocations[1])) {
                if (manager.getLivingHumanList().contains(player)) {
                    manager.getLivingHumanList().remove(player); // 플레이어를 생존한 플레이어 목록에서 제거
                }
            }

        }
    }

    // 플레이어 상호작용 이벤트
    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();

        Manager manager = SelonZombieSurvival.manager;
        if (manager.getGameProgress() != Manager.GameProgress.GAME
                || !manager.getPlayerList().contains(player)
                || !manager.getMapName().equals(mapName)) {
            return; // 함수 반환
        }

        for (int i = 0; i < isUsedButtons.length; i++) {
            if (event.getClickedBlock().getLocation().equals(buttonLocations[i])) {
                if (isUsedButtons[i]) { // 이미 사용한 버튼일 경우
                    return; // 함수 반환
                }
                isUsedButtons[i] = true;

                useInteraction(i + 1, player); // 상호작용
            }
        }
    }

    // 라운드 종료 이벤트
    @EventHandler
    private void RoundEndEvent(RoundEndEvent event) {
        String mapName = SelonZombieSurvival.manager.getMapName();
        if (mapName.equals(this.mapName)) {

            for (int i = 0; i < isUsedButtons.length; i++) {
                isUsedButtons[i] = false;
            }

            initInteraction(); // 상호작용 초기화
        }
    }

    // 상호작용 사용
    private void useInteraction(int number, Player player) {
        Manager manager = SelonZombieSurvival.manager;
        if (manager.getZombieList().contains(player) && number == buttonLocations.length) { // 플레이어가 좀비이고, 마지막 버튼 번호가 아닐 경우
            manager.setZombieWin(true); // 좀비 승리
            return; // 함수 반환
        }

        ArrayList<Player> playerList = SelonZombieSurvival.manager.getPlayerList();
        int blockTime;
        int breakTime;

        switch (number) {
            case 1:
                // 승강기
                blockTime = 20;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l승강기 §f§l작동까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l승강기 §f§l작동이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = -1025;
                    for (y = 32; y <= 33; y++) {
                        for (z = 434; z <= 435; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.GLASS);
                        }
                    }

                    world.playSound(new Location(world, -1025, 33, 435), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                // 텔레포트 딜레이
                int teleportDelay = 1;
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        if (kr.sizniss.manager.Manager.methods.isBetweenLocations(targetPlayer.getLocation(), liftLocations[0], liftLocations[1])) {
                            Location location = targetPlayer.getLocation();
                            targetPlayer.teleport(new Location(location.getWorld(), location.getX(), location.getY() + 5, location.getZ(), location.getYaw(), location.getPitch()));
                        }
                    }

                }, blockTime * 20 + teleportDelay * 20);

                // 승강기 문 개방 딜레이
                int openDelay = 1;
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    x = -1025;
                    for (y = 37; y <= 38; y++) {
                        for (z = 434; z <= 435; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, -1025, 38, 435), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                }, blockTime * 20 + teleportDelay * 20 + openDelay * 20);

                // 계단 철문
                breakTime = 5;
                for (int i = 0; i < breakTime; i++) {
                    final int timer = i;
                    if ((breakTime - timer) % 5 == 0 || (breakTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l계단 철문 §f§l개방까지 §7§l" + (breakTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, blockTime * 20 + teleportDelay * 20 + openDelay * 20 + i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l계단 철문 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = -1033;
                    y = 29;
                    for (z = 445; z <= 446; z++) {
                        Location location = new Location(world, x, y, z);
                        location.getBlock().setType(Material.REDSTONE_BLOCK);
                    }

                }, blockTime * 20 + teleportDelay * 20 + openDelay * 20 + breakTime * 20);

                break;

            case 2:

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    x = -1061;
                    for (y = 37; y <= 39; y++) {
                        for (z = 432; z <= 436; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.IRON_FENCE);
                        }
                    }

                    world.playSound(new Location(world, -1061, 38, 434), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, 0);

                break;

            case 3:
                // 약품 보관실 입구
                blockTime = 15;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l약품 보관실 입구 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l약품 보관실 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    z = 456;
                    for (x = -1069; x <= -1067; x++) {
                        for (y = 37; y <= 39; y++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, -1068, 38, 456), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                break;
            case 4:

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    x = -1098;
                    for (y = 37; y <= 39; y++) {
                        for (z = 491; z <= 492; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.IRON_FENCE);
                        }
                    }

                    world.playSound(new Location(world, -1098, 38, 492), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, 0);

                break;

            case 5:
                // 진료실 입구
                blockTime = 15;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l진료실 입구 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l진료실 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = -1105;
                    for (y = 37; y <= 39; y++) {
                        for (z = 476; z <= 478; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, -1105, 38, 477), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                break;

            case 6:

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    x = -1132;
                    for (y = 42; y <= 44; y++) {
                        for (z = 451; z <= 455; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.IRON_FENCE);
                        }
                    }

                    world.playSound(new Location(world, -1132, 43, 453), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, 0);

                break;

            case 7:
                // 비상 계단 입구
                blockTime = 10;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l비상 계단 입구 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l비상 계단 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    z = 442;
                    for (x = -1136; x <= -1135; x++) {
                        for (y = 42; y <= 43; y++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, -1135, 43, 442), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                break;

            case 8:

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    x = -1153;
                    for (y = 37; y <= 39; y++) {
                        for (z = 442; z <= 445; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.IRON_FENCE);
                        }
                    }

                    world.playSound(new Location(world, -1153, 38, 444), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, 0);

                break;

            case 9:
                // 가스 배관실 입구
                blockTime = 10;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l가스 배관실 입구 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l가스 배관실 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = -1120;
                    for (y = 32; y <= 34; y++) {
                        for (z = 462; z <= 463; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, -1120, 33, 463), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                break;

            case 10:

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    z = 441;
                    for (x = -1145; x <= -1142; x++) {
                        for (y = 32; y <= 34; y++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.IRON_FENCE);
                        }
                    }

                    world.playSound(new Location(world, -1143, 33, 441), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, 0);

                break;

            case 11:

                // 스폰 위치 재설정
                ArrayList<Location> locations = new ArrayList<Location>();
                locations.add(mapLocations[number]);
                manager.setMapLocation(locations);

                // 구급차 시동
                blockTime = 25;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l구급차 §f§l시동까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l구급차 §f§l시동이 완료되었습니다.");
                    }

                    world.playSound(buttonLocations[10], Sound.ENTITY_MINECART_INSIDE, 3.0f, 2.0f); // 사운드 재생

                    manager.setHumanWin(true);// 인간 승리
                }, blockTime * 20);

                break;
        }
    }

    // 상호작용 초기화
    private void initInteraction() {
        int x, y, z;

        // 1
        x = -1025;
        for (y = 32; y <= 33; y++) {
            for (z = 434; z <= 435; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }
        x = -1033;
        y = 29;
        for (z = 445; z <= 446; z++) {
            Location location = new Location(world, x, y, z);
            location.getBlock().setType(Material.AIR);
        }
        x = -1025;
        for (y = 37; y <= 38; y++) {
            for (z = 434; z <= 435; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.GLASS);
            }
        }

        // 2
        x = -1061;
        for (y = 37; y <= 39; y++) {
            for (z = 432; z <= 436; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }

        // 3
        z = 456;
        for (x = -1069; x <= -1067; x++) {
            for (y = 37; y <= 39; y++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.IRON_FENCE);
            }
        }

        // 4
        x = -1098;
        for (y = 37; y <= 39; y++) {
            for (z = 491; z <= 492; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }

        // 5
        x = -1105;
        for (y = 37; y <= 39; y++) {
            for (z = 476; z <= 478; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.IRON_FENCE);
            }
        }

        // 6
        x = -1132;
        for (y = 42; y <= 44; y++) {
            for (z = 451; z <= 455; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }

        // 7
        z = 442;
        for (x = -1136; x <= -1135; x++) {
            for (y = 42; y <= 43; y++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.IRON_FENCE);
            }
        }

        // 8
        x = -1153;
        for (y = 37; y <= 39; y++) {
            for (z = 442; z <= 445; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }

        // 9
        x = -1120;
        for (y = 32; y <= 34; y++) {
            for (z = 462; z <= 463; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.IRON_FENCE);
            }
        }

        // 10
        z = 441;
        for (x = -1145; x <= -1142; x++) {
            for (y = 32; y <= 34; y++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }
    }

}
