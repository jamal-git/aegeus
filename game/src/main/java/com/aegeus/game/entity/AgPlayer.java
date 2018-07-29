package com.aegeus.game.entity;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AgPlayer extends AgLiving {
	private BossBar healthBar;
	private LocalTime lastAttack;

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

	public LocalTime getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(LocalTime lastAttack) {
		this.lastAttack = lastAttack;
	}

	public boolean attack() {
		if (lastAttack == null || LocalTime.now().isAfter(lastAttack.plus(400, ChronoUnit.MILLIS))) {
			lastAttack = LocalTime.now();
			return true;
		}
		return false;
	}
}
