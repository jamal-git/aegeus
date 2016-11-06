package com.aegeus.game.data;

import com.aegeus.game.Planet;
import org.bukkit.entity.LivingEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AegeusEntity {
	protected LivingEntity entity;
	protected UUID uuid;
	protected LocalDateTime combatDate;
	protected LivingEntity lastHitBy;
	protected Planet currentPlanet = Planet.TERMINAL;

	protected int level = 0;
	protected int hpRegen = 0;
	protected float energyRegen = 0;
	protected int energy = 0;
	protected float defense = 0;
	protected float magicRes = 0;
	protected float block = 0;

	protected int strength = 0;
	protected int intelligence = 0;
	protected int vitality = 0;
	protected int dexterity = 0;

//	private int Thorns = 0;
//	private int GoldFind = 0;

	protected AegeusEntity(LivingEntity entity) {
		this.entity = entity;
		uuid = entity.getUniqueId();
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
		return combatDate == null || LocalDateTime.now().isAfter(combatDate.plusSeconds(15));
	}

	public LivingEntity getLastHitBy() {
		return lastHitBy;
	}

	public void setLastHitBy(LivingEntity entity) {
		lastHitBy = entity;
	}

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public void setCurrentPlanet(Planet newPlanet) {
		if (currentPlanet != newPlanet) {
			currentPlanet = newPlanet;
			entity.teleport(newPlanet.getWorld().getSpawnLocation());
		}
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
		this.defense = defense;
	}

	public float getMagicRes() {
		return magicRes;
	}

	public void setMagicRes(float magicRes) {
		this.magicRes = magicRes;
	}

	public float getBlock() {
		return block;
	}

	public void setBlock(float block) {
		this.block = block;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
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
