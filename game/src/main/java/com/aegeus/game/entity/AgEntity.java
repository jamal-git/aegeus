package com.aegeus.game.entity;

import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AgEntity {
	protected final LivingEntity entity;
	protected final UUID uuid;
	protected LocalDateTime combatDate;

	protected int level = 0;
	protected int energy = 0;

	protected int hpRegen = 0;
	protected float energyRegen = 0;
	protected float defense = 0;
	protected float magicRes = 0;
	protected float block = 0;
	protected float critChance = 0;

	protected int strength = 0;
	protected int dexterity = 0;
	protected int intellect = 0;
	protected int vitality = 0;

//	private int Thorns = 0;
//	private int GoldFind = 0;

	public AgEntity(LivingEntity entity) {
		this.entity = entity;
		uuid = entity.getUniqueId();
	}

	public AgEntity(AgEntity other) {
		this.entity = other.entity;
		this.uuid = other.uuid;
		this.combatDate = other.combatDate;

		this.level = other.level;
		this.energy = other.energy;

		this.hpRegen = other.hpRegen;
		this.energyRegen = other.energyRegen;
		this.defense = other.defense;
		this.magicRes = other.magicRes;
		this.block = other.block;
		this.critChance = other.critChance;

		this.strength = other.strength;
		this.dexterity = other.dexterity;
		this.intellect = other.intellect;
		this.vitality = other.vitality;
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

	public void inCombat() {
		combatDate = LocalDateTime.now();
	}

	public boolean isInCombat() {
		return combatDate != null && LocalDateTime.now().isBefore(combatDate.plusSeconds(10));
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public float getDefense() {
		return defense;
	}

	public void setDefense(float defense) {
		this.defense = Math.min(1, defense);
	}

	public float getMagicRes() {
		return magicRes;
	}

	public void setMagicRes(float magicRes) {
		this.magicRes = Math.min(1, magicRes);
	}

	public float getBlock() {
		return block;
	}

	public void setBlock(float block) {
		this.block = Math.min(1, block);
	}

	public float getCritChance() {
		return critChance;
	}

	public void setCritChance(float crit) {
		this.critChance = Math.min(1, crit);
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntellect() {
		return intellect;
	}

	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
}
