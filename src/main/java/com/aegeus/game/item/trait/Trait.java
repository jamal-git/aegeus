package com.aegeus.game.item.trait;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface Trait {
    ItemStack getItem();

    void setItem(ItemStack item);

    default String buildName() {
        return "";
    }

    default List<String> buildLore() {
        return new ArrayList<>();
    }
}
