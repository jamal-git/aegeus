package com.aegeus.game.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.time.LocalDateTime;

public class AgLiving extends AgEntity {
	private final LivingEntity entity;
	private LocalDateTime combatDate;
	private Entity attacker;

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

	public AgLiving(AgEntity info) {
		super(info);
		this.entity = (LivingEntity) info.getEntity();
	}

	public AgLiving(AgLiving other) {
		super(other);
		this.entity = other.entity;
		this.combatDate = other.combatDate;
		this.attacker = other.attacker;

		this.hpRegen = other.hpRegen;
		this.physRes = other.physRes;
		this.magRes = other.magRes;
		this.block = other.block;
		this.dodge = other.dodge;
		this.reflect = other.reflect;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public LocalDateTime getCombatDate() {
		return combatDate;
	}

	public Entity getAttacker() {
		return attacker;
	}

	public void setAttacker(Entity attacker) {
		this.attacker = attacker;
	}

	public void inCombat() {
		combatDate = LocalDateTime.now();
	}

	public boolean isInCombat() {
		return combatDate != null && LocalDateTime.now().isBefore(combatDate.plusSeconds(10));
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
