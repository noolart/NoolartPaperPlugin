package breaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.noolart.noolartpaperplugin.Materials;

public class BreakerListener implements Listener {
	public BreakerListener() {
		garbagePlayersCollector = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(GARBAGE_SLEEP_TIME_MILLIS);
				}
				catch(InterruptedException e) {
					synchronized(this) {
						System.err.println("Garbage players collector can't sleep: " + e.getMessage());
						garbageCollectorDoesntWork = true;
						damageMaster.clear();
						players.clear();
						break;
					}
				}
				synchronized(this) {
					players.forEach((String playerName, Boolean playerGeneration)->{
						if(!playerGeneration.equals(currentPlayersGeneration)) {
							damageMaster.removePlayer(playerName);
							players.remove(playerName);
						}
					});
					if(players.size() == 0) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							System.err.println("BreakerListener can't stop garbagePlayersCollector: " + e.getMessage());
							garbageCollectorDoesntWork = true;
							damageMaster.clear();
							players.clear();
							break;
						}
					}
				}
				currentPlayersGeneration = !currentPlayersGeneration;
			}
		});
		garbagePlayersCollector.start();
	}
	
	@EventHandler
    public void interact(BlockDamageEvent event){
		synchronized(this) {
			if(garbageCollectorDoesntWork) {
				return;
			}
			if(players.size() == 0) {
				this.notify();
			}
		}
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if(Materials.getKeys(block.getType().toString().toLowerCase()).size() != 0) {
	    	if(damageMaster.initDamage(player, block, event.getItemInHand())) {
		    	event.setCancelled(true);
	    	};
		}
		else {
			damageMaster.setDefaultMode(player);
		}
		updatePlayers(player.getName());
    }
    
    @EventHandler
    public void interact(PlayerAnimationEvent event){
		synchronized(this) {
			if(garbageCollectorDoesntWork) {
				return;
			}
		}
    	PlayerAnimationType animation = event.getAnimationType();
    	if(animation.equals(PlayerAnimationType.ARM_SWING)) {
	    	damageMaster.increaseDamage(event.getPlayer());
    	}
    }
    
    private void updatePlayers(String playerName) {
    		players.put(playerName, currentPlayersGeneration);
    }
    
    private long GARBAGE_SLEEP_TIME_MILLIS = 100000;
    
    private DamageMaster damageMaster = new DamageMaster();
    private Map<String, Boolean> players = new ConcurrentHashMap<String, Boolean>();
    private boolean currentPlayersGeneration;
    private boolean garbageCollectorDoesntWork = false;
    private Thread garbagePlayersCollector;
}