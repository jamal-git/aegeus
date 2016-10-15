package com.aegeus.game.data;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.aegeus.game.chat.Chat;
import com.aegeus.game.planets.Planet;

public class PlayerData extends EntityData {
	private Player player;
	private BossBar bossBarHp;
	private Planet currentPlanet = Planet.TERMINAL;
	
//	private Alignment alignment = Alignment.LAWFUL;
//	private Mount mount = null;
//	private Story story = new Story(this);
//	private Bank bank = new Bank(this);
	
	private Player replyTo;
	private Chat.Channel chatChannel = Chat.Channel.LOCAL;
	private Inventory bank;
	private boolean isBankWithdraw = false;
	
	public PlayerData(Player player) {
		super((LivingEntity) player);
		this.player = player;
	}
	
	public PlayerData(UUID uuid) {
		super((LivingEntity) Bukkit.getPlayer(uuid));
	}
	
	public Player getPlayer() { return player; }

	public BossBar getBossBarHp() { return bossBarHp; }
	public void setBossBarHp(BossBar bossBarHp) { this.bossBarHp = bossBarHp; }

	public Planet getCurrentPlanet() { return currentPlanet; }
	public void setCurrentPlanet(Planet newPlanet) {
		if(currentPlanet != newPlanet) {
			currentPlanet = newPlanet;
			player.teleport(newPlanet.getWorld().getSpawnLocation());
		}
	}
	
//	public Alignment getAlignment() { return alignment; }
//	public void setAlignment(Alignment alignment) {
//		if(!alignment.equals(alignment)) {
//			this.alignment = alignment;
//			player.sendMessage(alignment.getDescription());
//			player.sendMessage("The first item in your hotbar has a **" + Math.round(alignment.getFirstItemChance()) + "** chance to drop.");
//			player.sendMessage("Each other item in your inventory have a **" + Math.round(alignment.getInventoryChance()) + "** chance to drop.");
//			player.sendMessage("Each item in your armor slots in has a **" + Math.round(alignment.getArmorChance()) + "** chance to drop.");
//		}
//	}
	
	public Player getReplyTo() { return replyTo; }
	public void setReplyTo(Player replyTo) { this.replyTo = replyTo; }
	
	public Chat.Channel getChatChannel() { return chatChannel; }
	public void setChatChannel(Chat.Channel chatChannel) { this.chatChannel = chatChannel; }
	
	public Inventory getBank() { return bank; }
	public void setBank(Inventory bank) { this.bank = bank; } 
	
	public boolean getBankWithdraw() { return isBankWithdraw; }
	public void setBankWithdraw(boolean isBankWithdraw) { this.isBankWithdraw = isBankWithdraw; }
	
}
