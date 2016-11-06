package com.aegeus.game.stats;

import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.google.common.collect.Lists;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatsContainer {
	private final Random RANDOM = new Random();
	private List<ArmorPossibility> helmets = new ArrayList<>();
	private List<ArmorPossibility> chestplates = new ArrayList<>();
	private List<ArmorPossibility> leggings = new ArrayList<>();
	private List<ArmorPossibility> boots = new ArrayList<>();
	private List<WeaponPossibility> weapons = new ArrayList<>();

	public void addHelmets(ArmorPossibility... possible) {
		this.helmets.addAll(Lists.newArrayList(possible));
	}

	public void addChestplates(ArmorPossibility... possible) {
		this.chestplates.addAll(Lists.newArrayList(possible));
	}

	public void addLeggings(ArmorPossibility... possible) {
		this.leggings.addAll(Lists.newArrayList(possible));
	}

	public void addBoots(ArmorPossibility... possible) {
		this.boots.addAll(Lists.newArrayList(possible));
	}

	public void addWeapons(WeaponPossibility... possible) {
		this.weapons.addAll(Lists.newArrayList(possible));
	}

	public List<ArmorPossibility> getAllHelmets() {
		return helmets;
	}

	public ArmorPossibility getHelmet() {
		return helmets.get(RANDOM.nextInt(helmets.size()));
	}

	public List<ArmorPossibility> getAllChestplates() {
		return chestplates;
	}

	public ArmorPossibility getChestplate() {
		return chestplates.get(RANDOM.nextInt(chestplates.size()));
	}

	public List<ArmorPossibility> getAllLeggings() {
		return leggings;
	}

	public ArmorPossibility getLeggings() {
		return leggings.get(RANDOM.nextInt(leggings.size()));
	}

	public List<ArmorPossibility> getAllBoots() {
		return boots;
	}

	public ArmorPossibility getBoots() {
		return boots.get(RANDOM.nextInt(boots.size()));
	}

	public List<WeaponPossibility> getAllWeapons() {
		return weapons;
	}

	public WeaponPossibility getWeapon() {
		return weapons.get(RANDOM.nextInt(weapons.size()));
	}

	public Armor get(ArmorPossibility p, float f) {
		Armor armor = new Armor(p.material);
		armor.getEquipmentInfo().setTier(p.tier);
		armor.setHp(Math.round(f * (p.maxHp)) + p.minHp);
		return armor;
	}

	public Weapon get(WeaponPossibility p, float f) {
		Weapon weapon = new Weapon(p.material);
		weapon.getEquipmentInfo().setTier(p.tier);
		int min = Math.round(f * (p.maxDmg - p.range)) + p.minDmg;
		int max = Math.round(RANDOM.nextInt(p.range) + min);
		weapon.setDmg(min, max);
		return weapon;
	}

	public class WeaponPossibility {
		private Material material;
		private int tier;
		private int minDmg;
		private int maxDmg;
		private int range;

		public WeaponPossibility(
				Material material, int tier,
				int minDmg, int maxDmg, int range) {
			this.material = material;
			this.tier = tier;
			this.minDmg = minDmg;
			this.maxDmg = maxDmg;
			this.range = range;
		}
	}

	public class ArmorPossibility {
		private Material material;
		private int tier;
		private int minHp;
		private int maxHp;

		public ArmorPossibility(
				Material material, int tier,
				int minHp, int maxHp) {
			this.material = material;
			this.tier = tier;
			this.minHp = minHp;
			this.maxHp = maxHp;
		}
	}

}
