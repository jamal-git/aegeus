package com.aegeus.game.entity;

import org.bukkit.entity.LivingEntity;

public class AgLiving extends AgEntity {
	private final LivingEntity entity;

	public AgLiving(LivingEntity entity) {
		super(entity);
		this.entity = entity;
	}

	public LivingEntity getEntity() {
		return entity;
	}
}
