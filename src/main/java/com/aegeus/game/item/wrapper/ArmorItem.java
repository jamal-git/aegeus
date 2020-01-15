package com.aegeus.game.item.wrapper;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.trait.DuraTrait;
import com.aegeus.game.item.trait.RarityTrait;
import com.aegeus.game.item.trait.TierTrait;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorItem extends ItemWrapper implements TierTrait, DuraTrait, RarityTrait {
    public static final String IDENTITY = "ARMOR";

    private int health;

    public ArmorItem(ItemStack item) {
        super(item, IDENTITY);
    }

    public ArmorItem(Material material) {
        super(material, IDENTITY);
    }

    @Override
    public ArmorItem load() {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        health = tag.getInt("health");
        return (ArmorItem) super.load();
    }

    @Override
    public ArmorItem save() {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        tag.setInt("health", health);
        setItem(ItemUtils.setNBTTag(getItem(), tag));
        return (ArmorItem) super.save();
    }

    @Override
    public String buildName() {
        return TierTrait.super.buildName() + "Armor";
    }

    @Override
    public List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Health: &c+" + health);
        lore.addAll(RarityTrait.super.buildLore());
        return lore;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxDura() {
        return 100;
    }
}
