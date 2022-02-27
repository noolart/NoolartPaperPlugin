package com.noolart.noolartpaperplugin.commands;

import com.noolart.noolartpaperplugin.NoolartPaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;

public class Commands implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public static void pythonrun(String filename, String argument) {
        try {
            Process p = Runtime.getRuntime().exec("python " + NoolartPaperPlugin.plugin.getDataFolder() + File.separator +
                    filename + ".py " + "--id=" + argument);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            while ((s = in.readLine()) != null) {
                Bukkit.broadcastMessage(ChatColor.BLUE + s);
            }

            in.close();
        } catch (IOException e) {
            Bukkit.broadcastMessage(e.toString());
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
        pythonrun(args[0], null);

        return true;
    }
}
