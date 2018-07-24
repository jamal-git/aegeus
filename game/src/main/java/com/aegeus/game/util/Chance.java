package com.aegeus.game.util;

import com.google.common.base.Objects;

public class Chance<T> {
	private T object;
	private T def;
	private float chance;

	public Chance(T object, float chance) {
		this.object = object;
		this.chance = chance;
	}

	public Chance(T object, T def, float chance) {
		this.object = object;
		this.def = def;
		this.chance = chance;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public T getDefault() {
		return def;
	}

	public void setDefault(T def) {
		this.def = def;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = Util.clamp(chance, 0, 1);
	}

	public T get() {
		return chance == 1 ? object : Util.randFloat() <= chance ? object : def;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(object, chance);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Chance
				&& Objects.equal(object, ((Chance) obj).object);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("object", object)
				.add("chance", chance)
				.toString();
	}
}