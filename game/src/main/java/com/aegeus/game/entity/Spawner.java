package com.aegeus.game.entity;

import com.aegeus.game.stats.impl.Mob;
import com.aegeus.game.stats.impl.Stats;
import com.aegeus.game.util.Util;
import org.bukkit.Difficulty;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spawner {
	private final Location location;
	private int maxCount = 3;
	private int count;
	private transient int currentDelay = 0;
	private int delayCount = 1;
	private List<Mob> mobs = new ArrayList<>();

	public Spawner(Location location) {
		this.location = location;
	}

	/**
	 * Get the location of the spawner
	 *
	 * @return Location of the spawner
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Get the list of mobs this spawner can spawn
	 *
	 * @return List of Stats
	 */
	public List<Mob> getMobs() {
		return mobs;
	}

	/**
	 * Overwrite the mobs this spawner can spawn.
	 *
	 * @param mobs of Stats this spawner will spawn
	 */
	public void setMobs(List<Mob> mobs) {
		this.mobs = mobs;
	}

	/**
	 * Set the types of mobs this spawner can spawn after clearing the original list
	 */
	public void set(Mob... mobs) {
		this.mobs = new ArrayList<>(Arrays.asList(mobs));
	}

	/**
	 * Add another type of mob that can be spawned by this spawner.
	 */
	public void add(Mob... mobs) {
		this.mobs.addAll(Arrays.asList(mobs));
	}

	/**
	 * Get a random Stats from the list of spawnable mobs from this spawner.
	 */
	public Stats get() {
		return mobs.get(Util.rInt(mobs.size()));
	}

	/**
	 * Conditions to spawn a mob are:
	 * The max amount of mobs spawned is not reached yet
	 * Chunk is loaded
	 * Random chance OR there is no player in a 32 block radius
	 * Basically it has to not be at max, players in area, and will occur when
	 * players are far enough away or can happen very close with a small chance.
	 */
	public boolean canSpawn() {
		return count < maxCount && location.getChunk().isLoaded() &&
				location.getWorld().getDifficulty() != Difficulty.PEACEFUL &&
				Util.getPlayersInRadius(location, 16, 16, 16).isEmpty();
	}

	public void delayCount() {
		currentDelay++;
		if ((currentDelay %= delayCount) == 0 && canSpawn())
			incrementCount().get().spawn(location, this);
	}

	/**
	 * Increment the count of monster spawned by this spawner
	 *
	 * @return Spawner
	 */
	public Spawner incrementCount() {
		count++;
		return this;
	}

	/**
	 * Decrement the count of monsters currently spawned by this spawner
	 *
	 * @return Spawner
	 */
	public Spawner decrementCount() {
		count = (count - 1) < 0 ? 0 : (count - 1);
		return this;
	}

	/**
	 * Get the max count of mobs this spawner can have spawned at one time
	 *
	 * @return Max count of mobs that this spawner can have in the world at one time
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * Set the max count of mobs this spawner can have spawned at one time
	 *
	 * @param maxCount of mobs that this spawner can have in the world at one time.
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * Get the current amount of mobs spawned by this spawner.
	 *
	 * @return Current mobs in the world spawned by this spawner
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Set the current amount of mobs spawned by this spawner.
	 *
	 * @param count of mobs currently spawned by this spawner
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public int getDelayCount() {
		return delayCount;
	}

	public void setDelayCount(int delayCount) {
		this.delayCount = delayCount;
		currentDelay = delayCount - 1;
	}

	public int getCurrentDelay() {
		return currentDelay;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Spawner && ((Spawner) o).getLocation().equals(getLocation()) && ((Spawner) o).getMaxCount() == getMaxCount() && ((Spawner) o).getDelayCount() == getDelayCount();
	}

	@Override
	public int hashCode() {
		int result = location.hashCode();
		result = 31 * result + maxCount;
		result = 31 * result + count;
		result = 31 * result + currentDelay;
		result = 31 * result + delayCount;
		result = 31 * result + (mobs != null ? mobs.hashCode() : 0);
		return result;
	}
}