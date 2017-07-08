package com.aegeus.game.stats;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Shatterbone;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.Condition;
import com.aegeus.game.util.FloatPoss;
import com.aegeus.game.util.IntPoss;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsShatterbone extends Stats {
	public StatsShatterbone() {
	}

	public StatsShatterbone(Stats parent) {
		super(parent);
	}

	@Override
	public void prepare() {
		setTier(3);
		setChance(0.86f);
		setForcedHp(20000);
		setDmgMultiplier(3f);

		getNames().add("&b&lShatterbone");

		getTypes().add(EntityType.SKELETON);

		getDefArmor().rarity = Rarity.DUNGEON;

		getSpawnConds().add(new Condition<LivingEntity>() {
			@Override
			public void onComplete(LivingEntity entity) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
			}
		});

		getHitConds().add(new Condition<EntityDamageEvent>() {
			@Override
			public void onComplete(EntityDamageEvent e) {
				e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_WITCH_HURT, 1, 0.7f);
			}

			@Override
			public boolean removeOnComplete() {
				return false;
			}
		});
		getHitConds().add(new Condition<EntityDamageEvent>() {
			@Override
			public boolean isComplete(EntityDamageEvent e) {
				return ((LivingEntity) e.getEntity()).getHealth() <= 12000;
			}

			@Override
			public void onComplete(EntityDamageEvent e) {
				LivingEntity entity = (LivingEntity) e.getEntity();
				entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999, 0));
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999, 2));
				entity.setInvulnerable(true);

				Shatterbone info = new Shatterbone(Aegeus.getInstance().getMonster(entity));
				for (int i = 0; i < 4; i++) {
					Stats stats = new StatsSkeleton(new StatsT3());
					stats.setChance(0);
					stats.setHpMultiplier(1.7f);
					stats.setDmgMultiplier(1.15f);
					stats.getDeathConds().add(new Condition<EntityDeathEvent>() {
						@Override
						public void onComplete(EntityDeathEvent e) {
							LivingEntity entity2 = e.getEntity();
							info.getMinions().remove(entity2);
						}
					});
					stats.getDeathConds().add(new Condition<EntityDeathEvent>() {
						@Override
						public boolean isComplete(EntityDeathEvent e) {
							return info.getMinions().isEmpty();
						}

						@Override
						public void onComplete(EntityDeathEvent e) {
							entity.setInvulnerable(false);
							entity.removePotionEffect(PotionEffectType.INVISIBILITY);
							entity.removePotionEffect(PotionEffectType.SLOW);
						}
					});
					info.getMinions().add(stats.spawn(entity.getLocation(), null));
				}
				Aegeus.getInstance().setEntity(entity, info);
			}
		});

		ArmorPossible helmet = new ArmorPossible();
		helmet.material = Material.IRON_HELMET;
		helmet.name = "&bShatterbone's Spiked Helmet";
		helmet.hpRegen = new IntPoss(20, 40);
		helmet.physRes = new Chance<>(new FloatPoss(0.02f), 1f);
		helmet.hp = new IntPoss(360, 490);
		getHelmets().add(helmet);

		ArmorPossible chestplate = new ArmorPossible();
		chestplate.material = Material.IRON_CHESTPLATE;
		chestplate.name = "&bShatterbone's Colossal Platemail";
		chestplate.hpRegen = new IntPoss(40, 90);
		chestplate.physRes = new Chance<>(new FloatPoss(0.05f), 1f);
		chestplate.hp = new IntPoss(790, 910);
		getChestplates().add(chestplate);

		ArmorPossible leggings = new ArmorPossible();
		leggings.material = Material.IRON_LEGGINGS;
		leggings.name = "&bShatterbone's Sturdy Leggings";
		leggings.hpRegen = new IntPoss(20, 40);
		leggings.physRes = new Chance<>(new FloatPoss(0.05f), 1f);
		leggings.hp = new IntPoss(710, 880);
		getAllLeggings().add(leggings);

		ArmorPossible boots = new ArmorPossible();
		boots.material = Material.IRON_BOOTS;
		boots.name = "&bShatterbone's Frosty Stompers";
		boots.hpRegen = new IntPoss(20, 40);
		boots.physRes = new Chance<>(new FloatPoss(0.02f), 1f);
		boots.hp = new IntPoss(310, 440);
		getAllBoots().add(boots);

		WeaponPossible weapon = new WeaponPossible();
		weapon.material = Material.IRON_SPADE;
		weapon.name = "&bShatterbone's Trusty Spade";
		weapon.rarity = Rarity.DUNGEON;
		weapon.dmg = new IntPoss(51, 60);
		weapon.range = new IntPoss(25, 30);
		weapon.iceDmg = new Chance<>(new IntPoss(7, 10), 1f);
		getWeapons().add(weapon);
	}
}
