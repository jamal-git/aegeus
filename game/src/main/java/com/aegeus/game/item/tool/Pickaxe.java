package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.item.info.ProfessionInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Silvre on 6/24/2017.
 */
public class Pickaxe extends AgItem implements ProfessionInfo {

	private static final double CONSTANT = 1.114;
	private static final Random r = new Random();

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
	private float durability;

	/**
	 * TODO
	 * <p>
	 * NOTE THAT DURABILITY THE VARIABLE AND DURABILITYENCHANT IN METHODS REFERS TO DURABILITY THE
	 * ENCHANT ON THE PICKAXE AND NOT THE ACTUAL DURABILITY OF THE ITEM.
	 * <p>
	 * DURA THE VARIABLE AND DURABILITY IN METHODS REFERS TO THE ACTUAL DURABILITY
	 * OF THE ITEM AND NOT THE ENCHANT USED TO INCREASE THE ACTUAL DURABILITY OF ITEM.
	 * <p>
	 * I KNOW THAT SOUNDS VERY CONFUSING BUT TRUST ME IT WORKS.  MAYBE ONE DAY I WILL
	 * UPDATE THE VARIABLE AND METHOD NAMES SO IT WILL NOT BE AS CONFUSING AS IT
	 * IS IN THE CURRENT STATE OF THE CODE
	 */

	public Pickaxe(ItemStack stack) {
		super(stack);
	}

	public Pickaxe() {
		this(1);
	}

	public Pickaxe(int level) {
		super(ProfessionTier.getTierByLevel(level).getPickaxeMaterial());
		this.level = level;
		tier = ProfessionTier.getTierByLevel(level);
		xp = 0;
		requiredxp = getXPRequired(level + 1);
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

	public int getXPRequired() {
		return requiredxp;
	}

	@Override
	public void impo() {
		ProfessionInfo.impo(this);

		NBTTagCompound info = getAegeusInfo();
		setMiningSuccess(info.hasKey("miningSuccess") ? info.getFloat("miningSuccess") : 0);
		setDenseFind(info.hasKey("denseFind") ? info.getFloat("denseFind") : 0);
		setDenseMultiplier(info.hasKey("denseMultiplier") ? info.getInt("denseMultiplier") : 0);
		setDoubleOre(info.hasKey("doubleOre") ? info.getFloat("doubleOre") : 0);
		setTripleOre(info.hasKey("tripleOre") ? info.getFloat("tripleOre") : 0);
		setGemFind(info.hasKey("gemFind") ? info.getFloat("gemFind") : 0);
		setDurabilityEnchant(info.hasKey("durability") ? info.getFloat("durability") : 0);

	}

	@Override
	public void store() {
		ProfessionInfo.store(this);

		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("pickaxe"));
		info.set("miningSuccess", new NBTTagFloat(getMiningSuccess()));
		info.set("denseFind", new NBTTagFloat(getDenseFind()));
		info.set("denseMultiplier", new NBTTagInt(getDenseMultiplier()));
		info.set("doubleOre", new NBTTagFloat(getDoubleOre()));
		info.set("tripleOre", new NBTTagFloat(getTripleOre()));
		info.set("gemFind", new NBTTagFloat(getGemFind()));
		info.set("durability", new NBTTagFloat(getDurabilityEnchant()));
		setAegeusInfo(info);
	}

	public List<String> buildLore() {
		List<String> lore = ProfessionInfo.buildLore(this);
		if (miningSuccess > 0) lore.add(Util.colorCodes("&cMINING SUCCESS: +" + (miningSuccess * 100) + "%"));
		if (denseFind > 0) lore.add(Util.colorCodes("&cDENSE FIND: +" + denseFind * 100 + "%"));
		if (denseMultiplier > 0) lore.add(Util.colorCodes("&cDENSE MULTIPLIER: " + denseMultiplier + "x"));
		if (doubleOre > 0) lore.add(Util.colorCodes("&cDOUBLE ORE: +" + doubleOre * 100 + "%"));
		if (tripleOre > 0) lore.add(Util.colorCodes("&cTRIPLE ORE: +" + tripleOre * 100 + "%"));
		if (gemFind > 0) lore.add(Util.colorCodes("&cGEM FIND: +" + gemFind * 100 + "%"));
		if (durability > 0) lore.add(Util.colorCodes("&cDURABILITY: +" + durability * 100 + "%"));
		return lore;
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("pickaxe");
	}

	@Override
	public ItemStack build() {
		store();
		setLore(buildLore());
		setName(tier.getColor() + tier.getPickaxeName());
		return super.build();
	}

	public boolean addExp(int i) {
		xp += i;
		if (xp >= requiredxp) {
			xp = 0;
			requiredxp = getXPRequired(++level + 1);
			if (level % 20 == 0) {
				tier = tier.next();
				setMaterial(tier.getPickaxeMaterial());
				//todo implement stats.
				tieredUp = true;
				int j = 1;
				switch (getTier()) {
					case IMPROVED:
					case REINFORCED:
						j = 5;
						break;
					case ELITE:
					case ULTIMATE:
					case TRANSCENDENT:
						j = 7;
						break;
				}
				switch (r.nextInt(j)) {
					case 0: //MINING SUCCESS
						if (tier == ProfessionTier.IMPROVED) miningSuccess += (r.nextInt(2) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.REINFORCED) miningSuccess += (r.nextInt(4) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ELITE) miningSuccess += (r.nextInt(8) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ULTIMATE) miningSuccess += (r.nextInt(10) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.TRANSCENDENT) miningSuccess += (r.nextInt(15) + 1.0f) / 100.0f;
						break;
					case 1: //DOUBLE ORE
						if (tier == ProfessionTier.IMPROVED) doubleOre += (r.nextInt(3) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.REINFORCED) doubleOre += (r.nextInt(3) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ELITE) doubleOre += (r.nextInt(5) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ULTIMATE) doubleOre += (r.nextInt(7) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.TRANSCENDENT) doubleOre += (r.nextInt(9) + 1.0f) / 100.0f;
						break;
					case 2: //TRIPLE ORE
						if (tier == ProfessionTier.IMPROVED) tripleOre += (r.nextInt(1) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.REINFORCED) tripleOre += (r.nextInt(2) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ELITE) tripleOre += (r.nextInt(4) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ULTIMATE) tripleOre += (r.nextInt(5) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.TRANSCENDENT) tripleOre += (r.nextInt(7) + 1.0f) / 100.0f;
						break;
					case 3: //GEM FIND
						if (tier == ProfessionTier.IMPROVED) gemFind += (r.nextInt(2) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.REINFORCED) gemFind += (r.nextInt(3) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ELITE) gemFind += (r.nextInt(5) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ULTIMATE) gemFind += (r.nextInt(7) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.TRANSCENDENT) gemFind += (r.nextInt(9) + 1.0f) / 100.0f;
						break;
					case 4: //DURABILITY
						if (tier == ProfessionTier.IMPROVED) durability += (r.nextInt(5) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.REINFORCED) durability += (r.nextInt(7) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ELITE) durability += (r.nextInt(11) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ULTIMATE) durability += (r.nextInt(13) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.TRANSCENDENT) durability += (r.nextInt(15) + 1.0f) / 100.0f;
						break;
					case 5: //DENSE MULTIPLIER
						if (tier == ProfessionTier.ELITE) denseMultiplier += r.nextInt(3);
						if (tier == ProfessionTier.ULTIMATE) denseMultiplier += r.nextInt(5);
						if (tier == ProfessionTier.TRANSCENDENT) denseMultiplier += r.nextInt(7);
						if (denseFind != 0.0) break;
					case 6: //DENSE FIND
						if (tier == ProfessionTier.ELITE) denseFind += (r.nextInt(2) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.ULTIMATE) denseFind += (r.nextInt(4) + 1.0f) / 100.0f;
						if (tier == ProfessionTier.TRANSCENDENT) denseFind += (r.nextInt(5) + 1.0f) / 100.0f;
						break;
					default:
						break;
				}
			}
			return true;
		}
		return false;
	}

	public boolean checkForNextTier() {
		return !(tieredUp ^= true);
	}

    /*
	 * Encapsulations
     */

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

	public float getDurabilityEnchant() {
		return durability;
	}

	public void setDurabilityEnchant(float durability) {
		this.durability = durability;
	}
}