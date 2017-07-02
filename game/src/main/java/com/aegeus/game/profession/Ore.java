package com.aegeus.game.profession;

import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.item.tool.Pickaxe;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Ore {
	RUTILE(Material.COAL_ORE, ChatColor.DARK_GRAY + "Rutile Ore", Arrays.asList(ChatColor.GRAY + "A very dirty chunk of stone with traces of Rutile in it.") ,115, 30, ProfessionTier.BASIC, 3),
	BAUXITE(Material.REDSTONE_ORE, ChatColor.RED + "Bauxite Ore", Arrays.asList(ChatColor.GRAY + "This chunk might proves useful for yielding Aluminium.") ,340, 80, ProfessionTier.IMPROVED, 5),
	IRON(Material.IRON_ORE, ChatColor.YELLOW + "Iron Ore", Arrays.asList(ChatColor.GRAY + "Magnetite contains large amounts of Iron, useful in many machines and equipment."),550, 100, ProfessionTier.IMPROVED, 10),
	AZURITE(Material.LAPIS_ORE, ChatColor.BLUE + "Azurite Ore", Arrays.asList(ChatColor.GRAY + "One of many minerals to contain copper, useful in manufacturing"),770, 140, ProfessionTier.REINFORCED, 20),
	GOLD(Material.GOLD_ORE, ChatColor.GOLD + "Gold Ore", Arrays.asList(ChatColor.GRAY + "This heavy ore contains gold, an excellent conductor in electronics."),1500, 200, ProfessionTier.ELITE, 30),
    CRYSTAL(Material.DIAMOND_ORE, ChatColor.AQUA + "Unsorted Crystal Cluster", Arrays.asList(ChatColor.GRAY + "An assortment of minerals and gemstones.", ChatColor.GRAY +"You should probably refine it first."),1150, 300, ProfessionTier.ELITE, 60),
    VERIDIUM(Material.EMERALD_ORE, ChatColor.DARK_GREEN + "Veridium Ore", Arrays.asList(ChatColor.GRAY + "The rarest of all minerals, only to be found in dangerous areas."),2100, 200, ProfessionTier.ULTIMATE, 90);

	private static final Random r = new Random();
	private final Material oreType;
	private final String displayName;
	private final int maxXP, range, respawnTime;
	private final ProfessionTier minimumTier;
	private final List<String> lore;

	Ore(Material oreItem, String displayName, List<String> lore, int maxXP, int range, ProfessionTier minimumTier, int respawnTime) {
		this.oreType = oreItem;
		this.displayName = displayName;
		this.lore = lore;
		this.maxXP = maxXP;
		this.range = range;
		this.minimumTier = minimumTier;
		this.respawnTime = respawnTime;
	}

	public static Ore getOreByMaterial(Material m)  {
	    for(Ore o : Ore.values())   if(o.getOre() == m || m == Material.GLOWING_REDSTONE_ORE) return o;
        return null;
    }

    /**
     * Determine if the tier of the pickaxe meets ore exceeds the mininmum tier of the ore being mined.
     * Useful when determining if the player can or cannot mine an ore.
     * @param p Pickaxe in question
     * @return If the pickaxe can mine the ore or not.
     */
    public boolean isMinable(Pickaxe p)  {
	    return minimumTier.ordinal() <= p.getTier().ordinal();
    }

    /**
     * Determine if the tier of the pickaxe is equal to the minimum tier of the ore being mined.
     * Useful in whether the player might fail mining an ore.
     * @param p Pickaxe in question
     * @return If pickaxe is same tier as minimum tier of ore
     */
    public boolean sameTier(Pickaxe p)   {
	    return minimumTier == p.getTier();
    }

    /**
     * Return the amount of seconds it takes to respawn after being mined.
     * @return Time to respawn in seconds
     */
    public long getRespawnTime() {
	    return respawnTime;
    }

    /**
     * Return the amount of ticks it takes to respawn after being mined.
     * @return Time to respawn in ticks
     */
    public long getRespawnTimeInTicks()  {
	    return respawnTime * 20L;
    }

	public Material getOre() {
		return oreType;
	}

	public String getName() {
		return displayName;
	}

	public List<String> getLore()  {
	    return lore;
    }
	public int getmaxXP()    {
	    return maxXP;
    }

    public int getRange()    {
	    return range;
    }

    public int getRandom()   {
	    return maxXP - r.nextInt(range);
    }
}