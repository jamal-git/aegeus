package com.aegeus.game.chat;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.aegeus.game.player.PlayerData;
import com.aegeus.game.util.Helper;

public class Chat implements Listener {

	private JavaPlugin parent;

	public Chat(JavaPlugin parent) {
		this.parent = parent;
	}

	@EventHandler
	// Local messages and custom message format
	private void onChatEvent(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		if(PlayerData.get(player).getBankWithdraw())	{
			try	{
				ItemStack module = new ItemStack(Material.BOOK);
				ItemMeta meta = module.getItemMeta();
				meta.setDisplayName(ChatColor.AQUA + "Bank Note Module");
				ArrayList<String> lore = new ArrayList<>();
				lore.add(ChatColor.GRAY + "Value: " + Integer.valueOf(event.getMessage()));
				lore.add("" + ChatColor.GRAY + ChatColor.ITALIC + "This module can be used to add gold to your bank.");
				meta.setLore(lore);
				module.setItemMeta(meta);
				player.getInventory().addItem(module);
				PlayerData.get(player).setBankWithdraw(false);
				return;
			}
			catch(Exception e)	{
				parent.getLogger().log(Level.SEVERE, "Could not parse message for bank withdrawal", e);
				player.sendMessage(ChatColor.RED + "Invalid Number!");
				PlayerData.get(player).setBankWithdraw(false);
				return;
			}
		}
		switch(PlayerData.get(player).getChatChannel()) {
			case GLOBAL:
				ChatManager.sendGlobalChat(player, Helper.colorCodes(event.getMessage()));
			default:
				ChatManager.sendLocalChat(player, Helper.colorCodes(event.getMessage()));
		}
	}
}
