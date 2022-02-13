package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ExpShop implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public ExpShop(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Bukkit.getPlayer(sender.getName()).openInventory(NoolartPaperPlugin.expShop);
        return true;
    }
}
