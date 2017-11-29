package com.aegeus.game.commands.entity;

import com.aegeus.game.commands.Executor;
import com.aegeus.game.stats.impl.Mob;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandMob implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return deny(sender, "You must be a player to execute this command.");
		if (!sender.hasPermission("aegeus.entity"))
			return deny(sender, "&cYou do not have permission to do that.");
		if (args.length < 1)
			return deny(sender, "&c/mob <type> [arguments]\n&fExample: /mob bandit 1");

		Player player = (Player) sender;
		String[] setup = Arrays.copyOfRange(args, 1, args.length);
		Mob mob = Mob.get(args[0], (Object[]) setup);

		if (mob == null)
			return deny(sender, "&cInvalid mob type.");

		mob.spawn(player.getLocation());

		return allow(sender, "&eSpawned " + mob.getId()
				+ (setup.length > 0 ? " with arguments " + Arrays.toString(setup) : ""));
	}

}
