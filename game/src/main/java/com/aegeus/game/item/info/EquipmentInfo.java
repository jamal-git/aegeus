package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public interface EquipmentInfo {
	static <T extends AgItem & EquipmentInfo> void impo(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		t.setTier((info.hasKey("tier")) ? info.getInt("tier") : 0);
		t.setRarity((info.hasKey("rarity")) ? (info.getInt("rarity") == -1 ? null : Rarity.fromID(info.getInt("rarity"))) : null);
		t.setEnchant((info.hasKey("enchant")) ? info.getInt("enchant") : 0);
	}

	static <T extends AgItem & EquipmentInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		info.setInt("tier", t.getTier());
		info.setInt("rarity", t.getRarity() == null ? -1 : t.getRarity().getId());
		info.setInt("enchant", t.getEnchant());
		t.setAegeusInfo(info);
	}

	static <T extends AgItem & EquipmentInfo> String buildPrefix(T t) {
		String name = "";
		if (t.getEnchant() > 0) name += "&a(+" + t.getEnchant() + ")&f ";
		return Util.colorCodes(name);
	}

	static <T extends AgItem & EquipmentInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		if (t.getRarity() != null) lore.add(Util.colorCodes(t.getRarity().getLore()));
		return lore;
	}

	int getTier();

	void setTier(int tier);

	Rarity getRarity();

	void setRarity(Rarity rarity);

	int getEnchant();

	void setEnchant(int enchant);

	default void addEnchant(int i) {
		setEnchant(getEnchant() + i);
	}
}
