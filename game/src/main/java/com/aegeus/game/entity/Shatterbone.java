package com.aegeus.game.entity;

import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Shatterbone extends AgMonster {
	private List<LivingEntity> minions = new ArrayList<>();

	public Shatterbone(LivingEntity entity) {
		super(entity);
	}

	public Shatterbone(AgEntity info) {
		super(info);
	}

	public Shatterbone(AgMonster info) {
		super(info);
	}

	public Shatterbone(Shatterbone other) {
		super(other);
		this.minions = other.minions;
	}

	public List<LivingEntity> getMinions() {
		return minions;
	}

	public void setMinions(List<LivingEntity> minions) {
		this.minions = minions;
	}
}
