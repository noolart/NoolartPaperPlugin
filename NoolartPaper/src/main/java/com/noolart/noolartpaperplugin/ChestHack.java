package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChestHack implements CommandExecutor {
    private static final int ITEMS_IN_CHEST_COUNT = 27;

    private NoolartPaperPlugin plugin;

    public ChestHack(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());
        Location location = player.getLocation();
        Block block = location.add(0, -1, 0).getBlock();
        block.setType(Material.CHEST);

        Chest chest = (Chest) block.getState();

        for (int i = 0; i < ITEMS_IN_CHEST_COUNT; i++) {
            chest.getInventory().addItem(new ItemStack(Material.valueOf(args[0]), 64));
        }

        return true;
    }
}
