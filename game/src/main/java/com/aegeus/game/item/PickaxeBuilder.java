package com.aegeus.game.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aegeus.game.mining.Mining;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class PickaxeBuilder {
	private Material itemType;
	private int level = 1;
	private int xp = 0;
	private int xpreq = Mining.getXPNeeded(2);
	private List<String> lore = new ArrayList<>();
	private String displayName;
	private boolean isCheaty = false; //Basically add in a line saying that this item was cheated in and should not be used.
	public PickaxeBuilder(int level, boolean isSpawnIn)	{
		this.setLevel(level);
		isCheaty = isSpawnIn;
		if(level > 79)	itemType = Material.GOLD_PICKAXE;
		else if(level > 59) itemType = Material.DIAMOND_PICKAXE;
		else if(level > 39) itemType = Material.IRON_PICKAXE;
		else if(level > 19) itemType = Material.STONE_PICKAXE;
		else itemType = Material.WOOD_PICKAXE;
		lore.add("level");
		lore.add("xp");
	}
	
	public PickaxeBuilder(ItemStack i)	{
		NBTTagCompound pick = CraftItemStack.asNMSCopy(i).getTag().getCompound("pickaxe"); //TODO make this more reliable
		/*
		 * Transfer the important info to our variables.
		 * The only things we should be transferring for now is:
		 * 		Level
		 * 		XP
		 * 		XP Required
		 * 		Extra Lore 
		 */
		this.level = pick.getInt("level");
		this.xp = pick.getInt("xp");
		this.xpreq = pick.getInt("xpreq");
		lore = i.getItemMeta().getLore();
		
	}
	
	public PickaxeBuilder()	{
		itemType = Material.WOOD_PICKAXE;
		lore.add("level");
		lore.add("xp");
	}
	
	public void setLevel(int target)	{
		level = target;
		xpreq = Mining.getXPNeeded(target + 1);
	}
	
	public void setExp(int target)	{
		if(target >= xpreq)	xp = 0;
		else xp = target;
	}
	
	public void addEnchant()	{
		
	}
	
	public void addCustomLore(String s)	{
		lore.add(s);
	}
	
	public ItemStack build()	{
		lore.set(0, ChatColor.GRAY + "Level: " + ChatColor.AQUA + level);
		lore.set(1, ChatColor.GRAY + "EXP: " + xp + " / " + xpreq);
		if(isCheaty) lore.add("" + ChatColor.GRAY + ChatColor.ITALIC + "This item was spawned in.  If a player is found with this, they will be permanently banned.");
		//Basically tell all the players to drop the fucking pickaxe if they have posession of the item or else they will be insta-perma banned no questions asked.
		ItemStack pickaxe = new ItemStack(itemType);
		ItemMeta meta = pickaxe.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Dank Meme");
		meta.setLore(lore);
		pickaxe.setItemMeta(meta);
		net.minecraft.server.v1_10_R1.ItemStack nms = CraftItemStack.asNMSCopy(pickaxe);
		NBTTagCompound nbt = nms.hasTag() ? nms.getTag() : new NBTTagCompound();
		NBTTagCompound nbtpick = new NBTTagCompound();
		nbtpick.setInt("level", level);
		nbtpick.setInt("xp", xp);
		nbtpick.setInt("xpreq", xpreq);
		//TODO add nbt tags for enchants
		nbt.set("pickaxe", nbtpick);
		nms.setTag(nbt);
		pickaxe = CraftItemStack.asBukkitCopy(nms);
		
		return pickaxe;
	}
}
