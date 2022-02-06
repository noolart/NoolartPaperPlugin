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

public class Balance implements CommandExecutor{

    private NoolartPaperPlugin plugin;
    public Balance(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length>0){
            return false;
        }
        int balance;
        Player p = Bukkit.getPlayer(sender.getName());
        File balanceCheck = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator+"users"+File.separator  + sender.getName() +"_balance.csv");

        try {
            Scanner scan = new Scanner(balanceCheck);
            balance = Integer.parseInt(scan.nextLine());
            p.sendMessage(ChatColor.GOLD+ "Ваш баланс: "+ ChatColor.BOLD+ChatColor.LIGHT_PURPLE +balance+"$");
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }
}
