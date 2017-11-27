package com.aegeus.game.stats;

import com.aegeus.game.stats.tier.impl.Mob;
import com.aegeus.game.util.Util;
import org.bukkit.entity.EntityType;

public class MobBandit extends Mob {
	@Override
	public void setup(Object... args) {
		copy(Util.findTier(args));
		getNames().add("Tired Bandit");
		getNames().add("Old Bandit");
		getNames().add("Young Bandit");
		getNames().add("Angry Bandit");
		getNames().add("Happy Bandit");
		getNames().add("Pretty Bandit");
		getNames().add("Ugly Bandit");

		getTypes().add(EntityType.ZOMBIE);
		getTypes().add(EntityType.SKELETON);
	}

	@Override
	public String getId() {
		return "bandit";
	}
}
