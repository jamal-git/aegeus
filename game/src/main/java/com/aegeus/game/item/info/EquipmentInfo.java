package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface EquipmentInfo extends ItemInfo {
	static <T extends EquipmentInfo> void impo(T t) {
		NBTTagCompound info = getEquipmentInfo(t.getItem());
		t.setTier((info.hasKey("tier")) ? info.getInt("tier") : 0);
		t.setRarity((info.hasKey("rarity")) ? Rarity.fromID(info.getInt("rarity")) : null);
		t.setEnchant((info.hasKey("enchant")) ? info.getInt("enchant") : 0);
	}

	static <T extends EquipmentInfo> void store(T t) {
		NBTTagCompound info = getEquipmentInfo(t.getItem());
		info.setInt("tier", t.getTier());
		info.setInt("rarity", t.getRarity() == null ? -1 : t.getRarity().getId());
		info.setInt("enchant", t.getEnchant());
		t.setItem(setEquipmentInfo(t.getItem(), info));
	}

	static <T extends EquipmentInfo> String buildPrefix(T t) {
		String name = "";
		if (t.getEnchant() > 0) name += "&a(+" + t.getEnchant() + ")&f ";
		return Util.colorCodes(name);
	}

	static <T extends EquipmentInfo> List<String> buildLore(T t) {
		List<String> lore = new ArrayList<>();
		if (t.getRarity() != null) lore.add(Util.colorCodes(t.getRarity().getLore()));
		return lore;
	}

	static boolean hasEquipmentInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("EquipmentInfo");
	}

	static NBTTagCompound getEquipmentInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasEquipmentInfo(item) ? tag.getCompound("EquipmentInfo") : new NBTTagCompound();
	}

	static ItemStack setEquipmentInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("EquipmentInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	int getTier();

	void setTier(int tier);

	Rarity getRarity();

	void setRarity(Rarity rarity);

	int getEnchant();

	void setEnchant(int enchant);

	default void addEnchant(int i) {
		setEnchant(getEnchant() + i);
	}
}
