package com.aegeus.game.item.drop;

import com.aegeus.game.item.wrapper.WeaponItem;
import com.aegeus.game.util.IntRange;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WeaponDrop extends Drop {
    private final IntRange damage;
    private final IntRange range;

    public WeaponDrop(Material material, IntRange damage, IntRange range) {
        super(material);
        this.damage = damage;
        this.range = range;
    }

    @Override
    public ItemStack get(float rarity) {
        WeaponItem weapon = new WeaponItem(getMaterial());
        // Set rarity
        weapon.setRarity(rarity);
        // Set damage based on rarity
        weapon.getDamage().setMin(damage.get(rarity));
        weapon.getDamage().setMax(weapon.getDamage().getMin() + range.get());
        return weapon.build();
    }
}