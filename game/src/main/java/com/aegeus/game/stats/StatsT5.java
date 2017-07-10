package com.aegeus.game.stats;

import com.aegeus.game.item.Tier;

public class StatsT5 extends Stats {
	@Override
	public void prepare() {
		copy(Tier.TIER_5.getStats());
	}
}
