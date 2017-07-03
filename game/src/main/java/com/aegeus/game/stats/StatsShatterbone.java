package com.aegeus.game.stats;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Condition;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class StatsShatterbone extends Stats {
	@Override
	public void prepare() {
		setTier(3);
		setChance(0.86f);
		setForcedHp(19000);
		setDmgMultiplier(4f);

		addName("&b&lShatterbone");

		addType(EntityType.SKELETON);

		getDefaultArmor().rarity = Rarity.DUNGEON;
		getDefaultArmor().statChance = 1f;
		getDefaultArmor().attChance = 1f;
		getDefaultArmor().vitality = new IntPossible(40, 50);

		addHitCond(new Condition<LivingEntity>() {
			@Override
			public void onComplete(LivingEntity entity) {
				entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITCH_HURT, 1, 0.7f);
			}

			@Override
			public boolean removeOnComplete() {
				return false;
			}
		});
		addHitCond(new Condition<LivingEntity>() {
			@Override
			public boolean isComplete(LivingEntity entity) {
				return entity.getHealth() <= 4000;
			}

			@Override
			public void onComplete(LivingEntity entity) {
				entity.setHealth(entity.getMaxHealth());
				entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 0);
			}

			@Override
			public List<Condition<LivingEntity>> addOnComplete() {
				List<Condition<LivingEntity>> conditions = new ArrayList<>();
				conditions.add(new Condition<LivingEntity>() {
					@Override
					public boolean isComplete(LivingEntity entity) {
						return entity.getHealth() <= 3000;
					}

					@Override
					public void onComplete(LivingEntity entity) {
						AgMonster info = Aegeus.getInstance().getMonster(entity);
						entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 5));
						info.setName(Util.colorCodes("&6&lEnraged Shatterbone"));
						info.setDmgMultiplier(8f);
						info.setDefense(0.35f);
						info.setBlock(0.3f);
						entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITCH_HURT, 1, 0);
					}
				});
				return conditions;
			}
		});

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.IRON_HELMET;
		helmet.name = "&bShatterbone's Spiked Helmet";
		helmet.hpRegen = new IntPossible(20, 40);
		helmet.defense = new FloatPossible(0.02f);
		helmet.thorns = new FloatPossible(0.05f, 0.1f);
		helmet.hp = new IntPossible(360, 490);
		addHelmet(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.IRON_CHESTPLATE;
		chestplate.name = "&bShatterbone's Colossal Platemail";
		chestplate.hpRegen = new IntPossible(40, 90);
		chestplate.defense = new FloatPossible(0.05f);
		chestplate.hp = new IntPossible(790, 910);
		addChestplate(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.IRON_LEGGINGS;
		leggings.name = "&bShatterbone's Sturdy Leggings";
		leggings.hpRegen = new IntPossible(20, 40);
		leggings.defense = new FloatPossible(0.05f);
		leggings.hp = new IntPossible(710, 880);
		addLeggings(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.IRON_BOOTS;
		boots.name = "&bShatterbone's Frosty Stompers";
		boots.hpRegen = new IntPossible(20, 40);
		boots.defense = new FloatPossible(0.02f);
		boots.hp = new IntPossible(310, 440);
		addBoots(boots);

		WeaponPossible weapon = new WeaponPossible();
		weapon.material = Material.IRON_SPADE;
		weapon.name = "&bShatterbone's Trusty Spade";
		weapon.rarity = Rarity.DUNGEON;
		weapon.attChance = 1f;
		weapon.dmg = new IntPossible(51, 60);
		weapon.range = new IntPossible(25, 30);
		weapon.iceDmg = new IntPossible(7, 10);
		addWeapon(weapon);
	}
}
