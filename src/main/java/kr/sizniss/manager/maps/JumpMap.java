package kr.sizniss.manager.maps;

import kr.sizniss.manager.Manager;
import kr.sizniss.manager.Methods;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static kr.sizniss.manager.Manager.plugin;
import static kr.sizniss.manager.Manager.variables;

public class JumpMap implements Listener {

    Location lobbyLocation = new Location(Bukkit.getWorld("world"), -108.5, 47, -18.5, 0, 0);

    Location jumpMapLocation = new Location(Bukkit.getWorld("world"), -454.5, 73, -26.5, 0, 0);
    Location[] dropZone = { new Location(Bukkit.getWorld("world"), -449,69,-12), new Location(Bukkit.getWorld("world"), -460,71,43) };

    boolean mainButtonCooldown = false;
    boolean[] buttonCooldown = { false, false, false };

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (kr.sizniss.manager.Manager.methods.isOnBlockLocation(player, new Location(world, -455, 72, 41))) {
            String serverTitle = variables.getServerTitle();
            int clearReward = variables.getJumpMapReward();

            player.teleport(jumpMapLocation);

            kr.sizniss.data.Files.addMoney(player, "Gold", clearReward); // 보상 지급

            player.sendMessage(serverTitle + " §e§l점프맵§f§l을 클리어하셨습니다! [ §e§l+" + clearReward + "G §f§l]");
            Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Clear jump map]");
        } else if (kr.sizniss.manager.Manager.methods.isBetweenLocations(player.getLocation(), dropZone[0], dropZone[1])) {
            player.teleport(jumpMapLocation);
        } else if (kr.sizniss.manager.Manager.methods.isOnBlockLocation(player, new Location(world, -455, 72, -31))) {
            player.teleport(lobbyLocation);
        }
    }

    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (event.getClickedBlock().getLocation().equals(new Location(world, -459, 74, -14))) {

            if (mainButtonCooldown) {
                return;
            }
            mainButtonCooldown = true;

            for (int z = -12; z <= 40; z++) {
                for (int x = -460; x <= -450; x++) {
                    if (world.getBlockAt(x, 72, z).getType() == Material.SEA_LANTERN) {
                        Location location = new Location(world, x, 72, z);
                        location.getBlock().setType(Material.AIR);
                        location.getWorld().playSound(location, Sound.BLOCK_STONE_BREAK, 2.0f, 1.0f);
                    }
                }
            }

            Location firstLoc = new Location(world, -455, 72, -13);
            Location lastLoc = new Location(world, -455, 72, 41);

            int num = 0;
            Location nowLoc = firstLoc;
            while (nowLoc.getZ() < lastLoc.getZ() - 5) {
                num++;
                if (num > 1000) {
                    break;
                }

                int ranS = Manager.methods.getRandomInteger(0, 2);
                int ran = Manager.methods.getRandomInteger(4, 6);
                int ranX = Manager.methods.getRandomInteger(1, ran - 2);
                int ranZ = ran - ranX;

                double x;
                double z;
                if (ranS == 1) {
                    x = nowLoc.getX() + ranX;
                    z = nowLoc.getZ() + ranZ;
                } else {
                    x = nowLoc.getX() - ranX;
                    z = nowLoc.getZ() + ranZ;
                }

                if (x >= -450 || x <= -460) {
                    continue;
                }

                nowLoc.setX(x);
                nowLoc.setZ(z);

                nowLoc.getBlock().setType(Material.SEA_LANTERN);
                nowLoc.getWorld().playSound(nowLoc, Sound.BLOCK_STONE_PLACE, 2.0f, 1.0f);
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                mainButtonCooldown = false;
            }, 1200L);

        } else if (event.getClickedBlock().getLocation().equals(new Location(world, -462, 74, 0))) {

            if (buttonCooldown[0]) {
                return;
            }
            buttonCooldown[0] = true;

            Location loc = null;
            for (int z = -1; z <= 1; z++) {
                for (int x = -460; x <= -450; x++) {
                    if (world.getBlockAt(x, 72, z).getType() == Material.SEA_LANTERN) {
                        loc = new Location(world, x, 72, z);

                        loc.getBlock().setType(Material.AIR);
                        loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BREAK, 2.0f, 1.0f);
                    }
                }
            }

            if (loc == null) {
                buttonCooldown[0] = false;

                return;
            }

            final Location location = loc;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                location.getBlock().setType(Material.SEA_LANTERN);
                location.getWorld().playSound(location, Sound.BLOCK_STONE_PLACE, 2.0f, 1.0f);
            }, 20L);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                buttonCooldown[0] = false;
            }, 200L);

        } else if (event.getClickedBlock().getLocation().equals(new Location(world, -462, 74, 15))) {

            if (buttonCooldown[1]) {
                return;
            }
            buttonCooldown[1] = true;

            Location loc = null;
            for (int z = 14; z <= 16; z++) {
                for (int x = -460; x <= -450; x++) {
                    if (world.getBlockAt(x, 72, z).getType() == Material.SEA_LANTERN) {
                        loc = new Location(world, x, 72, z);

                        loc.getBlock().setType(Material.AIR);
                        loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BREAK, 2.0f, 1.0f);
                    }
                }
            }

            if (loc == null) {
                buttonCooldown[1] = false;

                return;
            }

            final Location location = loc;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                location.getBlock().setType(Material.SEA_LANTERN);
                location.getWorld().playSound(location, Sound.BLOCK_STONE_PLACE, 2.0f, 1.0f);
            }, 20L);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                buttonCooldown[1] = false;
            }, 400L);

        } else if (event.getClickedBlock().getLocation().equals(new Location(world, -462, 74, 30))) {

            if (buttonCooldown[2]) {
                return;
            }
            buttonCooldown[2] = true;

            Location loc = null;
            for (int z = 29; z <= 31; z++) {
                for (int x = -460; x <= -450; x++) {
                    if (world.getBlockAt(x, 72, z).getType() == Material.SEA_LANTERN) {
                        loc = new Location(world, x, 72, z);

                        loc.getBlock().setType(Material.AIR);
                        loc.getWorld().playSound(loc, Sound.BLOCK_STONE_BREAK, 2.0f, 1.0f);
                    }
                }
            }

            if (loc == null) {
                buttonCooldown[2] = false;

                return;
            }

            final Location location = loc;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                location.getBlock().setType(Material.SEA_LANTERN);
                location.getWorld().playSound(location, Sound.BLOCK_STONE_PLACE, 2.0f, 1.0f);
            }, 20L);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                buttonCooldown[2] = false;
            }, 600L);

        }
    }

}
