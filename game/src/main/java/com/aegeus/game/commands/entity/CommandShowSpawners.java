package com.aegeus.game.commands.entity;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Silvre on 7/5/2017.
 */
public class CommandShowSpawners implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String st, String[] strings) {
		if (commandSender instanceof Player && strings.length == 0 && commandSender.hasPermission("aegeus.world")) {
			Player p = (Player) commandSender;
			for (Spawner s : Aegeus.getInstance().getSpawners())
				//noinspection deprecation
				p.sendBlockChange(s.getLocation(), Material.MOB_SPAWNER, (byte) 0);
			p.sendMessage(Util.colorCodes("&7Showing all spawners."));
			return true;
		}
		return false;
	}
}
