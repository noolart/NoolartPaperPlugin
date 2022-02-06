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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class RandomGenerationUseCommand implements CommandExecutor {

    private NoolartPaperPlugin plugin;

    public RandomGenerationUseCommand(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    public String printIntArr(int[] arr){
        String result="";
        for (int i = 0; i < arr.length; i++) {
            result = result + Integer.toString(arr[i]) +" ";
        }
        return result;
    }
    public String printAtrArr(String[] arr){
        String result="";
        for (int i = 0; i < arr.length; i++) {
            result = result + arr[i] +" ";
        }
        return result;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        int generation_length = Integer.parseInt(args[0]);
        int model_length = Integer.parseInt(args[1]);
        String[] models = new String[(args.length - 1) / 2];
        String[] probabilityes = new String[(args.length - 1) / 2];
        Scanner[] directories = new Scanner[(args.length - 1) / 2];
        HashMap<Integer, File> string_models_with_probabilityes = new HashMap<>();
        Location point_to_generate = NoolartPaperPlugin.point1;
        int model_counter=1;
        int allmodels = (int) Math.pow(generation_length,2);


        int universal_counter = 0;
        for (int model = 2; model < args.length - 1; model += 2) {
            models[universal_counter] = args[model];
            universal_counter++;
        }
        universal_counter = 1;
        probabilityes[0]=args[3];
        for (int probability = 5; probability < args.length; probability += 2) {
            probabilityes[universal_counter] =   Integer.toString(Integer.parseInt( args[probability])+Integer.parseInt(probabilityes[universal_counter-1]));
            universal_counter++;
        }





        File[] dir = new File[models.length];


        universal_counter = 0;
        for (String model : models) {
            File path = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv\\" + model + ".csv");
            try {
                directories[universal_counter] = new Scanner(path);
                dir[universal_counter] = path;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            universal_counter++;
        }



        for (int i = 0; i < probabilityes.length; i++) {
            string_models_with_probabilityes.put(Integer.parseInt(probabilityes[i]),dir[i]);
        }




        int max=0;
        int min=Integer.parseInt(probabilityes[0]);
        for (int i = 0; i < probabilityes.length; i++) {
            if (Integer.parseInt(probabilityes[i])>max){
                max=Integer.parseInt(probabilityes[i]);
            }
            if (Integer.parseInt(probabilityes[i])<min){
                min=Integer.parseInt(probabilityes[i]);
            }
        }

        Map<Integer, File> treeMap = new TreeMap<>(string_models_with_probabilityes);

        int[] keys = new int[probabilityes.length];
        universal_counter=0;
        for (Map.Entry<Integer, File> item : string_models_with_probabilityes.entrySet()) {
            keys[universal_counter]=item.getKey();
//            Bukkit.broadcastMessage(item.getValue().getPath());
//            Bukkit.broadcastMessage(item.getKey().toString());
            universal_counter++;
        }
        Arrays.sort(keys);

        File path = null;
        Random random = new Random();
        int r;
        for (int i = 0; i < generation_length; i++) {
            for (int j = 0; j < generation_length; j++) {


//                Bukkit.broadcastMessage(printAtrArr(probabilityes));
                r=random.nextInt(max+1);

//                Bukkit.broadcastMessage(Integer.toString(r));



                for (int k = 0; k < probabilityes.length-1; k++) {


                    if ((r>keys[k]&&r<=keys[k+1])){
                        path=treeMap.get(keys[k+1]);
//                        Bukkit.broadcastMessage(string_models_with_probabilityes.get(keys[k]).getPath());
//                         Bukkit.broadcastMessage(string_models_with_probabilityes.get(keys[k+1]).getPath());
                        break;
                    }
                    else if (r<min){
                        path=treeMap.get(keys[0]);
//                         Bukkit.broadcastMessage(string_models_with_probabilityes.get(keys[0]).getPath());
                        break;
                    }
                    if (k==probabilityes.length-1){
                        path=treeMap.get(k==probabilityes.length-1);
                        break;
                    }

                }
                Scanner scan = null;
                try {
                    scan = new Scanner(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                String name = sender.getName();
                Player p = Bukkit.getPlayer(name);

                int cx1 = (int) point_to_generate.getX();
                int cy1 = (int) point_to_generate.getY();
                int cz1 = (int) point_to_generate.getZ();


                String string = scan.nextLine();


                String[] first_line = string.split(";");

                int m1 = Integer.parseInt(first_line[0]);
                int m2 = Integer.parseInt(first_line[1]);
                int m3 = Integer.parseInt(first_line[2]);


//            Scanner scan1 = new Scanner(file);

                while (scan.hasNextLine()) {
                    String text = "";

             Bukkit.broadcastMessage("Model "+model_counter+" / "+allmodels);
             model_counter++;

                    int c1 = 0;
                    int c2 = 1;
                    int c3 = 2;
                    int k = 3;


                    for (int r1 = 0; r1 <= 10000; r1++) {
                        if (scan.hasNextLine()) {
                            String string1 = scan.nextLine();
                            text = (text + string1);
                        } else {
                            break;
                        }
                    }
//                    System.out.println(text);


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
                scan.close();


//                Bukkit.broadcastMessage("Done!");


                point_to_generate.setZ(point_to_generate.getZ()+model_length);

            }
            point_to_generate.setX(point_to_generate.getX()+model_length);
            point_to_generate.setZ(point_to_generate.getZ()-model_length*generation_length);
        }
        point_to_generate = NoolartPaperPlugin.point1;
        return true;

    }
}

