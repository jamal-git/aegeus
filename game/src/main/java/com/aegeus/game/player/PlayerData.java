package com.aegeus.game.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.aegeus.game.chat.ChatChannel;
import com.aegeus.game.planets.Planet;
import com.aegeus.game.planets.Terminal;

public class PlayerData extends EntityData {
	
	private Player player;
	private BossBar bossBarHP;
	private Planet currentPlanet = new Terminal();
	
//	private AlignmentType Alignment = AlignmentType.LAWFUL;
//	private HorseType Horse = null;
	
	private Player replyTo;
	private ChatChannel chatChannel = ChatChannel.LOCAL;
//	private int gold = 0;
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
	public UUID getPlayerUUID() { return uuid; }

	public BossBar getBossBarHP() { return bossBarHP; }
	public void setBossBarHP(BossBar bossBarHP) { this.bossBarHP = bossBarHP; }

	public Planet getCurrentPlanet() { return currentPlanet; }
	public void setCurrentPlanet(Planet newPlanet) {
		if(currentPlanet != newPlanet) {
			currentPlanet = newPlanet;
			player.teleport(newPlanet.getLocation());
		}
	}
	
	public Player getReplyTo() { return replyTo; }
	public void setReplyTo(Player replyTo) { this.replyTo = replyTo; }
	
	public ChatChannel getChatChannel() { return chatChannel; }
	public void setChatChannel(ChatChannel chatChannel) { this.chatChannel = chatChannel; }
	
	public Inventory getBank() { return bank; }
	public void setBank(Inventory bank) { this.bank = bank; } 
	
	public boolean getBankWithdraw() { return isBankWithdraw; }
	public void setBankWithdraw(boolean isBankWithdraw) { this.isBankWithdraw = isBankWithdraw; }
	
	public static PlayerData get(Player player){
		if(!data.containsKey(player)){
			data.put(player, new PlayerData(player));
		}
		return (PlayerData) data.get(player);
	}
	
	public static void remove(Player player){
		PlayerData pd = get(player);
		pd.save();
		data.remove(player);
	}
	
	public void save() {
		
	}
	
}
