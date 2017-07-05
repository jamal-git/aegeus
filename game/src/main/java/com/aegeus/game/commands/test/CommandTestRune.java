package com.aegeus.game.commands.test;

import com.aegeus.game.item.tool.Rune;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTestRune implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.test")) return false;
		if (args.length < 1) return false;

		Player player = (Player) sender;
		Rune rune = new Rune(Rune.RuneType.fromId(Integer.parseInt(args[0])));
		player.getInventory().addItem(rune.build());

		return true;
	}
}
