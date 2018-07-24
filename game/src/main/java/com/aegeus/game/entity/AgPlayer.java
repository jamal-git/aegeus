package com.aegeus.game.entity;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class AgPlayer extends AgLiving {
	private final Player player;
	private BossBar hpBar;

	private Player replyTo;

	public AgPlayer(Player player) {
		super(player);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public BossBar getHpBar() {
		return hpBar;
	}

	public void setHpBar(BossBar hpBar) {
		this.hpBar = hpBar;
	}

	public Player getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Player replyTo) {
		this.replyTo = replyTo;
	}
}
