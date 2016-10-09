package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.planets.Planet;
import com.aegeus.game.planets.PlanetManager;

public class CommandPlanet implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		String planetget = args[0];
		
		if(planetget.equalsIgnoreCase("terminal")) {
			PlanetManager.warpPlayer(player, Planet.Enum.TERMINAL);
		} else if(planetget.equalsIgnoreCase("xylo")) {
			PlanetManager.warpPlayer(player, Planet.Enum.XYLO);
		} else {
			return false;
		}

		return true;
	}

}
