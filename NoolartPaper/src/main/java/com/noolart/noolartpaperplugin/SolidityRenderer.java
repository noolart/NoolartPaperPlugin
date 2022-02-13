package com.noolart.noolartpaperplugin;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SolidityRenderer extends MapRenderer {
    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        try {
            canvas.drawImage(0, 0, ImageIO.read(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "solidity.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ?работа
    }
}
