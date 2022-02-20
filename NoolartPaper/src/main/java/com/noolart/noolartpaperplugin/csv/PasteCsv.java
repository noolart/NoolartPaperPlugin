package com.noolart.noolartpaperplugin.csv;

import com.noolart.noolartpaperplugin.NoolartPaperPlugin;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class PasteCsv implements CommandExecutor {
    public NoolartPaperPlugin plugin;

    public static void paste(String filename, Player player) {
        int cordX = 0;
        int cordY = 1;
        int cordZ = 2;
        int material = 3;

        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);

        File file = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + filename + ".csv");
        long start = System.nanoTime();
        try {
            parser.beginParsing(file);

            Location h = NoolartPaperPlugin.point1;
            int cx1 = (int) h.getX();
            int cy1 = (int) h.getY();
            int cz1 = (int) h.getZ();

            String[] firstBlock = parser.parseNext();
            int m1 = Integer.parseInt(firstBlock[0]);
            int m2 = Integer.parseInt(firstBlock[1]);
            int m3 = Integer.parseInt(firstBlock[2]);

            World world = player.getWorld();
            Location h1 = new Location(world, m1, m2, m3);

            for (String[] row : parser.iterate(file)) {
                try {
                    double dx = cx1 + Integer.parseInt(row[cordX]) - m1;
                    double dy = cy1 + Integer.parseInt(row[cordY]) - m2;
                    double dz = cz1 + Integer.parseInt(row[cordZ]) - m3;

                    h1.set(dx, dy, dz);
                    h1.getBlock().setType(Material.valueOf(row[material].toUpperCase()));
                } catch (Exception ignored) {
                    Bukkit.broadcastMessage("can't parse block " + Arrays.toString(row));
                }
            }

            Bukkit.broadcastMessage("Done!");
        } catch (Exception e) {
            Bukkit.broadcastMessage("File" + file.getAbsolutePath() + "not found");
        } finally {
            parser.stopParsing();
        }

        long finish = System.nanoTime();
        long elapsed = finish - start;
        Bukkit.broadcastMessage("вставка модели заняла: " + elapsed);
    }

    public static void pasteQuiet(String name, Player p) {
        try {
            String filename = name;
            String path = (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + filename + ".csv");
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

                for (int r = 0; r <= 1000; r++) {
                    if (scan1.hasNextLine()) {
                        String string1 = scan1.nextLine();
                        text = (text + string1);
                    } else {
                        break;
                    }
                }
                if (scan1.hasNextLine()) {
                    Bukkit.broadcastMessage("Part " + o + ", wait...");
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

    public PasteCsv(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
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
