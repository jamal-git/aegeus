package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.item.info.ProfessionInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class FishRod extends AgItem implements ProfessionInfo {
	// Level Info
	private int level;
	private int xp;
	private int requiredXp;
	private ProfessionTier tier;

	// Fishing Rod Stats
	private float fishingSuccess = 0;
	private float durability = 0;
	private float doubleCatch = 0;
	private float tripleCatch = 0;
	private float junkFind = 0;
	private float treasureFind = 0;

	public FishRod() {
		super(Material.FISHING_ROD);
	}

	public FishRod(ItemStack item) {
		super(item);
		impo();
	}

	@Override
	public void impo() {
		ProfessionInfo.impo(this);

		NBTTagCompound info = getAegeusInfo();
		fishingSuccess = (info.hasKey("fishingSuccess")) ? info.getInt("fishingSuccess") : 0;
		durability = (info.hasKey("durability")) ? info.getInt("durability") : 0;
		doubleCatch = (info.hasKey("doubleCatch")) ? info.getInt("doubleCatch") : 0;
		tripleCatch = (info.hasKey("tripleCatch")) ? info.getInt("tripleCatch") : 0;
		junkFind = (info.hasKey("junkFind")) ? info.getInt("junkFind") : 0;
		treasureFind = (info.hasKey("treasureFind")) ? info.getInt("treasureFind") : 0;
	}

	@Override
	public void store() {
		ProfessionInfo.store(this);

		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("fishrod"));
		info.set("fishingSuccess", new NBTTagFloat(fishingSuccess));
		info.set("durability", new NBTTagFloat(durability));
		info.set("doubleCatch", new NBTTagFloat(doubleCatch));
		info.set("tripleCatch", new NBTTagFloat(tripleCatch));
		info.set("junkFind", new NBTTagFloat(junkFind));
		info.set("treasureFind", new NBTTagFloat(treasureFind));
		setAegeusInfo(info);
	}

	public List<String> buildLore() {
		List<String> lore = ProfessionInfo.buildLore(this);
		if (fishingSuccess > 0) lore.add(Util.colorCodes("&cFISHING SUCCESS: +" + fishingSuccess));
		if (durability > 0) lore.add(Util.colorCodes("&cDURABILITY: +" + durability));
		if (doubleCatch > 0) lore.add(Util.colorCodes("&cDOUBLE CATCH: +" + doubleCatch));
		if (tripleCatch > 0) lore.add(Util.colorCodes("&cTRIPLE CATCH: +" + tripleCatch));
		if (junkFind > 0) lore.add(Util.colorCodes("&cJUNK FIND: +" + junkFind));
		if (treasureFind > 0) lore.add(Util.colorCodes("&cTREASURE FIND: +" + treasureFind));
		return lore;
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("fishrod");
	}

	@Override
	public ItemStack build() {
		store();
		ItemStack item = super.build();
		ItemMeta meta = item.getItemMeta();

		meta.setLore(Util.union(buildLore(), getLore()));

		item.setItemMeta(meta);

		return item;
	}

	/*
	Info Overrides
	 */

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
	public int getRequiredXp() {
		return (int) Math.round(1000 + (Math.pow(getLevel(), 1.114) * 200));
	}

	/*
	Fishing Rod Methods
	 */

	public ProfessionTier getTier() {
		return tier;
	}

	public void setTier(ProfessionTier tier) {
		this.tier = tier;
	}

	public float getFishingSuccess() {
		return fishingSuccess;
	}

	public void setFishingSuccess(float fishingSuccess) {
		this.fishingSuccess = fishingSuccess;
	}

	public float getDurability() {
		return durability;
	}

	public void setDurability(float durability) {
		this.durability = durability;
	}

	public float getDoubleCatch() {
		return doubleCatch;
	}

	public void setDoubleCatch(float doubleCatch) {
		this.doubleCatch = doubleCatch;
	}

	public float getTripleCatch() {
		return tripleCatch;
	}

	public void setTripleCatch(float tripleCatch) {
		this.tripleCatch = tripleCatch;
	}

	public float getJunkFind() {
		return junkFind;
	}

	public void setJunkFind(float junkFind) {
		this.junkFind = junkFind;
	}

	public float getTreasureFind() {
		return treasureFind;
	}

	public void setTreasureFind(float treasureFind) {
		this.treasureFind = treasureFind;
	}
}
