package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class GenerationFromCsv implements CommandExecutor {

    public NoolartPaperPlugin plugin;

    public GenerationFromCsv(NoolartPaperPlugin noolartPaperPlugin)  {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,Command command, String label, String[] args) {



            if (args.length!=2){
                return false;
            }
        try{
//            Random r = new Random();
//            int [][] arr = new int [10][10];
//            for (int i = 0; i < arr.length ; i++) {
//                for (int j = 0; j < arr[0].length; j++) {
//                    arr[i][j]=r.nextInt(4);
//                }
//            }

            String filename = args [0];
            Scanner scan = new Scanner(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator +"mccsv\\"+filename+".csv"));
            Bukkit.broadcastMessage("File is opened. Please wait...");

            String text = "";
            for (int r=0;r<=10000;r++) {
                if (scan.hasNextLine()) {
                    String string1 = scan.nextLine();
                    text = (text + string1);
                }
                else {
                    break;
                }
            }


            String[] symbols = text.split(";");


            int [][] arr = new int [Integer.parseInt(args[1])][Integer.parseInt(args[1])];
            int k = 0;
            for (int i = 0; i < arr.length ; i++) {
                for (int j = 0; j < arr[0].length; j++) {

                    arr[i][j]=Integer.parseInt(symbols[k]);
                    k++;
                }
            }







//            Location loc = new Location (Bukkit.getPlayer(sender.getName()).getWorld(),NoolartPaperPlugin.point1.getX(),NoolartPaperPlugin.point1.getY(),NoolartPaperPlugin.point1.getZ())
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    switch (arr[i][j]){
                        case 0:
                            PasteCsv.paste("model1",Bukkit.getPlayer(sender.getName()));
                            break;
                        case 1:
                            PasteCsv.paste("model2",Bukkit.getPlayer(sender.getName()));
                            break;
                        case 2:
                            PasteCsv.paste("model3",Bukkit.getPlayer(sender.getName()));
                            break;
                        case 3:
                            PasteCsv.paste("model4",Bukkit.getPlayer(sender.getName()));
                            break;
                        default:
                            PasteCsv.paste("model1",Bukkit.getPlayer(sender.getName()));
                            break;

                    }

                    NoolartPaperPlugin.point1.add(16,0,0);
                }
                NoolartPaperPlugin.point1.add(-arr.length*16,0,16);
            }

        } catch (Exception e) {
            Bukkit.broadcastMessage(e.getMessage());
            return false;
        }

        Bukkit.broadcastMessage("Done!");


        return true;
    }
}
