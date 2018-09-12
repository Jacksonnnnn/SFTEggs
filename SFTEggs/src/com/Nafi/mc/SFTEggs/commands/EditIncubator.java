package com.Nafi.mc.SFTEggs.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.Nafi.mc.SFTEggs.handler.EggHandler;

public class EditIncubator implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
	{
		if((sender instanceof Player) &&  !sender.hasPermission("sfteggs.editincubator"))
		{
			sender.sendMessage(ChatColor.RED  + "You do not have permission to perform this command");
			return true;
		}
		
		if(args.length > 1){
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
		
			if(args[0].equalsIgnoreCase("get"))
			{

				if(!EggHandler.IncMap.containsKey(target.getName()))
				{
					sender.sendMessage(ChatColor.GREEN + target.getName() + " has 1 incubation slot");
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + target.getName() + " has "+ EggHandler.IncMap.get(target.getName()) + " incubation slot(s)");
				}
			return true;
			}
			
			if(args.length >  2)
			{
			
				int amount = 0;
				
				ConsoleCommandSender console = Bukkit.getConsoleSender();
				
				try
				{
					amount = Integer.parseInt(args[2]);
				}
				catch(Exception e)
				{
					sender.sendMessage(ChatColor.RED +  "Invalid amount");
					return true;
				}
				
				if(amount < 1)
				{
					sender.sendMessage(ChatColor.RED + "Amount must be greater than or equal to 1");
					return true;
				}
				else if(amount > 5)
				{
					sender.sendMessage(ChatColor.RED + "Amount must be less than or equal to 5");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("add"))
				{

					if(!EggHandler.IncMap.containsKey(target.getName()))
					{
						console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " previous incubation slots: 1" );
						EggHandler.IncMap.put(target.getName(), amount + 1);

					}
					else
					{
						if(EggHandler.IncMap.get(target.getName()) + amount > 5)
						{
							sender.sendMessage(ChatColor.RED + "Failed to add incubation slot to " + target.getName());
							sender.sendMessage(ChatColor.RED +  "Amount is too high or player has reached the max amount of slots.");
							console.sendMessage(ChatColor.RED + "[SFTEggs] Failed attempt to add incubation slots to "+ target.getName());
							return true;
						}
						
						console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " previous incubation slots: " + EggHandler.IncMap.get(target.getName()) );
						EggHandler.IncMap.put(target.getName(), EggHandler.IncMap.get(target.getName()) + amount);
					}
					sender.sendMessage(ChatColor.GREEN + "Added " + amount + " incubation slots to " + target.getName());
					console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " current incubation slots: " + EggHandler.IncMap.get(target.getName()));
					return true;
				}
				
				if(args[0].equalsIgnoreCase("subtract"))
				{

					if(!EggHandler.IncMap.containsKey(target.getName()) || EggHandler.IncMap.get((target.getName())) == 1)
					{
						sender.sendMessage(ChatColor.RED + "Player already has the lowest amount of incubation slots(1)");
					}
					else
					{
						console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " previous incubation slots: " + EggHandler.IncMap.get(target.getName()));
						EggHandler.IncMap.put(target.getName(), EggHandler.IncMap.get(target.getName()) - amount);
						sender.sendMessage(ChatColor.GREEN + "Subtracted " + amount + " incubation slots from " + target.getName());
						return true;
					}


					console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " current incubation slots: " + EggHandler.IncMap.get(target.getName()));
					return true;
				}
				
				if(args[0].equalsIgnoreCase("set"))
				{

					if(!EggHandler.IncMap.containsKey(target.getName()))
					{
						console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " previous incubation slots: 1");
					}
					else
					{
						console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " previous incubation slots: " + EggHandler.IncMap.get(target.getName()));
					}
					EggHandler.IncMap.put(target.getName(), amount);
					sender.sendMessage(ChatColor.GREEN + "Set " + amount + " incubation slots to " + target.getName());
					console.sendMessage(ChatColor.GREEN + "[SFTEggs] " + target.getName() + " current incubation slots: " + EggHandler.IncMap.get(target.getName()));
					return true;
				}
			}
				
		}	
		
			sender.sendMessage(ChatColor.RED + "/" + cmd.getName() + " <add/subtract/set/get> <player> [amount]");

		return false;
	}
}
