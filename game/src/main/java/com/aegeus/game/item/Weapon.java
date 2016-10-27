package com.aegeus.game.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_10_R1.NBTTagFloat;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aegeus.game.util.Utility;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagInt;

public class Weapon extends Item {
	
	private int tier = 0;
	private int level = 0;
	private int xp = 0;
	private Rarity rarity = Rarity.NONE;

	private int minDmg = 0;
	private int maxDmg = 0;
	private int fireDmg = 0;
	private int iceDmg = 0;
	private float lifeSteal = 0;

	private int eStr = 0;
	private int eInt = 0;
	private int eVit = 0;
	
	public Weapon(Material material) {
		super(material);
	}
	
	public Weapon(ItemStack item){
		super(item);
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			tier = (aegeus.hasKey("tier")) ? aegeus.getInt("tier") : 0;
			level = (aegeus.hasKey("level")) ? aegeus.getInt("level") : 0;
			xp = (aegeus.hasKey("xp")) ? aegeus.getInt("xp") : 0;
			rarity = (aegeus.hasKey("rarity")) ? Rarity.getById(aegeus.getInt("rarity")) : Rarity.NONE;
			minDmg = (aegeus.hasKey("minDmg")) ? aegeus.getInt("minDmg") : 0;
			maxDmg = (aegeus.hasKey("maxDmg")) ? aegeus.getInt("maxDmg") : 0;
			fireDmg = (aegeus.hasKey("fireDmg")) ? aegeus.getInt("fireDmg") : 0;
			iceDmg = (aegeus.hasKey("iceDmg")) ? aegeus.getInt("iceDmg") : 0;
			lifeSteal = (aegeus.hasKey("lifeSteal")) ? aegeus.getFloat("lifeSteal") : 0;
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
	
	public void setDmg(int minDmg, int maxDmg){
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
	}
	
	public int getMinDmg(){
		return minDmg;
	}
	public int getMaxDmg(){
		return maxDmg;
	}
	
	public void setFireDmg(int fireDmg){
		this.fireDmg = fireDmg;
	}
	public int getFireDmg(){
		return fireDmg;
	}
	
	public void setIceDmg(int iceDmg){
		this.iceDmg = iceDmg;
	}
	public int getIceDmg(){
		return iceDmg;
	}
	
	public void setLifeSteal(float lifeSteal){
		this.lifeSteal = lifeSteal;
	}
	public double getLifeSteal(){ return lifeSteal; }

	public void setStr(int eStr) { this.eStr = eStr; }
	public int getStr() { return eStr; }
	public void setInt(int eInt) { this.eInt = eInt; }
	public int getInt() { return eInt; }
	public void setVit(int eVit) { this.eVit = eVit; }
	public int getVit() { return eVit; }
	
	@Override
	public List<String> getLore(){
		List<String> lore = new ArrayList<>();
		if(level >= 2)
			lore.add(Utility.colorCodes("&cDMG: " + minDmg + " - " + maxDmg + " &6(+" + Utility.calcWepLevelBuff(minDmg, level) + ")"));
		else
			lore.add(Utility.colorCodes("&cDMG: " + minDmg + " - " + maxDmg));
		if(fireDmg > 0) lore.add(Utility.colorCodes("&cFIRE DMG: +" + fireDmg));
		if(iceDmg > 0) lore.add(Utility.colorCodes("&cICE DMG: +" + iceDmg));
		if(lifeSteal > 0) lore.add(Utility.colorCodes("&cLIFE STEAL: +" + (lifeSteal * 100) + "%"));
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
		aegeus.set("minDmg", new NBTTagInt(minDmg));
		aegeus.set("maxDmg", new NBTTagInt(maxDmg));
		aegeus.set("fireDmg", new NBTTagInt(fireDmg));
		aegeus.set("iceDmg", new NBTTagInt(iceDmg));
		aegeus.set("lifeSteal", new NBTTagFloat(lifeSteal));
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
