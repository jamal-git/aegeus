package com.aegeus.game.commands;

import com.aegeus.game.chat.ChatManager;
import com.aegeus.game.util.Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (args.length < 2) return false;

		Player player = (Player) sender;
		Player target = Bukkit.getPlayer(args[0]);

		if (target == null)
			player.sendMessage(Util.colorCodes("&cThat user is either offline or does not exist."));
		else if (player.equals(target)) {
			player.sendMessage(Util.colorCodes("&cDon't do that! That's weird!"));
		} else {
			String msg = StringUtils.join(args, " ");
			ChatManager.sendPrivateMessage(player, target, msg.trim());
		}

		return true;
	}

}
