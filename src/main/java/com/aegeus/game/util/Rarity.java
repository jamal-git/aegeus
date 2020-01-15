package com.aegeus.game.util;

public class Rarity {
    public static final float COMMON = .27f;
    public static final float UNCOMMON = .26f;
    public static final float RARE = .25f;
    public static final float UNIQUE = .22f;

    public static float get(float a) {

		/*
		Common: 0.00 - 0.68: 27%
		Uncommon: 0.68 - 0.82: 26%
		Rare: 0.82 - 0.97: 25%
		Unique: 0.97 - 1.00: 22%
		 */

        if (a <= 0.68) return Util.RANDOM.nextFloat() * COMMON;
        else if (a <= 0.82) return (Util.RANDOM.nextFloat() * UNCOMMON) + COMMON;
        else if (a <= 0.97) return (Util.RANDOM.nextFloat() * RARE) + UNCOMMON + COMMON;
        else return (Util.RANDOM.nextFloat() * UNIQUE) + RARE + UNCOMMON + COMMON;
    }
}
