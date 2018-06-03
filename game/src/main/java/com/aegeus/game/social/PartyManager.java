package com.aegeus.game.social;

import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PartyManager {
	private static PartyManager instance;
	private List<Party> parties = Collections.synchronizedList(new ArrayList<>());
	private HashMap<AgPlayer, Party> invites = new HashMap<>();

	public static PartyManager getInstance() {
		return instance != null ? instance : (instance = new PartyManager());
	}

	public List<Party> getParties() {
		return parties;
	}

	public void registerParty(Party p) {
		parties.add(p);
	}

	public Party getPartyFromPlayer(AgPlayer player) {
		for (Party p : parties) {
			if (p.getMembers().contains(player)) return p;
		}
		return null;
	}

	public boolean isInParty(AgPlayer p) {
		return getPartyFromPlayer(p) != null;
	}

	/**
	 * Accept a party invite from a player and put them in the party
	 *
	 * @param player
	 * @return
	 */
	public boolean acceptInvite(AgPlayer player) {
		if (invites.containsKey(player)) {
			Party p = invites.remove(player);
			if (!p.isFull()) {
				p.add(player);
				p.sendMessage(Util.colorCodes("&f&l" + player.getPlayer().getDisplayName() + "&r&a has joined the party."), player, true);
				player.sendMessage(Util.colorCodes("&aYou have joined the party."));
				return true;
			} else {
				player.sendMessage(Util.colorCodes("&cThe party you are attempting to join is already full!"));
				return false;
			}
		}
		player.sendMessage(Util.colorCodes("&cYou do not have a pending party invite!"));
		return false;
	}

	public void declineInvite(AgPlayer player) {
		if (invites.containsKey(player)) {
			Party p = invites.remove(player);
			p.getLeader().sendMessage(Util.colorCodes("&f&l" + player.getPlayer().getDisplayName() + "&r&c has declined the party invite."));
			player.sendMessage(Util.colorCodes("&cYou declined the party invite."));
			return;
		}
		player.sendMessage(Util.colorCodes("&cYou do not have a pending party invite!"));
	}

	public void inviteToParty(AgPlayer inviter, AgPlayer invitee) {
		Party p = getPartyFromPlayer(inviter);
		if (!p.getLeader().equals(inviter))
			//not leader
			inviter.sendMessage(Util.colorCodes("&cYou are not the leader of the party!"));
		else if (inviter.equals(invitee))
			//trying to invite themself
			inviter.sendMessage(Util.colorCodes("&cYou cannot invite yourself!"));
		else if (invitee.isInParty())
			//in another party
			inviter.sendMessage(Util.colorCodes("&cThis player is already in a party!"));
		else if (invites.containsKey(invitee) && invites.get(invitee).equals(p))
			//already invited
			inviter.sendMessage(Util.colorCodes("&cThis player has already been invited to the party!"));
		else if (invites.containsKey(invitee) && !invites.get(invitee).equals(p))
			//invited to another party
			inviter.sendMessage(Util.colorCodes("&cThis player has already been invited to another party!"));
		else {
			invites.put(invitee, p);
			invitee.sendMessage(Util.colorCodes("&aYou have been invited to joined &f&l" + inviter.getPlayer().getDisplayName() + "&r&a's party." +
					"Use /p accept to join their party."));
			inviter.sendMessage(Util.colorCodes("&aYou invited &f&l" + invitee.getPlayer().getDisplayName() + "&r&a to join your party."));
		}
	}

	public void kickFromParty(AgPlayer kicker, AgPlayer target) {
		Party p = getPartyFromPlayer(kicker);
		if (!p.getLeader().equals(kicker))
			kicker.sendMessage(Util.colorCodes("&cYou are not the leader of the party!"));
		else if (!p.contains(target))
			kicker.sendMessage(Util.colorCodes("&cThat player is not in your party!"));
		else {
			p.remove(target);
		}
	}

	public void leaveParty(AgPlayer p) {
		for (Party party : parties) {
			if (party.contains(p)) {
				party.remove(p);
				p.sendMessage(Util.colorCodes("&cYou left the party."));
				return;
			}
		}
		p.sendMessage(Util.colorCodes("&cYou aren't in a party!"));
	}
}