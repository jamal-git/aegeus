package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInfo implements ItemInfo {
	private final AgItem item;
	private int tier = 0;
	private Rarity rarity;
	private int strength = 0;
	private int intellect = 0;
	private int vitality = 0;
	private int dexterity = 0;

	public EquipmentInfo(AgItem item) {
		this.item = item;
		NBTTagCompound info = item.getAegeusInfo();
		tier = (info.hasKey("tier")) ? info.getInt("tier") : 0;
		rarity = (info.hasKey("rarity")) ? Rarity.fromID(info.getInt("rarity")) : null;
		strength = (info.hasKey("strength")) ? info.getInt("strength") : 0;
		intellect = (info.hasKey("intellect")) ? info.getInt("intellect") : 0;
		vitality = (info.hasKey("vitality")) ? info.getInt("vitality") : 0;
		dexterity = (info.hasKey("dexterity")) ? info.getInt("dexterity") : 0;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntellect() {
		return intellect;
	}

	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		if (strength > 0) lore.add(Util.colorCodes("&cSTR: +" + strength));
		if (intellect > 0) lore.add(Util.colorCodes("&cINT: +" + intellect));
		if (vitality > 0) lore.add(Util.colorCodes("&cVIT: +" + vitality));
		if (dexterity > 0) lore.add(Util.colorCodes("&cDEX: +" + dexterity));
		if (rarity != null) lore.add(Util.colorCodes(rarity.getLore()));
		return lore;
	}

	@Override
	public void store() {
		NBTTagCompound info = item.getAegeusInfo();
		info.setInt("tier", tier);
		if (rarity != null) info.setInt("rarity", rarity.getId());
		info.setInt("strength", strength);
		info.setInt("intellect", intellect);
		info.setInt("vitality", vitality);
		info.setInt("dexterity", dexterity);
		item.setAegeusInfo(info);
	}
}
