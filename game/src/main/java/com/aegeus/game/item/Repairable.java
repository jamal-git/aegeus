package com.aegeus.game.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Repairable {
	int getDura();

	void setDura(int dura);

	int getMaxDura();

	default int display(ItemStack i) {
		return display(i.getType());
	}

	default int display(Material m) {
		return (getDura() / getMaxDura()) * (m.getMaxDurability() - 1);
	}
}
