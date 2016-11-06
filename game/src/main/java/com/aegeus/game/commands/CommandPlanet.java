package com.aegeus.game.commands;

import com.aegeus.game.Planet;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPlanet implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.world")) return false;
		if (args.length < 1) return false;

		Player player = (Player) sender;
		String search = StringUtils.join(args, " ");
		Planet planet = Planet.getByName(search);
		if (planet == null) return false;
		Planet.warpPlayer(player, planet);

		return true;
	}

}
