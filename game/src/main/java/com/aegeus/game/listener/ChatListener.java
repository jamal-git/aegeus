package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.chat.ChatManager;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	@EventHandler
	// Local messages and custom message format
	private void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player player = e.getPlayer();
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		switch (info.getChatChannel()) {
			case GLOBAL:
				ChatManager.sendGlobal(player, Util.colorCodes(e.getMessage()));
				break;
			default:
				ChatManager.sendLocal(player, Util.colorCodes(e.getMessage()));
				break;
		}
	}
}
