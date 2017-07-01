package com.aegeus.game.item;

import org.bukkit.Material;

public class ItemGold extends AgItem {
	public ItemGold(int amount) {
		super(Material.GOLD_NUGGET, amount);
		setName("&eGold");
		addLore("&7The currency of Zelkova.");
	}
}
