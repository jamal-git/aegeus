package com.aegeus.game.player;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.LivingEntity;

public class EntityData {

	protected static HashMap<LivingEntity, EntityData> data = new HashMap<>();
	
	protected LivingEntity entity;
	protected UUID uuid;
	protected LocalDateTime combatDate = LocalDateTime.now().minusSeconds(15);
	protected LivingEntity lastHitBy;
	
	protected int level = 0;
	protected int hpRegen = 0;
	protected int energyRegen = 0;
	
//	private int Dex = 0;
//	private int Str = 0;
//	private int Int = 0;
//	private int Vit = 0;
//	
//	private int Def = 0;
//	private int MagRes = 0;
//	private int Thorns = 0;
//	private int GoldFind = 0;
	
	public EntityData(LivingEntity entity) {
		this.entity = entity;
		uuid = entity.getUniqueId();
	}
	
	public LocalDateTime getCombatDate() { return combatDate; }
	public void triggerCombat() { combatDate = LocalDateTime.now(); }
	public boolean isInCombat() { return (LocalDateTime.now().isAfter(combatDate.plusSeconds(15))) ? true : false; }
	
	public LivingEntity getLastHitBy() { return lastHitBy; }
	public void setLastHitBy(LivingEntity entity) { lastHitBy = entity; }
	
	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }
	
	public int getHPRegen() { return hpRegen; }
	public void setHPRegen(int hpRegen) { this.hpRegen = hpRegen; }
	
	public int getEnergyRegen()	{ return energyRegen; }
	public void setEnergyRegen(int energyRegen) { this.energyRegen = energyRegen; }
	
	
	public static EntityData get(LivingEntity entity){
		if(!data.containsKey(entity)){
			data.put(entity, new EntityData(entity));
		}
		return data.get(entity);
	}
	
	public static void remove(LivingEntity entity){
		data.remove(entity);
	}
	
}
