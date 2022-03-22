package com.noolart.noolartpaperplugin.csv;

import com.noolart.noolartpaperplugin.NoolartPaperPlugin;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;

public class CsvInserter {
    private static final int CORD_X = 0;
    private static final int CORD_Y = 1;
    private static final int CORD_Z = 2;
    private static final int MATERIAL = 3;

    public static void pasteInNewThread(File file, NoolartPaperPlugin plugin) {
        Bukkit.getScheduler().runTask(plugin, () -> paste(file));
    }

    public static void pasteInNewThread(String filename, NoolartPaperPlugin plugin) {
        Bukkit.getScheduler().runTask(plugin, () -> paste(filename));
    }

    public static void paste(String filename) {
        File file = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + filename + ".csv");

        if (!file.exists() || !file.isFile()) {
            Bukkit.broadcastMessage("File" + file.getAbsolutePath() + "not found");
            return;
        }

        paste(file);
    }

    public static void paste(File file) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);

        long start = System.currentTimeMillis();

        try {
            parser.beginParsing(file);

            Location location = NoolartPaperPlugin.point1;
            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();

            for (String[] row : parser.iterate(file)) {
                try {
                    double dx = x + Integer.parseInt(row[CORD_X]);
                    double dy = y + Integer.parseInt(row[CORD_Y]);
                    double dz = z + Integer.parseInt(row[CORD_Z]);

                    location.set(dx, dy, dz);
                    location.getBlock().setType(Material.valueOf(row[MATERIAL].toUpperCase()));
                } catch (Exception ignored) {
                    Bukkit.broadcastMessage("can't parse block " + Arrays.toString(row));
                }
            }

            Bukkit.broadcastMessage("Done!");
        } finally {
            parser.stopParsing();
        }

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        Bukkit.broadcastMessage("вставка модели заняла: " + elapsed + "миллисекунд");
    }
}
