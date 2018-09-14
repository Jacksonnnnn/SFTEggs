package com.Nafi.mc.SFTEggs.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RewardDeliver implements Listener {

    @EventHandler
    public void onEggClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (EggHandler.isGenuine(e.getItem())) {
                    e.setCancelled(true);
                    if (EggHandler.isRewardable(e.getItem())) {
                        if (!deliverReward(e.getPlayer(), e.getItem())) {
                            return;
                        }
                        e.getItem().setAmount(0);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "Place this egg in your /hatcher!");
                    }
                }
            }
        }
    }


    private boolean deliverReward(Player p, ItemStack i) {
        String reward = ChatColor.stripColor(i.getItemMeta().getLore().get(2).split(":")[1].replaceAll(" ", ""));
        try {
            for (String cmd : EggHandler.permMap.get(reward)) {
                cmd = cmd.replace("{target}", p.getName()).replace("/", "");
                ConsoleCommandSender sender = Bukkit.getConsoleSender();
                Bukkit.dispatchCommand(sender, cmd);
            }
            return true;
        } catch (NullPointerException e) {
            p.sendMessage(ChatColor.RED + "Incorrect or old reward. Contact an Admin+ to refund your egg.");
            return false;
        }
    }


}
