package breaker;

import org.bukkit.plugin.java.JavaPlugin;

public class Breaker extends JavaPlugin {
    private static Breaker plugin;

    public Breaker() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BreakerListener(), this);
    }
    
    public static JavaPlugin getInstance() {
    	return plugin;
    }
}

