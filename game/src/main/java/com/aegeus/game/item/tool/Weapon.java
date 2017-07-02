package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.info.EquipmentInfo;
import com.aegeus.game.item.info.LevelInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends AgItem implements EquipmentInfo, LevelInfo {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	// Level Info
	private int level = 0;
	private int xp = 0;

	// Equipment Info
	private int tier = 0;
	private Rarity rarity = null;
	private int strength = 0;
	private int dexterity = 0;
	private int intellect = 0;
	private int vitality = 0;

	// Weapon Stats
	private int minDmg = 0;
	private int maxDmg = 0;
	private int fireDmg = 0;
	private int iceDmg = 0;
	private int poisonDmg = 0;
	private int pureDmg = 0;
	private float lifeSteal = 0;
	private float trueHearts = 0;
	private float blindness = 0;

	public Weapon(Material material) {
		super(new ItemStack(material));
	}

	public Weapon(ItemStack item) {
		super(item);
		impo();
	}

	@Override
	public void impo() {
		EquipmentInfo.impo(this);
		LevelInfo.impo(this);

		NBTTagCompound info = getAegeusInfo();
		minDmg = (info.hasKey("minDmg")) ? info.getInt("minDmg") : 0;
		maxDmg = (info.hasKey("maxDmg")) ? info.getInt("maxDmg") : 0;
		fireDmg = (info.hasKey("fireDmg")) ? info.getInt("fireDmg") : 0;
		iceDmg = (info.hasKey("iceDmg")) ? info.getInt("iceDmg") : 0;
		poisonDmg = (info.hasKey("poisonDmg")) ? info.getInt("poisonDmg") : 0;
		pureDmg = (info.hasKey("pureDmg")) ? info.getInt("pureDmg") : 0;
		lifeSteal = (info.hasKey("lifeSteal")) ? info.getFloat("lifeSteal") : 0;
		trueHearts = (info.hasKey("trueHearts")) ? info.getFloat("trueHearts") : 0;
		blindness = (info.hasKey("blindness")) ? info.getFloat("blindness") : 0;
	}

	@Override
	public void store() {
		EquipmentInfo.store(this);
		LevelInfo.store(this);

		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("weapon"));
		info.set("minDmg", new NBTTagInt(minDmg));
		info.set("maxDmg", new NBTTagInt(maxDmg));
		info.set("fireDmg", new NBTTagInt(fireDmg));
		info.set("iceDmg", new NBTTagInt(iceDmg));
		info.set("poisonDmg", new NBTTagInt(poisonDmg));
		info.set("pureDmg", new NBTTagInt(pureDmg));
		info.set("lifeSteal", new NBTTagFloat(lifeSteal));
		info.set("trueHearts", new NBTTagFloat(trueHearts));
		info.set("blindness", new NBTTagFloat(blindness));
		setAegeusInfo(info);
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&cDMG: " + minDmg + " - " + maxDmg));
		if (fireDmg > 0) lore.add(Util.colorCodes("&cFIRE DMG: +" + fireDmg));
		if (iceDmg > 0) lore.add(Util.colorCodes("&cICE DMG: +" + iceDmg));
		if (poisonDmg > 0) lore.add(Util.colorCodes("&cPOISON DMG: +" + poisonDmg));
		if (pureDmg > 0) lore.add(Util.colorCodes("&cPURE DMG: +" + pureDmg));
		if (lifeSteal > 0) lore.add(Util.colorCodes("&cLIFE STEAL: +" + Math.round(lifeSteal * 100) + "%"));
		if (trueHearts > 0) lore.add(Util.colorCodes("&cTRUE HEARTS: " + Math.round(trueHearts * 100) + "%"));
		if (blindness > 0) lore.add(Util.colorCodes("&cBLINDNESS: " + Math.round(blindness * 100) + "%"));
		lore.addAll(EquipmentInfo.super.buildLore());
		lore.addAll(LevelInfo.super.buildLore());
		return lore;
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("weapon");
	}

	@Override
	public ItemStack build() {
		store();
		setLore(buildLore());
		return super.build();
	}

	/*
	Info Overrides
	 */

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public int getMaxXp() {
		return (int) Util.calcMaxXP(level);
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public void setTier(int tier) {
		this.tier = tier;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	@Override
	public int getStrength() {
		return strength;
	}

	@Override
	public void setStrength(int strength) {
		this.strength = strength;
	}

	@Override
	public int getDexterity() {
		return dexterity;
	}

	@Override
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	@Override
	public int getIntellect() {
		return intellect;
	}

	@Override
	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	@Override
	public int getVitality() {
		return vitality;
	}

	@Override
	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	/*
	Weapon Methods
	 */

	public void setDmg(int minDmg, int maxDmg) {
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
	}

	public int getDmg() {
		return getMinDmg() == getMaxDmg() ? getMinDmg() : random.nextInt(getMinDmg(), getMaxDmg());
	}

	public int getMinDmg() {
		return minDmg;
	}

	public int getMaxDmg() {
		return maxDmg;
	}

	public int getFireDmg() {
		return fireDmg;
	}

	public void setFireDmg(int fireDmg) {
		this.fireDmg = fireDmg;
	}

	public int getIceDmg() {
		return iceDmg;
	}

	public void setIceDmg(int iceDmg) {
		this.iceDmg = iceDmg;
	}

	public int getPoisonDmg() {
		return poisonDmg;
	}

	public void setPoisonDmg(int poisonDmg) {
		this.poisonDmg = poisonDmg;
	}

	public int getPureDmg() {
		return pureDmg;
	}

	public void setPureDmg(int pureDmg) {
		this.pureDmg = pureDmg;
	}

	public float getLifeSteal() {
		return lifeSteal;
	}

	public void setLifeSteal(float lifeSteal) {
		this.lifeSteal = lifeSteal;
	}

	public float getTrueHearts() {
		return trueHearts;
	}

	public void setTrueHearts(float trueHearts) {
		this.trueHearts = trueHearts;
	}

	public float getBlindness() {
		return blindness;
	}

	public void setBlindness(float blindness) {
		this.blindness = blindness;
	}

}
