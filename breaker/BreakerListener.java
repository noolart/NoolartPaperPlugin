package breaker;

import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.block.Block;

import com.noolart.noolartpaperplugin.Materials;

public class BreakerListener implements Listener {
	@EventHandler
    public void interact(BlockDamageEvent event){
		Block block = event.getBlock();
		if(Materials.getKeys(block.getType().toString().toLowerCase()).size() != 0) {
	    	event.setCancelled(true);
	    	synchronized(damageMaster) {
	    		damageMaster.initDamage(event.getPlayer(), block, event.getItemInHand());
	    	}
		}
    }
    
    @EventHandler
    public void interact(PlayerAnimationEvent event){
    	PlayerAnimationType animation = event.getAnimationType();
    	if(animation.equals(PlayerAnimationType.ARM_SWING)) {
	    	synchronized(damageMaster) {
	    		damageMaster.increaseDamage(event.getPlayer());
	    	}
    	}
    }
    
    private DamageMaster damageMaster = new DamageMaster();
}