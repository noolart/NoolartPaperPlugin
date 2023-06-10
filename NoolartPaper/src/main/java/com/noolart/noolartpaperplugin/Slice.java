package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.commands.Commands;
import com.noolart.noolartpaperplugin.listener.MyListener;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

public class Slice implements CommandExecutor {

    private NoolartPaperPlugin plugin;

    public Slice(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }
    Location loc3;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Location loc1, loc2;
        int yMid = (int) Double.min(NoolartPaperPlugin.point1.getY(),NoolartPaperPlugin.point2.getY());

        if (NoolartPaperPlugin.point1.getY()>NoolartPaperPlugin.point2.getY()) {
            loc1 = NoolartPaperPlugin.point1.clone();
            loc2 = NoolartPaperPlugin.point2.add(0, -(NoolartPaperPlugin.point2.getY()), 0);
        }
        else{
            loc1 = NoolartPaperPlugin.point1.add(0, -(NoolartPaperPlugin.point1.getY()), 0);
            loc2 = NoolartPaperPlugin.point2.clone();
        }

        String filename = strings[0];


        if (loc1.getZ()==loc2.getZ()) {


            loc3 = new Location(loc1.getWorld(), (Double.max(loc1.getX(),loc2.getX())+Double.min(loc1.getX(),loc2.getX()))/2, loc1.getY()+1,loc1.getZ());

                 World w = loc1.getWorld();

            for (int i = 0; i < 100; i++) {
                w.spawnParticle(Particle.EXPLOSION_HUGE, loc3, 5, 2, 2, 2);
                w.playSound(loc3,Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            }


            try (FileWriter fw = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "slices\\" + filename + ".csv",false)){
                fw.write("");
                int xMax = (int) Double.max(loc1.getX(), loc2.getX());
                int yMax = (int) Double.max(loc1.getY(),loc2.getY());
                int xMin = (int) Double.min(loc1.getX(), loc2.getX());
                int yMin = (int) Double.min(loc1.getY(),loc2.getY());
                Location loc3 =  new Location(Bukkit.getPlayer(commandSender.getName()).getWorld(),xMin,yMin,loc1.getZ());

                for (int i = xMin; i < xMax; i++){
                    for (int j = yMin; j < yMax; j++) {
                        loc3.add(0, 1, 0);

                        //Bukkit.broadcastMessage(yMid+"");

                        if (j>=yMid && loc3.getBlock().getType()==Material.AIR){
                            fw.write((int) loc3.getX() + ";" + (int) loc3.getY() + ";" + (int) loc3.getZ() + ";" + "ANCIENT_DEBRIS" + ";" + "\n");
                        }

                        else if  (loc3.getBlock().getType()!=Material.VOID_AIR && loc3.getBlock().getType()!=Material.AIR) {
                            fw.write((int) loc3.getX() + ";" + (int) loc3.getY() + ";" + (int) loc3.getZ() + ";" + loc3.getBlock().getType().toString() + ";" + "\n");
                        }

                    }
                    loc3.add(1, -(yMax - yMin), 0);
                }
                fw.close();
                Commands.pythonrun("seism",filename);
                Bukkit.getPlayer(commandSender.getName()).getInventory().addItem(MyListener.mapToStack(filename+".jpg",Bukkit.getPlayer(commandSender.getName()).getWorld()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            Objects.requireNonNull(Bukkit.getPlayer(commandSender.getName())).sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Slice is done!");

        }
        if (loc1.getX()==loc2.getX()) {

            loc3 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+1,(Double.max(loc1.getZ(),loc2.getZ())+Double.min(loc1.getZ(),loc2.getZ()))/2);

            World w = loc1.getWorld();

            for (int i = 0; i < 100; i++) {
                w.spawnParticle(Particle.EXPLOSION_HUGE, loc3, 5, 2, 2, 2);
                w.playSound(loc3, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            }

            try (FileWriter fw = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "slices\\" + filename + ".csv",false)){
                fw.write("");
                int zMax = (int) Double.max(loc1.getZ(), loc2.getZ());
                int yMax = (int) Double.max(loc1.getY(),loc2.getY());
                int zMin = (int) Double.min(loc1.getZ(), loc2.getZ());
                int yMin = (int) Double.min(loc1.getY(),loc2.getY());
                Location loc3 =  new Location(Objects.requireNonNull(Bukkit.getPlayer(commandSender.getName())).getWorld(),loc1.getX(),yMin,zMin);
                for (int i = zMin; i < zMax; i++){
                    for (int j = yMin; j < yMax; j++) {
                        loc3.add(0, 1, 0);
                        if (loc3.getBlock().getType()!=Material.VOID_AIR && loc3.getBlock().getType()!=Material.AIR) {
                            fw.write((int) loc3.getZ() + ";" + (int) loc3.getY() + ";" + (int) loc3.getX() + ";" + loc3.getBlock().getType().toString() + ";" + "\n");
                        }
                    }
                    loc3.add(0, -(yMax - yMin), 1);
                }
                fw.close();
                Commands.pythonrun("seism",filename);

                Bukkit.getPlayer(commandSender.getName()).getInventory().addItem(MyListener.mapToStack(filename+".jpg",Bukkit.getPlayer(commandSender.getName()).getWorld()));


            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.getPlayer(commandSender.getName()).sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Slice is done!");


        }

        return true;
    }
}
