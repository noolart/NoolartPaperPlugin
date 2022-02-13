package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Balance implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public Balance(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            return false;
        }

        Player p = Bukkit.getPlayer(sender.getName());

        try (Scanner scan = new Scanner(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + sender.getName() + "_balance.csv"))) {
            int balance = Integer.parseInt(scan.nextLine());
            p.sendMessage(ChatColor.GOLD + "Ваш баланс: " + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + balance + "$");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }
}
