package com.aegeus.game.commands.test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.mobs.Mob;
import com.aegeus.game.mobs.MobBandit;
import com.aegeus.game.stats.StatsBasic;

public class CommandTestMob implements CommandExecutor {

	@Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		Mob mob = new MobBandit(new StatsBasic());
		mob.create(player.getWorld(), player.getLocation());
		
		return true;
	}

}
