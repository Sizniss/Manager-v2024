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

public class MK_13_EGLM implements Listener {

    double damage = 5;
    int duration = 40;
    int delay = 10;

    @EventHandler
    private void WeaponHitBlockEvent(WeaponHitBlockEvent event) {
        Player attacker = event.getPlayer();

        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("MK_13_EGLM")) {
            Location location = event.getAirBlock().getLocation();
            if (event.getBlock() != null) {
                location.setY(location.getY() + 0.5);
            }

            AreaEffectCloud lingering = attacker.getWorld().spawn(location, AreaEffectCloud.class);
            lingering.setParticle(Particle.SUSPENDED);
            lingering.setDuration(duration); // 가스 지속 시간
            lingering.setRadius(Float.parseFloat("2.5")); // 가스 반경
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
                    lingering.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, lingering.getLocation(), 5, posX / 4.0, (posY + height) / 4.0, posZ / 4.0, 0); // 가스 파티클
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

                            // CustomDamageEntityEvent 이벤트 호출
                            CustomDamageEntityEvent customDamageEntityEvent = new CustomDamageEntityEvent(attacker, victim, damage);
                            Bukkit.getPluginManager().callEvent(customDamageEntityEvent);

                            if (!customDamageEntityEvent.isCancelled()) {
                                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
                                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (customDamageEntityEvent.getDamage() - 1));
                            }
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
        if (weaponTitle.contains("MK_13_EGLM")) {
            Location location = event.getAirBlock().getLocation();
            if (event.getBlock() != null) {
                location.setY(location.getY() + 0.5);
            }

            AreaEffectCloud lingering = attacker.getWorld().spawn(location, AreaEffectCloud.class);
            lingering.setMetadata("Shooter", new FixedMetadataValue(plugin, attacker.getName()));
            lingering.setMetadata("WeaponTitle", new FixedMetadataValue(plugin, "MK_13_EGLM"));
            lingering.addCustomEffect(new PotionEffect(PotionEffectType.LUCK, 1, 1, false, false), true);
            // if (HuntingGround.playerList.contains(attacker)) {
            //     lingering.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1, false, false), true);
            // }
            lingering.setParticle(Particle.VILLAGER_HAPPY); // 가스 파티클
            lingering.setColor(Color.GREEN); // 가스 색깔
            lingering.setDuration(50); // 가스 지속 시간
            lingering.setRadius(Float.parseFloat("3")); // 가스 반경
            lingering.setRadiusOnUse(0); // 가스 크기 감소 시작까지 걸리는 시간
            lingering.setRadiusPerTick(Float.parseFloat("0.01")); // 시간별 가스 크기 감소율
            lingering.setReapplicationDelay(10); // 효과 재적용 지연 시간
            lingering.setWaitTime(10); // 효과 적용 지연 시간
        }
    }

    @EventHandler
    private void AreaEffectCloudApplyEvent(AreaEffectCloudApplyEvent event) {
        AreaEffectCloud areaEffectCloud = event.getEntity();

        if (areaEffectCloud.getMetadata("WeaponTitle") == null) {
            return;
        }
        String weaponTitle = areaEffectCloud.getMetadata("WeaponTitle").get(0).asString();

        if (weaponTitle.contains("MK_13_EGLM")) {

            // 공격자
            if (areaEffectCloud.getMetadata("Shooter") == null) {
                return;
            }
            Player attacker = Bukkit.getPlayer(areaEffectCloud.getMetadata("Shooter").get(0).asString());

            for (LivingEntity victim : event.getAffectedEntities()) {

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

                CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
                CraftPlayer craftAttacker = (CraftPlayer) attacker;
                EntityLiving victimHandle = craftVictim.getHandle();

                // CustomDamageEntityEvent 이벤트 호출
                CustomDamageEntityEvent customDamageEntityEvent = new CustomDamageEntityEvent(attacker, victim, damage);
                Bukkit.getPluginManager().callEvent(customDamageEntityEvent);

                if (!customDamageEntityEvent.isCancelled()) {
                    victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
                    victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (customDamageEntityEvent.getDamage() - 1));
                }

            }

        }
    }
     */

    /*
    @EventHandler
    private void ProjectileHitEvent(ProjectileHitEvent event) {

        if (event.getEntity().getType() != EntityType.SPLASH_POTION) { // 엔티티 타입이 투척용 물약이 아닐 경우
            return;
        }

        if (!(event.getEntity().getShooter() instanceof Player)) { // 발사자가 플레이어가 아닐 경우
            return;
        }
        Player attacker = (Player) event.getEntity().getShooter();

        int slot = -1;
        for (int i = 0; i < 9; i++) {

        }
        ItemStack item = attacker.getInventory().getItem(slot);
        if (item == null
                || item.getType() != Material.STONE_SPADE
                || item.getItemMeta() == null
                || item.getItemMeta().getDisplayName() == null
                || !item.getItemMeta().getDisplayName().contains("MK.13 EGLM")) { // 무기가 'MK.13 EGLM'이 아닐 경우
            return;
        }

        Location location;
        if (event.getHitBlock() == null) { // 블럭에 피격된 것이 아닐 경우
            location = event.getHitEntity().getLocation();
        }
        else { // 블럭에 피격됐을 경우
            location = event.getHitBlock().getLocation();
            location.setY(location.getY() + 1);
        }

        AreaEffectCloud lingering = attacker.getWorld().spawn(location, AreaEffectCloud.class);
        lingering.setMetadata("Shooter",new FixedMetadataValue(plugin, attacker.getName()));
        lingering.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 1, false, false), true);
        // lingering.setParticle(Particle.VILLAGER_HAPPY) // 가스 파티클
        lingering.setColor(Color.GREEN); // 가스 색깔
        lingering.setDuration(100); // 가스 지속 시간
        lingering.setRadius(Float.parseFloat("1.75")); // 가스 반경
        lingering.setRadiusOnUse(0); // 가스 크기 감소 시작까지 걸리는 시간
        lingering.setRadiusPerTick(Float.parseFloat("0.007")); // 시간별 가스 크기 감소율
        lingering.setReapplicationDelay(10); // 효과 재적용 지연 시간
        lingering.setWaitTime(0); // 효과 적용 지연 시간
    }
     */

    /*
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

        if (weaponTitle.contains("MK_13_EGLM")) {

            event.setCancelled(true);

            // 공격자 및 피해자
            if (event.getDamager().getMetadata("Shooter") == null) {
                return;
            }
            Player attacker = Bukkit.getPlayer(event.getDamager().getMetadata("Shooter").get(0).asString());

            if (!(event.getEntity() instanceof LivingEntity)) {
                return;
            }
            LivingEntity victim = (LivingEntity) event.getEntity();

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

            CraftLivingEntity craftVictim = (CraftLivingEntity) victim;
            CraftPlayer craftAttacker = (CraftPlayer) attacker;
            EntityLiving victimHandle = craftVictim.getHandle();

            // CustomDamageEntityEvent 이벤트 호출
            CustomDamageEntityEvent customDamageEntityEvent = new CustomDamageEntityEvent(attacker, victim, damage);
            Bukkit.getPluginManager().callEvent(customDamageEntityEvent);

            if (!customDamageEntityEvent.isCancelled()) {
                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), 1f);
                victimHandle.damageEntity(DamageSource.b(craftAttacker.getHandle()), (float) (customDamageEntityEvent.getDamage() - 1));
            }

        }

    }
     */

}
