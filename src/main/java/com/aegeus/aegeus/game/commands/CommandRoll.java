package com.aegeus.aegeus.game.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.aegeus.game.chat.ChatMethods;
import com.aegeus.aegeus.util.Helper;
import com.aegeus.aegeus.util.exceptions.NoneNearbyException;

public class CommandRoll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int max = 100;
		if (args.length >= 1) {
			try { max = Integer.parseInt(args[0]); }
			catch (NumberFormatException e) { return false; }
		}
		
		Random random = new Random();
		int roll = random.nextInt(max);
		sender.sendMessage(Helper.colorCodes(
				"&7You rolled a &f" + roll + "&7 out of &f" + max + "&7."));
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			try {
				ChatMethods.sendRadialChat(player, Helper.colorCodes(
						"&f" + player.getDisplayName() + "&7 rolled a &f" + roll + "&7 out of &f" + max + "&7."));
			} catch (NoneNearbyException e){
				player.sendMessage("" + ChatColor.GRAY + ChatColor.ITALIC + "The sound of your dice rolling echoes in the lonely wind.");
			}
		}
		return true;
	}

}
