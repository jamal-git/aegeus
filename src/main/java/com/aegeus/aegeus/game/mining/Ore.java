package com.aegeus.aegeus.game.mining;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Ore {
	RUTILE (Material.COAL_ORE, ChatColor.DARK_GRAY + "Rutile Ore"),
	BAUXITE (Material.REDSTONE_ORE, ChatColor.RED + "Bauxite Ore"),
	IRON (Material.IRON_ORE, ChatColor.YELLOW + "Iron Ore"),
	LAZURITE (Material.LAPIS_ORE, ChatColor.BLUE + "Lazurite Ore"),
	CRYSTAL (Material.DIAMOND_ORE, ChatColor.AQUA + "Unsorted Crystal Cluster"),
	VERIDIUM (Material.EMERALD_ORE, ChatColor.DARK_GREEN + "Veridium Ore"),
	GOLD (Material.GOLD_ORE, ChatColor.GOLD + "Gold Ore");
	
	private final Material oreType;
	private final String displayName;
	
	Ore(Material oreItem, String displayName)	{
		this.oreType = oreItem;
		this.displayName = displayName;
	}
	
	protected Material getOre()	{
		return oreType;
	}
	
	protected String getName()	{
		return displayName;
	}
}
