package com.aegeus.game.item.util;

import com.aegeus.game.item.impl.ItemArmor;
import com.aegeus.game.item.impl.ItemOrb;
import com.aegeus.game.item.impl.ItemWeapon;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
	public static boolean exists(ItemStack item) {
		return !ItemUtils.isNothing(item) && ItemUtils.getTag(item).hasKey("identity");
	}

	public static ItemWrapper get(ItemStack item) {
		switch (ItemUtils.getTag(item).getString("identity")) {
			case ItemWeapon.IDENTITY: return new ItemWeapon(item);
			case ItemArmor.IDENTITY: return new ItemArmor(item);
			case ItemOrb.IDENTITY: return new ItemOrb(item);
			default: return null;
		}
	}
}
