package com.noolart.noolartpaperplugin.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


public class MyListener2 implements Listener {


    @EventHandler

    public void interact (PlayerBucketFillEvent playerBucketFillEvent){

        int radius = 11;

        Location location = playerBucketFillEvent.getBlock().getLocation().add(-((radius-1)/2),-((radius-1)/2),-((radius-1)/2));
        Location start = location.clone();
        int count = 0;

        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius ; j++) {
                for (int k = 0; k < radius; k++) {

                    if (location.getBlock().getType() == Material.CUT_SANDSTONE){
                        count+= (radius-1)/2+1 - location.distance(start);
                    }

                    else if (location.getBlock().getType() == Material.AIR){
                        count+=0.5;
                    }

                    location.add(0,0,1);
                }
                location.add(0,1,0);
                location.add(0,0,-radius);
            }
            location.add(1,0,0);
            location.add(0,-radius,0);
        }

        ItemMeta bucketItemMeta = Objects.requireNonNull(playerBucketFillEvent.getItemStack()).getItemMeta();



        bucketItemMeta.setLore(Collections.singletonList(  Integer.toString ((int)(count/10))));

        System.out.println(Integer.toString(count));



        playerBucketFillEvent.getItemStack().setItemMeta(bucketItemMeta);


    }


    @EventHandler

    public void interact (CauldronLevelChangeEvent cauldronLevelChangeEvent){

        if (cauldronLevelChangeEvent.getOldLevel()<cauldronLevelChangeEvent.getNewLevel() && cauldronLevelChangeEvent.getBlock().getLocation().add(0,-1,0).getBlock().getType() == Material.SPRUCE_PLANKS){




            Player p = (Player) cauldronLevelChangeEvent.getEntity();
            assert p != null;

            int oilPercent = Integer.parseInt(p.getItemInHand().getItemMeta().getLore().get(0));

            p.getInventory().getItemInHand().setType(Material.AIR);

            p.sendMessage(
                    ""
                    + ChatColor.GOLD + ChatColor.BOLD
                    + "Содержание нефти: "
                    + ChatColor.LIGHT_PURPLE
                    + oilPercent
                    + "%"
            );

            cauldronLevelChangeEvent.setNewLevel(0);
            Location upToCauldron = cauldronLevelChangeEvent.getBlock().getLocation().add(0,1,0);
            ItemStack bucket = new ItemStack(Material.WATER_BUCKET);
            ItemMeta bucketMeta = bucket.getItemMeta();
            bucketMeta.setLore(Collections.singletonList(convertToInvisibleString( Integer.toString(oilPercent))));
            bucket.setItemMeta(bucketMeta);

            p.getWorld().dropItemNaturally(upToCauldron, bucket);

        }

    }

    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR +""+c;
        return hidden;
    }

}
