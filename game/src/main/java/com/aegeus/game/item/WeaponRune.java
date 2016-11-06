package com.aegeus.game.item;

public enum WeaponRune {
	DRAGONBREATH(0);

	private int id;

	WeaponRune(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public WeaponRune getById(int id) {
		for (WeaponRune rune : WeaponRune.values())
			if (rune.getId() == id) return rune;
		return null;
	}
}
