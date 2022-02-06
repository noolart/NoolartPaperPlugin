package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Pay implements CommandExecutor {

    private NoolartPaperPlugin plugin;
    public Pay(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length!=1){
            return false;
        }

        int balance;
        Player p = Bukkit.getPlayer(sender.getName());
        File balanceCheck = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator+"users"+File.separator  + sender.getName() +"_balance.csv");

        try {
            Scanner scan = new Scanner(balanceCheck);
            balance = Integer.parseInt(scan.nextLine());
            balance+=Integer.parseInt(args[0]);
            p.sendMessage(ChatColor.GOLD+"Баланс пополнен на " +ChatColor.BOLD+ChatColor.LIGHT_PURPLE+args[0]+"$ "+ ChatColor.RESET+ ChatColor.GOLD+ "Ваш баланс: "+ ChatColor.BOLD+ChatColor.LIGHT_PURPLE +balance+"$");
            scan.close();
            FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator+"users"+File.separator  + sender.getName() +"_balance.csv", false);
            writer.write(Integer.toString(balance));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}