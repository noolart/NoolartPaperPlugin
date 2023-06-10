package com.noolart.noolartpaperplugin;

import com.noolart.noolartpaperplugin.commands.*;
import com.noolart.noolartpaperplugin.csv.CopyCsv;
import com.noolart.noolartpaperplugin.csv.GenerationFromCsv;
import com.noolart.noolartpaperplugin.csv.PasteCsv;
import com.noolart.noolartpaperplugin.csv.PasteCsvHidden;
import com.noolart.noolartpaperplugin.levels.Level1;
import com.noolart.noolartpaperplugin.listener.MyListener;
import com.noolart.noolartpaperplugin.listener.MyListener1;
import com.noolart.noolartpaperplugin.listener.MyListener2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public class NoolartPaperPlugin extends JavaPlugin {
    public static NoolartPaperPlugin plugin;

    public static Location point1;
    public static Location point2;
    public static HashMap<String, HashMap<String, String>> finds = new HashMap<>();
    public static HashMap<String, Integer> shopPrices = new HashMap<>();
    public static Inventory shop, chooseToBuild;
    public static Inventory expShop;
    public static File readCommand;


    public NoolartPaperPlugin() {
        plugin = this;
    }

    @Override
    public void onEnable() {



        Materials.init();
        //Craft.init();

//        for (Object o : Craft.getAllValues().keySet()){
//            Bukkit.broadcastMessage(o.toString());
//        }


        //***************************************************

        //CUSTOM CRAFTS



        readCommand = new File (plugin.getDataFolder().getAbsolutePath() + File.separator + "readCommand.txt");


        ItemStack iron_bars = new ItemStack(Material.IRON_BARS,1);
        ShapedRecipe srIronBars = new ShapedRecipe(iron_bars);

        srIronBars.shape("B*B","%*%","%B%");

        srIronBars.setIngredient('B', Material.AIR);
        srIronBars.setIngredient('%', Material.STICK);
        srIronBars.setIngredient('*', Material.IRON_INGOT);

        getServer().addRecipe(srIronBars);


        ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 1);
        ShapedRecipe srCobblestone = new ShapedRecipe(cobblestone);

        srCobblestone.shape("BB ","BB ","   ");
        srCobblestone.setIngredient('B',Material.CLAY);
        getServer().addRecipe(srCobblestone);

        srCobblestone = new ShapedRecipe(cobblestone);

        srCobblestone.shape("   ","BB ","BB ");
        srCobblestone.setIngredient('B',Material.CLAY);
        getServer().addRecipe(srCobblestone);

        srCobblestone = new ShapedRecipe(cobblestone);

        srCobblestone.shape(" BB"," BB","   ");
        srCobblestone.setIngredient('B',Material.CLAY);
        getServer().addRecipe(srCobblestone);

        srCobblestone = new ShapedRecipe(cobblestone);

        srCobblestone.shape("   "," BB"," BB");
        srCobblestone.setIngredient('B',Material.CLAY);
        getServer().addRecipe(srCobblestone);






        ItemStack iron_nugget = new ItemStack(Material.IRON_NUGGET, 1);
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(iron_nugget, Material.CLAY);
        furnaceRecipe.setExperience(10);
        getServer().addRecipe(furnaceRecipe);

        //***************************************************

        chooseToBuild = Bukkit.createInventory(null, 9,"" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + "Что строим?");

        expShop = Bukkit.createInventory(null, 54, "Experience Shop Page 1");

        File expShopCsv = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "expShop" + File.separator + "expShop1.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(expShopCsv))) {
            for (int j = 0; j < 45; j++) {
                String nextLine = br.readLine();
                String[] current = nextLine.split(":");

                if (nextLine.equals("1")) {
                    ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                    expShop.setItem(j, itemStack);
                } else if (!nextLine.equals("0")) {
                    ItemStack itemStack = new ItemStack(Material.valueOf(current[0]));

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + current[1]);


                    itemMeta.setLore(Arrays.asList(current[2].split(";")));
                    itemStack.setItemMeta(itemMeta);
                    expShop.setItem(j, itemStack);
                }
            }

            ItemStack back = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            ItemMeta backItemMeta = back.getItemMeta();
            backItemMeta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "Назад (Страница 5)");
            back.setItemMeta(backItemMeta);
            expShop.setItem(45, back);

            ItemStack up = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
            ItemMeta upItemMeta = back.getItemMeta();
            upItemMeta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "Вперед (Страница 2)");
            up.setItemMeta(upItemMeta);
            expShop.setItem(53, up);
        } catch (IOException e) {
            e.printStackTrace();
        }



        shop = Bukkit.createInventory(null, 54, "Shop");
        File shopFile = new File(NoolartPaperPlugin.plugin.getDataFolder() + File.separator + "shop.csv");
        int i = 0;

        try(Scanner scan = new Scanner(shopFile)) {
            while (scan.hasNextLine() && i < 54) {
                String[] current = scan.nextLine().split(":");

                ItemStack itemStack = new ItemStack((Material.valueOf((current[0]).toUpperCase())));
                if(itemStack.getType() != Material.RED_STAINED_GLASS_PANE) {
                    shopPrices.put(current[0].toUpperCase(), Integer.parseInt(current[1]));
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.YELLOW + Materials.getString(itemStack.getType().toString().toLowerCase(), "name"));
                    itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "ПКМ " + ChatColor.WHITE + "Купить:" + current[1] + "$", ChatColor.RED + "ЛКМ " + ChatColor.WHITE + "Продать:" + (int) (Integer.parseInt(current[1]) * 0.8) + "$"));
                    if (i==53){
                        itemMeta.addEnchant(Enchantment.MENDING,1,false);
                    }
                    itemStack.setItemMeta(itemMeta);
                }
                shop.setItem(i, itemStack);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new MyListener(), this);
        getServer().getPluginManager().registerEvents(new MyListener1(), this);
        getServer().getPluginManager().registerEvents(new MyListener2(), this);
        getServer().getPluginManager().registerEvents(new Level1(), this);

        Objects.requireNonNull(getCommand("pythonrun")).setExecutor(new Commands(this));
        Objects.requireNonNull(getCommand("copyBin")).setExecutor(new Commands1(this));
        Objects.requireNonNull(getCommand("pasteBin")).setExecutor(new Commands2(this));
        Objects.requireNonNull(getCommand("copyCsvOld")).setExecutor(new Commands3(this));
        Objects.requireNonNull(getCommand("pasteCsvOld")).setExecutor(new Commands4(this));
        Objects.requireNonNull(getCommand("copyCsv")).setExecutor(new CopyCsv(this));
        Objects.requireNonNull(getCommand("pasteCsv")).setExecutor(new PasteCsv(this));
        Objects.requireNonNull(getCommand("pasteCsvHidden")).setExecutor(new PasteCsvHidden(this));
        Objects.requireNonNull(getCommand("randomGeneration")).setExecutor(new RandomGeneration(this));
        Objects.requireNonNull(getCommand("randomGenerationUseCommand")).setExecutor(new RandomGenerationUseCommand(this));
        Objects.requireNonNull(getCommand("modelFromImage")).setExecutor(new ModelFromImage(this));
        Objects.requireNonNull(getCommand("mapFromImage")).setExecutor(new MapFromImage(this));
        Objects.requireNonNull(getCommand("generationFromCsv")).setExecutor(new GenerationFromCsv(this));
        Objects.requireNonNull(getCommand("shop")).setExecutor(new Shop(this));
        Objects.requireNonNull(getCommand("expShop")).setExecutor(new ExpShop(this));
        Objects.requireNonNull(getCommand("balance")).setExecutor(new Balance(this));
        Objects.requireNonNull(getCommand("pay")).setExecutor(new Pay(this));
        Objects.requireNonNull(getCommand("chest")).setExecutor(new ChestHack(this));
        Objects.requireNonNull(getCommand("perlingeneration")).setExecutor(new PerlinGeneration(this));
        Objects.requireNonNull(getCommand("slice")).setExecutor(new Slice(this));
        Objects.requireNonNull(getCommand("electrical")).setExecutor(new ElectricalProspecting(this));
        Objects.requireNonNull(getCommand("image")).setExecutor(new Image(this));
        Objects.requireNonNull(getCommand("video")).setExecutor(new Video(this));
        Objects.requireNonNull(getCommand("diagonal")).setExecutor(new Diagonal(this));
        Objects.requireNonNull(getCommand("text")).setExecutor(new Text(this));
        Objects.requireNonNull(getCommand("textRemove")).setExecutor(new TextRemove(this));




        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Block b = p.getTargetBlock(20);

                if (b == null || Materials.getKeys(b.getType().toString().toLowerCase()).size() == 0) {
                    p.sendActionBar(" ");
                    continue;
                }

                StringBuilder res = new StringBuilder(ChatColor.AQUA + Materials.getString(b.getType().toString().toLowerCase(), "name") + ":" + ChatColor.WHITE);
                for (String key : Materials.getKeys(b.getType().toString().toLowerCase())) {
                    if (key.equals("name")) continue;
                    if (key.equals("finded")) continue;
//                    Bukkit.broadcastMessage(b.getType().toString().toLowerCase()+":"+hashMap.get(b.getType().toString().toLowerCase()));
                    //if (finds.get(p.getName()).get(b.getType().toString().toLowerCase()).equals("true")) {
                        res.append(" ").append(key).append("=").append(Materials.getString(b.getType().toString().toLowerCase(), key));
                    //}
                }
                p.sendActionBar();
                p.sendActionBar(res.toString());
            }



        }, 5, 5);


    }


    @Override
    public void onDisable() {

    }

    private void executeCommand (Player p, String cmd){
        getServer().dispatchCommand(p, cmd);
    }

}
