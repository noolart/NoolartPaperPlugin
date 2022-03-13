package breaker;

import org.bukkit.*;

public class DamageData{
	DamageData(Location location, float damageSpeed, float damageCoefficient){
		this.location = location;
		this.damageSpeed = damageSpeed;
		this.damageCoefficient = damageCoefficient;
		Thread thread = new Thread(()->{
			while(true) {
				synchronized(this) {
					System.out.println("THREAD");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	public Location getLocation(){
		return location;
	}
	
	public float getDamage(){
		return damage;
	}
	
	public void setLocation(Location location){
		synchronized(this) {
			this.location = location;
		}
	}
	
	public void clearDamage(){
		this.damage = NO_DAMAGE;
	}
	
	public void setDamageSpeed(float damageSpeed) {
		if(damageSpeed < NO_DAMAGE) {
			damageSpeed *= -1;
		}
		this.damageSpeed = damageSpeed;
	}
	
	public void increaseDamage(float extraDamageCoefficient) {
		if(damage == FULL_DAMAGE) {
			return;
		}
		damage += damageSpeed * damageCoefficient * extraDamageCoefficient;
		if(damage > FULL_DAMAGE) {
			damage = FULL_DAMAGE;
		}
	}
	
	public void setDamageCoefficient(float damageCoefficient) {
		if(damageCoefficient <= NO_DAMAGE) {
			damageCoefficient = DEFAULT_DAMAGE_COEFFICIENT;
		}
		this.damageCoefficient = damageCoefficient;
	}

	private float NO_DAMAGE = 0.0f;
	private float FULL_DAMAGE = 1.0f;
	private float DEFAULT_DAMAGE_COEFFICIENT = 1.0f;
	
	private Location location;
	private float damage = NO_DAMAGE;
	private float damageSpeed;
	private float damageCoefficient;
}
