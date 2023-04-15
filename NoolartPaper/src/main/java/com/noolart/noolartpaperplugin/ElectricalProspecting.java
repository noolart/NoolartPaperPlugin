package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.commands.Commands;
import com.noolart.noolartpaperplugin.listener.MyListener;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

public class ElectricalProspecting implements CommandExecutor {

    private NoolartPaperPlugin plugin;

    public ElectricalProspecting(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Location loc1 = NoolartPaperPlugin.point1.clone();
        Location loc2 = NoolartPaperPlugin.point2.add(0,-(NoolartPaperPlugin.point2.getY()),0);
        String filename = strings[0];
        Location loc3;


        if (loc1.getZ()==loc2.getZ()) {


            loc3 = new Location(loc1.getWorld(), (Double.max(loc1.getX(),loc2.getX())-Double.min(loc1.getX(),loc2.getX()))/2, loc1.getY()+1,loc1.getZ());

            World w = loc1.getWorld();
            w.spawnParticle(Particle.VILLAGER_ANGRY,loc3, 5, 1, 1,1);
            w.playSound(loc3, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,1,1);



            try (FileWriter fw = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "prospecting\\" + filename + ".csv",false)){
                fw.write("");
                int xMax = (int) Double.max(loc1.getX(), loc2.getX());
                int yMax = (int) Double.max(loc1.getY(),loc2.getY());
                int xMin = (int) Double.min(loc1.getX(), loc2.getX());
                int yMin = (int) Double.min(loc1.getY(),loc2.getY());
                int l = (yMax - yMin)/2;
                int h = 1;



                loc3 =  new Location(Bukkit.getPlayer(commandSender.getName()).getWorld(),xMin,yMin,loc1.getZ());
                for (int i = xMin; i < xMax; i++){
                    for (int j = yMin; j < yMax+h; j++) {
                        loc3.add(0, 1, 0);
                        if (loc3.getBlock().getType()!=Material.VOID_AIR && loc3.getBlock().getType()!=Material.AIR ) {
                            fw.write((int) loc3.getX() + ";" + (int) loc3.getY() + ";" + (int) loc3.getZ() + ";" + loc3.getBlock().getType().toString() + ";" + "\n");
                        }
                        if (h>l/2) {
                            h -= l / 2;
                        }
                        h++;

                    }
                    loc3.add(1, -(yMax - yMin), 0);
                }
                fw.close();
                Commands.pythonrun("electrical",filename);
                Bukkit.getPlayer(commandSender.getName()).getInventory().addItem(MyListener.mapToStack(filename+".jpg",Bukkit.getPlayer(commandSender.getName()).getWorld()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            Objects.requireNonNull(Bukkit.getPlayer(commandSender.getName())).sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Electrical prospecting is done!");

        }
        if (loc1.getX()==loc2.getX()) {

            loc3 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+1,(Double.max(loc1.getZ(),loc2.getZ())-Double.min(loc1.getZ(),loc2.getZ()))/2);

            World w = loc1.getWorld();
            w.spawnParticle(Particle.VILLAGER_ANGRY,loc3, 5, 1, 1,1);
            w.playSound(loc3, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,1,1);



            try (FileWriter fw = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "prospecting\\" + filename + ".csv",false)){
                fw.write("");
                int zMax = (int) Double.max(loc1.getZ(), loc2.getZ());
                int yMax = (int) Double.max(loc1.getY(),loc2.getY());
                int zMin = (int) Double.min(loc1.getZ(), loc2.getZ());
                int yMin = (int) Double.min(loc1.getY(),loc2.getY());
                int l = (yMax - yMin)/2;
                int h = 1;

                loc3 =  new Location(Objects.requireNonNull(Bukkit.getPlayer(commandSender.getName())).getWorld(),loc1.getX(),yMin,zMin);
                for (int i = zMin; i < zMax; i++){
                    for (int j = yMin; j < yMin+h; j++) {
                        loc3.add(0, 1, 0);
                        if (loc3.getBlock().getType()!=Material.VOID_AIR && loc3.getBlock().getType()!=Material.AIR) {
                            fw.write((int) loc3.getZ() + ";" + (int) loc3.getY() + ";" + (int) loc3.getX() + ";" + loc3.getBlock().getType().toString() + ";" + "\n");
                        }
                        if (h>l/2) {
                            h -= l / 2;
                        }
                        h++;
                    }
                    loc3.add(0, -(yMax - yMin), 1);
                }
                fw.close();
                Commands.pythonrun("electrical",filename);

                Bukkit.getPlayer(commandSender.getName()).getInventory().addItem(MyListener.mapToStack(filename+".jpg",Bukkit.getPlayer(commandSender.getName()).getWorld()));


            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.getPlayer(commandSender.getName()).sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Electrical prospecting is done!");


        }

        return true;
    }
}
