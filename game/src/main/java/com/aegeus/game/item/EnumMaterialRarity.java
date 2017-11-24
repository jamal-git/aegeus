package com.aegeus.game.item;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum EnumMaterialRarity {
	COMMON(ChatColor.WHITE, "Common"),
	UNCOMMON(ChatColor.GREEN, "Uncommon"),
	RARE(ChatColor.BLUE, "Rare"),
	ARCANE(ChatColor.LIGHT_PURPLE, "Arcane"),
	UNIQUE(ChatColor.GOLD, "Unique"),
	DIVINE(ChatColor.AQUA, "Divine"),
	CELESTIAL(ChatColor.RED, "Celestial"),
	MYTHICAL(ChatColor.DARK_BLUE, "Mythical"),
	LEGENDARY(ChatColor.YELLOW, "Legendary");

	public static Map<EnumMaterialRarity, Set<EnumCraftingMaterial>> materialRarityListMap = new HashMap<>();
	private ChatColor color;
	private String name;

	EnumMaterialRarity(ChatColor color, String name) {
		this.color = color;
		this.name = name;
	}

	public static Set<EnumCraftingMaterial> getMaterialsByRarity(EnumMaterialRarity r) {
		return materialRarityListMap.get(r);
	}

	public ChatColor getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public String getNameAndColor() {
		return color + name;
	}
}
