package com.aegeus.game.commands.social;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.social.ChatChannel;
import com.aegeus.game.util.Util;
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
		String name = args[0];
		ChatChannel channel = ChatChannel.fromName(name);
		if (channel == null) return false;

		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		info.setChatChannel(channel);

		player.sendMessage(Util.colorCodes("&7Default chat channel set to &b" + channel.getName() + "."));

		return true;
	}

}
