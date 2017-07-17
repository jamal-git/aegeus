package com.aegeus.game.item.info.impl;

import com.aegeus.game.item.info.ItemInfo;
import com.aegeus.game.item.info.LevelInfo;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LevelImpl implements LevelInfo {
	private ItemStack item;
	private String name;
	private List<String> lore = new ArrayList<>();

	private int level = 0;
	private int xp = 0;
	private int maxXp = 0;

	@Override
	public void impo() {
		ItemInfo.impo(this);
		LevelInfo.impo(this);
	}

	@Override
	public void store() {
		ItemInfo.store(this);
		LevelInfo.store(this);
	}

	@Override
	public ItemStack build() {
		return ItemInfo.build(this);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public int getMaxXp() {
		return maxXp;
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
