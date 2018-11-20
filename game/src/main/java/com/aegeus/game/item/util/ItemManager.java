package com.aegeus.game.item.util;

import com.aegeus.game.item.impl.ItemArmor;
import com.aegeus.game.item.impl.ItemWeapon;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
	public static boolean exists(ItemStack item) {
		return !ItemUtils.isNothing(item) && ItemUtils.getTag(item).hasKey("identity");
	}

	public static String identity(ItemStack item) {
		return exists(item) ? ItemUtils.getTag(item).getString("identity") : "";
	}

	public static boolean is(ItemStack item, String identity) {
		return exists(item) && ItemUtils.getTag(item).getString("identity").equalsIgnoreCase(identity);
	}

	public static ItemWrapper get(ItemStack item) {
		ItemWrapper info;
		switch (identity(item)) {
			case ItemWeapon.IDENTITY: info = new ItemWeapon(item);
				break;
			case ItemArmor.IDENTITY: info = new ItemArmor(item);
				break;
			default: return null;
		}
		info.load();
		return info;
	}
}
