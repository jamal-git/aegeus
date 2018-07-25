package com.aegeus.game.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class AgProjectile extends AgEntity {
	private ItemStack item;

	public AgProjectile(Projectile projectile) {
		super(projectile);
	}

	public Projectile getProjectile() {
		return (Projectile) getEntity();
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}
}
