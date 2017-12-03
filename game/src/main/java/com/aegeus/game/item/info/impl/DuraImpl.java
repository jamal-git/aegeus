package com.aegeus.game.item.info.impl;

import com.aegeus.game.item.info.DuraInfo;
import com.aegeus.game.item.info.ItemInfo;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DuraImpl implements DuraInfo {
	private ItemStack item;
	private String name = "";
	private List<String> lore = new ArrayList<>();

	private int maxDura = 0;
	private int dura = 0;
	private int maxReserves = 0;
	private int reserves = 0;

	public DuraImpl(ItemStack item) {
		this.item = item;
		impo();
	}

	@Override
	public void impo() {
		ItemInfo.impo(this);
		DuraInfo.impo(this);
	}

	@Override
	public void store() {
		ItemInfo.store(this);
		DuraInfo.store(this);
	}

	@Override
	public ItemStack build() {
		return ItemInfo.build(this);
	}

	@Override
	public int getMaxDura() {
		return maxDura;
	}

	@Override
	public void setMaxDura(int maxDura) {
		this.maxDura = maxDura;
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
	public int getMaxReserves() {
		return maxReserves;
	}

	@Override
	public void setMaxReserves(int maxReserves) {
		this.maxReserves = maxReserves;
	}

	@Override
	public int getReserves() {
		return reserves;
	}

	@Override
	public void setReserves(int reserves) {
		this.reserves = reserves;
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
