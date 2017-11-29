package com.aegeus.game.stats.impl;

import com.aegeus.game.stats.MobBandit;
import com.aegeus.game.stats.MobSkeleton;
import com.aegeus.game.stats.MobViktor;

public abstract class Mob extends Stats {
	public static Mob[] values() {
		return new Mob[]{new MobBandit(), new MobSkeleton(), new MobViktor()};
	}

	public static Mob get(String id, Object... args) {
		for (Mob m : values())
			if (m.getId().equalsIgnoreCase(id)) {
				try {
					Mob mob = m.getClass().newInstance();
					mob.setup(args);
					return mob;
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		return null;
	}

	public abstract String getId();

	@Override
	public String toString() {
		return getId();
	}
}
