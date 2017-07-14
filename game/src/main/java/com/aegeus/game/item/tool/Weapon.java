package com.aegeus.game.item.tool;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.Tier;
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
import java.util.concurrent.ThreadLocalRandom;

public class Weapon implements EquipmentInfo, LevelInfo, DuraInfo {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	// Item Info
	private ItemStack item;

	// Level Info
	private int level = 0;
	private int xp = 0;

	// Equipment Info
	private Tier tier = null;
	private Rarity rarity = null;
	private int enchant = 0;

	// Dura Info
	private int maxDura = 0;
	private int dura = 0;

	// Weapon Stats
	private int minDmg = 0;
	private int maxDmg = 0;
	private float pen = 0;
	private int fireDmg = 0;
	private int iceDmg = 0;
	private int poisonDmg = 0;
	private int pureDmg = 0;
	private float lifeSteal = 0;
	private float trueHearts = 0;
	private float blind = 0;

	public Weapon(Material material) {
		item = new ItemStack(material);
		setMaxDura(tier != null ? tier.getWepDura() : 0);
	}

	public Weapon(ItemStack item) {
		this.item = item;
		impo();
	}

	public static boolean hasWeaponInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("WeaponInfo");
	}

	public static NBTTagCompound getWeaponInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasWeaponInfo(item) ? tag.getCompound("WeaponInfo") : new NBTTagCompound();
	}

	public static ItemStack setWeaponInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("WeaponInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	@Override
	public void impo() {
		ItemInfo.impo(this);
		EquipmentInfo.impo(this);
		LevelInfo.impo(this);
		DuraInfo.impo(this);

		NBTTagCompound info = getWeaponInfo(getItem());
		minDmg = (info.hasKey("minDmg")) ? info.getInt("minDmg") : 0;
		maxDmg = (info.hasKey("maxDmg")) ? info.getInt("maxDmg") : 0;
		pen = (info.hasKey("pen")) ? info.getFloat("pen") : 0;
		fireDmg = (info.hasKey("fireDmg")) ? info.getInt("fireDmg") : 0;
		iceDmg = (info.hasKey("iceDmg")) ? info.getInt("iceDmg") : 0;
		poisonDmg = (info.hasKey("poisonDmg")) ? info.getInt("poisonDmg") : 0;
		pureDmg = (info.hasKey("pureDmg")) ? info.getInt("pureDmg") : 0;
		lifeSteal = (info.hasKey("lifeSteal")) ? info.getFloat("lifeSteal") : 0;
		trueHearts = (info.hasKey("trueHearts")) ? info.getFloat("trueHearts") : 0;
		blind = (info.hasKey("blind")) ? info.getFloat("blind") : 0;
	}

	@Override
	public void store() {
		ItemInfo.store(this);
		EquipmentInfo.store(this);
		LevelInfo.store(this);
		DuraInfo.store(this);

		NBTTagCompound info = getWeaponInfo(getItem());
		info.set("minDmg", new NBTTagInt(minDmg));
		info.set("maxDmg", new NBTTagInt(maxDmg));
		info.set("pen", new NBTTagFloat(pen));
		info.set("fireDmg", new NBTTagInt(fireDmg));
		info.set("iceDmg", new NBTTagInt(iceDmg));
		info.set("poisonDmg", new NBTTagInt(poisonDmg));
		info.set("pureDmg", new NBTTagInt(pureDmg));
		info.set("lifeSteal", new NBTTagFloat(lifeSteal));
		info.set("trueHearts", new NBTTagFloat(trueHearts));
		info.set("blind", new NBTTagFloat(blind));
		item = setWeaponInfo(getItem(), info);
	}

	/*
	Info Overrides
	 */

	public String buildPrefix() {
		return EquipmentInfo.buildPrefix(this);
	}

	public List<String> buildLore() {
		List<String> lore = new ArrayList<>();
		lore.add(Util.colorCodes("&cDMG: " + minDmg + " - " + maxDmg + (getLevelDmg() <= 0 ? "" : "&6 (+" + getLevelDmg() + ")")));
		if (pen > 0) lore.add(Util.colorCodes("&cPENETRATION: " + Math.round(pen * 100) + "%"));
		if (fireDmg > 0) lore.add(Util.colorCodes("&cFIRE DMG: +" + fireDmg));
		if (iceDmg > 0) lore.add(Util.colorCodes("&cICE DMG: +" + iceDmg));
		if (poisonDmg > 0) lore.add(Util.colorCodes("&cPOISON DMG: +" + poisonDmg));
		if (pureDmg > 0) lore.add(Util.colorCodes("&cPURE DMG: +" + pureDmg));
		if (lifeSteal > 0) lore.add(Util.colorCodes("&cLIFE STEAL: +" + Math.round(lifeSteal * 100) + "%"));
		if (trueHearts > 0) lore.add(Util.colorCodes("&cTRUE HEARTS: " + Math.round(trueHearts * 100) + "%"));
		if (blind > 0) lore.add(Util.colorCodes("&cBLIND: " + Math.round(blind * 100) + "%"));
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
		return (int) Util.calcMaxXP(getLevel(), getTier().getLevel());
	}

	@Override
	public Tier getTier() {
		return tier;
	}

	@Override
	public void setTier(Tier tier) {
		this.tier = tier;
		setMaxDura(tier.getWepDura());
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
	Weapon Methods
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

	public int getDmg() {
		return (getMinDmg() == getMaxDmg() ? getMinDmg() : random.nextInt(getMinDmg(), getMaxDmg() + 1)) + getLevelDmg();
	}

	public void setDmg(int minDmg, int maxDmg) {
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
	}

	public int getMinDmg() {
		return minDmg;
	}

	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	public int getMaxDmg() {
		return maxDmg;
	}

	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public int getLevelDmg() {
		return Math.round(getLevel() * (0.15f * getTier().getLevel()));
	}

	public float getPen() {
		return pen;
	}

	public void setPen(float pen) {
		this.pen = pen;
	}

	public int getFireDmg() {
		return fireDmg;
	}

	public void setFireDmg(int fireDmg) {
		this.fireDmg = fireDmg;
	}

	public int getIceDmg() {
		return iceDmg;
	}

	public void setIceDmg(int iceDmg) {
		this.iceDmg = iceDmg;
	}

	public int getPoisonDmg() {
		return poisonDmg;
	}

	public void setPoisonDmg(int poisonDmg) {
		this.poisonDmg = poisonDmg;
	}

	public int getPureDmg() {
		return pureDmg;
	}

	public void setPureDmg(int pureDmg) {
		this.pureDmg = pureDmg;
	}

	public float getLifeSteal() {
		return lifeSteal;
	}

	public void setLifeSteal(float lifeSteal) {
		this.lifeSteal = lifeSteal;
	}

	public float getTrueHearts() {
		return trueHearts;
	}

	public void setTrueHearts(float trueHearts) {
		this.trueHearts = trueHearts;
	}

	public float getBlind() {
		return blind;
	}

	public void setBlind(float blind) {
		this.blind = blind;
	}
}
