package breaker;

import java.io.File;

import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Breaker extends JavaPlugin {
    @Override
    public void onEnable() {
    	FileConfiguration instruments = null, materials = null;
    	materials = getConfiguration("materials.yml");
    	if(materials == null) {
    		return;
    	}
    	instruments = getConfiguration("instruments.yml");
        getServer().getPluginManager().registerEvents(new BreakerListener(instruments, materials), this);
    }
    
    private FileConfiguration getConfiguration(String fileName) {
    	File file = new File(this.getDataFolder() + File.separator + fileName);
        if (file.exists()) {
        	try {
        		return YamlConfiguration.loadConfiguration(file);
        	}
        	catch(IllegalArgumentException exception) {
        		System.err.println("Can't get configuration of  " + file + ": " + exception.getMessage());
        		return null;
        	}
        }
        else {
        	return null;
        }
    }
}

