package com.aegeus.game.item.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
	public static ItemStack gold(int amount) {
		ItemStack item = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemUtils.setName(item, "&eGold");
		ItemUtils.setLore(item, "&7The currency of Zelkova.");
		return item;
	}
}
