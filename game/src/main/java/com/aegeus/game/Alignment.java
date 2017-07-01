package com.aegeus.game;

public enum Alignment {
	LAWFUL(0, "Lawful", "&aYou have abided by the law, and have become Lawful.", 0, 0.5f, 0),
	NEUTRAL(1, "Neutral", "&eYou have seeded a disruption and have become Neutral.", 0, 1, 0.25f),
	OUTLAW(2, "Outlaw", "&cYou have killed a lawful player and have become an Outlaw.", 1, 1, 0.5f);

	private final int id;
	private final String name;
	private final String description;
	private final float firstItemChance;
	private final float invChance;
	private final float armorChance;

	Alignment(int id, String name, String description,
			  float firstItemChance, float invChance, float armorChance) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.firstItemChance = firstItemChance;
		this.invChance = invChance;
		this.armorChance = armorChance;
	}

	public static Alignment fromID(int id) {
		for (Alignment a : values())
			if (a.id == id) return a;
		return null;
	}

	public static Alignment fromName(String name) {
		for (Alignment a : values())
			if (a.name.equalsIgnoreCase(name)) return a;
		return null;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public float getFirstItemChance() {
		return firstItemChance;
	}

	public float getInventoryChance() {
		return invChance;
	}

	public float getArmorChance() {
		return armorChance;
	}

}
