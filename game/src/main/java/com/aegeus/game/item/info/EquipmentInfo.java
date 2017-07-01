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
		t.setRarity((info.hasKey("rarity")) ? Rarity.fromID(info.getInt("rarity")) : null);

		t.setStrength((info.hasKey("strength")) ? info.getInt("strength") : 0);
		t.setDexterity((info.hasKey("dexterity")) ? info.getInt("dexterity") : 0);
		t.setIntellect((info.hasKey("intellect")) ? info.getInt("intellect") : 0);
		t.setVitality((info.hasKey("vitality")) ? info.getInt("vitality") : 0);
	}

	static <T extends AgItem & EquipmentInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		if (t.getRarity() != null) info.setInt("rarity", t.getRarity().getId());
		info.setInt("strength", t.getStrength());
		info.setInt("dexterity", t.getDexterity());
		info.setInt("intellect", t.getIntellect());
		info.setInt("vitality", t.getVitality());
		t.setAegeusInfo(info);
	}

	@Override
	default List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		if (getStrength() > 0) lore.add(Util.colorCodes("&cSTR: +" + getStrength()));
		if (getDexterity() > 0) lore.add(Util.colorCodes("&cDEX: +" + getDexterity()));
		if (getIntellect() > 0) lore.add(Util.colorCodes("&cINT: +" + getIntellect()));
		if (getVitality() > 0) lore.add(Util.colorCodes("&cVIT: +" + getVitality()));
		if (getRarity() != null) lore.add(Util.colorCodes(getRarity().getLore()));
		return lore;
	}

	Rarity getRarity();

	void setRarity(Rarity rarity);

	int getStrength();

	void setStrength(int strength);

	int getDexterity();

	void setDexterity(int dexterity);

	int getIntellect();

	void setIntellect(int intellect);

	int getVitality();

	void setVitality(int vitality);
}
