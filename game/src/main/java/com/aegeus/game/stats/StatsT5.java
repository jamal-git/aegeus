package com.aegeus.game.stats;

import com.aegeus.game.util.Condition;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT5 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(5);
		setChance(0.009f);
        setHpMultiplier(1.6f);
        setGenName(true);

        getSpawnConds().add(new Condition<LivingEntity>() {
            @Override
            public boolean isComplete(LivingEntity entity) {
                return entity.getType().equals(EntityType.ZOMBIE);
            }

            @Override
            public void onComplete(LivingEntity entity) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
            }
        });

        getSpawnConds().add(new Condition<LivingEntity>() {
            @Override
            public boolean isComplete(LivingEntity entity) {
                return !entity.getType().equals(EntityType.ZOMBIE);
            }

            @Override
            public void onComplete(LivingEntity entity) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
            }
        });

		getDefArmor().hpRegen = new IntPossible(80, 140);
        getDefArmor().energyRegen = new FloatPossible(0.04f, 0.07f);
        getDefArmor().physRes = new FloatPossible(0.03f, 0.1f);
		getDefArmor().magRes = new FloatPossible(0.03f, 0.1f);
		getDefArmor().block = new FloatPossible(0.02f, 0.12f);
		getDefArmor().dodge = new FloatPossible(0.02f, 0.12f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.GOLD_HELMET;
		helmet.hp = new IntPossible(868, 2760);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.GOLD_CHESTPLATE;
		chestplate.hp = new IntPossible(1302, 4140);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.GOLD_LEGGINGS;
		leggings.hp = new IntPossible(1302, 4140);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.GOLD_BOOTS;
		boots.hp = new IntPossible(868, 2760);
		getAllBoots().add(boots);

		getDefWeapon().pen = new FloatPossible(0.02f, 0.18f);
		getDefWeapon().fireDmg = new IntPossible(6, 25);
		getDefWeapon().iceDmg = new IntPossible(6, 25);
		getDefWeapon().poisonDmg = new IntPossible(6, 25);
		getDefWeapon().pureDmg = new IntPossible(6, 25);
		getDefWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefWeapon().trueHearts = new FloatPossible(0.01f, 0.07f);
		getDefWeapon().blind = new FloatPossible(0.01f, 0.2f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.GOLD_SWORD;
		sword.dmg = new IntPossible(120, 300);
		sword.range = new IntPossible(0, 80);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.GOLD_AXE;
		axe.dmg = new IntPossible(126, 320);
		axe.range = new IntPossible(0, 75);
		getWeapons().add(axe);
	}
}
