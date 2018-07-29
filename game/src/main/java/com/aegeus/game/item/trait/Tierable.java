package com.aegeus.game.item.trait;

import com.aegeus.game.item.util.ItemUtils;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public interface Tierable extends ItemTrait {
	default int getTier() {
		return ItemUtils.getTag(getItem()).getInt("tier");
	}

	default void setTier(int tier) {
		NBTTagCompound tag = ItemUtils.getTag(getItem());
		tag.setInt("tier", tier);
		ItemUtils.setTag(getItem(), tag);
	}
}
