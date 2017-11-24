package com.aegeus.game.social;

import com.aegeus.game.dungeon.Dungeon;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Party {
	private final LinkedList<AgPlayer> members = new LinkedList<>();
	private Dungeon currentDungeon = null;

	public Party(AgPlayer p) {
		add(p);
	}

	public LinkedList<AgPlayer> getMembers() {
		return members;
	}

	public AgPlayer getLeader() {
		return members.peek();
	}

	public void setLeader(AgPlayer p) {
		if (getLeader().equals(p)) {
			p.sendMessage(Util.colorCodes("&cYou already are the party leader!"));
		}
		if (members.contains(p)) {
			AgPlayer leader = getLeader();
			members.remove(leader);
			members.addFirst(p);
			members.add(leader);

			leader.getPlayer().sendMessage(Util.colorCodes("&aYou have promoted &f&l" + p.getPlayer().getName() + "&r&a as the party leader."));
			p.getPlayer().sendMessage(Util.colorCodes("&aYou have been promoted to the &lPARTY LEADER&r&a."));
			sendMessage(Util.colorCodes("&f&l" + p.getPlayer().getDisplayName() + "&r&a has been promoted to party leader."), getLeader(), true);
		}
	}

	public void add(AgPlayer p) {
		if (!members.contains(p)) {
			members.add(p);
			update();
		}
	}

	public void remove(AgPlayer p) {
		if (members.contains(p)) {
			members.remove(p);
			if (members.size() > 0) {
				for (AgPlayer m : members) {
					GlowAPI.setGlowing(m.getPlayer(), false, p.getPlayer());
					if (p.getPlayer().isOnline())
						GlowAPI.setGlowing(p.getPlayer(), false, m.getPlayer());
					m.getPlayer().sendMessage(Util.colorCodes("&f&l" + p.getPlayer().getName() + "&a has left the party."));
				}
				getLeader().getPlayer().sendMessage(Util.colorCodes("&aYou are now the party leader."));
				sendMessage(Util.colorCodes("&f&l" + getLeader().getPlayer().getDisplayName() + "&r&a is now the leader."), getLeader(), true);
			}
		}
	}

	public boolean contains(AgPlayer p) {
		return members.contains(p);
	}

	public void sendMessage(String message, AgPlayer sender, boolean isCustom) {
		for (Player p : members.stream().map(AgPlayer::getPlayer).collect(Collectors.toList())) {
			if (!(sender != null && p.equals(sender.getPlayer())))
				if (!isCustom) p.sendMessage(Util.colorCodes("&d" + p.getDisplayName() + "&7: " + message));
				else p.sendMessage(Util.colorCodes(message));
		}
	}

	public boolean isFull() {
		return members.size() >= 4;
	}

	public boolean isInDungeon() {
		return currentDungeon != null;
	}

	public Dungeon getCurrentDungeon() {
		return currentDungeon;
	}

	public void setCurrentDungeon(Dungeon currentDungeon) {
		this.currentDungeon = currentDungeon;
	}

	public void update() {
		members.stream().map(AgPlayer::getPlayer).forEach(this::update);
	}


	public void update(Player p) {
		List<Player> list = members.stream().map(AgPlayer::getPlayer)
				.filter(a -> !a.equals(p)).collect(Collectors.toList());
		if (p.getHealth() <= p.getMaxHealth() * 0.25)
			GlowAPI.setGlowing(p, GlowAPI.Color.RED, list);
		else if (p.getHealth() <= p.getMaxHealth() * 0.5)
			GlowAPI.setGlowing(p, GlowAPI.Color.GOLD, list);
		else if (p.getHealth() <= p.getMaxHealth() * 0.75)
			GlowAPI.setGlowing(p, GlowAPI.Color.YELLOW, list);
		else
			GlowAPI.setGlowing(p, GlowAPI.Color.GREEN, list);
	}
}