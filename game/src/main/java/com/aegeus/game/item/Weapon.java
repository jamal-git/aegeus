package com.aegeus.game.item;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aegeus.game.util.Helper;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagDouble;
import net.minecraft.server.v1_10_R1.NBTTagInt;

public class Weapon extends Item {
	
	private int tier = 0;
	
	private int minDmg = 0;
	private int maxDmg = 0;
	
	private int fireDmg = 0;
	private int iceDmg = 0;
	private double lifeSteal = 0;
	
	private int level = 0;
	private int xp = 0;
	
	private Rarity rarity = Rarity.NONE;
	
	public Weapon(Material material) {
		super(material);
	}
	
	public Weapon(ItemStack item){
		super(item);
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			tier = (aegeus.hasKey("tier")) ? aegeus.getInt("tier") : 0;
			minDmg = (aegeus.hasKey("minDmg")) ? aegeus.getInt("minDmg") : 0;
			maxDmg = (aegeus.hasKey("maxDmg")) ? aegeus.getInt("maxDmg") : 0;
			fireDmg = (aegeus.hasKey("fireDmg")) ? aegeus.getInt("fireDmg") : 0;
			iceDmg = (aegeus.hasKey("iceDmg")) ? aegeus.getInt("iceDmg") : 0;
			lifeSteal = (aegeus.hasKey("lifeSteal")) ? aegeus.getFloat("lifeSteal") : 0;
			level = (aegeus.hasKey("level")) ? aegeus.getInt("level") : 0;
			xp = (aegeus.hasKey("xp")) ? aegeus.getInt("xp") : 0;
			rarity = (aegeus.hasKey("rarity")) ? Rarity.getById(aegeus.getInt("rarity")) : Rarity.NONE;
		}
	}
	
	public void setTier(int tier){
		this.tier = tier;
	}
	
	public int getTier(){
		return tier;
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
	
	public void setLifeSteal(double lifeSteal){
		this.lifeSteal = lifeSteal;
	}
	
	public double getLifeSteal(){
		return lifeSteal;
	}
	
	public void setLevel(int level){
		this.level = level;
		this.xp = 0;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setXp(int xp){
		this.xp = xp;
	}
	
	public void addXp(int xp) {
		this.xp += xp;
	}
	
	public int getXp(){
		return xp;
	}
	
	public void setRarity(Rarity rarity){
		this.rarity = rarity;
	}
	
	public Rarity getRarity(){
		return rarity;
	}
	
	@Override
	public List<String> getLore(){
		List<String> lore = new ArrayList<>();
		if(level >= 2)
			lore.add(Helper.colorCodes("&cDMG: " + minDmg + " - " + maxDmg + " &6(+" + Helper.calcWepLevelBuff(minDmg, level) + ")"));
		else
			lore.add(Helper.colorCodes("&cDMG: " + minDmg + " - " + maxDmg));
		if(fireDmg >= 1) lore.add(Helper.colorCodes("&cFIRE DMG: +" + fireDmg));
		if(iceDmg >= 1) lore.add(Helper.colorCodes("&cICE DMG: +" + iceDmg));
		if(lifeSteal >= 1) lore.add(Helper.colorCodes("&cLIFE STEAL: +" + (lifeSteal * 100) + "%"));
		if(level >= 1) {
			int maxXp = Math.round(Helper.calcMaxXP(level));
			lore.add(Helper.colorCodes("&6&oLevel " + level + " &7&o(" + Math.round((xp / maxXp) * 100) + "%)"));
		}
		if(rarity != null && !rarity.getLore().equalsIgnoreCase(""))
			lore.add(Helper.colorCodes(rarity.getLore()));
		return lore;
	}
	
	@Override
	public ItemStack build(){
		ItemStack item = new ItemStack(material);
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagCompound aegeus = new NBTTagCompound();
		aegeus.set("tier", new NBTTagInt(tier));
		aegeus.set("minDmg", new NBTTagInt(minDmg));
		aegeus.set("maxDmg", new NBTTagInt(maxDmg));
		aegeus.set("fireDmg", new NBTTagInt(fireDmg));
		aegeus.set("iceDmg", new NBTTagInt(iceDmg));
		aegeus.set("lifeSteal", new NBTTagDouble(lifeSteal));
		aegeus.set("level", new NBTTagInt(level));
		aegeus.set("xp", new NBTTagInt(xp));
		aegeus.set("rarity", new NBTTagInt(rarity.getId()));
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
