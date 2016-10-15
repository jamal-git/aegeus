package com.aegeus.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.chat.Chat;
import com.aegeus.game.data.Data;
import com.aegeus.game.data.PlayerData;
import com.aegeus.game.util.Helper;

public class CommandChatChannel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		String search = args[0];
		Chat.Channel channel = Chat.Channel.getByShortcuts(search);
		if(channel == null) return false;
		
		PlayerData pd = Data.getPlayerData(player);
		pd.setChatChannel(channel);
		
		player.sendMessage(Helper.colorCodes("&7Default chat channel set to &b" + channel.getName() + "."));
		
		return true;
	}

}
