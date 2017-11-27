package com.aegeus.game.item.tool;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.stats.tier.impl.Tier;
import com.aegeus.game.item.info.DuraInfo;
import com.aegeus.game.item.info.EquipmentInfo;
import com.aegeus.game.item.info.ItemInfo;
import com.aegeus.game.item.info.LevelInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Armor implements EquipmentInfo, LevelInfo, DuraInfo {
	// Item Info
	private ItemStack item;
	private String name;
	private List<String> lore = new ArrayList<>();

	// Level Info
	private int level = 0;
	private int xp = 0;

	// Equipment Info
	private int tier = 0;
	private Rarity rarity = null;
	private int enchant = 0;

	// Dura Info
	private int maxDura = 0;
	private int dura = 0;

	// Armor Stats
	private int hp = 0;
	private int hpRegen = 0;
	private float physRes = 0;
	private float magRes = 0;
	private float block = 0;
	private float dodge = 0;
	private float reflect = 0;

	public Armor(Material material) {
		item = new ItemStack(material);
		setMaxDura(Tier.get(tier).getArmorDura());
	}

	public Armor(ItemStack item) {
		this.item = item;
		impo();
	}

	public static boolean hasArmorInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("ArmorInfo");
	}

	public static NBTTagCompound getArmorInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasArmorInfo(item) ? tag.getCompound("ArmorInfo") : new NBTTagCompound();
	}

	public static ItemStack setArmorInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("ArmorInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	@Override
	public void impo() {
		ItemInfo.impo(this);
		EquipmentInfo.impo(this);
		LevelInfo.impo(this);
		DuraInfo.impo(this);

		NBTTagCompound info = getArmorInfo(getItem());
		hp = (info.hasKey("hp")) ? info.getInt("hp") : 0;
		hpRegen = (info.hasKey("hpRegen")) ? info.getInt("hpRegen") : 0;
		physRes = (info.hasKey("physRes")) ? info.getFloat("physRes") : 0;
		magRes = (info.hasKey("magRes")) ? info.getFloat("magRes") : 0;
		block = (info.hasKey("block")) ? info.getFloat("block") : 0;
		dodge = (info.hasKey("dodge")) ? info.getFloat("dodge") : 0;
		reflect = (info.hasKey("reflect")) ? info.getFloat("reflect") : 0;
	}

	@Override
	public void store() {
		ItemInfo.store(this);
		EquipmentInfo.store(this);
		LevelInfo.store(this);
		DuraInfo.store(this);

		NBTTagCompound info = getArmorInfo(item);
		info.set("hp", new NBTTagInt(hp));
		info.set("hpRegen", new NBTTagInt(hpRegen));
		info.set("physRes", new NBTTagFloat(physRes));
		info.set("magRes", new NBTTagFloat(magRes));
		info.set("block", new NBTTagFloat(block));
		info.set("dodge", new NBTTagFloat(dodge));
		info.set("reflect", new NBTTagFloat(reflect));
		item = setArmorInfo(item, info);
	}

	/*
	Info Overrides
	 */

	public String buildPrefix() {
		return EquipmentInfo.buildPrefix(this);
	}

	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&cHP: +" + hp));
		if (hpRegen > 0) lore.add(Util.colorCodes("&cHP REGEN: +" + hpRegen + "/s"));
		if (physRes > 0) lore.add(Util.colorCodes("&cPHYSICAL RESIST: " + Math.round(physRes * 100) + "%"));
		if (magRes > 0) lore.add(Util.colorCodes("&cMAGIC RESIST: " + Math.round(magRes * 100) + "%"));
		if (block > 0) lore.add(Util.colorCodes("&cBLOCK: " + Math.round(block * 100) + "%"));
		if (dodge > 0) lore.add(Util.colorCodes("&cDODGE: " + Math.round(dodge * 100) + "%"));
		if (reflect > 0) lore.add(Util.colorCodes("&cREFLECTION: " + Math.round(reflect * 100) + "%"));
		lore.addAll(EquipmentInfo.buildLore(this));
		lore.addAll(LevelInfo.buildLore(this));
		return lore;
	}

	@Override
	public ItemStack build() {
		store();
		ItemStack item = ItemInfo.build(this);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(String.join("", buildPrefix(), getName()));
		meta.setLore(Util.union(buildLore(), getLore()));
		item.setItemMeta(meta);

		if (getEnchant() >= 4) item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

		return item;
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

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public int getMaxXp() {
		return (int) Util.calcMaxXP(getLevel(), getTier());
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public void setTier(int tier) {
		this.tier = tier;
		setMaxDura(Tier.get(tier).getArmorDura());
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	@Override
	public int getEnchant() {
		return enchant;
	}

	@Override
	public void setEnchant(int enchant) {
		this.enchant = enchant;
	}

	@Override
	public int getMaxDura() {
		return maxDura;
	}

	/*
	Armor Methods
	 */

	@Override
	public void setMaxDura(int maxDura) {
		this.maxDura = maxDura;
		setDura(getMaxDura());
	}

	@Override
	public int getDura() {
		return dura;
	}

	@Override
	public void setDura(int dura) {
		this.dura = dura;
		DuraInfo.update(this);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHpRegen() {
		return hpRegen;
	}

	public void setHpRegen(int hpRegen) {
		this.hpRegen = hpRegen;
	}

	public float getPhysRes() {
		return physRes;
	}

	public void setPhysRes(float physRes) {
		this.physRes = physRes;
	}

	public float getMagRes() {
		return magRes;
	}

	public void setMagRes(float magRes) {
		this.magRes = magRes;
	}

	public float getBlock() {
		return block;
	}

	public void setBlock(float block) {
		this.block = block;
	}

	public float getDodge() {
		return dodge;
	}

	public void setDodge(float dodge) {
		this.dodge = dodge;
	}

	public float getReflect() {
		return reflect;
	}

	public void setReflect(float reflect) {
		this.reflect = reflect;
	}
}
