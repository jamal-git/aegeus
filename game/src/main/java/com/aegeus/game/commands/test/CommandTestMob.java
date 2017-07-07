package com.aegeus.game.commands.test;

import com.aegeus.game.stats.Stats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class CommandTestMob implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.test")) return false;

		Player player = (Player) sender;
		try {
			String[] split = args[0].split(":");
			Class clazz = Class.forName("com.aegeus.game.stats." + split[0]);
			if (split.length >= 2) {
				Class inherit = Class.forName("com.aegeus.game.stats." + split[1]);
				((Stats) (clazz.getConstructor(Stats.class).newInstance((Stats) inherit.newInstance()))).spawn(player.getLocation(), null);
			} else
				((Stats) clazz.newInstance()).spawn(player.getLocation(), null);
		} catch (InstantiationException | InvocationTargetException | NoSuchMethodException
				| IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return true;
	}

}
