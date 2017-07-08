package com.aegeus.game.stats;

import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.util.*;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT3 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(3);
		setChance(0.025f);
		setHpMultiplier(1.3f);
		setGenName(true);

		getSpawnConds().add(new Condition<LivingEntity>() {
			@Override
			public boolean isComplete(LivingEntity entity) {
				return entity.getType().equals(EntityType.ZOMBIE);
			}

			@Override
			public void onComplete(LivingEntity entity) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
			}
		});

		getDefArmor().rune = new Chance<>(new ListPoss<>(Rune.RuneType.ARCANE_MIGHT), 0.01f);

		getDefArmor().hpRegen = new IntPoss(15, 40);
		getDefArmor().energyRegen = new FloatPoss(0.02f, 0.05f);
		getDefArmor().physRes = new Chance<>(new FloatPoss(0.01f, 0.05f), 0.12f);
		getDefArmor().magRes = new Chance<>(new FloatPoss(0.01f, 0.05f), 0.12f);
		getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.06f), 0.06f);
		getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.06f), 0.06f);
		getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.06f), 0.06f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.IRON_HELMET;
		helmet.hp = new IntPoss(150, 564);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.IRON_CHESTPLATE;
		chestplate.hp = new IntPoss(225, 846);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.IRON_LEGGINGS;
		leggings.hp = new IntPoss(225, 846);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.IRON_BOOTS;
		boots.hp = new IntPoss(150, 564);
		getAllBoots().add(boots);

		getDefWeapon().rune = new Chance<>(new ListPoss<>(Rune.RuneType.BLOOD_HUNT), 0.01f);

		getDefWeapon().pen = new Chance<>(new FloatPoss(0.01f, 0.1f), 0.05f);
		getDefWeapon().fireDmg = new Chance<>(new IntPoss(2, 10), 0.05f);
		getDefWeapon().iceDmg = new Chance<>(new IntPoss(2, 10), 0.05f);
		getDefWeapon().poisonDmg = new Chance<>(new IntPoss(2, 10), 0.05f);
		getDefWeapon().pureDmg = new Chance<>(new IntPoss(2, 10), 0.05f);
		getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
		getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.05f), 0.05f);
		getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.16f), 0.05f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.IRON_SWORD;
		sword.dmg = new IntPoss(21, 68);
		sword.range = new IntPoss(0, 40);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.IRON_AXE;
		axe.dmg = new IntPoss(23, 70);
		axe.range = new IntPoss(0, 37);
		getWeapons().add(axe);

		WeaponPossible bow = new WeaponPossible();
		bow.material = Material.BOW;
		bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
		bow.dmg = new IntPoss(23, 70);
		bow.range = new IntPoss(0, 34);
		getWeapons().add(bow);
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}
}
