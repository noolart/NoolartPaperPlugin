package com.noolart.noolartpaperplugin.csv;

import com.noolart.noolartpaperplugin.Fill;
import com.noolart.noolartpaperplugin.NoolartPaperPlugin;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
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

    public static void paste(File file){
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);

        long start = System.currentTimeMillis();

        try {
            parser.beginParsing(file);

            Location location = NoolartPaperPlugin.point1;

            Location locationBackUp = location.clone();

            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();

            for (String[] row : parser.iterate(file)) {
                try {
                    double dx = x + Integer.parseInt(row[CORD_X]);
                    double dy = y + Integer.parseInt(row[CORD_Y]);
                    double dz = z + Integer.parseInt(row[CORD_Z]);

                    //System.out.println(dx+" "+ dy+ " "+dz);
                    //System.out.println(row[MATERIAL].toUpperCase());

                    location.set(dx, dy, dz);
                    location.getBlock().setType(Material.valueOf(row[MATERIAL].toUpperCase()));
                } catch (Exception ignored) {
                    Bukkit.broadcastMessage(ignored.getMessage());
                    Bukkit.broadcastMessage("can't parse block " + Arrays.toString(row));
                }
            }

            NoolartPaperPlugin.point1 = locationBackUp;
            Bukkit.broadcastMessage("Done!");
        } catch (Exception e) {
            Bukkit.broadcastMessage("File" + file.getAbsolutePath() + "not found");
        } finally {
            parser.stopParsing();
        }

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        Bukkit.broadcastMessage("вставка модели заняла: " + elapsed + "миллисекунд");
    }

    public static void pasteHidden(String filename) {
        File file = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "mccsv" + File.separator + filename + ".csv");

        if (!file.exists() || !file.isFile()) {
            Bukkit.broadcastMessage("File" + file.getAbsolutePath() + "not found");
            return;
        }

        pasteHidden(file);
    }

    public static void pasteHidden(File file){
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);

        long start = System.currentTimeMillis();

        try {
            parser.beginParsing(file);

            Location location = NoolartPaperPlugin.point1;

            Location locationBackUp = location.clone();

            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();


            //Bukkit.broadcastMessage(tail(file));

            int finalWallX = x + Integer.parseInt(tail(file).split(";")[0]);
            int finalWallY = y + Integer.parseInt(tail(file).split(";")[1]);
            int finalWallZ = z + Integer.parseInt(tail(file).split(";")[2]);




            for (String[] row : parser.iterate(file)) {
                try {
                    double dx = x + Integer.parseInt(row[CORD_X]);
                    double dy = y + Integer.parseInt(row[CORD_Y]);
                    double dz = z + Integer.parseInt(row[CORD_Z]);

                    //System.out.println(dx+" "+ dy+ " "+dz);
                    //System.out.println(row[MATERIAL].toUpperCase());

                    location.set(dx, dy, dz);
                    location.getBlock().setType(Material.valueOf(row[MATERIAL].toUpperCase()));
                } catch (Exception ignored) {
                    //Bukkit.broadcastMessage(ignored.getMessage());
                    //Bukkit.broadcastMessage("can't parse block " + Arrays.toString(row));
                }
            }

           // Bukkit.broadcastMessage((x-1) + " " + (y-1) + " " + (z-1) + " " + (finalWallX+1) + " " + (finalWallY+1) + " " + (finalWallZ+1));

            Fill.walls(new Location(locationBackUp.getWorld(),x-1,y-1,z-1),new Location(locationBackUp.getWorld(),finalWallX+1,finalWallY+1,finalWallZ+1), Material.BEDROCK);

            NoolartPaperPlugin.point1 = locationBackUp;
            // Bukkit.broadcastMessage("Done!");
        } catch (Exception e) {
            //Bukkit.broadcastMessage("File" + file.getAbsolutePath() + "not found");
        } finally {
            parser.stopParsing();
        }

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        //Bukkit.broadcastMessage("вставка модели заняла: " + elapsed + "миллисекунд");
    }

    public static String tail(File file) {
        RandomAccessFile fileHandler = null;
        try {
            fileHandler = new RandomAccessFile( file, "r" );
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();

            for(long filePointer = fileLength; filePointer != -1; filePointer--){
                fileHandler.seek( filePointer );
                int readByte = fileHandler.readByte();

                if( readByte == 0xA ) {
                    if( filePointer == fileLength ) {
                        continue;
                    }
                    break;

                } else if( readByte == 0xD ) {
                    if( filePointer == fileLength - 1 ) {
                        continue;
                    }
                    break;
                }

                sb.append( ( char ) readByte );
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch( java.io.FileNotFoundException e ) {
            e.printStackTrace();
            return null;
        } catch( java.io.IOException e ) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileHandler != null )
                try {
                    fileHandler.close();
                } catch (IOException e) {
                    /* ignore */
                }
        }
    }



}
