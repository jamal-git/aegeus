package com.aegeus.game.stats;

import com.aegeus.game.item.Tier;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT1 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		copy(Tier.TIER_1.getStats());
	}

	@Override
	public boolean hasChestplate() {
		return random.nextBoolean();
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}

	@Override
	public boolean hasBoots() {
		return random.nextBoolean();
	}
}
