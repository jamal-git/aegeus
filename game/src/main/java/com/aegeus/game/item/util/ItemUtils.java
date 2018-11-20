package com.aegeus.game.item.util;

import com.aegeus.game.util.Util;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {
	public static NBTTagCompound getTag(ItemStack item) {
		if (isNothing(item)) return null;
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		return nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
	}

	public static ItemStack setTag(ItemStack item, NBTTagCompound tag) {
		if (isNothing(item)) return null;
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		nmsStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}

	public static String getName(ItemStack item) {
		return isNothing(item) ? "Nothing" : CraftItemStack.asNMSCopy(item).getName();
	}

	public static void setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Util.colorCodes(name));
		item.setItemMeta(meta);
	}

	public static List<String> getLore(ItemStack item) {
		return isNothing(item) ? new ArrayList<>() : item.getItemMeta().getLore();
	}

	public static void setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore.stream().map(Util::colorCodes).collect(Collectors.toList()));
		item.setItemMeta(meta);
	}

	public static void setLore(ItemStack item, String... lore) {
		setLore(item, Arrays.asList(lore));
	}

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

	public static boolean isGlowing(ItemStack i) {
		return !isNothing(i) && i.getItemMeta().getEnchantLevel(Enchantment.ARROW_INFINITE) >= 1;
	}

	public static void setGlowing(ItemStack item, boolean glowing) {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		if (glowing) meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		else meta.removeEnchant(Enchantment.ARROW_INFINITE);

		item.setItemMeta(meta);
	}
}
