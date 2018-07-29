package com.aegeus.game.listener;

import com.aegeus.game.item.util.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemListener implements Listener {
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		if (ItemManager.exists(event.getCursor()))
			ItemManager.get(event.getCursor()).onInvClick(event);

		if (ItemManager.exists(event.getCurrentItem()))
			ItemManager.get(event.getCurrentItem()).onInvClick(event);
	}
}
