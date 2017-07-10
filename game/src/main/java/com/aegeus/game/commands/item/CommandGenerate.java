package com.aegeus.game.commands.item;

import com.aegeus.game.stats.Stats;
import com.aegeus.game.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class CommandGenerate implements CommandExecutor {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.item")) return false;
		if (args.length < 2) return false;

		Player player = (Player) sender;
		Stats stats;
		String[] split = args[0].split(":");

		if (split.length >= 2)
			stats = Stats.fromName(split[0], split[1]);
		else
			stats = Stats.fromName(split[0]);

		if (stats == null) return false;
		else {
			float min = args.length >= 3 ? Float.parseFloat(args[2]) : 0;
			float max = args.length >= 4 ? Float.parseFloat(args[3]) : 1;
			float f = (ThreadLocalRandom.current().nextFloat() * (max - min)) + min;

			if (args[1].equalsIgnoreCase("helmet"))
				player.getInventory().addItem(stats.getHelmet().get(Util.rarity(f)).build());
			else if (args[1].equalsIgnoreCase("chestplate"))
				player.getInventory().addItem(stats.getChestplate().get(Util.rarity(f)).build());
			else if (args[1].equalsIgnoreCase("leggings"))
				player.getInventory().addItem(stats.getLeggings().get(Util.rarity(f)).build());
			else if (args[1].equalsIgnoreCase("boots"))
				player.getInventory().addItem(stats.getBoots().get(Util.rarity(f)).build());
			else if (args[1].equalsIgnoreCase("weapon"))
				player.getInventory().addItem(stats.getWeapon().get(Util.rarity(f)).build());
			else return false;
		}

		return true;
	}
}
