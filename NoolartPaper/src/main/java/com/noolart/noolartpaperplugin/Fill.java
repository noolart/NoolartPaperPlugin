package com.noolart.noolartpaperplugin;

import org.bukkit.*;


public class Fill {

        public static void fill (Location loc1, Location loc2, Material material){

            for (int i = (int) Double.min(loc1.getX(),loc2.getX()); i < Double.max(loc1.getX(),loc2.getX()) + 1; i++) {
                for (int j = (int) Double.min(loc1.getY(),loc2.getY()); j < Double.max(loc1.getY(),loc2.getY()) + 1; j++) {
                    for (int k = (int) Double.min(loc1.getZ(),loc2.getZ()); k < Double.max(loc1.getZ(),loc2.getZ()) + 1; k++) {

                        loc1.getWorld().getBlockAt(i,j,k).setType(material);
                    }
                }
            }

        }


        public static void walls (Location loc1, Location loc2, Material material){

            Location loc3 = loc2.clone();
            loc3.setX(loc1.getX());

            Location loc4 = loc2.clone();
            loc4.setZ(loc1.getZ());

            loc2.setY(loc1.getY());

            fill(loc1,loc3,material);
            fill(loc2,loc3,material);
            fill(loc1,loc4,material);
            fill(loc2,loc4,material);

        }








}
