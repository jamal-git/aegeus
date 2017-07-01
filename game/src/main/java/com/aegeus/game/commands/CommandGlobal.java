package com.aegeus.game.commands;

import com.aegeus.game.chat.ChatManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGlobal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (args.length < 1) return false;

		Player player = (Player) sender;
		String msg = StringUtils.join(args, " ");

		ChatManager.sendAuto(player, msg.trim());

		return true;
	}

}
