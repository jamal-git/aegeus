package com.aegeus.game.item.wrapper;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.trait.DuraTrait;
import com.aegeus.game.item.trait.RarityTrait;
import com.aegeus.game.item.trait.TierTrait;
import com.aegeus.game.util.IntRange;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WeaponItem extends ItemWrapper implements TierTrait, DuraTrait, RarityTrait {
    public static final String IDENTITY = "WEAPON";

    private IntRange damage = new IntRange();

    public WeaponItem(ItemStack item) {
        super(item, IDENTITY);
    }

    public WeaponItem(Material material) {
        super(material, IDENTITY);
    }

    @Override
    public WeaponItem load() {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        damage.setMin(tag.getInt("damage_min"));
        damage.setMax(tag.getInt("damage_max"));
        return (WeaponItem) super.load();
    }

    @Override
    public WeaponItem save() {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        tag.setInt("damage_min", damage.getMin());
        tag.setInt("damage_max", damage.getMax());
        setItem(ItemUtils.setNBTTag(getItem(), tag));
        return (WeaponItem) super.save();
    }

    @Override
    public String buildName() {
        return TierTrait.super.buildName() + "Weapon";
    }

    @Override
    public List<String> buildLore() {
        List<String> lore = super.buildLore();
        lore.add("&7Damage: &c" + damage.getMin() + "&7 - &c" + damage.getMax());
        lore.addAll(RarityTrait.super.buildLore());
        return lore;
    }

    @Override
    public int getMaxDura() {
        return 100;
    }

    public IntRange getDamage() {
        return damage;
    }
}
