package com.aegeus.game.commands.item;

import com.aegeus.game.commands.Executor;
import com.aegeus.game.stats.impl.Tier;
import com.aegeus.game.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGenerate implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return deny(sender, Executor.NOT_PLAYER);
		if (!sender.hasPermission("aegeus.entity")) return deny(sender, Executor.INVALID_PERMS);
		if (args.length < 2) return deny(sender, "&c/generate <tier> <type> [min] [max]\n&fExample: /generate 3 chestplate");

		Player player = (Player) sender;
		Tier tier = Tier.get(Integer.parseInt(args[0]));

		float min = args.length >= 3 ? Float.parseFloat(args[2]) : 0;
		float max = args.length >= 4 ? Float.parseFloat(args[3]) : 1;
		float f = Util.rFloat(min, max);

		if (args[1].equalsIgnoreCase("helmet"))
			player.getInventory().addItem(tier.getHelmet().get(Util.rarity(f)).build());
		else if (args[1].equalsIgnoreCase("chestplate"))
			player.getInventory().addItem(tier.getChestplate().get(Util.rarity(f)).build());
		else if (args[1].equalsIgnoreCase("leggings"))
			player.getInventory().addItem(tier.getLeggings().get(Util.rarity(f)).build());
		else if (args[1].equalsIgnoreCase("boots"))
			player.getInventory().addItem(tier.getBoots().get(Util.rarity(f)).build());
		else if (args[1].equalsIgnoreCase("weapon"))
			player.getInventory().addItem(tier.getWeapon().get(Util.rarity(f)).build());
		else return false;

		return true;
	}
}
