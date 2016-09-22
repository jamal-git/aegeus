package com.aegeus.game.planets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class PlanetXylo implements Planet {

	// TODO Remove.
	// Not a real planet, only for testing.
	
	private static final World world = Bukkit.getWorld("planet_xylo");
	private static final int type = 0;
	private static final String name = "Planet Xylo";
	private static final String description = "A temporary planet with tons of gold resources.";
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
