package com.aegeus.game.commands;

import com.aegeus.game.chat.ChatManager;
import com.aegeus.game.util.Util;
import com.aegeus.game.util.exceptions.NoneNearbyException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class CommandRoll implements CommandExecutor {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int max = 100;
		if (args.length >= 1) {
			try {
				max = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				return false;
			}
		}

		max = Math.min(100000, Math.max(1, max));
		int roll = random.nextInt(max);
		sender.sendMessage(Util.colorCodes("&7You rolled a(n) &f" + roll + "&7 out of &f" + max + "&7."));

		if (sender instanceof Player) {
			Player player = (Player) sender;
			try {
				ChatManager.sendRadial(player, Util.colorCodes(
						"&7" + player.getDisplayName() + " rolled a &f" + roll + "&7 out of &f" + max + "&7."));
			} catch (NoneNearbyException e) {
				player.sendMessage(Util.colorCodes(
						"&7&oThe sound of your die rolling echoes in the wind."));
			}
		}
		return true;
	}

}
