package com.aegeus.aegeus.game.item;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aegeus.aegeus.util.Helper;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagDouble;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagString;

public class ItemWeapon extends Item {
	
	private int MinDmg = 0;
	private int MaxDmg = 0;
	
	private int FireDmg = 0;
	private int IceDmg = 0;
	private double LifeSteal = 0;
	
	private int Tier = 0;
	private int Level = 0;
	private int XP = 0;
	
	private ItemRarity Rarity = null;
	
	public ItemWeapon(Material material) {
		super(material);
	}
	
	public ItemWeapon(ItemStack item){
		super(item);
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			MinDmg = aegeus.getInt("MinDmg");
			MaxDmg = aegeus.getInt("MaxDmg");
			FireDmg = (aegeus.hasKey("FireDmg")) ? aegeus.getInt("FireDmg") : 0;
			IceDmg = (aegeus.hasKey("IceDmg")) ? aegeus.getInt("IceDmg") : 0;
			LifeSteal = (aegeus.hasKey("LifeSteal")) ? aegeus.getDouble("LifeSteal") : 0;
			Tier = aegeus.getInt("Tier");
			Level = (aegeus.hasKey("Level")) ? aegeus.getInt("Level") : 0;
			XP = (aegeus.hasKey("XP")) ? aegeus.getInt("XP") : 0;
			Rarity = (aegeus.hasKey("Rarity")) ? ItemRarity.fromTypeID(aegeus.getInt("Rarity")) : null;
		}
	}
	
	public void setDmg(int MinDmg, int MaxDmg){
		this.MinDmg = MinDmg;
		this.MaxDmg = MaxDmg;
	}
	
	public int getMinDmg(){
		return MinDmg;
	}
	
	public int getMaxDmg(){
		return MaxDmg;
	}
	
	public void setFireDmg(int FireDmg){
		this.FireDmg = FireDmg;
	}
	
	public int getFireDmg(){
		return FireDmg;
	}
	
	public void setIceDmg(int IceDmg){
		this.IceDmg = IceDmg;
	}
	
	public int getIceDmg(){
		return IceDmg;
	}
	
	public void setLifeSteal(double LifeSteal){
		this.LifeSteal = LifeSteal;
	}
	
	public double getLifeSteal(){
		return LifeSteal;
	}
	
	public void setTier(int Tier){
		this.Tier = Tier;
	}
	
	public int getTier(){
		return Tier;
	}
	
	public void setLevel(int Level){
		this.Level = Level;
		this.XP = 0;
	}
	
	public int getLevel(){
		return Level;
	}
	
	public void setXP(int XP){
		this.XP = XP;
	}
	
	public int getXP(){
		return XP;
	}
	
	public void setRarity(ItemRarity Rarity){
		this.Rarity = Rarity;
	}
	
	public ItemRarity getRarity(){
		return Rarity;
	}
	
	public static List<String> getLore(ItemStack item){
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			int MinDmg = aegeus.getInt("MinDmg");
			int MaxDmg = aegeus.getInt("MaxDmg");
			int FireDmg = (aegeus.hasKey("FireDmg")) ? aegeus.getInt("FireDmg") : 0;
			int IceDmg = (aegeus.hasKey("IceDmg")) ? aegeus.getInt("IceDmg") : 0;
			int LifeSteal = (aegeus.hasKey("LifeSteal")) ? aegeus.getInt("LifeSteal") : 0;
			int Level = (aegeus.hasKey("Level")) ? aegeus.getInt("Level") : 0;
			int XP = (aegeus.hasKey("XP")) ? aegeus.getInt("XP") : 0;
			String Rarity = (aegeus.hasKey("Rarity")) ? aegeus.getString("Rarity") : "";
			List<String> lore = new ArrayList<>();
			if(Level >= 2) lore.add(Helper.colorCodes("&cDMG: " + MinDmg + " - " + MaxDmg + " &6(+" + Helper.calcWepLevelBuff(MinDmg, Level) + ")"));
			else lore.add(Helper.colorCodes("&cDMG: " + MinDmg + " - " + MaxDmg));
			if(FireDmg >= 1) lore.add(Helper.colorCodes("&cFIRE DMG: +" + FireDmg));
			if(IceDmg >= 1) lore.add(Helper.colorCodes("&cICE DMG: +" + IceDmg));
			if(LifeSteal >= 1) lore.add(Helper.colorCodes("&cLIFE STEAL: +" + (LifeSteal * 100) + "%"));
			if(Level >= 1){
				float MaxXP = Math.round(Helper.calcMaxXP(Level));
				lore.add(Helper.colorCodes("&6&oLevel " + Level + " &7&o(" + Math.round((XP / MaxXP) * 100) + "%)"));
			}
			if(Rarity != "") lore.add(Helper.colorCodes(Rarity));
			return lore;
		}
		return null;
	}
	
	@Override
	public ItemStack build(){
		ItemStack item = new ItemStack(material);
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagCompound aegeus = new NBTTagCompound();
		aegeus.set("MinDmg", new NBTTagInt(MinDmg));
		aegeus.set("MaxDmg", new NBTTagInt(MaxDmg));
		aegeus.set("FireDmg", new NBTTagInt(FireDmg));
		aegeus.set("IceDmg", new NBTTagInt(IceDmg));
		aegeus.set("LifeSteal", new NBTTagDouble(LifeSteal));
		aegeus.set("Tier", new NBTTagInt(Tier));
		aegeus.set("Level", new NBTTagInt(Level));
		aegeus.set("XP", new NBTTagInt(XP));
		aegeus.set("Rarity", new NBTTagString(Rarity.getLore()));
		compound.set("AegeusInfo", aegeus);
		nmsStack.setTag(compound);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		lore = getLore(item);
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
