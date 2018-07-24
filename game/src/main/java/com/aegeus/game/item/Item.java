package com.aegeus.game.item;

import com.aegeus.game.item.util.ItemUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Item {
	public ItemStack item;

	public Item(ItemStack i) {
		item = i;
		load();
	}

	public ItemStack build() {
		save();
		ItemUtils.setName(item, buildName());
		ItemUtils.setLore(item, buildLore());
		return item;
	}

	public abstract void load();

	public abstract void save();

	public String buildName() {
		return ItemUtils.getName(item);
	}

	public List<String> buildLore() {
		return ItemUtils.getLore(item);
	}
}
