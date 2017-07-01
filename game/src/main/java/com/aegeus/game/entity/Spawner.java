package com.aegeus.game.entity;

import com.aegeus.game.stats.Stats;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Spawner {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Location location;
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
		return list.get(random.nextInt(list.size()));
	}

	public boolean canSpawn() {
		return (location.getWorld().getNearbyEntities(location, 25, 25, 25).size() < 5);
	}
}