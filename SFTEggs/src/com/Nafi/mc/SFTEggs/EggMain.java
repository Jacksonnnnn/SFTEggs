package com.Nafi.mc.SFTEggs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nafi.mc.SFTEggs.commands.EditIncubator;
import com.Nafi.mc.SFTEggs.commands.GiveEgg;
import com.Nafi.mc.SFTEggs.commands.Incubator;
import com.Nafi.mc.SFTEggs.config.EggConfig;
import com.Nafi.mc.SFTEggs.handler.IncubatorGUI;
import com.Nafi.mc.SFTEggs.handler.RewardDeliver;

public class EggMain extends JavaPlugin{
	
	public static EggMain getPlugin;
	
	
	@Override
	public void onEnable()
	{
		getPlugin = this;
		EggConfig.loadRewards();
		EggConfig.loadEggTracker();
		EggConfig.loadIncubation();
		this.getCommand("giveegg").setExecutor(new GiveEgg());
		this.getCommand("incubator").setExecutor(new Incubator());
		this.getCommand("editincubator").setExecutor(new EditIncubator());
		Bukkit.getPluginManager().registerEvents(new IncubatorGUI(), this);
		Bukkit.getPluginManager().registerEvents(new RewardDeliver(), this);
	}
	
	@Override
	public void onDisable()
	{
		EggConfig.saveEggTracker();
		EggConfig.saveIncubationConfig();
	}
	
	

}
