package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

public class CommandSpaceCart implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {
		if(!(sender instanceof Player))	return false;
		Player p =(Player) sender;
		p.getWorld().spawn(p.getLocation(), Minecart.class);
		return false;
	}

}
