
package com.aegeus.game.item;

import com.aegeus.game.util.Utility;

public enum Rarity {
	STARTER(-1, "Starter", "&9&oStarter"),
	STANDARD(0, "Standard", "&7&oStandard"),
	UNCOMMON(1, "Uncommon", "&a&oUncommon"),
	RARE(2, "Rare", "&b&oRare"),
	UNIQUE(3, "Unique", "&e&oUnique"),
	DUNGEON(4, "Dungeon Loot", "&c&oDungeon Loot");

	private int id;
	private String name;
	private String lore;

	Rarity(int id, String name, String lore) {
		this.id = id;
		this.name = name;
		this.lore = Utility.colorCodes(lore);
	}

	public int getId() { return id; }
	public String getName() { return name; }
	public String getLore() { return lore; }

	public static Rarity getById(int id) {
		for(Rarity r : Rarity.values())
			if(r.id == id) return r;
		return null;
	}
	
	public static Rarity getByName(String name) {
		for(Rarity r : Rarity.values())
			if(r.name.equalsIgnoreCase(name)) return r;
		return null;
	}
}
