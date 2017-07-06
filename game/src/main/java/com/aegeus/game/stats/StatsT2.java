package com.aegeus.game.stats;

import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT2 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(2);
		setChance(0.05f);

		getDefaultArmor().hpRegen = new IntPossible(10, 20);
		getDefaultArmor().energyRegen = new FloatPossible(0.01f, 0.03f);
		getDefaultArmor().physRes = new FloatPossible(0.01f, 0.04f);
		getDefaultArmor().magRes = new FloatPossible(0.01f, 0.04f);
		getDefaultArmor().block = new FloatPossible(0.01f, 0.03f);
		getDefaultArmor().dodge = new FloatPossible(0.01f, 0.03f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.CHAINMAIL_HELMET;
		helmet.name = "&aChainmail Helmet";
		helmet.hp = new IntPossible(32, 180);
		addHelmet(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.CHAINMAIL_CHESTPLATE;
		chestplate.name = "&aChainmail Chestplate";
		chestplate.hp = new IntPossible(59, 330);
		addChestplate(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.CHAINMAIL_LEGGINGS;
		leggings.name = "&aChainmail Leggings";
		leggings.hp = new IntPossible(58, 320);
		addLeggings(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.CHAINMAIL_BOOTS;
		boots.name = "&aChainmail Boots";
		boots.hp = new IntPossible(31, 170);
		addBoots(boots);

		getDefaultWeapon().pen = new FloatPossible(0.01f, 0.07f);
		getDefaultWeapon().fireDmg = new IntPossible(1, 6);
		getDefaultWeapon().iceDmg = new IntPossible(1, 6);
		getDefaultWeapon().poisonDmg = new IntPossible(1, 6);
		getDefaultWeapon().pureDmg = new IntPossible(1, 6);
		getDefaultWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefaultWeapon().trueHearts = new FloatPossible(0.01f, 0.04f);
		getDefaultWeapon().blind = new FloatPossible(0.01f, 0.14f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.STONE_SWORD;
		sword.name = "&aStone Sword";
		sword.dmg = new IntPossible(10, 30);
		sword.range = new IntPossible(0, 10);
		addWeapon(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.STONE_AXE;
		axe.name = "&aStone Axe";
		axe.dmg = new IntPossible(11, 35);
		axe.range = new IntPossible(0, 12);
		addWeapon(axe);
	}

	@Override
	public boolean hasChestplate() {
		return random.nextBoolean();
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}
}
