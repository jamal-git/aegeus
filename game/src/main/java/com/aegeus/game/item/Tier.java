package com.aegeus.game.item;

public enum Tier {
	NONE("&f", "Custom", "Custom", "Custom Sword", "Custom Axe", "Custom Bow", "Custom Staff", "Custom Polearm"),
	TIER_1("&f", "Leather", "Wood", "Shortsword", "Hatchet", "Basic Bow", "Basic Staff", "Basic Polearm"),
	TIER_2("&a", "Chainmail", "Stone", "Longsword", "Great Axe", "Advanced Bow", "Advanced Staff", "Advanced Polearm"),
	TIER_3("&b", "Magic", "Iron", "Magic Sword", "Magic Axe", "Magic Bow", "Magic Staff", "Magic Polearm"),
	TIER_4("&d", "Ancient", "Diamond", "Ancient Sword", "Ancient Axe", "Ancient Bow", "Ancient Staff", "Ancient Polearm"),
	TIER_5("&e", "Legendary", "Gold", "Legendary Sword", "Legendary Axe", "Legendary Bow", "Legendary Staff", "Legendary Polearm");

	private static final Tier[] values = values();

	private final String color;
	private final String armor;
	private final String weapon;
	private final String sword;
	private final String axe;
	private final String bow;
	private final String staff;
	private final String polearm;

	Tier(String color, String armor, String weapon, String sword, String axe, String bow, String staff, String polearm) {
		this.color = color;
		this.armor = armor;
		this.weapon = weapon;
		this.sword = sword;
		this.axe = axe;
		this.bow = bow;
		this.staff = staff;
		this.polearm = polearm;
	}

	public static Tier fromTier(int tier) {
		if (tier > 5 || tier < 0) return NONE;
		return values[tier];
	}

	public String getColor() {
		return color;
	}

	public String getArmor() {
		return armor;
	}

	public String getWeapon() {
		return weapon;
	}

	public String getSword() {
		return sword;
	}

	public String getAxe() {
		return axe;
	}

	public String getBow() {
		return bow;
	}

	public String getStaff() {
		return staff;
	}

	public String getPolearm() {
		return polearm;
	}
}
