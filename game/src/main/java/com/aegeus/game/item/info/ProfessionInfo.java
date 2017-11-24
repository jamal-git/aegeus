package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public interface ProfessionInfo extends ItemInfo {
	static <T extends ProfessionInfo> void impo(T t) {
		NBTTagCompound info = getProfessionInfo(t.getItem());
		t.setLevel(info.hasKey("level") ? info.getInt("level") : 0);
		t.setXp(info.hasKey("xp") ? info.getInt("xp") : 0);
	}

	static <T extends ProfessionInfo> void store(T t) {
		NBTTagCompound info = getProfessionInfo(t.getItem());
		info.set("level", new NBTTagInt(t.getLevel()));
		info.set("xp", new NBTTagInt(t.getXp()));
		t.setItem(setProfessionInfo(t.getItem(), info));
	}

	static <T extends ProfessionInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&7Level: " + ProfessionTier.getTierByLevel(t.getLevel()).getColor() + t.getLevel()));
		lore.add(Util.colorCodes("&7" + t.getXp() + " / " + t.getRequiredXp() + "(" + new DecimalFormat("####0.00%").format((t.getXp() + 0.0) / t.getRequiredXp()) + ")"));
		return lore;
	}

	static boolean hasProfessionInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("ProfessionInfo");
	}

	static NBTTagCompound getProfessionInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasProfessionInfo(item) ? tag.getCompound("ProfessionInfo") : new NBTTagCompound();
	}

	static ItemStack setProfessionInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("ProfessionInfo", info);
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

	int getRequiredXp();
}
