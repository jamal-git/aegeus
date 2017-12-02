package com.aegeus.game.commands;

import com.aegeus.game.util.Util;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface Executor extends CommandExecutor {
	String NOT_PLAYER = "You must be a player to execute this command.";
	String INVALID_PERMS = "&cYou do not have permission to use this command.";

	default boolean allow(CommandSender sender, String message) {
		sender.sendMessage(Util.colorCodes(message));
		return true;
	}

	default boolean deny(CommandSender sender, String message) {
		sender.sendMessage(Util.colorCodes(message));
		return false;
	}
}
