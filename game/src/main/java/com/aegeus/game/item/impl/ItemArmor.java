package com.aegeus.game.item.impl;

import com.aegeus.game.item.Tiers;
import com.aegeus.game.item.trait.Repairable;
import com.aegeus.game.item.trait.Tierable;
import com.aegeus.game.item.util.ItemWrapper;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemArmor extends ItemWrapper implements Tierable, Repairable {
	public static final String IDENTITY = "armor";

	private int hp = 1;

	public ItemArmor(ItemStack item) {
		super(item);
	}

	public ItemArmor(Material material) {
		super(material);
	}

	@Override
	public NBTTagCompound load() {
		NBTTagCompound tag = super.load();
		hp = tag.getInt("hp");
		return tag;
	}

	@Override
	public NBTTagCompound save() {
		NBTTagCompound tag = super.save();
		tag.setInt("hp", hp);
		return tag;
	}

	@Override
	public String buildName() {
		return super.buildName();
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = super.buildLore();
		lore.add("&7HP: &c+" + hp);
		return lore;
	}

	@Override
	public int getMaxDura() {
		return Tiers.Durability.armor(getTier());
	}

	@Override
	public String getIdentity() {
		return IDENTITY;
	}

	public int getHP() {
		return hp;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}
}
