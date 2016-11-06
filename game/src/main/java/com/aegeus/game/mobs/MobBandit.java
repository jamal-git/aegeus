package com.aegeus.game.mobs;

import com.aegeus.game.Statistics;
import com.aegeus.game.data.AegeusMonster;
import com.aegeus.game.data.Data;
import com.aegeus.game.stats.Stats;
import com.aegeus.game.stats.StatsContainer;
import com.aegeus.game.util.Utility;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import java.util.Random;

public class MobBandit implements Mob {
	private final Random RANDOM = new Random();
	private final String name = "Bandit";
	private final String description = "An outlaw human of its inhabiting planet.";
	private final EntityType[] types = {EntityType.SKELETON, EntityType.ZOMBIE};
	private Stats stats;
	private StatsContainer container;

	public MobBandit(Stats stats) {
		this.stats = stats;
		this.container = stats.getContainer();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public EntityType[] getTypes() {
		return types;
	}

	@Override
	public Stats getStats() {
		return stats;
	}

	@Override
	public LivingEntity create(Location loc) {
		return prepare((Monster) loc.getWorld().spawnEntity(loc, types[RANDOM.nextInt(types.length)]));
	}

	@Override
	public LivingEntity prepare(Monster monster) {
		//switch(entity.getType()) {
		//	default:
		//		entity.setCustomName(name + " " + entity.getName());
		//}
		monster.setCustomName(monster.getName() + " " + name);
		monster.setCustomNameVisible(true);
		if (stats.hasHelmet())
			monster.getEquipment().setHelmet(container.get(
					container.getHelmet(), Utility.rarity()).build());
		if (stats.hasChestplate())
			monster.getEquipment().setChestplate(container.get(
					container.getChestplate(), Utility.rarity()).build());
		if (stats.hasLeggings())
			monster.getEquipment().setLeggings(container.get(
					container.getLeggings(), Utility.rarity()).build());
		if (stats.hasBoots())
			monster.getEquipment().setBoots(container.get(
					container.getBoots(), Utility.rarity()).build());
		if (stats.hasWeapon())
			monster.getEquipment().setItemInMainHand(container.get(
					container.getWeapon(), Utility.rarity()).build());
		Statistics.updateStats(monster);
		monster.setHealth(monster.getMaxHealth());
		AegeusMonster am = Data.get(monster);
		am.setDropChance(stats.getChance());
		return monster;
	}


}
