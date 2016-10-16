package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.planets.Planet;
import com.aegeus.game.util.Utility;

public class CommandPlanet implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		String search = Utility.buildArgString(args, 0);
		Planet planet = Planet.getByName(search);
		if(planet == null) return false;
		Planet.warpPlayer(player, planet);

		return true;
	}

}
