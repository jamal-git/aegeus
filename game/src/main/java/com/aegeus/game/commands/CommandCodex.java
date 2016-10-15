package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.aegeus.game.mobs.Mob;
import com.aegeus.game.planets.Planet;

public class CommandCodex implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 2) return false;
		String type = args[0].toLowerCase();
		String search = args[1].toLowerCase();
		switch(type) {
			case "planet":
				Planet planet = Planet.getByName(search);
				if(planet == null) return false;
				sender.sendMessage(planet.getName());
				sender.sendMessage("&o" + planet.getDescription());
				sender.sendMessage("Level required: " + planet.getLevelRequired());
				return true;
			case "mob":
				Mob mob = Mob.Premade.byName(search).getMob();
				if(mob == null) return false;
				sender.sendMessage(mob.getName());
				sender.sendMessage("&o" + mob.getDescription());
				return true;
			default: return false;
		}
	}
}
