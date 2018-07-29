package com.aegeus.game.listener;

import com.aegeus.game.entity.util.EntityUtils;
import com.aegeus.game.item.impl.ItemArmor;
import com.aegeus.game.item.util.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		event.getPlayer().setHealthScale(10);
		EntityUtils.update(event.getPlayer());
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		EntityUtils.update(event.getPlayer());
	}

	@EventHandler
	private void on(PlayerInteractEvent event) {
		if (ItemManager.is(event.getItem(), ItemArmor.IDENTITY))
			EntityUtils.update(event.getPlayer());
	}

	@EventHandler
	private void onInvClick(InventoryClickEvent event) {
		if (ItemManager.exists(event.getCursor()))
			ItemManager.get(event.getCursor()).onInvClick(event);

		if (ItemManager.exists(event.getCurrentItem()))
			ItemManager.get(event.getCurrentItem()).onInvClick(event);

		EntityUtils.update(event.getWhoClicked());
	}
}
