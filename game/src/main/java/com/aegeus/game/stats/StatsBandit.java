package com.aegeus.game.stats;

import org.bukkit.entity.EntityType;

public class StatsBandit extends Stats {
	public StatsBandit(Stats inherit) {
		super(inherit);
	}

	@Override
	public void prepare() {
		setHpMultiplier(0.86f);
		setDmgMultiplier(0.95f);

		addName("Tired Bandit");
		addName("Old Bandit");
		addName("Young Bandit");
		addName("Angry Bandit");
		addName("Happy Bandit");
		addName("Pretty Bandit");
		addName("Ugly Bandit");

		addType(EntityType.ZOMBIE);
	}
}
