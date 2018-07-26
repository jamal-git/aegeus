package com.aegeus.game.item;

import java.util.HashMap;
import java.util.Map;

public class Tiers {
	public static class Durability {
		private static Map<Integer, Integer> armor = new HashMap<>();
		private static Map<Integer, Integer> weapon = new HashMap<>();

		public static int armor(int tier) {
			return armor.getOrDefault(tier, 0);
		}

		public static int weapon(int tier) {
			return weapon.getOrDefault(tier, 0);
		}

		public static Map<Integer, Integer> getArmor() {
			return armor;
		}

		public static void setArmor(Map<Integer, Integer> armor) {
			Durability.armor = armor;
		}

		public static Map<Integer, Integer> getWeapon() {
			return weapon;
		}

		public static void setWeapon(Map<Integer, Integer> weapon) {
			Durability.weapon = weapon;
		}
	}
}
