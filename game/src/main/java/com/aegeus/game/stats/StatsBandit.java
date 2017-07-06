package com.aegeus.game.stats;

import org.bukkit.entity.EntityType;

public class StatsBandit extends Stats {
	public StatsBandit(Stats inherit) {
		super(inherit);
	}

	@Override
	public void prepare() {
		getNames().add("Tired Bandit");
		getNames().add("Old Bandit");
		getNames().add("Young Bandit");
		getNames().add("Angry Bandit");
		getNames().add("Happy Bandit");
		getNames().add("Pretty Bandit");
		getNames().add("Ugly Bandit");

		getTypes().add(EntityType.ZOMBIE);
	}
}
