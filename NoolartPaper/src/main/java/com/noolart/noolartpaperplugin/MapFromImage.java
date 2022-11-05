package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.csv.PasteCsv;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapFromImage implements CommandExecutor {
    private final NoolartPaperPlugin plugin;

    public MapFromImage(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    private final int ALPHA = 24;
    private final int RED = 16;
    private final int GREEN = 8;
    private final int BLUE = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label1, String[] args) {
        if (args.length != 1) {
            return false;
        }

        String filename = args[0];

        try {
            Bukkit.broadcastMessage("loading...");
            BufferedImage img = ImageIO.read(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "imgs" + File.separator + filename + ".png"));

            float[][] colorHues = {{-1, 0.5f}, {-0.5f, 0}, {0, 20}, {20, 40}, {40, 60}, {80, 120}, {120, 160}, {160, 200}, {200, 230}, {230, 260}, {260, 280}, {280, 310}, {310, 340}, {340, 360}, {361, 362}};

//            Material[] blocks = {Material.BLACK_CONCRETE, Material.GRAY_CONCRETE ,Material.RED_CONCRETE,Material.ORANGE_CONCRETE,Material.YELLOW_CONCRETE,Material.LIME_CONCRETE,Material.GREEN_CONCRETE,Material.CYAN_CONCRETE,Material.LIGHT_BLUE_CONCRETE,Material.BLUE_CONCRETE,Material.PURPLE_CONCRETE,Material.MAGENTA_CONCRETE,Material.PINK_CONCRETE,Material.RED_CONCRETE, Material.WHITE_CONCRETE};
            String[] toCSV = {"orange", "gray", "red", "orange", "yellow", "lime", "green", "cyan", "light_blue", "blue", "PURPLE_CONCRETE", "MAGENTA_CONCRETE", "PINK_CONCRETE", "red", "white"};
            float[][] pixels = new float[img.getWidth()][img.getHeight()];

            int[][] res = new int[img.getWidth()][img.getHeight()];

            Color c = new Color(0, 0, 0);

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    c = new Color(img.getRGB(i, j));

                    float[] hsv = new float[3];
                    Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);

                    if ((hsv[2] * 100 >= 70) && (hsv[1] * 100 <= 10)) {
                        pixels[i][j] = 361;
                    } else if (hsv[2] * 100 <= 10) {
                        pixels[i][j] = -1;
                    } else if (hsv[1] * 100 <= 10) {
                        pixels[i][j] = -0.5f;
                    } else {
                        pixels[i][j] = hsv[0] * 360;
                    }
                }
            }

            Location loc = new Location(NoolartPaperPlugin.point1.getWorld(), NoolartPaperPlugin.point1.getX(), NoolartPaperPlugin.point1.getY(), NoolartPaperPlugin.point1.getZ());

            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    for (int k = 0; k < colorHues.length; k++) {
                        if (pixels[i][j] >= colorHues[k][0] && pixels[i][j] < colorHues[k][1]) {
//                            if (k==colorHues.length-2) {
//                                k=1;
//                            }
                            res[i][j] = k;
                        }
                    }

                    PasteCsv.pasteQuiet(("map//" + toCSV[res[i][j]]), Bukkit.getPlayer(sender.getName()));
                    NoolartPaperPlugin.point1.add(0, 0, 1);
                }
                NoolartPaperPlugin.point1.add(1, 0, -pixels.length);
            }

            PasteCsv.pasteImage(filename, Bukkit.getPlayer(sender.getName()), img.getWidth(), img.getHeight());
            Bukkit.broadcastMessage("Done!");
        } catch (IOException e) {
            Bukkit.broadcastMessage(e.getMessage());
        }
        return true;
    }
}

