package com.Nafi.mc.SFTEggs.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Nafi.mc.SFTEggs.EggType;
import com.Nafi.mc.SFTEggs.Statistic;


public class EggHandler {

	public static HashMap<String, List<String>> permMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<ItemStack>> eggMap = new HashMap<String, List<ItemStack>>();
	public static HashMap<String, Integer> IncMap = new HashMap<String, Integer>();
	
	public static ItemStack getEgg(Statistic stat, int goal, String reward, EggType type)
	{		
		ItemStack i = new ItemStack(Material.MONSTER_EGG, 1, type.getValue()) ;
		ItemMeta iMeta = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Genuine Hatchable SFT Egg");
		lore.add(ChatColor.GOLD + stat.getUsableString() + ":" + ChatColor.AQUA +" 0/" + goal + " "+ stat.getUnit());
		lore.add(ChatColor.GOLD + "Reward: " + ChatColor.AQUA + reward);
		Random r = new Random();
		lore.add(ChatColor.YELLOW + "#" + type.getValue()  + ":" + r.nextInt(10001));
		lore.add(ChatColor.RED + "Place in your /hatcher!");
		iMeta.setLore(lore);
		iMeta.setDisplayName(ChatColor.GREEN + "Hatchable EGG");
		i.setItemMeta(iMeta);
		return i;
	}	
	
	public static ItemStack reBuildEgg(String statName,int progress, int goal, String reward, short type)
	{		
		ItemStack i = new ItemStack(Material.MONSTER_EGG, 1, (short) type) ;
		ItemMeta iMeta = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Genuine Hatchable SFT Egg");
		lore.add(ChatColor.GOLD + statName + ":" + ChatColor.AQUA + " "+ progress + "/" + goal + " "+ Statistic.parse.fromUsableString(statName).getUnit());
		lore.add(ChatColor.GOLD + "Reward: " + ChatColor.AQUA + reward);
		Random r = new Random();
		lore.add(ChatColor.YELLOW + "#" + type  + ":" + r.nextInt(10001));
		lore.add(ChatColor.RED + "Place in your /hatcher!");
		iMeta.setLore(lore);
		iMeta.setDisplayName(ChatColor.GREEN + "Hatchable EGG");
		i.setItemMeta(iMeta);
		return i;
	}
	
	
	
	public static Inventory getIncubator(Player p)
	{
		Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "              Incubator");
		inv.setMaxStackSize(1);
		ItemStack barrierStack = new ItemStack(Material.BARRIER);
		ItemMeta bMeta = barrierStack.getItemMeta();
		bMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "LOCKED");
		List<String> bList = new ArrayList<String>();
		bList.add(ChatColor.GREEN + "☀" + ChatColor.GRAY + " Place your egg in the open slot");
		bList.add(ChatColor.GRAY + "to start it hatching!");
		bList.add(ChatColor.GREEN + "☀" + ChatColor.GRAY + " Get more slots through /buy");
		bMeta.setLore(bList);
		barrierStack.setItemMeta(bMeta);
	

		if(IncMap.containsKey(p.getName()))
		{
			int slots = IncMap.get(p.getName());
			for(int i = slots; i < 5; i++)
			{
				inv.setItem(i, barrierStack);
			}
		}
		else
		{
			for(int i = 1; i < 5; i++)
			{
				inv.setItem(i,barrierStack);
			}
		}
		
		if(eggMap.containsKey(p.getName()))
		{
			for(ItemStack egg: eggMap.get(p.getName()))
			{
				try
				{
					inv.setItem(inv.firstEmpty(), egg);
				}
				catch(Exception e)
				{
					return inv;
				}		
			}
		}
		
		return inv;
	}
	
	public static void updateEggs(Player p)
	{
		if(eggMap.containsKey(p.getName()))
		{
			List<ItemStack> eggList = new ArrayList<ItemStack>();
			for(ItemStack stack: eggMap.get(p.getName()))
			{
				List<String> loreList = stack.getItemMeta().getLore();
				String stat = ChatColor.stripColor(loreList.get(1).split(":")[0]);
				int curProgress = Integer.parseInt(ChatColor.stripColor(loreList.get(1).split("/")[0].split(":")[1].replaceAll(" ", "")));
				int goal = Integer.parseInt(loreList.get(1).split("/")[1].split(" ")[0]);
				Statistic s = Statistic.parse.fromUsableString(stat);
				int progress = 0;
				if(!s.isMobKill())
				{
					progress = p.getStatistic(s.getStatistic());
				}
				else
				{
					progress = p.getStatistic(s.getStatistic(), s.getEntity());
				}
				if(Statistic.parse.fromUsableString(stat).getUnit().equalsIgnoreCase("m"))
				{
					progress = (int) progress/100;
				}
				else if(Statistic.parse.fromUsableString(stat).getUnit().equalsIgnoreCase("sec"))
				{
					progress = (int) progress/(20);
				}
				if((progress + curProgress) >= goal)
				{
					progress = goal;
					if(loreList.size() == 5)
					{
						loreList.set(4, ChatColor.GREEN + "" + ChatColor.BOLD + "Place your egg to claim your reward!");
					}
				}
				else
				{
					progress += curProgress;
				}
				loreList.set(1, ChatColor.GOLD + stat + ": "+ ChatColor.AQUA + progress + "/" + goal + " "+ Statistic.parse.fromUsableString(stat).getUnit());

				ItemMeta sMeta = stack.getItemMeta();
				sMeta.setLore(loreList);
				stack.setItemMeta(sMeta);
				eggList.add(stack);
			}
			eggMap.put(p.getName(), eggList);
		}
	}
	
	
	
	
	public static boolean isGenuine(ItemStack i)
	{
		if(i.getType() == Material.MONSTER_EGG)
		{
			if(i.getItemMeta().hasLore())
			{
				for(String line : i.getItemMeta().getLore())
				{
					if(line.equalsIgnoreCase(ChatColor.RED +  "Genuine Hatchable SFT Egg"))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isRewardable(ItemStack i)
	{
		if(i.getType() == Material.MONSTER_EGG)
		{
			if(i.getItemMeta().hasLore())
			{
				for(String line : i.getItemMeta().getLore())
				{
					if(ChatColor.stripColor(line).equalsIgnoreCase("Place your egg to claim your reward!"))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public static String eggToString(ItemStack i)
	{
		if(isGenuine(i) == false)
		{
			return "Item is not a genuine egg";
		}
		
		StringBuilder sb = new StringBuilder();
		for(String l : i.getItemMeta().getLore())
		{
			l = ChatColor.stripColor(l);
			if(l.contains("/") && !l.contains("!"))
			{
				sb.append(l.split(":")[0] + ";");
				sb.append(l.split(":")[1].split("/")[0].replaceAll(" ","") + ";");
				sb.append(l.split("/")[1].split(" ")[0]+ ";");
			}
			else if(l.startsWith("Reward"))
			{
				sb.append(l.split(":")[1].replaceAll(" ", "") + ";");
			}
			else if(l.startsWith("#"))
			{
				sb.append(l.replace("#", "").split(":")[0]);
			}
		}
		return sb.toString();
	}
	
	
	
	
}
