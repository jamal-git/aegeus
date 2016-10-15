package com.aegeus.game.data;

import org.bukkit.entity.Monster;

public class MonsterData extends EntityData {

	private Monster monster;
	private float dropChance;
	
	public MonsterData(Monster monster) {
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
