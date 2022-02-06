package com.noolart.noolartpaperplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Commands1 implements CommandExecutor {

    private NoolartPaperPlugin plugin;

    public Commands1(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        if (args.length != 4) {
            return false;
        }
        String filename = args[3];


        try {
            FileOutputStream fileOs = new FileOutputStream( NoolartPaperPlugin.plugin.getDataFolder() + File.separator + filename + ".bin", false);
            ObjectOutputStream os = new ObjectOutputStream(fileOs);


            String name = sender.getName();
            Player p = Bukkit.getPlayer(name);
            Location l = p.getLocation();

            int cx = (int) l.getX();
            int cy = (int) l.getY();
            int cz = (int) l.getZ();


            int ax = Integer.parseInt(args[0]);
            int ay = Integer.parseInt(args[1]);
            int az = Integer.parseInt(args[2]);


            for (int i = cx; i < cx + ax; i++) {
                for (int i1 = cy; i1 < cy + ay; i1++) {
                    for (int i2 = cz; i2 < cz + az; i2++) {
                        World w = p.getWorld();
                        Location h = new Location(w, (double) i, (double) i1, (double) i2);
                        String space = " ";
//						Bukkit.broadcastMessage(i+" "+i1+" "+i2+" "+ h.getBlock().getType().toString());
                        os.writeChars(i + " " + i1 + " " + i2 + " " + h.getBlock().getType().toString() + space);
                    }
                }
            }
            Bukkit.broadcastMessage("Done!");
            os.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
}


