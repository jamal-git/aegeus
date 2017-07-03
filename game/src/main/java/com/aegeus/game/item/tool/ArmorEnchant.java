package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Tier;
import com.aegeus.game.item.info.ItemInfo;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorEnchant extends AgItem implements ItemInfo {
	private int tier;

	public ArmorEnchant(int tier) {
		super(Material.EMPTY_MAP);
		setName("&f&lEnchant:&7 " + Tier.fromTier(tier).getArmor() + " Armor");
		addLore("&c+5% HP");
		addLore("&c+5% HP REGEN");
		addLore("    &7- &oOR&7 -");
		addLore("&c+1% ENERGY REGEN");
		addLore("&7&oArmor will VANISH if enchant above +3 FAILS.");
		this.tier = tier;
	}

	public ArmorEnchant(ItemStack item) {
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
		info.set("type", new NBTTagString("enchant_armor"));
		info.set("tier", new NBTTagInt(tier));
		setAegeusInfo(info);
	}

	@Override
	public List<String> buildLore() {
		return new ArrayList<>();
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("enchant_armor");
	}

	@Override
	public ItemStack build() {
		store();

		List<String> lore = buildLore();
		lore.addAll(getLore());
		setLore(lore);

		return super.build();
	}

	public int getTier() {
		return tier;
	}
}
