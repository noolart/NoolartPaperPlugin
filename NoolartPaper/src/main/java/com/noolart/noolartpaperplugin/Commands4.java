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
import java.io.IOException;
import java.util.Scanner;

public class Commands4 implements CommandExecutor {

    private NoolartPaperPlugin plugin;

    public Commands4(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        if (args.length != 2) {
            return false;
        }

        try {
            String filename = args[0];
            int heigth = Integer.parseInt(args[1]);
            String path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + filename + ".csv");
            File file = new File(path);

            Scanner scan = new Scanner(file);


            String name = sender.getName();
            Player p = Bukkit.getPlayer(name);
            Location h = p.getLocation();
            int cx1 = (int) h.getX();
            int cy1 = (int) h.getY()-heigth;
            int cz1 = (int) h.getZ();
            int o = 0;





            String string = scan.nextLine();
            String[] first_line = string.split(";");

            int m1 = Integer.parseInt(first_line[0]);
            int m2 = Integer.parseInt(first_line[1]);
            int m3 = Integer.parseInt(first_line[2]);

            scan.close();

            Scanner scan1 = new Scanner(file);

            while (scan1.hasNextLine()) {
                String text = "";
                o++;
                Bukkit.broadcastMessage("Part " + o + ", wait");
                int c1 = 0;
                int c2 = 1;
                int c3 = 2;
                int k = 3;


                for (int r=0;r<=10000;r++) {
                    if (scan1.hasNextLine()) {
                        String string1 = scan1.nextLine();
                        text = (text + string1);
                    }
                    else {
                        break;
                    }
                }


                String[] symbols = text.split(";");
                int scanlength = symbols.length / 4;


                while (scanlength > 0) {
                    String materialName = symbols[k].toUpperCase();
                    Material theMaterial = null;
                    try {
                        theMaterial = Material.valueOf(materialName);
                    } catch (Exception e1) {
                        //Not a valid material
                    }
                    World w = p.getWorld();



                    double dx = cx1 + Integer.parseInt(symbols[c1]) - m1;
                    double dy = cy1 + Integer.parseInt(symbols[c2]) - m2;
                    double dz = cz1 + Integer.parseInt(symbols[c3]) - m3;

                    Location h1 = new Location(w, dx, dy, dz);
//                Bukkit.broadcastMessage(h1.toString());
                    h1.getBlock().setType(theMaterial);
                    c1 = c1 + 4;
                    c2 = c2 + 4;
                    c3 = c3 + 4;
                    k = k + 4;
                    scanlength--;

                }
            }
            scan1.close();


            Bukkit.broadcastMessage("Done!");

        } catch (IOException e1) {
            Bukkit.broadcastMessage("File not found!");
            e1.printStackTrace();
        }

        return true;
    }
}
