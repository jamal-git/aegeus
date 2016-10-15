package com.aegeus.game.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class Data {
	
	private static Map<LivingEntity, EntityData> map = new HashMap<>();
	
	public static EntityData get(LivingEntity entity) {
		createData(entity);
		return map.get(entity);
	}
	
	public static void remove(LivingEntity entity) {
		map.remove(entity);
	}
	
	public static PlayerData getPlayerData(Player player) {
		createData(player);
		return (PlayerData) map.get(player);
	}
	
	public static MonsterData getMonsterData(LivingEntity entity) {
		createData(entity);
		return (MonsterData) map.get(entity);
	}
	
	private static void createData(LivingEntity entity) {
		if(!map.containsKey(entity))
			if(entity.getType().getClass().isAssignableFrom(Monster.class))
				map.put(entity, new MonsterData((Monster) entity));
			else if (entity.getType().equals(EntityType.PLAYER))
				map.put(entity, new PlayerData((Player) entity));
			else
				map.put(entity, new EntityData(entity));
	}
	
}
