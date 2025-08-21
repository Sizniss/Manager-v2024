package kr.sizniss.manager.abilities;

import com.shampaggon.crackshot.CSUtility;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import kr.sizniss.manager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import static kr.sizniss.manager.Manager.*;

public class Shock implements Listener {

    public int cooldown = 25;
    public double duration = 0.75;
    public int amount = 1;


    // 충격탄 사용
    @EventHandler
    private void WeaponShootEvent(WeaponShootEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("충격")) {

            Player player = event.getPlayer();
            int slot = player.getInventory().first(Material.ARROW);
            ItemStack item = player.getInventory().getItem(slot);

            final String type = variables.getSelectedType().get(player);

            // 재사용 대기 시간 진행
            int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
            int minusCooldown = cooldownStat * 2;
            int amountStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_사용_횟수");
            int plusAmount = amountStat;
            methods.cooldown(player, item, slot, cooldown - minusCooldown, amount + plusAmount);
        }
    }


    // 충격 피격
    @EventHandler
    private void ShockHitEvent(WeaponDamageEntityEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("충격")) {

            if (event.getVictim().getType() != EntityType.PLAYER) { // 피해자가 플레이어가 아닌 경우
                return; // 함수 반환
            }

            Player attacker = event.getPlayer();
            Player victim = (Player) event.getVictim();

            if (SelonZombieSurvival.manager.getHumanList().contains(victim)) { // 피해자가 인간일 경우

                int slot = 0;
                ItemStack item = victim.getInventory().getItem(slot);

                if (item.getType() == Material.BARRIER) { // 무기가 이미 봉인된 상태일 경우
                    return; // 함수 반환
                }


                // 무기가 '진압 방패' 혹은 '전기 방패' 혹은 '가시 방패'일 경우
                if (item.getItemMeta().getDisplayName().contains("진압 방패")
                        || item.getItemMeta().getDisplayName().contains("전기 방패")
                        || item.getItemMeta().getDisplayName().contains("가시 방패")) {

                    // 막고 있고, 정면 피해를 받을 경우
                    if (victim.isBlocking() && !methods.isBackstab(attacker, victim)) {
                        return;
                    }

                    methods.cooldown(victim, item, slot, duration); // 무기 봉인

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        if (SelonZombieSurvival.manager.getHumanList().contains(victim)) {
                            String selectedMainWeaponName = variables.getSelectedMainWeapon().get(victim);
                            ItemStack mainWeapon;
                            int mainWeaponLevel = kr.sizniss.data.Files.getMainWeaponLevel(victim, selectedMainWeaponName);
                            if (mainWeaponLevel == 0) {
                                mainWeapon = new CSUtility().generateWeapon(selectedMainWeaponName).clone();
                            } else {
                                mainWeapon = new CSUtility().generateWeapon("(+" + mainWeaponLevel + ")_" + selectedMainWeaponName).clone();
                            }
                            victim.getInventory().setItem(0, mainWeapon);
                        }
                    },  (int)(duration * 20));
                }
                else {
                    methods.cooldown(victim, item, slot, duration); // 무기 봉인
                }
                
            }

        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEvent(WeaponDamageEntityEvent event) {
        String weaponTitle = event.getWeaponTitle();
        if (weaponTitle.contains("충격")) {

            Player attacker = event.getPlayer();

            if (event.getVictim().getType() != EntityType.PLAYER) {
                return;
            }
            Player victim = (Player) event.getVictim();

            if (SelonZombieSurvival.manager.getZombieList().contains(attacker)) {
                event.setCancelled(false);
            }
        }
    }

}
