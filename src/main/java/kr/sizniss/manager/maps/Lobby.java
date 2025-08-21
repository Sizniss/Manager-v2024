package kr.sizniss.manager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static kr.sizniss.manager.Manager.methods;

public class Lobby implements Listener {

    Location jumpMapLocation = new Location(Bukkit.getWorld("world"), -454.5, 73, -26.5, 0, 0);
    Location huntingGroundLocation = new Location(Bukkit.getWorld("world"), -749.5, 46, 963.5, 0, 0);

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, -118, 46, -9))) {
            player.teleport(jumpMapLocation);
        }
        else if (methods.isOnBlockLocation(player, new Location(world, -131, 46, 17))) {
            player.teleport(huntingGroundLocation);
        }
    }

}
