package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aegeus.game.mobs.Mob;
import com.aegeus.game.planets.Planet;

public class CommandCodex implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String type = args[0].toLowerCase();
		String search = args[1].toLowerCase();
		switch(type) {
			case "planet":
				Planet planet = Planet.Enum.byName(search).getPlanet();
				sender.sendMessage(planet.getName());
				sender.sendMessage(planet.getDescription());
				sender.sendMessage("Level required: " + planet.getLevelRequired());
				return true;
			case "mob":
				Mob mob = Mob.Enum.byName(search).getMob();
				sender.sendMessage(mob.getName());
				sender.sendMessage(mob.getDescription());
				sender.sendMessage("Stats: " + mob.getStats().getName());
				return true;
			default: return false;
		}
	}
}
