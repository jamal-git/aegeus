package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

public interface DuraInfo {
	static <T extends AgItem & DuraInfo> void impo(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		t.setMaxDura((info.hasKey("maxDura")) ? info.getInt("maxDura") : 0);
		t.setDura((info.hasKey("dura")) ? info.getInt("dura") : t.getMaxDura());
	}

	static <T extends AgItem & DuraInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		info.setInt("maxDura", t.getMaxDura());
		info.setInt("dura", t.getDura());
		t.setAegeusInfo(info);
	}

	static <T extends AgItem & DuraInfo> void update(T t) {
		if (t.getMaterial().getMaxDurability() > 0 && t.getMaxDura() > 0) {
			t.getItem().setDurability((short) (t.getMaterial().getMaxDurability()
					- (((float) t.getDura() / (float) t.getMaxDura()) * t.getMaterial().getMaxDurability())));
		}
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
