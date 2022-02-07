package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Shop implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public Shop(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            return false;
        }

        Bukkit.getPlayer(sender.getName()).openInventory(NoolartPaperPlugin.shop);
        return true;
    }
}
