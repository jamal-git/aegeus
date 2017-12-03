package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.util.Util;
import com.sk89q.jnbt.NBTConstants;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public interface ItemInfo {
	static <T extends ItemInfo> void impo(T t) {
		NBTTagCompound info = getItemInfo(t.getItem());
		t.setName((info.hasKey("name")) ? info.getString("name") : "");
		if (info.hasKey("info"))
			for (int i = 0; i < info.getList("lore", NBTConstants.TYPE_LIST).size(); i++)
				t.addLore(info.getList("lore", NBTConstants.TYPE_LIST).getString(i));
	}

	static <T extends ItemInfo> void store(T t) {
		NBTTagCompound info = getItemInfo(t.getItem());
		if (t.getName() != null && !t.getName().isEmpty())
			info.set("name", new NBTTagString(t.getName()));
		if (t.getLore() != null && !t.getLore().isEmpty()) {
			NBTTagList lore = new NBTTagList();
			t.getLore().forEach(s -> lore.add(new NBTTagString(s)));
			info.set("lore", lore);
		}
		t.setItem(setItemInfo(t.getItem(), info));
	}

	static <T extends ItemInfo> ItemStack build(T t) {
		store(t);

		ItemMeta meta = t.getItem().getItemMeta();
		meta.setDisplayName(t.getName());
		meta.setLore(t.getLore());
		meta.addItemFlags(
				ItemFlag.HIDE_ENCHANTS,
				ItemFlag.HIDE_POTION_EFFECTS,
				ItemFlag.HIDE_ATTRIBUTES
		);
		t.getItem().setItemMeta(meta);

		return t.getItem();
	}

	static boolean hasItemInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("ItemInfo");
	}

	static NBTTagCompound getItemInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasItemInfo(item) ? tag.getCompound("ItemInfo") : new NBTTagCompound();
	}

	static ItemStack setItemInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("ItemInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	void impo();

	void store();

	ItemStack build();

	ItemStack getItem();

	void setItem(ItemStack item);

	default Material getMaterial() {
		return getItem() != null ? getItem().getType() : Material.AIR;
	}

	default void setMaterial(Material material) {
		if (getItem() != null) getItem().setType(material);
	}

	String getName();

	void setName(String name);

	List<String> getLore();

	void setLore(List<String> lore);

	default void addLore(String line) {
		List<String> lore = new ArrayList<>(getLore());
		lore.add(Util.colorCodes(line));
		setLore(lore);
	}
}
