package com.aegeus.game.social;

public enum ChatChannel {
	LOCAL(0, "Local"), // Nearby players
	GLOBAL(1, "Global"); // Players on the current server

	private final int id;
	private final String name;

	ChatChannel(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static ChatChannel fromID(int id) {
		for (ChatChannel c : values())
			if (c.id == id) return c;
		return null;
	}

	public static ChatChannel fromName(String name) {
		for (ChatChannel c : values())
			if (c.name.equalsIgnoreCase(name)) return c;
		return null;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
}