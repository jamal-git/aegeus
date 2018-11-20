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
	private void onJoin(PlayerJoinEvent evt) {
		evt.getPlayer().setHealthScale(20);
		EntityUtils.update(evt.getPlayer());
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent evt) {
		EntityUtils.update(evt.getPlayer());
	}

	@EventHandler
	private void on(PlayerInteractEvent evt) {
		if (ItemManager.is(evt.getItem(), ItemArmor.IDENTITY))
			EntityUtils.update(evt.getPlayer());
	}

	@EventHandler
	private void onInvClick(InventoryClickEvent evt) {
		if (ItemManager.exists(evt.getCursor()))
			ItemManager.get(evt.getCursor()).onInvClick(evt);

		if (ItemManager.exists(evt.getCurrentItem()))
			ItemManager.get(evt.getCurrentItem()).onInvClick(evt);

		EntityUtils.update(evt.getWhoClicked());
	}
}
