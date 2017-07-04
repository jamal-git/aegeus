package com.aegeus.game.commands.test;

import com.aegeus.game.item.tool.ArmorEnchant;
import com.aegeus.game.item.tool.WeaponEnchant;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTestEnchant implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.test")) return false;
		if (args.length < 2) return false;

		Player player = (Player) sender;
		String type = args[0];
		int tier = Integer.parseInt(args[1]);

		if (type.equalsIgnoreCase("armor"))
			player.getInventory().addItem(new ArmorEnchant(tier).build());
		else if (type.equalsIgnoreCase("weapon"))
			player.getInventory().addItem(new WeaponEnchant(tier).build());

		return true;
	}
}
