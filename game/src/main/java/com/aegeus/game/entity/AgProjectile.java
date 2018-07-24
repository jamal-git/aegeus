package com.aegeus.game.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class AgProjectile extends AgEntity {
	private final Projectile projectile;
	private ItemStack launcher;

	public AgProjectile(Projectile projectile) {
		super(projectile);
		this.projectile = projectile;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public ItemStack getLauncher() {
		return launcher;
	}

	public void setLauncher(ItemStack launcher) {
		this.launcher = launcher;
	}
}
