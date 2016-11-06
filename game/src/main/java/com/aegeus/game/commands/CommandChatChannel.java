package com.aegeus.game.commands;

import com.aegeus.game.chat.Chat;
import com.aegeus.game.data.AegeusPlayer;
import com.aegeus.game.data.Data;
import com.aegeus.game.util.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChatChannel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (args.length < 1) return false;

		Player player = (Player) sender;
		String search = args[0];
		Chat.Channel channel = Chat.Channel.getByShortcuts(search);
		if (channel == null) return false;

		AegeusPlayer ap = Data.get(player);
		ap.setChatChannel(channel);

		player.sendMessage(Utility.colorCodes("&7Default chat channel set to &b" + channel.getName() + "."));

		return true;
	}

}
