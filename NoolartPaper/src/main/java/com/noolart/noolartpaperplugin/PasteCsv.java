package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Scanner;

public class PasteCsv implements CommandExecutor {


    public static void paste(String name, Player p){
        try {
            String filename = name;
            String path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ filename + ".csv");
            File file = new File(path);

            Scanner scan = new Scanner(file);


            Location h = NoolartPaperPlugin.point1;
            int cx1 = (int) h.getX();
            int cy1 = (int) h.getY();
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





                    double dx = cx1 + Integer.parseInt(symbols[c1]) - m1;
                    double dy = cy1 + Integer.parseInt(symbols[c2]) - m2;
                    double dz = cz1 + Integer.parseInt(symbols[c3]) - m3;

                    World w = p.getWorld();

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
    }
    public static void pasteQuiet(String name, Player p){
        try {
            String filename = name;
            String path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ filename + ".csv");
            File file = new File(path);

            Scanner scan = new Scanner(file);


            Location h = NoolartPaperPlugin.point1;
            int cx1 = (int) h.getX();
            int cy1 = (int) h.getY();
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



                int c1 = 0;
                int c2 = 1;
                int c3 = 2;
                int k = 3;


                for (int r=0;r<=1000;r++) {
                    if (scan1.hasNextLine()) {
                        String string1 = scan1.nextLine();
                        text = (text + string1);
                    }
                    else {
                        break;
                    }
                }
                if (scan1.hasNextLine()) {
                    Bukkit.broadcastMessage("Part "+o+", wait...");
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





                    double dx = cx1 + Integer.parseInt(symbols[c1]) - m1;
                    double dy = cy1 + Integer.parseInt(symbols[c2]) - m2;
                    double dz = cz1 + Integer.parseInt(symbols[c3]) - m3;

                    World w = p.getWorld();

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



        } catch (IOException e1) {
            Bukkit.broadcastMessage("File not found!");
            e1.printStackTrace();
        }
    }


    public static void pasteImage(String name, Player p, int width, int heigth) {
        try {
            int o = 0;
            String filename = name;
            String path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv\\" + filename + ".csv");
            File file = new File(path);


            Location h = NoolartPaperPlugin.point1;

            Scanner scan = new Scanner(file);

            String text = "";
            while (scan.hasNextLine()) {
                text += scan.nextLine();

            }
            scan.close();


            String[] symbols = text.split(";");
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

    public NoolartPaperPlugin plugin;

    public PasteCsv(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {

            return false;
        }
        paste(args[0],Bukkit.getPlayer(sender.getName()));




        return true;
    }
}
