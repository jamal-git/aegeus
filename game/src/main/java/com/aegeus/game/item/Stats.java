package com.aegeus.game.item;

import com.aegeus.game.item.impl.ItemWeapon;
import com.aegeus.game.util.IntRange;
import com.aegeus.game.util.Util;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Stats {
	private List<Weapon> weapons = new ArrayList<>();

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<Weapon> weapons) {
		this.weapons = weapons;
	}

	public ItemWeapon weapon() {
		return weapon(Util.rarity(Util.randFloat()));
	}

	public ItemWeapon weapon(float f) {
		return !weapons.isEmpty() ? weapons.get(Util.randInt(weapons.size())).get(f) : null;
	}

	public static class Weapon {
		private Material material;
		private IntRange dmg;
		private IntRange range;

		public Weapon(Material material, IntRange dmg, IntRange range) {
			this.material = material;
			this.dmg = dmg;
			this.range = range;
		}

		public ItemWeapon get(float f) {
			ItemWeapon weapon = new ItemWeapon(material);
			weapon.setMinDMG((int) (dmg.getMin() + ((dmg.getMax() - dmg.getMin()) * f)));
			weapon.setMaxDMG(weapon.getMinDMG() + range.get());
			return weapon;
		}
	}
}
