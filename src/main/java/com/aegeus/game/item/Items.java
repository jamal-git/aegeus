package com.aegeus.game.item;

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
    };

    public abstract ItemStack get();

    public ItemStack get(int amount) {
        ItemStack item = get();
        item.setAmount(amount);
        return item;
    }
}
