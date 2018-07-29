package com.aegeus.game.item.impl;

import com.aegeus.game.item.Tiers;
import com.aegeus.game.item.trait.Repairable;
import com.aegeus.game.item.trait.Tierable;
import com.aegeus.game.item.util.ItemWrapper;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemWeapon extends ItemWrapper implements Tierable, Repairable {
	public static final String IDENTITY = "weapon";

	private int minDmg = 1;
	private int maxDmg = 1;

	public ItemWeapon(ItemStack item) {
		super(item);
	}

	public ItemWeapon(Material material) {
		super(material);
	}

	@Override
	public NBTTagCompound load() {
		NBTTagCompound tag = super.load();
		minDmg = tag.getInt("min_dmg");
		maxDmg = tag.getInt("max_dmg");
		return tag;
	}

	@Override
	public NBTTagCompound save() {
		NBTTagCompound tag = super.save();
		tag.setInt("min_dmg", minDmg);
		tag.setInt("max_dmg", maxDmg);
		return tag;
	}

	@Override
	public String buildName() {
		return super.buildName();
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = super.buildLore();
		lore.add("&7DMG: &c" + minDmg + " - " + maxDmg);
		return lore;
	}

	@Override
	public String getIdentity() {
		return IDENTITY;
	}

	@Override
	public int getMaxDura() {
		return Tiers.Durability.weapon(getTier());
	}

	public int getMinDMG() {
		return minDmg;
	}

	public void setMinDMG(int minDmg) {
		this.minDmg = minDmg;
	}

	public int getMaxDMG() {
		return maxDmg;
	}

	public void setMaxDMG(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public void setDMG(int min, int max) {
		minDmg = min;
		maxDmg = max;
	}
}
