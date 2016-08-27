package com.aegeus.aegeus.common;

public class Constants {

	public static boolean debug = false;
	public static final String BUILD = "dev";
	public static final String BUILD_NOTE = "ASCII boats.";
	public static final String[] DEVELOPERS = new String[]{"oopsjpeg", "Silvre"};
	
	/*
	 * These are the values of exp that ores will give you on your pickaxe.
	 * Also includes the range of each ore so you don't get the same xp every time. 
	 * The respawn timers are in ticks, 20 ticks in 1 second.
	 */
	public static final int RUTILE_EXP = 25, RUTILE_RANGE = 10, RUTILE_TIMER = 100; //Coal Ore
	public static final int BAUXITE_EXP = 50, BAUXITE_RANGE = 10, BAUXITE_TIMER = 100; //Redstone Ore
	public static final int IRON_EXP = 100, IRON_RANGE = 15, IRON_TIMER = 100; // Iron Ore
	public static final int LAZURITE_EXP = 150, LAZURITE_RANGE = 25, LAZURITE_TIMER = 100; // Lapis ore
	public static final int CRYSTAL_EXP = 300, CRYSTAL_RANGE = 35, CRYSTAL_TIMER = 100; //Diamond Ore
	public static final int VERIDIUM_EXP = 500, VERIDIUM_RANGE = 45, VERIDIUM_TIMER = 100; //Emerald Ore
	public static final int GOLD_EXP = 1000, GOLD_RANGE = 50, GOLD_TIMER = 100; //Gold Ore
    
}