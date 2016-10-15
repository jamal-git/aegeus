package com.aegeus.game.stats;

public interface Stats {
	
	public static enum Premade {
		BASIC(new StatsBasic());
		
		private final Stats stats;
		
		Premade(Stats stats) { this.stats = stats; }
		public Stats getStats() { return stats; }
	}
	
	public String getName();
	public StatsContainer getContainer();
	public boolean hasHelmet();
	public boolean hasChestplate();
	public boolean hasLeggings();
	public boolean hasBoots();
	public boolean hasWeapon();
	public float getChance();
	
}
