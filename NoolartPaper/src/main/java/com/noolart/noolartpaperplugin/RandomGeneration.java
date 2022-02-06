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
import java.util.Random;
import java.util.Scanner;

public class RandomGeneration implements CommandExecutor {

    public NoolartPaperPlugin plugin;
    static int allmodels=0;
    static int model_counter=0;

    public RandomGeneration(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }
        int distance =Integer.parseInt(args[0]);
        allmodels=(int)Math.pow(distance,2);
        Location h = NoolartPaperPlugin.point1;
        String path;
        File file;
        Scanner scan;
        for (int i = 0; i < distance; i++) {
            for (int j = 0; j < distance; j++) {

                try {
                    Random random = new Random();
                    int u = random.nextInt(4);
                    Bukkit.broadcastMessage(Integer.toString(u));
                    if (u == 5){
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ "model1.csv");
                        file = new File(path);
                        scan = new Scanner(file);
                    }
                    else if (u == 6){
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ "model2.csv");
                        file = new File(path);
                        scan = new Scanner(file);
                    }
                    else if (u == 7){
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ "model3.csv");
                        file = new File(path);
                        scan = new Scanner(file);
                    }
                    else if (u == 8){
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ "model4.csv");
                        file = new File(path);
                        scan = new Scanner(file);
                    }
                    else{
                        path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+ "testbig.csv");
                        file = new File(path);
                        scan = new Scanner(file);
                    }













                    String name = sender.getName();
                    Player p = Bukkit.getPlayer(name);

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
                        //Bukkit.broadcastMessage("Part " + o + ", wait");
                        Bukkit.broadcastMessage("Model "+model_counter+" / "+allmodels);
                        model_counter++;

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
                h.setX(h.getX()+16);


            }
            h.setX(h.getX()-distance*16);
            h.setZ(h.getZ()+16);

        }




        return true;
    }
}