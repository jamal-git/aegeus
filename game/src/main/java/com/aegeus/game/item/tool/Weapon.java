package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.info.EquipmentInfo;
import com.aegeus.game.item.info.ItemInfo;
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

public class Weapon extends AgItem implements ItemInfo {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private EquipmentInfo equipmentInfo;
	private LevelInfo levelInfo;

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
		super(material);
		equipmentInfo = new EquipmentInfo(this);
		levelInfo = new LevelInfo(this);
	}

	public Weapon(ItemStack item) {
		super(item);
		equipmentInfo = new EquipmentInfo(this);
		levelInfo = new LevelInfo(this);

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

	public static boolean verify(ItemStack item) {
		AgItem agItem = new AgItem(item);
		NBTTagCompound info = agItem.getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("weapon");
	}

	public EquipmentInfo getEquipmentInfo() {
		return equipmentInfo;
	}

	public void setEquipmentInfo(EquipmentInfo equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}

	public LevelInfo getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(LevelInfo levelInfo) {
		this.levelInfo = levelInfo;
	}

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
		lore.addAll(equipmentInfo.buildLore());
		lore.addAll(levelInfo.buildLore());
		return lore;
	}

	@Override
	public void store() {
		equipmentInfo.store();
		levelInfo.store();

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
	public ItemStack build() {
		store();
		setLore(buildLore());
		return super.build();
	}

}
