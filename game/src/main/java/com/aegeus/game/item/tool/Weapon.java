package com.aegeus.game.item.tool;

import com.aegeus.game.item.AegeusItem;
import com.aegeus.game.item.info.EquipmentInfo;
import com.aegeus.game.item.info.ItemInfo;
import com.aegeus.game.item.info.LevelInfo;
import com.aegeus.game.util.Utility;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends AegeusItem implements ItemInfo {
	private EquipmentInfo equipmentInfo;
	private LevelInfo levelInfo;
	private int minDmg = 0;
	private int maxDmg = 0;
	private int fireDmg = 0;
	private int iceDmg = 0;
	private float lifeSteal = 0;

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
		lifeSteal = (info.hasKey("lifeSteal")) ? info.getFloat("lifeSteal") : 0;
	}

	public EquipmentInfo getEquipmentInfo() {
		return equipmentInfo;
	}

	public LevelInfo getLevelInfo() {
		return levelInfo;
	}

	public void setDmg(int minDmg, int maxDmg) {
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
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

	public double getLifeSteal() {
		return lifeSteal;
	}

	public void setLifeSteal(float lifeSteal) {
		this.lifeSteal = lifeSteal;
	}

	@Override
	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add(Utility.colorCodes("&cDMG: " + minDmg + " - " + maxDmg));
		if (fireDmg > 0) lore.add(Utility.colorCodes("&cFIRE DMG: +" + fireDmg));
		if (iceDmg > 0) lore.add(Utility.colorCodes("&cICE DMG: +" + iceDmg));
		if (lifeSteal > 0) lore.add(Utility.colorCodes("&cLIFE STEAL: +" + Math.round(lifeSteal * 100) + "%"));
		lore.addAll(equipmentInfo.buildLore());
		lore.addAll(levelInfo.buildLore());
		return lore;
	}

	@Override
	public void store() {
		NBTTagCompound info = getAegeusInfo();
		equipmentInfo.store();
		levelInfo.store();
		info.set("minDmg", new NBTTagInt(minDmg));
		info.set("maxDmg", new NBTTagInt(maxDmg));
		info.set("fireDmg", new NBTTagInt(fireDmg));
		info.set("iceDmg", new NBTTagInt(iceDmg));
		info.set("lifeSteal", new NBTTagFloat(lifeSteal));
		setAegeusInfo(info);
	}

	@Override
	public ItemStack build() {
		store();
		setLore(buildLore());
		return buildDefault();
	}

}
