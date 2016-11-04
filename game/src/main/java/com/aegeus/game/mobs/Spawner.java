package com.aegeus.game.mobs;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class Spawner {
	private static Map<Location, Spawner> map = new HashMap<>();

	private Location location;
	private Mob mob;
	private int maxInRadius;
	private float chanceToSpawn;
	private int updateTick = 0;
	private int updateDelay;

	private Spawner(Location location, Mob mob, int maxInRadius, float chanceToSpawn, int updateDelay) {
		this.location = location;
		this.mob = mob;
		this.maxInRadius = maxInRadius;
		this.chanceToSpawn = chanceToSpawn;
		this.updateDelay = updateDelay;
	}

	public static Spawner[] getAll() { return map.values().toArray(new Spawner[map.size()]); }
	public static Spawner get(Location location) {
		return (map.containsKey(location)) ? map.get(location) : null;
	}
	public static Spawner create(Location location, Mob mob, int maxInRadius, float chanceToSpawn, int updateDelay) {
		Spawner spawner = new Spawner(location, mob, maxInRadius, chanceToSpawn, updateDelay);
		map.put(location, spawner);
		return spawner;
	}

	public Location getLocation() { return location; }
	public Mob getMob() { return mob; }
	public void setMob(Mob mob) { this.mob = mob; }
	public int getMaxInRadius() { return maxInRadius; }
	public void setMaxInRadius(int maxInRadius) { this.maxInRadius = maxInRadius; }
	public boolean canSpawn() {
		return (location.getWorld().getNearbyEntities(location, 25, 25, 25).size() < maxInRadius);
	}
	public float getChanceToSpawn() { return chanceToSpawn; }
	public void setChanceToSpawn(float chanceToSpawn) { this.chanceToSpawn = chanceToSpawn; }
	public int addUpdateTick() { this.updateTick += 1; return updateTick; }
	public int getUpdateTick() { return updateTick; }
	public void setUpdateTick(int updateTick) { this.updateTick = updateTick; }
	public int getUpdateDelay() { return updateDelay; }
	public void setUpdateDelay(int updateDelay) { this.updateDelay = updateDelay; }
}