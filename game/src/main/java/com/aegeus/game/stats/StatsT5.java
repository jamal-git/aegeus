package com.aegeus.game.stats;

import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.FloatPoss;
import com.aegeus.game.util.IntPoss;
import com.aegeus.game.util.ListPoss;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT5 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(5);
		setChance(0.009f);
		setHpMultiplier(1.6f);
		setGenName(true);

		getDefArmor().rune = new Chance<>(new ListPoss<>(Rune.RuneType.ARCANE_MIGHT), 0.03f);

		getDefArmor().hpRegen = new IntPoss(80, 140);
		getDefArmor().energyRegen = new FloatPoss(0.05f, 0.08f);
		getDefArmor().physRes = new Chance<>(new FloatPoss(0.03f, 0.1f), 0.2f);
		getDefArmor().magRes = new Chance<>(new FloatPoss(0.03f, 0.1f), 0.2f);
		getDefArmor().block = new Chance<>(new FloatPoss(0.02f, 0.12f), 0.1f);
		getDefArmor().dodge = new Chance<>(new FloatPoss(0.02f, 0.12f), 0.1f);
		getDefArmor().reflect = new Chance<>(new FloatPoss(0.02f, 0.12f), 0.1f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.GOLD_HELMET;
		helmet.hp = new IntPoss(868, 2760);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.GOLD_CHESTPLATE;
		chestplate.hp = new IntPoss(1302, 4140);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.GOLD_LEGGINGS;
		leggings.hp = new IntPoss(1302, 4140);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.GOLD_BOOTS;
		boots.hp = new IntPoss(868, 2760);
		getAllBoots().add(boots);

		getDefWeapon().rune = new Chance<>(new ListPoss<>(Rune.RuneType.BLOOD_HUNT), 0.03f);

		getDefWeapon().pen = new Chance<>(new FloatPoss(0.02f, 0.18f), 0.05f);
		getDefWeapon().fireDmg = new Chance<>(new IntPoss(6, 25), 0.05f);
		getDefWeapon().iceDmg = new Chance<>(new IntPoss(6, 25), 0.05f);
		getDefWeapon().poisonDmg = new Chance<>(new IntPoss(6, 25), 0.05f);
		getDefWeapon().pureDmg = new Chance<>(new IntPoss(6, 25), 0.05f);
		getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
		getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.07f), 0.05f);
		getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.GOLD_SWORD;
		sword.dmg = new IntPoss(120, 280);
		sword.range = new IntPoss(0, 80);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.GOLD_AXE;
		axe.dmg = new IntPoss(126, 290);
		axe.range = new IntPoss(0, 75);
		getWeapons().add(axe);

		WeaponPossible bow = new WeaponPossible();
		bow.material = Material.BOW;
		bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
		bow.dmg = new IntPoss(126, 290);
		bow.range = new IntPoss(0, 75);
		getWeapons().add(bow);
	}
}
