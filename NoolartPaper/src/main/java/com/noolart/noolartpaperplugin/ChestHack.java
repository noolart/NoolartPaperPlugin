package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChestHack implements CommandExecutor {


    private NoolartPaperPlugin plugin;
    public ChestHack(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }




    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = Bukkit.getPlayer(sender.getName());
        Location l = p.getLocation();
        Block b = l.add(0,-1,0).getBlock();
        b.setType(Material.CHEST);

        BlockState state = b.getState();
        Chest chest = (Chest) state;
        for (int i = 0; i < 27; i++) {
            chest.getInventory().addItem(new ItemStack(Material.valueOf(args[0]),64));
        }




        return true;
    }
}
