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

public class Armor extends AgItem implements EquipmentInfo, LevelInfo {
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

	// Armor Stats
	private int hp = 0;
	private int hpRegen = 0;
	private float energyRegen = 0;
	private float defense = 0;
	private float magicRes = 0;
	private float block = 0;
	private float thorns = 0;

	public Armor(Material material) {
		super(new ItemStack(material));
	}

	public Armor(ItemStack item) {
		super(item);
		impo();
	}

	@Override
	public void impo() {
		EquipmentInfo.impo(this);
		LevelInfo.impo(this);

		NBTTagCompound info = getAegeusInfo();
		hp = (info.hasKey("hp")) ? info.getInt("hp") : 0;
		hpRegen = (info.hasKey("hpRegen")) ? info.getInt("hpRegen") : 0;
		energyRegen = (info.hasKey("energyRegen")) ? info.getFloat("energyRegen") : 0;
		defense = (info.hasKey("defense")) ? info.getFloat("defense") : 0;
		magicRes = (info.hasKey("magicRes")) ? info.getFloat("magicRes") : 0;
		block = (info.hasKey("block")) ? info.getFloat("block") : 0;
		thorns = (info.hasKey("thorns")) ? info.getFloat("thorns") : 0;
	}

	@Override
	public void store() {
		EquipmentInfo.store(this);
		LevelInfo.store(this);

		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("armor"));
		info.set("hp", new NBTTagInt(hp));
		info.set("hpRegen", new NBTTagInt(hpRegen));
		info.set("energyRegen", new NBTTagFloat(energyRegen));
		info.set("defense", new NBTTagFloat(defense));
		info.set("magicRes", new NBTTagFloat(magicRes));
		info.set("block", new NBTTagFloat(block));
		info.set("thorns", new NBTTagFloat(thorns));
		setAegeusInfo(info);
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		if (hp > 0) lore.add(Util.colorCodes("&cHP: +" + hp));
		if (hpRegen > 0) lore.add(Util.colorCodes("&cHP REGEN: +" + hpRegen + "/s"));
		if (energyRegen > 0) lore.add(Util.colorCodes("&cENERGY REGEN: +" + Math.round(energyRegen * 100) + "%"));
		if (defense > 0) lore.add(Util.colorCodes("&cDEFENSE: " + Math.round(defense * 100) + "%"));
		if (magicRes > 0) lore.add(Util.colorCodes("&cMAGIC RES: " + Math.round(magicRes * 100) + "%"));
		if (block > 0) lore.add(Util.colorCodes("&cBLOCK: " + Math.round(block * 100) + "%"));
		if (thorns > 0) lore.add(Util.colorCodes("&cTHORNS: " + Math.round(thorns * 100) + "%"));
		lore.addAll(EquipmentInfo.super.buildLore());
		lore.addAll(LevelInfo.super.buildLore());
		return lore;
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("armor");
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
		return (int) Util.calcMaxXP(getLevel());
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
	Armor Methods
	 */

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHpRegen() {
		return hpRegen;
	}

	public void setHpRegen(int hpRegen) {
		this.hpRegen = hpRegen;
	}

	public float getEnergyRegen() {
		return energyRegen;
	}

	public void setEnergyRegen(float energyRegen) {
		this.energyRegen = energyRegen;
	}

	public float getDefense() {
		return defense;
	}

	public void setDefense(float defense) {
		this.defense = defense;
	}

	public float getMagicRes() {
		return magicRes;
	}

	public void setMagicRes(float magicRes) {
		this.magicRes = magicRes;
	}

	public float getBlock() {
		return block;
	}

	public void setBlock(float block) {
		this.block = block;
	}

	public float getThorns() {
		return thorns;
	}

	public void setThorns(float thorns) {
		this.thorns = thorns;
	}

}
