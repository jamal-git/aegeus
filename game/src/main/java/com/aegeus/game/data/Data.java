package com.aegeus.game.data;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Data {
	private static Map<LivingEntity, AegeusEntity> entityData = new HashMap<>();

	public static AegeusEntity get(LivingEntity entity) {
		if (!entityData.containsKey(entity))
			entityData.put(entity, new AegeusEntity(entity));
		return entityData.get(entity);
	}

	public static AegeusPlayer get(Player player) {
		if (!entityData.containsKey(player))
			entityData.put(player, new AegeusPlayer(player));
		return (AegeusPlayer) entityData.get(player);
	}

	public static AegeusMonster get(Monster monster) {
		if (!entityData.containsKey(monster))
			entityData.put(monster, new AegeusMonster(monster));
		return (AegeusMonster) entityData.get(monster);
	}

	public static void remove(LivingEntity entity) {
		entityData.remove(entity);
	}
}
