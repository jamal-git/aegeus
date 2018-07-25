package com.aegeus.game.entity;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class AgPlayer extends AgLiving {
	private BossBar healthBar;

	public AgPlayer(Player player) {
		super(player);
	}

	public Player getPlayer() {
		return (Player) getEntity();
	}

	public BossBar getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(BossBar healthBar) {
		this.healthBar = healthBar;
	}
}
