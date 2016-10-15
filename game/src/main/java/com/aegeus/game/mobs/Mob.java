package com.aegeus.game.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.aegeus.game.stats.Stats;

public interface Mob {
	
	public enum Premade {
		BANDIT(new MobBandit());
		
		private final Mob mob;
		
		Premade(Mob mob) {
			this.mob = mob;
		}
		
		public static Premade byName(String name) {
			for(Premade mob : values())
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
	public LivingEntity create(World world, Location loc);
	public LivingEntity prepare(LivingEntity entity);
}

