package com.aegeus.game.stats;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT1Bandit extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(1);
		setChance(0.1f);
		setHpMultiplier(0.86f);
		setDmgMultiplier(0.95f);

		addName("Tired Bandit");
		addName("Old Bandit");
		addName("Angry Bandit");

		addType(EntityType.ZOMBIE);

		ArmorPossible armor = new ArmorPossible();
		armor.material = Material.LEATHER_HELMET;
		armor.hp = new IntPossible(10, 56);
		addHelmet(armor);

		armor.material = Material.LEATHER_CHESTPLATE;
		armor.hp = new IntPossible(18, 98);
		addChestplate(armor);

		armor.material = Material.LEATHER_LEGGINGS;
		armor.hp = new IntPossible(15, 84);
		addLeggings(armor);

		armor.material = Material.LEATHER_BOOTS;
		armor.hp = new IntPossible(8, 42);
		addBoots(armor);

		WeaponPossible weapon = new WeaponPossible();
		weapon.strength = new IntPossible(1, 10);
		weapon.dexterity = new IntPossible(1, 10);
		weapon.intellect = new IntPossible(1, 10);
		weapon.vitality = new IntPossible(1, 10);
		weapon.fireDmg = new IntPossible(1, 8);
		weapon.iceDmg = new IntPossible(1, 8);
		weapon.poisonDmg = new IntPossible(1, 8);
		weapon.lifeSteal = new FloatPossible(0.01f, 0.2f);

		weapon.material = Material.WOOD_SWORD;
		weapon.dmg = new IntPossible(1, 16);
		weapon.range = new IntPossible(0, 4);
		addWeapon(weapon);

		weapon.material = Material.WOOD_AXE;
		weapon.dmg = new IntPossible(1, 17);
		weapon.range = new IntPossible(0, 5);
		addWeapon(weapon);
	}

	@Override
	public boolean hasChestplate() {
		return random.nextBoolean();
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}

	@Override
	public boolean hasBoots() {
		return random.nextBoolean();
	}
}
