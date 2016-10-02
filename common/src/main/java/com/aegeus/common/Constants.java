package com.aegeus.common;

public class Constants {

	public static final boolean DEBUG = false;
	public static final String BUILD = "dev";
	public static final String BUILD_NOTE = "ASCII boats.";
	public static final String[] DEVELOPERS = new String[]{"oopsjpeg", "Silvre", "AkenoHimejima_"};
	
	/*
	 * These are the values of exp that ores will give you on your pickaxe.
	 * Also includes the range of each ore so you don't get the same xp every time. 
	 * The respawn timers are in ticks, 20 ticks in 1 second.
	 */
	public static final int BAUXITE_EXP = 50, BAUXITE_RANGE = 10, BAUXITE_TIMER = 100; // Redstone Ore T1
	public static final int IRON_EXP = 100, IRON_RANGE = 15, IRON_TIMER = 100; // Iron Ore T2
	public static final int RUTILE_EXP = 300, RUTILE_RANGE = 25, RUTILE_TIMER = 100; // Coal Ore T3
	public static final int CRYSTAL_EXP = 250, CRYSTAL_RANGE = 25, CRYSTAL_TIMER = 100; // Diamond Ore T3
	public static final int LAZURITE_EXP = 450, LAZURITE_RANGE = 35, LAZURITE_TIMER = 100; // Lapis ore T4
	public static final int GOLD_EXP = 500, GOLD_RANGE = 45, GOLD_TIMER = 100; // Gold Ore T4
	public static final int VERIDIUM_EXP = 1000, VERIDIUM_RANGE = 50, VERIDIUM_TIMER = 100; // Emerald Ore T5
    
}
