package kr.sizniss.manager.maps;

import kr.sizniss.manager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static kr.sizniss.manager.Manager.methods;

public class Assault implements Listener {

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, 1017, 53, -7))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.5, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, 1047, 53, 27))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.5, 0));
        }
    }

}
