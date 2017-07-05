package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Tier;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WeaponEnchant extends AgItem {
	private int tier;

	public WeaponEnchant(int tier) {
		super(Material.EMPTY_MAP);
		setName("&f&lEnchant:&7 " + Tier.fromTier(tier).getWeapon() + " Weapon");
		addLore("&c+5% DMG");
		addLore("&7&oWeapon will VANISH if enchant above +3 FAILS.");
		this.tier = tier;
	}

	public WeaponEnchant(ItemStack item) {
		super(item);
		impo();
	}

	@Override
	public void impo() {
		NBTTagCompound info = getAegeusInfo();
		tier = info.hasKey("tier") ? info.getInt("tier") : -1;
	}

	@Override
	public void store() {
		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("enchant_weapon"));
		info.set("tier", new NBTTagInt(tier));
		setAegeusInfo(info);
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("enchant_weapon");
	}

	@Override
	public ItemStack build() {
		store();
		return super.build();
	}

	public int getTier() {
		return tier;
	}
}