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
import static kr.sizniss.manager.Manager.variables;

public class Tutorial implements Listener {

    Location lobbyLocation = new Location(Bukkit.getWorld("world"), -108.5, 47, -18.5, 0, 0);

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, 1752, 26, -460))) {
            String serverTitle = variables.getServerTitle();

            player.teleport(lobbyLocation);

            player.sendMessage("");
            player.sendMessage(serverTitle + " §f§l서버에 오신 것을 환영합니다!");
            player.sendMessage(serverTitle + " §f§l'§e/게임§f§l' 명령어를 통해 게임에 참여하실 수 있습니다!");
            player.sendTitle("§e/게임", "§f게임에 참여하거나 퇴장하실 수 있습니다!", 5, 100, 5);
        }
    }

}

