package com.aegeus.game.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class AgProjectile extends AgEntity {
	private final Projectile projectile;
	private ItemStack firedWith;

	public AgProjectile(Projectile projectile) {
		super(projectile);
		this.projectile = projectile;
	}

	public AgProjectile(AgEntity info) {
		super(info);
		this.projectile = (Projectile) info.getEntity();
	}

	public AgProjectile(AgProjectile other) {
		super(other);
		this.projectile = other.projectile;
		this.firedWith = other.firedWith;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public ItemStack getFiredWith() {
		return firedWith;
	}

	public void setFiredWith(ItemStack firedWith) {
		this.firedWith = firedWith;
	}
}
