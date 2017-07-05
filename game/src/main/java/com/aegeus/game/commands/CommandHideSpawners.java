package com.aegeus.game.commands;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Silvre on 7/5/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class CommandHideSpawners implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String st, String[] strings) {
		if (commandSender instanceof Player && strings.length == 0 && commandSender.hasPermission("aegeus.world")) {
			Player p = (Player) commandSender;
			for (Spawner s : Aegeus.getInstance().getSpawners())
				//noinspection deprecation
				p.sendBlockChange(s.getLocation(), Material.AIR, (byte) 0);
			return true;
		}
		return false;
	}
}
