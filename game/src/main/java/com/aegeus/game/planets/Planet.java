package com.aegeus.game.planets;

import org.bukkit.Location;
import org.bukkit.World;

public interface Planet {
	public static final Planet TERMINAL = new Terminal();
	public static final Planet XYLO = new PlanetXylo();
	
	public World getWorld();
	public String getName();
	public int getType();
	public String getDescription();
	public int getLevelRequired();
	public Location getLocation();
}
