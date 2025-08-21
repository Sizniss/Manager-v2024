package kr.sizniss.manager;

import com.google.gson.JsonElement;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import net.dv8tion.jda.api.JDA;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EnumMoveType;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.text.SimpleDateFormat;
import java.util.*;

import static kr.sizniss.manager.Manager.*;

public class Methods {

    public class CustomArmorStand extends EntityArmorStand {

        public CustomArmorStand(net.minecraft.server.v1_12_R1.World world, double d0, double d1, double d2) {
            super(world, d0, d1, d2);
            this.setNoGravity(true);
        }

        protected float g(float f, float f1) {
            if (!isNoGravity()) {
                super.g(f, f1);
            }
            else {
                move(EnumMoveType.SELF, motX, motY, motZ);
            }

            return 0.0f;
        }

    }

    // 위치 판별 함수
    public boolean isBetweenLocations(Location targetLocation, Location location1, Location location2) {
        double minX = Math.min(location1.getX(), location2.getX());
        double minY = Math.min(location1.getY(), location2.getY());
        double minZ = Math.min(location1.getZ(), location2.getZ());

        double maxX = Math.max(location1.getX(), location2.getX());
        double maxY = Math.max(location1.getY(), location2.getY());
        double maxZ = Math.max(location1.getZ(), location2.getZ());

        double targetX = targetLocation.getX();
        double targetY = targetLocation.getY();
        double targetZ = targetLocation.getZ();

        return targetX >= minX && targetX <= maxX
                && targetY >= minY && targetY <= maxY
                && targetZ >= minZ && targetZ <= maxZ;
    }

    // 블럭 표면 접촉 판별 함수
    public boolean isOnBlockLocation(Player player, Location location) {
        double playerPosX = player.getLocation().getX();
        double playerPosY = player.getLocation().getY();
        double playerPosZ = player.getLocation().getZ();
        Location playerLocation = new Location(player.getWorld(), playerPosX, playerPosY - 1, playerPosZ);

        return location.getBlock().getLocation().equals(playerLocation.getBlock().getLocation());
    }

    // 백스텝 판별 함수
    public boolean isBackstab(LivingEntity attacker, LivingEntity victim) {
        Vector attackerDirection = attacker.getLocation().getDirection();
        Vector victimDirection = victim.getLocation().getDirection();

        // 플레이어 간의 벡터 내적
        double dotProduct = attackerDirection.dot(victimDirection);

        // 내적이 음수이면 시선이 역방향이므로 백스텝으로 판단
        return victim.getEyeLocation().getPitch() < -60 ? true : dotProduct >= 0;
    }

    // 랜덤 정수 반환 함수
    public int getRandomInteger(int min, int max) {
        int minNum;
        int maxNum;

        if (min > max) {
            minNum = max;
            maxNum = min;
        } else {
            minNum = min;
            maxNum = max;
        }

        return (int) (Math.random() * (maxNum - minNum)) + minNum;
    }

    // 배열 정수 합산값 반환 함수
    public int getSumElement(ArrayList<Integer> array, int number) {
        int sum = 0;

        number = Math.min(number, array.size()); // number 변수가 배열 크기를 넘어가지 않도록 확인
        for (int i = 0; i < number; i++) {
            sum += array.get(i); // 배열의 값을 합산
        }

        return sum;
    }

    // 색깔 반환 함수
    public ChatColor getColor(String color) {
        switch (color) {
            case "White":
                return ChatColor.WHITE;
            case "Green":
                return ChatColor.GREEN;
            case "Skyblue":
                return ChatColor.AQUA;
            case "Pink":
                return ChatColor.RED;
            case "Magenta":
                return ChatColor.LIGHT_PURPLE;
            case "Yellow":
                return ChatColor.YELLOW;
            default:
                return ChatColor.WHITE;
        }
    }

    public Player getPlayerInSight(Player player, double maxDistance) {
        Location playerEyeLocation = player.getEyeLocation();
        Vector playerDirection = playerEyeLocation.getDirection();

        // 플레이어 주변 제한된 영역 내의 엔티티만 가져오기
        List<Entity> nearbyEntities = player.getNearbyEntities(maxDistance, maxDistance, maxDistance);

        for (Entity target : nearbyEntities) {
            // 엔티티가 플레이어 뒤에 있으면 무시
            Location targetLoc = target.getLocation();
            targetLoc.setY(targetLoc.getY() + 1);
            Vector toEntity = targetLoc.toVector().subtract(playerEyeLocation.toVector());
            if (toEntity.dot(playerDirection) <= 0) continue;

            // 엔티티까지의 거리 확인
            double distance = toEntity.length();
            if (distance > maxDistance) continue;

            // 시야각 확인 (약 60도)
            double angle = Math.toDegrees(Math.acos(toEntity.normalize().dot(playerDirection)));
            if (angle > 30) continue;

            // 시야 차단 확인
            boolean hasLineOfSight = player.hasLineOfSight(target);
            if (!hasLineOfSight) continue;

            // 엔티티 타입이 플레이어인지 확인
            if (target.getType() != EntityType.PLAYER) continue;

            return (Player) target;
        }

        return null;
    }



    // 점프 함수
    public void jump(Player player, Location location) {
        player.setVelocity(location.toVector());
    }

    // 넉백 함수
    public void applyKnockback(Location location, Entity entity, double knockback) {
        // 근원 위치에서 엔티티를 바라보는 방향 구하기
        Vector direction = entity.getLocation().toVector().subtract(location.toVector()).normalize();

        // 엔티티에게 넉백 적용
        entity.setVelocity(direction.multiply(knockback));
    }

    // 사망 방지 및 감염 함수
    public void preventDeathAndInfected(EntityDamageEvent event, Player victim) {
        // 피해자가 인간일 경우
        if (SelonZombieSurvival.manager.getHumanList().contains(victim)) {
            event.setDamage(0);

            // 피해자를 감염
            SelonZombieSurvival.manager.infected(victim);

        }
        // 피해자가 좀비일 경우
        else if (SelonZombieSurvival.manager.getZombieList().contains(victim)) {

        }
        // 피해자가 인간 및 좀비가 아닐 경우
        else {
            event.setCancelled(true);
            if (!victim.isDead()) {
                victim.setHealth(0);
            }
        }
    }

    public void preventDeathAndInfected(EntityDamageEvent event, Player victim, Player attacker) {
        // 피해자가 인간일 경우
        if (SelonZombieSurvival.manager.getHumanList().contains(victim)) {
            event.setDamage(0);

            // 피해자를 감염
            // 가해자가 좀비일 경우
            if (SelonZombieSurvival.manager.getZombieList().contains(attacker)) {
                SelonZombieSurvival.manager.infected(victim, attacker);
            }
            // 가해자가 좀비가 아닐 경우
            else {
                SelonZombieSurvival.manager.infected(victim);
            }

        }
        // 피해자가 좀비일 경우
        else if (SelonZombieSurvival.manager.getZombieList().contains(victim)) {

        }
        // 피해자가 인간 및 좀비가 아닐 경우
        else {
            event.setCancelled(true);
            if (!victim.isDead()) {
                victim.setHealth(0);
            }
        }
    }



    // 신입 유저 여부 확인 함수
    public boolean isNewUser(OfflinePlayer player) {
        int win = kr.sizniss.data.Files.getRecord(player, "Win");
        int lose = kr.sizniss.data.Files.getRecord(player, "Lose");
        int kill = kr.sizniss.data.Files.getRecord(player, "Kill");

        if (win + lose < 500 && kill < 500) {
            return true;
        }

        return false;
    }

    public int getNewUserLevel(OfflinePlayer player) {
        int win = kr.sizniss.data.Files.getRecord(player, "Win");
        int lose = kr.sizniss.data.Files.getRecord(player, "Lose");
        int kill = kr.sizniss.data.Files.getRecord(player, "Kill");

        if (win + lose <= 33 && kill <= 33) {
            return 3;
        }
        if (win + lose <= 66 && kill <= 66) {
            return 2;
        }
        if (win + lose <= 99 && kill <= 99) {
            return 1;
        }

        return 0;
    }

    public boolean isFirstJoin(OfflinePlayer player) {
        int win = kr.sizniss.data.Files.getRecord(player, "Win");
        int lose = kr.sizniss.data.Files.getRecord(player, "Lose");
        int kill = kr.sizniss.data.Files.getRecord(player, "Kill");

        if (win + lose == 0 && kill == 0) {
            return true;
        }

        return false;
    }



    // 병과 반환 함수
    public ItemStack getClass(String kind) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("0"));
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> itemLore = new ArrayList<String>();

        switch (kind) {
            case "보병":
                itemMeta.setDisplayName("§f보병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 물약 ]");
                itemLore.add(" §e→ §f보유 개수: §72개");
                itemLore.add(" §e→ §f지속 시간: §75초");
                itemLore.add(" §e→ §f효과 레벨: §710");
                itemLore.add(" §e→ §7속도 증가 효과를 얻습니다.");
                itemLore.add("   §f(§eR-Click §7or §eF§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "포병":
                itemMeta.setDisplayName("§f포병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 폭탄 ]");
                itemLore.add(" §e→ §f보유 개수: §72개");
                itemLore.add(" §e→ §7폭발 범위 내의 모든 좀비에게 큰");
                itemLore.add("   §7피해를 입힙니다. §f(§eR-Click§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "교란병":
                itemMeta.setDisplayName("§f교란병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 수약 ]");
                itemLore.add(" §e→ §f보유 개수: §72개");
                itemLore.add(" §e→ §f지속 시간: §77초");
                itemLore.add(" §e→ §7투명화 효과를 얻습니다.");
                itemLore.add("   §f(§eR-Click §7or §eF§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "의무병":
                itemMeta.setDisplayName("§f의무병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 치료제 ]");
                itemLore.add(" §e→ §f보유 개수: §72개");
                itemLore.add(" §e→ §7감염자에게 접종하여 인간으로");
                itemLore.add("   §7치유합니다. §f(§eL-Click§f)");
                itemLore.add(" §e→ §7숙주에게 접종하여 큰 피해를");
                itemLore.add("   §7입힙니다. §f(§eL-Click§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "기갑병":
                itemMeta.setDisplayName("§f기갑병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 영약 ]");
                itemLore.add(" §e→ §f보유 개수: §72개");
                itemLore.add(" §e→ §f지속 시간: §77초");
                itemLore.add(" §e→ §f효과 레벨: §75");
                itemLore.add(" §e→ §7생명력 강화 효과를 얻습니다.");
                itemLore.add("   §f(§eR-Click §7or §eF§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "보급병":
                itemMeta.setDisplayName("§f보급병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 탄약 박스 ]");
                itemLore.add(" §e→ §f보유 개수: §74개");
                itemLore.add(" §e→ §7장착되어 있는 무기들을 즉시");
                itemLore.add("   §7재장전합니다. §f(§eR-Click §7or §eF§f)");
                itemLore.add(" §e→ §7대상 인간에게 병과 아이템을");
                itemLore.add("   §7보급합니다. §f(§eQ§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "공병":
                itemMeta.setDisplayName("§f공병");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §720");
                itemLore.add(" §e→ §f이동 속도: §70.22");
                itemLore.add("");
                itemLore.add(" §e→ §f추가 탄창: §70%");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 아이템");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 끈끈이 ]");
                itemLore.add(" §e→ §f보유 개수: §72개");
                itemLore.add(" §e→ §f지속 시간: §73초");
                itemLore.add(" §e→ §7이동을 봉쇄하는 끈끈이를");
                itemLore.add("   §7설치합니다. §f(§eR-Click§f)");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
        }

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack getClass(OfflinePlayer player, String kind) {
        ItemStack item = getClass(kind);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> itemLore = (ArrayList<String>) itemMeta.getLore();
        int plusLoreNumber = 0;

        // 최대 체력
        int maxHealthStat = kr.sizniss.data.Files.getClassStatus(player, kind, "최대_체력");
        if (maxHealthStat > 0) { itemLore.set(3, " §e→ §f최대 체력: §b" + (20 + maxHealthStat * 4)); }

        // 이동 속도
        int moveSpeedStat = kr.sizniss.data.Files.getClassStatus(player, kind, "이동_속도");
        if (moveSpeedStat > 0) { itemLore.set(4, " §e→ §f이동 속도: §b" + String.format("%.3f", 0.22 + moveSpeedStat * 0.015)); }

        // 재생
        int regenerationStat = kr.sizniss.data.Files.getClassStatus(player, kind, "재생");
        if (regenerationStat > 0) {
            plusLoreNumber += 2;
            itemLore.add(4 + 1, "");
            itemLore.add(4 + 2, " §e→ §f지속 효과: §b재생(Lv." + regenerationStat + ")");
        }

        // 추가 탄창
        switch (kind) {
            case "보병":
            case "포병":
            case "교란병":
            case "의무병":
            case "기갑병":
            case "보급병":
            case "공병":
                int ammoAmountStat = kr.sizniss.data.Files.getClassStatus(player, kind, "추가_탄창");
                if (ammoAmountStat > 0) { itemLore.set(6 + plusLoreNumber, " §e→ §f추가 탄창: §b" + (ammoAmountStat * 50) + "%"); }
        }

        // 아이템 보유 개수
        int itemAmountStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_보유_개수");
        switch (kind) {
            case "보병":
            case "포병":
            case "교란병":
            case "의무병":
            case "기갑병":
            case "공병":
                if (itemAmountStat > 0) {
                    itemLore.set(12 + plusLoreNumber, " §e→ §f보유 개수: §b" + (2 + itemAmountStat) + "개");
                }
                break;
            case "보급병":
                if (itemAmountStat > 0) {
                    itemLore.set(12 + plusLoreNumber, " §e→ §f보유 개수: §b" + (4 + itemAmountStat * 2) + "개");
                }
                break;
        }

        // 아이템 지속 시간
        /*
        int durationStat;
        switch (kind) {
            case "보병":
                durationStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_지속_시간");
                if (durationStat > 0) { itemLore.set(13 + plusLoreNumber, " §e→ §f지속 시간: §b" + (5 + durationStat) + "초"); }
                break;
            case "교란병":
                durationStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_지속_시간");
                if (durationStat > 0) { itemLore.set(13 + plusLoreNumber, " §e→ §f지속 시간: §b" + (7 + durationStat) + "초"); }
                break;
        }
         */

        // 아이템 효과 레벨
        /*
        switch (kind) {
            case "기갑병":
                int levelStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_효과_레벨");
                if (levelStat > 0) { itemLore.set(14 + plusLoreNumber, " §e→ §f효과 레벨: §b" + (7 + levelStat)); }
        }
         */

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        return item;
    }

    // 병과 레벨 반환 함수
    public int getClassLevel(OfflinePlayer player, String kind) {
        // int playCount = kr.sizniss.data.Files.getClassPlayCount(player, kind);
        int playCount = 0;
        for (String kinds : variables.getClassName()) {
            playCount += kr.sizniss.data.Files.getClassPlayCount(player, kinds);
        }
        int level = 0;
        ArrayList<Integer> experience = variables.getClassExperience();
        int stackInteger = 0;
        for (int i = 0; i < experience.size(); i++) {
            stackInteger += experience.get(i);
            level += 1;
            if (playCount < stackInteger) {
                stackInteger -= experience.get(i);
                level -= 1;
                break;
            }
        }

        return level;
    }

    // 병과 잔여 포인트 반환 함수
    public int getClassRemainingPoint(OfflinePlayer player, String kind) {
        int point = getClassLevel(player, kind) - (kr.sizniss.data.Files.getClassStatus(player, kind, "최대_체력")
                + kr.sizniss.data.Files.getClassStatus(player, kind, "재생")
                + kr.sizniss.data.Files.getClassStatus(player, kind, "이동_속도")
                + kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_보유_개수") * 2);

        switch (kind) {
            /*
            case "보병":
            case "교란병":
                point -= kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_지속_시간");
                break;
             */
            case "보병":
            case "포병":
            case "교란병":
            case "의무병":
            case "기갑병":
            case "보급병":
            case "공병":
                point -= kr.sizniss.data.Files.getClassStatus(player, kind, "추가_탄창") * 2;
                break;
                /*
            case "기갑병":
                point -= kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_효과_레벨");
                break;
                 */
        }

        return point;
    }

    // 병과 아이템 반환 함수
    public ItemStack getClassItem(String kind) {
        ItemStack classItem;
        switch (kind) {
            case "보병":
                classItem = variables.getLiquidMedicine().clone();
                break;
            case "포병":
                classItem = variables.getBomb().clone();
                break;
            case "교란병":
                classItem = variables.getWaterMedicine().clone();
                break;
            case "의무병":
                classItem = variables.getRemedy().clone();
                break;
            case "기갑병":
                classItem = variables.getElixir().clone();
                break;
            case "보급병":
                classItem = variables.getAmmoBox().clone();
                break;
            case "공병":
                classItem = variables.getLime().clone();
                break;
            default:
                classItem = variables.getLiquidMedicine().clone();
                break;
        }

        return classItem;
    }

    // 병과 아이템 개수 반환 함수
    public int getClassItemAmount(String kind) {
        int itemAmount;
        switch (kind) {
            case "보병":
                itemAmount = 1;
                break;
            case "포병":
                itemAmount = 1;
                break;
            case "교란병":
                itemAmount = 1;
                break;
            case "의무병":
                itemAmount = 1;
                break;
            case "기갑병":
                itemAmount = 1;
                break;
            case "보급병":
                itemAmount = 2;
                break;
            case "공병":
                itemAmount = 1;
                break;
            default:
                itemAmount = 1;
                break;
        }

        return itemAmount;
    }

    // 병과 아이템 개수 반환 함수
    public int getClassItemAmount(Player player) {
        String kind = variables.getSelectedClass().get(player);

        int amount;
        int amountStat = kr.sizniss.data.Files.getClassStatus(player, kind, "아이템_보유_개수");
        switch (kind) {
            case "보병":
                amount = 2;
                amount += amountStat;
                break;
            case "포병":
                amount = 2;
                amount += amountStat;
                break;
            case "교란병":
                amount = 2;
                amount += amountStat;
                break;
            case "의무병":
                amount = 2;
                amount += amountStat;
                break;
            case "기갑병":
                amount = 2;
                amount += amountStat;
                break;
            case "보급병":
                amount = 4;
                amount += amountStat * 2;
                break;
            case "공병":
                amount = 2;
                amount += amountStat;
                break;
            default:
                amount = 2;
                amount += amountStat;
                break;
        }

        return amount;
    }

    // 탄약 개수 반환 함수
    public int getAmmoAmount(Player player) {
        String kind = variables.getSelectedClass().get(player);
        String weapon = variables.getSelectedMainWeapon().get(player);
        int level = kr.sizniss.data.Files.getMainWeaponLevel(player, weapon);

        int ammoStat = 0;
        switch (kind) {
            case "보병":
            case "포병":
            case "교란병":
            case "의무병":
            case "기갑병":
            case "보급병":
            case "공병":
                ammoStat += kr.sizniss.data.Files.getClassStatus(player, kind, "추가_탄창");
        }

        int amount = getAmmoAmount(weapon, level, 1.0 + ammoStat * 0.5);

        return amount;
    }

    public int getAmmoAmount(String weapon, double percent) {
        return getAmmoAmount(weapon, 0, percent);
    }

    public int getAmmoAmount(String weapon, int level, double percent) {
        int amount = 8;

        if (weapon.contains("MP5")) {
            amount = 8;
        } else if (weapon.contains("P90")) {
            amount = 6;
        } else if (weapon.contains("KRISS_Vector")) {
            amount = 10;
        } else if (weapon.contains("AR-57")) {
            amount = 6;
        } else if (weapon.contains("M1928_Thompson")) {
            amount = 6;
        } else if (weapon.contains("M4A1")) {
            amount = 8;
        } else if (weapon.contains("AK-47")) {
            amount = 6;
        } else if (weapon.contains("M16A4")) {
            amount = 8;
        } else if (weapon.contains("AUG")) {
            amount = 8;
        } else if (weapon.contains("Steyr_Scout")) {
            amount = 6;
        } else if (weapon.contains("AWP")) {
            amount = 8;
        } else if (weapon.contains("AWM")) {
            amount = 8;
        } else if (weapon.contains("SG550")) {
            amount = 4;
        } else if (weapon.contains("Kar98k")) {
            amount = 40;
        } else if (weapon.contains("SPAS-12")) {
            amount = 56;
        } else if (weapon.contains("USAS-12")) {
            amount = 4;
        } else if (weapon.contains("AA-12")) {
            amount = 4;
        } else if (weapon.contains("Triple_Barrel")) {
            amount = 22;
        } else if (weapon.contains("M1887_Winchester")) {
            amount = 40;
        } else if (weapon.contains("M249")) {
            amount = 2;
        } else if (weapon.contains("MG3")) {
            amount = 2;
        } else if (weapon.contains("PKM")) {
            amount = 2;
        } else if (weapon.contains("M134_Minigun")) {
            amount = 2;
        } else if (weapon.contains("석궁")) {
            amount = 20;
        } else if (weapon.contains("RPG-7")) {
            amount = 18;
        } else if (weapon.contains("진압_방패")) {
            amount = 18;
        } else if (weapon.contains("화염_방사기")) {
            amount = 2;
        } else if (weapon.contains("Barret_M99")) {
            amount = 16;
        } else if (weapon.contains("화룡포")) {
            amount = 16;
        } else if (weapon.contains("혈적자")) {
            amount = 20;
        } else if (weapon.contains("전기톱")) {
            amount = 2;
        } else if (weapon.contains("M32_MGL")) {
            amount = 36;
        } else if (weapon.contains("신기전")) {
            if (level == 0) {
                amount = 40;
            } else if (level == 1) {
                amount = 50;
            }
        } else if (weapon.contains("MK_11_SWS")) {
            amount = 4;
        } else if (weapon.contains("VSS_Vintorez")) {
            amount = 10;
        } else if (weapon.contains("가시_방패")) {
            amount = 18;
        } else if (weapon.contains("물대포")) {
            amount = 2;
        } else if (weapon.contains("C4")) {
            amount = 20;
        } else if (weapon.contains("AEA_Zeus_2")) {
            amount = 16;
        } else if (weapon.contains("DP-12")) {
            amount = 84;
        } else if (weapon.contains("시한폭탄")) {
            amount = 12;
        } else if (weapon.contains("SSW40")) {
            amount = 4;
        }

        amount = (int) (amount * percent);

        return amount;
    }

    // 탄약 추가 함수
    public boolean addAmmoAmount(Player player, int amount) {
        ItemStack ammo = variables.getAmmo().clone();
        ItemStack[] extraAmmo = {ammo.clone(), ammo.clone()};

        int ammoAmount = 0;
        int[] extraAmount = {0, 0};
        Inventory inventory = player.getInventory();
        if (inventory.contains(Material.IRON_INGOT)) {
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i) == null) {
                    continue;
                }
                ItemStack slotItem = inventory.getItem(i);

                if (slotItem.getType() == Material.IRON_INGOT) {
                    ammoAmount += slotItem.getAmount();
                }
            }
        }
        ammoAmount += amount;

        if (ammoAmount > 381) {
            return false; // 탄약 추가 실패
        }

        if (ammoAmount > 127) {
            extraAmount[0] = ammoAmount - 127;
            if (extraAmount[0] > 127) {
                extraAmount[1] = extraAmount[0] - 127;
                extraAmount[0] = 127;
            }
            ammoAmount = 127;
        }

        ammo.setAmount(ammoAmount);
        extraAmmo[0].setAmount(extraAmount[0]);
        extraAmmo[1].setAmount(extraAmount[1]);

        player.getInventory().setItem(8, ammo);
        player.getInventory().setItem(7, extraAmmo[0]);
        player.getInventory().setItem(6, extraAmmo[1]);

        return true; // 탄약 추가 완료
    }

    // 탄약 차감 함수
    public boolean subtractAmmoAmount(Player player, int amount) {
        ItemStack ammo = variables.getAmmo().clone();
        ItemStack[] extraAmmo = {ammo.clone(), ammo.clone()};

        int ammoAmount = 0;
        int[] extraAmount = {0, 0};
        Inventory inventory = player.getInventory();
        if (inventory.contains(Material.IRON_INGOT)) {
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i) == null) {
                    continue;
                }
                ItemStack slotItem = inventory.getItem(i);

                if (slotItem.getType() == Material.IRON_INGOT) {
                    ammoAmount += slotItem.getAmount();
                }
            }
        }
        ammoAmount -= amount;

        if (ammoAmount < 0) {
            return false; // 탄약 차감 실패
        }

        if (ammoAmount > 127) {
            extraAmount[0] = ammoAmount - 127;
            if (extraAmount[0] > 127) {
                extraAmount[1] = extraAmount[0] - 127;
                extraAmount[0] = 127;
            }
            ammoAmount = 127;
        }

        ammo.setAmount(ammoAmount);
        extraAmmo[0].setAmount(extraAmount[0]);
        extraAmmo[1].setAmount(extraAmount[1]);

        player.getInventory().setItem(8, ammo);
        player.getInventory().setItem(7, extraAmmo[0]);
        player.getInventory().setItem(6, extraAmmo[1]);

        return true; // 탄약 차감 성공
    }

    // 약실 크기 반환 함수
    public int getPowderChamberSize(String weapon) {
        return getPowderChamberSize(weapon, 0);
    }

    public int getPowderChamberSize(String weapon, int level) {
        int size = 1;

        if (weapon.contains("Kar98k")) {
            size = 5;
        } else if (weapon.contains("SPAS-12")) {
            size = 7;
        } else if (weapon.contains("M1887_Winchester")) {
            size = 5;
        } else if (weapon.contains("M32_MGL")) {
            size = 6;
        } else if (weapon.contains("신기전")) {
            if (level == 0) {
                size = 4;
            } else if (level == 1) {
                size = 5;
            }
        } else if (weapon.contains("DP-12")) {
            size = 14;
        }

        return size;
    }

    // 소모 함수
    public void consume(Player player, ItemStack item, int slot, int amount) {
        int itemAmount = item.getAmount();

        if (itemAmount > amount) { // 아이템 개수가 소모 개수의 초과일 경우
            item.setAmount(itemAmount - amount);
        } else {
            item.setAmount(0);
        }

        player.getInventory().setItem(slot, item);
    }



    // 타입 반환 함수
    public ItemStack getType(String type) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("2"));
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> itemLore = new ArrayList<String>();

        switch (type) {
            case "버서커":
                itemMeta.setDisplayName("§f버서커");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 폭주 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §77초");
                itemLore.add(" §e→ §f효과 레벨: §7+1");
                itemLore.add(" §e→ §f효과 범위: §710m");
                itemLore.add(" §e→ §7속도 증가 효과를 얻습니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "바드":
                itemMeta.setDisplayName("§f바드");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 치유 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §77초");
                itemLore.add(" §e→ §f효과 레벨: §76");
                itemLore.add(" §e→ §f효과 범위: §710m");
                itemLore.add(" §e→ §7재생 효과를 얻습니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "나이트":
                itemMeta.setDisplayName("§f나이트");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 강화 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §77초");
                itemLore.add(" §e→ §f효과 레벨: §7+2");
                itemLore.add(" §e→ §f효과 범위: §710m");
                itemLore.add(" §e→ §7저항 효과를 얻습니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "네크로맨서":
                itemMeta.setDisplayName("§f네크로맨서");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 불사 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f사용 횟수: §71개");
                itemLore.add(" §e→ §7사망에 이르는 피해를 입을 경우");
                itemLore.add("   §7사망이 저지되고, 체력을 일부");
                itemLore.add("   §7회복합니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "워리어":
                itemMeta.setDisplayName("§f워리어");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 반전 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §75초");
                itemLore.add(" §e→ §72초간 피해를 누적시킵니다.");
                itemLore.add("   §7누적된 피해량에 비례해서 흡수");
                itemLore.add("   §7효과를 얻습니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "로그":
                itemMeta.setDisplayName("§f로그");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 비행 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §75초");
                itemLore.add(" §e→ §f효과 레벨: §71");
                itemLore.add(" §e→ §7공중 부양 효과를 얻습니다.");
                itemLore.add(" §e→ §7도약의 재사용 대기 시간이 크게");
                itemLore.add("   §7감소합니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "어쌔신":
                itemMeta.setDisplayName("§f어쌔신");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 은신 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §74초");
                itemLore.add(" §e→ §7투명화 효과를 얻습니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "레인저":
                itemMeta.setDisplayName("§f레인저");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 충격 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f사용 횟수: §71개");
                itemLore.add(" §e→ §7충격탄을 발사하여 피격된 인간의");
                itemLore.add("   §7주 무기를 잠시 봉인합니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "버스터":
                itemMeta.setDisplayName("§f버스터");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 생체 폭탄 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f사용 횟수: §71개");
                itemLore.add(" §e→ §7넉백 효과가 있는 생체 폭탄을");
                itemLore.add("   §7투척합니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "서머너":
                itemMeta.setDisplayName("§f서머너");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 풀링 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f사용 횟수: §71개");
                itemLore.add(" §e→ §7검은 투사체를 발사하여 피격된");
                itemLore.add("   §7인간을 자신이 있는 곳으로");
                itemLore.add("   §7끌고옵니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
            case "헌터":
                itemMeta.setDisplayName("§f헌터");

                itemLore.add("");
                itemLore.add(" §f※ 능력치");
                itemLore.add("");
                itemLore.add(" §e→ §f최대 체력: §7100");
                itemLore.add(" §e→ §f이동 속도: §70.25");
                itemLore.add("");
                itemLore.add(" §e→ §f지속 효과: §7재생(Lv.3)");
                itemLore.add("");
                itemLore.add(" §e→ §f도약 재사용 대기 시간: §720초");
                itemLore.add("");
                itemLore.add("");
                itemLore.add(" §f※ 능력");
                itemLore.add("");
                itemLore.add(" §e→ §f[ 투시 ]");
                itemLore.add(" §e→ §f재사용 대기 시간: §725초");
                itemLore.add(" §e→ §f지속 시간: §75초");
                itemLore.add(" §e→ §f효과 범위: §730m");
                itemLore.add(" §e→ §7가장 가까이 있는 인간에게");
                itemLore.add("   §7발광 효과를 부여합니다.");
                itemLore.add("");
                itemMeta.setLore(itemLore);

                break;
        }

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack getType(OfflinePlayer player, String type) {
        ItemStack item = getType(type);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> itemLore = (ArrayList<String>) itemMeta.getLore();
        int plusLoreNumber = 0;

        // 최대 체력
        int maxHealthStat = kr.sizniss.data.Files.getTypeStatus(player, type, "최대_체력");
        if (maxHealthStat > 0) { itemLore.set(3, " §e→ §f최대 체력: §b" + (100 + maxHealthStat * 20)); }

        // 이동 속도
        int moveSpeedStat = kr.sizniss.data.Files.getTypeStatus(player, type, "이동_속도");
        if (moveSpeedStat > 0) { itemLore.set(4, " §e→ §f이동 속도: §b" + String.format("%.3f", 0.25 + moveSpeedStat * 0.015)); }

        // 재생
        int regenerationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "재생");
        itemLore.set(6, " §e→ §f지속 효과: §" + (regenerationStat > 0 ? "b" : "7") + "재생(Lv." + (3 + regenerationStat) + ")");

        // 점프 강화
        int jumpStat = kr.sizniss.data.Files.getTypeStatus(player, type, "점프_강화");
        if (jumpStat > 0) {
            plusLoreNumber += 1;
            itemLore.add(6 + 1, "            §0. §b점프 강화(Lv." + jumpStat + ")");
        }

        // 도약 재사용 대기 시간
        int leapCooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "도약_재사용_대기_시간");
        if (leapCooldownStat > 0) { itemLore.set(8 + plusLoreNumber, " §e→ §f도약 재사용 대기 시간: §b" + (20 - leapCooldownStat * 2) + "초"); }

        // 능력 재사용 대기 시간
        int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
        if (cooldownStat > 0) { itemLore.set(14 + plusLoreNumber, " §e→ §f재사용 대기 시간: §b" + (25 - cooldownStat * 2) + "초"); }

        // 능력 지속 시간
        int durationStat;
        switch (type) {
            case "버서커":
            case "바드":
            case "나이트":
                durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
                if (durationStat > 0) { itemLore.set(15 + plusLoreNumber, " §e→ §f지속 시간: §b" + (7 + durationStat) + "초"); }
                break;
            case "워리어":
            case "로그":
                durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
                if (durationStat > 0) { itemLore.set(15 + plusLoreNumber, " §e→ §f지속 시간: §b" + (5 + durationStat) + "초"); }
                break;
            case "어쌔신":
                durationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
                if (durationStat > 0) { itemLore.set(15 + plusLoreNumber, " §e→ §f지속 시간: §b" + (4 + durationStat) + "초"); }
                break;
        }

        // 능력 사용 횟수
        int amountStat;
        switch (type) {
            case "네크로맨서":
            case "레인저":
            case "버스터":
            case "서머너":
                amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
                if (amountStat > 0) { itemLore.set(15 + plusLoreNumber, " §e→ §f사용 횟수: §b" + (1 + amountStat)); }
                break;
        }

        // 능력 효과 범위
        switch (type) {
            case "헌터":
                int radiusStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_효과_범위");
                if (radiusStat > 0) { itemLore.set(16 + plusLoreNumber, " §e→ §f효과 범위: §b" + (30 + radiusStat * 10) + "m"); }
        }

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        return item;
    }

    // 타입 레벨 반환 함수
    public int getTypeLevel(OfflinePlayer player, String type) {
        // int playCount = kr.sizniss.data.Files.getTypePlayCount(player, type);
        int playCount = 0;
        for (String types : variables.getTypeName()) {
            playCount += kr.sizniss.data.Files.getTypePlayCount(player, types);
        }
        int level = 0;
        ArrayList<Integer> experience = variables.getTypeExperience();
        int stackInteger = 0;
        for (int i = 0; i < experience.size(); i++) {
            stackInteger += experience.get(i);
            level += 1;
            if (playCount < stackInteger) {
                stackInteger -= experience.get(i);
                level -= 1;
                break;
            }
        }

        return level;
    }

    // 타입 잔여 포인트 반환 함수
    public int getTypeRemainingPoint(OfflinePlayer player, String type) {
        int point = getTypeLevel(player, type) - (kr.sizniss.data.Files.getTypeStatus(player, type, "최대_체력")
                + kr.sizniss.data.Files.getTypeStatus(player, type, "재생")
                + kr.sizniss.data.Files.getTypeStatus(player, type, "이동_속도")
                + kr.sizniss.data.Files.getTypeStatus(player, type, "점프_강화")
                + kr.sizniss.data.Files.getTypeStatus(player, type, "도약_재사용_대기_시간")
                + kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간"));

        switch (type) {
            case "버서커":
            case "바드":
            case "나이트":
            case "워리어":
            case "로그":
            case "어쌔신":
                point -= kr.sizniss.data.Files.getTypeStatus(player, type, "능력_지속_시간");
                break;
            case "네크로맨서":
            case "레인저":
            case "버스터":
            case "서머너":
                point -= kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수") * 2;
                break;
            case "헌터":
                point -= kr.sizniss.data.Files.getTypeStatus(player, type, "능력_효과_범위");
                break;
        }

        return point;
    }

    // 타입 아이템 반환 함수
    public ItemStack getTypeItem(String type) {
        ItemStack ability;
        switch (type) {
            case "버서커":
                ability = variables.getRunaway().clone();
                break;
            case "바드":
                ability = variables.getCure().clone();
                break;
            case "나이트":
                ability = variables.getStrengthen().clone();
                break;
            case "네크로맨서":
                ability = variables.getUndying().clone();
                break;
            case "워리어":
                ability = variables.getReversal().clone();
                break;
            case "로그":
                ability = variables.getFlight().clone();
                break;
            case "어쌔신":
                ability = variables.getHide().clone();
                break;
            case "레인저":
                ability = variables.getShock().clone();
                break;
            case "버스터":
                ability = variables.getBioBomb().clone();
                break;
            case "서머너":
                ability = variables.getPulling().clone();
                break;
            case "헌터":
                ability = variables.getClairvoyance().clone();
                break;
            default:
                ability = variables.getRunaway().clone();
                break;
        }

        return ability;
    }

    // 타입 아이템 개수 반환 함수
    public int getTypeItemAmount(Player player) {
        String type = variables.getSelectedType().get(player);

        int amount = 1;

        switch (type) {
            case "네크로맨서":
            case "레인저":
            case "버스터":
            case "서머너":
                int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
                amount += amountStat;
        }

        return amount;
    }

    // 재사용 대기 시간 진행 함수
    public void cooldown(Player player, ItemStack item, int slot, double cooldown) {
        cooldown(player, item, slot, cooldown, 1);
    }

    public void cooldown(Player player, ItemStack item, int slot, double cooldown, int returnAmount) {
        final int[] task = { -1 };

        task[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            double currentCooldown = cooldown + 0.25;
            Inventory inventory = player.getInventory();

            ItemMeta meta = item.getItemMeta();
            String title = meta.getDisplayName();
            Material material = item.getType();
            Short durability = item.getDurability();
            int amount = item.getAmount();

            @Override
            public void run() {
                currentCooldown -= 0.25;

                if (material == Material.BARRIER) {
                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }

                if (inventory.getItem(slot) == null) {
                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }

                if (!inventory.getItem(slot).equals(item)
                        && (inventory.getItem(slot).getType() != Material.BARRIER
                        || inventory.getItem(slot).getAmount() != (int)Math.floor(currentCooldown) + 1)) {
                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }

                if (amount > 1) { // 아이템 개수가 1개 초과일 경우
                    item.setAmount(amount - 1);

                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }

                if (currentCooldown > 0) { // 현재 재사용 대기 시간이 0 초과일 경우
                    meta.setDisplayName("§f" + title);
                    item.setItemMeta(meta);
                    item.setType(Material.BARRIER);
                    item.setAmount((int)Math.ceil(currentCooldown));
                    inventory.setItem(slot, item);
                } else { // 현재 재사용 대기 시간이 0 이하일 경우
                    meta.setDisplayName(title);
                    item.setItemMeta(meta);
                    item.setType(material);
                    item.setDurability(durability);
                    item.setAmount(returnAmount);
                    inventory.setItem(slot, item);

                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }
            }

        }, 0, 5);

    }

    // 변이 진행 함수
    public void variationProgress(Player player, ItemStack item, int slot, double cooldown) {
        final int[] task = { -1 };

        task[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            double currentCooldown = cooldown + 0.25;
            Inventory inventory = player.getInventory();

            @Override
            public void run() {
                currentCooldown -= 0.25;

                // 해당 아이템 슬롯이 비어 있을 경우
                if (inventory.getItem(slot) == null) {
                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }

                if (!inventory.getItem(slot).equals(item)
                        && (inventory.getItem(slot).getType() != Material.MAGMA_CREAM
                        || inventory.getItem(slot).getAmount() != (int)Math.floor(currentCooldown) + 1)) {
                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }

                // 현재 변이 지연 시간이 0 초과일 경우
                if (currentCooldown > 0) {
                    item.setAmount((int)Math.ceil(currentCooldown));
                    inventory.setItem(slot, item);
                }
                // 현재 변이 지연 시간이 0 이하일 경우
                else {
                    item.setAmount(1);
                    inventory.setItem(slot, item);

                    // 플레이어가 사망한 상태일 경우
                    if (player.isDead()) {
                        Bukkit.getScheduler().cancelTask(task[0]);
                        return;
                    }

                    // 변이 레벨 상승
                    int currentVariationLevel = variables.getVariationLevel().get(player);
                    variables.getVariationLevel().put(player, currentVariationLevel + 1);

                    // 변이 효과 적용
                    methods.variationApply(player);

                    // 변이 조회기 갱신
                    ItemStack variationViewer = methods.getVariationViewer(player);
                    inventory.setItem(slot, variationViewer);

                    // 다음 변이 진행
                    int variationTime = variables.getVariationTime();
                    variationProgress(player, variationViewer, slot, variationTime);

                    Bukkit.getScheduler().cancelTask(task[0]);
                    return; // 함수 반환
                }
            }

        }, 0, 5);

    }

    // 변이 조회기 반환 함수
    public ItemStack getVariationViewer(Player player) {
        ItemStack variationViewer = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta variationViewerItemMeta = variationViewer.getItemMeta();

        int variationLevel = variables.getVariationLevel().get(player);

        variationViewerItemMeta.setDisplayName("§f변이 Lv." + variationLevel);

        boolean isHost = SelonZombieSurvival.manager.getHostList().contains(player)
                || SelonZombieSurvival.manager.getCarrierList().contains(player);

        double health = variationLevel * (10 * (isHost ? 1 : 0.5));
        double moveSpeed = variationLevel * (0.0075 * (isHost ? 1 : 0.5));

        ArrayList<String> variationViewerLore = new ArrayList<String>();
        variationViewerLore.add("");
        variationViewerLore.add("§f최대 체력: " + (variationLevel > 0 ? "§b" : "§7") + "+" + health);
        variationViewerLore.add("§f이동 속도: " + (variationLevel > 0 ? "§b" : "§7") + "+" + String.format("%.3f", moveSpeed));
        variationViewerItemMeta.setLore(variationViewerLore);

        variationViewer.setItemMeta(variationViewerItemMeta);

        return variationViewer;
    }

    // 변이 적용 함수
    public void variationApply(Player player) {
        double maxHealth = getMaxHealth(player);
        float moveSpeed = getMoveSpeed(player);

        int variationLevel = variables.getVariationLevel().get(player);

        boolean isHost = SelonZombieSurvival.manager.getHostList().contains(player)
                || SelonZombieSurvival.manager.getCarrierList().contains(player);

        boolean isCarrier = SelonZombieSurvival.manager.getCarrierList().contains(player);

        // 체력 조정
        double plusHealth = variationLevel * (10 * (isHost ? 1 : 0.5));
        maxHealth += plusHealth;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        double health = (isCarrier && (variationLevel == variables.getCarrierVariationLevel()))
                ? player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
                : Math.min(player.getHealth() + (10 * (isHost ? 1 : 0.5)), player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        if (!player.isDead()) {
            player.setHealth(health);
        }

        // 이동 속도
        moveSpeed += variationLevel * (0.0075 * (isHost ? 1 : 0.5));
        player.setWalkSpeed(moveSpeed);
    }



    // 최대 체력 반환 함수
    public double getMaxHealth(Player player) {
        double maxHealth = 20;
        if (SelonZombieSurvival.manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우
            String kind = variables.getSelectedClass().get(player);

            int maxHealthStat = kr.sizniss.data.Files.getClassStatus(player, kind, "최대_체력");
            maxHealth = 20 + maxHealthStat * 4;
        } else if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
            String type = variables.getSelectedType().get(player);

            boolean isHost = SelonZombieSurvival.manager.getHostList().contains(player)
                    || SelonZombieSurvival.manager.getCarrierList().contains(player);

            int maxHealthStat = kr.sizniss.data.Files.getTypeStatus(player, type, "최대_체력");
            maxHealth = 120 + maxHealthStat * 20;
        }

        return maxHealth;
    }
    
    // 이동 속도 반환 함수
    public float getMoveSpeed(Player player) {
        float moveSpeed = 0.22f;
        if (SelonZombieSurvival.manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우
            String kind = variables.getSelectedClass().get(player);

            int moveSpeedStat = kr.sizniss.data.Files.getClassStatus(player, kind, "이동_속도");
            moveSpeed = 0.22f + moveSpeedStat * 0.015f;
        } else if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
            String type = variables.getSelectedType().get(player);

            boolean isHost = SelonZombieSurvival.manager.getHostList().contains(player)
                    || SelonZombieSurvival.manager.getCarrierList().contains(player);

            int moveSpeedStat = kr.sizniss.data.Files.getTypeStatus(player, type, "이동_속도");
            moveSpeed = 0.25f + moveSpeedStat * 0.015f;
        }

        return moveSpeed;
    }

    public void potionApply(LivingEntity livingEntity, PotionEffectType potionEffectType, double duration, int level) {
        int intDuration = (int)(duration * 20);

        if (livingEntity.getPotionEffect(potionEffectType) != null
                && livingEntity.getPotionEffect(potionEffectType).getAmplifier() > level) {
            return; // 함수 반환
        }

        livingEntity.removePotionEffect(potionEffectType); // 포션 효과 초기화

        PotionEffect potion = null;
        if (potionEffectType == PotionEffectType.INVISIBILITY && SelonZombieSurvival.manager.getZombieList().contains(livingEntity)) {
            potion = new PotionEffect(potionEffectType, intDuration, level - 1, false, true);
        } else {
            potion = new PotionEffect(potionEffectType, intDuration, level - 1, true, false);
        }
        livingEntity.addPotionEffect(potion); // 포션 효과 부여

        if (potionEffectType == PotionEffectType.HEALTH_BOOST) {
            double currentHealth = livingEntity.getHealth();
            double currentMaxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double plusHealth = level * 4;
            currentHealth = Math.min(currentHealth + plusHealth, currentMaxHealth);
            if (!livingEntity.isDead()) {
                livingEntity.setHealth(currentHealth);
            }
        }

        if (livingEntity.getType() == EntityType.PLAYER
                && (potionEffectType == PotionEffectType.HEALTH_BOOST
                || potionEffectType == PotionEffectType.REGENERATION
                || potionEffectType == PotionEffectType.DAMAGE_RESISTANCE
                || potionEffectType == PotionEffectType.SPEED
                || potionEffectType == PotionEffectType.JUMP)) {

            Player player = (Player) livingEntity;

            final boolean wasHuman = SelonZombieSurvival.manager.getHumanList().contains(player);
            final boolean wasZombie = SelonZombieSurvival.manager.getZombieList().contains(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (SelonZombieSurvival.manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우
                    if (!wasHuman) { // 플레이어가 과거 인간이 아니었을 경우
                        return;
                    }

                    passiveApply(player);
                } else if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
                    if (!wasZombie) { // 플레이어가 과거 좀비가 아니었을 경우
                        return;
                    }

                    passiveApply(player);
                }
            }, intDuration);

        }
    }

    // 포션 효과 적용 함수
    public void potionApplyTest(Player player, PotionEffectType potionEffectType, double duration, int level) {
        int intDuration = (int)(duration * 20);

        ArrayList<PotionEffect> appliedPotionEffects = new ArrayList<PotionEffect>(); // 누적된 포션 효과
        for (PotionEffect potionEffect : variables.getAppliedPotionEffects().get(player)) {
            if (potionEffect.getType() != potionEffectType) { // 포션 효과가 다를 경우
                appliedPotionEffects.add(potionEffect); // 변경 없이 누적시킴
            }
            else { // 포션 효과가 같을 경우
                if (potionEffect.getDuration() > intDuration) { // 누적된 포션 효과의 지속 시간이 현재의 포션 효과의 지속 시간보다 더 길 경우
                    appliedPotionEffects.add(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() - intDuration, potionEffect.getAmplifier())); // 지속 시간 변경 후 누적시킴
                }
            }
        }
        variables.getAppliedPotionEffects().put(player, appliedPotionEffects);

        if (player.getPotionEffect(potionEffectType) != null
                && player.getPotionEffect(potionEffectType).getAmplifier() > level) { // 이미 적용되어 있는 포션 효과의 레벨이 더 높을 경우

            if (intDuration > player.getPotionEffect(potionEffectType).getDuration()) { // 이미 적용되어 있는 포션 효과의 지속 시간이 끝난 후에도, 현재의 포션 효과의 지속 시간이 남아 있을 경우
                PotionEffect potionEffect = new PotionEffect(potionEffectType, intDuration - player.getPotionEffect(potionEffectType).getDuration(), level); // 현재의 포션 효과
                appliedPotionEffects.add(potionEffect); // 현재의 포션 효과를 누적시킴

                variables.getAppliedPotionEffects().put(player, appliedPotionEffects);
            }

            return; // 함수 반환
        }

        player.removePotionEffect(potionEffectType); // 포션 효과 초기화

        PotionEffect potion = null;
        if (potionEffectType == PotionEffectType.INVISIBILITY && SelonZombieSurvival.manager.getZombieList().contains(player)) {
            potion = new PotionEffect(potionEffectType, intDuration, level - 1, false, true);
        } else {
            potion = new PotionEffect(potionEffectType, intDuration, level - 1, true, false);
        }
        player.addPotionEffect(potion); // 포션 효과 부여

        if (potionEffectType == PotionEffectType.HEALTH_BOOST) {
            double currentHealth = player.getHealth();
            double currentMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double plusHealth = level * 4;
            currentHealth = Math.min(currentHealth + plusHealth, currentMaxHealth);
            if (!player.isDead()) {
                player.setHealth(currentHealth);
            }
        }


        final boolean wasHuman = SelonZombieSurvival.manager.getHumanList().contains(player);
        final boolean wasZombie = SelonZombieSurvival.manager.getZombieList().contains(player);

        if (!variables.getAppliedPotionEffects().get(player).isEmpty()) { // 누적된 포션 효과가 있을 경우

        }

        if (potionEffectType == PotionEffectType.HEALTH_BOOST
                || potionEffectType == PotionEffectType.REGENERATION
                || potionEffectType == PotionEffectType.DAMAGE_RESISTANCE
                || potionEffectType == PotionEffectType.SPEED
                || potionEffectType == PotionEffectType.JUMP) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (SelonZombieSurvival.manager.getHumanList().contains(player)) { // 플레이어가 인간일 경우
                    if (!wasHuman) { // 플레이어가 과거 인간이 아니었을 경우
                        return;
                    }

                    passiveApply(player);
                } else if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우
                    if (!wasZombie) { // 플레이어가 과거 좀비가 아니었을 경우
                        return;
                    }

                    passiveApply(player);
                }
            }, intDuration);

        }
    }

    public void passiveApply(Player player) {
        double prevHealth = player.getHealth();
        double prevMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        int duration = Integer.MAX_VALUE;

        int healthBoostLevel = getPotionLevel(player, PotionEffectType.HEALTH_BOOST);
        int regenerationLevel = getPotionLevel(player, PotionEffectType.REGENERATION);
        int damageResistanceLevel = getPotionLevel(player, PotionEffectType.DAMAGE_RESISTANCE);
        int speedLevel = getPotionLevel(player, PotionEffectType.SPEED);
        int jumpLevel = getPotionLevel(player, PotionEffectType.JUMP);

        PotionEffect healthBoostPotion = new PotionEffect(PotionEffectType.HEALTH_BOOST, duration, healthBoostLevel - 1, true, false);
        PotionEffect regenerationPotion = new PotionEffect(PotionEffectType.REGENERATION, duration, regenerationLevel - 1, true, false);
        PotionEffect damagaResistancePotion = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, damageResistanceLevel - 1, true, false);
        PotionEffect speedPotion = new PotionEffect(PotionEffectType.SPEED, duration, speedLevel - 1, true, false);
        PotionEffect jumpPotion = new PotionEffect(PotionEffectType.JUMP, duration, jumpLevel - 1, true, false);

        if (healthBoostLevel > 0) {
            player.addPotionEffect(healthBoostPotion);
        }
        if (regenerationLevel > 0) {
            player.addPotionEffect(regenerationPotion);
        }
        if (damageResistanceLevel > 0) {
            player.addPotionEffect(damagaResistancePotion);
        }
        if (speedLevel > 0) {
            player.addPotionEffect(speedPotion);
        }
        if (jumpLevel > 0) {
            player.addPotionEffect(jumpPotion);
        }

        if (prevHealth == prevMaxHealth) {
            double currentMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            if (!player.isDead()) {
                player.setHealth(currentMaxHealth);
            }
        }
    }

    public int getPotionLevel(Player player, PotionEffectType potionEffectType) {
        int healthBoostLevel = 0;
        int regenerationLevel = 0;
        int damageResistanceLevel = 0;
        int speedLevel = 0;
        int jumpLevel = 0;

        // 플레이어가 인간일 경우
        if (SelonZombieSurvival.manager.getHumanList().contains(player)) {
            String kind = variables.getSelectedClass().get(player);

            healthBoostLevel = 0;
            regenerationLevel = 0;
            damageResistanceLevel = 0;
            speedLevel = 0;
            jumpLevel = 0;

            // 플레이어가 영웅일 경우
            if (SelonZombieSurvival.manager.getHeroList().contains(player)) {
                healthBoostLevel += 2;
                regenerationLevel += 1;
                damageResistanceLevel += 2;
                speedLevel += 2;
            }

            // 신입 유저 지원 효과
            int newUserLevel = getNewUserLevel(player);
            if (newUserLevel >= 1) {
                healthBoostLevel += 1;
            }
            if (newUserLevel >= 2) {
                regenerationLevel += 1;
            }
            if (newUserLevel >= 3) {
                speedLevel += 1;
            }

            int regenerationStat = kr.sizniss.data.Files.getClassStatus(player, kind, "재생");
            regenerationLevel += regenerationStat;
        }
        // 플레이어가 좀비일 경우
        else if (SelonZombieSurvival.manager.getZombieList().contains(player)) {
            String type = variables.getSelectedType().get(player);

            healthBoostLevel = 0;
            regenerationLevel = 3;
            damageResistanceLevel = 0;
            speedLevel = 0;
            jumpLevel = 0;

            // 플레이어가 숙주 혹은 보균자일 경우
            if (SelonZombieSurvival.manager.getHostList().contains(player)
                    ||SelonZombieSurvival.manager.getCarrierList().contains(player)) {
                healthBoostLevel += 25;
                regenerationLevel += 0;
                damageResistanceLevel += 1;
                speedLevel += 1;
                jumpLevel += 1;
            }

            // 신입 유저 지원 효과
            int newUserLevel = getNewUserLevel(player);
            if (newUserLevel >= 1) {
                healthBoostLevel += 5;
            }
            if (newUserLevel >= 2) {
                regenerationLevel += 1;
            }
            if (newUserLevel >= 3) {
                speedLevel += 1;
            }

            int regenerationStat = kr.sizniss.data.Files.getTypeStatus(player, type, "재생");
            regenerationLevel += regenerationStat;

            int jumpStat = kr.sizniss.data.Files.getTypeStatus(player, type, "점프_강화");
            jumpLevel += jumpStat;
        }

        switch (potionEffectType.getName()) {
            case "HEALTH_BOOST":
                return healthBoostLevel;
            case "REGENERATION":
                return regenerationLevel;
            case "DAMAGE_RESISTANCE":
                return damageResistanceLevel;
            case "SPEED":
                return speedLevel;
            case "JUMP":
                return jumpLevel;
            default:
                return 0;
        }
    }



    // 통계 전송 함수
    public void broadcastRecordLog() {
        JDA jda = variables.getJda();

        String message = "";

        String dateValue = new SimpleDateFormat("yyyy-MM-dd").format(Files.getRecordDate());

        int number;

        String[] teamRatioKey = new String[Files.getRecordTeamRatio().entrySet().size()];
        int[] teamRatioValue = new int[Files.getRecordTeamRatio().entrySet().size()];
        number = 0;
        for (Map.Entry<String, JsonElement> entry: Files.getRecordTeamRatio().entrySet()) {
            String key = entry.getKey();

            teamRatioKey[number] = key;
            teamRatioValue[number] = Files.getRecordTeamRatio(key);
            number++;
        }

        String[] mapRatioKey = new String[Files.getRecordMapRatio().entrySet().size()];
        int[][] mapRatioValue = new int[Files.getRecordMapRatio().entrySet().size()][2];
        number = 0;
        for (Map.Entry<String, JsonElement> entry: Files.getRecordMapRatio().entrySet()) {
            String key = entry.getKey();

            mapRatioKey[number] = key;
            mapRatioValue[number][0] = Files.getRecordMapRatio(key, "Human");
            mapRatioValue[number][1] = Files.getRecordMapRatio(key, "Zombie");
            number++;
        }

        String[] classCountKey = new String[Files.getRecordClassCount().entrySet().size()];
        int[] classCountValue = new int[Files.getRecordClassCount().entrySet().size()];
        number = 0;
        for(Map.Entry<String, JsonElement> entry: Files.getRecordClassCount().entrySet()) {
            String key = entry.getKey();

            classCountKey[number] = key;
            classCountValue[number] = Files.getRecordClassCount(key);
            number++;
        }

        String[] typeCountKey = new String[Files.getRecordTypeCount().entrySet().size()];
        int[] typeCountValue = new int[Files.getRecordTypeCount().entrySet().size()];
        number = 0;
        for(Map.Entry<String, JsonElement> entry: Files.getRecordTypeCount().entrySet()) {
            String key = entry.getKey();

            typeCountKey[number] = key;
            typeCountValue[number] = Files.getRecordTypeCount(key);
            number++;
        }

        String[] mainWeaponCountKey = new String[Files.getRecordMainWeaponCount().entrySet().size()];
        int[] mainWeaponCountValue = new int[Files.getRecordMainWeaponCount().entrySet().size()];
        number = 0;
        for(Map.Entry<String, JsonElement> entry: Files.getRecordMainWeaponCount().entrySet()) {
            String key = entry.getKey();

            mainWeaponCountKey[number] = key;
            mainWeaponCountValue[number] = Files.getRecordMainWeaponCount(key);
            number++;
        }

        String[] subWeaponCountKey = new String[Files.getRecordSubWeaponCount().entrySet().size()];
        int[] subWeaponCountValue = new int[Files.getRecordSubWeaponCount().entrySet().size()];
        number = 0;
        for(Map.Entry<String, JsonElement> entry: Files.getRecordSubWeaponCount().entrySet()) {
            String key = entry.getKey();

            subWeaponCountKey[number] = key;
            subWeaponCountValue[number] = Files.getRecordSubWeaponCount(key);
            number++;
        }

        String[] meleeWeaponCountKey = new String[Files.getRecordMeleeWeaponCount().entrySet().size()];
        int[] meleeWeaponCountValue = new int[Files.getRecordMeleeWeaponCount().entrySet().size()];
        number = 0;
        for(Map.Entry<String, JsonElement> entry: Files.getRecordMeleeWeaponCount().entrySet()) {
            String key = entry.getKey();

            meleeWeaponCountKey[number] = key;
            meleeWeaponCountValue[number] = Files.getRecordMeleeWeaponCount(key);
            number++;
        }

        String teamRatioStr = "";
        for (int i = 0; i < teamRatioKey.length; i++) {
            teamRatioStr = teamRatioStr + teamRatioKey[i] + ": " + teamRatioValue[i];
            if (i < teamRatioKey.length - 1) {
                teamRatioStr = teamRatioStr + "\n";
            }
        }

        String mapRatioStr = "";
        for (int i = 0; i < mapRatioKey.length; i++) {
            mapRatioStr = mapRatioStr + mapRatioKey[i] + ": " + mapRatioValue[i][0] + ":" + mapRatioValue[i][1] +
                    " (" + (int)Math.ceil(((double)mapRatioValue[i][0] / (double)(mapRatioValue[i][0] + mapRatioValue[i][1])) * 100) + "%)";
            if (i < mapRatioKey.length - 1) {
                mapRatioStr = mapRatioStr + "\n";
            }
        }

        String classCountStr = "";
        for (int i = 0; i < classCountKey.length; i++) {
            classCountStr = classCountStr + classCountKey[i] + ": " + classCountValue[i];
            if (i < classCountKey.length - 1) {
                classCountStr = classCountStr + "\n";
            }
        }

        String typeCountStr = "";
        for (int i = 0; i < typeCountKey.length; i++) {
            typeCountStr = typeCountStr + typeCountKey[i] + ": " + typeCountValue[i];
            if (i < typeCountKey.length - 1) {
                typeCountStr = typeCountStr + "\n";
            }
        }

        String mainWeaponCountStr = "";
        for (int i = 0; i < mainWeaponCountKey.length; i++) {
            mainWeaponCountStr = mainWeaponCountStr + mainWeaponCountKey[i] + ": " + mainWeaponCountValue[i];
            if (i < mainWeaponCountKey.length - 1) {
                mainWeaponCountStr = mainWeaponCountStr + "\n";
            }
        }

        String subWeaponCountStr = "";
        for (int i = 0; i < subWeaponCountKey.length; i++) {
            subWeaponCountStr = subWeaponCountStr + subWeaponCountKey[i] + ": " + subWeaponCountValue[i];
            if (i < subWeaponCountKey.length - 1) {
                subWeaponCountStr = subWeaponCountStr + "\n";
            }
        }

        String meleeWeaponCountStr = "";
        for (int i = 0; i < meleeWeaponCountKey.length; i++) {
            meleeWeaponCountStr = meleeWeaponCountStr + meleeWeaponCountKey[i] + ": " + meleeWeaponCountValue[i];
            if (i < meleeWeaponCountKey.length - 1) {
                meleeWeaponCountStr = meleeWeaponCountStr + "\n";
            }
        }

        message = "```\n" +
                "Date: " + dateValue + "\n\n" +
                "> Ratio\n" +
                ">> Team\n" +
                teamRatioStr + "\n\n" +
                ">> Map\n" +
                mapRatioStr + "\n\n" +
                "> Count\n" +
                classCountStr + "\n\n" +
                typeCountStr + "\n\n" +
                mainWeaponCountStr + "\n\n" +
                subWeaponCountStr + "\n\n" +
                meleeWeaponCountStr + "\n\n" +
                "```";

        jda.getTextChannelById("1170501659353415740").sendMessage(message).queue();
        jda.getTextChannelById("1177122562539524116").sendMessage(message).queue();
    }

    // 오류 감지 시스템
    public void errorDetectionSystem() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
            
            if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) { // 게임이 진행 중일 경우
                int timer = manager.getTimer();

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME
                            && manager.getTimer() == timer) { // 게임이 진행 중이고, 타이머의 시간이 동일할 경우

                        String serverTitle = variables.getServerTitle();
                        for (Player player : manager.getPlayerList()) {
                            int errorReward = variables.getErrorReward();

                            kr.sizniss.data.Files.addMoney(player, "Gold", errorReward);

                            player.sendMessage(serverTitle + " §f§l오류가 감지되어 게임을 재시작합니다. [ §a§l+" + errorReward + " §f§l]");
                        }
                        
                        // 게임 재시작
                        manager.gameStop();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { manager.gameStart(); }, 40L);
                        
                    }
                }, 40L);

            }
        }, 0L, 200L);
    }

    // 타이머 시스템
    public void timerSystem() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            String year = new SimpleDateFormat("yyyy").format(new Date());
            String month = new SimpleDateFormat("MM").format(new Date());
            String date = new SimpleDateFormat("dd").format(new Date());
            String hour = new SimpleDateFormat("HH").format(new Date());
            String minute = new SimpleDateFormat("mm").format(new Date());
            String second = new SimpleDateFormat("ss").format(new Date());
            String day = new SimpleDateFormat("E요일").format(new Date());

            String serverTitle = variables.getServerTitle();

            // 마인리스트 추천 유도 공지
            if (hour.equals("00") && minute.equals("00") && second.equals("00")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String minelistDate = formatter.format(Files.getMinelistDate());
                String currentDate = formatter.format(new Date());
                if (!minelistDate.equals(currentDate)) { // 같은 날이 아닐 경우
                    Files.setMinelistUser(new ArrayList<UUID>()); // 마인리스트 추천 유저 목록 초기화
                    Files.setMinelistDate(new Date()); // 날짜 갱신
                }
            } else if ((minute.equals("00") || minute.equals("10") || minute.equals("20")
                    || minute.equals("30") || minute.equals("40") || minute.equals("50"))
                    && second.equals("00")) {
                ArrayList<UUID> minelistUser = Files.getMinelistUser();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!minelistUser.contains(player.getUniqueId())) {
                        player.sendMessage(serverTitle + " §e§l마인리스트 §f§l추천을 하시면 보상을 받을 수 있습니다!");
                    }
                }
            }

            // 통계
            if (day.equals("월요일") && hour.equals("00") && minute.equals("00") && second.equals("00")) {

                broadcastRecordLog(); // 통계 전송

                // 통계 초기화
                Files.setRecordDate(new Date());

                // 진영 승리 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordTeamRatio().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordTeamRatio(key, 0);
                }

                // 맵 승리 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordMapRatio().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordMapRatio(key, "Human", 0);
                    Files.setRecordMapRatio(key, "Zombie", 0);
                }

                // 병과 플레이 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordClassCount().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordClassCount(key, 0);
                }

                // 타입 플레이 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordTypeCount().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordTypeCount(key, 0);
                }

                // 주 무기 플레이 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordMainWeaponCount().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordMainWeaponCount(key, 0);
                }

                // 보조 무기 플레이 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordSubWeaponCount().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordSubWeaponCount(key, 0);
                }

                // 근접 무기 플레이 횟수
                for(Map.Entry<String, JsonElement> entry: Files.getRecordMeleeWeaponCount().entrySet()) {
                    String key = entry.getKey();

                    Files.setRecordMeleeWeaponCount(key, 0);
                }

            } else if (day.equals("금요일") && hour.equals("00") && minute.equals("00") && second.equals("00")) {

                broadcastRecordLog(); // 통계 전송

            }

            // 서버 재시작
            if (hour.equals("02") && minute.equals("30") && second.equals("00")) {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(serverTitle + " §e§l30분 §f§l뒤 서버가 재시작됩니다.");
                }

            } else if (hour.equals("02") && minute.equals("55") && second.equals("00")) {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(serverTitle + " §e§l5분 §f§l뒤 서버가 재시작됩니다.");
                }

            } else if (hour.equals("02") && minute.equals("59") && second.equals("00")) {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(serverTitle + " §e§l1분 §f§l뒤 서버가 재시작됩니다.");
                }

            } else if (hour.equals("03") && minute.equals("00") && second.equals("00")) {

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

            }

        }, 0L, 20L);
    }

}
