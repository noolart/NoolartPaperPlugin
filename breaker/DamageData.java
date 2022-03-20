package breaker;

import org.bukkit.*;

public class DamageData{
	DamageData(Location location, float damageSpeed, float damageCoefficient){
		this.location = location;
		if(damageSpeed <= NO_DAMAGE) {
			damageSpeed = DEFAULT_DAMAGE_SPEED;
		}
		this.damageSpeed = damageSpeed;
		if(damageCoefficient <= NO_DAMAGE) {
			damageCoefficient = DEFAULT_DAMAGE_COEFFICIENT;
		}
		this.damageCoefficient = damageCoefficient;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public float getDamage(){
		return damage;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public void clearDamage(){
		this.damage = NO_DAMAGE;
	}
	
	public void setDamageSpeed(float damageSpeed){
		if(damageSpeed <= NO_DAMAGE) {
			damageSpeed = DEFAULT_DAMAGE_SPEED;
		}
		this.damageSpeed = damageSpeed;
	}
	
	public void increaseDamage(float extraDamageCoefficient) {
		if(damage == FULL_DAMAGE) {
			return;
		}
		if(extraDamageCoefficient <= NO_DAMAGE) {
			extraDamageCoefficient = DEFAULT_DAMAGE_COEFFICIENT;
		}
		damage += damageSpeed * damageCoefficient * extraDamageCoefficient;
		if(damage > FULL_DAMAGE) {
			damage = FULL_DAMAGE;
		}
	}
	
	public void setDamageCoefficient(float damageCoefficient){
		if(damageCoefficient <= NO_DAMAGE) {
			damageCoefficient = DEFAULT_DAMAGE_COEFFICIENT;
		}
		this.damageCoefficient = damageCoefficient;
	}

	private float NO_DAMAGE = 0.0f;
	private float FULL_DAMAGE = 1.0f;
	private float DEFAULT_DAMAGE_COEFFICIENT = 1.0f;
	private float DEFAULT_DAMAGE_SPEED = 0.05F;
	
	private Location location;
	private float damage = NO_DAMAGE;
	private float damageSpeed;
	private float damageCoefficient;
}
