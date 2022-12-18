package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.commands.Commands;
import com.noolart.noolartpaperplugin.listener.MyListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Image implements CommandExecutor {

    public Image(NoolartPaperPlugin noolartPaperPlugin) {}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length != 1) {
            return false;
        }

        else {
            String filename = strings[0];
            Player p = Bukkit.getPlayer(commandSender.getName());
            assert p != null;
            Block targeted = p.getTargetBlock(3);
            BlockFace blockFace = p.getFacing();

            pasteImageInItemFrame(filename,blockFace,targeted);

        }

        return true;
    }



    public static void pasteImageInItemFrame (String filename, BlockFace blockface, Block rightUpBlock){

        assert filename!=null;

        Commands.pythonrun("image_paste",filename);

        int x, y, z;
        World w = rightUpBlock.getLocation().getWorld();

        x = rightUpBlock.getX();
        y = rightUpBlock.getY();
        z = rightUpBlock.getZ();

        Location firstFrame = new Location(w,x,y,z);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(firstFrame, ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(blockface, false);
                    itemFrame.setItem(MyListener.mapToStack(filename.split("\\.")[0] + "-" + finalJ + "-" + finalI + "." + filename.split("\\.")[1], w));
                    //Bukkit.broadcastMessage(filename + "-" + finalI + "-" + finalJ + ".png");

                }) ;
                firstFrame.add(0,-1,0);

            }
            firstFrame.add( blockface.getModZ(),2, blockface.getModX());

        }

    }

}





