package com.aegeus.game.item;

import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
	public static NBTTagCompound getTag(ItemStack item) {
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		return (nmsStack.getTag() != null) ? nmsStack.getTag() : new NBTTagCompound();
	}

	public static ItemStack setTag(ItemStack item, NBTTagCompound tag) {
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		nmsStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}

	public static boolean isDisplayItem(ItemStack item) {
	    return getTag(item).hasKey("displayitem") && getTag(item).getBoolean("displayitem");
    }

    public static void setDisplayItem(ItemStack item, boolean b)    {
	    getTag(item).setBoolean("displayitem", b);
    }
}
