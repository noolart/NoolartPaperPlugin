package com.noolart.noolartpaperplugin.csv;

import com.noolart.noolartpaperplugin.NoolartPaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Scanner;

public class PasteCsv implements CommandExecutor {
    public static NoolartPaperPlugin plugin;

    public PasteCsv(NoolartPaperPlugin noolartPaperPlugin) {
        plugin = noolartPaperPlugin;
    }

    public static void paste(String filename, Player player) {
        CsvInserter.pasteInNewThread(filename, plugin);
    }

    public static void pasteQuiet(String filename, Player p) {
        CsvInserter.pasteInNewThread(filename, plugin);
    }

    public static void pasteImage(String filename, Player p, int width, int heigth) {
        try {
            String path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + filename + ".csv");
            File file = new File(path);

            Scanner scan = new Scanner(file);

            StringBuilder text = new StringBuilder();
            while (scan.hasNextLine()) {
                text.append(scan.nextLine());
            }

            scan.close();

            String[] symbols = text.toString().split(";");
            int k = 0;
            Location loc = new Location(NoolartPaperPlugin.point1.getWorld(), NoolartPaperPlugin.point1.getX(), NoolartPaperPlugin.point1.getY(), NoolartPaperPlugin.point1.getZ());

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    loc.getBlock().setType(Material.valueOf(symbols[k]));
                    k++;
                    loc.add(0, 0, 1);
                }

                loc.add(1, 0, -heigth);
            }

            Bukkit.broadcastMessage("Done!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {

            return false;
        }
        paste(args[0], Bukkit.getPlayer(sender.getName()));

        return true;
    }
}
