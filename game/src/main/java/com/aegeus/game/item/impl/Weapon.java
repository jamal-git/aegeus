package com.aegeus.game.item.impl;

import com.aegeus.game.item.Tiers;
import com.aegeus.game.item.trait.Repairable;
import com.aegeus.game.item.trait.Tierable;
import com.aegeus.game.item.util.ItemUtils;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Weapon implements Tierable, Repairable {
	private final ItemStack item;
	private int tier = 0;
	private int dura = 0;

	public Weapon(ItemStack item) {
		this.item = item;
	}

	@Override
	public void save() {
		Tierable.super.save();
		Repairable.super.save();
		NBTTagCompound tag = ItemUtils.getTag(item);
		ItemUtils.setTag(item, tag);
	}

	@Override
	public void load() {
		Tierable.super.load();
		Repairable.super.load();
		NBTTagCompound tag = ItemUtils.getTag(item);
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		return lore;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public void setTier(int tier) {
		this.tier = tier;
	}

	@Override
	public int getDura() {
		return dura;
	}

	@Override
	public void setDura(int dura) {
		this.dura = dura;
	}

	@Override
	public int getMaxDura() {
		return Tiers.Durability.weapon(getTier());
	}
}
