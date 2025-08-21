package kr.sizniss.manager;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import dselon.selonzombiesurvival.SelonZombieSurvival;
import dselon.selonzombiesurvival.customevents.*;
import kr.ohurjon.antihack.event.PlayerCpsEvent;
import kr.ohurjon.antihack.event.PlayerReachEvent;
import kr.sizniss.manager.abilities.*;
import kr.sizniss.manager.customevents.CustomDamageEntityEvent;
import kr.sizniss.manager.guis.MenuGUI;
import kr.sizniss.manager.maps.HuntingGround;
import net.dv8tion.jda.api.JDA;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.text.SimpleDateFormat;
import java.util.*;

import static kr.sizniss.manager.Manager.*;

public class Events implements Listener {

    private String serverTitle = Files.getServerTitle();
    private String serverMotd = Files.getServerMotd();

    // 플러그인 활성화 이벤트
    @EventHandler
    private void PluginEnableEvent(PluginEnableEvent event) {
        if (event.getPlugin().getName().equals("Manager")) {
            World world = Bukkit.getWorld("world");

            world.setGameRuleValue("announceAdvancements", "false"); // 도전과제 수행 알림 비활성화
            world.setGameRuleValue("keepInventory", "true"); // 인벤토리 보존 규칙 활성화
            world.setGameRuleValue("showDeathMessages", "false"); // 사망 메시지 보이기 규칙 비활성화
            world.setGameRuleValue("maxEntityCramming", "50"); // 엔티티의 최대 겹침 수 설정
            world.setGameRuleValue("doDaylightCycle", "false"); // 시간 변화 비활성화
            world.setGameRuleValue("doMobSpawning", "false"); // 몬스터 스폰 비활성화
            world.setGameRuleValue("doMobLoot", "false"); // 엔티티 사망 시 아이템 드롭 비활성화
            world.setGameRuleValue("mobGriefing", "false"); // 몬스터 블럭 파괴 비활성화
            world.setGameRuleValue("spectatorsGenerateChunks", "false"); // 관전자 청크 로드 비활성화
        }


        if (event.getPlugin().getName().equals("SelonZombieSurvival")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (String playerName : Files.getPlayingUser()) {
                    if (Bukkit.getPlayer(playerName) == null) {
                        continue;
                    }
                    Player player = Bukkit.getPlayer(playerName);

                    SelonZombieSurvival.manager.addPlayer(player);
                }
            }, 20L);
        }
    }

    // 플러그인 비활성화 이벤트
    @EventHandler(priority = EventPriority.LOW)
    private void PluginDisableEvent(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("SelonZombieSurvival")) {
            Files.setPlayingUser(new ArrayList<String>()); // 게임 중인 플레이어 목록 초기화

            for (Player playingPlayer : SelonZombieSurvival.manager.getPlayerList()) {
                Files.addPlayingUser(playingPlayer.getName());
            }
        }
    }

    @EventHandler
    private void ServerListPingEvent(ServerListPingEvent event) {
        event.setMotd("§f§l[ §b§lSIZNISS §f§l] §f" + serverMotd);
    }

    // 커맨드 입력 이벤트
    @EventHandler
    private void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] command = event.getMessage().split(" ");

        if (command[0].toLowerCase().contains("/minecraft")) { // 'minecraft' 명령어 금지
            if (!player.isOp()) {
                event.setCancelled(true);
            }
        } else if (command[0].toLowerCase().equals("/me")) { // 'me' 명령어 금지
            if (!player.isOp()) {
                event.setCancelled(true);
            }
        } else if (command[0].toLowerCase().contains("/msg")) { // 'msg' 명령어 금지
            if (!player.isOp()) {
                event.setCancelled(true);
            }
        } else if (command[0].toLowerCase().equals("/tell")) { // 'msg' 명령어 금지
            if (!player.isOp()) {
                event.setCancelled(true);
            }
        } else if (command[0].toLowerCase().equals("/tellraw")) { // 'msg' 명령어 금지
            if (!player.isOp()) {
                event.setCancelled(true);
            }
        }
    }

    // 플레이어 로그인 이벤트
    @EventHandler
    private void PlayerLoginEvent(PlayerLoginEvent event) {

        Player player = event.getPlayer();

        // 플레이어 이름이 'D_Selon'일 경우
        if (player.getName().equals("D_Selon")) {

            // 접속 차단
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§4§l해당 닉네임은 보안 정책에 위배됩니다!");

        }

    }

    // 플레이어 입장 이벤트
    @EventHandler
    private void PlayerJoinEvnet(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String message = serverTitle + " §6§l" + player.getName() + "§f§l님께서 서버에 입장하셨습니다.";

        Bukkit.getConsoleSender().sendMessage(message);
        event.setJoinMessage(message); // 입장 메시지 설정

        new MenuGUI(player); // MenuGUI 생성
        player.setMaximumNoDamageTicks(0); // 피해 지연 시간 제거
        player.setWalkSpeed(0.2f); // 이동 속도 변경

        kr.sizniss.data.Files.setLastDate(player, new Date());
        kr.sizniss.data.Files.addCount(player, 1);
        kr.sizniss.data.Files.setUserName(player, player.getName());
        kr.sizniss.data.Files.addUserNameList(player, player.getName());
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            kr.sizniss.data.Files.setIp(player, player.getAddress().getHostName());
            kr.sizniss.data.Files.addIpList(player, player.getAddress().getHostName());
        });

        if (kr.sizniss.data.Files.getCount(player) == 1) { // 첫 접속일 경우
            kr.sizniss.data.Files.getMoney(player);
            kr.sizniss.data.Files.getRecord(player);
            kr.sizniss.data.Files.getOption(player);
            kr.sizniss.data.Files.getStatus(player);
            kr.sizniss.data.Files.getPlayCount(player);
            kr.sizniss.data.Files.getWeapon(player);
            kr.sizniss.data.Files.getWeaponLevel(player);
            kr.sizniss.data.Files.getCodi(player);
            kr.sizniss.data.Files.getBox(player);
            kr.sizniss.data.Files.getBoxOpenCount(player);
            kr.sizniss.data.Files.getPiece(player);

            kr.sizniss.data.Files.setMainWeapon(player, "MP5", true);
            kr.sizniss.data.Files.setSubWeapon(player, "HK_USP", true);
            kr.sizniss.data.Files.setMeleeWeapon(player, "씰_나이프", true);

            kr.sizniss.data.Files.setBorderColor(player, "White", true);
            kr.sizniss.data.Files.setPrefixColor(player, "White", true);
            kr.sizniss.data.Files.setNickColor(player, "White", true);
            kr.sizniss.data.Files.setChatColor(player, "White", true);

            kr.sizniss.data.Files.addMoney(player, "Gold", 50000);
            kr.sizniss.data.Files.addBox(player, "Normal Weapon Box", 50); // 일반 무기 상자(x50) 추가
            kr.sizniss.data.Files.setMainWeaponLevel(player, "MP5", 2); // MP5 +2 강화
        }

        boolean isFirstJoin = methods.isFirstJoin(player);
        if (isFirstJoin) {
            player.teleport(new Location(Bukkit.getWorld("world"), 1779.5, 27, -441.5, 180, 0)); // 튜토리얼로 이동
        } else {
            player.teleport(dselon.selonzombiesurvival.Files.getLobbyLocation()); // 로비로 이동
        }
        int newUserLevel = methods.getNewUserLevel(player);
        if (!isFirstJoin
                && newUserLevel > 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                player.sendMessage("");
                player.sendMessage(serverTitle + " §f§l서버에 오신 것을 환영합니다!");
                player.sendMessage(serverTitle + " §f§l'§e/게임§f§l' 명령어를 통해 게임에 참여하실 수 있습니다!");
                player.sendTitle("§e/게임", "§f게임에 참여하거나 퇴장하실 수 있습니다!", 5, 100, 5);
            }, 1L);
        }

        // 게임 현황 로그 전송
        boolean isNewUser = methods.isNewUser(player);
        if (Bukkit.getServer().getPort() == 25565
                && isNewUser
                && !variables.isNewUserJoinLogDelayed()) {
            JDA jda = variables.getJda();
            String logMessage = "```\n" +
                    "※ 신입 유저 접속\n" +
                    "시간: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n" +
                    "인원: " + Bukkit.getOnlinePlayers().size() + "\n" +
                    "```";
            jda.getTextChannelById("819168473624215603").sendMessage(logMessage).queue();

            variables.setNewUserJoinLogDelayed(true);
            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
                variables.setNewUserJoinLogDelayed(false);
            }, variables.getNewUserJoinLogDelayTime() * 20L);
        }

    }

    @EventHandler
    private void hidePlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ArrayList<Player> vanishPlayers = variables.getVanishPlayers();

        if (!player.isOp()) { // 플레이어가 오피가 아닐 경우
            for (Player vanishPlayer : vanishPlayers) {
                player.hidePlayer(plugin, vanishPlayer);
            }
        }
    }

    @EventHandler
    private void showPlayer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArrayList<Player> vanishPlayers = variables.getVanishPlayers();

        if (vanishPlayers.contains(player)) {
            variables.getVanishPlayers().remove(player);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.equals(player) || onlinePlayer.isOp()) { // 대상이 자신이거나 오피일 경우
                    continue;
                }

                player.showPlayer(plugin, onlinePlayer); // 플레이어 모습 드러내기
            }

            player.setGameMode(GameMode.ADVENTURE); // 게임 모드 변경
        }


        if (!player.isOp()) { // 플레이어가 오피가 아닐 경우
            for (Player vanishPlayer : vanishPlayers) {
                player.showPlayer(plugin, vanishPlayer);
            }
        }
    }

    // 플레이어 퇴장 이벤트
    @EventHandler
    private void PlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String message = serverTitle + " §6§l" + player.getName() + "§f§l님께서 서버에서 퇴장하셨습니다.";

        event.setQuitMessage("");
        Bukkit.getConsoleSender().sendMessage(message);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.isOp()) {
                continue;
            }

            onlinePlayer.sendMessage(message); // 퇴장 메시지 설정
        }

        // 인벤토리 초기화
        for(int i = 0; i < 41; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR, 1));
        }

        // 포션 효과 초기화
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.setGameMode(GameMode.ADVENTURE); // 게임 모드 변경
    }

    // 플레이어 채팅 이벤트
    @EventHandler
    private void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String[] border = {"[-", "-]"};
        String prefix = PermissionsEx.getUser(player).getPrefix().replace("&", "§");
        String playername = player.getName();
        String message = player.hasPermission("manager.op") ? event.getMessage().replace("&", "§") : event.getMessage();

        if (prefix.equals("User")) {
            if (variables.getChatDelayTime().containsKey(player)) {
                double chatDelayTime = variables.getChatDelayTime().get(player);
                player.sendMessage(serverTitle + " §f§l채팅 속도가 너무 빠릅니다! [§7§l" + chatDelayTime + "초§f§l]");

                event.setCancelled(true);
                return;
            }

            int delayTime = 3;
            variables.getChatDelayTime().put(player, (double) delayTime);
            for (int i = 0; i <= delayTime * 10; i++) {
                final int number = i;

                if (number != delayTime * 10) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        variables.getChatDelayTime().put(player, (double) (delayTime * 10 - number) / 10);
                    }, number);
                } else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        variables.getChatDelayTime().remove(player);
                    }, number);
                }
            }
        }

        ChatColor borderColor;
        ChatColor prefixColor;
        ChatColor nickColor;
        ChatColor chatColor;

        borderColor = methods.getColor(kr.sizniss.data.Files.getOptionCodi(player, "BorderColor"));
        prefixColor = methods.getColor(kr.sizniss.data.Files.getOptionCodi(player, "PrefixColor"));
        nickColor = methods.getColor(kr.sizniss.data.Files.getOptionCodi(player, "NickColor"));
        chatColor = methods.getColor(kr.sizniss.data.Files.getOptionCodi(player, "ChatColor"));

        event.setFormat(borderColor + border[0] + prefixColor + prefix + borderColor + border[1] + " " + nickColor + playername + borderColor + " : " + chatColor + message.replace("%", "%%"));
        for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_LAVA_POP, 2.0f, 2.0f);
        }

        // 채팅 로그 전송
        JDA jda = variables.getJda();
        jda.getTextChannelById("1099973707058925588").sendMessage("[ " + player.getName() + " ] " + event.getMessage()).queue();
    }

    // 플레이어 이동 이벤트
    @EventHandler
    private void PlayerMoveEvent(PlayerMoveEvent event) {
        if (SelonZombieSurvival.manager.getPlayerList().size() != 3) {
            return;
        }
    }

    // 플레이어 사망 이벤트
    @EventHandler
    private void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY) {
            variables.getSelectedClass().put(player, kr.sizniss.data.Files.getOptionClass(player));
            variables.getSelectedType().put(player, kr.sizniss.data.Files.getOptionType(player));
            variables.getSelectedMainWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Main"));
            variables.getSelectedSubWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Sub"));
            variables.getSelectedMeleeWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Melee"));
        }

        // 쿨타임 및 변이 지연 저장
        if (SelonZombieSurvival.manager.getZombieList().contains(player)) {
            String type = variables.getSelectedType().get(player);

            ItemStack ability = player.getInventory().getItem(2);
            int abilityCooldown;
            if (ability.getType() != Material.BARRIER) { // 능력이 쿨타임 진행 중이 아닐 경우
                if (ability.getAmount() < methods.getTypeItemAmount(player)) { // 능력 개수가 타입 아이템 개수보다 적을 경우
                    int cooldownStat = kr.sizniss.data.Files.getTypeStatus(player, type, "능력_재사용_대기_시간");
                    int minusCooldown = cooldownStat * 2;

                    int cooldown;
                    switch (type) {
                        case "버서커":
                            cooldown = new Runaway().cooldown;
                            break;
                        case "바드":
                            cooldown = new Cure().cooldown;
                            break;
                        case "나이트":
                            cooldown = new Strengthen().cooldown;
                            break;
                        case "네크로맨서":
                            cooldown = new Undying().cooldown;
                            break;
                        case "워리어":
                            cooldown = new Reversal().cooldown;
                            break;
                        case "로그":
                            cooldown = new Flight().cooldown;
                            break;
                        case "어쌔신":
                            cooldown = new Hide().cooldown;
                            break;
                        case "레인저":
                            cooldown = new Shock().cooldown;
                            break;
                        case "버스터":
                            cooldown = new BioBomb().cooldown;
                            break;
                        case "서머너":
                            cooldown = new Pulling().cooldown;
                            break;
                        case "헌터":
                            cooldown = new Clairvoyance().cooldown;
                            break;
                        default:
                            cooldown = new Runaway().cooldown;
                            break;
                    }
                    abilityCooldown = cooldown - minusCooldown;
                } else { // 능력 개수가 타입 아이템 개수랑 같거나 많을 경우
                    abilityCooldown = 1;
                }
            } else { // 능력이 쿨타임 진행 중일 경우
                abilityCooldown = ability.getAmount();
            }

            ItemStack skill = player.getInventory().getItem(3);
            int skillCooldown = skill.getAmount();

            ItemStack variationViewer = player.getInventory().getItem(8);
            int variationDelay = variationViewer.getAmount();

            variables.getAbilityCooldown().put(player, abilityCooldown);
            variables.getSkillCooldown().put(player, skillCooldown);
            variables.getVariationDelay().put(player, variationDelay);
        }
    }

    // 자동 리스폰
    @EventHandler
    private void autoRespawn(PlayerDeathEvent event) {
        event.setDeathMessage("");
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.setDroppedExp(0);

        int deadPlayerCount = 0;
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isDead()) {
                deadPlayerCount++;
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { event.getEntity().spigot().respawn(); }, deadPlayerCount * 15); // 자동 부활
    }

    // 플레이어 리스폰 이벤트
    @EventHandler(priority = EventPriority.HIGH)
    private void PlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;

        // 플레이어가 게임 참여 중일 경우
        if (manager.getPlayerList().contains(player)) {
            dselon.selonzombiesurvival.Manager.GameProgress gameProgress = manager.getGameProgress();

            switch (gameProgress) {
                // 게임이 진행 중일 경우
                case GAME:
                    // 플레이어가 좀비일 경우
                    if (manager.getZombieList().contains(player)) {
                        // 흡수 효과 부여
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            methods.potionApply(player, PotionEffectType.ABSORPTION, 5, 25);
                        }, 2L);
                    }
                    break;
            }
        }
    }

    // 허기 레벨 변경 이벤트
    @EventHandler
    private void FoodLevelChangeEvent(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    // 엔티티 데미지 이벤트
    @EventHandler
    private void EntityDamageEvent(EntityDamageEvent event) {
        // 엔티티 타입이 플레이어가 아닐 경우
        if (event.getEntityType() != EntityType.PLAYER) {
            return; // 함수 반환
        }

        switch(event.getCause()) {
            case FALL: // 낙하 피해
                event.setCancelled(true); // 이벤트 취소
                break;
            case DROWNING: // 산소 고갈 피해
                event.setCancelled(true); // 이벤트 취소
                break;
            case CUSTOM: // 커스텀 공격 피해 (damage() 함수로 발생한 피해)
                break;
            case ENTITY_ATTACK: // 엔티티 공격 피해
                break;
            case ENTITY_EXPLOSION: // 엔티티 폭발 피해
                break;
            case ENTITY_SWEEP_ATTACK: // 엔티티 스윕 공격 피해
                break;
            case MAGIC: // 마법 피해
                break;
            case POISON: // 독 피해
                break;
            case PROJECTILE: // 투사체 피해
                break;
            default:
                double currentHealth = ((LivingEntity) event.getEntity()).getHealth();
                double damage = event.getFinalDamage();
                if (currentHealth <= damage) { // 현재 체력이 데미지보다 작거나 같을 경우

                    Player victim = (Player) event.getEntity();
                    methods.preventDeathAndInfected(event, victim);

                }
                break;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getDamager();
        
        // 사냥터 이용 중일 경우
        if (HuntingGround.playerList.contains(player)) {
            return;
        }

        if (variables.getCpsPlayers().contains(player)) {
            event.setCancelled(true);
        }
    }

    // PVP 방지 설정
    @EventHandler
    private void preventPVP(EntityDamageByEntityEvent event) {
        // 엔티티 타입이 플레이어가 아닐 경우
        if (event.getEntityType() != EntityType.PLAYER) {
            return; // 함수 반환
        }

        Player victim = (Player) event.getEntity(); // 피해자
        Player attacker = null; // 가해자

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) { // 데미지 사유가 엔티티 공격일 경우
            if (event.getDamager().getType() == EntityType.PLAYER) { // 공격자 엔티티 타입이 플레이어일 경우
                attacker = (Player) event.getDamager();
            }
            else if (event.getDamager().getType() == EntityType.AREA_EFFECT_CLOUD) { // 공격자 엔티티 타입이 구름일 경우
                if (event.getDamager().getMetadata("Shooter") == null) {
                    return;
                }
                attacker = Bukkit.getPlayer(event.getDamager().getMetadata("Shooter").get(0).asString());
            }
        }
        else if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE
                || event.getCause() == EntityDamageEvent.DamageCause.MAGIC) { // 데미지 사유가 투사체이거나 마법일 경우
            if (((Projectile) event.getDamager()).getShooter() instanceof Player) { // 공격자 엔티티 타입이 플레이어일 경우
                attacker = (Player) ((Projectile) event.getDamager()).getShooter();
            }
        }

        if (SelonZombieSurvival.manager.getPlayerList().contains(victim)) { // 피해자가 게임에 참여 중일 경우
            if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) { // 게임이 진행 중일 경우
                return; // 함수 반환
            }
        }
        
        if (attacker == null) { // 공격자가 없을 경우
            return; // 함수 반환
        }

        if (attacker.getGameMode() != GameMode.CREATIVE) { // 공격자의 게임 모드가 크리에이티브가 아닐 경우
            event.setCancelled(true);
        }
    }

    // 날씨 변경 이벤트
    @EventHandler
    private void WeatherChangeEvent(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    // 블럭 퍼지기 이벤트
    @EventHandler
    private void BlockSpreadEvent(BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    // 블럭 불 붙기 이벤트
    @EventHandler
    private void BlockBurnEvent(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    // 블럭 파괴 이벤트
    @EventHandler
    private void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("manager.op")) { // 권한이 있을 경우
            if (player.getGameMode() != GameMode.CREATIVE) { // 크리에이티브 모드일 경우
                event.setCancelled(true);
            }
        } else { // 권한이 없을 경우
            event.setCancelled(true);
        }
    }

    // 블럭 설치 이벤트
    @EventHandler
    private void BlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("manager.op")) { // 권한이 있을 경우
            if (player.getGameMode() != GameMode.CREATIVE) { // 크리에이티브 모드가 아닐 경우
                event.setCancelled(true);
            }
        } else { // 권한이 없을 경우
            event.setCancelled(true);
        }
    }

    // 블럭 폭발 이벤트
    @EventHandler(priority = EventPriority.HIGHEST)
    private void BlockExplodeEvent(BlockExplodeEvent event) {
        event.blockList().clear();
    }

    // 엔티티 폭발 이벤트
    @EventHandler(priority = EventPriority.HIGHEST)
    private void EntityExplodeEvent(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    // 플레이어 경험치 변경 이벤트
    @EventHandler
    private void PlayerExpChangeEvent(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }

    // 플레이어 아이템 내구도 손상 이벤트
    @EventHandler
    private void PlayerItemDamageEvent(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    // 플레이어 양동이 비우기 이벤트
    @EventHandler
    private void PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("manager.op")) { // 권한이 있을 경우
            if (player.getGameMode() != GameMode.CREATIVE) { // 크리에이티브 모드일 경우
                event.setCancelled(true);
            }
        } else { // 권한이 없을 경우
            event.setCancelled(true);
        }
    }

    // 플레이어 양동이 채우기 이벤트
    @EventHandler
    private void PlayerBucketFillEvent(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("manager.op")) { // 권한이 있을 경우
            if (player.getGameMode() != GameMode.CREATIVE) { // 크리에이티브 모드일 경우
                event.setCancelled(true);
            }
        } else { // 권한이 없을 경우
            event.setCancelled(true);
        }
    }

    // 플레이어 상호작용 이벤트
    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) { // 블럭을 우클릭하였을 경우
            Player player = event.getPlayer();

            dselon.selonzombiesurvival.Manager manager = dselon.selonzombiesurvival.SelonZombieSurvival.manager;
            if (manager.getPlayerList().contains(player)) { // 게임에 참여 중일 경우
                if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.IDLE
                        || manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.WAITING) { // 게임 진행 상태가 운휴 중이거나 대기 중일 경우
                    event.setCancelled(true);
                } else if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY
                        || manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME
                        || manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.ENDING) { // 게임 진행 상태가 준비 중이거나 게임 중이거나 종료 중일 경우
                    
                    if (event.getClickedBlock().getType() == Material.FLOWER_POT
                            || event.getClickedBlock().getType() == Material.BED_BLOCK
                            || event.getClickedBlock().getType() == Material.WORKBENCH
                            || event.getClickedBlock().getType() == Material.FURNACE
                            || event.getClickedBlock().getType() == Material.ANVIL
                            || event.getClickedBlock().getType() == Material.CHEST
                            || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                        event.setCancelled(true);
                    }
                    
                }
            } else { // 게임에 참여 중이 아닐 경우
                if (player.hasPermission("manager.op")) { // 권한이 있을 경우
                    if (player.getGameMode() != GameMode.CREATIVE) { // 크리에이티브 모드가 아닐 경우
                        event.setCancelled(true);
                    }
                } else { // 권한이 없을 경우
                    event.setCancelled(true);
                }
            }
        }
    }

    // 플레이어 손 바꾸기 이벤트
    @EventHandler
    private void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    // 플레이어 아이템 버리기 이벤트
    @EventHandler
    private void PlayerDropItemEvent(PlayerDropItemEvent event) {
        if (event.getItemDrop() == null) {
            return;
        }

        if (event.getItemDrop().getItemStack() == null) {
            return;
        }
        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType() == Material.IRON_INGOT) {
            Player player = event.getPlayer();
            PlayerInventory inventory = (PlayerInventory) player.getInventory();

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (int i = 0; i < 9; i++) {
                    if (i == inventory.getHeldItemSlot()) {
                        continue;
                    }

                    if (inventory.getItem(i) == null) {
                        continue;
                    }
                    ItemStack slotItem = inventory.getItem(i);

                    if (slotItem.getType() == Material.IRON_INGOT) {
                        inventory.setItem(i, new ItemStack(Material.AIR));
                    }
                }
            }, 0L);
        }
    }


    // 무기 데미지 엔티티 이벤트
    @EventHandler(priority = EventPriority.LOW)
    private void WeaponDamageEntityEventLow(WeaponDamageEntityEvent event) {
        if (event.getDamager() == null) { // 공격자 엔티티가 없을 경우
            return;
        }

        // 피해자 타입이 플레이어가 아닐 경우
        if (event.getVictim().getType() != EntityType.PLAYER) {
            return; // 함수 반환
        }

        Player victim = (Player) event.getVictim(); // 피해자
        Player attacker = event.getPlayer(); // 가해자


        if (event.getDamager().getType() == EntityType.PRIMED_TNT) { // 공격자 엔티티가 TNT일 경우
            if (SelonZombieSurvival.manager.getPlayerList().contains(victim)) { // 피해자가 게임에 참여 중일 경우
                if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) { // 게임이 진행 중일 경우
                    return; // 함수 반환
                }
            }

            if (attacker.getGameMode() != GameMode.CREATIVE) { // 공격자의 게임 모드가 크리에이티브가 아닐 경우
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void WeaponDamageEntityEventHigh(WeaponDamageEntityEvent event) {
        if (event.getDamager() == null) { // 공격자 엔티티가 없을 경우
            return;
        }

        // 피해자 타입이 플레이어가 아닐 경우
        if (event.getVictim().getType() != EntityType.PLAYER) {
            return; // 함수 반환
        }

        Player victim = (Player) event.getVictim(); // 피해자
        Player attacker = event.getPlayer(); // 가해자


        if (SelonZombieSurvival.manager.getZombieList().contains(attacker)
                || SelonZombieSurvival.manager.getHumanList().contains(victim)) { // 가해자가 좀비이거나 혹은 피해자가 인간일 경우
            event.setDamage(0);

            return;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void CustomDamageEntityEvent(CustomDamageEntityEvent event) {
        // 공격자 엔티티가 없을 경우
        if (event.getAttacker() == null) {
            return;
        }

        // 공격자 타입이 플레이어가 아닐 경우
        if (event.getAttacker().getType() != EntityType.PLAYER) {
            return;
        }

        // 피해자 타입이 플레이어가 아닐 경우
        if (event.getVictim().getType() != EntityType.PLAYER) {
            return; // 함수 반환
        }

        Player victim = (Player) event.getVictim(); // 피해자
        Player attacker = (Player) event.getAttacker(); // 가해자

        if (SelonZombieSurvival.manager.getPlayerList().contains(victim)) { // 피해자가 게임에 참여 중일 경우
            if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) { // 게임이 진행 중일 경우
                return; // 함수 반환
            }
        }

        if (attacker.getGameMode() != GameMode.CREATIVE) { // 공격자의 게임 모드가 크리에이티브가 아닐 경우
            event.setCancelled(true);
        }
    }


    // 플레이어 CPS 이벤트
    @EventHandler
    private void PlayerCpsEvent(PlayerCpsEvent event) {
        Player player = event.getPlayer();

        if (!variables.getCpsPlayers().contains(player)) {
            variables.getCpsPlayers().add(player);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                variables.getCpsPlayers().remove(player);
            }, 100L);
        }
    }

    // 플레이어 리치 이벤트
    @EventHandler
    private void PlayerReachEvent(PlayerReachEvent event) {
        variables.getJda().getTextChannelById("813083729567809568").sendMessage(event.getPlayer().getName() + "님의 Reach: " + String.format("%.2f", event.getReach())).queue();
    }


    // 로비 이동 이벤트
    @EventHandler
    private void PlayerMoveLobbyEvent(PlayerMoveLobbyEvent event) {
        Player player = event.getPlayer();

        if (player.isDead()) { // 플레이어가 죽어있는 상태일 경우
            player.spigot().respawn(); // 강제 부활
        }
    }

    // 맵 이동 이벤트
    @EventHandler
    private void PlayerMoveMapEvent(PlayerMoveMapEvent event) {
        Player player = event.getPlayer();

        if (player.isDead()) { // 플레이어가 죽어있는 상태일 경우
            player.spigot().respawn(); // 강제 부활
        }
    }

    // 기본 변환 이벤트
    @EventHandler
    private void ConvertDefaultEvent(ConvertDefaultEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();

        if (player.isDead()) { // 플레이어가 죽어있는 상태일 경우
            player.spigot().respawn(); // 강제 부활
        }

        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
        }
        player.getInventory().setItem(40, new ItemStack(Material.AIR));
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().setFoodLevel(20);
        if (!event.getPlayer().isDead()) {
            event.getPlayer().setHealth(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()); // 체력 초기화
        }

        if (event.getPlayer().isOp()) { // 플레이어가 오피일 경우
            Bukkit.dispatchCommand(event.getPlayer(), "ConvertDefault"); // 명령어 실행
        } else { // 플레이어가 오피가 아닐 경우
            event.getPlayer().setOp(true); // 오피 권환 지급
            Bukkit.dispatchCommand(event.getPlayer(), "ConvertDefault"); // 명령어 실행
            event.getPlayer().setOp(false); // 오피 권환 회수
        }
    }

    // 인간 변환 이벤트
    @EventHandler
    private void ConvertHumanEvent(ConvertHumanEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();

        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
        }
        player.getInventory().setItem(40, new ItemStack(Material.AIR));
        ItemStack helmet = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("0"));
        helmet.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName("§9생존자");
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        helmet.setItemMeta(helmetMeta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

        if (player.isOp()) { // 플레이어가 오피일 경우
            Bukkit.dispatchCommand(player, "ConvertHuman"); // 명령어 실행
        } else { // 플레이어가 오피가 아닐 경우
            player.setOp(true); // 오피 권환 지급
            Bukkit.dispatchCommand(player, "ConvertHuman"); // 명령어 실행
            player.setOp(false); // 오피 권환 회수
        }
    }

    // 좀비 변환 이벤트
    @EventHandler
    private void ConvertZombieEvent(ConvertZombieEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();

        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
        }
        player.getInventory().setItem(40, new ItemStack(Material.AIR));
        ItemStack helmet = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("0"));
        helmet.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName("§c감염자");
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        helmet.setItemMeta(helmetMeta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

        if (player.isOp()) { // 플레이어가 오피일 경우
            Bukkit.dispatchCommand(player, "ConvertZombie"); // 명령어 실행
        } else { // 플레이어가 오피가 아닐 경우
            player.setOp(true); // 오피 권환 지급
            Bukkit.dispatchCommand(player, "ConvertZombie"); // 명령어 실행
            player.setOp(false); // 오피 권환 회수
        }
    }

    // 숙주 선정 이벤트
    @EventHandler
    private void SelectHostEvent(SelectHostEvent event) {
        ArrayList<Player> prevHumanList = event.getHumanList();

        ArrayList<Player> newHumanList = new ArrayList<Player>();
        for(Player player : prevHumanList) {
            int win = kr.sizniss.data.Files.getRecord(player, "Win");
            int lose = kr.sizniss.data.Files.getRecord(player, "Lose");
            int kill = kr.sizniss.data.Files.getRecord(player, "Kill");
            // 신입 유저일 경우
            if (win + lose <= 99 && kill <= 99) {
                newHumanList.add(player);
            }
            // 일반 유저일 경우
            else {
                newHumanList.add(player);
                newHumanList.add(player);
            }
        }

        event.setHumanList(newHumanList);
    }

    // 보균자 선정 이벤트
    @EventHandler
    private void SelectCarrierEvent(SelectCarrierEvent event) {
        ArrayList<Player> prevHumanList = event.getHumanList();

        ArrayList<Player> newHumanList = new ArrayList<Player>();
        for(Player player : prevHumanList) {
            int win = kr.sizniss.data.Files.getRecord(player, "Win");
            int lose = kr.sizniss.data.Files.getRecord(player, "Lose");
            int kill = kr.sizniss.data.Files.getRecord(player, "Kill");
            // 신입 유저일 경우
            if (win + lose <= 99 && kill <= 99) {
                newHumanList.add(player);
            }
            // 일반 유저일 경우
            else {
                newHumanList.add(player);
                newHumanList.add(player);
            }
        }

        event.setHumanList(newHumanList);
    }

    // 보균자 팀 변경 이벤트
    @EventHandler
    private void TeamChangeCarrierEvent(TeamChangeCarrierEvent event) {
        Player carrier = event.getCarrier();

        int carrierVariationLevel = variables.getCarrierVariationLevel();

        variables.getVariationLevel().put(carrier, carrierVariationLevel); // 보균자 변이 레벨 초기화
        variables.getKillCount().put(carrier, 0); // 보균자 킬 카운트 초기화
        variables.getDeathCount().put(carrier, 0); // 보균자 데스 카운트 초기화
    }

    // 감염자를 보균자로 변경 이벤트
    @EventHandler
    private void InfecteeChangeCarrierEvent(InfecteeChangeCarrierEvent event) {
        Player carrier = event.getCarrier();

        int carrierVariationLevel = variables.getCarrierVariationLevel();

        variables.getVariationLevel().put(carrier, carrierVariationLevel); // 보균자 변이 레벨 초기화
    }

    // 맵 선정 이벤트
    @EventHandler
    private void SelectMapEvent(SelectMapEvent event) {
        int[] needCount = new int[2];
        int playerCount = SelonZombieSurvival.manager.getPlayerList().size();

        String mapName = event.getMapName();
        switch (mapName) {
            case "이탈리아":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "마지막 도시":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "뱀파이어 런던":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "밀리샤":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "더스트2":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "뉴크":
                needCount[0] = 10;
                needCount[1] = 50;
                break;
            case "어썰트":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "G큐브":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "747":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "이스테이트":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "오래된 공장":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "유람선":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "시장":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "위장":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "어비스3":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "베네치아A":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "전초 기지":
                needCount[0] = 45;
                needCount[1] = 50;
                break;
            case "고대 유적":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "데저트 빌리지":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "브리튼":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "공장":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "더스트":
                needCount[0] = 10;
                needCount[1] = 50;
                break;
            case "아오오니 저택":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "다크 시티":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "유적지":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "쥐구멍":
                needCount[0] = 10;
                needCount[1] = 50;
                break;
            case "베네치아B":
                needCount[0] = 10;
                needCount[1] = 50;
                break;
            case "생존캠프":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "버려진 보급고":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "스쿼저":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "하이든":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "데스하우스":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "교회":
                needCount[0] = 10;
                needCount[1] = 50;
                break;
            case "옥상":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "연구소":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "박스 창고":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "사막 시장":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "병원":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "프리즌":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            case "성당":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "유령 도시A":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "유령 도시B":
                needCount[0] = 5;
                needCount[1] = 50;
                break;
            case "지하 주차장":
                needCount[0] = 0;
                needCount[1] = 15;
                break;
            default:
                needCount[0] = 0;
                needCount[1] = 50;
                break;
        }

        if (playerCount < needCount[0] || playerCount >= needCount[1]) {
            event.setCancelled(true);
        }
    }

    // 보급 투하 이벤트
    @EventHandler
    private void SupplyDropEvent(SupplyDropEvent event) {
        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
        if (manager.getPlayerList().size() < 4) { // 플레이어 인원이 4명 미만일 경우
            event.setCancelled(true);
        }
    }

    // 보급 획득 이벤트
    @EventHandler
    private void SupplyAcquireEvent(SupplyAcquireEvent event) {
        Player player = event.getPlayer();

        int acquiredSupplyCount = variables.getAcquiredSupplyCount().get(player);
        if (acquiredSupplyCount >= 3) { // 보급을 이미 획득했을 경우
            player.sendMessage(serverTitle + " §9§l보급§f§l은 1라운드에 3번만 획득 가능합니다!");
            event.setCancelled(true);

            return;
        }
        variables.getAcquiredSupplyCount().put(player, acquiredSupplyCount + 1); // 보급 획득 여부 변경
    }

    // 타이머 이벤트
    @EventHandler
    private void TimerEvent(TimerEvent event) {
        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
        if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY) { // 준비 중일 경우

            if (event.getTimer() == 5) { // 타이머가 5초 남았을 경우
                for (Player player : manager.getHumanList()) {
                    player.getInventory().setItem(4, new ItemStack(Material.AIR)); // 사약 제거
                }
            }

        } else if (manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) { // 게임 중일 경우
            
            if (event.getTimer() == manager.getRoundTime()) { // 타이머가 라운드 최대 시간일 경우
                for (Player player : manager.getHumanList()) {
                    ItemStack classItem = methods.getClassItem(variables.getSelectedClass().get(player)).clone();
                    int itemAmount = methods.getClassItemAmount(player);
                    classItem.setAmount(itemAmount);

                    player.getInventory().setItem(4, classItem); // 병과 아이템 지급
                }
            } else if (event.getTimer() == 0) { // 타이머가 0초일 경우
                if (manager.getMapName().equals("사막 시장")
                        || manager.getMapName().equals("병원")) { // 맵이 좀비 탈출 모드일 경우
                    manager.setZombieWin(true); // 좀비 승리
                }
            }

            int humanCount = manager.getHumanList().size();
            int heroCount = manager.getHeroList().size();

            if (humanCount == heroCount) { // 인간 수와 영웅 수가 같을 경우
                manager.setZombieWin(true); // 좀비 승리 함수 호출
            }
            
        }
    }

    // 라운드 시작 이벤트
    @EventHandler
    private void RoundStartEvent(RoundStartEvent event) {
        variables.setSelectedClass(new HashMap<Player, String>()); // 선택된 병과 초기화
        variables.setSelectedType(new HashMap<Player, String>()); // 선택된 타입 초기화
        variables.setSelectedMainWeapon(new HashMap<Player, String>()); // 선택된 주 무기 초기화
        variables.setSelectedSubWeapon(new HashMap<Player, String>()); // 선택된 보조 무기 초기화
        variables.setSelectedMeleeWeapon(new HashMap<Player, String>()); // 선택된 근접 무기 초기화

        variables.setAbilityCooldown(new HashMap<Player, Integer>()); // 능력 재사용 대기 시간 초기화
        variables.setSkillCooldown(new HashMap<Player, Integer>()); // 도약 재사용 대기 시간 초기화

        variables.setVariationDelay(new HashMap<Player, Integer>()); // 변이 지연 초기화
        variables.setVariationLevel(new HashMap<Player, Integer>()); // 변이 레벨 초기화
        variables.setKillCount(new HashMap<Player, Integer>()); // 킬 카운트 초기화
        variables.setDeathCount(new HashMap<Player, Integer>()); // 데스 카운트 초기화
        variables.setAcquiredSupplyCount(new HashMap<Player, Integer>()); // 보급 획득 여부 초기화

        for (Player player : SelonZombieSurvival.manager.getPlayerList()) {
            variables.getSelectedClass().put(player, kr.sizniss.data.Files.getOptionClass(player));
            variables.getSelectedType().put(player, kr.sizniss.data.Files.getOptionType(player));
            variables.getSelectedMainWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Main"));
            variables.getSelectedSubWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Sub"));
            variables.getSelectedMeleeWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Melee"));

            variables.getAbilityCooldown().put(player, 0);
            variables.getSkillCooldown().put(player, 0);

            variables.getVariationDelay().put(player, variables.getVariationTime());
            variables.getVariationLevel().put(player, 0);
            variables.getKillCount().put(player, 0);
            variables.getDeathCount().put(player, 0);
            variables.getAcquiredSupplyCount().put(player, 0);
        }

        // 게임 현황 로그 전송
        if (Bukkit.getServer().getPort() == 25565) { // 포트 번호가 25565일 경우
            JDA jda = variables.getJda();
            String message = "```\n" +
                    "※ 게임 진행 현황\n" +
                    "시간: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n" +
                    "맵: " + SelonZombieSurvival.manager.getMapName() + "\n" +
                    "인원: " + SelonZombieSurvival.manager.getPlayerList().size() + "\n" +
                    "```";
            jda.getTextChannelById("819168473624215603").sendMessage(message).queue();
        }

        // 조언 메시지
        ArrayList<String> tipList = variables.getTipList();
        int randomNumber = (int)(Math.random() * tipList.size());
        for (Player player : SelonZombieSurvival.manager.getPlayerList()) {
            player.sendMessage("");
            player.sendMessage(tipList.get(randomNumber));
            player.sendMessage("");
        }
    }

    // 라운드 종료 이벤트
    @EventHandler
    private void RoundEndEvent(RoundEndEvent event) {

        // 게임에 신입 유저가 있을 경우
        boolean existNewUser = false;
        for (Player player : SelonZombieSurvival.manager.getPlayerList()) {
            if (methods.isNewUser(player)) {
                existNewUser = true;
                break;
            }
        }

        for (Player player : SelonZombieSurvival.manager.getPlayerList()) {

            String kind = variables.getSelectedClass().get(player);
            String type = variables.getSelectedType().get(player);
            String mainWeapon = variables.getSelectedMainWeapon().get(player);
            String subWeapon = variables.getSelectedSubWeapon().get(player);
            String meleeWeapon = variables.getSelectedMeleeWeapon().get(player);

            // 게임에 신입 유저가 있을 경우
            if (existNewUser) {
                int newUserBonus = variables.getNewUserBonusReward();
                kr.sizniss.data.Files.addMoney(player, "Diamond", newUserBonus);
                player.sendMessage(serverTitle + " §e§l신입 유저 §f§l플레이 보너스 [ §b§l+" + newUserBonus + "D §f§l]");
            }

            // 플레이어 인원 수가 4명 이상이거나 신입 유저일 경우
            if (true /* SelonZombieSurvival.manager.getPlayerList().size() >= 4
                    || methods.isNewUser(player) */) {

                // 경험치 증가
                kr.sizniss.data.Files.addClassPlayCount(player, kind, 1);
                kr.sizniss.data.Files.addTypePlayCount(player, type, 1);
                kr.sizniss.data.Files.addMainWeaponPlayCount(player, mainWeapon, 1);
                kr.sizniss.data.Files.addSubWeaponPlayCount(player, subWeapon, 1);
                kr.sizniss.data.Files.addMeleeWeaponPlayCount(player, meleeWeapon, 1);

                int stackInteger;

                // int classPlayCount = kr.sizniss.data.Files.getClassPlayCount(player, kind);
                int classPlayCount = 0;
                for (String kinds : variables.getClassName()) {
                    classPlayCount += kr.sizniss.data.Files.getClassPlayCount(player, kinds);
                }
                ArrayList<Integer> classExperience = variables.getClassExperience();
                stackInteger = 0;
                for (int i = 0; i < classExperience.size(); i++) {
                    stackInteger += classExperience.get(i);
                    if (classPlayCount == stackInteger) { // 레벨 상승
                        // player.sendMessage(serverTitle + " §9§l" + kind + " §f§l병과의 레벨이 올랐습니다!");
                        // player.sendTitle("§9" + kind + " §f병과의 레벨이 올랐습니다!", "§f'§d/병과§f'에서 능력치를 분배해 보세요!", 5, 100, 5);
                        player.sendMessage(serverTitle + " §9§l병과§f§l의 레벨이 올랐습니다!");
                        player.sendTitle("§9병과§f의 레벨이 올랐습니다!", "§f'§d/병과§f'에서 능력치를 분배해 보세요!", 5, 100, 5);
                        break;
                    }
                }

                // int typePlayCount = kr.sizniss.data.Files.getTypePlayCount(player, type);
                int typePlayCount = 0;
                for (String types : variables.getTypeName()) {
                    typePlayCount += kr.sizniss.data.Files.getTypePlayCount(player, types);
                }
                ArrayList<Integer> typeExperience = variables.getTypeExperience();
                stackInteger = 0;
                for (int i = 0; i < typeExperience.size(); i++) {
                    stackInteger += typeExperience.get(i);
                    if (typePlayCount == stackInteger) { // 레벨 상승
                        // player.sendMessage(serverTitle + " §c§l" + type + " §f§l타입의 레벨이 올랐습니다!");
                        // player.sendTitle("§c" + type + " §f타입의 레벨이 올랐습니다!", "§f'§d/타입§f'에서 능력치를 분배해 보세요!", 5, 100, 5);
                        player.sendMessage(serverTitle + " §c§l타입§f§l의 레벨이 올랐습니다!");
                        player.sendTitle("§c타입§f의 레벨이 올랐습니다!", "§f'§d/타입§f'에서 능력치를 분배해 보세요!", 5, 100, 5);
                        break;
                    }
                }

            }

            // 플레이어 인원 수가 4명 이상일 경우
            if (true /* SelonZombieSurvival.manager.getPlayerList().size() >= 4 */) {

                // 통계 기록
                Files.addRecordClassCount(kind, 1);
                Files.addRecordTypeCount(type, 1);
                Files.addRecordMainWeaponCount(mainWeapon, 1);
                Files.addRecordSubWeaponCount(subWeapon, 1);
                Files.addRecordMeleeWeaponCount(meleeWeapon, 1);

            }

        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            for (Player player : SelonZombieSurvival.manager.getPlayerList()) {
                if (methods.isNewUser(player)) {
                    player.sendMessage("");
                    player.sendMessage(serverTitle + " §f§l골드를 획득하였습니다!");
                    player.sendMessage(serverTitle + " §f§l'§e/상점§f§l' 명령어를 통해 무기 상자를 구입하실 수 있습니다!");
                    player.sendTitle("§e/상점", "§f무기 상자를 구입하실 수 있습니다!", 5, 100, 5);
                }
            }
        }, 120L);


        // 이벤트 코드
        /*
        if (SelonZombieSurvival.manager.getPlayerList().size() >= 4) { // 플레이어 인원 수가 4명 이상일 경우
            for (Player player : SelonZombieSurvival.manager.getPlayerList()) {
                Files.addEventCount(player, 1);

                int eventCount = Files.getEventCount(player);
                int intervalCount = 10;
                int maxCount = 5;
                if (eventCount <= intervalCount * maxCount) { // 이벤트 카운트가 최대 횟수 이하일 경우

                    String playEvent = "3*1";
                    String playEventDisplay = "§d§l삼일절";

                    if (eventCount % intervalCount == 0) { // 이벤트 카운트가 간격 수의 배수일 경우

                        // 보상 지급
                        String product = "Premium Weapon Box";
                        String productDisplay = "§f고급 무기 상자";

                        int amount = 1;

                        kr.sizniss.data.Files.addBox(player, product, 1); // 상자 지급

                        player.sendMessage(serverTitle + " " + playEventDisplay + " §f§l플레이 이벤트 보상 획득! (§7§l" + eventCount + "§f§l/§7§l" + (((eventCount + intervalCount - 1) / intervalCount) * intervalCount) + "§f§l)");
                        Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Clear " + playEvent + " event(" + eventCount + ")]");

                        player.sendMessage(serverTitle + " §f§l'" + productDisplay + "(x" + amount + ")§f§l'을(를) 획득하셨습니다.");
                        Bukkit.getConsoleSender().sendMessage("[" + player.getName() + ": Get " + product + "(x" + amount + ")]");

                    } else { // 이벤트 카운트가 간격 수의 배수가 아닐 경우
                        player.sendMessage(serverTitle + " " + playEventDisplay + " §f§l플레이 이벤트 진행 중! (§7§l" + eventCount + "§f§l/§7§l" + (((eventCount + intervalCount - 1) / intervalCount) * intervalCount) + "§f§l)");
                    }
                }
            }
        }
         */

    }

    // 인간 승리 이벤트
    @EventHandler
    private void HumanWinEvent(HumanWinEvent event) {
        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
        if (manager.getPlayerList().size() >= 4) { // 플레이어 인원 수가 4명 이상일 경우
            Files.addRecordTeamRatio("Human", 1);
            Files.addRecordMapRatio(manager.getMapName(), "Human", 1);
        }
    }

    // 좀비 승리 이벤트
    @EventHandler
    private void ZombieWinEvent(ZombieWinEvent event) {
        dselon.selonzombiesurvival.Manager manager = SelonZombieSurvival.manager;
        if (manager.getPlayerList().size() >= 4) { // 플레이어 인원 수가 4명 이상일 경우
            Files.addRecordTeamRatio("Zombie", 1);
            Files.addRecordMapRatio(manager.getMapName(), "Zombie", 1);
        }
    }

    // 플레이어 추가 이벤트
    @EventHandler
    private void PlayerAddEvent(PlayerAddEvent event) {
        Player player = event.getPlayer();

        if (SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.READY
                || SelonZombieSurvival.manager.getGameProgress() == dselon.selonzombiesurvival.Manager.GameProgress.GAME) {
            variables.getSelectedClass().put(player, kr.sizniss.data.Files.getOptionClass(player));
            variables.getSelectedType().put(player, kr.sizniss.data.Files.getOptionType(player));
            variables.getSelectedMainWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Main"));
            variables.getSelectedSubWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Sub"));
            variables.getSelectedMeleeWeapon().put(player, kr.sizniss.data.Files.getOptionWeapon(player, "Melee"));

            variables.getAbilityCooldown().put(player, 0);
            variables.getSkillCooldown().put(player, 0);

            variables.getVariationDelay().put(player, variables.getVariationTime());
            variables.getVariationLevel().put(player, 0);
            variables.getKillCount().put(player, 0);
            variables.getDeathCount().put(player, 0);
            variables.getAcquiredSupplyCount().put(player, 0);
        }
    }

    // 인간 감염 이벤트
    @EventHandler
    private void HumanInfectedEvent(HumanInfectedEvent event) {
        Player human = event.getHuman();

        // 인간이 보균자일 경우
        if (SelonZombieSurvival.manager.getCarrierList().contains(human)) {
            int carrierVariationLevel = variables.getCarrierVariationLevel();

            variables.getVariationLevel().put(human, carrierVariationLevel); // 보균자 변이 레벨 초기화
        }

        variables.getKillCount().put(human, 0); // 인간 킬 카운트 초기화
        variables.getDeathCount().put(human, 0); // 인간 데스 카운트 초기화
    }

    // 인간 감염 이벤트
    @EventHandler
    private void HumanInfectedByZombieEvent(HumanInfectedByZombieEvent event) {
        Player zombie = event.getZombie();
        
        int currentKillCount = variables.getKillCount().get(zombie);
        variables.getKillCount().put(zombie, currentKillCount + 1); // 좀비 킬 카운트 증가
    }

    // 좀비 사망 이벤트
    @EventHandler
    private void ZombieKilledByHumanEvent(ZombieKilledByHumanEvent event) {
        Player human = event.getHuman();
        Player zombie = event.getZombie();

        int currentKillCount = variables.getKillCount().get(human);
        variables.getKillCount().put(human, currentKillCount + 1); // 인간 킬 카운트 증가
        int currentDeathCount = variables.getDeathCount().get(zombie);
        variables.getDeathCount().put(zombie, currentDeathCount + 1); // 좀비 데스 카운트 증가
    }

    // 좀비 치유 이벤트
    @EventHandler
    private void ZombieCuredEvent(ZombieCuredEvent event) {
        Player zombie = event.getZombie();

        variables.getVariationDelay().put(zombie, variables.getVariationTime()); // 좀비 변이 지연 초기화
        variables.getVariationLevel().put(zombie, 0); // 좀비 변이 레벨 초기화
        variables.getKillCount().put(zombie, 0); // 좀비 킬 카운트 초기화
        variables.getDeathCount().put(zombie, 0); // 좀비 데스 카운트 초기화
        variables.getAcquiredSupplyCount().put(zombie, 0); // 좀비 보급 획득 여부 초기화
    }

    // 좀비 치유 이벤트
    @EventHandler
    private void ZombieCuredByHumanEvent(ZombieCuredByHumanEvent event) {
        Player human = event.getHuman();

        int currentKillCount = variables.getKillCount().get(human);
        variables.getKillCount().put(human, currentKillCount + 1); // 인간 킬 카운트 증가
    }

    // 패배 보상 이벤트
    @EventHandler
    private void RewardLoseEvent(RewardLoseEvent event) {
        Player player = event.getPlayer();

        if (SelonZombieSurvival.manager.isZombieWin() || SelonZombieSurvival.manager.getHumanList().size() == 0) { // 좀비 승리일 경우
            if (SelonZombieSurvival.manager.getZombieList().contains(player)) { // 플레이어가 좀비일 경우

                int neededKillCount = variables.getNeededKillCount();
                int killCount = variables.getKillCount().get(player);
                // 킬 수 조건이 충족된 경우
                if (killCount >= neededKillCount) {
                    event.setCancelled(true); // 이벤트 취소
                    SelonZombieSurvival.manager.rewardWin(player); // 승리 함수 호출
                }

            }
        }
    }

}
