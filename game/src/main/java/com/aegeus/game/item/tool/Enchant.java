package com.aegeus.game.item.tool;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.stats.impl.Tier;
import com.aegeus.game.item.info.ItemInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Enchant implements ItemInfo {
	public static final int WEAPON = 0;
	public static final int ARMOR = 1;

	private ItemStack item;
	private String name;
	private List<String> lore = new ArrayList<>();

	private int tier;
	private int type;

	public Enchant(int tier, int type) {
		this(tier, type, 1);
	}

	public Enchant(int tier, int type, int amount) {
		this.tier = tier;
		this.type = type;
		this.item = new ItemStack(Material.EMPTY_MAP, amount);
	}

	public Enchant(ItemStack item) {
		this.item = item;
		impo();
	}

	public static boolean hasEnchantInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("EnchantInfo");
	}

	public static NBTTagCompound getEnchantInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasEnchantInfo(item) ? tag.getCompound("EnchantInfo") : new NBTTagCompound();
	}

	public static ItemStack setEnchantInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("EnchantInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	@Override
	public void impo() {
		ItemInfo.impo(this);

		NBTTagCompound info = getEnchantInfo(item);
		tier = info.hasKey("tier") ? info.getInt("tier") : 0;
		type = info.hasKey("type") ? info.getInt("type") : WEAPON;
	}

	@Override
	public void store() {
		ItemInfo.store(this);

		NBTTagCompound info = getEnchantInfo(item);
		info.setInt("tier", tier);
		info.setInt("type", type);
		item = setEnchantInfo(item, info);
	}

	@Override
	public ItemStack build() {
		store();

		Tier tier = Tier.get(this.tier);
		if (type == WEAPON) {
			setName("&f&lEnchant:&7 " + tier.getColor() + tier.getName() + " Weapon");
			setLore(new ArrayList<>());
			addLore("&c+5% DMG");
			addLore("&7&oWeapon will VANISH if enchant above +3 FAILS.");
		} else if (type == ARMOR) {
			setName("&f&lEnchant:&7 " + tier.getColor() + tier.getName() + " Armor");
			setLore(new ArrayList<>());
			addLore("&c+5% HP");
			addLore("&c+5% HP REGEN");
			addLore("&7&oArmor will VANISH if enchant above +3 FAILS.");
		}

		return ItemInfo.build(this);
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = Util.colorCodes(name);
	}

	@Override
	public List<String> getLore() {
		return lore;
	}

	@Override
	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public int getTier() {
		return tier;
	}

	public int getType() {
		return type;
	}
}
