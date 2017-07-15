package com.aegeus.game.item.info;

import com.aegeus.game.item.ItemUtils;
import com.sk89q.jnbt.NBTConstants;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.NBTTagString;
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
		info.set("name", new NBTTagString(t.getName()));
		NBTTagList lore = new NBTTagList();
		t.getLore().forEach(s -> lore.add(new NBTTagString(s)));
		info.set("lore", lore);
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
		return getItem() != null ? getItem().getType() : null;
	}

	default void setMaterial(Material material) {
		if (getItem() != null) getItem().setType(material);
	}

	default String getName() {
		return getItem() != null && getItem().hasItemMeta()
				? getItem().getItemMeta().getDisplayName() : null;
	}

	default void setName(String name) {
		if (getItem() != null && getItem().hasItemMeta()) {
			ItemMeta meta = getItem().getItemMeta();
			meta.setDisplayName(name);
			getItem().setItemMeta(meta);
		}
	}

	default List<String> getLore() {
		return getItem() != null && getItem().hasItemMeta()
				? getItem().getItemMeta().getLore() : null;
	}

	default void setLore(List<String> lore) {
		if (getItem() != null && getItem().hasItemMeta()) {
			ItemMeta meta = getItem().getItemMeta();
			meta.setLore(lore);
			getItem().setItemMeta(meta);
		}
	}

	default void addLore(String line) {
		List<String> lore = new ArrayList<>(getLore());
		lore.add(line);
		setLore(lore);
	}
}
