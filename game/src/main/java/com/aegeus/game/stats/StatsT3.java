package com.aegeus.game.stats;

import com.aegeus.game.item.Tier;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT3 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		copy(Tier.TIER_3.getStats());
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}
}
