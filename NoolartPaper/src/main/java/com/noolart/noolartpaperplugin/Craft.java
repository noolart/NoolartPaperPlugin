package com.noolart.noolartpaperplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;


public class Craft {
        static File file;
        static FileConfiguration YML;

        public static void init() {
            File file = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "crafts.yml");
            com.noolart.noolartpaperplugin.Materials.file = file;
            com.noolart.noolartpaperplugin.Materials.YML = YamlConfiguration.loadConfiguration(file);
        }


        public static Map<String, Object> getAllValues() {


            return YML.getValues(true);


        }








        public static String getString(String craftName, String dataName) {
            String data = YML.getString(craftName + "." + dataName, "");
            assert data != null;
            return data;
        }

        public static long getLong(String material, String dataName) {
            try {
                long data = YML.getLong(material + "." + dataName, -1);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return -1;
        }

        public static double getDouble(String material, String dataName) {
            return YML.getDouble(material + "." + dataName, Double.NaN);
        }


}
