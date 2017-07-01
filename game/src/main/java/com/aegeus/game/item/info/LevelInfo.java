package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public interface LevelInfo extends ItemInfo {
	static <T extends AgItem & LevelInfo> void impo(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		t.setLevel((info.hasKey("level")) ? info.getInt("level") : 0);
		t.setXp((info.hasKey("xp")) ? info.getInt("xp") : 0);
	}

	static <T extends AgItem & LevelInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		info.setInt("level", t.getLevel());
		info.setInt("xp", t.getXp());
		t.setAegeusInfo(info);
	}

	@Override
	default List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&6&oLevel " + (getLevel() + 1) + " &7&o("
				+ Math.round(((float) getXp() / (float) Util.calcMaxXP(getLevel())) * 100) + "%)"));
		return lore;
	}

	int getLevel();

	void setLevel(int level);

	default void addLevel(int i) {
		setLevel(getLevel() + i);
	}

	int getXp();

	void setXp(int xp);

	default void addXp(int i) {
		setXp(getXp() + i);
	}
}
