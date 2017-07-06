package com.aegeus.game.stats;

import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT4 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(4);
		setChance(0.012f);
		setGenNames(true);

		getDefArmor().hpRegen = new IntPossible(50, 90);
		getDefArmor().energyRegen = new FloatPossible(0.03f, 0.06f);
		getDefArmor().physRes = new FloatPossible(0.02f, 0.07f);
		getDefArmor().magRes = new FloatPossible(0.02f, 0.07f);
		getDefArmor().block = new FloatPossible(0.01f, 0.1f);
		getDefArmor().dodge = new FloatPossible(0.01f, 0.1f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.DIAMOND_HELMET;
		helmet.hp = new IntPossible(524, 1920);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.DIAMOND_CHESTPLATE;
		chestplate.hp = new IntPossible(786, 2880);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.DIAMOND_LEGGINGS;
		leggings.hp = new IntPossible(786, 2880);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.DIAMOND_BOOTS;
		boots.hp = new IntPossible(524, 1920);
		getAllBoots().add(boots);

		getDefWeapon().pen = new FloatPossible(0.01f, 0.12f);
		getDefWeapon().fireDmg = new IntPossible(3, 18);
		getDefWeapon().iceDmg = new IntPossible(3, 18);
		getDefWeapon().poisonDmg = new IntPossible(3, 18);
		getDefWeapon().pureDmg = new IntPossible(3, 18);
		getDefWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefWeapon().trueHearts = new FloatPossible(0.01f, 0.06f);
		getDefWeapon().blind = new FloatPossible(0.01f, 0.18f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.DIAMOND_SWORD;
		sword.dmg = new IntPossible(90, 160);
		sword.range = new IntPossible(0, 46);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.DIAMOND_AXE;
		axe.dmg = new IntPossible(94, 172);
		axe.range = new IntPossible(0, 40);
		getWeapons().add(axe);
	}
}
