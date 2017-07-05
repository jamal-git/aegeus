package com.aegeus.game.item;

import com.aegeus.game.util.Util;

public enum Rarity {
	STARTER(0, "Starter", "&9&oStarter"),
	COMMON(1, "Common", "&7&oCommon"),
	UNCOMMON(2, "Uncommon", "&a&oUncommon"),
	RARE(3, "Rare", "&b&oRare"),
	UNIQUE(4, "Unique", "&e&oUnique"),
	DUNGEON(5, "Dungeon Loot", "&4&oDungeon Loot");

	private final int id;
	private final String name;
	private final String lore;

	Rarity(int id, String name, String lore) {
		this.id = id;
		this.name = name;
		this.lore = Util.colorCodes(lore);
	}

	public static Rarity fromID(int id) {
		for (Rarity r : values())
			if (r.id == id) return r;
		return null;
	}

	public static Rarity fromName(String name) {
		for (Rarity r : values())
			if (r.name.equalsIgnoreCase(name)) return r;
		return null;
	}

	public static Rarity fromValue(float f) {
		if (f <= 0.62) return Rarity.COMMON;
		else if (f <= 0.86) return Rarity.UNCOMMON;
		else if (f <= 0.97) return Rarity.RARE;
		else return Rarity.UNIQUE;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLore() {
		return lore;
	}
}
