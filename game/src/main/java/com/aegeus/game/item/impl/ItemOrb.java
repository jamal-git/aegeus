package com.aegeus.game.item.impl;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.util.ItemWrapper;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemOrb extends ItemWrapper {
	public static final String IDENTITY = "orb";

	public static final int ALTERATION = 0;

	private int type = 0;

	public ItemOrb(ItemStack item) {
		super(item);
	}

	public ItemOrb(int type) {
		super(Material.MAGMA_CREAM);
	}

	@Override
	public void onInvClick(InventoryClickEvent event) {
		Aegeus.getInstance().getLogger().info("hello");
		// TODO Create orb.
	}

	@Override
	public NBTTagCompound load() {
		NBTTagCompound tag = super.load();
		type = tag.hasKey("type") ? tag.getInt("type") : ALTERATION;
		return tag;
	}

	@Override
	public NBTTagCompound save() {
		NBTTagCompound tag = super.save();
		tag.setInt("type", type);
		return tag;
	}

	@Override
	public String buildName() {
		return super.buildName();
	}

	@Override
	public List<String> buildLore() {
		return super.buildLore();
	}

	@Override
	public String getIdentity() {
		return IDENTITY;
	}
}
