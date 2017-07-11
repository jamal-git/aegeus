package com.aegeus.game.util;

import java.util.concurrent.ThreadLocalRandom;

public class Chance<T> {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private T object;
	private float chance = 1f;

	public Chance() {

	}

	public Chance(T object, float chance) {
		this.object = object;
		this.chance = chance;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = chance;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public T get() {
		return get(null);
	}

	public T get(T def) {
		return random.nextFloat() <= chance ? object : def;
	}
}