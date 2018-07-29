package com.aegeus.game.entity;

import org.bukkit.entity.Entity;

public class AgEntity {
	private final Entity entity;
	private String name = "";

	public AgEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
