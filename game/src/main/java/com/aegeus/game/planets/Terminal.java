package com.aegeus.game.planets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Terminal implements Planet {
	
	private static final World world = Bukkit.getWorld("terminal");
	private static final int type = -1;
	private static final String name = "The Terminal";
	private static final String description = "The hub of global communication.";
	private static final int levelRequired = 0;
	private static final Location location = world.getSpawnLocation();

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public int getLevelRequired() {
		return levelRequired;
	}
	
	@Override
	public Location getLocation() {
		return location;
	}

}
