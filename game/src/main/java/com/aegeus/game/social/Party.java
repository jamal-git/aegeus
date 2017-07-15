package com.aegeus.game.social;

import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Party {
	private final LinkedList<AgPlayer> members = new LinkedList<>();

	public Party(AgPlayer p) {
		add(p);
	}

	public AgPlayer getLeader() {
		return members.peekFirst();
	}

	public void setLeader(AgPlayer p) {
		if (members.contains(p)) {
			AgPlayer leader = getLeader();
			members.remove(leader);
			members.addFirst(p);
			members.add(leader);

			leader.getPlayer().sendMessage(Util.colorCodes("&aYou have promoted &l" + p.getPlayer().getName() + "&a to the party leader."));
			p.getPlayer().sendMessage(Util.colorCodes("&aYou have been promoted to the party leader."));

			members.stream().filter(m -> !m.equals(leader)).forEach(m -> m.getPlayer()
					.sendMessage(Util.colorCodes("&a&l" + p.getPlayer().getName() + "&a has been promoted to the party leader.")));
		}
	}

	public void add(AgPlayer p) {
		if (!members.contains(p)) {
			members.add(p);
			p.setParty(this);
			update();
		}
	}

	public void remove(AgPlayer p) {
		if (members.contains(p)) {
			members.remove(p);
			p.setParty(null);

			if (p.getPlayer().isOnline())
				p.getPlayer().sendMessage(Util.colorCodes("&aYou have left the party."));
            if(members.size() > 0) {
                for (AgPlayer m : members) {
                    GlowAPI.setGlowing(m.getPlayer(), false, p.getPlayer());
                    if (p.getPlayer().isOnline())
                        GlowAPI.setGlowing(p.getPlayer(), false, m.getPlayer());
                    m.getPlayer().sendMessage("&a&l" + p.getPlayer().getName() + "&a has left the party.");
                }
                getLeader().getPlayer().sendMessage(Util.colorCodes("&aYou have been promoted to the party leader."));
                members.stream().filter(m -> !m.equals(getLeader())).forEach(m -> m.getPlayer()
                        .sendMessage(Util.colorCodes("&a&l" + getLeader().getPlayer().getName() + "&a has been promoted to the party leader.")));
            }
		}
	}

	public boolean contains(AgPlayer p) {
		return members.contains(p);
	}

	public void sendMessage(String message, AgPlayer sender, boolean isCustom) {
		for (Player p : members.stream().map(AgPlayer::getPlayer).collect(Collectors.toList())) {
			if (!(sender != null && p.equals(sender.getPlayer())))
				if(!isCustom)   p.sendMessage(Util.colorCodes("&d" + p.getDisplayName() + "&7: " + message));
			    else p.sendMessage(Util.colorCodes(message));
		}
	}

	public List<AgPlayer> getPlayers()  {
	    return members;
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