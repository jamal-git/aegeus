package com.aegeus.game.commands.test;

import com.aegeus.game.item.tool.FishRod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTestRod implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.test")) return false;

		Player player = (Player) sender;
		FishRod rod = new FishRod();

		player.getInventory().addItem(rod.build());

		return true;
	}

}
