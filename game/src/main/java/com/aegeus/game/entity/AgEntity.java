package com.aegeus.game.entity;

import org.bukkit.entity.Entity;

public class AgEntity {
	private final Entity entity;

	public AgEntity(Entity entity) {
		this.entity = entity;
	}

	public AgEntity(AgEntity other) {
		this.entity = other.entity;
	}

	public Entity getEntity() {
		return entity;
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
