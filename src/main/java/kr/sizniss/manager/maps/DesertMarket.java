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

public class DesertMarket implements Listener {

    String serverTitle = Files.getServerTitle();

    String mapName = "사막 시장";
    World world = Bukkit.getWorld("world");
    boolean[] isUsedButtons = { false, false, false, false, false, false };
    Location[] buttonLocations = {
            new Location(world, 229, 61, 1383),
            new Location(world, 254, 61, 1409),
            new Location(world, 281, 61, 1399),
            new Location(world, 344, 61, 1402),
            new Location(world, 304, 64, 1443),
            new Location(world, 230, 61, 1466)
    };
    Location[] mapLocations = {
            new Location(world, 162.5, 60, 1367.5, 0, 0),
            new Location(world, 201.5, 60, 1393.5, 270, 0),
            new Location(world, 201.5, 60, 1393.5, 270, 0),
            new Location(world, 254.5, 60, 1412.5, 270, 0),
            new Location(world, 350.5, 63, 1377.5, 0, 0),
            new Location(world, 285.5, 60, 1435.5, 270, 0),
            new Location(world, 289.5, 60, 1466.5, 90, 0)
    };
    Location[] escapeLocations = {
            new Location(world, 235, 60, 1469),
            new Location(world, 224.5, 63, 1464)
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
                // 마을 입구
                blockTime = 15;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l마을 입구 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l마을 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = 236;
                    for (y = 60; y <= 63; y++) {
                        for (z = 1383; z <= 1387; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, 236, 62, 1385), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                break;

            case 2:

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    int x, y, z;
                    x = 250;
                    for (y = 60; y <= 62; y++) {
                        for (z = 1410; z <= 1413; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.BIRCH_FENCE);
                        }
                    }
                    x = 247;
                    z = 1416;
                    for (y = 60; y <= 61; y++) {
                        Location location = new Location(world, x, y, z);
                        location.getBlock().setType(Material.AIR);
                    }

                    world.playSound(new Location(world, 250, 61, 1412), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생
                    world.playSound(new Location(world, 247, 61, 1416), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, 0);

                break;

            case 3:
                // 하수로 입구
                blockTime = 25;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l하수로 입구 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l하수로 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = 279;
                    z = 1414;
                    for (y = 52; y <= 53; y++) {
                        Location location = new Location(world, x, y, z);
                        location.getBlock().setType(Material.AIR);
                    }
                    z = 1408;
                    for (x = 282; x <= 284; x++) {
                        for (y = 60; y <= 62; y++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.BIRCH_FENCE);
                        }
                    }

                    world.playSound(new Location(world, 279, 53, 1414), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생
                    world.playSound(new Location(world, 283, 61, 1408), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                // 골목길 입구
                breakTime = 15;
                for (int i = 0; i < breakTime; i++) {
                    final int timer = i;
                    if ((breakTime - timer) % 5 == 0 || (breakTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l골목길 입구 §f§l개방까지 §7§l" + (breakTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, blockTime * 20 + i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l골목길 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x= 285;
                    for (y = 60; y <= 62; y++) {
                        for (z = 1410; z <= 1414; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, 285, 61, 1412), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생
                }, blockTime * 20 + breakTime * 20);

                break;
            case 4:
                // 건초 더미
                blockTime = 25;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l건초 더미 §f§l제거까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l건초 더미 §f§l제거가 완료되었습니다.");
                    }

                    int x, y, z;
                    z = 1404;
                    for (x = 348; x <= 351; x++) {
                        for (y = 60; y <= 63; y++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, 350, 62, 1404), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                break;

            case 5:
                // 사다리 문
                blockTime = 15;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l사다리 문 §f§l개방까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l사다리 문 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = 315;
                    for (y = 63; y <= 64; y++) {
                        for (z = 1442; z <= 1444; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                    z = 1441;
                    for (x = 304; x <= 305; x++) {
                        for (y = 63; y <= 64; y++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.BIRCH_FENCE);
                        }
                    }

                    world.playSound(new Location(world, 315, 64, 1443), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생
                    world.playSound(new Location(world, 305, 64, 1441), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생

                    // 스폰 위치 재설정
                    ArrayList<Location> locations = new ArrayList<Location>();
                    locations.add(mapLocations[number]);
                    manager.setMapLocation(locations);

                }, blockTime * 20);

                // 빨래터 입구
                breakTime = 10;
                for (int i = 0; i < breakTime; i++) {
                    final int timer = i;
                    if ((breakTime - timer) % 5 == 0 || (breakTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l빨래터 입구 §f§l개방까지 §7§l" + (breakTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, blockTime * 20 + i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l빨래터 입구 §f§l개방이 완료되었습니다.");
                    }

                    int x, y, z;
                    x = 323;
                    for (y = 60; y <= 63; y++) {
                        for (z = 1435; z <= 1439; z++) {
                            Location location = new Location(world, x, y, z);
                            location.getBlock().setType(Material.AIR);
                        }
                    }

                    world.playSound(new Location(world, 323, 62, 1437), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 3.0f, 1.0f); // 사운드 재생
                }, blockTime * 20 + breakTime * 20);

                break;

            case 6:

                // 스폰 위치 재설정
                ArrayList<Location> locations = new ArrayList<Location>();
                locations.add(mapLocations[number]);
                manager.setMapLocation(locations);

                // 기차 시동
                blockTime = 25;
                for (int i = 0; i < blockTime; i++) {
                    final int timer = i;
                    if ((blockTime - timer) % 5 == 0 || (blockTime - timer) <= 5) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (manager.getGameProgress() != Manager.GameProgress.GAME) {
                                return;
                            } // 게임 중이 아닐 경우, 함수 반환

                            for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                                targetPlayer.sendMessage(serverTitle + " §e§l기차 §f§l시동까지 §7§l" + (blockTime - timer) + "초 §f§l남았습니다.");
                            }
                        }, i * 20);
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() != Manager.GameProgress.GAME) { return; } // 게임 중이 아닐 경우, 함수 반환

                    for (Player targetPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                        targetPlayer.sendMessage(serverTitle + " §e§l기차 §f§l시동이 완료되었습니다.");
                    }

                    world.playSound(buttonLocations[5], Sound.ENTITY_MINECART_INSIDE, 3.0f, 2.0f); // 사운드 재생

                    manager.setHumanWin(true);// 인간 승리
                }, blockTime * 20);

                break;
        }
    }

    // 상호작용 초기화
    private void initInteraction() {
        int x, y, z;

        // 1
        x = 236;
        for (y = 60; y <= 63; y++) {
            for (z = 1383; z <= 1387; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.BIRCH_FENCE);
            }
        }

        // 2
        x = 250;
        for (y = 60; y <= 62; y++) {
            for (z = 1410; z <= 1413; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }
        x = 247;
        z = 1416;
        for (y = 60; y <= 61; y++) {
            Location location = new Location(world, x, y, z);
            location.getBlock().setType(Material.IRON_FENCE);
        }

        // 3
        x = 279;
        z = 1414;
        for (y = 52; y <= 53; y++) {
            Location location = new Location(world, x, y, z);
            location.getBlock().setType(Material.IRON_FENCE);
        }
        z = 1408;
        for (x = 282; x <= 284; x++) {
            for (y = 60; y <= 62; y++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }
        x= 285;
        for (y = 60; y <= 62; y++) {
            for (z = 1410; z <= 1414; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.BIRCH_FENCE);
            }
        }

        // 4
        z = 1404;
        for (x = 348; x <= 351; x++) {
            for (y = 60; y <= 63; y++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.HAY_BLOCK);
            }
        }

        // 5
        x = 315;
        for (y = 63; y <= 64; y++) {
            for (z = 1442; z <= 1444; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.BIRCH_FENCE);
            }
        }
        z = 1441;
        for (x = 304; x <= 305; x++) {
            for (y = 63; y <= 64; y++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.AIR);
            }
        }
        x = 323;
        for (y = 60; y <= 63; y++) {
            for (z = 1435; z <= 1439; z++) {
                Location location = new Location(world, x, y, z);
                location.getBlock().setType(Material.BIRCH_FENCE);
            }
        }
    }

}

