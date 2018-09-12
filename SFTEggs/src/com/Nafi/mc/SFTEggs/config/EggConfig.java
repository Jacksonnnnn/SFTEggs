package com.Nafi.mc.SFTEggs.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.Nafi.mc.SFTEggs.EggMain;
import com.Nafi.mc.SFTEggs.handler.EggHandler;


public class EggConfig {
	
	private static FileConfiguration RewardConfig;
	private static File RewardFile;
	
	private static FileConfiguration EggTrackerConfig;
	private static File EggTrackerFile;
	
	private static FileConfiguration IncubationConfig;
	private static File IncubationFile;
	
	public static void loadRewards()
	{
		  File RewardFile = new File("plugins/SFTEggs/rewards.yml");
		  
		  if(!RewardFile.exists())
		  {
			  EggMain.getPlugin.saveResource("rewards.yml",false);
		  }
		  
		  for(String perm: getRewardConfig().getConfigurationSection("Rewards").getKeys(false))
		  {
			  EggHandler.permMap.put(perm, getRewardConfig().getStringList("Rewards." + perm));
		  }
		  
	}
	
	public static FileConfiguration getRewardConfig()
	{
			if (RewardConfig == null) {
				reloadRewardConfig();
		    }
		    return RewardConfig;
		
	}
	
	public static void reloadRewardConfig() {
	    if (RewardConfig == null) {
	    	RewardFile = new File(EggMain.getPlugin.getDataFolder(), "rewards.yml");
	    }
	    RewardConfig = YamlConfiguration.loadConfiguration(RewardFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(EggMain.getPlugin.getResource("rewards.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        RewardConfig.setDefaults(defConfig);
	    }
	}
	
	public static void loadEggTracker()
	{
		  File EggTrackerFile = new File("plugins/SFTEggs/EggTracker.yml");
		  
		  if(!EggTrackerFile.exists())
		  {
			  EggMain.getPlugin.saveResource("EggTracker.yml",false);
		  }
		  
		  for(String player: getEggTrackerConfig().getConfigurationSection("EggTracker").getKeys(false))
		  {
			  List<ItemStack> EggList = new ArrayList<ItemStack>();
			  for(String e: getEggTrackerConfig().getStringList("EggTracker." + player))
			  {
				  String stat = e.split(";")[0];
				  int progress = Integer.parseInt(e.split(";")[1]);
				  int goal = Integer.parseInt(e.split(";")[2]);
				  String reward = e.split(";")[3];
				  short eggType = Short.parseShort(e.split(";")[4]);
				  
				 EggList.add(EggHandler.reBuildEgg(stat, progress, goal, reward, eggType));
			  }
			  EggHandler.eggMap.put(player, EggList);
		  }
		  
	}
	
	public static FileConfiguration getEggTrackerConfig()
	{
			if (EggTrackerConfig == null) {
				reloadEggTrackerConfig();
		    }
		    return EggTrackerConfig;
		
	}
	
	public static void reloadEggTrackerConfig() {
	    if (EggTrackerConfig == null) {
	    	EggTrackerFile = new File(EggMain.getPlugin.getDataFolder(), "EggTracker.yml");
	    }
	    EggTrackerConfig = YamlConfiguration.loadConfiguration(EggTrackerFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(EggMain.getPlugin.getResource("EggTracker.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        EggTrackerConfig.setDefaults(defConfig);
	    }
	}
	
	public static void saveEggTracker()
	{
		for(String p : EggHandler.eggMap.keySet())
		{
			List<String> EggStrings = new ArrayList<String>();
			for(ItemStack i : EggHandler.eggMap.get(p))
			{
				EggStrings.add(EggHandler.eggToString(i));
			}
			getEggTrackerConfig().set("EggTracker." + p, EggStrings);
		}
		
		
		
	    if (EggTrackerConfig == null || EggTrackerFile == null) {
	        return;
	    }
	    try {
	    	getEggTrackerConfig().save(EggTrackerFile);
	    } catch (IOException ex) {}
	}
	
	public static void loadIncubation()
	{
		  File IncubationFile = new File("plugins/SFTEggs/IncubationTracker.yml");
		  
		  if(!IncubationFile.exists())
		  {
			  EggMain.getPlugin.saveResource("IncubationTracker.yml",false);
		  }
		  
		  for(String perm: getIncubationConfig().getConfigurationSection("Incubation").getKeys(false))
		  {
			  EggHandler.IncMap.put(perm, getIncubationConfig().getInt("Incubation." + perm));
		  }
		  
	}
	
	public static FileConfiguration getIncubationConfig()
	{
			if (IncubationConfig == null) {
				reloadIncubationConfig();
		    }
		    return IncubationConfig;
		
	}
	
	public static void reloadIncubationConfig() {
	    if (IncubationConfig == null) {
	    	IncubationFile = new File(EggMain.getPlugin.getDataFolder(), "IncubationTracker.yml");
	    }
	    IncubationConfig = YamlConfiguration.loadConfiguration(IncubationFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(EggMain.getPlugin.getResource("IncubationTracker.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        IncubationConfig.setDefaults(defConfig);
	    }
	}
	
	public static void saveIncubationConfig()
	{
		for(String s: EggHandler.IncMap.keySet())
		{
			getIncubationConfig().set("Incubation." + s, EggHandler.IncMap.get(s));
		}
		
	    if (IncubationConfig == null || IncubationFile == null) {
	        return;
	    }
	    try {
	    	getIncubationConfig().save(IncubationFile);
	    } catch (IOException ex) {}
	}
	
	
}
