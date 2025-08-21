package kr.sizniss.manager;

import kr.sizniss.manager.abilities.*;
import kr.sizniss.manager.guis.MenuGUI;
import kr.sizniss.manager.items.*;
import kr.sizniss.manager.maps.*;
import kr.sizniss.manager.weapons.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Manager extends JavaPlugin {

    public static Manager plugin;
    public static Files files;
    public static Variables variables;
    public static Methods methods;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        files = new Files(); // 파일 객체 생성
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { variables = new Variables(); }, 1L); // 변수 객체 생성
        methods = new Methods(); // 함수 객체 생성


        // 이벤트 등록
        Bukkit.getPluginManager().registerEvents(new Events(), plugin);

        Bukkit.getPluginManager().registerEvents(new MenuGUI.Event(), plugin);

        Bukkit.getPluginManager().registerEvents(new Poison(), plugin);
        Bukkit.getPluginManager().registerEvents(new LiquidMedicine(), plugin);
        Bukkit.getPluginManager().registerEvents(new Bomb(), plugin);
        Bukkit.getPluginManager().registerEvents(new WaterMedicine(), plugin);
        Bukkit.getPluginManager().registerEvents(new Remedy(), plugin);
        Bukkit.getPluginManager().registerEvents(new Elixir(), plugin);
        Bukkit.getPluginManager().registerEvents(new AmmoBox(), plugin);
        Bukkit.getPluginManager().registerEvents(new Lime(), plugin);

        Bukkit.getPluginManager().registerEvents(new Leap(), plugin);
        Bukkit.getPluginManager().registerEvents(new Runaway(), plugin);
        Bukkit.getPluginManager().registerEvents(new Cure(), plugin);
        Bukkit.getPluginManager().registerEvents(new Strengthen(), plugin);
        Bukkit.getPluginManager().registerEvents(new Undying(), plugin);
        Bukkit.getPluginManager().registerEvents(new Reversal(), plugin);
        Bukkit.getPluginManager().registerEvents(new Flight(), plugin);
        Bukkit.getPluginManager().registerEvents(new Hide(), plugin);
        Bukkit.getPluginManager().registerEvents(new Shock(), plugin);
        Bukkit.getPluginManager().registerEvents(new BioBomb(), plugin);
        Bukkit.getPluginManager().registerEvents(new Pulling(), plugin);
        Bukkit.getPluginManager().registerEvents(new Clairvoyance(), plugin);

        Bukkit.getPluginManager().registerEvents(new Rake(), plugin);
        Bukkit.getPluginManager().registerEvents(new MP5(), plugin);
        Bukkit.getPluginManager().registerEvents(new P90(), plugin);
        Bukkit.getPluginManager().registerEvents(new AR_57(), plugin);
        Bukkit.getPluginManager().registerEvents(new M1928_Thompson(), plugin);
        Bukkit.getPluginManager().registerEvents(new M4A1(), plugin);
        Bukkit.getPluginManager().registerEvents(new Kar98k(), plugin);
        Bukkit.getPluginManager().registerEvents(new M1887_Winchester(), plugin);
        Bukkit.getPluginManager().registerEvents(new MG3(), plugin);
        Bukkit.getPluginManager().registerEvents(new M134_Minigun(), plugin);
        Bukkit.getPluginManager().registerEvents(new RPG_7(), plugin);
        Bukkit.getPluginManager().registerEvents(new Riot_Shield(), plugin);
        Bukkit.getPluginManager().registerEvents(new FlameThrower(), plugin);
        Bukkit.getPluginManager().registerEvents(new Barret_M99(), plugin);
        Bukkit.getPluginManager().registerEvents(new Hwaryongpo(), plugin);
        Bukkit.getPluginManager().registerEvents(new HyeolJeokJa(), plugin);
        Bukkit.getPluginManager().registerEvents(new Chainsaw(), plugin);
        Bukkit.getPluginManager().registerEvents(new M32_MGL(), plugin);
        Bukkit.getPluginManager().registerEvents(new MK_11_SWS(), plugin);
        Bukkit.getPluginManager().registerEvents(new VSS_Vintorez(), plugin);
        Bukkit.getPluginManager().registerEvents(new Thorn_Shield(), plugin);
        Bukkit.getPluginManager().registerEvents(new Muldaepo(), plugin);
        Bukkit.getPluginManager().registerEvents(new AEA_Zeus_2(), plugin);
        Bukkit.getPluginManager().registerEvents(new DP_12(), plugin);
        Bukkit.getPluginManager().registerEvents(new TimeBomb(), plugin);
        Bukkit.getPluginManager().registerEvents(new SSW40(), plugin);
        Bukkit.getPluginManager().registerEvents(new TaserGun(), plugin);
        Bukkit.getPluginManager().registerEvents(new MK_13_EGLM(), plugin);
        Bukkit.getPluginManager().registerEvents(new Buchae(), plugin);
        Bukkit.getPluginManager().registerEvents(new Katana(), plugin);
        Bukkit.getPluginManager().registerEvents(new Taser_Knife(), plugin);
        Bukkit.getPluginManager().registerEvents(new Claymore(), plugin);

        Bukkit.getPluginManager().registerEvents(new Abyss3(), plugin);
        Bukkit.getPluginManager().registerEvents(new AncientRuins(), plugin);
        Bukkit.getPluginManager().registerEvents(new Assault(), plugin);
        Bukkit.getPluginManager().registerEvents(new Britain(), plugin);
        Bukkit.getPluginManager().registerEvents(new Cathedral(), plugin);
        Bukkit.getPluginManager().registerEvents(new CruiseShip(), plugin);
        Bukkit.getPluginManager().registerEvents(new DeathHouse(), plugin);
        Bukkit.getPluginManager().registerEvents(new DesertMarket(), plugin);
        Bukkit.getPluginManager().registerEvents(new Dust(), plugin);
        Bukkit.getPluginManager().registerEvents(new Factory(), plugin);
        Bukkit.getPluginManager().registerEvents(new GhostCityA(), plugin);
        Bukkit.getPluginManager().registerEvents(new GhostCityB(), plugin);
        Bukkit.getPluginManager().registerEvents(new Hiden(), plugin);
        Bukkit.getPluginManager().registerEvents(new Hospital(), plugin);
        Bukkit.getPluginManager().registerEvents(new Laboratory(), plugin);
        Bukkit.getPluginManager().registerEvents(new OldFactory(), plugin);
        Bukkit.getPluginManager().registerEvents(new ParkingLot(), plugin);
        Bukkit.getPluginManager().registerEvents(new RatHole(), plugin);
        Bukkit.getPluginManager().registerEvents(new Rooftop(), plugin);
        Bukkit.getPluginManager().registerEvents(new SubmergedArea(), plugin);
        Bukkit.getPluginManager().registerEvents(new VeniceA(), plugin);
        Bukkit.getPluginManager().registerEvents(new VeniceB(), plugin);

        Bukkit.getPluginManager().registerEvents(new Lobby(), plugin);
        Bukkit.getPluginManager().registerEvents(new Tutorial(), plugin);
        Bukkit.getPluginManager().registerEvents(new JumpMap(), plugin);
        Bukkit.getPluginManager().registerEvents(new HuntingGround(), plugin);


        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { methods.timerSystem(); }, 600L); // 타이머 시스템 작동
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { methods.errorDetectionSystem(); }, 20L); // 오류 감지 시스템 작동
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("manager.op")) {
            if (command.getName().equalsIgnoreCase("Test")) {
                return new Commands().Test(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ConvertDefault")) {
                return new Commands().ConvertDefault(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ConvertHuman")) {
                return new Commands().ConvertHuman(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ConvertZombie")) {
                return new Commands().ConvertZombie(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ConvertHost")) {
                return new Commands().ConvertHost(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ConvertHero")) {
                return new Commands().ConvertHero(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ConvertCarrier")) {
                return new Commands().ConvertCarrier(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("AcquiredSupply")) {
                return new Commands().AcquiredSupply(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("RewardWin")) {
                return new Commands().RewardWin(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("RewardLose")) {
                return new Commands().RewardLose(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("RewardKill")) {
                return new Commands().RewardKill(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("RewardCure")) {
                return new Commands().RewardCure(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("RewardDeath")) {
                return new Commands().RewardDeath(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("EscapePenalty")) {
                return new Commands().EscapePenalty(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("Minelist")) {
                return new Commands().Minelist(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("UnBan")) {
                return new Commands().UnBan(sender, command, label, args);
            }  else if (command.getName().equalsIgnoreCase("KickAll")) {
                return new Commands().KickAll(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("TPAll")) {
                return new Commands().TPAll(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("GM")) {
                return new Commands().GM(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("Spawn")) {
                return new Commands().Spawn(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("Heal")) {
                return new Commands().Heal(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("MaxHealth")) {
                return new Commands().MaxHealth(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("Health")) {
                return new Commands().Health(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("ChatOp")) {
                return new Commands().ChatOp(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("Vanish")) {
                return new Commands().Vanish(sender, command, label, args);
            }
        }

        if (sender.getName().equals("CONSOLE") || sender.getName().equals("S_Sasisa")) {
            if (command.getName().equalsIgnoreCase("Donation")) {
                return new Commands().Donation(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("Var")) {
                return new Commands().Var(sender, command, label, args);
            } else if (command.getName().equalsIgnoreCase("BroadcastRecordLog")) {
                return new Commands().BroadcastRecordLog(sender, command, label, args);
            }
        }

        if (command.getName().equalsIgnoreCase("Whisper")) {
            return new Commands().Whisper(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Game")) {
            return new Commands().Game(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Shop")) {
            return new Commands().Shop(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Box")) {
            return new Commands().Box(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Equipment")) {
            return new Commands().Equipment(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Class")) {
            return new Commands().Class(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Type")) {
            return new Commands().Type(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Codi")) {
            return new Commands().Codi(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Profile")) {
            return new Commands().Profile(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Link")) {
            return new Commands().Link(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Rule")) {
            return new Commands().Rule(sender, command, label, args);
        } else if (command.getName().equalsIgnoreCase("Tutorial")) {
            return new Commands().Tutorial(sender, command, label, args);
        }
        return false;
    }

}
