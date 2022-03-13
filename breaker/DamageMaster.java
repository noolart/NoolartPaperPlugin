package breaker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.*;

import com.noolart.noolartpaperplugin.Materials;

public class DamageMaster{
	public DamageMaster() {
        File file = new File(Breaker.getInstance().getDataFolder() + File.separator + "instruments.yml");
        if (file.exists()) {
        	try {
        		instruments = YamlConfiguration.loadConfiguration(file);
        	}
        	catch(IllegalArgumentException exception) {
        		System.err.println("Bad file: " + file + ":\n" + exception.getMessage());
        		instruments = null;
        	}
        }
	}
	
	public void setDefaultMode(Player player) {
		if(damages.containsKey(player.getName())) {
			damages.get(player.getName()).setLocation(null);
		}
	}
	
	public void initDamage(Player player, Block block, ItemStack item) {
		DamageData damageData;
		float damageSpeed = getDamageSpeed(Materials.getLong(block.getType().toString().toLowerCase(), "Density"));
		float damageCoefficient = getDamageCoefficient(item);
   		Location newLocation = block.getLocation();
		if(!damages.containsKey(player.getName())) {
	    	damageData = new DamageData(newLocation, damageSpeed, damageCoefficient);
		   	damages.put(player.getName(), damageData);
		}
	    else {
	    	damageData = damages.get(player.getName());
	   		newLocation = block.getLocation();
	   		Location oldLocation = damageData.getLocation();
	   		if(oldLocation == null || !sameLocations(oldLocation, newLocation)) {
	   			damageData.setLocation(newLocation);
		   		damageData.setDamageSpeed(damageSpeed);
	    		damageData.clearDamage();
	    	}
	    	damageData.setDamageCoefficient(damageCoefficient);
	    }
	}
	
	public void increaseDamage(Player player){
		if(damages.containsKey(player.getName())) {
			DamageData damageData = damages.get(player.getName());
			Location location = damageData.getLocation();
			Block block = player.getTargetBlock(DAMAGE_LENGTH);
   			if(location != null) {
   				String locationName = location.toString();
   				synchronized(locationName) {
		    		if(!location.getBlock().getBlockData().getMaterial().equals(Material.AIR) && 
		    				sameLocations(location, block.getLocation())) {
				    	if(damageData.getDamage() == FULL_DAMAGE) {
				    		location.getBlock().breakNaturally();
			    			damageData.setLocation(null);
			    			return;
				   		}
				   		location.getBlock().setType(block.getBlockData().getMaterial());
				   		player.sendBlockDamage(location, damageData.getDamage());
				   		damageData.increaseDamage(getExtraDamageCoefficient(player, location));
		   			}
   				}
   			}
   		}
    }
    
    private boolean sameLocations(Location locationA, Location locationB) {
    	if(locationA == null || locationB == null) {
    		return locationA == locationB;
    	}
    	return locationA.getX() == locationB.getX() && 
    			locationA.getY() == locationB.getY() && 
    			locationA.getZ() == locationB.getZ();
    }
    
    private float getDamageSpeed(long density) {
    	return (MAX_DENSITY - density) / MAX_DENSITY / DAMAGE_SPEED_COEFFICIENT;
    }
    
    private float getDamageCoefficient(ItemStack item) {
    	if(instruments == null) {
    		return DEFAULT_DAMAGE_COEFFICIENT;
    	}
        Double damageCoefficient = instruments.getDouble(
        		item.getType().toString().toLowerCase() + ".damageCoefficient", Double.NaN);
        if (Double.isNaN(damageCoefficient)) {
        	return DEFAULT_DAMAGE_COEFFICIENT;
        }
        return damageCoefficient.floatValue();
    }
    
    private float getExtraDamageCoefficient(Player player, Location location) {
    	Location playerLocation = player.getLocation();
    	if(location.getY() + 1.0f <= playerLocation.getY()) {
    		return MAX_EXTRA_DAMAGE_COEFFICIENT;
    	}
    	else if(location.getY() >= playerLocation.getY() + 1.0f) {
    		return MIN_EXTRA_DAMAGE_COEFFICIENT;
    	}
    	else {
    		return DEFAULT_DAMAGE_COEFFICIENT;
    	}
    }
    
    private float FULL_DAMAGE = 1.0f;
    private int DAMAGE_LENGTH = 5;
    private float MAX_DENSITY = 19300.0f;
    private int DAMAGE_SPEED_COEFFICIENT = 10;
    private float DEFAULT_DAMAGE_COEFFICIENT = 1.0F;
    private float MAX_EXTRA_DAMAGE_COEFFICIENT = 1.5F;
    private float MIN_EXTRA_DAMAGE_COEFFICIENT = 0.5F;
    
    private FileConfiguration instruments;
    private Map<String, DamageData> damages = new ConcurrentHashMap<String, DamageData>();
}
