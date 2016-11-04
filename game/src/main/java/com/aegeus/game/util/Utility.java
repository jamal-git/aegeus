package com.aegeus.game.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {
	public static float calcRarity(float i, float min, float max) {
		// TODO do this without basic math
		i /= 1;
		return (float) (-max * (Math.sqrt(1 - i * i) - 1) + min);
	}
	
	public static String colorCodes(String s){
		return colorCodes(s, '&');
	}
	public static String colorCodes(String s, char c){
		return ChatColor.translateAlternateColorCodes(c, s);
	}

	public static int calcMaxXP(int level){
		return (int) Math.round(50 * (level * (level * 0.4)));
	}
	public static int calcLevelBuff(int value, int level){
		return Math.round((value * level) / 65);
	}

	public static String buildString(String[] args, int start) {
		List<String> build = new ArrayList<>();
		build.addAll(Arrays.asList(args).subList(start, args.length));
		return StringUtils.join(build, " ");
	}
}
