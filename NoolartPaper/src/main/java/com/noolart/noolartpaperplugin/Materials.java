package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Materials {
    static File file;
    static FileConfiguration YML;

    public static void init() {
        File file = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "materials.yml");

        Bukkit.broadcastMessage(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "materials.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                Bukkit.getLogger().severe("РќРµРІРѕР·РјРѕР¶РЅРѕ СЃРѕР·РґР°С‚СЊ С„Р°Р№Р» materials.yml");
                Bukkit.broadcast(ChatColor.DARK_RED + "РќРµРІРѕР·РјРѕР¶РЅРѕ СЃРѕР·РґР°С‚СЊ С„Р°Р№Р» materials.yml", "admin");
            }
        }
        Materials.file = file;
        Materials.YML = YamlConfiguration.loadConfiguration(file);
    }

    public static String getString(String material, String dataName) {
        String data = YML.getString(material + "." + dataName, "");
        assert data != null;
        if (data.equals("")) {
            data = YML.getString( "universal" + "." + dataName, "");
        }
        return data;

    }

    public static long getLong(String material, String dataName) {
        try {
            long data = YML.getLong(material + "." + dataName, -1);
            return data;
        } catch (Exception e) {
            long data = YML.getLong( "universal" + "." + dataName, -1);
            return data;
        }
    }

    public static double getDouble(String material, String dataName) {
        return YML.getDouble(material + "." + dataName, Double.NaN);
    }

    public static void setValue(String material, String dataname, String value) {
        YML.set(material + "." + dataname, value);
        YML = YamlConfiguration.loadConfiguration(file);
    }

    public static Set<String> getKeys(String material) {
        if (!YML.contains(material)) return new HashSet<>();
        return Objects.requireNonNull(YML.getConfigurationSection(material)).getKeys(false);
    }
}
