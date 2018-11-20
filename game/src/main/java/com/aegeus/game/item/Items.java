package com.aegeus.game.item;

import com.aegeus.game.item.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {
	GOLD {
		public ItemStack get() {
			ItemStack item = new ItemStack(Material.GOLD_NUGGET);
			ItemUtils.setName(item, "&eGold");
			ItemUtils.setLore(item, "&7The currency of Zelkova.");
			return item;
		}
	},
	TRAP {
		public ItemStack get() {
			ItemStack item = new ItemStack(Material.TRAP_DOOR);
			ItemUtils.setName(item, "&bMonster Trap");
			ItemUtils.setLore(item, "&7A small monster trap.",
					"&7Traps a small monster for 12 seconds.",
					"&7Place onto the ground and right-click to enable.");
			return item;
		}
	};

	public abstract ItemStack get();

	public ItemStack get(int amount) {
		ItemStack item = get();
		item.setAmount(amount);
		return item;
	}
}
