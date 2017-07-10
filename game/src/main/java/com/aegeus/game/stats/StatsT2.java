package com.aegeus.game.stats;

import com.aegeus.game.item.Tier;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT2 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		copy(Tier.TIER_2.getStats());
	}

	@Override
	public boolean hasChestplate() {
		return random.nextBoolean();
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}
}
