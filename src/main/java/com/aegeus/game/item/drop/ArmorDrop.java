package com.aegeus.game.item.drop;

import com.aegeus.game.item.wrapper.ArmorItem;
import com.aegeus.game.util.IntRange;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorDrop extends Drop {
    private final IntRange health;

    public ArmorDrop(Material material, IntRange health) {
        super(material);
        this.health = health;
    }

    @Override
    public ItemStack get(float rarity) {
        ArmorItem armor = new ArmorItem(getMaterial());
        // Set rarity
        armor.setRarity(rarity);
        // Set health based on rarity
        armor.setHealth(health.get(rarity));
        return armor.build();
    }
}