package com.aegeus.game.util;

public class Chance<T> {
	private T object;
	private float chance;

	public Chance(T object, float chance) {
		this.object = object;
		this.chance = chance;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = Util.clamp(chance, 0, 1);
	}

	public T get(T def) {
		return chance == 1 ? object : Util.randFloat() <= chance ? object : def;
	}

	public T get() {
		return get(null);
	}
}