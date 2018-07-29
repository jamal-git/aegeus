package com.aegeus.game.entity;

import org.bukkit.entity.LivingEntity;

public class AgLiving extends AgEntity {
	public AgLiving(LivingEntity entity) {
		super(entity);
	}

	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}
}
