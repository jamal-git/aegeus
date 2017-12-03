package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface DuraInfo extends ItemInfo {
	static <T extends DuraInfo> void impo(T t) {
		NBTTagCompound info = getDuraInfo(t.getItem());
		t.setMaxDura((info.hasKey("maxDura")) ? info.getInt("maxDura") : 0);
		t.setDura((info.hasKey("dura")) ? info.getInt("dura") : t.getMaxDura());
		t.setMaxReserves((info.hasKey("maxReserves")) ? info.getInt("maxReserves") : 0);
		t.setReserves((info.hasKey("reserves")) ? info.getInt("reserves") : 0);
	}

	static <T extends DuraInfo> void store(T t) {
		NBTTagCompound info = getDuraInfo(t.getItem());
		info.setInt("maxDura", t.getMaxDura());
		info.setInt("dura", t.getDura());
		info.setInt("maxReserves", t.getMaxReserves());
		info.setInt("reserves", t.getReserves());
		t.setItem(setDuraInfo(t.getItem(), info));
	}

	static <T extends DuraInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		if (t.getDura() <= 0 && t.getReserves() > 0)
			lore.add(Util.colorCodes("&4&lRESERVE:&4 " + t.getReserves() + " / " + t.getMaxReserves()));
		return lore;
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

	default void subDura(int i) {
		setDura(getDura() - i);
	}

	int getMaxReserves();

	void setMaxReserves(int reserves);

	int getReserves();

	void setReserves(int reserves);

	default void addReserves(int i) {
		setReserves(getReserves() + i);
	}

	default void subReserves(int i) {
		setReserves(getReserves() - i);
	}

	default boolean isInReserve() {
		return getDura() <= 0 && getReserves() > 0;
	}

}
