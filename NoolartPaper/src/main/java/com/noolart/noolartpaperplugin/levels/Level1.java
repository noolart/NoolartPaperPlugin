package com.noolart.noolartpaperplugin.levels;

import com.noolart.noolartpaperplugin.Field;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;



public class Level1 implements Listener {

    public static Field level1Field;


    @EventHandler

    public void interact(BlockBreakEvent blockBreakEvent){

        Player p = blockBreakEvent.getPlayer();
        Block target = blockBreakEvent.getBlock();

        Location level1Location1 = p.getLocation().set(888,256,1111);
        Location level1Location2 = p.getLocation().set(997,0,1209);

        level1Field = new Field(level1Location1, level1Location2);

        if (level1Field.contains(target.getLocation())){

            if (target.getLocation().add(0,-target.getLocation().getY()+73,0).getBlock().getType() == Material.LIME_CONCRETE_POWDER && target.getType() == Material.CLAY){
                Bukkit.broadcastMessage(ChatColor.RED + "К сожалению, здесь ничего нет!");
                blockBreakEvent.setCancelled(true);

            }

        }


    }

}
