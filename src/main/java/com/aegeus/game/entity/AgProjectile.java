package com.aegeus.game.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class AgProjectile extends AgEntity {
    private ItemStack itemShotFrom;

    public AgProjectile(Projectile p) {
        super(p);
    }

    public AgProjectile(Projectile p, ItemStack i) {
        super(p);
        itemShotFrom = i;
    }

    public Projectile getProjectile() {
        return (Projectile) getEntity();
    }

    public ItemStack getItemShotFrom() {
        return itemShotFrom;
    }

    public void setItemShotFrom(ItemStack i) {
        itemShotFrom = i;
    }
}
