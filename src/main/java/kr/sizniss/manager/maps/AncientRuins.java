package kr.sizniss.manager.maps;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import dselon.selonzombiesurvival.customevents.RoundEndEvent;
import dselon.selonzombiesurvival.customevents.RoundStartEvent;
import dselon.selonzombiesurvival.customevents.TeamChangeHostEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.variables;

public class AncientRuins implements Listener {

    String mapName = "고대 유적";
    int plusVariationTime = 5;

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