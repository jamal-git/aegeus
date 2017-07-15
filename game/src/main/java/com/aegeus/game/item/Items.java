package com.aegeus.game.item;

import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Items {
	public static ItemStack getGold(int amount) {
		ItemStack gold = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemMeta meta = gold.getItemMeta();
		meta.setDisplayName(Util.colorCodes("&eGold"));
		meta.setLore(Arrays.asList("&7The currency of Zelkova."));
		gold.setItemMeta(meta);
		return gold;
	}
}
