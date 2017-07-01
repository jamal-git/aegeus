package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class LevelInfo implements ItemInfo {
	private final AgItem item;
	private int level = 0;
	private int xp = 0;

	public LevelInfo(AgItem item) {
		this.item = item;
		NBTTagCompound info = item.getAegeusInfo();
		level = (info.hasKey("level")) ? info.getInt("level") : 0;
		xp = (info.hasKey("xp")) ? info.getInt("xp") : 0;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		if (this.level > 99) this.level = 99;
		xp = 0;
	}

	public void addLevel(int i) {
		setLevel(getLevel() + 1);
	}

	public int getXp() {
		return xp;
	}

	public boolean setXp(int xp) {
		this.xp = xp;
		if (xp >= Util.calcMaxXP(level) && level < 99) {
			setLevel(level + 1);
			return true;
		}
		return false;
	}

	public boolean addXp(int i) {
		return setXp(getXp() + i);
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&6&oLevel " + (level + 1) + " &7&o("
				+ Math.round(((float) xp / (float) Util.calcMaxXP(level)) * 100) + "%)"));
		return lore;
	}

	@Override
	public void store() {
		NBTTagCompound info = item.getAegeusInfo();
		info.setInt("level", level);
		info.setInt("xp", xp);
		item.setAegeusInfo(info);
	}
}
