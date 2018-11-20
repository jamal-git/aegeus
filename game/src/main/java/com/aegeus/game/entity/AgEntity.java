package com.aegeus.game.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class AgEntity {
	protected UUID uuid;
	private String name = "";

	public AgEntity(Entity entity) {
		uuid = entity.getUniqueId();
	}

	public Entity getEntity() {
		return Bukkit.getEntity(uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
