package com.aegeus.game.item.tool;

import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.item.info.ItemInfo;
import com.aegeus.game.item.info.ProfessionInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pickaxe implements ProfessionInfo {

	private static final double CONSTANT = 1.114;
	private static final Random r = new Random();

	private ItemStack item;
	private String name;
	private List<String> lore = new ArrayList<>();

	private int level;
	private int xp;
	private int requiredxp;

	private ProfessionTier tier;
	private boolean tieredUp;

	private float miningSuccess;
	private float denseFind;
	private int denseMultiplier;
	private float doubleOre;
	private float tripleOre;
	private float gemFind;

	public Pickaxe() {
		this(1);
	}

	public Pickaxe(int level) {
		this.item = new ItemStack(ProfessionTier.getTierByLevel(level).getPickaxeMaterial());
		this.level = level;
		tier = ProfessionTier.getTierByLevel(level);
		xp = 0;
		requiredxp = getXPRequired(level + 1);
	}

	public Pickaxe(ItemStack item) {
		this.item = item;
		impo();
	}

	public static int getXPRequired(int targetLevel) {
		if (targetLevel == 101) return 1;
		double xp = Math.pow(CONSTANT, targetLevel) * 100;
		if (targetLevel >= 81) xp += 50000 * (targetLevel - 70);
		if (targetLevel >= 61) xp += 10000 * (targetLevel - 50);
		if (targetLevel >= 41) xp += 5000 * (targetLevel - 30);
		if (targetLevel >= 21) xp += 1200 * targetLevel;
		if (targetLevel >= 11) xp += 300 * targetLevel;
		xp += 100 * targetLevel;
		return (int) Math.round(xp);
	}

	public static boolean hasPickaxeInfo(ItemStack item) {
		return ItemUtils.getTag(item).hasKey("PickaxeInfo");
	}

	public static NBTTagCompound getPickaxeInfo(ItemStack item) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		return hasPickaxeInfo(item) ? tag.getCompound("PickaxeInfo") : new NBTTagCompound();
	}

	public static ItemStack setPickaxeInfo(ItemStack item, NBTTagCompound info) {
		NBTTagCompound tag = ItemUtils.getTag(item);
		tag.set("PickaxeInfo", info);
		return ItemUtils.setTag(item, tag);
	}

	public int getTierAsInt() {
		return tier.ordinal() + 1;
	}

	public ProfessionTier getTier() {
		return tier;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		setMaterial(ProfessionTier.getTierByLevel(level).getPickaxeMaterial());
		this.level = level;
		tier = ProfessionTier.getTierByLevel(level);
		requiredxp = getXPRequired(level + 1);
	}

	@Override
	public int getXp() {
		return xp;
	}

	/**
	 * DON'T USE THIS.
	 *
	 * @param xp
	 */
	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public int getRequiredXp() {
		return requiredxp;
	}

	@Override
	public void impo() {
		ItemInfo.impo(this);
		ProfessionInfo.impo(this);

		NBTTagCompound info = getPickaxeInfo(item);
		setMiningSuccess(info.hasKey("miningSuccess") ? info.getFloat("miningSuccess") : 0);
		setDenseFind(info.hasKey("denseFind") ? info.getFloat("denseFind") : 0);
		setDenseMultiplier(info.hasKey("denseMultiplier") ? info.getInt("denseMultiplier") : 0);
		setDoubleOre(info.hasKey("doubleOre") ? info.getFloat("doubleOre") : 0);
		setTripleOre(info.hasKey("tripleOre") ? info.getFloat("tripleOre") : 0);
		setGemFind(info.hasKey("gemFind") ? info.getFloat("gemFind") : 0);

	}

	@Override
	public void store() {
		ProfessionInfo.store(this);

		NBTTagCompound info = getPickaxeInfo(item);
		info.set("miningSuccess", new NBTTagFloat(getMiningSuccess()));
		info.set("denseFind", new NBTTagFloat(getDenseFind()));
		info.set("denseMultiplier", new NBTTagInt(getDenseMultiplier()));
		info.set("doubleOre", new NBTTagFloat(getDoubleOre()));
		info.set("tripleOre", new NBTTagFloat(getTripleOre()));
		info.set("gemFind", new NBTTagFloat(getGemFind()));
		item = setPickaxeInfo(item, info);
	}

	public List<String> buildLore() {
		List<String> lore = ProfessionInfo.buildLore(this);
		if (miningSuccess > 0) lore.add(Util.colorCodes("&cMINING SUCCESS: +" + (miningSuccess * 100) + "%"));
		if (denseFind > 0) lore.add(Util.colorCodes("&cDENSE FIND: +" + denseFind * 100 + "%"));
		if (denseMultiplier > 0) lore.add(Util.colorCodes("&cDENSE MULTIPLIER: " + denseMultiplier + "x"));
		if (doubleOre > 0) lore.add(Util.colorCodes("&cDOUBLE ORE: +" + doubleOre * 100 + "%"));
		if (tripleOre > 0) lore.add(Util.colorCodes("&cTRIPLE ORE: +" + tripleOre * 100 + "%"));
		if (gemFind > 0) lore.add(Util.colorCodes("&cGEM FIND: +" + gemFind * 100 + "%"));
		return lore;
	}

	@Override
	public ItemStack build() {
		store();
		setLore(buildLore());
		setName(tier.getColor() + tier.getPickaxeName());
		return ItemInfo.build(this);
	}

	public boolean addExp(int i) {
		xp += i;
		if (xp >= requiredxp) {
			xp = 0;
			requiredxp = getXPRequired(++level + 1);
			if (level % 20 == 0) {
				tier = tier.next();
				setMaterial(tier.getPickaxeMaterial());
				tieredUp = true;
				int j = 1;
				switch (getTier()) {
					case IMPROVED:
					case REINFORCED:
						j = 4;
						break;
					case ELITE:
					case ULTIMATE:
					case TRANSCENDENT:
						j = 6;
						break;
				}
				switch (r.nextInt(j)) {
					case 0: //MINING SUCCESS
						addRandomMiningSuccess();
						break;
					case 1: //DOUBLE ORE
						addRandomDoubleOre();
						break;
					case 2: //TRIPLE ORE
						addRandomTripleOre();
						break;
					case 3: //GEM FIND
						addRandomGemFind();
						break;
					case 4: //DENSE MULTIPLIER
						addRandomDenseMultiplier();
						break;
					case 5: //DENSE FIND
						addRandomDenseFind();
						break;
					default:
						break;
				}
			}
			return true;
		}
		return false;
	}

	public void addRandomMiningSuccess() {
		if (tier == ProfessionTier.IMPROVED) miningSuccess += (r.nextInt(2) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.REINFORCED) miningSuccess += (r.nextInt(4) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ELITE) miningSuccess += (r.nextInt(8) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ULTIMATE) miningSuccess += (r.nextInt(10) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.TRANSCENDENT) miningSuccess += (r.nextInt(15) + 1.0f) / 100.0f;
	}

	public void addRandomDoubleOre() {
		if (tier == ProfessionTier.IMPROVED) doubleOre += (r.nextInt(3) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.REINFORCED) doubleOre += (r.nextInt(3) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ELITE) doubleOre += (r.nextInt(5) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ULTIMATE) doubleOre += (r.nextInt(7) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.TRANSCENDENT) doubleOre += (r.nextInt(9) + 1.0f) / 100.0f;
	}

	public void addRandomTripleOre() {
		if (tier == ProfessionTier.IMPROVED) tripleOre += (r.nextInt(1) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.REINFORCED) tripleOre += (r.nextInt(2) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ELITE) tripleOre += (r.nextInt(4) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ULTIMATE) tripleOre += (r.nextInt(5) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.TRANSCENDENT) tripleOre += (r.nextInt(7) + 1.0f) / 100.0f;
	}

	public void addRandomGemFind() {
		if (tier == ProfessionTier.IMPROVED) gemFind += (r.nextInt(2) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.REINFORCED) gemFind += (r.nextInt(3) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ELITE) gemFind += (r.nextInt(5) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ULTIMATE) gemFind += (r.nextInt(7) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.TRANSCENDENT) gemFind += (r.nextInt(9) + 1.0f) / 100.0f;
	}

	public void addRandomDenseMultiplier() {
		if (tier == ProfessionTier.ELITE) denseMultiplier += r.nextInt(3);
		if (tier == ProfessionTier.ULTIMATE) denseMultiplier += r.nextInt(5);
		if (tier == ProfessionTier.TRANSCENDENT) denseMultiplier += r.nextInt(7);
		denseMultiplier++;
		if (getDenseFind() == 0.00) addRandomDenseFind();
	}

	public void addRandomDenseFind() {
		if (tier == ProfessionTier.ELITE) denseFind += (r.nextInt(2) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.ULTIMATE) denseFind += (r.nextInt(4) + 1.0f) / 100.0f;
		if (tier == ProfessionTier.TRANSCENDENT) denseFind += (r.nextInt(5) + 1.0f) / 100.0f;
		if (denseMultiplier == 0.00) addRandomDenseMultiplier();
	}

	/*
	 * Encapsulations
     */

	public boolean checkForNextTier() {
		return !(tieredUp ^= true);
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

	public float getMiningSuccess() {
		return miningSuccess;
	}

	public void setMiningSuccess(float miningSuccess) {
		this.miningSuccess = miningSuccess;
	}

	public float getDenseFind() {
		return denseFind;
	}

	public void setDenseFind(float denseFind) {
		this.denseFind = denseFind;
	}

	public int getDenseMultiplier() {
		return denseMultiplier;
	}

	public void setDenseMultiplier(int denseMultiplier) {
		this.denseMultiplier = denseMultiplier;
	}

	public float getDoubleOre() {
		return doubleOre;
	}

	public void setDoubleOre(float doubleOre) {
		this.doubleOre = doubleOre;
	}

	public float getTripleOre() {
		return tripleOre;
	}

	public void setTripleOre(float tripleOre) {
		this.tripleOre = tripleOre;
	}

	public float getGemFind() {
		return gemFind;
	}

	public void setGemFind(float gemFind) {
		this.gemFind = gemFind;
	}
}