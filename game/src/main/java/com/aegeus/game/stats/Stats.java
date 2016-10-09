package com.aegeus.game.stats;

public interface Stats {
	
	public enum Enum {
		BASIC(new StatsBasic());
		
		private final Stats stats;
		
		Enum(Stats stats) { this.stats = stats; }
		public Stats getStats() { return stats; }
	}
	
	public String getName();
	public StatsContainer getContainer();
	public boolean hasHelmet();
	public boolean hasChestplate();
	public boolean hasLeggings();
	public boolean hasBoots();
	public boolean hasWeapon();
	
}
