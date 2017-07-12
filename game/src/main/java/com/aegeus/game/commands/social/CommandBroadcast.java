package com.aegeus.game.commands.social;

import com.aegeus.game.Aegeus;
import com.aegeus.game.social.ChatManager;
import com.aegeus.game.util.Util;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Silvre on 7/10/2017.
 */
public class CommandBroadcast implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof ConsoleCommandSender || (commandSender instanceof Player && commandSender.hasPermission("aegeus.admin"))) {
			ChatManager.sendBroadcast(String.join(" ", strings));
			for (Player p : Aegeus.getInstance().getServer().getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.4f, 1);
			}
			return true;
		} else {
			commandSender.sendMessage(Util.colorCodes("&cYou do not have sufficient permissions to execute this command!"));
			return true;
		}
	}
}
