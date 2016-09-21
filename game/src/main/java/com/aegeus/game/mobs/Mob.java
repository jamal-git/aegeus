package com.aegeus.aegeus.game.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.aegeus.aegeus.game.planets.Planet;
import com.aegeus.aegeus.game.stats.Stats;

public interface Mob {
	/*
	 * Mobs!
	 * Can be hostile, or friendly, or neutral.
	 */
	public String getName();
	public String getDescription();
	public EntityType getType();
	public Stats[] getStats();
	public Planet[] getPlanets();
	public Entity create(World world, Location loc);
}

