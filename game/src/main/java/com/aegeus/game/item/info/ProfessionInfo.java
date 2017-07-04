package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silvre on 7/1/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public interface ProfessionInfo extends ItemInfo {
	static <T extends AgItem & ProfessionInfo> void impo(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		t.setLevel(info.hasKey("level") ? info.getInt("level") : 0);
		t.setXp(info.hasKey("xp") ? info.getInt("xp") : 0);

	}

	static <T extends AgItem & ProfessionInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		info.set("level", new NBTTagInt(t.getLevel()));
		info.set("xp", new NBTTagInt(t.getXp()));
		t.setAegeusInfo(info);
	}

	static <T extends AgItem & ProfessionInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&7Level: " + ProfessionTier.getTierByLevel(t.getLevel()).getColor() + t.getLevel()));
		lore.add(Util.colorCodes("&7" + t.getXp() + " / " + t.getRequiredXp() + "(" + new DecimalFormat("####0.00%").format((t.getXp() + 0.0) / t.getRequiredXp()) + ")"));
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

	default void subtractXp(int i) {
		setXp(getXp() - i);
	}

	int getRequiredXp();
}
