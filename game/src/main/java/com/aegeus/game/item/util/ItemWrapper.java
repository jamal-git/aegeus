package com.aegeus.game.item.util;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemWrapper {
	private ItemStack item;

	public ItemWrapper(ItemStack item) {
		this.item = item;
		load();
	}

	public ItemWrapper(Material material) {
		this.item = new ItemStack(material);
	}

	public ItemStack getItem() {
		return item;
	}

	protected void setItem(ItemStack item) {
		this.item = item;
	}

	public abstract String getIdentity();

	public void load() {}

	public NBTTagCompound save() {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.setString("identity", getIdentity());
		return tag;
	}

	public ItemStack build() {
		setItem(ItemUtils.setTag(item, save()));
		if (!buildName().isEmpty()) ItemUtils.setName(getItem(), buildName());
		if (!buildLore().isEmpty()) ItemUtils.setLore(getItem(), buildLore());
		return getItem();
	}

	public String buildName() {
		return "";
	}

	public List<String> buildLore() {
		return new ArrayList<>();
	}

	public void onInvClick(InventoryClickEvent event) {

	}

}
