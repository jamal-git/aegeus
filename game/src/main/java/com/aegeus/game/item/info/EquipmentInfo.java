package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public interface EquipmentInfo extends ItemInfo {
	static <T extends AgItem & EquipmentInfo> void impo(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		t.setTier((info.hasKey("tier")) ? info.getInt("tier") : 0);
		t.setRarity((info.hasKey("rarity")) ? Rarity.fromID(info.getInt("rarity")) : null);
		t.setEnchant((info.hasKey("enchant")) ? info.getInt("enchant") : 0);

		t.setStrength((info.hasKey("strength")) ? info.getInt("strength") : 0);
		t.setDexterity((info.hasKey("dexterity")) ? info.getInt("dexterity") : 0);
		t.setIntellect((info.hasKey("intellect")) ? info.getInt("intellect") : 0);
		t.setVitality((info.hasKey("vitality")) ? info.getInt("vitality") : 0);
	}

	static <T extends AgItem & EquipmentInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		info.setInt("tier", t.getTier());
		if (t.getRarity() != null) info.setInt("rarity", t.getRarity().getId());
		info.setInt("enchant", t.getEnchant());
		info.setInt("strength", t.getStrength());
		info.setInt("dexterity", t.getDexterity());
		info.setInt("intellect", t.getIntellect());
		info.setInt("vitality", t.getVitality());
		t.setAegeusInfo(info);
	}

	static <T extends AgItem & EquipmentInfo> String buildNamePrefix(T t) {
		String name = "";
		if (t.getEnchant() > 0) name += "&e[+" + t.getEnchant() + "]&f ";
		return name;
	}

	static <T extends AgItem & EquipmentInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		if (t.getStrength() > 0) lore.add(Util.colorCodes("&cSTR: +" + t.getStrength()));
		if (t.getDexterity() > 0) lore.add(Util.colorCodes("&cDEX: +" + t.getDexterity()));
		if (t.getIntellect() > 0) lore.add(Util.colorCodes("&cINT: +" + t.getIntellect()));
		if (t.getVitality() > 0) lore.add(Util.colorCodes("&cVIT: +" + t.getVitality()));
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

	int getStrength();

	void setStrength(int strength);

	int getDexterity();

	void setDexterity(int dexterity);

	int getIntellect();

	void setIntellect(int intellect);

	int getVitality();

	void setVitality(int vitality);
}
