package com.aegeus.game.commands.entity;

import com.aegeus.game.stats.Stats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMob implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.entity")) return false;
		if (args.length < 1) return false;

		Player player = (Player) sender;
		Stats stats;
		String[] split = args[0].split(":");

		if (split.length >= 2)
			stats = Stats.fromName(split[0], split[1]);
		else
			stats = Stats.fromName(split[0]);

		if (stats == null) return false;
		else stats.spawn(player.getLocation());
		return true;
	}

}
