package com.aegeus.game.stats;

import com.aegeus.game.item.Tier;

public class StatsT4 extends Stats {
	@Override
	public void prepare() {
		copy(Tier.TIER_4.getStats());
	}
}
