package com.aegeus.aegeus.game.planets;

import org.bukkit.Location;
import org.bukkit.World;

public interface Planet {
	/*
	 * Planets!
	 * They should explain themselves.
	 */
	public World getWorld();
	public String getName();
	public int getType();
	public String getDescription();
	public int getLevelRequired();
	public Location getLocation();
}
