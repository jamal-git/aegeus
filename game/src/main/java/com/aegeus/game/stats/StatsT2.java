package com.aegeus.game.stats;

import com.aegeus.game.util.Condition;
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

		getDefArmor().hpRegen = new IntPossible(10, 20);
		getDefArmor().energyRegen = new FloatPossible(0.01f, 0.03f);
		getDefArmor().physRes = new FloatPossible(0.01f, 0.04f);
		getDefArmor().magRes = new FloatPossible(0.01f, 0.04f);
		getDefArmor().block = new FloatPossible(0.01f, 0.03f);
		getDefArmor().dodge = new FloatPossible(0.01f, 0.03f);

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.CHAINMAIL_HELMET;
		helmet.name = "&aChainmail Helmet";
		helmet.hp = new IntPossible(46, 170);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.CHAINMAIL_CHESTPLATE;
		chestplate.name = "&aChainmail Chestplate";
		chestplate.hp = new IntPossible(69, 255);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.CHAINMAIL_LEGGINGS;
		leggings.name = "&aChainmail Leggings";
		leggings.hp = new IntPossible(69, 255);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.CHAINMAIL_BOOTS;
		boots.name = "&aChainmail Boots";
		boots.hp = new IntPossible(46, 170);
		getAllBoots().add(boots);

		getDefWeapon().pen = new FloatPossible(0.01f, 0.07f);
		getDefWeapon().fireDmg = new IntPossible(1, 6);
		getDefWeapon().iceDmg = new IntPossible(1, 6);
		getDefWeapon().poisonDmg = new IntPossible(1, 6);
		getDefWeapon().pureDmg = new IntPossible(1, 6);
		getDefWeapon().lifeSteal = new FloatPossible(0.01f, 0.2f);
		getDefWeapon().trueHearts = new FloatPossible(0.01f, 0.04f);
		getDefWeapon().blind = new FloatPossible(0.01f, 0.14f);

		WeaponPossible sword = new WeaponPossible();
		sword.material = Material.STONE_SWORD;
		sword.name = "&aStone Sword";
		sword.dmg = new IntPossible(10, 30);
		sword.range = new IntPossible(0, 10);
		getWeapons().add(sword);

		WeaponPossible axe = new WeaponPossible();
		axe.material = Material.STONE_AXE;
		axe.name = "&aStone Axe";
		axe.dmg = new IntPossible(11, 35);
		axe.range = new IntPossible(0, 12);
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
}
