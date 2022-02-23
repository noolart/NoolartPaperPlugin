package com.noolart.noolartpaperplugin;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PerlinGeneration implements CommandExecutor {
    private NoolartPaperPlugin plugin;

    public PerlinGeneration(NoolartPaperPlugin noolartPaperPlugin) {
        this.plugin = noolartPaperPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 5) {
            return false;
        }

        MyMap myMap = new MyMap(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
//        createFrame(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        int height = 0;
        int height0 = 200;
        int[][] mapToSort = MyMap.map.clone();
        for (int i = 0; i < mapToSort.length; i++) {
            Arrays.sort(mapToSort[i]);
            //возвращение в плоский слои
//            if(mapToSort[i][mapToSort[0].length-1]>height){
//                height=mapToSort[i][mapToSort[0].length-1];
//            }
            if (mapToSort[i][0] < height0) {
                height0 = mapToSort[i][0];
            }
        }

        //height-=height0;
        Random r = new Random();

        Location loc = NoolartPaperPlugin.point1;
        for (int i = 0; i < myMap.map.length; i++) {
            for (int j = 0; j < myMap.map.length; j++) {
                //убрать при возвращении к плоским слоям
                height = MyMap.map[i][j];
                height -= height0;

                Bukkit.broadcastMessage(height0 / Integer.parseInt(args[4]) + " " + myMap.map[i][j] / Integer.parseInt(args[4]) + "");
                for (int k = (height0 / Integer.parseInt(args[4])); k < (myMap.map[i][j] / Integer.parseInt(args[4]) + 1); k++) {
                    loc.add(0, 1, 0);
                    Bukkit.broadcastMessage(k + "");

                    if (k - height0 / Integer.parseInt(args[4]) > height * 0.9) {
                        loc.getBlock().setType(Material.SNOW_BLOCK);
                    } else if (k - height0 / Integer.parseInt(args[4]) == height * 0.9) {
                        if (r.nextInt(2) == 1) {
                            loc.getBlock().setType(Material.SNOW_BLOCK);
                        } else {
                            loc.getBlock().setType(Material.STONE);
                        }
                    } else if (k - height0 / Integer.parseInt(args[4]) > height / Integer.parseInt(args[4]) * 0.6) {
                        loc.getBlock().setType(Material.STONE);
                    } else if (k - height0 / Integer.parseInt(args[4]) == height * 0.6) {
                        if (r.nextInt(2) == 1) {
                            loc.getBlock().setType(Material.STONE);
                        } else {
                            loc.getBlock().setType(Material.DIRT);
                        }
                    } else if (k - height0 / Integer.parseInt(args[4]) > height / Integer.parseInt(args[4]) * 0.4) {

                        if (k - height0 / Integer.parseInt(args[4]) - 1 <= height / Integer.parseInt(args[4]) * 0.4) {
                            if (r.nextInt(2) == 1) {
                                loc.getBlock().setType(Material.DIRT);
                            } else {
                                loc.getBlock().setType(Material.SANDSTONE);
                            }
                        } else {
                            loc.getBlock().setType(Material.DIRT);
                        }
                    } else if (k - height0 / Integer.parseInt(args[4]) >= height / Integer.parseInt(args[4]) * 0.3) {
                        loc.getBlock().setType(Material.SANDSTONE);
                    } else if (k - height0 / Integer.parseInt(args[4]) >= height / Integer.parseInt(args[4]) * 0.1) {
                        loc.getBlock().setType(Material.WATER);
                    } else {
                        loc.getBlock().setType(Material.STONE);
                    }
                }

                loc.add(1, -(myMap.map[i][j] / Integer.parseInt(args[4]) - height0 / Integer.parseInt(args[4]) + 1), 0);
            }
            loc.add(-myMap.map.length, 0, 1);
        }

        return true;
    }

    JFrame frame;

    private void createFrame(int width, int height, int cellSize, long seed) {
        frame = new JFrame("Perlin noise 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new MyMap(width, height, cellSize, seed));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class MyMap extends JPanel {
    static int[][] map;
    private int widthInCell;
    private int heightInCell;
    private int cellSize;

    MyMap(int width, int height, int cellSize, long seed) {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));

        widthInCell = width / cellSize;
        heightInCell = height / cellSize;
        this.cellSize = cellSize;

        map = new int[widthInCell][heightInCell];

        Perlin2D perlin = new Perlin2D(seed);
        for (int x = 0; x < widthInCell; x++) {
            for (int y = 0; y < heightInCell; y++) {
                float value = perlin.getNoise(x / 100f, y / 100f, 8, 0.5f);
                map[x][y] = (int) (value * 255 + 128) & 255;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < widthInCell; ++x) {
            for (int y = 0; y < heightInCell; ++y) {
                Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize, cellSize, cellSize);
                g2d.setColor(new Color(map[x][y], map[x][y], map[x][y]));
                g2d.fill(cell);
            }
        }
    }

}

class Perlin2D {
    private byte[] permutationTable;

    public Perlin2D(long seed) {
        Random random = new Random(seed);
        permutationTable = new byte[1024];
        random.nextBytes(permutationTable);
    }

    public float getNoise(float fx, float fy, int octaves, float persistence) {
        float amplitude = 1;
        float max = 0;
        float result = 0;

        while (octaves-- > 0) {
            max += amplitude;
            result += getNoise(fx, fy) * amplitude;
            amplitude *= persistence;
            fx *= 2;
            fy *= 2;
        }

        return result / max;
    }

    public float getNoise(float x, float y) {
        int left = (int) x;
        int top = (int) y;

        float localX = x - left;
        float localY = y - top;

        // извлекаем градиентные векторы для всех вершин квадрата:
        Vector topLeftGradient = getPseudoRandomGradientVector(left, top);
        Vector topRightGradient = getPseudoRandomGradientVector(left + 1, top);
        Vector bottomLeftGradient = getPseudoRandomGradientVector(left, top + 1);
        Vector bottomRightGradient = getPseudoRandomGradientVector(left + 1, top + 1);

        // вектора от вершин квадрата до точки внутри квадрата:
        Vector distanceToTopLeft = new Vector(localX, localY);
        Vector distanceToTopRight = new Vector(localX - 1, localY);
        Vector distanceToBottomLeft = new Vector(localX, localY - 1);
        Vector distanceToBottomRight = new Vector(localX - 1, localY - 1);

        // считаем скалярные произведения между которыми будем интерполировать
        float tx1 = dot(distanceToTopLeft, topLeftGradient);
        float tx2 = dot(distanceToTopRight, topRightGradient);
        float bx1 = dot(distanceToBottomLeft, bottomLeftGradient);
        float bx2 = dot(distanceToBottomRight, bottomRightGradient);

        // интерполяция:
        float tx = lerp(tx1, tx2, qunticCurve(localX));
        float bx = lerp(bx1, bx2, qunticCurve(localX));
        float tb = lerp(tx, bx, qunticCurve(localY));

        return tb;
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private float dot(Vector a, Vector b) {
        return a.x * b.x + a.y * b.y;
    }

    private float qunticCurve(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private Vector getPseudoRandomGradientVector(int x, int y) {
        // псевдо-случайное число от 0 до 3 которое всегда неизменно при данных x и y
        int v = (int) (((x * 1836311903L) ^ (y * 2971215073L) + 4807526976L) & 1023L);
        v = permutationTable[v] & 3;

        switch (v) {
            case 0:
                return new Vector(1, 0);
            case 1:
                return new Vector(-1, 0);
            case 2:
                return new Vector(0, 1);
            default:
                return new Vector(0, -1);
        }
    }


    private static class Vector {
        float x;
        float y;

        Vector(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }

}

