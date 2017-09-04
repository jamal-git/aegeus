package com.aegeus.game.item;

import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Team Tower Defense on 9/1/2017.
 */
public enum EnumCraftingMaterial {
    SKELETON_BONE("Skeleton Bone", EnumMaterialRarity.COMMON,
            Arrays.asList("A mysterious white bone.", "Probably from a skeleton."),
            Material.BONE),
    RAGGED_CLOTH("Ragged Cloth", EnumMaterialRarity.COMMON,
            Arrays.asList("This piece of cloth is well worn, but still durable.", "Useful in crafting clothing."),
            Material.BONE),
    TATTERED_PARCHMENT("Tattered Parchment", EnumMaterialRarity.UNCOMMON,
            Arrays.asList("A heavily worn piece of paper."),
            Material.PAPER),
    SUN_INGOT("Sun Ingot", EnumMaterialRarity.MYTHICAL,
            Arrays.asList("The unmeasurable amount of power of the sun in a tangible form.", "This fabled ingot can be used in crafting very rare items."),
            Material.GOLD_INGOT){
        @Override
        public ItemStack getItem() {
            ItemStack stack = new ItemStack(getMaterial(), 1);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Util.colorCodes("&eSun Ingot"));
            meta.getLore().addAll(getDescription());
            meta.getLore().add(getRarity().getNameAndColor());
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return stack;
        }
    },
    MOON_INGOT("Moon Ingot", EnumMaterialRarity.MYTHICAL,
            Arrays.asList("The essence of the moon in a tangible form.", "This fabled ingot can be used in crafting very rare items."),
            Material.IRON_INGOT){
        @Override
        public ItemStack getItem() {
            ItemStack stack = new ItemStack(getMaterial(), 1);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Util.colorCodes("&bMoon Ingot"));
            meta.getLore().addAll(getDescription());
            meta.getLore().add(getRarity().getNameAndColor());
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return stack;
        }
    };


    private String name;
    private EnumMaterialRarity rarity;
    private List<String> description;
    private Material material;

    EnumCraftingMaterial(String name, EnumMaterialRarity rarity, List<String> description, Material material)   {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public EnumMaterialRarity getRarity() {
        return rarity;
    }

    public List<String> getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItem()  {
        ItemStack stack = new ItemStack(getMaterial(), 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(getName());
        List<String> lore = meta.getLore();
        lore.addAll(getDescription());
        lore.add(getRarity().getNameAndColor());
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
