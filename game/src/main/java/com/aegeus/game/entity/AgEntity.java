package com.aegeus.game.entity;

import org.bukkit.entity.Entity;

public class AgEntity {
	private final Entity entity;

	public AgEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return entity.equals(obj);
	}

	@Override
	public String toString() {
		return entity.toString();
	}
}
