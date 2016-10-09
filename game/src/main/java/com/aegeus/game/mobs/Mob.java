package com.aegeus.game.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.aegeus.game.planets.Planet;
import com.aegeus.game.stats.Stats;

public interface Mob {
	
	public enum Enum {
		XYLO(new MobXylo());
		
		private final Mob mob;
		
		Enum(Mob mob) {
			this.mob = mob;
		}
		
		public static Enum byName(String name) {
			for(Enum mob : values())
				if(mob.getMob().getName().equalsIgnoreCase(name))
					return mob;
			return null;
		}
		
		public Mob getMob() { return mob; }
	}
	
	public String getName();
	public String getDescription();
	public EntityType[] getTypes();
	public Stats getStats();
	public Planet.Enum[] getPlanets();
	public LivingEntity create(World world, Location loc);
	public LivingEntity prepare(LivingEntity entity);
}

