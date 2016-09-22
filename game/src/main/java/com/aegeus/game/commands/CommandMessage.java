package com.aegeus.game.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.chat.ChatManager;
import com.aegeus.game.util.Helper;

public class CommandMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (args.length < 2) return false;
		
		Player player = (Player) sender;
		Player target = Bukkit.getPlayer(args[0]);
		
		if(player.equals(target)){
			player.sendMessage(Helper.colorCodes("&cDon't do that! That's weird!"));
			return true;
		}
		
		String msg = Helper.buildArgString(args, 1);
		
		ChatManager.sendPrivateMessage(player, target, msg.trim());
		return true;
	}

}
