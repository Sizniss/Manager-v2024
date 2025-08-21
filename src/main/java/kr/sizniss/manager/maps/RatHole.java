package kr.sizniss.manager.maps;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import dselon.selonzombiesurvival.customevents.RoundEndEvent;
import dselon.selonzombiesurvival.customevents.RoundStartEvent;
import dselon.selonzombiesurvival.customevents.TeamChangeHostEvent;
import kr.sizniss.manager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.variables;

public class RatHole implements Listener {

    String mapName = "쥐구멍";
    int plusVariationTime = 5;

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");

        if (methods.isOnBlockLocation(player, new Location(world, -493, 48, 499))) {
            methods.jump(player, new Location(player.getWorld(), 0, 3, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -428, 48, 499))) {
            methods.jump(player, new Location(player.getWorld(), 0, 2.2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -474, 48, 449))) {
            methods.jump(player, new Location(player.getWorld(), 0, 2, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -423, 58, 441))) {
            methods.jump(player, new Location(player.getWorld(), 0, 1.8, 0));
        } else if (methods.isOnBlockLocation(player, new Location(world, -485, 69, 434))) {
            double x = player.getLocation().getDirection().getX(); x *= 3.5;
            double y = player.getLocation().getDirection().getY(); y *= 3.5;
            double z = player.getLocation().getDirection().getZ(); z *= 3.5;

            methods.jump(player, new Location(player.getWorld(), x, y, z));
        }
    }

    // 라운드 시작 이벤트
    @EventHandler(priority = EventPriority.HIGH)
    private void RoundStartEvent(RoundStartEvent event) {
        String mapName = SelonZombieSurvival.manager.getMapName();
        if (mapName.equals(this.mapName)) {

            // 변이 시간 조정
            variables.setVariationTime(variables.getVariationTime() + plusVariationTime);
            variables.setVariationDelay(new HashMap<Player, Integer>());
            for (Player player : SelonZombieSurvival.manager.getPlayerList()) {
                variables.getVariationDelay().put(player, variables.getVariationTime());
            }

        }
    }

    // 라운드 종료 이벤트
    @EventHandler(priority = EventPriority.LOW)
    private void RoundEndEvent(RoundEndEvent event) {
        String mapName = SelonZombieSurvival.manager.getMapName();
        if (mapName.equals(this.mapName)) {

            // 변이 시간 초기화
            variables.setVariationTime(variables.getVariationTime() - plusVariationTime);

        }
    }

    @EventHandler
    private void TeamChangeHostEvent(TeamChangeHostEvent event) {
        String mapName = SelonZombieSurvival.manager.getMapName();
        if (mapName.equals(this.mapName)) {
            Player host = event.getHost();

            int mapLocationSize = SelonZombieSurvival.manager.getMapLocation().size();
            Location mapLocation = SelonZombieSurvival.manager.getMapLocation().get(methods.getRandomInteger(0, mapLocationSize));
            host.teleport(mapLocation);
        }
    }

}
