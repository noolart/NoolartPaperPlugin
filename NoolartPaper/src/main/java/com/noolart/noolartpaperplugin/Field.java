package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Field {


    private final Location l1;
    private final Location l2;


    public Field (Location l1, Location l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    public boolean contains(Location l){

//        Bukkit.broadcastMessage(Double.max(l1.getX(), l2.getX())+"");
//        Bukkit.broadcastMessage(l.getX()+"");
//        Bukkit.broadcastMessage(Double.min(l1.getX(), l2.getX())+"");
//
//        Bukkit.broadcastMessage(Double.max(l1.getY(), l2.getY())+"");
//        Bukkit.broadcastMessage(l.getY()+"");
//        Bukkit.broadcastMessage(Double.min(l1.getY(), l2.getY())+"");
//
//        Bukkit.broadcastMessage(Double.max(l1.getZ(), l2.getZ())+"");
//        Bukkit.broadcastMessage(l.getZ() +"");
//        Bukkit.broadcastMessage(Double.min(l1.getZ(), l2.getZ())+"");


        return (Double.max(l1.getX(), l2.getX()) >= l.getX() && l.getX() >= Double.min(l1.getX(), l2.getX())) && (Double.max(l1.getY(), l2.getY()) >= l.getY() && l.getY() >= Double.min(l1.getY(), l2.getY())) && Double.max(l1.getZ(), l2.getZ()) >= l.getZ() && l.getZ() >= Double.min(l1.getZ(), l2.getZ());


    }

}
