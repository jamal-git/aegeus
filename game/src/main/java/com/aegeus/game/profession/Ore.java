package com.aegeus.game.profession;

import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum Ore {
	RUTILE(Material.COAL_ORE, ChatColor.DARK_GRAY + "Rutile Ore", Collections.singletonList(Util.colorCodes("&7A very dirty chunk of stone with traces of Rutile in it.")), 115, 30, ProfessionTier.BASIC, 3, 10),
	BAUXITE(Material.REDSTONE_ORE, ChatColor.RED + "Bauxite Ore", Collections.singletonList(Util.colorCodes("&7This chunk might prove useful for yielding Aluminium.")), 250, 50, ProfessionTier.IMPROVED, 5, 20),
	IRON(Material.IRON_ORE, ChatColor.YELLOW + "Iron Ore", Collections.singletonList(Util.colorCodes("&7Magnetite contains large amounts of Iron, useful in many machines and equipment.")), 350, 75, ProfessionTier.IMPROVED, 10, 30),
	AZURITE(Material.LAPIS_ORE, ChatColor.BLUE + "Azurite Ore", Collections.singletonList(Util.colorCodes("&7One of many minerals to contain copper, useful in manufacturing")), 770, 100, ProfessionTier.REINFORCED, 20, 50),
	GOLD(Material.GOLD_ORE, ChatColor.GOLD + "Gold Ore", Collections.singletonList(Util.colorCodes("&7This heavy ore contains gold, an excellent conductor in electronics.")), 950, 125, ProfessionTier.ELITE, 30, 90),
	CRYSTAL(Material.DIAMOND_ORE, ChatColor.AQUA + "Unsorted Crystal Cluster", Arrays.asList(Util.colorCodes("&7An assortment of minerals and gemstones."), Util.colorCodes("&7You should probably refine it first.")), 1500, 300, ProfessionTier.ELITE, 60, 85),
	VERIDIUM(Material.EMERALD_ORE, ChatColor.DARK_GREEN + "Veridium Ore", Collections.singletonList(Util.colorCodes("&7The rarest of all minerals, only to be found in dangerous areas.")), 2100, 150, ProfessionTier.ULTIMATE, 90, 120);

	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Material oreType;
	private final String displayName;
	private final int maxXP, range, respawnTime, goldDrop;
	private final ProfessionTier minimumTier;
	private final List<String> lore;

	Ore(Material oreItem, String displayName, List<String> lore, int maxXP, int range, ProfessionTier minimumTier, int respawnTime, int goldDrop) {
		this.oreType = oreItem;
		this.displayName = displayName;
		this.lore = lore;
		this.maxXP = maxXP;
		this.range = range;
		this.minimumTier = minimumTier;
		this.respawnTime = respawnTime;
		this.goldDrop = goldDrop;
	}

	public static Ore getOreByMaterial(Material m) {
		if (m == Material.GLOWING_REDSTONE_ORE) return Ore.BAUXITE;
		for (Ore o : Ore.values()) if (o.getOre() == m) return o;
		return null;
	}

	/**
	 * Determine if the tier of the pickaxe meets ore exceeds the mininmum tier of the ore being mined.
	 * Useful when determining if the player can or cannot mine an ore.
	 *
	 * @param p Pickaxe in question
	 * @return If the pickaxe can mine the ore or not.
	 */
	public boolean isMinable(Pickaxe p) {
		return minimumTier.ordinal() <= p.getTier().ordinal();
	}

	/**
	 * Determine if the tier of the pickaxe is equal to the minimum tier of the ore being mined.
	 * Useful in whether the player might fail mining an ore.
	 *
	 * @param p Pickaxe in question
	 * @return If pickaxe is same tier as minimum tier of ore
	 */
	public boolean sameTier(Pickaxe p) {
		return minimumTier == p.getTier();
	}

	/**
	 * Return the amount of seconds it takes to respawn after being mined.
	 *
	 * @return Time to respawn in seconds
	 */
	public long getRespawnTime() {
		return respawnTime;
	}

	/**
	 * Return the amount of ticks it takes to respawn after being mined.
	 *
	 * @return Time to respawn in ticks
	 */
	public long getRespawnTimeInTicks() {
		return respawnTime * 20L;
	}

	public Material getOre() {
		return oreType;
	}

	public String getName() {
		return displayName;
	}

	public List<String> getLore() {
		return lore;
	}

	public int getmaxXP() {
		return maxXP;
	}

	public int getRange() {
		return range;
	}

	public int getRandom() {
		return maxXP - random.nextInt(range);
	}

	public int getRandomGold() {
		return random.nextInt(goldDrop) + goldDrop / 2;
	}
}