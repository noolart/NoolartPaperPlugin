package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.csv.CsvInserter;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RandomGenerationUseCommand implements CommandExecutor {

    private NoolartPaperPlugin plugin;

    public RandomGenerationUseCommand(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    public String printIntArr(int[] arr) {
        StringBuilder result = new StringBuilder();

        for (int j : arr) {
            result.append(j).append(" ");
        }

        return result.toString();
    }

    public String printAtrArr(String[] arr) {
        StringBuilder result = new StringBuilder();
        for (String s : arr) {
            result.append(s).append(" ");
        }
        return result.toString();
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

        int universal_counter = 0;

        for (int model = 2; model < args.length - 1; model += 2) {
            models[universal_counter] = args[model];
            universal_counter++;
        }

        universal_counter = 1;
        probabilityes[0] = args[3];

        for (int probability = 5; probability < args.length; probability += 2) {
            probabilityes[universal_counter] = Integer.toString(Integer.parseInt(args[probability]) + Integer.parseInt(probabilityes[universal_counter - 1]));
            universal_counter++;
        }

        File[] dir = new File[models.length];

        universal_counter = 0;

        for (String model : models) {
            File path = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + model + ".csv");
            try {
                directories[universal_counter] = new Scanner(path);
                dir[universal_counter] = path;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            universal_counter++;
        }

        for (int i = 0; i < probabilityes.length; i++) {
            string_models_with_probabilityes.put(Integer.parseInt(probabilityes[i]), dir[i]);
        }

        int max = 0;
        int min = Integer.parseInt(probabilityes[0]);

        for (String probabilitye : probabilityes) {
            if (Integer.parseInt(probabilitye) > max) {
                max = Integer.parseInt(probabilitye);
            }
            if (Integer.parseInt(probabilitye) < min) {
                min = Integer.parseInt(probabilitye);
            }
        }

        Map<Integer, File> treeMap = new TreeMap<>(string_models_with_probabilityes);

        int[] keys = new int[probabilityes.length];
        universal_counter = 0;

        for (Map.Entry<Integer, File> item : string_models_with_probabilityes.entrySet()) {
            keys[universal_counter] = item.getKey();
            universal_counter++;
        }

        Arrays.sort(keys);

        File path = null;
        Random random = new Random();
        int r;
        for (int i = 0; i < generation_length; i++) {
            for (int j = 0; j < generation_length; j++) {
                r = random.nextInt(max + 1);

                for (int k = 0; k < probabilityes.length - 1; k++) {
                    if ((r > keys[k] && r <= keys[k + 1])) {
                        path = treeMap.get(keys[k + 1]);
                        break;
                    } else if (r < min) {
                        path = treeMap.get(keys[0]);
                        break;
                    }

                    if (k == probabilityes.length - 1) {
                        path = treeMap.get(k == probabilityes.length - 1);
                        break;
                    }
                }

                CsvInserter.paste(path);

                point_to_generate.setZ(point_to_generate.getZ() + model_length);

            }
            point_to_generate.setX(point_to_generate.getX() + model_length);
            point_to_generate.setZ(point_to_generate.getZ() - model_length * generation_length);
        }

        return true;
    }
}
