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

	public NBTTagCompound load() {
		return ItemUtils.getTag(item);
	}

	public NBTTagCompound save() {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.setString("identity", getIdentity());
		return tag;
	}

	public ItemStack build() {
		item = ItemUtils.setTag(item, save());
		if (!buildName().isEmpty()) ItemUtils.setName(item, buildName());
		if (!buildLore().isEmpty()) ItemUtils.setLore(item, buildLore());
		return item;
	}

	public String buildName() {
		return "";
	}

	public List<String> buildLore() {
		return new ArrayList<>();
	}

	public void onInvClick(InventoryClickEvent event) {

	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public String toString() {
		return item.toString();
	}
}
