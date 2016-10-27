package com.aegeus.game.data;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.entity.LivingEntity;

public class EntityData {
	protected LivingEntity entity;
	protected UUID uuid;
	protected LocalDateTime combatDate = LocalDateTime.now().minusSeconds(15);
	protected LivingEntity lastHitBy;

	protected int level = 0;
	protected int hpRegen = 0;
	protected float energyRegen = 0;
	protected float defense = 0;
	protected float magicRes = 0;
	protected float block = 0;
	
//	private int eDex = 0;
	private int eStr = 0;
	private int eInt = 0;
	private int eVit = 0;

//	private int Thorns = 0;
//	private int GoldFind = 0;

	protected EntityData(LivingEntity entity) {
		this.entity = entity;
		uuid = entity.getUniqueId();
	}
	
	public LivingEntity getEntity() { return entity; }
	public UUID getUUID() { return uuid; }
	
	public LocalDateTime getCombatDate() { return combatDate; }
	public void triggerCombat() { combatDate = LocalDateTime.now(); }
	public boolean isInCombat() { return (LocalDateTime.now().isAfter(combatDate.plusSeconds(15))); }
	
	public LivingEntity getLastHitBy() { return lastHitBy; }
	public void setLastHitBy(LivingEntity entity) { lastHitBy = entity; }
	
	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }
	
	public int getHpRegen() { return hpRegen; }
	public void setHpRegen(int hpRegen) { this.hpRegen = hpRegen; }
	
	public float getEnergyRegen() { return energyRegen; }
	public void setEnergyRegen(float energyRegen) { this.energyRegen = energyRegen; }

	public float getDefense() { return defense; }
	public void setDefense(float defense) { this.defense = defense; }

	public float getMagicRes() { return magicRes; }
	public void setMagicRes(float magicRes) { this.magicRes = magicRes; }

	public float getBlock() { return block; }
	public void setBlock(float block) { this.block = block; }

	public void setStr(int eStr) { this.eStr = eStr; }
	public int getStr() { return eStr; }
	public void setInt(int eInt) { this.eInt = eInt; }
	public int getInt() { return eInt; }
	public void setVit(int eVit) { this.eVit = eVit; }
	public int getVit() { return eVit; }

}
