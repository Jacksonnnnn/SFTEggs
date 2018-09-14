package com.Nafi.mc.SFTEggs.config;

import com.Nafi.mc.SFTEggs.EggMain;
import com.Nafi.mc.SFTEggs.handler.EggHandler;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class EggConfig {

    private static YamlConfiguration RewardConfig;
    private static File RewardFile;

    private static YamlConfiguration EggTrackerConfig;
    private static File EggTrackerFile;

    private static YamlConfiguration IncubationConfig;
    private static File IncubationFile;

    public static void loadRewards() {
        File RewardFile = new File(EggMain.getInstance().getDataFolder(), "rewards.yml");

        if (!RewardFile.exists()) {
            EggMain.getInstance().saveResource("rewards.yml", false);
        }

        ConfigurationSection rewardSection = getRewardConfig().getConfigurationSection("Rewards");
        for (String perm : rewardSection.getKeys(false)) {
            EggHandler.permMap.put(perm, rewardSection.getStringList("perm"));
        }

    }

    public static FileConfiguration getRewardConfig() {
        if (RewardConfig == null) {
            reloadRewardConfig();
        }
        return RewardConfig;

    }

    public static void reloadRewardConfig() {
        if (RewardConfig == null) {
            RewardFile = new File(EggMain.getInstance().getDataFolder(), "rewards.yml");
        }
        RewardConfig = YamlConfiguration.loadConfiguration(RewardFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(EggMain.getInstance().getResource("rewards.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            RewardConfig.setDefaults(defConfig);
        }
    }

    /**
     * Loads from file
     * Gets serialized ItemStacks from player UUID, deserializes and then puts in the player's hashmap entry
     */
    public static void loadEggTracker() {
        File EggTrackerFile = new File(EggMain.getInstance().getDataFolder(), "EggTracker.yml");

        if (!EggTrackerFile.exists()) {
            EggMain.getInstance().saveResource("EggTracker.yml", false);
        }

        YamlConfiguration configuration = getEggTrackerConfig();
        for (String player : configuration.getKeys(false)) {
            UUID playerUUID = UUID.fromString(player);
            List<ItemStack> eggStacks = new ArrayList<>();
            ConfigurationSection playerSection = configuration.getConfigurationSection(player);
            for (String stack : playerSection.getKeys(false))
                eggStacks.add(playerSection.getItemStack(stack));
            EggHandler.eggMap.put(playerUUID, eggStacks);
        }

    }

    public static YamlConfiguration getEggTrackerConfig() {
        if (EggTrackerConfig == null) {
            reloadEggTrackerConfig();
        }
        return EggTrackerConfig;

    }

    /**
     * Looks inside the jar in case the file data goes missing; recreates
     */
    public static void reloadEggTrackerConfig() {
        if (EggTrackerConfig == null) {
            EggTrackerFile = new File(EggMain.getInstance().getDataFolder(), "EggTracker.yml");
        }
        EggTrackerConfig = YamlConfiguration.loadConfiguration(EggTrackerFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(EggMain.getInstance().getResource("EggTracker.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            EggTrackerConfig.setDefaults(defConfig);
        }
    }

    /**
     * Parses through UUIDs & Spawn Egg lists
     * Saves each as a collection of values under a UUID
     */
    public static void saveEggTracker() {
        YamlConfiguration configuration = getEggTrackerConfig();
        EggHandler.eggMap.forEach((player, list) -> {
            int count = 0;
            ConfigurationSection playerSection = configuration.getConfigurationSection(player.toString());
            for (ItemStack stack : list) {
                playerSection.set("" + count++, stack);
            }
        });

        //TODO rework this bit, you're just going to throw the data away if you can't find the file???
        if (EggTrackerConfig == null || EggTrackerFile == null) {
            return;
        }
        try {
            configuration.save(EggTrackerFile);
        } catch (IOException ignored) {
        }
    }

    public static void loadIncubation() {
        File IncubationFile = new File(EggMain.getInstance().getDataFolder(), "IncubationTracker.yml");

        if (!IncubationFile.exists()) {
            EggMain.getInstance().saveResource("IncubationTracker.yml", false);
        }

        for (String player : getIncubationConfig().getKeys(false)) {
            EggHandler.IncMap.put(UUID.fromString(player), getIncubationConfig().getInt(player));
        }

    }

    public static FileConfiguration getIncubationConfig() {
        if (IncubationConfig == null) {
            reloadIncubationConfig();
        }
        return IncubationConfig;

    }

    public static void reloadIncubationConfig() {
        if (IncubationConfig == null) {
            IncubationFile = new File(EggMain.getInstance().getDataFolder(), "IncubationTracker.yml");
        }
        IncubationConfig = YamlConfiguration.loadConfiguration(IncubationFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(EggMain.getInstance().getResource("IncubationTracker.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            IncubationConfig.setDefaults(defConfig);
        }
    }

    public static void saveIncubationConfig() {
        Configuration incConfig = getIncubationConfig();
        EggHandler.IncMap.forEach((uuid, num) -> incConfig.set(uuid.toString(), num));

        //TODO fix this, is the data just going to disappear?
        if (IncubationConfig == null || IncubationFile == null) {
            return;
        }
        try {
            getIncubationConfig().save(IncubationFile);
        } catch (IOException ignored) {
        }
    }


}
