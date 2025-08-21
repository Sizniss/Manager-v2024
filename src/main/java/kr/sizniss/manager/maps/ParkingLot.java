package kr.sizniss.manager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static kr.sizniss.manager.Manager.methods;

public class ParkingLot implements Listener {

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, 1807, 35, 481))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        }
    }

}