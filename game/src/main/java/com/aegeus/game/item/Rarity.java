package com.aegeus.game.item;

import com.aegeus.game.util.Util;

public enum Rarity {
	STARTER(-1, "Starter", "&9&oStarter"),
	COMMON(0, "Common", "&7&oCommon"),
	UNCOMMON(1, "Uncommon", "&a&oUncommon"),
	RARE(2, "Rare", "&b&oRare"),
	UNIQUE(3, "Unique", "&e&oUnique"),
	DUNGEON(4, "Dungeon Loot", "&4&oDungeon Loot");

	private final int id;
	private final String name;
	private final String lore;

	Rarity(int id, String name, String lore) {
		this.id = id;
		this.name = name;
		this.lore = Util.colorCodes(lore);
	}

	public static Rarity fromID(int id) {
		for (Rarity r : Rarity.values())
			if (r.id == id) return r;
		return null;
	}

	public static Rarity fromName(String name) {
		for (Rarity r : Rarity.values())
			if (r.name.equalsIgnoreCase(name)) return r;
		return null;
	}

	public static Rarity fromValue(float f) {
		if (f <= 0.65) return Rarity.COMMON;
		else if (f <= 0.87) return Rarity.UNCOMMON;
		else if (f <= 0.98) return Rarity.RARE;
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
