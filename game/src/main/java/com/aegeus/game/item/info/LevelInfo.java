package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface LevelInfo extends ItemInfo {
	static <T extends LevelInfo> void impo(T t) {
		NBTTagCompound info = getLevelInfo(t.getItem());
		t.setLevel((info.hasKey("level")) ? info.getInt("level") : 0);
		t.setXp((info.hasKey("xp")) ? info.getInt("xp") : 0);
	}

	static <T extends LevelInfo> void store(T t) {
		NBTTagCompound info = getLevelInfo(t.getItem());
		info.setInt("level", t.getLevel());
		info.setInt("xp", t.getXp());
		t.setItem(setLevelInfo(t.getItem(), info));
	}

	static <T extends LevelInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&6&oLevel " + (t.getLevel() + 1) + " &7&o("
				+ Math.round(((float) t.getXp() / (float) t.getMaxXp()) * 100) + "%)"));
		return lore;
	}

	static boolean hasLevelInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("LevelInfo");
	}

	static NBTTagCompound getLevelInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasLevelInfo(item) ? tag.getCompound("LevelInfo") : new NBTTagCompound();
	}

	static ItemStack setLevelInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("LevelInfo", info);
		return ItemUtils.setTag(item, tag);
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

	default int getXpPercent() {
		return Math.round(((float) getXp() / (float) getMaxXp()) * 100);
	}

	int getMaxXp();

}
