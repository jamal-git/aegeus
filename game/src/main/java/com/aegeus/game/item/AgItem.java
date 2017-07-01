package com.aegeus.game.item;

import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AgItem {
	private ItemStack item;
	private String name;

	public AgItem(Material material) {
		this.item = new ItemStack(material);
	}

	public AgItem(Material material, int amount) {
		this.item = new ItemStack(material, amount);
	}

	public AgItem(ItemStack item) {
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Util.colorCodes(name);
	}

	public List<String> getLore() {
		return item.getItemMeta().getLore() == null ? new ArrayList<>() : item.getItemMeta().getLore();
	}

	public void setLore(List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public void addLore(String line) {
		List<String> lore = getLore();
		lore.add(Util.colorCodes(line));
		setLore(lore);
	}

	public Material getMaterial() {
		return item.getType();
	}

	public void setMaterial(Material material) {
		item.setType(material);
	}

	private NBTTagCompound getTag() {
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		return (nmsStack.getTag() != null) ? nmsStack.getTag() : new NBTTagCompound();
	}

	private void setTag(NBTTagCompound tag) {
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
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

	public boolean verify() {
		return true;
	}

	public ItemStack build() {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.addItemFlags(
				ItemFlag.HIDE_ENCHANTS,
				ItemFlag.HIDE_POTION_EFFECTS,
				ItemFlag.HIDE_ATTRIBUTES
		);
		item.setItemMeta(meta);
		return item;
	}
}
