package kr.sizniss.manager.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import static kr.sizniss.manager.Manager.plugin;

public class CodiGUI {

    private Inventory inventory;

    private Player player;
    private OfflinePlayer targetPlayer;

    private int task; // 갱신 테스크


    public CodiGUI(Player player, OfflinePlayer targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;

        createGUI(); // GUI 생성
        Bukkit.getPluginManager().registerEvents(new Event(), plugin); // 이벤트 등록
    }


    // GUI 생성 함수
    private void createGUI() {
        inventory = Bukkit.createInventory(player, 54, "코디"); // 인벤토리 생성

        // 플레이어 머리
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, Short.parseShort("3"));
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName("§f" + targetPlayer.getName());
        headMeta.setOwner(targetPlayer.getName());
        head.setItemMeta(headMeta);

        // 검은색 색유리 판
        ItemStack blackPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, Short.parseShort("15"));
        ItemMeta blackPaneMeta = blackPane.getItemMeta();
        blackPaneMeta.setDisplayName("§f");
        blackPane.setItemMeta(blackPaneMeta);

        // 레일
        ItemStack rail = new ItemStack(Material.RAILS);
        ItemMeta railMeta = rail.getItemMeta();
        railMeta.setDisplayName("§f");
        rail.setItemMeta(railMeta);

        // 테두리
        ItemStack border = new ItemStack(Material.PAINTING);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.setDisplayName("§f테두리");
        border.setItemMeta(borderMeta);

        // 칭호
        ItemStack prefix = new ItemStack(Material.ITEM_FRAME);
        ItemMeta prefixMeta = prefix.getItemMeta();
        prefixMeta.setDisplayName("§f칭호");
        prefix.setItemMeta(prefixMeta);

        // 닉네임
        ItemStack nick = new ItemStack(Material.NAME_TAG);
        ItemMeta nickMeta = nick.getItemMeta();
        nickMeta.setDisplayName("§f닉네임");
        nick.setItemMeta(nickMeta);

        // 채팅
        ItemStack chat = new ItemStack(Material.SIGN);
        ItemMeta chatMeta = chat.getItemMeta();
        chatMeta.setDisplayName("§f채팅");
        chat.setItemMeta(chatMeta);


        // 테두리 양털
        ItemStack borderWhite;
        ItemMeta borderWhiteMeta;
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "White")) {
            borderWhite = new ItemStack(Material.WOOL, 1, Short.parseShort("0"));
            borderWhiteMeta = borderWhite.getItemMeta();
            borderWhiteMeta.setDisplayName("§f하얀색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "BorderColor").equals("White")) {
                borderWhiteMeta.setDisplayName(borderWhiteMeta.getDisplayName() + " §5선택됨");
                borderWhiteMeta.addEnchant(Enchantment.LUCK, 1, true);
                borderWhiteMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            borderWhite = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            borderWhiteMeta = borderWhite.getItemMeta();
            borderWhiteMeta.setDisplayName("§7하얀색");
        }
        borderWhite.setItemMeta(borderWhiteMeta);
        ItemStack borderGreen;
        ItemMeta borderGreenMeta;
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Green")) {
            borderGreen = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
            borderGreenMeta = borderGreen.getItemMeta();
            borderGreenMeta.setDisplayName("§a연두색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "BorderColor").equals("Green")) {
                borderGreenMeta.setDisplayName(borderGreenMeta.getDisplayName() + " §5선택됨");
                borderGreenMeta.addEnchant(Enchantment.LUCK, 1, true);
                borderGreenMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            borderGreen = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            borderGreenMeta = borderGreen.getItemMeta();
            borderGreenMeta.setDisplayName("§7연두색");
        }
        borderGreen.setItemMeta(borderGreenMeta);
        ItemStack borderSkyblue;
        ItemMeta borderSkyblueMeta;
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Skyblue")) {
            borderSkyblue = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
            borderSkyblueMeta = borderSkyblue.getItemMeta();
            borderSkyblueMeta.setDisplayName("§b하늘색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "BorderColor").equals("Skyblue")) {
                borderSkyblueMeta.setDisplayName(borderSkyblueMeta.getDisplayName() + " §5선택됨");
                borderSkyblueMeta.addEnchant(Enchantment.LUCK, 1, true);
                borderSkyblueMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            borderSkyblue = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            borderSkyblueMeta = borderSkyblue.getItemMeta();
            borderSkyblueMeta.setDisplayName("§7하늘색");
        }
        borderSkyblue.setItemMeta(borderSkyblueMeta);
        ItemStack borderPink;
        ItemMeta borderPinkMeta;
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Pink")) {
            borderPink = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
            borderPinkMeta = borderPink.getItemMeta();
            borderPinkMeta.setDisplayName("§c분홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "BorderColor").equals("Pink")) {
                borderPinkMeta.setDisplayName(borderPinkMeta.getDisplayName() + " §5선택됨");
                borderPinkMeta.addEnchant(Enchantment.LUCK, 1, true);
                borderPinkMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            borderPink = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            borderPinkMeta = borderPink.getItemMeta();
            borderPinkMeta.setDisplayName("§7분홍색");
        }
        borderPink.setItemMeta(borderPinkMeta);
        ItemStack borderMagenta;
        ItemMeta borderMagentaMeta;
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Magenta")) {
            borderMagenta = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
            borderMagentaMeta = borderMagenta.getItemMeta();
            borderMagentaMeta.setDisplayName("§d자홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "BorderColor").equals("Magenta")) {
                borderMagentaMeta.setDisplayName(borderMagentaMeta.getDisplayName() + " §5선택됨");
                borderMagentaMeta.addEnchant(Enchantment.LUCK, 1, true);
                borderMagentaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            borderMagenta = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            borderMagentaMeta = borderMagenta.getItemMeta();
            borderMagentaMeta.setDisplayName("§7자홍색");
        }
        borderMagenta.setItemMeta(borderMagentaMeta);
        ItemStack borderYellow;
        ItemMeta borderYellowMeta;
        if (kr.sizniss.data.Files.getBorderColor(targetPlayer, "Yellow")) {
            borderYellow = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
            borderYellowMeta = borderYellow.getItemMeta();
            borderYellowMeta.setDisplayName("§e노란색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "BorderColor").equals("Yellow")) {
                borderYellowMeta.setDisplayName(borderYellowMeta.getDisplayName() + " §5선택됨");
                borderYellowMeta.addEnchant(Enchantment.LUCK, 1, true);
                borderYellowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            borderYellow = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            borderYellowMeta = borderYellow.getItemMeta();
            borderYellowMeta.setDisplayName("§7노란색");
        }
        borderYellow.setItemMeta(borderYellowMeta);

        // 칭호 양털
        ItemStack prefixWhite;
        ItemMeta prefixWhiteMeta;
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "White")) {
            prefixWhite = new ItemStack(Material.WOOL, 1, Short.parseShort("0"));
            prefixWhiteMeta = prefixWhite.getItemMeta();
            prefixWhiteMeta.setDisplayName("§f하얀색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "PrefixColor").equals("White")) {
                prefixWhiteMeta.setDisplayName(prefixWhiteMeta.getDisplayName() + " §5선택됨");
                prefixWhiteMeta.addEnchant(Enchantment.LUCK, 1, true);
                prefixWhiteMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            prefixWhite = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            prefixWhiteMeta = prefixWhite.getItemMeta();
            prefixWhiteMeta.setDisplayName("§7하얀색");
        }
        prefixWhite.setItemMeta(prefixWhiteMeta);
        ItemStack prefixGreen;
        ItemMeta prefixGreenMeta;
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Green")) {
            prefixGreen = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
            prefixGreenMeta = prefixGreen.getItemMeta();
            prefixGreenMeta.setDisplayName("§a연두색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "PrefixColor").equals("Green")) {
                prefixGreenMeta.setDisplayName(prefixGreenMeta.getDisplayName() + " §5선택됨");
                prefixGreenMeta.addEnchant(Enchantment.LUCK, 1, true);
                prefixGreenMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            prefixGreen = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            prefixGreenMeta = prefixGreen.getItemMeta();
            prefixGreenMeta.setDisplayName("§7연두색");
        }
        prefixGreen.setItemMeta(prefixGreenMeta);
        ItemStack prefixSkyblue;
        ItemMeta prefixSkyblueMeta;
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Skyblue")) {
            prefixSkyblue = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
            prefixSkyblueMeta = prefixSkyblue.getItemMeta();
            prefixSkyblueMeta.setDisplayName("§b하늘색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "PrefixColor").equals("Skyblue")) {
                prefixSkyblueMeta.setDisplayName(prefixSkyblueMeta.getDisplayName() + " §5선택됨");
                prefixSkyblueMeta.addEnchant(Enchantment.LUCK, 1, true);
                prefixSkyblueMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            prefixSkyblue = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            prefixSkyblueMeta = prefixSkyblue.getItemMeta();
            prefixSkyblueMeta.setDisplayName("§7하늘색");
        }
        prefixSkyblue.setItemMeta(prefixSkyblueMeta);
        ItemStack prefixPink;
        ItemMeta prefixPinkMeta;
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Pink")) {
            prefixPink = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
            prefixPinkMeta = prefixPink.getItemMeta();
            prefixPinkMeta.setDisplayName("§c분홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "PrefixColor").equals("Pink")) {
                prefixPinkMeta.setDisplayName(prefixPinkMeta.getDisplayName() + " §5선택됨");
                prefixPinkMeta.addEnchant(Enchantment.LUCK, 1, true);
                prefixPinkMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            prefixPink = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            prefixPinkMeta = prefixPink.getItemMeta();
            prefixPinkMeta.setDisplayName("§7분홍색");
        }
        prefixPink.setItemMeta(prefixPinkMeta);
        ItemStack prefixMagenta;
        ItemMeta prefixMagentaMeta;
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Magenta")) {
            prefixMagenta = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
            prefixMagentaMeta = prefixMagenta.getItemMeta();
            prefixMagentaMeta.setDisplayName("§d자홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "PrefixColor").equals("Magenta")) {
                prefixMagentaMeta.setDisplayName(prefixMagentaMeta.getDisplayName() + " §5선택됨");
                prefixMagentaMeta.addEnchant(Enchantment.LUCK, 1, true);
                prefixMagentaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            prefixMagenta = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            prefixMagentaMeta = prefixMagenta.getItemMeta();
            prefixMagentaMeta.setDisplayName("§7자홍색");
        }
        prefixMagenta.setItemMeta(prefixMagentaMeta);
        ItemStack prefixYellow;
        ItemMeta prefixYellowMeta;
        if (kr.sizniss.data.Files.getPrefixColor(targetPlayer, "Yellow")) {
            prefixYellow = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
            prefixYellowMeta = prefixYellow.getItemMeta();
            prefixYellowMeta.setDisplayName("§e노란색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "PrefixColor").equals("Yellow")) {
                prefixYellowMeta.setDisplayName(prefixYellowMeta.getDisplayName() + " §5선택됨");
                prefixYellowMeta.addEnchant(Enchantment.LUCK, 1, true);
                prefixYellowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            prefixYellow = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            prefixYellowMeta = prefixYellow.getItemMeta();
            prefixYellowMeta.setDisplayName("§7노란색");
        }
        prefixYellow.setItemMeta(prefixYellowMeta);

        // 닉네임 양털
        ItemStack nickWhite;
        ItemMeta nickWhiteMeta;
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "White")) {
            nickWhite = new ItemStack(Material.WOOL, 1, Short.parseShort("0"));
            nickWhiteMeta = nickWhite.getItemMeta();
            nickWhiteMeta.setDisplayName("§f하얀색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "NickColor").equals("White")) {
                nickWhiteMeta.setDisplayName(nickWhiteMeta.getDisplayName() + " §5선택됨");
                nickWhiteMeta.addEnchant(Enchantment.LUCK, 1, true);
                nickWhiteMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            nickWhite = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            nickWhiteMeta = nickWhite.getItemMeta();
            nickWhiteMeta.setDisplayName("§7하얀색");
        }
        nickWhite.setItemMeta(nickWhiteMeta);
        ItemStack nickGreen;
        ItemMeta nickGreenMeta;
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Green")) {
            nickGreen = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
            nickGreenMeta = nickGreen.getItemMeta();
            nickGreenMeta.setDisplayName("§a연두색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "NickColor").equals("Green")) {
                nickGreenMeta.setDisplayName(nickGreenMeta.getDisplayName() + " §5선택됨");
                nickGreenMeta.addEnchant(Enchantment.LUCK, 1, true);
                nickGreenMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            nickGreen = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            nickGreenMeta = nickGreen.getItemMeta();
            nickGreenMeta.setDisplayName("§7연두색");
        }
        nickGreen.setItemMeta(nickGreenMeta);
        ItemStack nickSkyblue;
        ItemMeta nickSkyblueMeta;
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Skyblue")) {
            nickSkyblue = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
            nickSkyblueMeta = nickSkyblue.getItemMeta();
            nickSkyblueMeta.setDisplayName("§b하늘색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "NickColor").equals("Skyblue")) {
                nickSkyblueMeta.setDisplayName(nickSkyblueMeta.getDisplayName() + " §5선택됨");
                nickSkyblueMeta.addEnchant(Enchantment.LUCK, 1, true);
                nickSkyblueMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            nickSkyblue = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            nickSkyblueMeta = nickSkyblue.getItemMeta();
            nickSkyblueMeta.setDisplayName("§7하늘색");
        }
        nickSkyblue.setItemMeta(nickSkyblueMeta);
        ItemStack nickPink;
        ItemMeta nickPinkMeta;
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Pink")) {
            nickPink = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
            nickPinkMeta = nickPink.getItemMeta();
            nickPinkMeta.setDisplayName("§c분홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "NickColor").equals("Pink")) {
                nickPinkMeta.setDisplayName(nickPinkMeta.getDisplayName() + " §5선택됨");
                nickPinkMeta.addEnchant(Enchantment.LUCK, 1, true);
                nickPinkMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            nickPink = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            nickPinkMeta = nickPink.getItemMeta();
            nickPinkMeta.setDisplayName("§7분홍색");
        }
        nickPink.setItemMeta(nickPinkMeta);
        ItemStack nickMagenta;
        ItemMeta nickMagentaMeta;
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Magenta")) {
            nickMagenta = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
            nickMagentaMeta = nickMagenta.getItemMeta();
            nickMagentaMeta.setDisplayName("§d자홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "NickColor").equals("Magenta")) {
                nickMagentaMeta.setDisplayName(nickMagentaMeta.getDisplayName() + " §5선택됨");
                nickMagentaMeta.addEnchant(Enchantment.LUCK, 1, true);
                nickMagentaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            nickMagenta = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            nickMagentaMeta = nickMagenta.getItemMeta();
            nickMagentaMeta.setDisplayName("§7자홍색");
        }
        nickMagenta.setItemMeta(nickMagentaMeta);
        ItemStack nickYellow;
        ItemMeta nickYellowMeta;
        if (kr.sizniss.data.Files.getNickColor(targetPlayer, "Yellow")) {
            nickYellow = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
            nickYellowMeta = nickYellow.getItemMeta();
            nickYellowMeta.setDisplayName("§e노란색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "NickColor").equals("Yellow")) {
                nickYellowMeta.setDisplayName(nickYellowMeta.getDisplayName() + " §5선택됨");
                nickYellowMeta.addEnchant(Enchantment.LUCK, 1, true);
                nickYellowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            nickYellow = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            nickYellowMeta = nickYellow.getItemMeta();
            nickYellowMeta.setDisplayName("§7노란색");
        }
        nickYellow.setItemMeta(nickYellowMeta);

        // 채팅 양털
        ItemStack chatWhite;
        ItemMeta chatWhiteMeta;
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "White")) {
            chatWhite = new ItemStack(Material.WOOL, 1, Short.parseShort("0"));
            chatWhiteMeta = chatWhite.getItemMeta();
            chatWhiteMeta.setDisplayName("§f하얀색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "ChatColor").equals("White")) {
                chatWhiteMeta.setDisplayName(chatWhiteMeta.getDisplayName() + " §5선택됨");
                chatWhiteMeta.addEnchant(Enchantment.LUCK, 1, true);
                chatWhiteMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            chatWhite = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            chatWhiteMeta = chatWhite.getItemMeta();
            chatWhiteMeta.setDisplayName("§7하얀색");
        }
        chatWhite.setItemMeta(chatWhiteMeta);
        ItemStack chatGreen;
        ItemMeta chatGreenMeta;
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Green")) {
            chatGreen = new ItemStack(Material.WOOL, 1, Short.parseShort("5"));
            chatGreenMeta = chatGreen.getItemMeta();
            chatGreenMeta.setDisplayName("§a연두색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "ChatColor").equals("Green")) {
                chatGreenMeta.setDisplayName(chatGreenMeta.getDisplayName() + " §5선택됨");
                chatGreenMeta.addEnchant(Enchantment.LUCK, 1, true);
                chatGreenMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            chatGreen = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            chatGreenMeta = chatGreen.getItemMeta();
            chatGreenMeta.setDisplayName("§7연두색");
        }
        chatGreen.setItemMeta(chatGreenMeta);
        ItemStack chatSkyblue;
        ItemMeta chatSkyblueMeta;
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Skyblue")) {
            chatSkyblue = new ItemStack(Material.WOOL, 1, Short.parseShort("3"));
            chatSkyblueMeta = chatSkyblue.getItemMeta();
            chatSkyblueMeta.setDisplayName("§b하늘색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "ChatColor").equals("Skyblue")) {
                chatSkyblueMeta.setDisplayName(chatSkyblueMeta.getDisplayName() + " §5선택됨");
                chatSkyblueMeta.addEnchant(Enchantment.LUCK, 1, true);
                chatSkyblueMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            chatSkyblue = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            chatSkyblueMeta = chatSkyblue.getItemMeta();
            chatSkyblueMeta.setDisplayName("§7하늘색");
        }
        chatSkyblue.setItemMeta(chatSkyblueMeta);
        ItemStack chatPink;
        ItemMeta chatPinkMeta;
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Pink")) {
            chatPink = new ItemStack(Material.WOOL, 1, Short.parseShort("6"));
            chatPinkMeta = chatPink.getItemMeta();
            chatPinkMeta.setDisplayName("§c분홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "ChatColor").equals("Pink")) {
                chatPinkMeta.setDisplayName(chatPinkMeta.getDisplayName() + " §5선택됨");
                chatPinkMeta.addEnchant(Enchantment.LUCK, 1, true);
                chatPinkMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            chatPink = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            chatPinkMeta = chatPink.getItemMeta();
            chatPinkMeta.setDisplayName("§7분홍색");
        }
        chatPink.setItemMeta(chatPinkMeta);
        ItemStack chatMagenta;
        ItemMeta chatMagentaMeta;
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Magenta")) {
            chatMagenta = new ItemStack(Material.WOOL, 1, Short.parseShort("2"));
            chatMagentaMeta = chatMagenta.getItemMeta();
            chatMagentaMeta.setDisplayName("§d자홍색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "ChatColor").equals("Magenta")) {
                chatMagentaMeta.setDisplayName(chatMagentaMeta.getDisplayName() + " §5선택됨");
                chatMagentaMeta.addEnchant(Enchantment.LUCK, 1, true);
                chatMagentaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            chatMagenta = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            chatMagentaMeta = chatMagenta.getItemMeta();
            chatMagentaMeta.setDisplayName("§7자홍색");
        }
        chatMagenta.setItemMeta(chatMagentaMeta);
        ItemStack chatYellow;
        ItemMeta chatYellowMeta;
        if (kr.sizniss.data.Files.getChatColor(targetPlayer, "Yellow")) {
            chatYellow = new ItemStack(Material.WOOL, 1, Short.parseShort("4"));
            chatYellowMeta = chatYellow.getItemMeta();
            chatYellowMeta.setDisplayName("§e노란색");
            if (kr.sizniss.data.Files.getOptionCodi(targetPlayer, "ChatColor").equals("Yellow")) {
                chatYellowMeta.setDisplayName(chatYellowMeta.getDisplayName() + " §5선택됨");
                chatYellowMeta.addEnchant(Enchantment.LUCK, 1, true);
                chatYellowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        } else {
            chatYellow = new ItemStack(Material.STAINED_GLASS, 1, Short.parseShort("8"));
            chatYellowMeta = chatYellow.getItemMeta();
            chatYellowMeta.setDisplayName("§7노란색");
        }
        chatYellow.setItemMeta(chatYellowMeta);


        inventory.setItem(1, head);

        inventory.setItem(9, border);
        inventory.setItem(18, prefix);
        inventory.setItem(27, nick);
        inventory.setItem(36, chat);

        inventory.setItem(11, borderWhite);
        inventory.setItem(12, borderGreen);
        inventory.setItem(13, borderSkyblue);
        inventory.setItem(14, borderPink);
        inventory.setItem(15, borderMagenta);
        inventory.setItem(16, borderYellow);

        inventory.setItem(20, prefixWhite);
        inventory.setItem(21, prefixGreen);
        inventory.setItem(22, prefixSkyblue);
        inventory.setItem(23, prefixPink);
        inventory.setItem(24, prefixMagenta);
        inventory.setItem(25, prefixYellow);

        inventory.setItem(29, nickWhite);
        inventory.setItem(30, nickGreen);
        inventory.setItem(31, nickSkyblue);
        inventory.setItem(32, nickPink);
        inventory.setItem(33, nickMagenta);
        inventory.setItem(34, nickYellow);

        inventory.setItem(38, chatWhite);
        inventory.setItem(39, chatGreen);
        inventory.setItem(40, chatSkyblue);
        inventory.setItem(41, chatPink);
        inventory.setItem(42, chatMagenta);
        inventory.setItem(43, chatYellow);

        inventory.setItem(0, blackPane);
        for (int i = 2; i < 9; i++) {
            inventory.setItem(i, blackPane);
        }
        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, blackPane);
        }

        for (int i = 10; i < 45; i += 9) {
            inventory.setItem(i, rail);
        }
        for (int i = 17; i < 45; i += 9) {
            inventory.setItem(i, rail);
        }

        player.openInventory(inventory); // 플레이어에게 인벤토리 노출

        // GUI 갱신
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            new CodiGUI(player, targetPlayer); // CodiGUI 호출
        }, 20L);
    }


    // 이벤트 리스너 클래스
    private class Event implements Listener {

        // 인벤토리 클릭 이벤트
        @EventHandler
        private void InventoryClickEvent(InventoryClickEvent event) {
            if (event.getClickedInventory() == null) {
                return;
            }

            if (event.getClickedInventory().equals(inventory)) {
                event.setCancelled(true);

                if (!targetPlayer.equals(event.getWhoClicked())) { // 타겟 플레이어와 인벤토리 클릭자가 다를 경우
                    return; // 함수 반환
                }

                int slot = event.getSlot(); // 슬롯
                for(int i = 11; i < 45; i += 9) {
                    int count = 0;
                    String color = "White";
                    boolean isClicked = false;

                    if (slot == i) { // 하얀색 클릭
                        count = 0;
                        color = "White";
                        isClicked = true;
                    } else if (event.getSlot() == i + 1) { // 연두색 클릭
                        count = 1;
                        color = "Green";
                        isClicked = true;
                    } else if (event.getSlot() == i + 2) { // 하늘색 클릭
                        count = 2;
                        color = "Skyblue";
                        isClicked = true;
                    } else if (event.getSlot() == i + 3) { // 분홍색 클릭
                        count = 3;
                        color = "Pink";
                        isClicked = true;
                    } else if (event.getSlot() == i + 4) { // 자홍색 클릭
                        count = 4;
                        color = "Magenta";
                        isClicked = true;
                    } else if (event.getSlot() == i + 5) { // 노란색 클릭
                        count = 5;
                        color = "Yellow";
                        isClicked = true;
                    }

                    if (isClicked) {
                        if (slot == 11 + count) { // 테두리
                            if (kr.sizniss.data.Files.getBorderColor(player, color)) {
                                kr.sizniss.data.Files.setOptionCodi(player, "BorderColor", color);
                            }
                        } else if (slot == 20 + count) { // 칭호
                            if (kr.sizniss.data.Files.getPrefixColor(player, color)) {
                                kr.sizniss.data.Files.setOptionCodi(player, "PrefixColor", color);
                            }
                        } else if (slot == 29 + count) { // 닉네임
                            if (kr.sizniss.data.Files.getNickColor(player, color)) {
                                kr.sizniss.data.Files.setOptionCodi(player, "NickColor", color);
                            }
                        } else if (slot == 38 + count) { // 채팅
                            if (kr.sizniss.data.Files.getChatColor(player, color)) {
                                kr.sizniss.data.Files.setOptionCodi(player, "ChatColor", color);
                            }
                        }
                        new CodiGUI(player, (OfflinePlayer) inventory.getHolder()); // CodiGUI 호출
                    }
                }

            }
        }

        // 인벤토리 닫기 이벤트
        @EventHandler
        private void InventoryCloseEvent(InventoryCloseEvent event) {
            if (event.getInventory().equals(inventory)) {
                Bukkit.getScheduler().cancelTask(task); // GUI 갱신 종료
                HandlerList.unregisterAll(this); // 이벤트 등록 해제
            }
        }

    }

}
