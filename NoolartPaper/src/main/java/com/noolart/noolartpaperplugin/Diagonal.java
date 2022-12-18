package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Diagonal implements CommandExecutor {


    public Diagonal(NoolartPaperPlugin noolartPaperPlugin) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Location loc1, loc2;

        World w = Objects.requireNonNull(Bukkit.getPlayer(commandSender.getName())).getWorld();

        loc1 = NoolartPaperPlugin.point1.clone();
        loc2 = NoolartPaperPlugin.point2.clone();

        int maxX = (int) Double.max (loc1.getX(), loc2.getX());
        int maxY = (int) Double.max (loc1.getY(), loc2.getY());

        for (int i = (int) Double.min (loc1.getX(), loc2.getX()); i <= maxX; i++) {
            for (int j = (int) Double.min (loc1.getY(), loc2.getY()); j <= maxY; j++) {
                for (int k = (int) Double.min (loc1.getZ(), loc2.getZ()); k <= Double.max (loc1.getZ(), loc2.getZ()); k++) {

                    if (maxX - i <= maxY - j) {
                        w.getBlockAt(i, j, k).setType(Material.IRON_BLOCK);
                    }
                    else {
                        w.getBlockAt(i, j, k).setType(Material.CLAY);
                    }


                }
            }
        }



        return false;

    }
}
