package com.aegeus.aegeus.game.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.aegeus.aegeus.game.planets.Planet;
import com.aegeus.aegeus.game.planets.PlanetXylo;
import com.aegeus.aegeus.game.stats.Stats;
import com.aegeus.aegeus.game.stats.StatsBasic;

public class MobXyloSkeleton implements Mob {

	private static String name = "Xylo Skeleton";
	private static String description = "A fast test skeleton from the planet Xylo.";
	private static Stats[] stats = {new StatsBasic()};
	private static Planet[] planets = {new PlanetXylo()};
	
	private static EntityType type = EntityType.SKELETON;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public EntityType getType() {
		return type;
	}
	
	@Override
	public Stats[] getStats() {
		return stats;
	}
	
	@Override
	public Planet[] getPlanets(){
		return planets;
	}

	@Override
	public Entity create(World world, Location loc) {
		LivingEntity entity = (LivingEntity) world.spawnEntity(loc, type);
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
		entity.getEquipment().setHelmet(stats[0].getHelmet().build());
		return entity;
	}

}
