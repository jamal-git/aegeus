package com.aegeus.game.item;

import com.aegeus.game.item.util.ItemUtils;
import com.aegeus.game.item.util.Tiers;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item implements Tiered, Repairable {
	private int tier = 0;
	private int dura = 0;

	private int dmg = 1;

	public Weapon(ItemStack i) {
		super(i);
	}

	@Override
	public void load() {
		NBTTagCompound tag = ItemUtils.getTag(item);
		dmg = tag.getInt("dmg");
	}

	@Override
	public void save() {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.setInt("dmg", dmg);
		ItemUtils.setTag(item, tag);
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add("&7DMG: &c" + dmg);
		return lore;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
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
		return Tiers.Durability.weapon(tier);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && ((obj instanceof Weapon && dmg == ((Weapon) obj).getDmg())
				|| (obj instanceof ItemStack && new Weapon((ItemStack) obj).equals(this)));
	}
}
