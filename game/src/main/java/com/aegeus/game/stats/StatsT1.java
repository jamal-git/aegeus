package com.aegeus.game.stats;

import com.aegeus.game.util.Condition;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class StatsT1 extends Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@Override
	public void prepare() {
		setTier(1);
		setChance(0.1f);
        setGenName(true);

        getSpawnConds().add(new Condition<LivingEntity>() {
            @Override
            public boolean isComplete(LivingEntity entity) {
                return entity.getType().equals(EntityType.SKELETON);
            }

            @Override
            public void onComplete(LivingEntity entity) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999, 1));
            }
        });

		getDefArmor().hpRegen = new IntPossible(5, 10);
		getDefArmor().energyRegen = new FloatPossible(0.01f, 0.02f);
		getDefArmor().physRes = new FloatPossible(0.01f, 0.03f);
		getDefArmor().magRes = new FloatPossible(0.01f, 0.03f);
		getDefArmor().block = new FloatPossible(0.01f, 0.02f);
		getDefArmor().dodge = new FloatPossible(0.01f, 0.02f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.LEATHER_HELMET;
		helmet.hp = new IntPossible(16, 64);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.LEATHER_CHESTPLATE;
		chestplate.hp = new IntPossible(24, 96);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.LEATHER_LEGGINGS;
		leggings.hp = new IntPossible(24, 96);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.LEATHER_BOOTS;
		boots.hp = new IntPossible(16, 64);
		getAllBoots().add(boots);

		getDefWeapon().pen = new FloatPossible(0.01f, 0.05f);
		getDefWeapon().fireDmg = new IntPossible(1, 4);
		getDefWeapon().iceDmg = new IntPossible(1, 4);
		getDefWeapon().poisonDmg = new IntPossible(1, 4);
		getDefWeapon().pureDmg = new IntPossible(1, 4);
		getDefWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefWeapon().trueHearts = new FloatPossible(0.01f, 0.03f);
		getDefWeapon().blind = new FloatPossible(0.01f, 0.12f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.WOOD_SWORD;
		sword.dmg = new IntPossible(1, 12);
		sword.range = new IntPossible(0, 5);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.WOOD_AXE;
		axe.dmg = new IntPossible(1, 13);
		axe.range = new IntPossible(0, 4);
		getWeapons().add(axe);
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
