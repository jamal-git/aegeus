package com.aegeus.game.stats;

import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT3 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(3);
		setChance(0.025f);

		getDefaultArmor().hpRegen = new IntPossible(15, 40);
		getDefaultArmor().energyRegen = new FloatPossible(0.02f, 0.05f);
		getDefaultArmor().physRes = new FloatPossible(0.01f, 0.05f);
		getDefaultArmor().magRes = new FloatPossible(0.01f, 0.05f);
		getDefaultArmor().block = new FloatPossible(0.01f, 0.06f);
		getDefaultArmor().dodge = new FloatPossible(0.01f, 0.06f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.IRON_HELMET;
		helmet.name = "&bMagic Helmet";
		helmet.hp = new IntPossible(112, 538);
		addHelmet(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.IRON_CHESTPLATE;
		chestplate.name = "&bMagic Chestplate";
		chestplate.hp = new IntPossible(205, 987);
		addChestplate(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.IRON_LEGGINGS;
		leggings.name = "&bMagic Leggings";
		leggings.hp = new IntPossible(198, 957);
		addLeggings(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.IRON_BOOTS;
		boots.name = "&bMagic Boots";
		boots.hp = new IntPossible(105, 508);
		addBoots(boots);

		getDefaultWeapon().pen = new FloatPossible(0.01f, 0.1f);
		getDefaultWeapon().fireDmg = new IntPossible(2, 10);
		getDefaultWeapon().iceDmg = new IntPossible(2, 10);
		getDefaultWeapon().poisonDmg = new IntPossible(2, 10);
		getDefaultWeapon().pureDmg = new IntPossible(2, 10);
		getDefaultWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefaultWeapon().trueHearts = new FloatPossible(0.01f, 0.05f);
		getDefaultWeapon().blind = new FloatPossible(0.01f, 0.16f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.IRON_SWORD;
		sword.name = "&bMagic Sword";
		sword.dmg = new IntPossible(21, 68);
		sword.range = new IntPossible(0, 40);
		addWeapon(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.IRON_AXE;
		axe.name = "&bMagic Axe";
		axe.dmg = new IntPossible(23, 70);
		axe.range = new IntPossible(0, 36);
		addWeapon(axe);
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}
}
