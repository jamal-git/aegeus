package com.aegeus.game.entity;

import com.aegeus.game.Alignment;
import com.aegeus.game.Division;
import com.aegeus.game.Legion;
import com.aegeus.game.chat.ChatChannel;
import com.aegeus.game.social.Party;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AgPlayer extends AgEntity {
	private final Player player;
	private BossBar hpBar;

	private int level = 0;
	private float energy = 100;

	private Alignment alignment = Alignment.LAWFUL;
	private Division division = null;
	private Legion legion = Legion.FEROCIOUS;
	private Party party = null;
	private Party invitedParty = null;
//	private Mount mount = null;
//	private Story story = new Story(this);
//	private BankListener bank = new BankListener(this);

	private Player replyTo;
	private ChatChannel chatChannel = ChatChannel.LOCAL;

	public AgPlayer(Player player) {
		super(player);
		this.player = player;
	}

	public AgPlayer(AgEntity info, Player player) {
		super(info);
		this.player = player;
	}

	public AgPlayer(AgPlayer other) {
		super(other);
		this.player = other.player;
		this.hpBar = other.hpBar;

		this.level = other.level;
		this.energy = other.energy;

		this.alignment = other.alignment;
		this.division = other.division;
		this.legion = other.legion;
		this.replyTo = other.replyTo;
		this.chatChannel = other.chatChannel;
	}

	public AgPlayer(UUID uuid) {
		super(Bukkit.getPlayer(uuid));
		this.player = Bukkit.getPlayer(uuid);
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

	public Alignment getAlignment() {
		return alignment;
	}
//	public void setAlignment(Alignment alignment) {
//		if(!alignment.equals(alignment)) {
//			this.alignment = alignment;
//			player.sendMessage(alignment.getDescription());
//			player.sendMessage("The first item in your hotbar has a **" + Math.round(alignment.getFirstItemChance()) + "** chance to drop.");
//			player.sendMessage("Each other item in your inventory have a **" + Math.round(alignment.getInventoryChance()) + "** chance to drop.");
//			player.sendMessage("Each item in your armor slots in has a **" + Math.round(alignment.getArmorChance()) + "** chance to drop.");
//		}
//	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

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

	public ChatChannel getChatChannel() {
		return chatChannel;
	}

	public void setChatChannel(ChatChannel chatChannel) {
		this.chatChannel = chatChannel;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party p) {
		party = p;
	}

	public Party getInvitedParty() {
		return invitedParty;
	}

	public void setInvitedParty(Party invitedParty) {
		this.invitedParty = invitedParty;
	}
}
