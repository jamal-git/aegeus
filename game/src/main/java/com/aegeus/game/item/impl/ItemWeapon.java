package com.aegeus.game.item.impl;

import com.aegeus.game.item.Tiers;
import com.aegeus.game.item.trait.Repairable;
import com.aegeus.game.item.trait.Tierable;
import com.aegeus.game.item.util.ItemWrapper;
import com.aegeus.game.util.IntRange;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemWeapon extends ItemWrapper implements Tierable, Repairable {
	public static final String IDENTITY = "weapon";

	private IntRange dmg = new IntRange(1);

	public ItemWeapon(ItemStack item) {
		super(item);
	}

	public ItemWeapon(Material material) {
		super(material);
	}

	@Override
	public NBTTagCompound load() {
		NBTTagCompound tag = super.load();
		dmg.setMin(tag.getInt("dmg_min"));
		dmg.setMax(tag.getInt("dmg_max"));
		return tag;
	}

	@Override
	public NBTTagCompound save() {
		NBTTagCompound tag = super.save();
		tag.setInt("dmg_min", dmg.getMin());
		tag.setInt("dmg_max", dmg.getMax());
		return tag;
	}

	@Override
	public String buildName() {
		return super.buildName();
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = super.buildLore();
		lore.add("&7DMG: &c" + dmg.getMin() + " - " + dmg.getMax());
		return lore;
	}

	@Override
	public String getIdentity() {
		return IDENTITY;
	}

	@Override
	public int getMaxDura() {
		return Tiers.Dura.weapon(getTier());
	}

	public int getMinDMG() {
		return dmg.getMin();
	}

	public void setMinDMG(int min) {
		dmg.setMin(Math.max(0, min));
	}

	public int getMaxDMG() {
		return dmg.getMax();
	}

	public void setMaxDMG(int max) {
		dmg.setMax(Math.max(0, max));
	}

	public int getDMG() {
		return dmg.get();
	}

	public void setDMG(IntRange dmg) {
		this.dmg = dmg;
	}

	public void setDMG(int min, int max) {
		dmg.setMin(min);
		dmg.setMax(max);
	}
}
