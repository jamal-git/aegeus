package com.aegeus.game.data;

import org.bukkit.entity.Monster;

public class AegeusMonster extends AegeusEntity {
	private Monster monster;
	private float dropChance;

	protected AegeusMonster(Monster monster) {
		super(monster);
		this.monster = monster;
	}

	public Monster getMonster() {
		return monster;
	}

	public float getDropChance() {
		return dropChance;
	}

	public void setDropChance(float dropChance) {
		this.dropChance = dropChance;
	}
}
