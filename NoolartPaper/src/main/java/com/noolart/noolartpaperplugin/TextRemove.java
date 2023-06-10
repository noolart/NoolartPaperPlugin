package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;



public class TextRemove implements CommandExecutor{


    private NoolartPaperPlugin plugin;
    public TextRemove(NoolartPaperPlugin noolartPaperPlugin) {this.plugin = noolartPaperPlugin;}


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (ArmorStand a : (Bukkit.getPlayer(commandSender.getName())).getLocation().getNearbyEntitiesByType(ArmorStand.class,5)){
            a.remove();
        };
        return true;


    }
}