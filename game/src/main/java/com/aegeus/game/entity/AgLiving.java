package com.aegeus.game.entity;

import org.bukkit.entity.LivingEntity;

public class AgLiving extends AgEntity {
	private final LivingEntity entity;

	private int hpRegen = 0;
	private float physRes = 0;
	private float magRes = 0;
	private float block = 0;
	private float dodge = 0;
	private float reflect = 0;

	public AgLiving(LivingEntity entity) {
		super(entity);
		this.entity = entity;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public int getHpRegen() {
		return hpRegen;
	}

	public void setHpRegen(int hpRegen) {
		this.hpRegen = hpRegen;
	}

	public float getPhysRes() {
		return physRes;
	}

	public void setPhysRes(float physRes) {
		this.physRes = physRes;
	}

	public float getMagRes() {
		return magRes;
	}

	public void setMagRes(float magRes) {
		this.magRes = magRes;
	}

	public float getBlock() {
		return block;
	}

	public void setBlock(float block) {
		this.block = block;
	}

	public float getDodge() {
		return dodge;
	}

	public void setDodge(float dodge) {
		this.dodge = dodge;
	}

	public float getReflect() {
		return reflect;
	}

	public void setReflect(float reflect) {
		this.reflect = reflect;
	}
}
