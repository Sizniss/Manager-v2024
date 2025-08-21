package kr.sizniss.manager.abilities;

import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static kr.sizniss.manager.Manager.*;
import static kr.sizniss.manager.Manager.plugin;

public class Pulling implements Listener {

    public int cooldown = 25;
    public int amount = 1;
    private double speed = 1; // 속도
    private double knockback = 0.3; // 넉백
    private double radius = 0.25; // 범위
    private int range = 160; // 사정거리
    private double duration = 0.75; // 지속 시간


    private void useAbility(Player player, ItemStack item, int slot) {
        if (item.getType() == Material.BARRIER) { // 아이템 타입이 방벽일 경우
            return; // 함수 반환
        }

        final String type = variables.getSelectedType().get(player);

        // 능력 발동
        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.5f, 2.0f);

        shootPulling(player);

        // 재사용 대기 시간 진행
        int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
        int minusCooldown = cooldownStat * 2;
        int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
        int plusAmount = amountStat;
        methods.cooldown(player, item, slot, cooldown - minusCooldown, amount + plusAmount);
    }

    private void shootPulling(Player attacker) {
        CraftWorld world = (CraftWorld)attacker.getWorld();
        WorldServer worldServer = world.getHandle();

        Location location = attacker.getEyeLocation().clone();
        location.setY(location.getY() - 0.7);

        ArrayList<Entity> damaged = new ArrayList<Entity>(SelonZombieSurvival.manager.getZombieList()); // 좀비 목록
        damaged.add(attacker);
        Methods.CustomArmorStand customArmorStand = methods.new CustomArmorStand(worldServer, location.getX(), location.getY(), location.getZ());
        ArmorStand armorStand = world.addEntity(customArmorStand, CreatureSpawnEvent.SpawnReason.CUSTOM);
        CraftArmorStand craftArmorStand = (CraftArmorStand)armorStand;
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
                                boolean passThrough = true;
                                boolean collider = false;

                                AxisAlignedBB box = craftArmorStand.getHandle().getBoundingBox().g(0.008);
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

                        armorStand.getWorld().spawnParticle(Particle.SMOKE_NORMAL, armorStand.getEyeLocation(), 25, 0.2, 0.1, 0.2, 0);
                        customArmorStand.motX = multipledDirection.getX()*3;
                        customArmorStand.motY = multipledDirection.getY()*3;
                        customArmorStand.motZ = multipledDirection.getZ()*3;
                        customArmorStand.velocityChanged = true;

                        if (customArmorStand.inBlock()) {
                            warn_guillotine += 1;
                        }

                        if (armorStand.getEyeLocation().add(direction).getBlock().getType().isSolid()) {
                            warn_guillotine += 1;
                        }

                        if (movingLocation.distance(spawnLocation) >= speed / 1.5) {
                            List<Entity> caught = armorStand.getNearbyEntities(radius + directionX, radius * directionY, radius + directionZ);
                            caught.removeIf(damaged::contains);
                            caught.removeIf(p -> p.getType() != EntityType.PLAYER);
                            if (!caught.isEmpty()) {
                                victim = (LivingEntity)caught.get(caught.size() - 1);
                            }
                        }
                    }

                    if (victim != attacker && !started) {

                        started = true;
                        valid = 0;

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                ticks += 1;
                                if (ticks > (int)(duration * 20)) {
                                    cancel();
                                }

                                victim.getWorld().spawnParticle(Particle.SMOKE_NORMAL, victim.getEyeLocation(), 50, 0.2, 0.1, 0.2, 0);
                                Location victimloc = victim.getLocation().clone();
                                Location targetLoc = attacker.getLocation().clone();
                                if (victim.isOnGround()) {
                                    targetLoc.setY(victimloc.getY() + 1);
                                }
                                victimloc.setDirection(targetLoc.toVector().subtract(victimloc.toVector()));
                                victim.setVelocity(victimloc.getDirection().multiply(knockback));
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


    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 함수 반환
        }

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return; // 함수 반환
        }

        Player player = event.getPlayer();
        ItemStack item = null;
        int slot = player.getInventory().getHeldItemSlot();

        if (event.hasItem()) {
            item = event.getItem();
        } else if (player.getItemInHand() != null) {
            item = player.getItemInHand();
        }

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName().contains("풀링")) {
            useAbility(player, item, slot);
        }
    }


    @EventHandler
    private void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = null;
        int slot;

        for (int i = 0; i < 9; i++) {
            Inventory inventory = player.getInventory();
            ItemStack indexItem = inventory.getItem(i);

            if (indexItem == null || indexItem.getItemMeta().getDisplayName() == null) {
                continue;
            }

            if (indexItem.getItemMeta().getDisplayName().contains("풀링")) {
                item = indexItem;
                break;
            }
        }

        if (item == null) { // 아이템이 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta() == null) { // 아이템 메타 정보가 없을 경우
            return; // 함수 반환
        }

        if (item.getItemMeta().getDisplayName() == null) { // 아이템 이름이 없을 경우
            return; // 함수 반환
        }

        slot = player.getInventory().first(item);

        if (item.getItemMeta().getDisplayName().contains("풀링")) {
            useAbility(player, item, slot);
        }
    }

}
