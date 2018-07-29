package com.aegeus.game.item;

import java.util.ArrayList;
import java.util.List;

public class Tiers {
	public static class Dura {
		private static List<Integer> armor = new ArrayList<>();
		private static List<Integer> weapon = new ArrayList<>();

		public static int armor(int tier) {
			return armor.get(tier);
		}

		public static int weapon(int tier) {
			return weapon.get(tier);
		}

		public static List<Integer> getArmor() {
			return armor;
		}

		public static void setArmor(List<Integer> armor) {
			Dura.armor = armor;
		}

		public static List<Integer> getWeapon() {
			return weapon;
		}

		public static void setWeapon(List<Integer> weapon) {
			Dura.weapon = weapon;
		}
	}

	public static class DefStats {
		private static List<Stats> stats = new ArrayList<>();

		public static Stats get(int tier) {
			return stats.get(tier);
		}

		public static List<Stats> getStats() {
			return stats;
		}

		public static void setStats(List<Stats> stats) {
			DefStats.stats = stats;
		}
	}
}
