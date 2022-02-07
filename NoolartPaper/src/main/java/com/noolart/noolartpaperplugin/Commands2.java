package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Commands2 implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public Commands2(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        if (args.length != 4) {
            return false;
        }
        String filename = args[3];
        try (FileInputStream fileIs = new FileInputStream(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + filename + ".bin")) {
            ObjectInputStream is = new ObjectInputStream(fileIs);
            String text = is.readLine();
//			Bukkit.broadcastMessage(text);
            String[] textS = text.split("");

            StringBuilder full = new StringBuilder();
            for (int i = 1; i <= textS.length; i = i + 2) {
                String a = textS[i];
                full.append(a);
            }

            is.close();
//			Bukkit.broadcastMessage(full);
            String name = sender.getName();
            Player p = Bukkit.getPlayer(name);
            Location h = p.getLocation();
            String[] symbols = full.toString().split(" ");

            int cx1 = (int) h.getX();
            int cy1 = (int) h.getY();
            int cz1 = (int) h.getZ();
            int ax = Integer.parseInt(args[0]);
            int ay = Integer.parseInt(args[1]);
            int az = Integer.parseInt(args[2]);
            int k = 3;

            for (int q = cx1; q < cx1 + ax; q++) {
                for (int q1 = cy1; q1 < cy1 + ay; q1++) {
                    for (int q2 = cz1; q2 < cz1 + az; q2++) {

                        String materialName = symbols[k].toUpperCase();
                        Material theMaterial = null;
                        try {
                            theMaterial = Material.valueOf(materialName);
                        } catch (Exception e1) {
                            //Not a valid material
                        }
                        World w = p.getWorld();
                        Location h1 = new Location(w, (double) q, (double) q1, (double) q2);
                        h1.getBlock().setType(theMaterial);
                        k = k + 4;
                    }
                }
            }

            Bukkit.broadcastMessage("Done!");
        } catch (IOException e1) {
            System.out.println("fileNotFound");
            e1.printStackTrace();
        }

        return true;
    }
}










