package com.noolart.noolartpaperplugin.listener;

import com.noolart.noolartpaperplugin.Materials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MyListener1 implements Listener {
    @EventHandler
    public void interact(PlayerInteractEvent e) {
        Action a = e.getAction();

        if (a == Action.RIGHT_CLICK_AIR) {
            Player p = e.getPlayer();
            ItemStack item = p.getItemInHand();

            if (item.getType() == Material.WOODEN_HOE) {
                long sum = 0;
                Location cur = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 0.6, p.getLocation().getBlockZ());

                for (int x = p.getLocation().add(0, 0.6, 0).getBlockX() - 15; x <= p.getLocation().add(0, 0.6, 0).getBlockX() + 15; x++) {
                    for (int y = p.getLocation().add(0, 0.6, 0).getBlockY() - 15; y <= p.getLocation().add(0, 0.6, 0).getBlockY() - 1; y++) {
                        for (int z = p.getLocation().add(0, 0.6, 0).getBlockZ() - 15; z <= p.getLocation().add(0, 0.6, 0).getBlockZ() + 15; z++) {

                            Location loc = new Location(p.getWorld(), x, y, z);

                            //loc.getBlock().setType(Material.POLISHED_ANDESITE );
                            //Bukkit.broadcastMessage(Double.parseDouble(Materials.getString(loc.getBlock().getType().toString().toLowerCase(), "Density"))+"");


                            double sol = Double.parseDouble(Materials.getString(loc.getBlock().getType().toString().toLowerCase(), "Density"));
                            sum += sol / (loc.distance(cur) * loc.distance(cur));
                        }
                    }
                }

                p.sendMessage(ChatColor.DARK_GREEN + "Гравитационное поле в данной точке: " + (sum * 6.67 * 0.000000000001));
            }
        }

    }

}

