package com.aegeus.game.item.impl;

import com.aegeus.game.item.Tiers;
import com.aegeus.game.item.trait.Repairable;
import com.aegeus.game.item.trait.Tierable;
import com.aegeus.game.item.util.ItemWrapper;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemWeapon extends ItemWrapper implements Tierable, Repairable {
	public static final String IDENTITY = "weapon";

	public ItemWeapon(ItemStack item) {
		super(item);
	}

	@Override
	public void load() {
		super.load();
	}

	@Override
	public NBTTagCompound save() {
		NBTTagCompound tag = super.save();
		return tag;
	}

	@Override
	public String buildName() {
		return super.buildName();
	}

	@Override
	public List<String> buildLore() {
		return super.buildLore();
	}

	@Override
	public int getMaxDura() {
		return Tiers.Durability.weapon(getTier());
	}

	@Override
	public String getIdentity() {
		return IDENTITY;
	}
}
