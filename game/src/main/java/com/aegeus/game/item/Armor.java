package com.aegeus.game.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aegeus.game.util.Utility;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import net.minecraft.server.v1_10_R1.NBTTagInt;

public class Armor extends Item {
	
	private int tier = 0;
	private int level = 0;
	private int xp = 0;
	private Rarity rarity = Rarity.NONE;

	private int hp = 0;
	private int hpRegen = 0;
	private float energyRegen = 0;
	private float defense = 0;
	private float magicRes = 0;
	private float block = 0;

	private int eStr = 0;
	private int eInt = 0;
	private int eVit = 0;

	//private List<ArmorRune> runes = new ArrayList<>();
	
	public Armor(Material material) {
		super(material);
	}
	
	public Armor(ItemStack item){
		super(item);
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			tier = (aegeus.hasKey("tier")) ? aegeus.getInt("tier") : 0;
			level = (aegeus.hasKey("level")) ? aegeus.getInt("level") : 0;
			xp = (aegeus.hasKey("xp")) ? aegeus.getInt("xp") : 0;
			rarity = (aegeus.hasKey("rarity")) ? Rarity.getById(aegeus.getInt("rarity")) : Rarity.NONE;
			hp = (aegeus.hasKey("hp")) ? aegeus.getInt("hp") : 0;
			hpRegen = (aegeus.hasKey("hpRegen")) ? aegeus.getInt("hpRegen") : 0;
			energyRegen = (aegeus.hasKey("energyRegen")) ? aegeus.getFloat("energyRegen") : 0;
			defense = (aegeus.hasKey("defense")) ? aegeus.getFloat("defense") : 0;
			magicRes = (aegeus.hasKey("magicRes")) ? aegeus.getFloat("magicRes") : 0;
			block = (aegeus.hasKey("block")) ? aegeus.getFloat("block") : 0;
		}
	}

	public void setTier(int tier){ this.tier = tier; }
	public int getTier() { return tier; }

	public void setLevel(int level) { this.level = level; }
	public int getLevel() { return level; }

	public void setXp(int xp){
		this.xp = xp;
		if(xp >= Utility.calcMaxXP(level)) {
			this.xp = 0;
			this.level += 1;
		}
	}
	public void addXp(int xp) { setXp(getXp() + xp); }
	public int getXp(){ return xp; }

	public void setRarity(Rarity rarity){
		this.rarity = rarity;
	}
	public Rarity getRarity(){
		return rarity;
	}
	
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

	public void setStr(int eStr) { this.eStr = eStr; }
	public int getStr() { return eStr; }
	public void setInt(int eInt) { this.eInt = eInt; }
	public int getInt() { return eInt; }
	public void setVit(int eVit) { this.eVit = eVit; }
	public int getVit() { return eVit; }
	
	//public List<ArmorRune> getRunes() {
	//	return runes;
	//}
	
	public List<String> getLore(){
		List<String> lore = new ArrayList<>();
		if(level >= 2)
			lore.add(Utility.colorCodes("&cHP: +" + hp + " &a(+" + Utility.calcLevelBuff(hp, level) + ")"));
		else
			lore.add(Utility.colorCodes("&cHP: +" + hp));
		if(hpRegen > 0) lore.add(Utility.colorCodes("&cHP REGEN: +" + hpRegen + "/s"));
		if(energyRegen > 0) lore.add(Utility.colorCodes("&cENERGY REGEN: +" + Math.round(energyRegen * 100) + "%"));
		if(defense > 0) lore.add(Utility.colorCodes("&cDEFENSE: " + Math.round(defense * 100) + "%"));
		if(magicRes > 0) lore.add(Utility.colorCodes("&cMAGIC RES: " + Math.round(magicRes * 100) + "%"));
		if(block > 0) lore.add(Utility.colorCodes("&cBLOCK: " + Math.round(block * 100) + "%"));
		if(eStr > 0) lore.add(Utility.colorCodes("&cSTR: +" + eStr));
		if(eInt > 0) lore.add(Utility.colorCodes("&cINT: +" + eInt));
		if(eVit > 0) lore.add(Utility.colorCodes("&cVIT: +" + eVit));
		if(level >= 1) {
			int maxXp = Math.round(Utility.calcMaxXP(level));
			lore.add(Utility.colorCodes("&6&oLevel " + level + " &7&o(" + Math.round((xp / maxXp) * 100) + "%)"));
		}
		if(rarity != null && !rarity.getLore().equalsIgnoreCase(""))
			lore.add(Utility.colorCodes(rarity.getLore()));
		return lore;
	}
	
	@Override
	public ItemStack build(){
		ItemStack item = new ItemStack(material);
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagCompound aegeus = new NBTTagCompound();

		aegeus.set("tier", new NBTTagInt(tier));
		aegeus.set("level", new NBTTagInt(level));
		aegeus.set("xp", new NBTTagInt(xp));
		aegeus.set("rarity", new NBTTagInt(rarity.getId()));
		aegeus.set("hp", new NBTTagInt(hp));
		aegeus.set("hpRegen", new NBTTagInt(hpRegen));
		aegeus.set("energyRegen", new NBTTagFloat(energyRegen));
		aegeus.set("eStr", new NBTTagInt(eStr));
		aegeus.set("eInt", new NBTTagInt(eInt));
		aegeus.set("eVit", new NBTTagInt(eVit));

		compound.set("AegeusInfo", aegeus);
		nmsStack.setTag(compound);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		lore = getLore();
		ItemMeta meta = item.getItemMeta();
		if(name!=null) meta.setDisplayName(name);
		if(lore!=null) meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(meta);
		return item;
	}
	
}
