package com.Nafi.mc.SFTEggs.handler;

import com.Nafi.mc.SFTEggs.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class IncubatorGUI implements Listener {


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().replaceAll(" ", "").equalsIgnoreCase("Incubator") && e.getInventory().getType() == InventoryType.HOPPER && e.getInventory().getMaxStackSize() == 1) {
            Inventory i = e.getInventory();
            List<ItemStack> stackList = new ArrayList<ItemStack>();
            for (ItemStack item : i.getContents()) {
                if (item != null) {
                    if (EggHandler.isGenuine(item)) {
                        stackList.add(item);
                    }
                }
            }
            EggHandler.eggMap.put(e.getPlayer().getUniqueId(), stackList);
            for (Statistic stat : Statistic.values()) {
                Player p = (Player) e.getPlayer();
                if (!stat.isMobKill()) {
                    p.setStatistic(stat.getStatistic(), 0);
                } else {
                    p.setStatistic(stat.getStatistic(), stat.getEntity(), 0);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().replaceAll(" ", "").equalsIgnoreCase("Incubator") && e.getInventory().getType() == InventoryType.HOPPER && e.getInventory().getMaxStackSize() == 1) {
            if (e.getCurrentItem() != null) {
                if (e.isShiftClick()) {
                    e.setCancelled(true);
                    return;
                }


                if (!EggHandler.isGenuine(e.getCurrentItem())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDispenserUse(BlockDispenseEvent e) {
        if (EggHandler.isGenuine(e.getItem())) {
            e.setCancelled(true);
        }
    }


}
