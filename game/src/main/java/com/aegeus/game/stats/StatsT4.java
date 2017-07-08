package com.aegeus.game.stats;

import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.util.*;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT4 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(4);
		setChance(0.012f);
		setHpMultiplier(1.45f);
		setGenName(true);

		getDefArmor().rune = new Chance<>(new ListPoss<>(Rune.RuneType.ARCANE_MIGHT), 0.02f);

		getDefArmor().hpRegen = new IntPoss(50, 90);
		getDefArmor().energyRegen = new FloatPoss(0.04f, 0.07f);
		getDefArmor().physRes = new Chance<>(new FloatPoss(0.02f, 0.07f), 0.16f);
		getDefArmor().magRes = new Chance<>(new FloatPoss(0.02f, 0.07f), 0.16f);
		getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.1f), 0.08f);
		getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.1f), 0.08f);
		getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.1f), 0.08f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.DIAMOND_HELMET;
		helmet.hp = new IntPoss(524, 1920);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.DIAMOND_CHESTPLATE;
		chestplate.hp = new IntPoss(786, 2880);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.DIAMOND_LEGGINGS;
		leggings.hp = new IntPoss(786, 2880);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.DIAMOND_BOOTS;
		boots.hp = new IntPoss(524, 1920);
		getAllBoots().add(boots);

		getDefWeapon().rune = new Chance<>(new ListPoss<>(Rune.RuneType.BLOOD_HUNT), 0.02f);

		getDefWeapon().pen = new Chance<>(new FloatPoss(0.01f, 0.12f), 0.05f);
		getDefWeapon().fireDmg = new Chance<>(new IntPoss(3, 18), 0.05f);
		getDefWeapon().iceDmg = new Chance<>(new IntPoss(3, 18), 0.05f);
		getDefWeapon().poisonDmg = new Chance<>(new IntPoss(3, 18), 0.05f);
		getDefWeapon().pureDmg = new Chance<>(new IntPoss(3, 18), 0.05f);
		getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
		getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.06f), 0.05f);
		getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.18f), 0.05f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.DIAMOND_SWORD;
		sword.dmg = new IntPoss(90, 160);
		sword.range = new IntPoss(0, 46);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.DIAMOND_AXE;
		axe.dmg = new IntPoss(94, 172);
		axe.range = new IntPoss(0, 42);
		getWeapons().add(axe);

		WeaponPossible bow = new WeaponPossible();
		bow.material = Material.BOW;
		bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
		bow.dmg = new IntPoss(94, 172);
		bow.range = new IntPoss(0, 38);
		getWeapons().add(bow);
	}
}
