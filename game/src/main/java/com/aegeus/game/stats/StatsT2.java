package com.aegeus.game.stats;

import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.util.*;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT2 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(2);
		setChance(0.05f);
		setHpMultiplier(1.15f);
		setGenName(true);

		getDefArmor().hpRegen = new IntPoss(10, 20);
		getDefArmor().energyRegen = new FloatPoss(0.01f, 0.03f);
		getDefArmor().physRes = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.08f);
		getDefArmor().magRes = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.08f);
		getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
		getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
		getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.CHAINMAIL_HELMET;
		helmet.hp = new IntPoss(46, 170);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.CHAINMAIL_CHESTPLATE;
		chestplate.hp = new IntPoss(69, 255);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.CHAINMAIL_LEGGINGS;
		leggings.hp = new IntPoss(69, 255);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.CHAINMAIL_BOOTS;
		boots.hp = new IntPoss(46, 170);
		getAllBoots().add(boots);

		getDefWeapon().pen = new Chance<>(new FloatPoss(0.01f, 0.07f), 0.05f);
		getDefWeapon().fireDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
		getDefWeapon().iceDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
		getDefWeapon().poisonDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
		getDefWeapon().pureDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
		getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
		getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.05f);
		getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.14f), 0.05f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.STONE_SWORD;
		sword.dmg = new IntPoss(10, 30);
		sword.range = new IntPoss(0, 10);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.STONE_AXE;
		axe.dmg = new IntPoss(11, 35);
		axe.range = new IntPoss(0, 8);
		getWeapons().add(axe);

		WeaponPossible bow = new WeaponPossible();
		bow.material = Material.BOW;
		bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
		bow.dmg = new IntPoss(11, 35);
		bow.range = new IntPoss(0, 6);
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
}
