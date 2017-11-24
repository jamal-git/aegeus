package com.aegeus.game.util;

import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;

public class InventoryMenuManager {
	private static Set<InventoryBuilder> inventories = new HashSet<>();

	public static void addInventory(InventoryBuilder i) {
		inventories.add(i);
	}

	public static void removeInventory(InventoryBuilder b) {
		inventories.remove(b);
	}

	public static InventoryBuilder getBuilderFromInventory(Inventory i) {
		for (InventoryBuilder inventory : inventories) {
			if (i.getHolder().equals(inventory.getInventory().getHolder())) {
				return inventory;
			}
		}
		return null;
	}
}
