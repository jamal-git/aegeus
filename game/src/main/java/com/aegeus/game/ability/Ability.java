package com.aegeus.game.ability;

import com.aegeus.game.entity.AgMonster;

public abstract class Ability {
	private final String name;
	private final String desc;

	public Ability(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public abstract void activate(AgMonster info);

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return getName();
	}
}
