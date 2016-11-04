package com.aegeus.game.commands;

import com.aegeus.game.chat.ChatManager;
import com.aegeus.game.util.Utility;
import com.aegeus.game.util.exceptions.NoneNearbyException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandRoll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int max = 100;
		if(args.length >= 1) {
			try { max = Integer.parseInt(args[0]); }
			catch (NumberFormatException e) { return false; }
		}
		
		if(max > 100000) max = 100000;
		
		Random random = new Random();
		int roll = random.nextInt(max);
		sender.sendMessage(Utility.colorCodes(
				"&7You rolled a &f" + roll + "&7 out of &f" + max + "&7."));
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			try {
				ChatManager.sendRadialChat(player, Utility.colorCodes(
						"&7" + player.getDisplayName() + " rolled a &f" + roll + "&7 out of &f" + max + "&7."), true);
			} catch (NoneNearbyException e) {
				player.sendMessage(Utility.colorCodes(
						"&7&oThe sound of your die rolling echoes in the wind."));
			}
		}
		return true;
	}

}
