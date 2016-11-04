package com.aegeus.game.mobs;

import com.aegeus.game.stats.Stats;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public interface Mob {
	String getName();
	String getDescription();
	EntityType[] getTypes();
	Stats getStats();
	LivingEntity create(Location loc);
	LivingEntity prepare(LivingEntity entity);
}

