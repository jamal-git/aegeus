package com.aegeus.game.item.util;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Item {
	ItemStack getItem();

	default void load() {}

	default void save() {}

	default ItemStack build() {
		save();
		ItemUtils.setName(getItem(), buildName());
		ItemUtils.setLore(getItem(), buildLore());
		return getItem();
	}

	default String buildName() {
		return ItemUtils.getName(getItem());
	}

	default List<String> buildLore() {
		return ItemUtils.getLore(getItem());
	}
}
