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

public class Armor extends AgItem implements ItemInfo {
	private EquipmentInfo equipmentInfo;
	private LevelInfo levelInfo;
	private int hp = 0;
	private int hpRegen = 0;
	private float energyRegen = 0;
	private float defense = 0;
	private float magicRes = 0;
	private float block = 0;
	private float thorns = 0;

	public Armor(Material material) {
		super(material);
		equipmentInfo = new EquipmentInfo(this);
		levelInfo = new LevelInfo(this);
	}

	public Armor(ItemStack item) {
		super(item);
		equipmentInfo = new EquipmentInfo(this);
		levelInfo = new LevelInfo(this);

		NBTTagCompound info = getAegeusInfo();
		hp = (info.hasKey("hp")) ? info.getInt("hp") : 0;
		hpRegen = (info.hasKey("hpRegen")) ? info.getInt("hpRegen") : 0;
		energyRegen = (info.hasKey("energyRegen")) ? info.getFloat("energyRegen") : 0;
		defense = (info.hasKey("defense")) ? info.getFloat("defense") : 0;
		magicRes = (info.hasKey("magicRes")) ? info.getFloat("magicRes") : 0;
		block = (info.hasKey("block")) ? info.getFloat("block") : 0;
		thorns = (info.hasKey("thorns")) ? info.getFloat("thorns") : 0;
	}

//	private List<ArmorRune> runes = new ArrayList<>();

	public static boolean verify(ItemStack item) {
		AgItem agItem = new AgItem(item);
		NBTTagCompound info = agItem.getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("armor");
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

	//public List<ArmorRune> getRunes() {
	//	return runes;
	//}

	public void setThorns(float thorns) {
		this.thorns = thorns;
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
		lore.addAll(equipmentInfo.buildLore());
		lore.addAll(levelInfo.buildLore());
		return lore;
	}

	@Override
	public void store() {
		NBTTagCompound info = getAegeusInfo();
		equipmentInfo.store();
		levelInfo.store();
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
	public ItemStack build() {
		store();
		setLore(buildLore());
		return super.build();
	}

}
