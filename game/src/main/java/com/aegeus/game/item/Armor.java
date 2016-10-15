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
import net.minecraft.server.v1_10_R1.NBTTagInt;

public class Armor extends Item {
	
	private ItemType type = ItemType.ARMOR;
	private int hp = 0;
	private int hpRegen = 0;
	
	private int tier = 0;
	private int level = 0;
	private int xp = 0;
	
	private Rarity rarity = Rarity.NONE;
	//private List<ArmorRune> runes = new ArrayList<>();
	
	public Armor(Material material) {
		super(material);
	}
	
	public Armor(ItemStack item){
		super(item);
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			hp = aegeus.getInt("hp");
			hpRegen = (aegeus.hasKey("hpRegen")) ? aegeus.getInt("hpRegen") : 0;
			tier = aegeus.getInt("tier");
			level = (aegeus.hasKey("level")) ? aegeus.getInt("level") : 0;
			xp = (aegeus.hasKey("xp")) ? aegeus.getInt("xp") : 0;
			rarity = (aegeus.hasKey("rarity")) ? Rarity.getById(aegeus.getInt("rarity")) : Rarity.NONE;
		}
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
	
	public void setTier(int tier){
		this.tier = tier;
	}
	
	public void setRarity(Rarity rarity){
		this.rarity = rarity;
	}
	
	//public List<ArmorRune> getRunes() {
	//	return runes;
	//}
	
	public static List<String> buildLore(ItemStack item){
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			int hp = aegeus.getInt("hp");
			int hpRegen = (aegeus.hasKey("hpRegen")) ? aegeus.getInt("hpRegen") : 0;
			String rarity = (aegeus.hasKey("rarity")) ? aegeus.getString("rarity") : "";
			List<String> lore = new ArrayList<>();
			lore.add(Helper.colorCodes("&cHP: +" + hp));
			if(hpRegen > 0) lore.add(Helper.colorCodes("&cHP REGEN: +" + hpRegen + "/s"));
			if(rarity != "") lore.add(Helper.colorCodes(rarity));
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
		aegeus.set("type", new NBTTagInt(type.getId()));
		aegeus.set("tier", new NBTTagInt(tier));
		aegeus.set("hp", new NBTTagInt(hp));
		aegeus.set("hpRegen", new NBTTagInt(hpRegen));
		aegeus.set("level", new NBTTagInt(level));
		aegeus.set("xp", new NBTTagInt(xp));
		aegeus.set("rarity", new NBTTagInt(rarity.getId()));
		compound.set("AegeusInfo", aegeus);
		nmsStack.setTag(compound);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		lore = buildLore(item);
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
