package com.aegeus.game.item;

public enum ItemType {

	NONE(-1, "None"),
	WEAPON(0, "Weapon"),
	ARMOR(1, "Armor"),
	PICKAXE(2, "Pickaxe");
	
	private int id;
	private String name;
	
	ItemType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	
	public static ItemType getById(int id) {
		for(ItemType i : ItemType.values())
			if(i.id == id) return i;
		return null;
	}
	
	public static ItemType getByName(String name) {
		for(ItemType i : ItemType.values())
			if(i.name.equalsIgnoreCase(name)) return i;
		return null;
	}
	
}
