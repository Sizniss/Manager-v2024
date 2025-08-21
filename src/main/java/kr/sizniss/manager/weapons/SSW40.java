package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponHitBlockEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAreaEffectCloud;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

import static kr.sizniss.manager.Manager.plugin;

public class SSW40 implements Listener {

    double damage = 12;
    int duration = 40;
    int delay = 10;

    @EventHandler
    private void WeaponHitBlockEvent(WeaponHitBlockEvent event) {
        Player attacker = event.getPlayer();

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("SSW40")) {
            int level = 0;
            if (weaponTitle.contains("(+1)")) {
                level = 1;
            }
            final int weaponLevel = level;

            Location location = event.getAirBlock().getLocation();
            if (event.getBlock() != null) {
                location.setY(location.getY() + 0.5);
            }

            AreaEffectCloud lingering = attacker.getWorld().spawn(location, AreaEffectCloud.class);
            lingering.setParticle(Particle.SUSPENDED);
            lingering.setDuration(duration); // 가스 지속 시간
            lingering.setRadius(Float.parseFloat("3.0")); // 가스 반경
            lingering.setRadiusOnUse(0); // 가스 크기 감소 시작까지 걸리는 시간
            lingering.setRadiusPerTick(Float.parseFloat("0.01")); // 시간별 가스 크기 감소율

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
                    lingering.getWorld().spawnParticle(Particle.SMOKE_LARGE, lingering.getLocation(), 5, posX / 4.0, (posY + height) / 4.0, posZ / 4.0, 0); // 가스 파티클
                    if (weaponLevel == 1) {
                        lingering.getWorld().spawnParticle(Particle.LAVA, lingering.getLocation(), 2, posX / 4.0, (posY + height) / 4.0, posZ / 4.0, 0); // 가스 파티클
                    }
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

                            CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
                            CraftLivingEntity craftAttacker = (CraftLivingEntity) attacker;
                            EntityLiving victimHandle = craftVictim.getHandle();

                            double finalDamage = damage;
                            if (weaponLevel == 1) {
                                finalDamage += (damage / 4.0);
                            }

                            // CustomDamageEntityEvent 이벤트 호출
                            CustomDamageEntityEvent customDamageEntityEvent = new CustomDamageEntityEvent(attacker, victim, finalDamage);
                            Bukkit.getPluginManager().callEvent(customDamageEntityEvent);

                            if (!customDamageEntityEvent.isCancelled()) {
                                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
                                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (customDamageEntityEvent.getDamage() - 1));

                                if (weaponLevel == 1) {
                                    victim.setFireTicks(300 * 20);
                                }
                            }
                        }
                    }
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }

}
