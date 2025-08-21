package kr.sizniss.manager.items;

import com.shampaggon.crackshot.events.WeaponHitBlockEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Manager;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAreaEffectCloud;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.plugin;

public class Lime implements Listener {

    int duration = 60;
    int delay = 1;

    @EventHandler
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.equals("끈끈이")) {

            Player player = event.getPlayer();
            int slot = player.getInventory().getHeldItemSlot();
            ItemStack item = player.getInventory().getItem(slot);

            // 아이템 소모
            Manager.methods.consume(player, item, slot, 1);
        }
    }

    @EventHandler
    private void WeaponHitBlockEvent(WeaponHitBlockEvent event) {
        Player attacker = event.getPlayer();

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("끈끈이")) {
            Location location = event.getAirBlock().getLocation();
            if (event.getBlock() != null) {
                location.setY(location.getY() + 0.5);
            }

            AreaEffectCloud lingering = attacker.getWorld().spawn(location, AreaEffectCloud.class);
            lingering.setParticle(Particle.SUSPENDED);
            lingering.setDuration(duration); // 가스 지속 시간
            lingering.setRadius(Float.parseFloat("3")); // 가스 반경
            lingering.setRadiusOnUse(0); // 가스 크기 감소 시작까지 걸리는 시간
            lingering.setRadiusPerTick(Float.parseFloat("0")); // 시간별 가스 크기 감소율

            new BukkitRunnable() {
                int tick = 0;

                @Override
                public void run() {
                    tick += 1;

                    if (tick > duration) {
                        return;
                    }

                    AxisAlignedBB box = ((CraftAreaEffectCloud) lingering).getHandle().getBoundingBox();
                    double posX = lingering.getRadius() * 2;
                    double posY = Math.abs(Math.abs(box.b) - Math.abs(box.e));
                    double posZ = lingering.getRadius() * 2;
                    double height = 1.5;
                    lingering.getWorld().spawnParticle(Particle.CLOUD, lingering.getLocation(), 10, posX / 4.0, (posY + height) / 4.0, posZ / 4.0, 0); // 가스 파티클
                    List<Entity> entityList = lingering.getNearbyEntities(posX / 32.0, (posY + height) / 2.0, posZ / 32.0);
                    List<Entity> entityListClone = new ArrayList<Entity>(entityList);

                    for (Entity entity : entityListClone) {
                        if (!(entity instanceof LivingEntity)) {
                            entityList.remove(entity);
                        }
                    }

                    if (!entityList.isEmpty() && tick % delay == 0) {
                        for (Entity entity : entityList) {
                            LivingEntity victim = (LivingEntity) entity;

                            // 팀 확인
                            Team victimTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(victim.getName());
                            Team attackerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(attacker.getName());
                            if (victimTeam != null && attackerTeam != null) {
                                if (victimTeam.equals(attackerTeam)) {
                                    if (!victimTeam.allowFriendlyFire()) {
                                        continue;
                                    }
                                }
                            }

                            // 공격자가 좀비일 경우
                            if (SelonZombieSurvival.manager.getZombieList().contains(attacker)) {
                                continue;
                            }

                            methods.potionApply(victim, PotionEffectType.SLOW, 1, 7); // 구속 효과 부여
                            methods.potionApply(victim, PotionEffectType.JUMP, 1, 250); // 점프 강화 효과 부여

                        }
                    }
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }

    /*
    @EventHandler
    private void WeaponHitBlockEvent(WeaponHitBlockEvent event) {
        Player attacker = event.getPlayer();

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("끈끈이")) {
            Location location = event.getAirBlock().getLocation();
            if (event.getBlock() != null) {
                location.setY(location.getY() + 0.5);
            }

            AreaEffectCloud lingering = attacker.getWorld().spawn(location, AreaEffectCloud.class);
            lingering.setMetadata("Shooter", new FixedMetadataValue(plugin, attacker.getName()));
            lingering.setMetadata("WeaponTitle", new FixedMetadataValue(plugin, "끈끈이"));
            lingering.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 1, false, false), true);
            lingering.setParticle(Particle.CLOUD); // 가스 파티클
            lingering.setColor(Color.WHITE); // 가스 색깔
            lingering.setDuration(80); // 가스 지속 시간
            lingering.setRadius(Float.parseFloat("5")); // 가스 반경
            lingering.setRadiusOnUse(0); // 가스 크기 감소 시작까지 걸리는 시간
            lingering.setRadiusPerTick(Float.parseFloat("0")); // 시간별 가스 크기 감소율
            lingering.setReapplicationDelay(1); // 효과 재적용 지연 시간
            lingering.setWaitTime(1); // 효과 적용 지연 시간
        }
    }

    @EventHandler
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager().getType() != EntityType.AREA_EFFECT_CLOUD) {
            return;
        }

        if (event.getDamager().getMetadata("WeaponTitle") == null) {
            return;
        }
        String weaponTitle = event.getDamager().getMetadata("WeaponTitle").get(0).asString();
        if (weaponTitle.contains("끈끈이")) {

            event.setCancelled(true);

            // 공격자 및 피해자
            if (event.getDamager().getMetadata("Shooter") == null) {
                return;
            }
            Player attacker = Bukkit.getPlayer(event.getDamager().getMetadata("Shooter").get(0).asString());
            if (event.getEntityType() != EntityType.PLAYER) {
                return;
            }
            Player victim = (Player) event.getEntity();

            // 팀 확인
            Team victimTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(victim.getName());
            Team attackerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(attacker.getName());
            if (victimTeam != null && attackerTeam != null) {
                if (victimTeam.equals(attackerTeam)) {
                    if (!victimTeam.allowFriendlyFire()) {
                        return;
                    }
                }
            }

            // 공격자가 좀비일 경우
            if (SelonZombieSurvival.manager.getZombieList().contains(attacker)) {
                return;
            }

            methods.potionApply(victim, PotionEffectType.SLOW, duration, level); // 구속 효과 부여
            methods.potionApply(victim, PotionEffectType.JUMP, duration, 250); // 점프 강화 효과 부여

        }

    }
     */

    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if ((player.getPotionEffect(PotionEffectType.SLOW) != null && player.getPotionEffect(PotionEffectType.SLOW).getAmplifier() >= 7 - 1)
                && (player.getPotionEffect(PotionEffectType.JUMP) != null && player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() >= 250 - 1)) {

            Location fromLocation = event.getFrom();
            Location toLocation = event.getTo();

            if (!fromLocation.equals(toLocation)) {
                event.setCancelled(true);
            }

        }
    }

}

