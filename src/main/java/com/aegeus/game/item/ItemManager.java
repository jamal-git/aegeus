package com.aegeus.game.item;

import com.aegeus.game.item.wrapper.ArmorItem;
import com.aegeus.game.item.wrapper.ItemWrapper;
import com.aegeus.game.item.wrapper.WeaponItem;
import org.bukkit.inventory.ItemStack;

public final class ItemManager {
    /**
     * Checks whether the item has an identity.
     *
     * @param item The item.
     * @return Whether the item has an identity.
     */
    public static boolean ex(ItemStack item) {
        return !ItemUtils.isNothing(item) && ItemUtils.getNBTTag(item).hasKey("identity");
    }

    /**
     * Gets the identity for an item.
     *
     * @param item The item.
     * @return The item's identity.
     */
    public static String id(ItemStack item) {
        return ex(item) ? ItemUtils.getNBTTag(item).getString("identity") : "";
    }

    /**
     * Creates and loads an item wrapper for an item, as long as said item has an identity.
     *
     * @param item The item.
     * @return The item wrapper.
     */
    public static ItemWrapper get(ItemStack item) {
        switch (id(item)) {
            case WeaponItem.IDENTITY:
                return new WeaponItem(item).load();
            case ArmorItem.IDENTITY:
                return new ArmorItem(item).load();
        }
        return null;
    }
}
