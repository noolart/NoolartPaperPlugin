package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.csv.CsvInserter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.util.Random;

public class RandomGeneration implements CommandExecutor {
    public NoolartPaperPlugin plugin;

    public RandomGeneration(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }

        int distance = Integer.parseInt(args[0]);
        Location location = NoolartPaperPlugin.point1;
        String path;

        for (int i = 0; i < distance; i++) {
            for (int j = 0; j < distance; j++) {
                try {
                    Random random = new Random();
                    int u = random.nextInt(4);
                    Bukkit.broadcastMessage(Integer.toString(u));

                    if (u == 5) {
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + "model1.csv");
                    } else if (u == 6) {
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + "model2.csv");
                    } else if (u == 7) {
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + "model3.csv");
                    } else if (u == 8) {
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + "model4.csv");
                    } else {
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + "testbig.csv");
                    }

                    CsvInserter.paste(path);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                location.setX(location.getX() + 16);
            }

            location.setX(location.getX() - distance * 16);
            location.setZ(location.getZ() + 16);
        }

        return true;
    }
}