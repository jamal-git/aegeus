package com.aegeus.game.item;

import com.aegeus.game.item.util.ItemUtils;
import com.aegeus.game.item.util.Tiers;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Armor extends Item implements Tiered, Repairable {
	private int tier = 0;
	private int dura = 0;

	private int hp = 1;

	public Armor(ItemStack i) {
		super(i);
	}

	@Override
	public void load() {
		NBTTagCompound tag = ItemUtils.getTag(item);
		hp = tag.getInt("hp");
	}

	@Override
	public void save() {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.setInt("hp", hp);
		ItemUtils.setTag(item, tag);
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add("&7HP: &c+" + hp);
		return lore;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
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
		return Tiers.Durability.armor(tier);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return item.equals(obj);
	}

	@Override
	public String toString() {
		return item.toString();
	}
}
