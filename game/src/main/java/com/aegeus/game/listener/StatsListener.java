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
				AgPlayer info = parent.getPlayer(player);
				if (!player.isDead() && player.getHealth() < player.getMaxHealth() && !info.isInCombat()) {
					player.setHealth(Math.max(0, Math.min(player.getMaxHealth(), player.getHealth() + info.getHpRegen() + 10)));
					Util.updateDisplay(player);
				}
			}
		}, 20, 20);

		Bukkit.getScheduler().runTaskTimer(parent, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				AgPlayer info = parent.getPlayer(player);
				if (!player.isDead()) {
					if (info.getEnergy() >= 0)
						info.setEnergy(Math.max(0, Math.min(100, info.getEnergy() + 1.2f + (info.getEnergyRegen() * 8))));
					else
						info.setEnergy(Math.max(-40, Math.min(100, info.getEnergy() + 1)));
					Util.updateEnergy(player);
				}
			}
		}, 1, 1);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		Bukkit.getScheduler().runTaskLater(parent, () -> Util.updateStats(e.getWhoClicked()), 1);
	}

}
