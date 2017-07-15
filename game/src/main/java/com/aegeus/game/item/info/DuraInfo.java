package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

public interface DuraInfo extends ItemInfo {
	static <T extends DuraInfo> void impo(T t) {
		NBTTagCompound info = getDuraInfo(t.getItem());
		t.setMaxDura((info.hasKey("maxDura")) ? info.getInt("maxDura") : 0);
		t.setDura((info.hasKey("dura")) ? info.getInt("dura") : t.getMaxDura());
	}

	static <T extends DuraInfo> void store(T t) {
		NBTTagCompound info = getDuraInfo(t.getItem());
		info.setInt("maxDura", t.getMaxDura());
		info.setInt("dura", t.getDura());
		t.setItem(setDuraInfo(t.getItem(), info));
	}

	static <T extends DuraInfo> void update(T t) {
		if (t.getMaterial().getMaxDurability() > 0 && t.getMaxDura() > 0) {
			t.getItem().setDurability((short) (t.getMaterial().getMaxDurability()
					- (((float) t.getDura() / (float) t.getMaxDura()) * t.getMaterial().getMaxDurability())));
		}
	}

	static boolean hasDuraInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("DuraInfo");
	}

	static NBTTagCompound getDuraInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return (tag.hasKey("DuraInfo")) ? tag.getCompound("DuraInfo") : new NBTTagCompound();
	}

	static ItemStack setDuraInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("DuraInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	int getMaxDura();

	void setMaxDura(int maxDura);

	int getDura();

	void setDura(int dura);

	default void addDura(int i) {
		setDura(getDura() + i);
	}

	default void subtractDura(int i) {
		setDura(getDura() - i);
	}
}
