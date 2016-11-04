package com.aegeus.game.commands;

import com.aegeus.game.mobs.Mob;
import com.aegeus.game.mobs.MobBandit;
import com.aegeus.game.mobs.Spawner;
import com.aegeus.game.stats.StatsBasic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawner implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(!sender.hasPermission("aegeus.world")) return false;

		Player player = (Player) sender;
		Location loc = player.getLocation().getBlock().getLocation();
		Mob mob = new MobBandit(new StatsBasic());
		Spawner.create(loc, mob, 3, 1, 1);
		player.sendBlockChange(loc, Material.MOB_SPAWNER, (byte) 0);

		return true;
	}
}
