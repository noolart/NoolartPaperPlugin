package com.noolart.noolartpaperplugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class PictureDrawer extends MapRenderer {
    File picture;
    Scale scale;
    boolean drawed = false;
    int x;
    int y;

    public PictureDrawer(File picture, Scale scale, int x, int y) {
        this.picture = picture;
        this.scale = scale;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player p) {
        if (drawed) return;
        BufferedImage img = null;

        try {
            img = ImageIO.read(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.setScale(scale);
        canvas.drawImage(x, y, img);
        drawed = true;
    }

}