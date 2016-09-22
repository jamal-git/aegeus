package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.chat.ChatChannel;
import com.aegeus.game.player.PlayerData;
import com.aegeus.game.util.Helper;

public class CommandChatChannel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		
		if (args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("local")) {
			PlayerData.get(player).setChatChannel(ChatChannel.LOCAL);
			player.sendMessage(Helper.colorCodes(
					"Chat channel set to Local."));
			return true;
		} else if (args[0].equalsIgnoreCase("gl") || args[0].equalsIgnoreCase("global")) {
			PlayerData.get(player).setChatChannel(ChatChannel.GLOBAL);
			player.sendMessage(Helper.colorCodes(
					"Chat channel set to Global."));
			return true;
		}
		return false;
	}

}
