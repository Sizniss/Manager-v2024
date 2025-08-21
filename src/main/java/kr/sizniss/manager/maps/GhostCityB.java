package kr.sizniss.manager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static kr.sizniss.manager.Manager.methods;

public class GhostCityB implements Listener {

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, -1440, 39, 441))
                || methods.isOnBlockLocation(player, new Location(world, -1439, 39, 441))
                || methods.isOnBlockLocation(player, new Location(world, -1438, 39, 441))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.7, 0));
        }
        else if (methods.isOnBlockLocation(player, new Location(world, -1480, 38, 454))
                || methods.isOnBlockLocation(player, new Location(world, -1480, 38, 455))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.7, 0));
        }
        else if (methods.isOnBlockLocation(player, new Location(world, -1547, 38, 453))
                || methods.isOnBlockLocation(player, new Location(world, -1548, 38, 453))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.4, 0));
        }
    }

}
