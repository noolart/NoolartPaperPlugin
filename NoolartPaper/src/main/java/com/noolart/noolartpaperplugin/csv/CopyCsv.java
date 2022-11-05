package com.noolart.noolartpaperplugin.csv;

import com.noolart.noolartpaperplugin.NoolartPaperPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCsv implements CommandExecutor {
    private final static String FIELD_SEPARATOR = ";";

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
            try (FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + filename + ".csv", false)) {
                String name = sender.getName();
                Player player = Bukkit.getPlayer(name);

                int beginX = (int) Double.min(NoolartPaperPlugin.point1.getX(), NoolartPaperPlugin.point2.getX());
                int beginY = (int) Double.min(NoolartPaperPlugin.point1.getY(), NoolartPaperPlugin.point2.getY());
                int beginZ = (int) Double.min(NoolartPaperPlugin.point1.getZ(), NoolartPaperPlugin.point2.getZ());

                int endX = (int) Double.max(NoolartPaperPlugin.point1.getX(), NoolartPaperPlugin.point2.getX());
                int endY = (int) Double.max(NoolartPaperPlugin.point1.getY(), NoolartPaperPlugin.point2.getY());
                int endZ = (int) Double.max(NoolartPaperPlugin.point1.getZ(), NoolartPaperPlugin.point2.getZ());

                World world = player.getWorld();
                Location location = new Location(world, beginX, beginY, beginZ);
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = beginX; i < endX + 1; i++) {
                    for (int j = beginY; j < endY + 1; j++) {
                        for (int k = beginZ; k < endZ + 1; k++) {
                            location.set(i, j, k);

                            stringBuilder.append(i - beginX).append(FIELD_SEPARATOR)
                                    .append(j - beginY).append(FIELD_SEPARATOR)
                                    .append(k - beginZ).append(FIELD_SEPARATOR)
                                    .append(location.getBlock().getType().toString()).append(FIELD_SEPARATOR)
                                    .append("\n");

                            writer.write(stringBuilder.toString());
                            stringBuilder.setLength(0);
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
