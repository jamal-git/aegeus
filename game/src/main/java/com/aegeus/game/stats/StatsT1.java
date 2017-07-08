package com.aegeus.game.stats;

import com.aegeus.game.util.Chance;
import com.aegeus.game.util.FloatPoss;
import com.aegeus.game.util.IntPoss;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT1 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(1);
		setChance(0.1f);
		setGenName(true);

		getDefArmor().hpRegen = new IntPoss(5, 10);
		getDefArmor().energyRegen = new FloatPoss(0.01f, 0.02f);
		getDefArmor().physRes = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
		getDefArmor().magRes = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
		getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.02f), 0.02f);
		getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.02f), 0.02f);
		getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.02f), 0.02f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.LEATHER_HELMET;
		helmet.hp = new IntPoss(16, 64);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.LEATHER_CHESTPLATE;
		chestplate.hp = new IntPoss(24, 96);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.LEATHER_LEGGINGS;
		leggings.hp = new IntPoss(24, 96);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.LEATHER_BOOTS;
		boots.hp = new IntPoss(16, 64);
		getAllBoots().add(boots);

		getDefWeapon().pen = new Chance<>(new FloatPoss(0.01f, 0.05f), 0.05f);
		getDefWeapon().fireDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
		getDefWeapon().iceDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
		getDefWeapon().poisonDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
		getDefWeapon().pureDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
		getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
		getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.05f);
		getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.12f), 0.05f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.WOOD_SWORD;
		sword.dmg = new IntPoss(1, 12);
		sword.range = new IntPoss(0, 5);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.WOOD_AXE;
		axe.dmg = new IntPoss(1, 13);
		axe.range = new IntPoss(0, 4);
		getWeapons().add(axe);

		WeaponPossible bow = new WeaponPossible();
		bow.material = Material.BOW;
		bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
		bow.dmg = new IntPoss(1, 13);
		bow.range = new IntPoss(0, 3);
		getWeapons().add(bow);
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
