package kr.sizniss.manager.weapons;

import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.Manager;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import kr.sizniss.manager.abilities.Reversal;
import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import kr.sizniss.manager.maps.HuntingGround;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static kr.sizniss.manager.Manager.methods;
import static kr.sizniss.manager.Manager.plugin;

public class HyeolJeokJa implements Listener {

    private double damage = 140;
    double chance = 1.0;
    private double speed = 1.5; // 속도
    private double radius = 0.5; // 범위
    private int range = 160; // 사정거리
    private int repeatDelay = 3; // 공격 반복 지연 시간
    private int repeat = 10; // 공격 반복 횟수

    // 이벤트 메소드
    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("혈적자")) {
            int level = 0;
            if (weaponTitle.contains("(+1)")) {
                level = 1;
            }
            final int weaponLevel = level;

            Player attacker = event.getPlayer(); // 공격자
            CraftWorld world = (CraftWorld)attacker.getWorld();
            WorldServer worldServer = world.getHandle();

            Location location = attacker.getEyeLocation().clone();
            location.setY(location.getY() - 0.7);

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

            Methods.CustomArmorStand customArmorStand = methods.new CustomArmorStand(worldServer,location.getX(),location.getY(),location.getZ());
            ArmorStand armorStand = world.addEntity(customArmorStand, CreatureSpawnEvent.SpawnReason.CUSTOM);
            CraftArmorStand craftstand = (CraftArmorStand)armorStand;
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setMarker(false);
            armorStand.setBasePlate(false);
            armorStand.setInvulnerable(true);
            armorStand.teleport(location);

            Vector direction = location.getDirection().clone();
            Vector multipledDirection = direction.multiply(speed);
            double directionX = Math.abs(direction.normalize().multiply(radius).getX());
            double directionY = Math.abs(direction.normalize().multiply(radius).getY());
            double directionZ = Math.abs(direction.normalize().multiply(radius).getZ());
            Location spawnLocation = armorStand.getLocation();
            for (Player playingPlayer : SelonZombieSurvival.manager.getPlayerList()) { // 플레이어 목록
                CraftPlayer craftPlayer = (CraftPlayer) playingPlayer;
                craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(armorStand.getEntityId()));
            }

            new BukkitRunnable() {

                boolean started = false;
                boolean pass = true;
                int warn_guillotine = 0;
                int valid = 200; // 수정 금지
                int ticks = 0; // 수정 금지
                Location movingLocation;
                LivingEntity victim = attacker; // 피해자

                @Override
                public void run() {
                    if (valid > 0) {
                        valid--;

                        movingLocation = armorStand.getLocation();

                        if (!started) {

                            if (movingLocation.distance(spawnLocation) >= range) {
                                valid = 0;
                            }

                            if (warn_guillotine > 0) {
                                valid = 0;
                            }

                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    pass = true;
                                    boolean passThrough = true;
                                    boolean collider = false;

                                    AxisAlignedBB box = craftstand.getHandle().getBoundingBox().g(0.008);
                                    BlockPosition.PooledBlockPosition pos1 = BlockPosition.PooledBlockPosition.d(box.a + 0.001, box.b + 0.002, box.c + 0.001);
                                    BlockPosition.PooledBlockPosition pos2 = BlockPosition.PooledBlockPosition.d(box.d - 0.001, box.e - 0.001, box.f - 0.001);
                                    BlockPosition.PooledBlockPosition pos4 = BlockPosition.PooledBlockPosition.d(box.a, box.b + 0.748, box.c);
                                    BlockPosition.PooledBlockPosition pos5 = BlockPosition.PooledBlockPosition.d(box.d, box.e, box.f);
                                    BlockPosition.PooledBlockPosition pos3 = BlockPosition.PooledBlockPosition.s();

                                    for (int i = pos1.getX(); i <= pos2.getX(); i++) {
                                        for (int j = pos1.getY(); j <= pos2.getY(); j++) {
                                            for (int k = pos1.getZ(); k <= pos2.getZ(); k++) {
                                                pos3.f(i, j, k);

                                                if (worldServer.getType(pos3).d(worldServer,pos3) != null) {
                                                    AxisAlignedBB blockbox = Objects.requireNonNull(worldServer.getType(pos3).d(worldServer, pos3)).d(i,j,k);

                                                    if (blockbox.e-box.b > 0 && blockbox.e-box.b < 0.02) {
                                                        warn_guillotine += 2;

                                                        break;
                                                    }

                                                    if (blockbox.a-box.d < 0 || blockbox.c-box.f < 0 || blockbox.b-box.e < 0){
                                                        collider = true;

                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    for (int i = pos4.getX(); i <= pos5.getX(); i++) {
                                        for (int j = pos4.getY(); j <= pos5.getY(); j++) {
                                            for (int k = pos4.getZ(); k <= pos5.getZ(); k++) {
                                                pos3.f(i, j, k);

                                                if (worldServer.getType(pos3).d(worldServer,pos3) != null) {
                                                    AxisAlignedBB blockbox = Objects.requireNonNull(worldServer.getType(pos3).d(worldServer, pos3)).d(i, j, k);

                                                    if (blockbox.a - box.d < 0 || blockbox.c - box.f < 0 || blockbox.b - box.e < 0) {
                                                        passThrough = false;
                                                        warn_guillotine += 1;

                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (!collider && passThrough) {
                                        BlockPosition specialLocation = new BlockPosition(armorStand.getLocation().getX(),armorStand.getLocation().getY()-1,armorStand.getLocation().getZ());

                                        if (worldServer.getType(specialLocation).d(worldServer,specialLocation) != null) {
                                            collider = true;
                                        }
                                    }

                                    if (collider && passThrough) {

                                        warn_guillotine = 0;

                                        if (movingLocation.distance(spawnLocation) >= 1.5) {
                                            List<Block> blocks = armorStand.getLineOfSight(null, ((int) Math.round(speed)));
                                            for (Block b : blocks) {
                                                if (b.getType().isSolid()) {
                                                    warn_guillotine += 1;

                                                    break;
                                                }
                                            }
                                        }

                                        armorStand.teleport(armorStand.getLocation().add(direction.clone().multiply(speed)));
                                    }
                                }
                            }.runTaskAsynchronously(plugin);

                            customArmorStand.motX = multipledDirection.getX() * 3;
                            customArmorStand.motY = multipledDirection.getY() * 3;
                            customArmorStand.motZ = multipledDirection.getZ() * 3;
                            customArmorStand.velocityChanged = true;

                            if (customArmorStand.inBlock()) {
                                warn_guillotine += 1;
                            }

                            if (armorStand.getEyeLocation().add(direction).getBlock().getType().isSolid()) {
                                warn_guillotine += 1;
                            }

                            armorStand.getWorld().spawnParticle(Particle.SPELL, armorStand.getEyeLocation(), 50, 0.2, 0.1, 0.2, 0);
                            if (movingLocation.distance(spawnLocation) >= speed / 1.5) {
                                List<Entity> caught = armorStand.getNearbyEntities(radius + directionX, radius * directionY, radius + directionZ);
                                caught.removeIf(damaged::contains);
                                caught.removeIf(p -> !(p instanceof LivingEntity));

                                if (!caught.isEmpty()) {
                                    victim = (LivingEntity)caught.get(caught.size() - 1);
                                }
                            }
                        }

                        if (victim != attacker && !started) {
                            started = true;
                            valid = 0;

                            new BukkitRunnable() {
                                double finalDamage = 0;

                                @Override
                                public void run() {
                                    ticks += 1;
                                    if (ticks > repeat * repeatDelay) {
                                        cancel();
                                    }

                                    victim.getWorld().spawnParticle(Particle.SPELL, victim.getEyeLocation(), 50, 0.2, 0.1, 0.2, 0);
                                    if (ticks % repeatDelay == 0) {
                                        CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
                                        CraftLivingEntity craftAttacker = (CraftLivingEntity) attacker;
                                        EntityLiving victimHandle = craftVictim.getHandle();

                                        Location vector = attacker.getLocation().subtract(victim.getLocation());
                                        victimHandle.a(victimHandle, 0.4F, vector.getX() != 0 ? vector.getX() : 0.1, vector.getY() != 0 ? vector.getY() : 0.1);
                                        victimHandle.velocityChanged = true;

                                        double finalDamage = damage;
                                        if (weaponLevel == 1) {
                                            finalDamage += damage / 4.0;
                                        }
                                        finalDamage /= repeat;

                                        // CustomDamageEntityEvent 이벤트 호출
                                        CustomDamageEntityEvent event = new CustomDamageEntityEvent(attacker, victim, finalDamage);
                                        Bukkit.getPluginManager().callEvent(event);

                                        if (!event.isCancelled()) {
                                            World world = attacker.getWorld();
                                            world.playSound(attacker.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 1.0f);

                                            if (weaponLevel == 1) {
                                                double random = Math.random();
                                                if (random < chance * 0.01) {
                                                    world = victim.getWorld();
                                                    world.strikeLightningEffect(victim.getLocation());
                                                    event.setDamage(1000);
                                                }
                                            }

                                            victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
                                            victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (event.getDamage() - 1));
                                        }
                                    }
                                }
                            }.runTaskTimer(plugin, 0L,1L);
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
