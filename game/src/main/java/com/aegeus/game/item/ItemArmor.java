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
import net.minecraft.server.v1_10_R1.NBTTagString;

public class ItemArmor extends Item {
	
	private int Hp = 0;
	private int HpRegen = 0;
	
	private int Tier = 0;
	private int Level = 0;
	private int XP = 0;
	
	private ItemRarity Rarity;
	
	public ItemArmor(Material material) {
		super(material);
	}
	
	public ItemArmor(ItemStack item){
		super(item);
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			Hp = aegeus.getInt("Hp");
			HpRegen = (aegeus.hasKey("HpRegen")) ? aegeus.getInt("HpRegen") : 0;
			Tier = aegeus.getInt("Tier");
			Level = (aegeus.hasKey("Level")) ? aegeus.getInt("Level") : 0;
			XP = (aegeus.hasKey("XP")) ? aegeus.getInt("XP") : 0;
			Level = (aegeus.hasKey("Level")) ? aegeus.getInt("Level") : 0;
			XP = (aegeus.hasKey("XP")) ? aegeus.getInt("XP") : 0;
			Rarity = (aegeus.hasKey("Rarity")) ? ItemRarity.fromTypeID(aegeus.getInt("Rarity")) : null;
		}
	}
	
	public void setHp(int Hp){
		this.Hp = Hp;
	}
	
	public int getHp(){
		return Hp;
	}
	
	public void setHpRegen(int HpRegen){
		this.HpRegen = HpRegen;
	}
	
	public int getHpRegen(){
		return HpRegen;
	}
	
	public void setTier(int Tier){
		this.Tier = Tier;
	}
	
	public void setRarity(ItemRarity Rarity){
		this.Rarity = Rarity;
	}
	
	public static List<String> buildLore(ItemStack item){
		NBTTagCompound aegeus = Item.getAegeusInfo(item);
		if(aegeus != null){
			int Hp = aegeus.getInt("Hp");
			int HpRegen = (aegeus.hasKey("HpRegen")) ? aegeus.getInt("HpRegen") : 0;
			String Rarity = (aegeus.hasKey("Rarity")) ? aegeus.getString("Rarity") : "";
			List<String> lore = new ArrayList<>();
			lore.add(Helper.colorCodes("&cHP: +" + Hp));
			if(HpRegen > 0) lore.add(Helper.colorCodes("&cHP REGEN: +" + HpRegen + "/s"));
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
		aegeus.set("Hp", new NBTTagInt(Hp));
		aegeus.set("HpRegen", new NBTTagInt(HpRegen));
		aegeus.set("Tier", new NBTTagInt(Tier));
		aegeus.set("Level", new NBTTagInt(Level));
		aegeus.set("XP", new NBTTagInt(XP));
		aegeus.set("Rarity", new NBTTagString(Rarity.getLore()));
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
