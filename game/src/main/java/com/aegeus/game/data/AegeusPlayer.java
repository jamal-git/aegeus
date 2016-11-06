package com.aegeus.game.data;

import com.aegeus.game.Division;
import com.aegeus.game.Legion;
import com.aegeus.game.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class AegeusPlayer extends AegeusEntity {
	private Player player;
	private BossBar bossBarHp;
	private boolean combatLog = false;

	//	private Alignment alignment = Alignment.LAWFUL;
	private Division division = null;
	private Legion legion = null;
//	private Mount mount = null;
//	private Story story = new Story(this);
//	private Bank bank = new Bank(this);

	private Player replyTo;
	private Chat.Channel chatChannel = Chat.Channel.LOCAL;
	private Inventory bank;
	private boolean isBankWithdraw = false;

	protected AegeusPlayer(Player player) {
		super(player);
		this.player = player;
	}

	public AegeusPlayer(UUID uuid) {
		super(Bukkit.getPlayer(uuid));
	}

	public Player getPlayer() {
		return player;
	}

	public BossBar getBossBarHp() {
		return bossBarHp;
	}

	public void setBossBarHp(BossBar bossBarHp) {
		this.bossBarHp = bossBarHp;
	}

	public boolean isCombatLog() {
		return combatLog;
	}

	public void setCombatLog(boolean combatLog) {
		this.combatLog = combatLog;
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

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Legion getLegion() {
		return legion;
	}

	public void setLegion(Legion legion) {
		this.legion = legion;
	}

	public Player getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Player replyTo) {
		this.replyTo = replyTo;
	}

	public Chat.Channel getChatChannel() {
		return chatChannel;
	}

	public void setChatChannel(Chat.Channel chatChannel) {
		this.chatChannel = chatChannel;
	}

	public Inventory getBank() {
		return bank;
	}

	//	public void setBank(Bank bank) { this.bank = bank; }
	public void setBank(Inventory bank) {
		this.bank = bank;
	}

	public boolean getBankWithdraw() {
		return isBankWithdraw;
	}

	public void setBankWithdraw(boolean isBankWithdraw) {
		this.isBankWithdraw = isBankWithdraw;
	}
}
