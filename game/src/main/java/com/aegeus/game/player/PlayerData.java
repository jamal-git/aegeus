package com.aegeus.game.player;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.aegeus.game.chat.ChatChannel;
import com.aegeus.game.planets.Planet;
import com.aegeus.game.planets.Terminal;

public class PlayerData {
	
	private static Map<Player, PlayerData> data = new HashMap<>();
	
	private BossBar bossBarHP;
	private Planet currentPlanet = new Terminal();
	private UUID uuid;
	
//	private AlignmentType Alignment = AlignmentType.LAWFUL;
//	private HorseType Horse = null;
	
	private LocalDateTime inCombat = LocalDateTime.now().minusSeconds(15);
//	private LocalDateTime lastAttack = LocalDateTime.now();
	private Player replyTo;
	private ChatChannel chatChannel = ChatChannel.LOCAL;
	private int level = 0;
	private int hpRegen = 0;
	private int energyRegen = 0;
//	private int gold = 0;
	private Inventory bank;
	private boolean isBankWithdraw = false;
	
//	private int Dex = 0;
//	private int Str = 0;
//	private int Int = 0;
//	private int Vit = 0;
//	
//	private int Def = 0;
//	private int MagRes = 0;
//	private int Thorns = 0;
//	private int GoldFind = 0;
	
	public BossBar getBossBarHP() { return bossBarHP; }
	public void setBossBarHP(BossBar bossBarHP) { this.bossBarHP = bossBarHP; }

	public Planet getCurrentPlanet() { return currentPlanet; }
	public void setCurrentPlanet(Planet currentPlanet) { this.currentPlanet = currentPlanet; }
	
	public LocalDateTime getInCombat() { return inCombat; }
	public void setInCombat(LocalDateTime inCombat) { this.inCombat = inCombat; }
	
	public Player getReplyTo() { return replyTo; }
	public void setReplyTo(Player replyTo) { this.replyTo = replyTo; }
	
	public ChatChannel getChatChannel() { return chatChannel; }
	public void setChatChannel(ChatChannel chatChannel) { this.chatChannel = chatChannel; }
	
	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }
	
	public int getHPRegen() { return hpRegen; }
	public void setHPRegen(int hpRegen) { this.hpRegen = hpRegen; }
	
	public int getEnergyRegen()	{ return energyRegen; }
	public void setEnergyRegen(int energyRegen) { this.energyRegen = energyRegen; }
	
	public Inventory getBank() { return bank; }
	public void setBank(Inventory bank) { this.bank = bank; } 
	
	public boolean getBankWithdraw() { return isBankWithdraw; }
	public void setBankWithdraw(boolean isBankWithdraw) { this.isBankWithdraw = isBankWithdraw; }
	
	public static PlayerData get(Player player){
		if(!data.containsKey(player)){
			// TODO Load from database
			data.put(player, new PlayerData());
		}
		return data.get(player);
	}
	
	public static void remove(Player player){
		if(data.containsKey(player)){
			// TODO Save to database
			data.remove(player);
		}
	}
	
}
