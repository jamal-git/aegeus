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

public class Armor extends AegeusItem implements ItemInfo {
	private EquipmentInfo equipmentInfo;
	private LevelInfo levelInfo;
	private int hp = 0;
	private int hpRegen = 0;
	private float energyRegen = 0;
	private float defense = 0;
	private float magicRes = 0;
	private float block = 0;

	public Armor(Material material) {
		super(material);
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
	}

//	private List<ArmorRune> runes = new ArrayList<>();
	
	public void setHp(int hp){
		this.hp = hp;
	}
	public int getHp(){
		return hp;
	}

	public void setHpRegen(int hpRegen){
		this.hpRegen = hpRegen;
	}
	public int getHpRegen(){
		return hpRegen;
	}
	public float getEnergyRegen() { return energyRegen; }
	public void setEnergyRegen(float energyRegen) { this.energyRegen = energyRegen; }

	public float getDefense() { return defense; }
	public void setDefense(float defense) { this.defense = defense; }
	public float getMagicRes() { return magicRes; }
	public void setMagicRes(float magicRes) { this.magicRes = magicRes; }
	public float getBlock() { return block; }
	public void setBlock(float block) { this.block = block; }

	//public List<ArmorRune> getRunes() {
	//	return runes;
	//}

	@Override
	public List<String> buildLore(){
		List<String> lore = new ArrayList<>();
		if(hp > 0) lore.add(Utility.colorCodes("&cHP: +" + hp));
		if(hpRegen > 0) lore.add(Utility.colorCodes("&cHP REGEN: +" + hpRegen + "/s"));
		if(energyRegen > 0) lore.add(Utility.colorCodes("&cENERGY REGEN: +" + Math.round(energyRegen * 100) + "%"));
		if(defense > 0) lore.add(Utility.colorCodes("&cDEFENSE: " + Math.round(defense * 100) + "%"));
		if(magicRes > 0) lore.add(Utility.colorCodes("&cMAGIC RES: " + Math.round(magicRes * 100) + "%"));
		if(block > 0) lore.add(Utility.colorCodes("&cBLOCK: " + Math.round(block * 100) + "%"));
		lore.addAll(equipmentInfo.buildLore());
		lore.addAll(levelInfo.buildLore());
		return lore;
	}

	@Override
	public void store(){
		NBTTagCompound info = getAegeusInfo();
		equipmentInfo.store();
		levelInfo.store();
		info.set("hp", new NBTTagInt(hp));
		info.set("hpRegen", new NBTTagInt(hpRegen));
		info.set("energyRegen", new NBTTagFloat(energyRegen));
		setAegeusInfo(info);
	}

	@Override
	public ItemStack build(){
		store();
		return buildDefault();
	}
	
}
