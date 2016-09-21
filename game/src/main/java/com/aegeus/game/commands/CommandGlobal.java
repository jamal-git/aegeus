package com.aegeus.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.aegeus.game.chat.ChatManager;
import com.aegeus.aegeus.util.Helper;

public class CommandGlobal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		String msg = Helper.buildArgString(args, 0);
		
		ChatManager.sendAutoChat(player, msg.trim());
		
		return true;
	}

}
