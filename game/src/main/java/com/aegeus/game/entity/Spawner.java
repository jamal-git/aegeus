package com.aegeus.game.entity;

import com.aegeus.game.stats.Stats;
import com.aegeus.game.util.Util;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Spawner {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Location location;
	private int maxCount = 3;
	private int count;
	private List<Stats> list = new ArrayList<>();

	public Spawner(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public List<Stats> getList() {
		return list;
	}

	public void setList(List<Stats> list) {
		this.list = list;
	}

	public void set(Stats... stats) {
		list.clear();
		list.addAll(Arrays.asList(stats));
	}

	public void add(Stats... stats) {
		list.addAll(Arrays.asList(stats));
	}

	public Stats get() {
		return list.size() < 2 ? list.get(0) : list.get(random.nextInt(list.size()));
	}

	/**
	 * Conditions to spawn a mob are:
	 * The max amount of mobs spawned is not reached yet
	 * Chunk is loaded
	 * Random chance OR there is no player in a 32 block radius
	 * Basically it has to not be at max, players in area, and will occur when
	 * players are far enough away or can happen very close with a small chance.
	 *
	 * @return
	 */
	public boolean canSpawn() {
		return count < maxCount && location.getChunk().isLoaded() &&
				(Util.getPlayersInRadius(location, 24, 24, 24).isEmpty() || (random.nextDouble() < 0.04));
	}

	public Spawner incrementCount() {
		count++;
		return this;
	}

	public Spawner decrementCount() {
		count = (count - 1) < 0 ? 0 : (count - 1);
		return this;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}