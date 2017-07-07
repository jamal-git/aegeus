package com.aegeus.game.stats;

import com.aegeus.game.util.Condition;
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

		getDefArmor().hpRegen = new IntPossible(15, 40);
		getDefArmor().energyRegen = new FloatPossible(0.02f, 0.05f);
		getDefArmor().physRes = new FloatPossible(0.01f, 0.05f);
		getDefArmor().magRes = new FloatPossible(0.01f, 0.05f);
		getDefArmor().block = new FloatPossible(0.01f, 0.06f);
		getDefArmor().dodge = new FloatPossible(0.01f, 0.06f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.IRON_HELMET;
		helmet.hp = new IntPossible(150, 564);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.IRON_CHESTPLATE;
		chestplate.hp = new IntPossible(225, 846);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.IRON_LEGGINGS;
		leggings.hp = new IntPossible(225, 846);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.IRON_BOOTS;
		boots.hp = new IntPossible(150, 564);
		getAllBoots().add(boots);

		getDefWeapon().pen = new FloatPossible(0.01f, 0.1f);
		getDefWeapon().fireDmg = new IntPossible(2, 10);
		getDefWeapon().iceDmg = new IntPossible(2, 10);
		getDefWeapon().poisonDmg = new IntPossible(2, 10);
		getDefWeapon().pureDmg = new IntPossible(2, 10);
		getDefWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefWeapon().trueHearts = new FloatPossible(0.01f, 0.05f);
		getDefWeapon().blind = new FloatPossible(0.01f, 0.16f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.IRON_SWORD;
		sword.dmg = new IntPossible(21, 68);
		sword.range = new IntPossible(0, 40);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.IRON_AXE;
		axe.dmg = new IntPossible(23, 70);
		axe.range = new IntPossible(0, 36);
		getWeapons().add(axe);
	}

	@Override
	public boolean hasLeggings() {
		return random.nextBoolean();
	}
}
