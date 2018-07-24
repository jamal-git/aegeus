package com.aegeus.game.item.util;

import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
	public static ItemStack getGold(int amount) {
		ItemStack item = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemUtils.setDisplayName(item, Util.colorCodes("&eGold"));
		ItemUtils.setLore(item, Util.colorCodes("&7The currency of Zelkova."));
		return item;
	}
}
