package com.aegeus.game.item.trait;

import com.aegeus.game.item.util.ItemUtils;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Repairable extends ItemTrait {
	default int getDura() {
		return ItemUtils.getTag(getItem()).getInt("dura");
	}

	default void setDura(int dura) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInt("dura", dura);
		ItemUtils.setTag(getItem(), tag);
	}

	int getMaxDura();

	default int display(ItemStack i) {
		return display(i.getType());
	}

	default int display(Material m) {
		return (getDura() / getMaxDura()) * (m.getMaxDurability() - 1);
	}
}
