package com.aegeus.game.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum ProfessionTier {
	BASIC("Basic Pickaxe", "Basic Fishing Rod", Material.WOOD_PICKAXE, ChatColor.RED, 500),
	IMPROVED("Improved Pickaxe", "Improved Fishing Rod", Material.STONE_PICKAXE, ChatColor.GREEN, 1000),
	REINFORCED("Reinforced Pickaxe", "Reinforced Fishing Rod", Material.IRON_PICKAXE, ChatColor.AQUA, 1500),
	ELITE("Elite Pickaxe", "Elite Fishing Rod", Material.DIAMOND_PICKAXE, ChatColor.LIGHT_PURPLE, 2000),
	ULTIMATE("Ultimate Pickaxe", "Ultimate Fishing Rod", Material.GOLD_PICKAXE, ChatColor.YELLOW, 3000),
	TRANSCENDENT("Transcendent Pickaxe", "Transcendent Fishing Rod", Material.GOLD_PICKAXE, ChatColor.YELLOW, 5000);

	private static final ProfessionTier[] vals = values();

	private final Material pickMat;
	private final ChatColor color;
	private final String pickName;
	private final String rodName;
	private final int maxDurability;

	ProfessionTier(String pickName, String rodName, Material pickMat, ChatColor color, int maxDurability) {
		this.pickName = pickName;
		this.rodName = rodName;
		this.pickMat = pickMat;
		this.color = color;
		this.maxDurability = maxDurability;
	}

	public static ProfessionTier getTierByLevel(int level) {
		if (level >= 100) {
			return TRANSCENDENT;
		}
		if (level >= 80) {
			return ULTIMATE;
		}
		if (level >= 60) {
			return ELITE;
		}
		if (level >= 40) {
			return REINFORCED;
		}
		if (level >= 20) {
			return IMPROVED;
		}
		return BASIC;
	}

	public ProfessionTier next() {
		return vals[(this.ordinal() + 1) % vals.length];
	}

	public ChatColor getColor() {
		return color;
	}

	public String getPickaxeName() {
		return pickName;
	}

	public Material getPickaxeMaterial() {
		return pickMat;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public String getRodName() {
		return rodName;
	}

	public Material getRodMaterial() {
		return Material.FISHING_ROD;
	}
}
