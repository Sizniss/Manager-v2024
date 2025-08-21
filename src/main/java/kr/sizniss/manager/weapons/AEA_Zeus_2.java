package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.Manager;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import kr.sizniss.manager.maps.HuntingGround;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.plugin;

public class AEA_Zeus_2 implements Listener {

    double speed = 1; // 속도 (0~1.25)
    int range = 20; // 사정거리
    int pullingDuration = 7; // 지속 시간
    double radius = 0.75; // 범위 (0.75~)
    int knockback = 100; // 넉백
    double damage = 80; // 공격력

    double duration = 3;
    int level = 3;

    // 이벤트 메소드
    @EventHandler
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("AEA_Zeus_2")) {
            int level = 0;
            if (weaponTitle.contains("(+1)")) {
                level = 1;
            }
            final int weaponLevel = level;

            Player attacker = event.getPlayer();
            Location location = attacker.getLocation();

            ArrayList<Entity> damaged;
            if (SelonZombieSurvival.manager.getPlayerList().contains(attacker)
                    && (SelonZombieSurvival.manager.getGameProgress() == Manager.GameProgress.READY
                    || SelonZombieSurvival.manager.getGameProgress() == Manager.GameProgress.GAME)) {
                damaged = new ArrayList<>(SelonZombieSurvival.manager.getHumanList()); // 인간 목록
            }
            else if (HuntingGround.playerList.contains(attacker)) {
                damaged = new ArrayList<>(HuntingGround.playerList); // 인간 목록
            }
            else {
                damaged = new ArrayList<Entity>();
            }
            damaged.add(attacker);

            location.setY(location.getY() + 1);
            ArmorStand armorStand = attacker.getWorld().spawn(location.add(attacker.getEyeLocation().getDirection().multiply(1)), ArmorStand.class);
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setMarker(true);
            armorStand.setBasePlate(false);
            armorStand.setInvulnerable(true);
            armorStand.setGravity(false);
            armorStand.getLocation().setDirection(attacker.getLocation().getDirection());
            armorStand.setMetadata("valid", new FixedMetadataValue(plugin, 200));

            Location spawnLocation = armorStand.getLocation();
            double directionX = Math.abs(armorStand.getLocation().getDirection().normalize().multiply(radius * 2 + speed).getX());
            double directionZ = Math.abs(armorStand.getLocation().getDirection().normalize().multiply(radius * 2 + speed).getZ());

            new BukkitRunnable() {
                boolean started = false;
                @Override
                public void run() {

                    int duration = AEA_Zeus_2.this.pullingDuration;
                    Location movingLocation = armorStand.getLocation();

                    if (armorStand.getMetadata("valid").get(0).asInt() > 0) {
                        armorStand.setMetadata("valid", new FixedMetadataValue(plugin, armorStand.getMetadata("valid").get(0).asInt()-1));

                        if (movingLocation.distance(spawnLocation) >= range) {
                            armorStand.remove();
                            cancel();
                        }

                        if (armorStand.getLocation().getBlock().getType().isSolid()) {
                            armorStand.remove();
                            cancel();
                        }

                        Location location = armorStand.getLocation();
                        location.add(armorStand.getLocation().getDirection().multiply(-speed));

                        Collection<Entity> caught = armorStand.getWorld().getNearbyEntities(location,radius + directionX,radius * speed * 1.2,radius + directionZ);
                        caught.removeIf(damaged::contains);
                        caught.removeIf(p -> !(p instanceof LivingEntity));
                        caught.removeIf(p -> p.getType() == EntityType.ARMOR_STAND);

                        armorStand.teleport(movingLocation.add(armorStand.getLocation().getDirection().multiply(speed)));

                        if (weaponLevel == 1) {
                            armorStand.getWorld().spawnParticle(Particle.FALLING_DUST, armorStand.getLocation(), (int) (30 * radius),radius / 2.0,radius / 2.0,radius / 2.0,10, new MaterialData(Material.ICE));
                        }

                        armorStand.getWorld().spawnParticle(Particle.CLOUD, armorStand.getLocation(),  (int) (30 * radius), radius / 2.0, radius / 2.0, radius / 2.0, 0);
                        movingLocation = armorStand.getLocation();

                        if (!caught.isEmpty() && !started) {

                            started = true;

                            if (movingLocation.distance(spawnLocation) >= duration) {
                                duration = duration / 2;
                            } else {
                                duration = (int) (duration-(movingLocation.distance(spawnLocation)/2));
                            }

                            int finalDuration = duration;
                            new BukkitRunnable() {
                                int ticks = 0; // 수정 금지
                                final Location newLocation = armorStand.getLocation();
                                HashSet<Entity> damaged1 = new HashSet<>();
                                @Override
                                public void run() {

                                    ticks++;
                                    if (ticks > finalDuration) {
                                        armorStand.setMetadata("valid", new FixedMetadataValue(plugin, 0));
                                        cancel();
                                    }

                                    Location movingLocation = armorStand.getLocation();
                                    if (newLocation.distance(movingLocation) >= knockback) {
                                        armorStand.setMetadata("valid", new FixedMetadataValue(plugin, 0));
                                        cancel();
                                    }

                                    Location location = armorStand.getLocation();
                                    location.add(armorStand.getLocation().getDirection().multiply(-speed));

                                    Collection<Entity> caught = armorStand.getWorld().getNearbyEntities(location,radius + directionX, radius * speed * 1.2, radius + directionZ);
                                    caught.removeIf(damaged::contains);
                                    caught.removeIf(p -> !(p instanceof LivingEntity));
                                    caught.removeIf(p -> p.getType() == EntityType.ARMOR_STAND);

                                    for (Entity entity : caught) {
                                        if (damaged1.add(entity)) {
                                            LivingEntity livingEntity = (LivingEntity) entity;

                                            CraftLivingEntity craftVictim = (CraftLivingEntity) livingEntity;
                                            CraftLivingEntity craftAttacker = (CraftLivingEntity) attacker;
                                            EntityLiving victimHandle = craftVictim.getHandle();

                                            double finalDamage = damage;
                                            if (weaponLevel == 1) {
                                                finalDamage += (damage / 4.0);
                                            }

                                            // CustomDamageEntityEvent 이벤트 호출
                                            CustomDamageEntityEvent event = new CustomDamageEntityEvent(attacker, livingEntity, finalDamage);
                                            Bukkit.getPluginManager().callEvent(event);

                                            if (!event.isCancelled()) {
                                                World world = attacker.getWorld();
                                                world.playSound(attacker.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 1.0f);

                                                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
                                                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (event.getDamage() - 1));

                                                if (weaponLevel == 1) {
                                                    methods.potionApply(livingEntity, PotionEffectType.SLOW, AEA_Zeus_2.this.duration, AEA_Zeus_2.this.level);
                                                }
                                            }
                                        }
                                        entity.setVelocity(armorStand.getLocation().getDirection().multiply(speed));
                                    }

                                }
                            }.runTaskTimer(plugin,0,1);
                        }
                    } else {
                        armorStand.remove();
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 1);

        }
    }

}
