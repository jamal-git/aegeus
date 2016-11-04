package com.aegeus.game.stats;

public interface Stats {
	
	enum Premade {
		BASIC(new StatsBasic());
		
		private final Stats stats;
		
		Premade(Stats stats) { this.stats = stats; }
		public Stats getStats() { return stats; }
	}
	
	String getName();
	StatsContainer getContainer();
	boolean hasHelmet();
	boolean hasChestplate();
	boolean hasLeggings();
	boolean hasBoots();
	boolean hasWeapon();
	float getChance();
	
}
