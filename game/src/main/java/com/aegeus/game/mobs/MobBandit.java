package com.aegeus.game.mobs;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import com.aegeus.game.Statistics;
import com.aegeus.game.data.Data;
import com.aegeus.game.data.MonsterData;
import com.aegeus.game.stats.Stats;
import com.aegeus.game.stats.StatsContainer;

public class MobBandit implements Mob {

	private static final String name = "Bandit";
	private static final String description = "An outlaw human of its inhabiting planet.";
	private static final EntityType[] types = { EntityType.SKELETON };
	private Stats stats;
	private StatsContainer container;
	
	private final Random random = new Random();
	
	public MobBandit() { }
	
	public MobBandit(Stats stats) {
		this.stats = stats;
		this.container = stats.getContainer();
	}
	
	@Override public String getName() { return name; }
	@Override public String getDescription() { return description; }
	@Override public EntityType[] getTypes() { return types; }
	@Override public Stats getStats() { return stats; }
	
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
		MonsterData md = Data.getMonsterData(entity);
		md.setDropChance(stats.getChance());
		return entity;
	}


}
