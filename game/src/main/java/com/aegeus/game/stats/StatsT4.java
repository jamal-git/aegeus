package com.aegeus.game.stats;

import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT4 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(4);
		setChance(0.012f);

		getDefaultArmor().hpRegen = new IntPossible(50, 90);
		getDefaultArmor().energyRegen = new FloatPossible(0.03f, 0.06f);
		getDefaultArmor().physRes = new FloatPossible(0.02f, 0.07f);
		getDefaultArmor().magRes = new FloatPossible(0.02f, 0.07f);
		getDefaultArmor().block = new FloatPossible(0.01f, 0.1f);
		getDefaultArmor().dodge = new FloatPossible(0.01f, 0.1f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.DIAMOND_HELMET;
		helmet.name = "&dAncient Helmet";
		helmet.hp = new IntPossible(468, 1710);
		addHelmet(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.DIAMOND_CHESTPLATE;
		chestplate.name = "&dAncient Chestplate";
		chestplate.hp = new IntPossible(858, 3135);
		addChestplate(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.DIAMOND_LEGGINGS;
		leggings.name = "&dAncient Boots";
		leggings.hp = new IntPossible(832, 3040);
		addLeggings(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.DIAMOND_BOOTS;
		boots.name = "&dAncient Boots";
		boots.hp = new IntPossible(442, 1615);
		addBoots(boots);

		getDefaultWeapon().pen = new FloatPossible(0.01f, 0.12f);
		getDefaultWeapon().fireDmg = new IntPossible(3, 18);
		getDefaultWeapon().iceDmg = new IntPossible(3, 18);
		getDefaultWeapon().poisonDmg = new IntPossible(3, 18);
		getDefaultWeapon().pureDmg = new IntPossible(3, 18);
		getDefaultWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefaultWeapon().trueHearts = new FloatPossible(0.01f, 0.06f);
		getDefaultWeapon().blind = new FloatPossible(0.01f, 0.18f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.DIAMOND_SWORD;
		sword.name = "&dAncient Sword";
		sword.dmg = new IntPossible(90, 160);
		sword.range = new IntPossible(0, 46);
		addWeapon(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.DIAMOND_AXE;
		axe.name = "&dAncient Axe";
		axe.dmg = new IntPossible(94, 172);
		axe.range = new IntPossible(0, 40);
		addWeapon(axe);
	}
}
