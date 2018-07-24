package com.aegeus.game.item.util;

import com.aegeus.game.util.Util;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
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
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		return (nmsStack.getTag() != null) ? nmsStack.getTag() : new NBTTagCompound();
	}

	public static ItemStack setTag(ItemStack item, NBTTagCompound tag) {
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		nmsStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}

	public static String getName(ItemStack i) {
		return isNothing(i.getType()) ? "Nothing" : CraftItemStack.asNMSCopy(i).getName();
	}

	public static String getName(Material m) {
		return isNothing(m) ? "Nothing" : CraftItemStack.asNMSCopy(new ItemStack(m)).getName();
	}

	public static void setName(ItemStack i, String name) {
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(Util.colorCodes(name));
		i.setItemMeta(meta);
	}

	public static List<String> getLore(ItemStack i) {
		return i == null || i.getItemMeta() == null || i.getItemMeta().getLore() == null
				? new ArrayList<>() : i.getItemMeta().getLore();
	}

	public static void setLore(ItemStack i, List<String> lore) {
		ItemMeta meta = i.getItemMeta();
		meta.setLore(lore.stream().map(Util::colorCodes).collect(Collectors.toList()));
		i.setItemMeta(meta);
	}

	public static void setLore(ItemStack i, String... lore) {
		setLore(i, Arrays.stream(lore).map(Util::colorCodes).collect(Collectors.toList()));
	}

	public static boolean isNothing(Material m) {
		return m == null | m == Material.AIR;
	}

	public static boolean isSword(Material m) {
		return m != null && m.name().contains("_SWORD");
	}

	public static boolean isAxe(Material m) {
		return m != null && m.name().contains("_AXE");
	}

	public static boolean isSpade(Material m) {
		return m != null && m.name().contains("_SPADE");
	}

	public static boolean isHoe(Material m) {
		return m != null && m.name().contains("_HOE");
	}

	public static boolean isHelmet(Material m) {
		return m != null && m.name().contains("_HELMET");
	}

	public static boolean isChestplate(Material m) {
		return m != null && m.name().contains("_CHESTPLATE");
	}

	public static boolean isLeggings(Material m) {
		return m != null && m.name().contains("_LEGGINGS");
	}

	public static boolean isBoots(Material m) {
		return m != null && m.name().contains("_BOOTS");
	}

	public static boolean isDisplayItem(ItemStack i) {
		return getTag(i).hasKey("display_item") && getTag(i).getBoolean("display_item");
	}

	public static void setDisplayItem(ItemStack i, boolean b) {
		getTag(i).setBoolean("display_item", b);
	}

	public static boolean isGlowing(ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		return meta.getEnchantLevel(Enchantment.ARROW_INFINITE) >= 1;
	}

	public static void setGlowing(ItemStack i, boolean glowing) {
		ItemMeta meta = i.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		if (glowing)
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		else
			meta.removeEnchant(Enchantment.ARROW_INFINITE);
		i.setItemMeta(meta);
	}
}
