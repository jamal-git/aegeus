package com.aegeus.game.chat;

import com.aegeus.game.Aegeus;
import com.aegeus.game.util.Util;
import com.aegeus.game.util.exceptions.NoneNearbyException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatManager {
	/**
	 * Sends a global, trade, or recruit message depending on keywords.
	 *
	 * @param sender  The sender.
	 * @param content The content.
	 */
	public static void sendAuto(Player sender, String content) {
		if (content.contains("WTB") || content.contains("WTS") || content.contains("WTT") ||
				content.contains("Buying") || content.contains("Selling") || content.contains("Trading"))
			sendTrade(sender, content);
		else if (content.contains("WTR") || content.contains("Recruiting"))
			sendRecruit(sender, content);
		else
			sendGlobal(sender, content);
	}

	/**
	 * Sends a local message.
	 *
	 * @param sender  The sender.
	 * @param content The content.
	 */
	public static void sendLocal(Player sender, String content) {
		try {
			sendRadial(sender, Util.colorCodes("&7" + sender.getDisplayName() + ":&f " + content));
		} catch (NoneNearbyException e) {
			sender.sendMessage(Util.colorCodes(
					"&7&oYour voice echoes in the wind."));
		}
	}

	/**
	 * Sends a global message.
	 *
	 * @param sender  The sender.
	 * @param content The content.
	 */
	public static void sendGlobal(Player sender, String content) {
		Bukkit.broadcastMessage(Util.colorCodes(
				"&b(&lG&b)&7 " + sender.getDisplayName() + ":&f " + Util.colorCodes(content)));
	}

	/**
	 * Sends a trade message.
	 *
	 * @param sender  The sender.
	 * @param content The content.
	 */
	public static void sendTrade(Player sender, String content) {
		Bukkit.broadcastMessage(Util.colorCodes(
				"&a(&lT&a)&7 " + sender.getDisplayName() + ":&f " + Util.colorCodes(content)));
	}

	/**
	 * Sends a recruit message.
	 *
	 * @param sender  The sender.
	 * @param content The content.
	 */
	public static void sendRecruit(Player sender, String content) {
		Bukkit.broadcastMessage(Util.colorCodes(
				"&c(&lR&c)&7 " + sender.getDisplayName() + ":&f " + Util.colorCodes(content)));
	}

	/**
	 * Sends a broadcast.
	 *
	 * @param content The content.
	 */
	public static void sendBroadcast(String content) {
		Bukkit.broadcastMessage(Util.colorCodes("&b&l>>&b " + content));
	}

	/**
	 * Sends a message within a radius of a location.
	 *
	 * @param sender  The sender.
	 * @param content The content.
	 * @throws NoneNearbyException No players are nearby.
	 */
	public static void sendRadial(Player sender, String content) throws NoneNearbyException {
		sender.sendMessage(Util.colorCodes(content));
		List<Entity> entities = sender.getNearbyEntities(35, 35, 35);
		if (entities.isEmpty()) throw new NoneNearbyException();
		sender.getNearbyEntities(35, 35, 35).stream().filter(e -> e instanceof Player && !e.equals(sender))
				.forEach(e -> e.sendMessage(Util.colorCodes(content)));
	}

	/**
	 * Sends a private message to a player.
	 *
	 * @param sender  The sender.
	 * @param target  The target.
	 * @param content The content.
	 */
	public static void sendPrivateMessage(Player sender, Player target, String content) {
		if (!target.isOnline())
			sender.sendMessage(Util.colorCodes("&c&l" + target.getDisplayName() + "&c is currently offline."));
		else {
			sender.sendMessage(Util.colorCodes("&7&lTO&f " + target.getDisplayName() + "&7:&f " + content.trim()));
			target.sendMessage(Util.colorCodes("&7&lFROM&f " + sender.getDisplayName() + "&7:&f " + content.trim()));
			Aegeus.getInstance().getPlayer(target).setReplyTo(sender);
		}
	}

}
