package com.aegeus.game.item.info;

import com.aegeus.game.item.AegeusItem;
import com.aegeus.game.util.Utility;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class LevelInfo implements ItemInfo {
	private AegeusItem item;
	private int level = 0;
	private int xp = 0;

	public LevelInfo(AegeusItem item) {
		this.item = item;
		NBTTagCompound info = item.getAegeusInfo();
		level = (info.hasKey("level")) ? info.getInt("level") : 0;
		xp = (info.hasKey("xp")) ? info.getInt("xp") : 0;
	}

	public int getLevel() { return level; }
	public void setLevel(int level) {
		this.level = level;
		xp = 0;
	}
	public int getXp() { return xp; }
	public boolean setXp(int xp) {
		this.xp = xp;
		if(xp >= Utility.calcMaxXP(level)) {
			setLevel(level + 1);
			return true;
		}
		return false;
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		if(level > 0) lore.add(Utility.colorCodes("&6&oLevel " + (level + 1) + " &7&o("
				+ ((xp / Utility.calcMaxXP(level)) * 100) + ")"));
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
