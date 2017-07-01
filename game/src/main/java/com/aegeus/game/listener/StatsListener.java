package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StatsListener implements Listener {
	private final Aegeus parent;

	public StatsListener(Aegeus parent) {
		this.parent = parent;
		Bukkit.getScheduler().runTaskTimer(parent, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				AgPlayer info = Aegeus.getInstance().getPlayer(player);
				if (!player.isDead() && player.getHealth() < player.getMaxHealth() && !info.isInCombat()) {
					player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + info.getHpRegen() + 10));
					Util.updateDisplay(player);
				}
			}
		}, 20, 20);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		Bukkit.getScheduler().runTaskLater(parent, () -> Util.updateStats(e.getWhoClicked()), 1);
	}

}
