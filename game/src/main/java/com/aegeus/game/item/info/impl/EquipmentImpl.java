package com.aegeus.game.item.info.impl;

import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.info.EquipmentInfo;
import com.aegeus.game.item.info.ItemInfo;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EquipmentImpl implements EquipmentInfo {
	private ItemStack item;
	private String name = "";
	private List<String> lore = new ArrayList<>();

	private int tier = 0;
	private Rarity rarity = null;
	private int enchant = 0;

	@Override
	public void impo() {
		ItemInfo.impo(this);
		EquipmentInfo.impo(this);
	}

	@Override
	public void store() {
		ItemInfo.store(this);
		EquipmentInfo.store(this);
	}

	@Override
	public ItemStack build() {
		return ItemInfo.build(this);
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
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	@Override
	public int getEnchant() {
		return enchant;
	}

	@Override
	public void setEnchant(int enchant) {
		this.enchant = enchant;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<String> getLore() {
		return lore;
	}

	@Override
	public void setLore(List<String> lore) {
		this.lore = lore;
	}
}
