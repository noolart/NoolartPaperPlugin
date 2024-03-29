package com.noolart.noolartpaperplugin.listener;

import com.google.common.collect.Lists;
import com.noolart.noolartpaperplugin.*;
import com.noolart.noolartpaperplugin.commands.Commands;
import com.noolart.noolartpaperplugin.csv.PasteCsv;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.Openable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LecternInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;


import java.io.*;
import java.util.*;

import static com.noolart.noolartpaperplugin.NoolartPaperPlugin.*;


public class MyListener implements Listener {
    public NoolartPaperPlugin plugin;

    public static String[] expShopStraight = {"18", "2", "20", "38", "14", "32", "26"};
    public static int[][] expShopValues = {{}, {18}, {18}, {18}, {2, 20}, {20, 38}, {14, 32}};
    private static final String[] graphicsNames = {"Density", "Magnetic", "Rad", "Res", "Vp", "Vs"};
    public static Block station;
    private static int baseID = 0;
    int spongeClickCounter = 0;


    public static ItemStack mapToStack(String filename, World w) {
        MapView map = Bukkit.createMap(w);

        for (MapRenderer mapRenderer : map.getRenderers()) {
            map.removeRenderer(mapRenderer);
        }
        map.addRenderer(new PictureDrawer(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "imgs" + File.separator + filename), MapView.Scale.NORMAL, 0, 0));
        ItemStack stack = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) stack.getItemMeta();
        meta.setMapId(map.getId());
        stack.setItemMeta(meta);
        return stack;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws FileNotFoundException {

        World w = event.getPlayer().getWorld();

        for (Entity e : w.getEntities()){
            if(e.getType()!=EntityType.PLAYER) {
                e.remove();
            }
        }
        //***********************************
        //TODO уберите эту сторчку в случае создания нового игрового мира
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 99, -57,-99));
        event.getPlayer().getWorld().spawn(event.getPlayer().getLocation().add(0.5,0,3.5), Villager.class, villager -> {
            villager.setRotation(180,0);
            villager.setCanPickupItems(false);
            villager.setProfession(Villager.Profession.NONE);
            villager.setCustomName("" + ChatColor.AQUA + ChatColor.BOLD +"Играть");
            villager.setAI(false);
        });

        event.getPlayer().getWorld().spawn(new Location(event.getPlayer().getWorld(),896.5, 94,1055.5), Villager.class, villager -> {
            villager.setRotation(180,0);
            villager.setCanPickupItems(false);
            villager.setProfession(Villager.Profession.NONE);
            villager.setCustomName("" + ChatColor.AQUA + ChatColor.BOLD +"Уровень 2");
            villager.setAI(false);
        });


        //entity.setInvulnerable(boolean flag) - неуязвимость
//
        event.getPlayer().getWorld().spawn(new Location(event.getPlayer().getWorld(), 95.5, -57, -95.5), Villager.class, villager -> {
            villager.setRotation(270,0);
            villager.setCanPickupItems(false);
            villager.setProfession(Villager.Profession.NONE);
            villager.setCustomName("" + ChatColor.AQUA + ChatColor.BOLD +"Вулкан");
            villager.setAI(false);

        });




        event.getPlayer().getWorld().spawn(new Location(event.getPlayer().getWorld(), 889.5,94,1048.5), Villager.class, villager -> {
            villager.setRotation(225,0);
            villager.setCanPickupItems(false);
            villager.setProfession(Villager.Profession.NONE);
            villager.setCustomName("" + ChatColor.AQUA + ChatColor.BOLD +"Торговец");
            villager.setProfession(Villager.Profession.ARMORER);
            villager.setCanPickupItems(false);
            villager.setAI(false);
            villager.setVillagerLevel(5);
            ItemStack tag = new ItemStack (Material.NAME_TAG, 1);
            ItemMeta tagItemMeta = tag.getItemMeta();
            tagItemMeta.setDisplayName(ChatColor.AQUA +  "Ключ");
            tag.setItemMeta(tagItemMeta);
            MerchantRecipe merchantRecipe = new MerchantRecipe(tag, 1000000);
            merchantRecipe.setIngredients(Collections.singletonList(new ItemStack(Material.DIAMOND, 10)));
            List <MerchantRecipe> merchantRecipeList = Collections.singletonList(merchantRecipe);
            villager.setRecipes(merchantRecipeList);


        });

//
//
//        Image.pasteImageInItemFrame( "cobblecraft.png", BlockFace.SOUTH, new Location (event.getPlayer().getLocation().getWorld(), 1001.00, 140.00, 1001.00).getBlock());
//
//
//
//
        Image.pasteImageInItemFrame("diagonal.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1208));
        Image.pasteImageInItemFrame("vertical.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1204));
        Image.pasteImageInItemFrame("circle.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1200));
        Image.pasteImageInItemFrame("square.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1196));

        Image.pasteImageInItemFrame("diagonal.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1152));
        Image.pasteImageInItemFrame("vertical.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1148));
        Image.pasteImageInItemFrame("circle.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1144));
        Image.pasteImageInItemFrame("square.png",BlockFace.WEST, w.getBlockAt( 787 ,124, 1140));

        Image.pasteImageInItemFrame("diagonal.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1152));
        Image.pasteImageInItemFrame("vertical.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1148));
        Image.pasteImageInItemFrame("circle.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1144));
        Image.pasteImageInItemFrame("square.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1140));

        Image.pasteImageInItemFrame("diagonal.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1202));
        Image.pasteImageInItemFrame("vertical.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1198));
        Image.pasteImageInItemFrame("circle.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1194));
        Image.pasteImageInItemFrame("square.png",BlockFace.WEST, w.getBlockAt( 838 ,110, 1190));


//

        //***********************************


        event.getPlayer().getWorld().setTime(6000);

        event.getPlayer().getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        event.getPlayer().getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);


        event.setJoinMessage("" + ChatColor.GOLD + ChatColor.BOLD + "Welcome, " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + event.getPlayer().getName() + "!" + "\n" + ChatColor.GOLD + ChatColor.BOLD + "server by Novosibirsk State University");

        for (Player p : Bukkit.getOnlinePlayers()) {
            NoolartPaperPlugin.point1 = new Location(p.getWorld(), 0, 0, 0);
            NoolartPaperPlugin.point2 = new Location(p.getWorld(), 0, 0, 0);
        }

        File file = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + event.getPlayer().getName() + ".csv");
        if (!file.exists()) {
            try {
                Scanner scan = new Scanner(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + "original.csv"));
                PrintWriter pw = new PrintWriter(file);

                while (scan.hasNextLine()) {
                    pw.write(scan.nextLine() + "\n");
                }
                pw.close();
                scan.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HashMap<String, String> playerFind = new HashMap<>();
        Scanner scan = new Scanner(new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + event.getPlayer().getName() + ".csv"));


        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            String a = s.split(":")[0];
            String b = s.split(":")[1];

            playerFind.put(a, b);
        }
        scan.close();

        NoolartPaperPlugin.finds.put(event.getPlayer().getName(), playerFind);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) throws IOException {
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR) {
            Player p = event.getPlayer();
            ItemStack item = p.getItemInHand();
            if (item.getType() == Material.DIAMOND_HOE) {
                try (FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "notes.csv", false)) {
                    Location l = p.getLocation();

                    int cx = (int) l.getX();
                    int cy = (int) l.getY();
                    int cz = (int) l.getZ();
                    int sth = 30;

                    for (int i = cx; i < cx + sth; i++) {
                        for (int i1 = cy; i1 < cy + sth; i1++) {
                            for (int i2 = cz; i2 < cz + sth; i2++) {
                                World w = p.getWorld();
                                Location h = new Location(w, i, i1, i2);
                                writer.write(i + ";" + i1 + ";" + i2 + ";" + h.getBlock().getType() + ";" + "\n");
                                Bukkit.broadcastMessage(i + " " + i1 + " " + i2 + " " + h.getBlock().getType());
                            }
                        }

                    }

                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }//if

        if (action == Action.RIGHT_CLICK_AIR) {
            Player p = event.getPlayer();
            ItemStack item = p.getItemInHand();
            if (item.getType() == Material.NETHERITE_HOE) {
                p.sendMessage("LLS");
                String path = NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "\\notes.csv";

                File file = new File(path);

                Scanner scan = new Scanner(file);
                StringBuilder text = new StringBuilder();


                while (scan.hasNextLine()) {
                    String string1 = scan.nextLine();
                    text.append(string1);
                }

                scan.close();

//			Bukkit.broadcastMessage(text);


                String[] symbols = text.toString().split(";");

                Location h = p.getLocation();
                int cx1 = (int) h.getX();
                int cy1 = (int) h.getY();
                int cz1 = (int) h.getZ();
                int sth1 = 30;
                int k = 3;

                for (int q = cx1; q < cx1 + sth1; q++) {
                    for (int q1 = cy1; q1 < cy1 + sth1; q1++) {
                        for (int q2 = cz1; q2 < cz1 + sth1; q2++) {
                            String materialName = symbols[k].toUpperCase();
                            Material theMaterial = null;

                            try {
                                theMaterial = Material.valueOf(materialName);
                            } catch (Exception e1) {
                                //Not a valid material
                            }

                            World w = p.getWorld();
                            Location h1 = new Location(w, q, q1, q2);
                            assert theMaterial != null;
                            h1.getBlock().setType(theMaterial);
                            k = k + 4;
                        }
                    }
                }
            }


        }

        if (action == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInHand();
            if (itemStack.getType() == Material.WOODEN_SWORD) {
                NoolartPaperPlugin.point1 = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                double x = event.getClickedBlock().getLocation().getX();
                double y = event.getClickedBlock().getLocation().getY();
                double z = event.getClickedBlock().getLocation().getZ();

                Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
                Objective obj = board.registerNewObjective("prefixofuser", "dummy");

                obj.setDisplayName(ChatColor.DARK_RED + "§l[Selected points:§l]");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score s1 = obj.getScore(ChatColor.YELLOW + "1st point: " + "x=" + x + " y=" + y + " z=" + z);
                Score s2 = obj.getScore(ChatColor.YELLOW + "2nd point: " + "x=" + NoolartPaperPlugin.point2.getX() + " y=" + NoolartPaperPlugin.point2.getY() + " z=" + NoolartPaperPlugin.point2.getZ());
                s1.setScore(1);
                s2.setScore(0);
                player.setScoreboard(board);
            }
        }



        if (action == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInHand();

            if (event.getClickedBlock().getType() == Material.JUNGLE_BUTTON){

                Block b = event.getClickedBlock();
                Player p = event.getPlayer();
                Location l = b.getLocation();

                if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1207){
                    p.sendMessage ("" + ChatColor.GREEN + ChatColor.BOLD + "ПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1);
                }

                else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1203){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1199){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1195){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1201){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1197){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1189){
                    p.sendMessage ("" + ChatColor.GREEN + ChatColor.BOLD + "ПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1193){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1151){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1147){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1143){
                    p.sendMessage ("" + ChatColor.GREEN + ChatColor.BOLD + "ПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1);
                }
                else if ((int)l.getX() == 838 && (int)l.getY() == 108 && (int)l.getZ() == 1139){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }

                else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1151){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }
                else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1147){
                    p.sendMessage ("" + ChatColor.GREEN + ChatColor.BOLD + "ПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1f,1);
                }
                else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1143){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }else if ((int)l.getX() == 787 && (int)l.getY() == 122 && (int)l.getZ() == 1139){
                    p.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "НЕПРАВИЛЬНО!");
                    l.getWorld().playSound(l,Sound.BLOCK_BEACON_DEACTIVATE,1f,1);
                }





            }



            if (event.getClickedBlock().getType() == Material.IRON_DOOR && player.getItemInHand().getType() == Material.NAME_TAG && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Ключ")){


                BlockState blockState = event.getClickedBlock().getState();

                Openable openable = (Openable) blockState.getBlockData();
                openable.setOpen(true);
                blockState.setBlockData(openable);blockState.update();
                event.getPlayer().playSound(blockState.getBlock().getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1,1);

            }


            if (event.getClickedBlock().getType() == Material.SPONGE && player.getItemInHand().getType() == Material.PAPER) {

                if (spongeClickCounter == graphicsNames.length) {
                    spongeClickCounter = 0;
                }

                ItemStack paper = player.getItemInHand();
                String paperText = Objects.requireNonNull(paper.getLore()).get(0);
                int curBaseID = Integer.parseInt(paperText);

                int x = 0;
                int y = 3;
                int z = -1;
                World world = player.getWorld();
                Block clickedBlock = event.getClickedBlock();



                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int finalI = i;
                        int finalJ = j;
                        world.spawn(new Location(world, clickedBlock.getX() + x, clickedBlock.getY() + y, clickedBlock.getZ() + z), ItemFrame.class, itemFrame -> {
                            itemFrame.setFacingDirection(BlockFace.NORTH, false);
                            itemFrame.setItem(mapToStack(curBaseID + graphicsNames[spongeClickCounter] + "-" + finalI + "-" + finalJ + ".jpg", world));
                        });
                        x--;
                    }
                    y--;
                    x = 0;
                }
                spongeClickCounter++;
            }

            if(event.getClickedBlock().getType() == Material.PINK_WOOL && player.getItemInHand().getType() == Material.PAPER) {
                Location screenCenter = event.getClickedBlock().getLocation().add(0, 2, -1);

                if(!screenCenter.getNearbyEntities(2, 2, 2).isEmpty()) {
                    for (Entity entity : screenCenter.getNearbyEntities(2, 2, 2)) {
                        try {
                            entity.remove();
                        } catch (UnsupportedOperationException ignored) {}
                    }
                }
            }

            if (event.getClickedBlock().getType() == Material.POLISHED_BLACKSTONE_BUTTON && event.getClickedBlock().getLocation().add(0,0,1).getBlock().getType()==Material.STONE_BRICKS){
                Block clickedBlock = event.getClickedBlock();
                Location clickedBlockLocation = clickedBlock.getLocation();

                Iterator<ItemFrame> iterator = clickedBlockLocation.add(0,1,2).getNearbyEntitiesByType(ItemFrame.class,1).iterator();
                ItemFrame it = iterator.next();

                Iterator<ItemFrame> iterator1 = clickedBlock.getLocation().add(0,1,0).getNearbyEntitiesByType(ItemFrame.class,1).iterator();

                ItemFrame screen = iterator1.next();
                screen.setItem(mapToStack( "microscope" + File.separator + it.getItem().getType().toString().toLowerCase()+".jpg",event.getPlayer().getWorld()));

            }

            if (itemStack.getType() == Material.WOODEN_SWORD) {
                NoolartPaperPlugin.point2 = event.getClickedBlock().getLocation();
                double x = event.getClickedBlock().getLocation().getX();
                double y = event.getClickedBlock().getLocation().getY();
                double z = event.getClickedBlock().getLocation().getZ();

                Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
                Objective obj = board.registerNewObjective("prefixofuser", "dummy");

                obj.setDisplayName(ChatColor.DARK_RED + "§l[Selected points:]");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score s1 = obj.getScore(ChatColor.YELLOW + "1st point: " + "x=" + NoolartPaperPlugin.point1.getX() + " y=" + NoolartPaperPlugin.point1.getY() + " z=" + NoolartPaperPlugin.point1.getZ());
                Score s2 = obj.getScore(ChatColor.YELLOW + "2nd point: " + "x=" + x + " y=" + y + " z=" + z);
                s1.setScore(1);
                s2.setScore(0);
                player.setScoreboard(board);
            }

            ItemStack i = player.getItemInHand();

            if (event.getClickedBlock().getType() == Material.LECTERN && !Materials.getString(i.getType().toString().toLowerCase(), "name").equals("")
                    && finds.get(event.getPlayer().getName()).get(event.getPlayer().getItemInHand().getType().toString().toLowerCase()).equals("false")) {
                String found = Materials.getString(i.getType().toString().toLowerCase(), "name");

                Lectern l = (Lectern) event.getClickedBlock().getState();
                LecternInventory lecternInventory = (LecternInventory) l.getInventory();

                    //for (HumanEntity humanEntity : lecternInventory.getViewers()) {
                    lecternInventory.close();
                    // }

//            e5.getPlayer().playSound(e5.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,16f,1f);

                event.getPlayer().getWorld().spawn(event.getClickedBlock().getLocation().add(0.5, 1, 0.5), Firework.class, firework -> {

                    FireworkMeta fm = firework.getFireworkMeta();
                    List<Color> c = new ArrayList<>();
                    c.add(Color.PURPLE);
                    c.add(Color.GREEN);
                    c.add(Color.YELLOW);
                    FireworkEffect ef = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
                    fm.addEffect(ef);
                    firework.setFireworkMeta(fm);
                    firework.detonate();

                });

                event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "============================" + "\n" + ChatColor.DARK_GREEN + "Поздравляем, ты нашел " + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + found.toUpperCase() + ChatColor.RESET + ChatColor.DARK_GREEN + "!" + "\n" + "Теперь характеристи каждого блока типа " + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + found.toUpperCase() + ChatColor.RESET + ChatColor.DARK_GREEN + " будут показываться на экране при наведении!" + "\n" + ChatColor.LIGHT_PURPLE + "============================");

                finds.get(event.getPlayer().getName()).put(i.getType().toString().toLowerCase(), "true");
                FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + event.getPlayer().getName() + ".csv");
                String[] keys = new String[48];
                String[] values = new String[48];

                int j = 0;
                for (String key : finds.get(event.getPlayer().getName()).keySet()) {
                    keys[j] = key;
                    j++;
                }

                j = 0;

                for (String value : finds.get(event.getPlayer().getName()).values()) {
                    values[j] = value;
                    j++;
                }

                StringBuilder text = new StringBuilder();
                for (int i1 = 0; i1 < 48; i1++) {
                    text.append(keys[i1]).append(":").append(values[i1]).append("\n");
                }

                writer.write(text.toString());
                writer.close();
            }

            if(event.getClickedBlock().getType() == Material.POLISHED_BLACKSTONE) {
                ItemStack paperWithBaseId = new ItemStack(Material.PAPER);
                ItemMeta paperMeta = paperWithBaseId.getItemMeta();
                paperMeta.setLore(Collections.singletonList(Integer.toString(baseID)));
                paperWithBaseId.setItemMeta(paperMeta);
                player.getInventory().addItem(paperWithBaseId);

            }
        }
    }

    @EventHandler
    public void interact(InventoryOpenEvent inventoryOpenEvent) {
        if (inventoryOpenEvent.getInventory() instanceof LecternInventory) {
            inventoryOpenEvent.setCancelled(true);
        }


    }

    @EventHandler
    public void interact(BlockPlaceEvent blockPlaceEvent) {
        Block block = blockPlaceEvent.getBlock();
        BlockState blockState = block.getState();


        if (block.getType() == Material.OAK_LOG){
            NoolartPaperPlugin.point1 = block.getLocation().add(-2,0,-2);
            PasteCsv.paste("tree",blockPlaceEvent.getPlayer());
        }



        if (block.getType() == Material.LECTERN) {
            if (blockState instanceof Lectern) {
                Lectern l = (Lectern) blockState;
                ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
                l.getInventory().addItem(itemStack);
            }

            blockPlaceEvent.getPlayer().getWorld().spawn(block.getLocation().add(0.5, -0.8, 0.5), ArmorStand.class, armorStand -> {
                armorStand.setVisible(false);
                armorStand.setCustomName(ChatColor.DARK_PURPLE + "Изучить");
                armorStand.setCustomNameVisible(true);
                armorStand.setCanMove(false);
                armorStand.setGravity(false);
            });
        }


        if (block.getType() == Material.OBSERVER) {
            try (FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "observers.csv", true)) {
                writer.write(block.getX() + ";" + block.getY() + ";" + block.getZ() + ";" + "\n");
                blockPlaceEvent.getPlayer().sendMessage(ChatColor.GOLD + "Observer have been placed!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Commands.pythonrun("graphic", null);
            World w = blockPlaceEvent.getPlayer().getWorld();
            w.spawn(new Location(w, block.getX(), block.getY() + 1, block.getZ()), ItemFrame.class, itemFrame -> {
                itemFrame.setFacingDirection(BlockFace.UP, false);
                itemFrame.setItem(mapToStack("test.png", w));
                itemFrame.setItemDropChance(0f);
            });

//            e1.getPlayer().getInventory().addItem(mapToStack("test.png",e1.getPlayer().getWorld()));
        }

        if (block.getType() == Material.OAK_FENCE){


            station = block;

            ItemStack dark_oak_fence = new ItemStack(Material.DARK_OAK_FENCE,1);
            ItemStack iron_bars = new ItemStack(Material.IRON_BARS, 1);
            ItemStack stone_bricks = new ItemStack(Material.STONE_BRICKS, 1);
            ItemStack chest_with_kern = new ItemStack(Material.CHEST,1);

            ItemMeta dark_oak_fenceMeta = dark_oak_fence.getItemMeta();
            dark_oak_fenceMeta.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Шахта");
            dark_oak_fence.setItemMeta(dark_oak_fenceMeta);
            dark_oak_fence.setLore(Collections.singletonList(""+ChatColor.YELLOW + ChatColor.ITALIC + "Срез 3x3"));

            ItemMeta iron_barsMeta = iron_bars.getItemMeta();
            iron_barsMeta.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Скважина без керна");
            iron_bars.setItemMeta(iron_barsMeta);
            iron_bars.setLore(Collections.singletonList(""+ChatColor.YELLOW + ChatColor.ITALIC + "Данные пород"));

            ItemMeta stone_bricksMeta = stone_bricks.getItemMeta();
            stone_bricksMeta.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Микроскоп");
            stone_bricks.setItemMeta(stone_bricksMeta);
            stone_bricks.setLore(Collections.singletonList(""+ChatColor.YELLOW + ChatColor.ITALIC + "Кристаллическая решетка вещества"));

            ItemMeta chest_with_kernMeta = chest_with_kern.getItemMeta();
            chest_with_kernMeta.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Скважина с керном");
            chest_with_kern.setItemMeta(chest_with_kernMeta);
            chest_with_kern.setLore(Collections.singletonList(""+ChatColor.YELLOW + ChatColor.ITALIC + "Данные пород + керн в сундуке"));


            NoolartPaperPlugin.chooseToBuild.setItem(3,dark_oak_fence);
            NoolartPaperPlugin.chooseToBuild.setItem(4,iron_bars);
            NoolartPaperPlugin.chooseToBuild.setItem(5,stone_bricks);
            NoolartPaperPlugin.chooseToBuild.setItem(6,chest_with_kern);

            blockPlaceEvent.getPlayer().openInventory(chooseToBuild);

        }



        if (block.getType() == Material.DARK_OAK_FENCE) {

            if (block.getLocation().add(0,-1,0).getBlock().getType()==Material.RED_CONCRETE) {
                pasteRid(blockPlaceEvent.getBlock(),blockPlaceEvent.getPlayer());
            }

            else{
                blockPlaceEvent.setCancelled(true);
                blockPlaceEvent.getPlayer().sendMessage(ChatColor.DARK_RED + "Шахту можно разместить только на специальной платформе");
            }
        }

        if (block.getType() == Material.STONE_BRICKS) {

            pateStoneBricks(block,blockPlaceEvent.getPlayer());

        }


        if (block.getType() == Material.IRON_BARS) {
            if (block.getLocation().add(0,-1,0).getBlock().getType()==Material.RED_CONCRETE) {
                NoolartPaperPlugin.point1 = new Location(blockPlaceEvent.getPlayer().getWorld(), block.getLocation().getX() - 3, block.getLocation().getY(), block.getLocation().getZ() - 3);
                blockPlaceEvent.getPlayer().sendMessage("wait...");
                pasteIronBar(block,blockPlaceEvent.getPlayer(), true);
            }
            else{
                blockPlaceEvent.setCancelled(true);
                blockPlaceEvent.getPlayer().sendMessage(ChatColor.DARK_RED + "Скважину можно разместить только на специальной платформе!");
            }

        }

        if (blockPlaceEvent.getBlock().getType() == Material.RED_CONCRETE) {

                boolean flag = true;
                Location l = block.getLocation().add(-5, 0, -5);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 10; j++) {
                        for (int k = 0; k < 10; k++) {
                            l.add(0, 0, 1);
                            //Bukkit.broadcastMessage(l.getBlock().getType().toString() + " "+l.getX()+" " + l.getZ());

                            if (l.getBlock().getType() != Material.AIR && l.getBlock().getType() != Material.RED_CONCRETE) {
                                flag = false;
                                break;
                            }
                        }
                        if (!flag) {
                            break;
                        }
                        l.add(1, 0, -10);
                    }
                    if (!flag) {
                        break;
                    }
                    l.add(-10, 1, 0);

                }
                if (flag) {
                    NoolartPaperPlugin.point1 = blockPlaceEvent.getBlock().getLocation().add(-5, 0, -5);
                    //PasteCsv.paste("building_platform",blockPlaceEvent.getPlayer());
//                    Thread thread = new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            for (int i = 5; i > 0; i--) {
//                                Bukkit.broadcastMessage(ChatColor.RED + ""+i);
//                                //armorStand.setCustomName(ChatColor.RED + "" + i);
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                    });
//                    thread.start();
//                    thread.join();
                        NoolartPaperPlugin.point1 = blockPlaceEvent.getBlock().getLocation().add(-5, 0, -5);
                        PasteCsv.pasteQuiet("11x11place", blockPlaceEvent.getPlayer());

                } else {
                    blockPlaceEvent.setCancelled(true);
                    blockPlaceEvent.getPlayer().sendMessage(ChatColor.DARK_RED + "Для установки платформы необходима свободная зона 11x6x11");
                }


        }

        if(block.getType() == Material.SPONGE) {
            Location spongeLocation = block.getLocation();
            Location woolLocation = new Location(blockPlaceEvent.getPlayer().getWorld(), spongeLocation.getX() - 1, spongeLocation.getY(), spongeLocation.getZ());
            woolLocation.getBlock().setType(Material.PINK_WOOL);

            for(int i = 0; i <= 2; i++) {
                for(int j = 1; j <= 3; j++) {
                    Location nextBlockLocation = new Location(blockPlaceEvent.getPlayer().getWorld(),
                            spongeLocation.getX() - i, spongeLocation.getY() + j, spongeLocation.getZ());
                    nextBlockLocation.getBlock().setType(Material.STONE);
                }
            }

        }
    }


    @EventHandler
    public void inventoryClick(InventoryClickEvent inventoryClickEvent) throws IOException {
        if (inventoryClickEvent.getInventory() == NoolartPaperPlugin.shop && inventoryClickEvent.getRawSlot() < 54 && inventoryClickEvent.getRawSlot() > -1) {
            inventoryClickEvent.setCancelled(true);

            ItemStack cursor = inventoryClickEvent.getCurrentItem();
            Player player = (Player) inventoryClickEvent.getWhoClicked();
            assert cursor != null;

            int balance;
            int price = NoolartPaperPlugin.shopPrices.get(cursor.getType().toString());

            File balanceCheck = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + inventoryClickEvent.getWhoClicked().getName() + "_balance.csv");

            if (!balanceCheck.exists()) {
                PrintWriter pw = new PrintWriter(balanceCheck);
                pw.write(Integer.toString(1000));
                pw.close();
            }
            Scanner scan = new Scanner(balanceCheck);
            balance = Integer.parseInt(scan.nextLine());
            scan.close();

            if (inventoryClickEvent.isRightClick()) {
                if (balance - price >= 0) {
                    balance -= price;
                    Objects.requireNonNull(Bukkit.getPlayer(inventoryClickEvent.getWhoClicked().getName())).getInventory().addItem(cursor);
                    Objects.requireNonNull(Bukkit.getPlayer(inventoryClickEvent.getWhoClicked().getName())).sendMessage(ChatColor.GOLD + "Вы купили " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + Materials.getString(cursor.getType().toString().toLowerCase(), "name") + ChatColor.RESET + ChatColor.GOLD + " Теперь ваш баланс: " + balance + "$");
                    FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + inventoryClickEvent.getWhoClicked().getName() + "_balance.csv", false);
                    writer.write(Integer.toString(balance));
                    writer.close();
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "Не хватает денег!");
                }
            }

            if (inventoryClickEvent.isLeftClick()) {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);

                    if (stack == null)
                        continue;

                    if (stack.getType() == cursor.getType()) {
                        stack.setAmount(stack.getAmount() - 1);
                        balance += ((int) (price * 0.8));
                        Objects.requireNonNull(Bukkit.getPlayer(inventoryClickEvent.getWhoClicked().getName())).sendMessage(ChatColor.GOLD + "Вы продали " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + Materials.getString(cursor.getType().toString().toLowerCase(), "name") + ChatColor.RESET + ChatColor.GOLD + " Теперь ваш баланс: " + balance + "$");
                        FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "users" + File.separator + inventoryClickEvent.getWhoClicked().getName() + "_balance.csv", false);
                        writer.write(Integer.toString(balance));
                        writer.close();
                        return;
                    }
                }
                (inventoryClickEvent.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "У Вас нет нужного предмета!");
            }
        }

        if (inventoryClickEvent.getInventory() == expShop && inventoryClickEvent.getRawSlot() < 54 && inventoryClickEvent.getRawSlot() > -1 && inventoryClickEvent.getCursor() != null) {
            inventoryClickEvent.setCancelled(true);

            ItemStack cursor = inventoryClickEvent.getCurrentItem();
            Player p = (Player) inventoryClickEvent.getWhoClicked();
            boolean flag = true;
            assert cursor != null;
            ItemMeta cursorMeta = cursor.getItemMeta();

            if (cursor.getType() != Material.YELLOW_STAINED_GLASS_PANE && cursor.getType() != Material.RED_STAINED_GLASS_PANE && cursor.getType() != Material.ORANGE_STAINED_GLASS_PANE && cursor.getType() != Material.LIME_STAINED_GLASS_PANE) {
                //p.sendMessage(Integer.toString(inventoryClickEvent.getSlot()));
                for (int slots : expShopValues[Arrays.asList(expShopStraight).indexOf(Integer.toString(inventoryClickEvent.getSlot()))]) {
                    //Bukkit.broadcastMessage(Integer.toString(slots));
                    ItemStack itemInSlot = expShop.getItem(slots);
                    assert itemInSlot != null;
                    ItemMeta checkMending = itemInSlot.getItemMeta();

                    if (!checkMending.hasEnchant(Enchantment.MENDING)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    if (p.getTotalExperience() >= 51) {
                        p.setTotalExperience(p.getTotalExperience() - 51);

                        if (inventoryClickEvent.getSlot() == 18) {
                            expShop.setItem(19, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                            expShop.setItem(9, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                            expShop.setItem(27, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                            cursorMeta.addEnchant(Enchantment.MENDING, 1, true);
                            cursor.setItemMeta(cursorMeta);
                        } else {
                            cursorMeta.addEnchant(Enchantment.MENDING, 1, true);
                            cursor.setItemMeta(cursorMeta);

                            int slot = inventoryClickEvent.getSlot();
                            expShop.setItem(slot + 1, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                            for (int i = 0; i < 5; i++) {
                                if (slot != 0 && expShop.getItem(slot - 1) != null) {
                                    if (Objects.requireNonNull(expShop.getItem(slot - 1)).getType() == Material.RED_STAINED_GLASS_PANE) {

                                        expShop.setItem(slot - 1, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                        slot--;
                                    } else {
                                        break;
                                    }

                                } else if (slot + 9 <= 44 && expShop.getItem(slot + 9) != null && Objects.requireNonNull(expShop.getItem(slot + 9)).getType() == Material.RED_STAINED_GLASS_PANE) {

                                    expShop.setItem(slot + 9, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                    slot += 9;
                                } else if (slot != 0 && expShop.getItem(slot - 9) != null && Objects.requireNonNull(expShop.getItem(slot - 9)).getType() == Material.RED_STAINED_GLASS_PANE) {
                                    expShop.setItem(slot - 9, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                    slot -= 9;
                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Не хватает опыта!");
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Сначала открой предыдущие навыки!");
                }
            } else if (Objects.requireNonNull(inventoryClickEvent.getCurrentItem()).getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                p.closeInventory();

                int page = Integer.parseInt(String.valueOf(Objects.requireNonNull(expShop.getItem(53)).getItemMeta().getDisplayName().charAt(21)));

                expShop = Bukkit.createInventory(null, 54, "Experience Shop Page " + page);

                File expShopCsv = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "expShop" + File.separator + "expShop" + page + ".csv");

                try {
                    FileReader fileInputStream = new FileReader(expShopCsv);
                    BufferedReader br = new BufferedReader(fileInputStream);
                    for (int j = 0; j < 45; j++) {
                        String nextLine = br.readLine();
                        String[] current = nextLine.split(":");

                        if (nextLine.equals("1")) {
                            ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            expShop.setItem(j, itemStack);
                        } else if (!nextLine.equals("0")) {
                            ItemStack itemStack = new ItemStack(Material.valueOf(current[0]));
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName("" + org.bukkit.ChatColor.GREEN + org.bukkit.ChatColor.BOLD + current[1]);

                            itemMeta.setLore(Arrays.asList(current[2].split(";")));
                            itemStack.setItemMeta(itemMeta);
                            expShop.setItem(j, itemStack);
                        }
                    }
                    br.close();

                    int pageUp = page + 1;
                    if (pageUp > 5) pageUp = 1;

                    int pageDown = page - 1;
                    if (pageDown < 1) pageDown = 5;

                    ItemStack back = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                    ItemMeta backItemMeta = back.getItemMeta();
                    backItemMeta.setDisplayName("" + org.bukkit.ChatColor.AQUA + org.bukkit.ChatColor.BOLD + "Назад (Страница " + pageDown + ")");
                    back.setItemMeta(backItemMeta);
                    expShop.setItem(45, back);

                    ItemStack up = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                    ItemMeta upItemMeta = back.getItemMeta();
                    upItemMeta.setDisplayName("" + org.bukkit.ChatColor.AQUA + org.bukkit.ChatColor.BOLD + "Вперед (Страница " + pageUp + ")");
                    up.setItemMeta(upItemMeta);
                    expShop.setItem(53, up);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                p.openInventory(expShop);
            } else if (inventoryClickEvent.getCurrentItem().getType() == Material.YELLOW_STAINED_GLASS_PANE) {

                p.closeInventory();

                int page = Integer.parseInt(String.valueOf(Objects.requireNonNull(expShop.getItem(45)).getItemMeta().getDisplayName().charAt(20)));


                expShop = Bukkit.createInventory(null, 54, "Experience Shop Page " + page);

                File expShopCsv = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "expShop" + File.separator + "expShop" + page + ".csv");

                try {
                    FileReader fileInputStream = new FileReader(expShopCsv);
                    BufferedReader br = new BufferedReader(fileInputStream);
                    for (int j = 0; j < 45; j++) {
                        String nextLine = br.readLine();
                        String[] current = nextLine.split(":");


                        if (nextLine.equals("1")) {
                            ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            expShop.setItem(j, itemStack);
                        } else if (!nextLine.equals("0")) {

                            ItemStack itemStack = new ItemStack(Material.valueOf(current[0]));


                            ItemMeta itemMeta;
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName("" + org.bukkit.ChatColor.GREEN + org.bukkit.ChatColor.BOLD + current[1]);


                            itemMeta.setLore(Arrays.asList(current[2].split(";")));
                            itemStack.setItemMeta(itemMeta);
                            expShop.setItem(j, itemStack);
                        }

                    }
                    br.close();


                    int pageUp = page + 1;
                    if (pageUp > 5) pageUp = 1;

                    int pageDown = page - 1;
                    if (pageDown < 1) pageDown = 5;


                    ItemStack back = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                    ItemMeta backItemMeta = back.getItemMeta();
                    backItemMeta.setDisplayName("" + org.bukkit.ChatColor.AQUA + org.bukkit.ChatColor.BOLD + "Назад (Страница " + pageDown + ")");
                    back.setItemMeta(backItemMeta);
                    expShop.setItem(45, back);

                    ItemStack up = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                    ItemMeta upItemMeta = back.getItemMeta();
                    upItemMeta.setDisplayName("" + org.bukkit.ChatColor.AQUA + org.bukkit.ChatColor.BOLD + "Вперед (Страница " + pageUp + ")");
                    up.setItemMeta(upItemMeta);
                    expShop.setItem(53, up);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.openInventory(expShop);
            }

        }

        if (inventoryClickEvent.getInventory() == NoolartPaperPlugin.chooseToBuild){
            inventoryClickEvent.setCancelled(true);
            Bukkit.broadcastMessage(Objects.requireNonNull(inventoryClickEvent.getCursor()).getType()+"");
            Bukkit.broadcastMessage(Objects.requireNonNull(inventoryClickEvent.getCurrentItem()).getType()+"");
            if (Objects.requireNonNull(inventoryClickEvent.getCurrentItem()).getType() == Material.DARK_OAK_FENCE){
                pasteRid(station, (Player) inventoryClickEvent.getWhoClicked());
            }
            else if (Objects.requireNonNull(inventoryClickEvent.getCurrentItem()).getType() == Material.IRON_BARS){
                pasteIronBar(station, (Player) inventoryClickEvent.getWhoClicked(), false);
            }
            else if (Objects.requireNonNull(inventoryClickEvent.getCurrentItem()).getType() == Material.STONE_BRICKS){
                pateStoneBricks(station, (Player) inventoryClickEvent.getWhoClicked());
            }
            else if (Objects.requireNonNull(inventoryClickEvent.getCurrentItem()).getType() == Material.CHEST){
                pasteIronBar(station, (Player) inventoryClickEvent.getWhoClicked(),true);
            }
        }

    }

    @EventHandler
    public void interact(BlockBreakEvent e2) throws FileNotFoundException {


        Block b = e2.getBlock();

        String path = NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "observers.csv";

        if (b.getType()==Material.COBBLESTONE){
            Player p = e2.getPlayer();
            int cobblesPickedUp = p.getStatistic(Statistic.MINE_BLOCK, Material.COBBLESTONE);

            int len = Integer.toString(cobblesPickedUp).length();
            int effectLevel = len - 2;
            if (cobblesPickedUp/(Math.pow(10,len-1))==1){
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,1000000,effectLevel-1));
                p.sendMessage("" + ChatColor.BOLD +ChatColor.GOLD+"Ты откопал " + ChatColor.RESET + ChatColor.GREEN + cobblesPickedUp + ChatColor.BOLD +ChatColor.GOLD+" блоков булыжника!"+"\n"+"Теперь на тебя наложен эффект "+ChatColor.RESET + ChatColor.BOLD + ChatColor.GREEN + "СПЕШКИ "+ChatColor.RESET + ChatColor.BOLD + ChatColor.AQUA +effectLevel +ChatColor.RESET + ChatColor.BOLD + ChatColor.GOLD+" уровня!" );
            }
        }

        if (b.getType() == Material.OBSERVER) {

            File file = new File(path);
            Scanner scan = new Scanner(file);

            StringBuilder text = new StringBuilder();


            while (scan.hasNextLine()) {
                String string1 = scan.nextLine();
                text.append(string1);
            }
            //System.out.println(text);
            scan.close();

            String[] symbols = text.toString().split(";");

            for (int i = 0; i < symbols.length; i = i + 3) {
                int x = Integer.parseInt(symbols[i]);
                int y = Integer.parseInt(symbols[i + 1]);
                int z = Integer.parseInt(symbols[i + 2]);

                if (b.getX() == x && b.getY() == y && b.getZ() == z) {
                    symbols[i] = "-1";
                    symbols[i + 1] = "-1";
                    symbols[i + 2] = "-1";
                    break;


                }
            }


            try (FileWriter writer = new FileWriter(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "\\observers.csv", false)) {


                for (int i1 = 0; i1 < symbols.length; i1 = i1 + 3) {

                    if (Integer.parseInt(symbols[i1]) != -1) {

                        writer.write(symbols[i1] + ";");
                        writer.write(symbols[i1 + 1] + ";");
                        writer.write(symbols[i1 + 2] + ";" + "\n");
                    }
                }


            } catch (IOException ex) {
                ex.printStackTrace();
            }

            MapView view = Bukkit.createMap(e2.getPlayer().getWorld());
            for (MapRenderer m : view.getRenderers()) {
                view.removeRenderer(m);
            }
            view.addRenderer(new Python());


        }

        if (b.getType() == Material.BASALT) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.GOLD_INGOT, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.IRON_BLOCK) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.CHARCOAL, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.ORANGE_TERRACOTTA) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.IRON_INGOT, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.PURPLE_TERRACOTTA) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.SCUTE, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.EMERALD_BLOCK) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.EMERALD, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.LIGHT_BLUE_TERRACOTTA) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.COAL, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.DIAMOND_ORE) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.DIAMOND, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.EMERALD_ORE) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.LAPIS_LAZULI, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.COAL_ORE) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.BOWL, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        if (b.getType() == Material.ANCIENT_DEBRIS) {
            Location location = b.getLocation();
            ItemStack itemStack = new ItemStack(Material.NETHERITE_SCRAP, 1);
            location.getWorld().dropItemNaturally(location, itemStack);
        }

        if (b.getType() == Material.LECTERN) {
            Collection<Entity> entities = b.getLocation().getNearbyEntities(1, 1, 1);
            for (Entity entity : entities) {
//                Bukkit.broadcastMessage(entity.getCustomName());
                if (Objects.equals(entity.getCustomName(), ChatColor.DARK_PURPLE + "Изучить")) {
                    entity.remove();
                }
            }
        }
    }

    @EventHandler
    public void interact(PlayerExpChangeEvent playerExpChangeEvent) {
        Player p = playerExpChangeEvent.getPlayer();
        p.sendMessage(Float.toString(p.getTotalExperience()));
        if (p.getTotalExperience() > 51 && p.getTotalExperience() < 58) {
            p.sendMessage("" + ChatColor.AQUA + "У тебя достаточно опыта для получения нового навыка! Приобрети его " + ChatColor.GREEN + ChatColor.BOLD + "/expShop");

        }

    }

    @EventHandler
    public void interact(EntityDamageByEntityEvent llamaEvent) {
        if (llamaEvent.getDamager().getType() == EntityType.PLAYER && llamaEvent.getEntity().getType() == EntityType.LLAMA) {
            Entity human = llamaEvent.getDamager();
            Entity llama = llamaEvent.getEntity();
            final int[] seconds = {25};
            float koef = 0.1f;
            Vector v = new Vector((human.getLocation().getX() - llama.getLocation().getX()) * koef, (human.getLocation().getY() - llama.getLocation().getY()) + 0.25, (human.getLocation().getZ() - llama.getLocation().getZ()) * koef);

            ((LivingEntity) llama).addPotionEffect(PotionEffectType.ABSORPTION.createEffect(1000000, 100));
            llama.setGravity(false);

            BukkitRunnable test = new BukkitRunnable() {
                @Override
                public void run() {

                    if (seconds[0] > 0) {

                        human.getWorld().spawn(human.getLocation(), LlamaSpit.class, llamaSpit -> {
                            Vector v1 = new Vector(human.getLocation().getX() - llama.getLocation().getX(), human.getLocation().getY() - llama.getLocation().getY() + 2, human.getLocation().getZ() - llama.getLocation().getZ());
                            llamaSpit.setVelocity(v1);


//                                Vector v1 = new Vector(human.getLocation().getX() - llama.getLocation().getX(), human.getLocation().getY() - llama.getLocation().getY() + 2, human.getLocation().getZ() - llama.getLocation().getZ());
                            human.setVelocity(v);
                            llama.setVelocity(new Vector(0, human.getLocation().getY() - llama.getLocation().getY(), 0));
                            llama.getFacing().getDirection().setX(v1.getX());
                            llama.getFacing().getDirection().setY(v1.getY());
                            llama.getFacing().getDirection().setZ(v1.getZ());
                            ((LivingEntity) human).damage(2);
                        });
                        seconds[0]--;
                    } else {
                        cancel();
                    }
                }

            };
            test.runTaskTimer(NoolartPaperPlugin.plugin, 5, 4);

        }
    }

    @EventHandler
    public void interact (PlayerInteractEntityEvent playerInteractEntityEvent){

        //Bukkit.broadcastMessage(playerInteractEntityEvent.getRightClicked().getCustomName());

        if ( Objects.requireNonNull(playerInteractEntityEvent.getRightClicked().getCustomName()).equals("" + ChatColor.AQUA + ChatColor.BOLD + "Играть")){
            playerInteractEntityEvent.getPlayer().teleport(new Location (playerInteractEntityEvent.getPlayer().getWorld(),896.5, 94, 1046));
        }

        if ( Objects.requireNonNull(playerInteractEntityEvent.getRightClicked().getCustomName()).equals("" + ChatColor.AQUA + ChatColor.BOLD + "Уровень 2")){
            playerInteractEntityEvent.getPlayer().teleport(new Location (playerInteractEntityEvent.getPlayer().getWorld(),790, 122, 1206));
        }

        if ( Objects.requireNonNull(playerInteractEntityEvent.getRightClicked().getCustomName()).equals("" + ChatColor.AQUA + ChatColor.BOLD + "Вулкан")){
            playerInteractEntityEvent.getPlayer().teleport(new Location (playerInteractEntityEvent.getPlayer().getWorld(),1000195.5, 20, 2.5));
        }


    }

    public void pasteRid (Block b, Player p){


        NoolartPaperPlugin.point1 = new Location(p.getWorld(), b.getX() - 2, b.getY(), b.getZ() - 2);

        PasteCsv.paste("OilRid", p);
        double depth = b.getLocation().getY() + 63;


        for (int i = 0; i < depth; i++) {


            if (i == depth - 1) {
                PasteCsv.paste("OilRidBottom1", p);

            } else if (i % 10 == 0) {
                PasteCsv.paste("OilRidBottom2", p);

            } else if (i % 5 == 0 || i % 6 == 0) {
                PasteCsv.paste("OilRidBottom3", p);
            } else {
                PasteCsv.paste("OilRidBottom", p);
            }
        }

        int blocks = p.getStatistic(Statistic.USE_ITEM, Material.DARK_OAK_FENCE) + 1;
//                Bukkit.broadcastMessage("" + blocks);
        int effectLevel = blocks / 5 * 300;
        if (blocks > 0 && blocks % 5 == 0) {
            Pay.giveMoneyToPlayer(effectLevel, p);
            p.sendMessage("" + ChatColor.BOLD + ChatColor.GOLD + "Ты установил " + ChatColor.RESET + ChatColor.GREEN + blocks + ChatColor.BOLD + ChatColor.GOLD + " шахт!" + "\n" + "Ты получаешь " + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD + effectLevel + "$");
        }

        //}
        // }, 5*20);
    }










    public void pasteIronBar (Block b, Player p, boolean needKern){

        File solidity = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "res" + File.separator + "Density.csv");
        PrintWriter w1 = null;
        try {
            w1 = new PrintWriter(solidity);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert w1 != null;
        w1.print("");
        w1.close();
        File magnetic = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "res" + File.separator + "Magnetic.csv");
        PrintWriter w2 = null;
        try {
            w2 = new PrintWriter(magnetic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert w2 != null;
        w2.print("");
        w2.close();
        File resistivity = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "res" + File.separator + "Res.csv");
        PrintWriter w3 = null;
        try {
            w3 = new PrintWriter(resistivity);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert w3 != null;
        w3.print("");
        w3.close();
        File vpspeed = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "res" + File.separator + "Vp.csv");
        PrintWriter w4 = null;
        try {
            w4 = new PrintWriter(vpspeed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert w4 != null;
        w4.print("");
        w4.close();
        File vsspeed = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "res" + File.separator + "Vs.csv");
        PrintWriter w5 = null;
        try {
            w5 = new PrintWriter(vsspeed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert w5 != null;
        w5.print("");
        w5.close();
        File rad = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "res" + File.separator + "Rad.csv");
        PrintWriter w6 = null;
        try {
            w6 = new PrintWriter(rad);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert w6 != null;
        w6.print("");
        w6.close();



        PasteCsv.pasteQuiet("budka", p);

            List<ItemStack> kern = new ArrayList<>();
            World w = p.getWorld();


            double depth = b.getLocation().getY() - 1;
            Location loc = new Location(p.getWorld(), b.getX(), 1, b.getZ());

            for (int i = 0; i < depth - 1; i++) {
                try (FileWriter writer = new FileWriter(solidity.getPath(), true)) {
                    writer.write(Materials.getLong(loc.getBlock().getType().toString().toLowerCase(), "Density") + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter(magnetic.getPath(), true)) {
                    writer.write(Materials.getLong(loc.getBlock().getType().toString().toLowerCase(), "Magnetic") + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter(resistivity.getPath(), true)) {
                    writer.write(Materials.getLong(loc.getBlock().getType().toString().toLowerCase(), "Res") + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter(vsspeed.getPath(), true)) {
                    writer.write(Materials.getLong(loc.getBlock().getType().toString().toLowerCase(), "Vs") + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter(vpspeed.getPath(), true)) {
                    writer.write(Materials.getLong(loc.getBlock().getType().toString().toLowerCase(), "Vp") + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writer = new FileWriter(rad.getPath(), true)) {
                    writer.write(Materials.getLong(loc.getBlock().getType().toString().toLowerCase(), "Rad") + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (loc.getBlock().getType() != Material.CAVE_AIR && loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.BEDROCK && loc.getBlock().getType() != Material.LAVA && loc.getBlock().getType() != Material.WATER) {
//                    if (loc.getBlock().getType()==)
                    kern.add(new ItemStack(loc.getBlock().getType()));
                }
                loc.add(0, 1, 0);
                //Bukkit.broadcastMessage(Double.toString(loc.getX())+" "+ Double.toString(loc.getY())+" "+Double.toString(loc.getZ())+Materials);
            }


            if (needKern) {

                b.getLocation().add(0, 1, -4).getBlock().setType(Material.CHEST);

                Chest chest = (Chest) b.getLocation().add(0, 1, -4).getBlock().getState();
                int currentSlot = 0;
                kern = Lists.reverse(kern);

                for (ItemStack item : kern) {
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.YELLOW + Materials.getString(item.getType().toString().toLowerCase(), "name"));
                    item.setItemMeta(itemMeta);

                    if (currentSlot > 0 && Objects.requireNonNull(chest.getInventory().getItem(currentSlot - 1)).getType() == item.getType()) {
                        int amount = Objects.requireNonNull(chest.getInventory().getItem(currentSlot - 1)).getAmount() + 1;
                        item.setAmount(amount);
                        chest.getInventory().setItem(currentSlot - 1, item);
                    } else {
                        chest.getInventory().setItem(currentSlot, item);
                        currentSlot++;
                    }
                }
            }



        NoolartPaperPlugin.point1 = new Location(w, b.getLocation().getX(), b.getLocation().getY() + 2, b.getLocation().getZ());

        for (int i = (int) b.getLocation().getY() + 2; i > 0; i--) {
            NoolartPaperPlugin.point1.getBlock().setType(Material.END_ROD);
            NoolartPaperPlugin.point1.setY(NoolartPaperPlugin.point1.getY() - 1);
        }

        NoolartPaperPlugin.point1 = b.getLocation().clone().add(-5, -1, -5);



        Commands.pythonrun("materials", Integer.toString(baseID));
        Commands.pythonrun("img", Integer.toString(baseID));

        int x = -4;
        int y = 3;
        int z = 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(new Location(w, b.getX() + x, b.getY() + y, b.getZ() + z), ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(BlockFace.EAST, false);
                    itemFrame.setItem(mapToStack(baseID + "Density" + "-" + (finalI) + "-" + finalJ + ".jpg", p.getWorld()));
                });
                z--;
            }
            y--;
            z = 3;
        }

        y = 3;
        z = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(new Location(w, b.getX() + x, b.getY() + y, b.getZ() + z), ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(BlockFace.EAST, false);
                    itemFrame.setItem(mapToStack(baseID + "Magnetic" + "-" + (finalI) + "-" + finalJ + ".jpg", p.getWorld()));
//                        Bukkit.broadcastMessage("magnetic"+"-"+(finalI)+"-"+ finalJ+".png");
                });
                z--;
            }
            y--;
            z = -1;
        }

        x = -3;
        y = 3;
        z = -4;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(new Location(w, b.getX() + x, b.getY() + y, b.getZ() + z), ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(BlockFace.SOUTH, false);
                    itemFrame.setItem(mapToStack(baseID + "Res" + "-" + (finalI) + "-" + finalJ + ".jpg", p.getWorld()));
                });
                x++;
            }
            y--;
            x = -3;
        }
        x = 1;
        y = 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(new Location(w, b.getX() + x, b.getY() + y, b.getZ() + z), ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(BlockFace.SOUTH, false);
                    itemFrame.setItem(mapToStack(baseID + "Vs" + "-" + (finalI) + "-" + finalJ + ".jpg", p.getWorld()));
                });
                x++;
            }
            y--;
            x = 1;
        }
        x = 4;
        y = 3;
        z = -3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(new Location(w, b.getX() + x, b.getY() + y, b.getZ() + z), ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(BlockFace.WEST, false);
                    itemFrame.setItem(mapToStack(baseID + "Vp" + "-" + (finalI) + "-" + finalJ + ".jpg", p.getWorld()));
                });
                z++;
            }
            y--;
            z = -3;
        }

        y = 3;
        z = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i;
                int finalJ = j;
                w.spawn(new Location(w, b.getX() + x, b.getY() + y, b.getZ() + z), ItemFrame.class, itemFrame -> {
                    itemFrame.setFacingDirection(BlockFace.WEST, false);
                    itemFrame.setItem(mapToStack(baseID + "Rad" + "-" + (finalI) + "-" + finalJ + ".jpg", p.getWorld()));
                });
                z++;
            }
            y--;
            z = 1;
        }

        baseID++;

    }
    public void pateStoneBricks (Block b, Player p){
        NoolartPaperPlugin.point1 = b.getLocation().clone();
        PasteCsv.paste("microscope", p);
        World w = p.getWorld();
        w.spawn(b.getLocation().add(0, 1, 0), ItemFrame.class, itemFrame -> itemFrame.setFacingDirection(BlockFace.NORTH, false));
        w.spawn(b.getLocation().add(0, 1, 2), ItemFrame.class, itemFrame -> itemFrame.setFacingDirection(BlockFace.UP));
    }


}
