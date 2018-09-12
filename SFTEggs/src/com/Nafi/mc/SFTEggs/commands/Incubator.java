package com.Nafi.mc.SFTEggs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Nafi.mc.SFTEggs.handler.EggHandler;

public class Incubator implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED  + "You have to be a player to open an incubator!");
			return true;
		}
		
		Player p = (Player) sender;
		
		
		if(!sender.hasPermission("sfteggs.incubator"))
		{
			sender.sendMessage(ChatColor.RED  + "You do not have permission to perform this command");
			return true;
		}
		
		EggHandler.updateEggs(p);
		p.openInventory(EggHandler.getIncubator(p));
		return false;
	}
}
