package com.aegeus.game.stats;

import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT5 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(5);
		setChance(0.009f);

		getDefaultArmor().hpRegen = new IntPossible(80, 140);
		getDefaultArmor().energyRegen = new FloatPossible(0.04f, 0.08f);
		getDefaultArmor().physRes = new FloatPossible(0.03f, 0.1f);
		getDefaultArmor().magRes = new FloatPossible(0.03f, 0.1f);
		getDefaultArmor().block = new FloatPossible(0.02f, 0.12f);
		getDefaultArmor().dodge = new FloatPossible(0.02f, 0.12f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.GOLD_HELMET;
		helmet.name = "&eLegendary Helmet";
		helmet.hp = new IntPossible(756, 2808);
		addHelmet(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.GOLD_CHESTPLATE;
		chestplate.name = "&eLegendary Chestplate";
		chestplate.hp = new IntPossible(1386, 5148);
		addChestplate(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.GOLD_LEGGINGS;
		leggings.name = "&eLegendary Leggings";
		leggings.hp = new IntPossible(1344, 4992);
		addLeggings(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.GOLD_BOOTS;
		boots.name = "&eLegendary Boots";
		boots.hp = new IntPossible(714, 2652);
		addBoots(boots);

		getDefaultWeapon().pen = new FloatPossible(0.02f, 0.18f);
		getDefaultWeapon().fireDmg = new IntPossible(6, 25);
		getDefaultWeapon().iceDmg = new IntPossible(6, 25);
		getDefaultWeapon().poisonDmg = new IntPossible(6, 25);
		getDefaultWeapon().pureDmg = new IntPossible(6, 25);
		getDefaultWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefaultWeapon().trueHearts = new FloatPossible(0.01f, 0.07f);
		getDefaultWeapon().blind = new FloatPossible(0.01f, 0.2f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.GOLD_SWORD;
		sword.name = "&eLegendary Sword";
		sword.dmg = new IntPossible(120, 300);
		sword.range = new IntPossible(0, 80);
		addWeapon(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.GOLD_AXE;
		axe.name = "&eLegendary Axe";
		axe.dmg = new IntPossible(126, 320);
		axe.range = new IntPossible(0, 75);
		addWeapon(axe);
	}
}
