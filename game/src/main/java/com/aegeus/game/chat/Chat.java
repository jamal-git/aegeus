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

import com.aegeus.game.data.Data;
import com.aegeus.game.util.Utility;

public class Chat implements Listener {

	private JavaPlugin parent;

	public Chat(JavaPlugin parent) {
		this.parent = parent;
	}
	
	public static enum Channel {
		LOCAL(0, "Local", "l", "nearby"),
		GLOBAL(1, "Global", "gl", "all"),
		GUILD(2, "Guild", "gu", "clan");
		
		private int id;
		private String name;
		private String[] shortcuts;
		
		Channel(int id, String name, String... shortcuts){
			this.id = id;
			this.name = name;
			this.shortcuts = shortcuts;
		}
		
		public int getId() { return id; }
		public String getName() { return name; }
		public String[] getShortcuts() { return shortcuts; }
		
		public static Channel getById(int id) {
			for(Channel c : Channel.values())
				if(c.id == id) return c;
			return null;
		}
		
		public static Channel getByName(String name) {
			for(Channel c : Channel.values())
				if(c.name.equalsIgnoreCase(name)) return c;
			return null;
		}
		
		public static Channel getByShortcuts(String shortcut) {
			for(Channel c : Channel.values()) {
				if(c.name.equalsIgnoreCase(shortcut)) return c;
				if(String.valueOf(c.id).equalsIgnoreCase(shortcut)) return c;
				for(String s : c.shortcuts)
					if(s.equalsIgnoreCase(shortcut)) return c;
			}
			return null;
		}
		
	}

	@EventHandler
	// Local messages and custom message format
	private void onChatEvent(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		if(Data.getPlayerData(player).getBankWithdraw())	{
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
				Data.getPlayerData(player).setBankWithdraw(false);
				return;
			}
			catch(Exception e)	{
				parent.getLogger().log(Level.SEVERE, "Could not parse message for bank withdrawal", e);
				player.sendMessage(ChatColor.RED + "Invalid Number!");
				Data.getPlayerData(player).setBankWithdraw(false);
				return;
			}
		}
		switch(Data.getPlayerData(player).getChatChannel()) {
			case GLOBAL:
				ChatManager.sendGlobalChat(player, Utility.colorCodes(event.getMessage())); break;
			default:
				ChatManager.sendLocalChat(player, Utility.colorCodes(event.getMessage())); break;
		}
	}
}
