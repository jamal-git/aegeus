package com.aegeus.game.item;

import com.aegeus.game.util.Utility;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class AegeusItem {
	private ItemStack item;

	public AegeusItem(Material material) {
		this.item = new ItemStack(material);
	}

	public AegeusItem(ItemStack item) {
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public String getName() {
		return item.getItemMeta().getDisplayName();
	}

	public void setName(String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utility.colorCodes(name));
		item.setItemMeta(meta);
	}

	public List<String> getLore() {
		return item.getItemMeta().getLore();
	}

	public void setLore(List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public void addLore(String... lines) {
		List<String> lore = getLore();
		for (String s : lines)
			lore.add(Utility.colorCodes(s));
		setLore(lore);
	}

	public Material getMaterial() {
		return item.getType();
	}

	public void setMaterial(Material material) {
		item.setType(material);
	}

	private NBTTagCompound getTag() {
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		return (nmsStack.getTag() != null) ? nmsStack.getTag() : new NBTTagCompound();
	}

	private void setTag(NBTTagCompound tag) {
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		nmsStack.setTag(tag);
		this.item = CraftItemStack.asBukkitCopy(nmsStack);
	}

	public NBTTagCompound getAegeusInfo() {
		NBTTagCompound tag = getTag();
		return (tag.hasKey("AegeusInfo")) ? tag.getCompound("AegeusInfo") : new NBTTagCompound();
	}

	public void setAegeusInfo(NBTTagCompound info) {
		NBTTagCompound tag = getTag();
		tag.set("AegeusInfo", info);
		setTag(tag);
	}

	public abstract ItemStack build();

	public ItemStack buildDefault() {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(
				ItemFlag.HIDE_ENCHANTS,
				ItemFlag.HIDE_POTION_EFFECTS,
				ItemFlag.HIDE_ATTRIBUTES
		);
		item.setItemMeta(meta);
		return item;
	}
}
