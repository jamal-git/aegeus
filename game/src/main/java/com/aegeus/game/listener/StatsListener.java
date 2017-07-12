package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class StatsListener implements Listener {
	private final Aegeus parent;

	public StatsListener(Aegeus parent) {
		this.parent = parent;

		// Health regeneration
		Bukkit.getScheduler().runTaskTimer(parent, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				AgPlayer info = parent.getPlayer(player);
				if (!player.isDead() && player.getHealth() < player.getMaxHealth() && !info.isInCombat()) {
					player.setHealth(Math.max(0, Math.min(player.getMaxHealth(), player.getHealth() + info.getHpRegen() + 10)));
					Util.updateDisplay(player);
				}
			}
		}, 20, 20);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		Bukkit.getScheduler().runTaskLater(parent, () -> {
			Util.updateStats(e.getWhoClicked());
			ItemStack item = e.getCurrentItem();

			if (item != null) {
				if (item.getType().equals(Material.PUMPKIN)
						&& e.getSlotType().equals(InventoryType.SlotType.ARMOR))
					item.setType(Material.JACK_O_LANTERN);
				else if (item.getType().equals(Material.JACK_O_LANTERN)
						&& !e.getSlotType().equals(InventoryType.SlotType.ARMOR))
					item.setType(Material.PUMPKIN);
			}

		}, 1);
	}

	@EventHandler
	private void onDrop(PlayerDropItemEvent e) {
		ItemStack item = e.getItemDrop().getItemStack();
		if (item != null && item.getType().equals(Material.JACK_O_LANTERN))
			item.setType(Material.PUMPKIN);
	}

}
