package com.Nafi.mc.SFTEggs.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Nafi.mc.SFTEggs.EggType;
import com.Nafi.mc.SFTEggs.Statistic;
import com.Nafi.mc.SFTEggs.handler.EggHandler;

public class GiveEgg implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
	{
		if((sender instanceof Player) &&  !sender.hasPermission("sfteggs.giveegg"))
		{
			sender.sendMessage(ChatColor.RED  + "You do not have permission to perform this command");
			return true;
		}
		
		Set<String> StatSet = new HashSet<String>();
		
		for(Statistic s : Statistic.values())
		{
			StatSet.add(s.toString());
		}

		if(args.length > 0)
		{
			if(!StatSet.contains(args[0].toUpperCase()))
			{
				StringBuilder sb = new StringBuilder(ChatColor.GREEN + "ACCEPTABLE STATISTICS: ");
				for(String stat: StatSet)
				{
					sb.append(ChatColor.GRAY + stat.toString() + ChatColor.WHITE + ", ");
				}
				sender.sendMessage(sb.toString());
				return true;
			}
		
			if(args.length > 1)
			{
				int goal = 0;
				try
				{
					goal = Integer.parseInt(args[1]);
				}
				catch(Exception e)
				{
					sender.sendMessage(ChatColor.GREEN + "ACCEPTABLE GOALS:" +ChatColor.GRAY + " '100', '5' etc.");
					return true;
				}
				if(args.length > 2)
				{
					if(!EggHandler.permMap.containsKey(args[2]))
					{
						StringBuilder sb = new StringBuilder(ChatColor.RED + "ACCEPTABLE REWARDS: ");
				
						for(String s: EggHandler.permMap.keySet())
						{
							sb.append(ChatColor.GRAY + s + ChatColor.WHITE + ", ");
						}
				
						sender.sendMessage(sb.toString());
						return true;
					}
				
					if(args.length > 3)
					{
						EggType e;
		
						if(EggType.parse.toEggValue(args[3]) == -1)
						{
							StringBuilder sb = new StringBuilder(ChatColor.RED + "ACCEPTABLE EGGTYPES: ");
							for(EggType egg: EggType.values())
							{
								sb.append(ChatColor.GRAY + egg.name() + ChatColor.WHITE + ", ");
							}
							sender.sendMessage(sb.toString());
							return true;
						}
		
						e = EggType.parse.toEggType(args[3]);
		
						if(args.length > 4)	
						{
							Player p = Bukkit.getPlayer(args[4]);
							if(p == null)
							{
								sender.sendMessage(ChatColor.RED + "Player is not online");
								return true;
							}
						
							ItemStack PlayerEgg = EggHandler.getEgg(Statistic.parse.fromString(args[0]), goal, args[2], e);
		
							p.getInventory().addItem(PlayerEgg);
							return true;
						}
					}
				}
			}
		}
		
		sender.sendMessage("/" + cmd.getLabel() + " <statistic> <goal> <reward> <eggtype> <player>");
		return false;
	}
	
	
}
