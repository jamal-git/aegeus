package com.aegeus.game.item;

import com.aegeus.game.util.Helper;

public enum ItemRarity {
	NONE(-2, ""),
	STARTER(-1, "&9&oStarter"),
	STANDARD(0, "&7&oStandard"),
	UNCOMMON(1, "&a&oUncommon"),
	RARE(2, "&b&oRare"),
	UNIQUE(3, "&e&oUnique"),
	DUNGEON(4, "&c&oDungeon Loot");
	
	private int typeID;
	private String lore;
	
	ItemRarity(int typeID, String lore){
		this.typeID = typeID;
		this.lore = Helper.colorCodes(lore);
	}
	
	public String getLore(){
		return lore;
	}

	public int getTypeID(){
		return typeID;
	}	
	
	public static ItemRarity fromTypeID(int typeID){
		switch(typeID){
			case -1:
				return STARTER;
			case 0:
				return STANDARD;
			case 1:
				return UNCOMMON;
			case 2:
				return RARE;
			case 3:
				return UNIQUE;
			case 4:
				return DUNGEON;
		}
		return null;
	}
}
