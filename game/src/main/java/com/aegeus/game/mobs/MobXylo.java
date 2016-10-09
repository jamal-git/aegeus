package com.aegeus.game.mobs;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.aegeus.game.Statistics;
import com.aegeus.game.planets.Planet;
import com.aegeus.game.stats.Stats;
import com.aegeus.game.stats.StatsBasic;
import com.aegeus.game.stats.StatsContainer;

public class MobXylo implements Mob {

	private static final String name = "Xylo";
	private static final String description = "A fast test mob from the planet Xylo.";
	private static final EntityType[] types = {
			EntityType.SKELETON
	};
	private static final Stats stats = new StatsBasic();
	private static final StatsContainer container = stats.getContainer();
	private static final Planet.Enum[] planets = {Planet.Enum.XYLO};
	
	private final Random random = new Random();
	
	@Override public String getName() { return name; }
	@Override public String getDescription() { return description; }
	@Override public EntityType[] getTypes() { return types; }
	@Override public Stats getStats() { return stats; }
	@Override public Planet.Enum[] getPlanets(){ return planets; }

	@Override
	public LivingEntity create(World world, Location loc) {
		Random random = new Random();
		LivingEntity entity = prepare((LivingEntity) world.spawnEntity(loc, types[random.nextInt(types.length)]));
		return entity;
	}
	
	@Override
	public LivingEntity prepare(LivingEntity entity) {
		//switch(entity.getType()) {
		//	default:
		//		entity.setCustomName(name + " " + entity.getName());
		//}
		entity.setCustomName(name + " " + entity.getName());
		entity.setCustomNameVisible(true);
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2, 2));
		if(stats.hasHelmet())
			entity.getEquipment().setHelmet(container.get(container.getHelmet(), random.nextFloat()).build());
		if(stats.hasChestplate())
			entity.getEquipment().setChestplate(container.get(container.getChestplate(), random.nextFloat()).build());
		if(stats.hasLeggings())
			entity.getEquipment().setLeggings(container.get(container.getLeggings(), random.nextFloat()).build());
		if(stats.hasBoots())
			entity.getEquipment().setBoots(container.get(container.getBoots(), random.nextFloat()).build());
		if(stats.hasWeapon())
			entity.getEquipment().setItemInMainHand(container.get(container.getWeapon(), random.nextFloat()).build());
		Statistics.updateStats(entity);
		return entity;
	}


}
