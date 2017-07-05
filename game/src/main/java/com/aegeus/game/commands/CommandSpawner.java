package com.aegeus.game.commands;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import com.aegeus.game.stats.StatsT1Bandit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;

public class CommandSpawner implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.world")) return false;

		Player player = (Player) sender;
		Location loc = player.getTargetBlock(new HashSet<>(Arrays.asList(Material.AIR)), 100).getLocation();
		Spawner spawner = new Spawner(loc);
		spawner.add(new StatsT1Bandit());
		Aegeus.getInstance().addSpawner(spawner);

		//noinspection deprecation
		player.sendBlockChange(loc, Material.MOB_SPAWNER, (byte) 0);

		return true;
	}
}
