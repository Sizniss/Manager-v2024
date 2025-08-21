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

public class VeniceB implements Listener {

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, -138, 43, -509))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -121, 43, -509))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -88, 43, -509))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -71, 43, -509))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -138, 43, -486))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -121, 43, -486))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -88, 43, -486))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -71, 43, -486))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -76, 41, -535))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.5, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -133, 41, -535))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.5, 0));
        }
    }

}
