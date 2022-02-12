package com.noolart.noolartpaperplugin.commands;

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

public class Commands3 implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public Commands3(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        if (args.length != 4) {
            return false;
        }

        String filename = args[3];
        try (FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + filename + ".csv", false)) {
            String name = sender.getName();
            Player p = Bukkit.getPlayer(name);
            Location l = p.getLocation();

            int cx = (int) l.getX();
            int cy = (int) l.getY();
            int cz = (int) l.getZ();

            int ax = Integer.parseInt(args[0]);
            int ay = Integer.parseInt(args[1]);
            int az = Integer.parseInt(args[2]);

            for (int i = cx; i < cx + ax; i++) {
                for (int i1 = cy; i1 < cy + ay; i1++) {
                    for (int i2 = cz; i2 < cz + az; i2++) {
                        World w = p.getWorld();
                        Location h = new Location(w, (double) i, (double) i1, (double) i2);
                        writer.write(i + ";" + i1 + ";" + i2 + ";" + h.getBlock().getType().toString() + ";" + "\n");
//						Bukkit.broadcastMessage(i+" "+i1+" "+i2+" "+ h.getBlock().getType().toString());
                    }
                }
            }

            Bukkit.broadcastMessage("Done!");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return true;
    }
}

