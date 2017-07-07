package com.aegeus.game.item;

public enum Tier {
	NONE("Custom", "Custom", "Custom Sword", "Custom Axe", "Custom Bow", "Custom Staff", "Custom Polearm"),
	TIER_1("&fLeather", "&fWood", "Shortsword", "Hatchet", "Basic Bow", "Basic Staff", "Basic Polearm"),
	TIER_2("&aChainmail", "&aStone", "Longsword", "Great Axe", "Advanced Bow", "Advanced Staff", "Advanced Polearm"),
	TIER_3("&bMagic", "&bIron", "Magic Sword", "Magic Axe", "Magic Bow", "Magic Staff", "Magic Polearm"),
	TIER_4("&dAncient", "&dDiamond", "Ancient Sword", "Ancient Axe", "Ancient Bow", "Ancient Staff", "Ancient Polearm"),
	TIER_5("&eLegendary", "&eGold", "Legendary Sword", "Legendary Axe", "Legendary Bow", "Legendary Staff", "Legendary Polearm");

	private final String armor;
	private final String weapon;
	private final String sword;
	private final String axe;
	private final String bow;
	private final String staff;
	private final String polearm;

	Tier(String armor, String weapon, String sword, String axe, String bow, String staff, String polearm) {
		this.armor = armor;
		this.weapon = weapon;
		this.sword = sword;
		this.axe = axe;
		this.bow = bow;
		this.staff = staff;
		this.polearm = polearm;
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
