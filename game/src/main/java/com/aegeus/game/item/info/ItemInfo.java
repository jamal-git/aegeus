package com.aegeus.game.item.info;

import com.aegeus.game.item.AegeusItem;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import java.util.List;

public interface ItemInfo {
	List<String> buildLore();
	void store();
}
