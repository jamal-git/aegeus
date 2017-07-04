package com.aegeus.game.item;

public enum Tier {
	NONE("Custom", "Custom", "Custom Sword", "Custom Axe"),
	TIER_1("&fLeather", "&fWood", "&fShortsword", "&fHatchet"),
	TIER_2("&aChainmail", "&aStone", "&aLongsword", "&aGreat Axe"),
	TIER_3("&bIron", "&bIron", "&bMagic Sword", "&bMagic Axe"),
	TIER_4("&dDiamond", "&dDiamond", "&dAncient Sword", "&dAncient Axe"),
	TIER_5("&eGold", "&eGold", "&eLegendary Sword", "&eLegendary Axe");

	private final String armor;
	private final String weapon;
	private final String sword;
	private final String axe;

	Tier(String armor, String weapon, String sword, String axe) {
		this.armor = armor;
		this.weapon = weapon;
		this.sword = sword;
		this.axe = axe;
	}

	public static Tier fromTier(int tier) {
		if (tier == 5) return TIER_5;
		else if (tier == 4) return TIER_4;
		else if (tier == 3) return TIER_3;
		else if (tier == 2) return TIER_2;
		else if (tier == 1) return TIER_1;
		else return NONE;
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
}
