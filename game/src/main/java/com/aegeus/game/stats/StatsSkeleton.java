package com.aegeus.game.stats;

import org.bukkit.entity.EntityType;

public class StatsSkeleton extends Stats {
	public StatsSkeleton() {

	}

	public StatsSkeleton(Stats parent) {
		super(parent);
	}

	@Override
	public void prepare() {
		getNames().add("Skeleton");
		getTypes().add(EntityType.SKELETON);
	}
}
