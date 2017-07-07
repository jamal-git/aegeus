package com.aegeus.game.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class AgProjectile {
	private final Projectile proj;
	private ItemStack firedWith;

	public AgProjectile(Projectile proj) {
		this.proj = proj;
	}

	public Projectile getProjectile() {
		return proj;
	}

	public ItemStack getFiredWith() {
		return firedWith;
	}

	public void setFiredWith(ItemStack firedWith) {
		this.firedWith = firedWith;
	}
}
