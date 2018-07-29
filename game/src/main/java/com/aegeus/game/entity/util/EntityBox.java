package com.aegeus.game.entity.util;

import com.aegeus.game.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

import java.util.ArrayList;

public class EntityBox extends ArrayList<AgEntity> {
	public AgEntity get(Entity entity) {
		return stream().filter(e -> e.getEntity().equals(entity))
				.findAny().orElseGet(() -> addEntity(resolve(entity)));
	}

	public AgLiving getLiving(LivingEntity entity) {
		return (AgLiving) get(entity);
	}

	public AgPlayer getPlayer(Player player) {
		return (AgPlayer) get(player);
	}

	public AgMonster getMonster(LivingEntity entity) {
		if (!(get(entity) instanceof AgMonster))
			add(new AgMonster(entity));
		return (AgMonster) get(entity);
	}

	public AgProjectile getProjectile(Projectile projectile) {
		return (AgProjectile) get(projectile);
	}

	public AgEntity addEntity(AgEntity entity) {
		if (contains(entity))
			set(indexOf(entity), entity);
		else
			add(entity);
		return entity;
	}

	public AgEntity resolve(Entity entity) {
		if (entity instanceof Player)
			return new AgPlayer((Player) entity);
		else if (entity instanceof Projectile)
			return new AgProjectile((Projectile) entity);
		else if (entity instanceof LivingEntity)
			return new AgLiving((LivingEntity) entity);
		return new AgEntity(entity);
	}

	public boolean contains(Entity entity) {
		return stream().map(AgEntity::getEntity).filter(e -> e.equals(entity)).count() >= 1;
	}
}