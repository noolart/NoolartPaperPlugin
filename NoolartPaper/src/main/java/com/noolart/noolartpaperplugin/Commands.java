package com.noolart.noolartpaperplugin;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;

public class Commands implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public static void pythonrun(String filename) {
        try {
            Process p = Runtime.getRuntime().exec("python " + NoolartPaperPlugin.plugin.getDataFolder() + File.separator + filename + ".py 1 2");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            while ((s = in.readLine()) != null) {
                Bukkit.broadcastMessage(ChatColor.BLUE + s);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Commands(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        if (args.length != 1) {
            return false;
        }
        pythonrun(args[0]);

        return true;
    }
}


























