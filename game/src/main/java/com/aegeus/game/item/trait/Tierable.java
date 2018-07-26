package com.aegeus.game.item.trait;

import com.aegeus.game.item.util.Item;
import com.aegeus.game.item.util.ItemUtils;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public interface Tierable extends Item {
	int getTier();

	void setTier(int tier);

	@Override
	default void save() {
		NBTTagCompound tag = ItemUtils.getTag(getItem());
		setTier(tag.getInt("tier"));
	}

	@Override
	default void load() {
		NBTTagCompound tag = ItemUtils.getTag(getItem());
		tag.setInt("tier", getTier());
		ItemUtils.setTag(getItem(), tag);
	}
}
