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

            File toRun = new File (NoolartPaperPlugin.plugin.getDataFolder() + File.separator + filename + ".py");
            String command = "python " + toRun.getAbsolutePath() + " " + "--id=" + argument;
            Process p = Runtime.getRuntime().exec(command);

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
        if (args.length != 2) {
            return false;
        }
        pythonrun(args[0], args[1]);

        return true;
    }
}
