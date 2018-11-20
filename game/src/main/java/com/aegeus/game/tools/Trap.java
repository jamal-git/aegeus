package com.aegeus.game.tools;

import com.aegeus.game.entity.AgLiving;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Trap {
	private final Location loc;
	private AgLiving entity = null;
	private int time;

	public Trap(Location loc, int time) {
		this.loc = loc;
		this.time = time;
	}

	public Location getLoc() {
		return loc;
	}

	public Block getBlock() {
		return loc.getBlock();
	}

	public World getWorld() {
		return loc.getWorld();
	}

	public AgLiving getEntity() {
		return entity;
	}

	public void setEntity(AgLiving entity) {
		this.entity = entity;
	}

	public boolean hasEntity() {
		return entity != null;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = Math.max(0, time);
	}

	public List<LivingEntity> getNearby() {
		return loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5).stream()
				.filter(e -> e instanceof LivingEntity)
				.map(e -> (LivingEntity) e)
				.collect(Collectors.toList());
	}
}
