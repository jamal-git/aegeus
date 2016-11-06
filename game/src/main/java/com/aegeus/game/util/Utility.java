package com.aegeus.game.util;

import org.bukkit.ChatColor;

import java.util.Random;

public class Utility {
	;
	private static final Random RANDOM = new Random();

	public static float rarity() {
		double a = Math.random();
		/*
			Common - 0 - 65 - 35%
			Uncommon - 66 - 87 - 30%
			Rare - 88 - 97 - 25%
			Legendary - 98 - 100 - 10%
		 */
		if (a <= 0.65) return RANDOM.nextFloat() * 0.35f;
		else if (a <= 0.87) return (RANDOM.nextFloat() * 0.30f) + 0.35f;
		else if (a <= 0.97) return (RANDOM.nextFloat() * 0.25f) + 0.65f;
		else return (RANDOM.nextFloat() * 0.10f) + 0.90f;
	}

	public static String colorCodes(String s) {
		return colorCodes(s, '&');
	}

	public static String colorCodes(String s, char c) {
		return ChatColor.translateAlternateColorCodes(c, s);
	}

	public static long calcMaxXP(int level) {
		return 100 + Math.round(Math.pow(50 * level, 1.28));
	}

	public static int calcLevelBuff(int value, int level) {
		return Math.round((value * level) / 65);
	}
}
