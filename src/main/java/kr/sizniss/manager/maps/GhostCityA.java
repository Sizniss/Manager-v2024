package kr.sizniss.manager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static kr.sizniss.manager.Manager.methods;

public class GhostCityA implements Listener {

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, -1450, 38, 485))
                || methods.isOnBlockLocation(player, new Location(world, -1451, 38, 485))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.7, 0));
        }
        else if (methods.isOnBlockLocation(player, new Location(world, -1475, 38, 505))
                || methods.isOnBlockLocation(player, new Location(world, -1476, 38, 505))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.5, 0));
        }
        else if (methods.isOnBlockLocation(player, new Location(world, -1478, 38, 477))
                || methods.isOnBlockLocation(player, new Location(world, -1478, 38, 478))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.8, 0));
        }
    }

}
