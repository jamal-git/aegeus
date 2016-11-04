package com.aegeus.game.item.info;

import com.aegeus.game.item.AegeusItem;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Utility;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInfo implements ItemInfo {
	private AegeusItem item;
	private int tier = 0;
	private Rarity rarity;
	private int strength = 0;
	private int intelligence = 0;
	private int vitality = 0;

	public EquipmentInfo(AegeusItem item) {
		this.item = item;
		NBTTagCompound info = item.getAegeusInfo();
		tier = (info.hasKey("tier")) ? info.getInt("tier") : 0;
		rarity = (info.hasKey("rarity")) ? Rarity.getById(info.getInt("rarity")) : null;
		strength = (info.hasKey("strength")) ? info.getInt("strength") : 0;
		intelligence = (info.hasKey("intelligence")) ? info.getInt("intelligence") : 0;
		vitality = (info.hasKey("vitality")) ? info.getInt("vitality") : 0;
	}

	public int getTier() { return tier; }
	public void setTier(int tier) { this.tier = tier; }
	public Rarity getRarity() { return rarity; }
	public void setRarity(Rarity rarity) { this.rarity = rarity; }

	public int getStrength() { return strength; }
	public void setStrength(int strength) { this.strength = strength; }
	public int getIntelligence() { return intelligence; }
	public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
	public int getVitality() { return vitality; }
	public void setVitality(int vitality) { this.vitality = vitality; }

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		if(strength > 0) lore.add(Utility.colorCodes("&cSTR: " + strength));
		if(intelligence > 0) lore.add(Utility.colorCodes("&cINT: " + intelligence));
		if(vitality > 0) lore.add(Utility.colorCodes("&cVIT: " + vitality));
		if(rarity != null) lore.add(Utility.colorCodes(rarity.getLore()));
		return lore;
	}

	@Override
	public void store() {
		NBTTagCompound info = item.getAegeusInfo();
		info.setInt("tier", tier);
		info.setInt("rarity", rarity.getId());
		info.setInt("strength", strength);
		info.setInt("intelligence", intelligence);
		info.setInt("vitality", vitality);
		item.setAegeusInfo(info);
	}
}
