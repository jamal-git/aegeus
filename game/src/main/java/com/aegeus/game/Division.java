package com.aegeus.game;

public enum Division {
	BRONZE(0, "Bronze"),
	SILVER(1, "Silver"),
	GOLD(2, "Gold"),
	DIAMOND(3, "Diamond"),
	PLATINUM(4, "Platinum"),
	LEGEND(5, "Legend");

	final int id;
	final String name;

	Division(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Division fromName(String name) {
		for (Division d : values())
			if (d.name.equalsIgnoreCase(name)) return d;
		return null;
	}

	public Division fromID(int id) {
		for (Division d : values())
			if (d.id == id) return d;
		return null;
	}
}
