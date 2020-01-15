package com.aegeus.game.item;

import com.aegeus.game.util.Util;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ItemUtils {
    /**
     * Gets or creates the Aegeus NBT tag for an item.
     *
     * @param item The item.
     * @return The Aegeus NBT tag.
     */
    public static NBTTagCompound getNBTTag(ItemStack item) {
        if (isNothing(item)) return null;
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        if (!nmsStack.getOrCreateTag().hasKey("aegeus"))
            nmsStack.getTag().set("aegeus", new NBTTagCompound());
        return nmsStack.getTag().getCompound("aegeus");
    }

    /**
     * Sets the Aegeus NBT tag for an item.
     *
     * @param item The item.
     * @param tag  The new Aegeus NBT tag.
     * @return The updated item.
     */
    public static ItemStack setNBTTag(ItemStack item, NBTTagCompound tag) {
        if (isNothing(item)) return null;
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        nmsStack.getOrCreateTag().set("aegeus", tag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    /**
     * Sets an item's name with color coding.
     *
     * @param item The item.
     * @param name The name for the item.
     */
    public static void setName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.colorCodes(name));
        item.setItemMeta(meta);
    }

    /**
     * Sets an item's lore with color coding.
     *
     * @param item The item.
     * @param lore The lore for the item.
     */
    public static void setLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore.stream().map(Util::colorCodes).collect(Collectors.toList()));
        item.setItemMeta(meta);
    }

    /**
     * Sets an item's lore with color coding.
     *
     * @param item The item.
     * @param lore The lore for the item.
     */
    public static void setLore(ItemStack item, String... lore) {
        setLore(item, Arrays.asList(lore));
    }

    /**
     * Checks whether an item is null or air.
     *
     * @param i The item.
     * @return Whether an item is null or air.
     */
    public static boolean isNothing(ItemStack i) {
        return i == null || i.getType() == Material.AIR;
    }

    public static boolean isSword(Material m) {
        return m.name().contains("_SWORD");
    }

    public static boolean isAxe(Material m) {
        return m.name().contains("_AXE");
    }

    public static boolean isSpade(Material m) {
        return m.name().contains("_SPADE");
    }

    public static boolean isHoe(Material m) {
        return m.name().contains("_HOE");
    }

    public static boolean isHelmet(Material m) {
        return m.name().contains("_HELMET");
    }

    public static boolean isChestplate(Material m) {
        return m.name().contains("_CHESTPLATE");
    }

    public static boolean isLeggings(Material m) {
        return m.name().contains("_LEGGINGS");
    }

    public static boolean isBoots(Material m) {
        return m.name().contains("_BOOTS");
    }

    public static boolean isGlowing(ItemStack item) {
        return item.getItemMeta().getEnchantLevel(Enchantment.ARROW_INFINITE) >= 1;
    }

    public static void setGlowing(ItemStack item, boolean glowing) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (glowing) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        else meta.removeEnchant(Enchantment.ARROW_INFINITE);

        item.setItemMeta(meta);
    }
}
