package com.aegeus.aegeus.game.item;

public enum ItemType {
	WEAPON(0),
	ARMOR(1),
	PICKAXE(2);
	
	private int typeID;
	
	ItemType(int typeID){
		this.typeID = typeID;
	}
	
	public int getTypeID(){
		return typeID;
	}
	
	public static ItemType fromTypeID(int typeID){
		switch(typeID){
			case 0:
				return WEAPON;
			case 1:
				return ARMOR;
			case 2:
				return PICKAXE;
		}
		return null;
	}
}
