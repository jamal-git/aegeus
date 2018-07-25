package com.aegeus.game.entity;

import org.bukkit.entity.LivingEntity;

public class AgMonster extends AgLiving {
	private String name;

	public AgMonster(LivingEntity entity) {
		super(entity);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
