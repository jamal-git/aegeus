package com.aegeus.game.entity;

import com.aegeus.game.Alignment;
import com.aegeus.game.Division;
import com.aegeus.game.Legion;
import com.aegeus.game.social.ChatChannel;
import com.aegeus.game.social.Party;
import com.aegeus.game.social.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AgPlayer extends AgLiving {
	private final Player player;
	private BossBar hpBar;

	private int level = 0;
	private int xp = 0;
	private int soulpoints = 0;

	//temporary
    private int logins = 0;

	private Alignment alignment = Alignment.LAWFUL;
	private Division division = null;
	private Legion legion = Legion.FEROCIOUS;
	private Spawner editSpawner = null;
//	private Mount mount = null;
//	private Story story = new Story(this);
//	private BankListener bank = new BankListener(this);

	private Player replyTo;
	private ChatChannel chatChannel = ChatChannel.LOCAL;

	public AgPlayer(Player player) {
		super(player);
		this.player = player;
	}

	public AgPlayer(AgEntity info) {
		super(info);
		this.player = (Player) info.getEntity();
	}

	public AgPlayer(AgLiving info) {
		super(info);
		this.player = (Player) info.getEntity();
	}

	public AgPlayer(AgPlayer other) {
		super(other);
		this.player = other.player;
		this.hpBar = other.hpBar;

		this.level = other.level;
		this.xp = other.xp;
		this.soulpoints = other.soulpoints;

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

    public void sendMessage(String message) {
	    getPlayer().sendMessage(message);
    }

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getSoulpoints() {
		return soulpoints;
	}

	public void setSoulpoints(int soulpoints) {
		this.soulpoints = soulpoints;
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

    public Spawner getEditSpawner() {
        return editSpawner;
    }

    public void setEditSpawner(Spawner editSpawner) {
        this.editSpawner = editSpawner;
    }

    public boolean isInParty()  {
	    return PartyManager.getInstance().isInParty(this);
    }

    public Party getParty() {
	    return PartyManager.getInstance().getPartyFromPlayer(this);
    }

    public void addLogins(int i) {
	    logins += i;
    }

    public void setLogins(int i)    {
	    logins = i;
    }

    public int getLogins() {
        return logins;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof AgPlayer)) return false;
        else return getPlayer().equals(((AgPlayer) o).getPlayer());
    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + (hpBar != null ? hpBar.hashCode() : 0);
        result = 31 * result + level;
        result = 31 * result + xp;
        result = 31 * result + soulpoints;
        result = 31 * result + logins;
        result = 31 * result + (alignment != null ? alignment.hashCode() : 0);
        result = 31 * result + (division != null ? division.hashCode() : 0);
        result = 31 * result + (legion != null ? legion.hashCode() : 0);
        result = 31 * result + editSpawner.hashCode();
        result = 31 * result + (replyTo != null ? replyTo.hashCode() : 0);
        result = 31 * result + (chatChannel != null ? chatChannel.hashCode() : 0);
        return result;
    }
}
