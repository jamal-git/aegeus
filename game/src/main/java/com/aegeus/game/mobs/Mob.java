package com.aegeus.game.mobs;

import com.aegeus.game.stats.Stats;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

public interface Mob {
	String getName();

	String getDescription();

	EntityType[] getTypes();

	Stats getStats();

	LivingEntity create(Location loc);

	LivingEntity prepare(Monster monster);
}

