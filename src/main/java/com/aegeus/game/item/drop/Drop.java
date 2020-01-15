package com.aegeus.game.item.drop;

import com.aegeus.game.util.RuntimeTypeAdapterFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Drop {
    public static RuntimeTypeAdapterFactory<Drop> ADAPTER_FACTORY = RuntimeTypeAdapterFactory.of(Drop.class)
            .registerSubtype(ArmorDrop.class, "armor")
            .registerSubtype(WeaponDrop.class, "weapon");

    private final Material material;

    public Drop(Material material) {
        this.material = material;
    }

    public abstract ItemStack get(float rarity);

    public final Material getMaterial() {
        return material;
    }


}