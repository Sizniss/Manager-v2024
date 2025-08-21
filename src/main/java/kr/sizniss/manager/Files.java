package kr.sizniss.manager;

import com.google.gson.*;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Files {

    public static JsonObject config;
    public static JsonObject data;


    public static String getServerTitle() {
        return config.get("ServerTitle").getAsString();
    }
    public static void setServerTitle(String serverTitle) { config.addProperty("ServerTitle", serverTitle); }
    public static String getServerMotd() { return config.get("ServerMotd").getAsString(); }
    public static void setServerMotd(String serverMotd) { config.addProperty("ServerMotd", serverMotd); }


    public Files() {
        loadConfig(); // 콘피그 불러오기
        loadData(); // 데이터 불러오기
    }


    // 콘피그 불러오기 함수
    public static void loadConfig() {
        File file = new File(Manager.plugin.getDataFolder(), "config.json");
        if (!file.exists()) {
            Manager.plugin.saveResource("config.json", false);
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            JsonParser parser = new JsonParser();
            config = parser.parse(reader).getAsJsonObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // 콘피그 저장 함수
    public static void saveConfig() {
        File file = new File(Manager.plugin.getDataFolder(), "config.json");
        if (!file.exists()) {
            Manager.plugin.saveResource("config.json", false);
        }
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);
            writer.append(System.lineSeparator());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 데이터 불러오기 함수
    public static void loadData() {
        File file = new File(Manager.plugin.getDataFolder(), "data.json");
        if (!file.exists()) {
            Manager.plugin.saveResource("data.json", false);
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            JsonParser parser = new JsonParser();
            data = parser.parse(reader).getAsJsonObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // 데이터 저장 함수
    public static void saveData() {
        File file = new File(Manager.plugin.getDataFolder(), "data.json");
        if (!file.exists()) {
            Manager.plugin.saveResource("data.json", false);
        }
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
            writer.append(System.lineSeparator());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // 통계
    public static JsonObject getRecord() {
        if (data.get("Record") == null) { // 통계 데이터가 없을 경우
            data.add("Record", new JsonObject());

            saveData();
        }

        return data.get("Record").getAsJsonObject();
    }
    public static void removeRecord() {
        data.add("Record", null);

        saveData();
    }

    public static Date getRecordDate() {
        if (getRecord().get("Date") == null) {
            getRecord().addProperty("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            saveData();
        }

        Date date;
        try {
            String str = getRecord().get("Date").getAsString();
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch(ParseException e) {
            date = null;
        }

        return date;
    }
    public static void setRecordDate(Date date) {
        getRecord().addProperty("Date", new SimpleDateFormat("yyyy-MM-dd").format(date));

        saveData();
    }
    public static void removeRecordDate() {
        getRecord().add("Date", null);

        saveData();
    }

    public static JsonObject getRecordRatio() {
        if (getRecord().get("Ratio") == null) {
            getRecord().add("Ratio", new JsonObject());

            saveData();
        }

        return getRecord().get("Ratio").getAsJsonObject();
    }
    public static void removeRecordRatio() {
        getRecord().add("Ratio", null);

        saveData();
    }

    public static JsonObject getRecordTeamRatio() {
        if (getRecordRatio().get("Team") == null) {
            getRecordRatio().add("Team", new JsonObject());

            saveData();
        }

        return getRecordRatio().get("Team").getAsJsonObject();
    }
    public static void removeRecordTeamRatio() {
        getRecordRatio().add("Team", null);

        saveData();
    }
    public static int getRecordTeamRatio(String team) {
        if (getRecordTeamRatio().get(team) == null) {
            getRecordTeamRatio().addProperty(team, 0);

            saveData();
        }

        return getRecordTeamRatio().get(team).getAsInt();
    }
    public static void setRecordTeamRatio(String team, int value) {
        getRecordTeamRatio().addProperty(team, value);

        saveData();
    }
    public static void addRecordTeamRatio(String team, int value) {
        int currentRatio = getRecordTeamRatio(team);

        getRecordTeamRatio().addProperty(team, currentRatio + value);

        saveData();
    }
    public static void subtractRecordTeamRatio(String team, int value) {
        int currentRatio = getRecordTeamRatio(team);

        getRecordTeamRatio().addProperty(team, currentRatio - value);

        saveData();
    }
    public static void removeRecordTeamRatio(String team) {
        getRecordTeamRatio().add(team, null);

        saveData();
    }

    public static JsonObject getRecordMapRatio() {
        if (getRecordRatio().get("Map") == null) {
            getRecordRatio().add("Map", new JsonObject());

            saveData();
        }

        return getRecordRatio().get("Map").getAsJsonObject();
    }
    public static void removeRecordMapRatio() {
        getRecordRatio().add("Map", null);

        saveData();
    }
    public static JsonObject getRecordMapRatio(String map) {
        if (getRecordMapRatio().get(map) == null) {
            getRecordMapRatio().add(map, new JsonObject());

            saveData();
        }

        return getRecordMapRatio().get(map).getAsJsonObject();
    }
    public static void removeRecordMapRatio(String map) {
        getRecordMapRatio().add(map, null);

        saveData();
    }
    public static int getRecordMapRatio(String map, String team) {
        if (getRecordMapRatio(map).get(team) == null) {
            getRecordMapRatio(map).addProperty(team, 0);

            saveData();
        }

        return getRecordMapRatio(map).get(team).getAsInt();
    }
    public static void setRecordMapRatio(String map, String team, int value) {
        getRecordMapRatio(map).addProperty(team, value);

        saveData();
    }
    public static void addRecordMapRatio(String map, String team, int value) {
        int currentRatio = getRecordMapRatio(map, team);

        getRecordMapRatio(map).addProperty(team, currentRatio + value);

        saveData();
    }
    public static void subtractRecordMapRatio(String map, String team, int value) {
        int currentRatio = getRecordMapRatio(map, team);

        getRecordMapRatio(map).addProperty(team, currentRatio - value);

        saveData();
    }
    public static void removeRecordMapRatio(String map, String team) {
        getRecordMapRatio(map).add(team, null);

        saveData();
    }

    public static JsonObject getRecordCount() {
        if (getRecord().get("Count") == null) {
            getRecord().add("Count", new JsonObject());

            saveData();
        }

        return getRecord().get("Count").getAsJsonObject();
    }
    public static void removeRecordCount() {
        getRecord().add("Count", null);

        saveData();
    }

    public static JsonObject getRecordClassCount() {
        if (getRecordCount().get("Class") == null) {
            getRecordCount().add("Class", new JsonObject());

            saveData();
        }

        return getRecordCount().get("Class").getAsJsonObject();
    }
    public static void removeRecordClassCount() {
        getRecordCount().add("Class", null);

        saveData();
    }
    public static int getRecordClassCount(String kind) {
        if (getRecordClassCount().get(kind) == null) {
            getRecordClassCount().addProperty(kind, 0);

            saveData();
        }

        return getRecordClassCount().get(kind).getAsInt();
    }
    public static void setRecordClassCount(String kind, int value) {
        getRecordClassCount().addProperty(kind, value);

        saveData();
    }
    public static void addRecordClassCount(String kind, int value) {
        int currentCount = getRecordClassCount(kind);

        getRecordClassCount().addProperty(kind, currentCount + value);

        saveData();
    }
    public static void subtractRecordClassCount(String kind, int value) {
        int currentCount = getRecordClassCount(kind);

        getRecordClassCount().addProperty(kind, currentCount - value);

        saveData();
    }
    public static void removeRecordClassCount(String kind) {
        getRecordClassCount().add(kind, null);

        saveData();
    }

    public static JsonObject getRecordTypeCount() {
        if (getRecordCount().get("Type") == null) {
            getRecordCount().add("Type", new JsonObject());

            saveData();
        }

        return getRecordCount().get("Type").getAsJsonObject();
    }
    public static void removeRecordTypeCount() {
        getRecordCount().add("Type", null);

        saveData();
    }
    public static int getRecordTypeCount(String type) {
        if (getRecordTypeCount().get(type) == null) {
            getRecordTypeCount().addProperty(type, 0);

            saveData();
        }

        return getRecordTypeCount().get(type).getAsInt();
    }
    public static void setRecordTypeCount(String type, int value) {
        getRecordTypeCount().addProperty(type, value);

        saveData();
    }
    public static void addRecordTypeCount(String type, int value) {
        int currentCount = getRecordTypeCount(type);

        getRecordTypeCount().addProperty(type, currentCount + value);

        saveData();
    }
    public static void subtractRecordTypeCount(String type, int value) {
        int currentCount = getRecordTypeCount(type);

        getRecordTypeCount().addProperty(type, currentCount - value);

        saveData();
    }
    public static void removeRecordTypeCount(String type) {
        getRecordTypeCount().add(type, null);

        saveData();
    }

    public static JsonObject getRecordWeaponCount() {
        if (getRecordCount().get("Weapon") == null) {
            getRecordCount().add("Weapon", new JsonObject());

            saveData();
        }

        return getRecordCount().get("Weapon").getAsJsonObject();
    }
    public static void removeRecordWeaponCount() {
        getRecordCount().add("Weapon", null);

        saveData();
    }

    public static JsonObject getRecordMainWeaponCount() {
        if (getRecordWeaponCount().get("Main") == null) {
            getRecordWeaponCount().add("Main", new JsonObject());

            saveData();
        }

        return getRecordWeaponCount().get("Main").getAsJsonObject();
    }
    public static void removeRecordMainWeaponCount() {
        getRecordWeaponCount().add("Main", null);

        saveData();
    }
    public static int getRecordMainWeaponCount(String weapon) {
        if (getRecordMainWeaponCount().get(weapon) == null) {
            getRecordMainWeaponCount().addProperty(weapon, 0);

            saveData();
        }

        return getRecordMainWeaponCount().get(weapon).getAsInt();
    }
    public static void setRecordMainWeaponCount(String weapon, int value) {
        getRecordMainWeaponCount().addProperty(weapon, value);

        saveData();
    }
    public static void addRecordMainWeaponCount(String weapon, int value) {
        int currentCount = getRecordMainWeaponCount(weapon);

        getRecordMainWeaponCount().addProperty(weapon, currentCount + value);

        saveData();
    }
    public static void subtractRecordMainWeaponCount(String weapon, int value) {
        int currentCount = getRecordMainWeaponCount(weapon);

        getRecordMainWeaponCount().addProperty(weapon, currentCount - value);

        saveData();
    }
    public static void removeRecordMainWeaponCount(String weapon) {
        getRecordMainWeaponCount().add(weapon, null);

        saveData();
    }

    public static JsonObject getRecordSubWeaponCount() {
        if (getRecordWeaponCount().get("Sub") == null) {
            getRecordWeaponCount().add("Sub", new JsonObject());

            saveData();
        }

        return getRecordWeaponCount().get("Sub").getAsJsonObject();
    }
    public static void removeRecordSubWeaponCount() {
        getRecordWeaponCount().add("Sub", null);

        saveData();
    }
    public static int getRecordSubWeaponCount(String weapon) {
        if (getRecordSubWeaponCount().get(weapon) == null) {
            getRecordSubWeaponCount().addProperty(weapon, 0);

            saveData();
        }

        return getRecordSubWeaponCount().get(weapon).getAsInt();
    }
    public static void setRecordSubWeaponCount(String weapon, int value) {
        getRecordSubWeaponCount().addProperty(weapon, value);

        saveData();
    }
    public static void addRecordSubWeaponCount(String weapon, int value) {
        int currentCount = getRecordSubWeaponCount(weapon);

        getRecordSubWeaponCount().addProperty(weapon, currentCount + value);

        saveData();
    }
    public static void subtractRecordSubWeaponCount(String weapon, int value) {
        int currentCount = getRecordSubWeaponCount(weapon);

        getRecordSubWeaponCount().addProperty(weapon, currentCount - value);

        saveData();
    }
    public static void removeRecordSubWeaponCount(String weapon) {
        getRecordSubWeaponCount().add(weapon, null);

        saveData();
    }

    public static JsonObject getRecordMeleeWeaponCount() {
        if (getRecordWeaponCount().get("Melee") == null) {
            getRecordWeaponCount().add("Melee", new JsonObject());

            saveData();
        }

        return getRecordWeaponCount().get("Melee").getAsJsonObject();
    }
    public static void removeRecordMeleeWeaponCount() {
        getRecordWeaponCount().add("Melee", null);

        saveData();
    }
    public static int getRecordMeleeWeaponCount(String weapon) {
        if (getRecordMeleeWeaponCount().get(weapon) == null) {
            getRecordMeleeWeaponCount().addProperty(weapon, 0);

            saveData();
        }

        return getRecordMeleeWeaponCount().get(weapon).getAsInt();
    }
    public static void setRecordMeleeWeaponCount(String weapon, int value) {
        getRecordMeleeWeaponCount().addProperty(weapon, value);

        saveData();
    }
    public static void addRecordMeleeWeaponCount(String weapon, int value) {
        int currentCount = getRecordMeleeWeaponCount(weapon);

        getRecordMeleeWeaponCount().addProperty(weapon, currentCount + value);

        saveData();
    }
    public static void subtractRecordMeleeWeaponCount(String weapon, int value) {
        int currentCount = getRecordMeleeWeaponCount(weapon);

        getRecordMeleeWeaponCount().addProperty(weapon, currentCount - value);

        saveData();
    }
    public static void removeRecordMeleeWeaponCount(String weapon) {
        getRecordMeleeWeaponCount().add(weapon, null);

        saveData();
    }


    // 후원 장부
    public static JsonObject getDonation() {
        if (data.get("Donation") == null) { // 후원 장부 데이터가 없을 경우
            data.add("Donation", new JsonObject());

            saveData();
        }

        return data.get("Donation").getAsJsonObject();
    }
    public static void removeDonation() {
        data.add("Donation", null);

        saveData();
    }

    public static int getDonationTotalAmount() {
        if (getDonation().get("TotalAmount") == null) {
            getDonation().addProperty("TotalAmount", 0);

            saveData();
        }

        return getDonation().get("TotalAmount").getAsInt();
    }
    public static void setDonationTotalAmount(int value) {
        getDonation().addProperty("TotalAmount", value);

        saveData();
    }
    public static void addDonationTotalAmount(int value) {
        int currentTotalAmount = getDonationTotalAmount();

        getDonation().addProperty("TotalAmount", currentTotalAmount + value);

        saveData();
    }
    public static void subtractDonationTotalAmount(int value) {
        int currentTotalAmount = getDonationTotalAmount();

        getDonation().addProperty("TotalAmount", currentTotalAmount - value);

        saveData();
    }
    public static void removeDonationTotalAmount() {
        getDonation().add("TotalAmount", null);

        saveData();
    }

    public static JsonObject getDonationPlayerList() {
        if (getDonation().get("PlayerList") == null) {
            getDonation().add("PlayerList", new JsonObject());

            saveData();
        }

        return getDonation().get("PlayerList").getAsJsonObject();
    }
    public static void removeDonationPlayerList() {
        getDonation().add("PlayerList", null);

        saveData();
    }

    public static JsonObject getDonationPlayer(OfflinePlayer player) {
        if (getDonationPlayerList().get(player.getUniqueId().toString()) == null) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("Name", player.getName());
            jsonObject.addProperty("NameList", player.getName());
            jsonObject.addProperty("Amount", 0);

            getDonationPlayerList().add(player.getUniqueId().toString(), jsonObject);

            saveData();
        }

        return getDonationPlayerList().get(player.getUniqueId().toString()).getAsJsonObject();
    }
    public static void removeDonationPlayer(OfflinePlayer player) {
        getDonationPlayerList().add(player.getUniqueId().toString(), null);

        saveData();
    }

    public static String getDonationName(OfflinePlayer player) {
        if (getDonationPlayer(player).get("Name") == null) {
            getDonationPlayer(player).addProperty("Name", player.getName());

            saveData();
        }

        return getDonationPlayer(player).get("Name").getAsString();
    }
    public static void setDonationName(OfflinePlayer player, String name) {
        getDonationPlayer(player).addProperty("Name", name);

        saveData();
    }
    public static void removeDonationName(OfflinePlayer player) {
        getDonationPlayer(player).add("Name", null);

        saveData();
    }

    public static String getDonationNameList(OfflinePlayer player) {
        if (getDonationPlayer(player).get("NameList") == null) {
            getDonationPlayer(player).addProperty("NameList", player.getName());

            saveData();
        }

        return getDonationPlayer(player).get("NameList").getAsString();
    }
    public static void addDonationNameList(OfflinePlayer player, String name) {
        String currentNameList = getDonationNameList(player);

        String[] list = currentNameList.split(", ");
        boolean isContained = false;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(name)) {
                isContained = true;
                break;
            }
        }

        if (!isContained) {
            currentNameList = currentNameList + ", " + name;

            getDonationPlayer(player).addProperty("NameList", currentNameList);

            saveData();
        }
    }
    public static void removeDonationNameList(OfflinePlayer player, String name) {
        String currentNameList = getDonationNameList(player);

        if (currentNameList.contains(name)) {
            String[] nameList = currentNameList.split(", ");

            String newNameList = "";
            for (int i = 0; i < nameList.length; i++) {
                if (nameList[i].equals(name)) {
                    continue;
                }

                newNameList = newNameList + nameList[i];
                if (i < nameList.length - 1) {
                    newNameList = newNameList + ", ";
                }
            }

            getDonationPlayer(player).addProperty("NameList", newNameList);

            saveData();
        }
    }
    public static void removeDonationNameList(OfflinePlayer player) {
        getDonationPlayer(player).add("NameList", null);

        saveData();
    }

    public static int getDonationAmount(OfflinePlayer player) {
        if (getDonationPlayer(player).get("Amount") == null) {
            getDonationPlayer(player).addProperty("Amount", 0);

            saveData();
        }

        return getDonationPlayer(player).get("Amount").getAsInt();
    }
    public static void setDonationAmount(OfflinePlayer player, int value) {
        getDonationPlayer(player).addProperty("Amount", value);

        saveData();
    }
    public static void addDonationAmount(OfflinePlayer player, int value) {
        int currentAmount = getDonationAmount(player);

        getDonationPlayer(player).addProperty("Amount", currentAmount + value);

        saveData();
    }
    public static void subtractDonationAmount(OfflinePlayer player, int value) {
        int currentAmount = getDonationAmount(player);

        getDonationPlayer(player).addProperty("Amount", currentAmount - value);

        saveData();
    }
    public static void removeDonationAmount(OfflinePlayer player) {
        getDonationPlayer(player).add("Amount", null);

        saveData();
    }

    public static JsonObject getDonationLedger() {
        if (getDonation().get("Ledger") == null) {
            getDonation().add("Ledger", new JsonObject());

            saveData();
        }

        return getDonation().get("Ledger").getAsJsonObject();
    }
    public static void removeDonationLedger() {
        getDonation().add("Ledger", null);

        saveData();
    }

    public static void addDonationBreakdown(Date date, OfflinePlayer player, int amount) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("UUID", player.getUniqueId().toString());
        jsonObject.addProperty("Name", player.getName());
        jsonObject.addProperty("Amount", amount);

        getDonationLedger().add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), jsonObject);

        saveData();
    }


    // 게임 중인 플레이어
    public static ArrayList<String> getPlayingUser() {
        if (data.get("PlayingUser") == null) { // 게임 중인 플레이어 데이터가 없을 경우
            data.add("PlayingUser", new JsonArray());

            saveData();
        }

        ArrayList<String> playingUser = new ArrayList<String>();

        for (JsonElement jsonElement : data.get("PlayingUser").getAsJsonArray()) {
            playingUser.add(jsonElement.getAsString());
        }

        return playingUser;
    }
    public static void setPlayingUser(ArrayList<String> userNameList) {
        JsonArray jsonArray = new JsonArray();

        for (String userName : userNameList) {
            jsonArray.add(userName);
        }

        data.add("PlayingUser", jsonArray);

        saveData();
    }
    public static void addPlayingUser(String userName) {
        JsonArray jsonArray = new JsonArray();

        ArrayList<String> playingUser = getPlayingUser();
        if (!playingUser.contains(userName)) {
            playingUser.add(userName);
        }
        for (String playingUserName : playingUser) {
            jsonArray.add(playingUserName);
        }

        data.add("PlayingUser", jsonArray);

        saveData();
    }
    public static void removePlayingUser(String userName) {
        JsonArray jsonArray = new JsonArray();

        ArrayList<String> playingUser = getPlayingUser();
        if (playingUser.contains(userName)) {
            playingUser.remove(userName);
        }
        for (String playingUserName : playingUser) {
            jsonArray.add(playingUserName);
        }

        data.add("PlayingUser", jsonArray);

        saveData();
    }
    public static void removePlayingUser() {
        data.add("PlayingUser", null);

        saveData();
    }


    // 마인리스트
    public static JsonObject getMinelist() {
        if (data.get("Minelist") == null) {
            data.add("Minelist", new JsonObject());

            saveData();
        }

        return data.get("Minelist").getAsJsonObject();
    }
    public static void removeMinelist() {
        data.add("Minelist", null);

        saveData();
    }

    public static Date getMinelistDate() {
        if (getMinelist().get("Date") == null) {
            getMinelist().addProperty("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            saveData();
        }

        Date date;
        try {
            String str = getMinelist().get("Date").getAsString();
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch(ParseException e) {
            date = null;
        }

        return date;
    }
    public static void setMinelistDate(Date date) {
        getMinelist().addProperty("Date", new SimpleDateFormat("yyyy-MM-dd").format(date));

        saveData();
    }
    public static void removeMinelistDate() {
        getMinelist().add("Date", null);

        saveData();
    }

    public static ArrayList<UUID> getMinelistUser() {
        if (getMinelist().get("User") == null) { // 마인리스트에 추천한 플레이어가 없을 경우
            getMinelist().add("User", new JsonArray());

            saveData();
        }

        ArrayList<UUID> minelistUser = new ArrayList<UUID>();

        for (JsonElement jsonElement : getMinelist().get("User").getAsJsonArray()) {
            minelistUser.add(UUID.fromString(jsonElement.getAsString()));
        }

        return minelistUser;
    }
    public static void setMinelistUser(ArrayList<UUID> userUuidList) {
        JsonArray jsonArray = new JsonArray();

        for (UUID userUuid : userUuidList) {
            jsonArray.add(userUuid.toString());
        }

        getMinelist().add("User", jsonArray);

        saveData();
    }
    public static void addMinelistUser(UUID userUuid) {
        JsonArray jsonArray = new JsonArray();

        ArrayList<UUID> minelistUser = getMinelistUser();
        if (!minelistUser.contains(userUuid)) {
            minelistUser.add(userUuid);
        }
        for (UUID minelistUserUuid : minelistUser) {
            jsonArray.add(minelistUserUuid.toString());
        }

        getMinelist().add("User", jsonArray);

        saveData();
    }
    public static void removeMinelistUser(UUID userUuid) {
        JsonArray jsonArray = new JsonArray();

        ArrayList<UUID> minelistUser = getMinelistUser();
        if (minelistUser.contains(userUuid)) {
            minelistUser.remove(userUuid);
        }
        for (UUID minelistUserUuid : minelistUser) {
            jsonArray.add(minelistUserUuid.toString());
        }

        getMinelist().add("User", jsonArray);

        saveData();
    }
    public static void removeMinelistUser() {
        getMinelist().add("User", null);

        saveData();
    }


    // 이벤트 진행 중인 플레이어
    public static JsonObject getEvent() {
        if (data.get("Event") == null) {
            data.add("Event", new JsonObject());

            saveData();
        }

        return data.get("Event").getAsJsonObject();
    }
    public static void removeEvent() {
        data.add("Event", null);

        saveData();
    }

    public static int getEventCount(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();

        if (getEvent().get(uuid) == null) {
            getEvent().addProperty(uuid, 0);

            saveData();
        }

        return getEvent().get(uuid).getAsInt();
    }
    public static void setEventCount(OfflinePlayer player, int value) {
        String uuid = player.getUniqueId().toString();

        getEvent().addProperty(uuid, value);

        saveData();
    }
    public static void addEventCount(OfflinePlayer player, int value) {
        String uuid = player.getUniqueId().toString();

        getEvent().addProperty(uuid, getEventCount(player) + value);

        saveData();
    }
    public static void subtractEventCount(OfflinePlayer player, int value) {
        String uuid = player.getUniqueId().toString();

        getEvent().addProperty(uuid, getEventCount(player) - value);

        saveData();
    }
    public static void removeEventCount(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();

        getEvent().add(uuid, null);

        saveData();
    }

}
