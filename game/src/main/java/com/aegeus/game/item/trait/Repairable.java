package com.aegeus.game.item.trait;

import com.aegeus.game.item.util.Item;
import com.aegeus.game.item.util.ItemUtils;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Repairable extends Item {
	int getDura();

	void setDura(int dura);

	int getMaxDura();

	default int display(ItemStack i) {
		return display(i.getType());
	}

	default int display(Material m) {
		return (getDura() / getMaxDura()) * (m.getMaxDurability() - 1);
	}

	@Override
	default void load() {
		NBTTagCompound tag = ItemUtils.getTag(getItem());
		setDura(tag.getInt("dura"));
	}

	@Override
	default void save() {
		NBTTagCompound tag = ItemUtils.getTag(getItem());
		tag.setInt("dura", getDura());
		ItemUtils.setTag(getItem(), tag);
	}
}
