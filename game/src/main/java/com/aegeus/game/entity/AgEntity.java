package com.aegeus.game.entity;

import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AgEntity {
	private final LivingEntity entity;
	private final UUID uuid;
	private LocalDateTime combatDate;
	private Entity attacker;

	private int hpRegen = 0;
	private float energyRegen = 0;
	private float physRes = 0;
	private float magRes = 0;
	private float block = 0;
	private float dodge = 0;
	private float reflect = 0;
	private float critChance = 0;

	public AgEntity(LivingEntity entity) {
		this.entity = entity;
		uuid = entity.getUniqueId();
	}

	public AgEntity(AgEntity other) {
		this.entity = other.entity;
		this.uuid = other.uuid;
		this.combatDate = other.combatDate;
		this.attacker = other.attacker;

		this.hpRegen = other.hpRegen;
		this.energyRegen = other.energyRegen;
		this.physRes = other.physRes;
		this.magRes = other.magRes;
		this.block = other.block;
		this.dodge = other.dodge;
		this.reflect = other.reflect;
		this.critChance = other.critChance;
	}

	private NBTTagCompound getTag() {
		NBTTagCompound tag = new NBTTagCompound();
		((CraftEntity) entity).getHandle().e(tag);
		return tag;
	}

	private void setTag(NBTTagCompound tag) {
		net.minecraft.server.v1_9_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		nmsEntity.c(tag);
		((EntityLiving) nmsEntity).a(tag);
	}

	public NBTTagCompound getAegeusInfo() {
		NBTTagCompound tag = getTag();
		return (tag.hasKey("AegeusInfo")) ? tag.getCompound("AegeusInfo") : new NBTTagCompound();
	}

	public void setAegeusInfo(NBTTagCompound info) {
		NBTTagCompound tag = getTag();
		tag.set("AegeusInfo", info);
		setTag(tag);
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public UUID getUUID() {
		return uuid;
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

	public float getEnergyRegen() {
		return energyRegen;
	}

	public void setEnergyRegen(float energyRegen) {
		this.energyRegen = energyRegen;
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

	public float getCritChance() {
		return critChance;
	}

	public void setCritChance(float crit) {
		this.critChance = Math.min(1, crit);
	}
}
