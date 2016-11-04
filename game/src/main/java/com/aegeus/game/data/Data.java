package com.aegeus.game.data;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Data {
	
	private static Map<LivingEntity, EntityData> entityData = new HashMap<>();
	
	public static EntityData get(LivingEntity entity) {
		createData(entity);
		return entityData.get(entity);
	}
	
	public static void remove(LivingEntity entity) {
		entityData.remove(entity);
	}
	
	public static PlayerData getPlayerData(Player player) {
		createData(player);
		return (PlayerData) entityData.get(player);
	}
	
	public static MonsterData getMonsterData(LivingEntity entity) {
		createData(entity);
		return (MonsterData) entityData.get(entity);
	}
	
	private static void createData(LivingEntity entity) {
		if(!entityData.containsKey(entity))
			if(entity.getType().getClass().isAssignableFrom(Monster.class))
				entityData.put(entity, new MonsterData((Monster) entity));
			else if (entity.getType().equals(EntityType.PLAYER))
				entityData.put(entity, new PlayerData((Player) entity));
			else
				entityData.put(entity, new EntityData(entity));
	}
	
}
