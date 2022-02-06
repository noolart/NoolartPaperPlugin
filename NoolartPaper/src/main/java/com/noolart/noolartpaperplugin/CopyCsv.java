package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class CopyCsv implements CommandExecutor {

    public NoolartPaperPlugin plugin;

    public CopyCsv(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String filename = args[0];


        try {
            try (FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ filename + ".csv", false)) {


                String name = sender.getName();
                Player p = Bukkit.getPlayer(name);


                int cx = (int) Double.min(NoolartPaperPlugin.point1.getX(), NoolartPaperPlugin.point2.getX());
                int cy = (int) Double.min(NoolartPaperPlugin.point1.getY(), NoolartPaperPlugin.point2.getY());
                int cz = (int) Double.min(NoolartPaperPlugin.point1.getZ(), NoolartPaperPlugin.point2.getZ());


                int ax = (int) Double.max(NoolartPaperPlugin.point1.getX(), NoolartPaperPlugin.point2.getX());
                int ay = (int) Double.max(NoolartPaperPlugin.point1.getY(), NoolartPaperPlugin.point2.getY());
                int az = (int) Double.max(NoolartPaperPlugin.point1.getZ(), NoolartPaperPlugin.point2.getZ());


                for (int i = cx; i < ax+1; i++) {
                    for (int i1 = cy; i1 < ay+1; i1++) {
                        for (int i2 = cz; i2 < az+1; i2++) {
                            World w = p.getWorld();
                            Location h = new Location(w, (double) i, (double) i1, (double) i2);
                            String space = " ";
//                            Bukkit.broadcastMessage(i + " " + i1 + " " + i2 + " " + h.getBlock().getType().toString());
                            writer.write(i + ";" + i1 + ";" + i2 + ";" + h.getBlock().getType().toString() + ";" + "\n");
                        }
                    }
                }
            }
                Bukkit.broadcastMessage("Done!");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return true;


    }
}
