package com.aegeus.game.commands.test;

import com.aegeus.game.stats.StatsT1Bandit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTestMob implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.test")) return false;

		Player player = (Player) sender;
		new StatsT1Bandit().spawn(player.getLocation());

		return true;
	}

}
