package com.aegeus.game.stats;

import com.aegeus.game.Aegeus;
import com.aegeus.game.ability.AbilityResolve;
import com.aegeus.game.ability.AbilityTackle;
import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.stats.impl.Mob;
import com.aegeus.game.util.Action;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.FloatPoss;
import com.aegeus.game.util.IntPoss;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class MobViktor extends Mob {
	public MobViktor() {
		setTier(5);
		setChance(0.18f);
		setDmgMultiplier(2.5f);
		setHpMultiplier(4);
		setGlowing(true);
		setGenName(false);

		getTypes().add(EntityType.SKELETON);
		getNames().add("&e&lViktor the Conqueror");

		getSpawnActs().add(new Action<LivingEntity>() {
			@Override
			public void activate(LivingEntity entity) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 3));
			}
		});

		getHitActs().add(new Action<CombatInfo>() {
			@Override
			public void activate(CombatInfo cInfo) {
				AgMonster info = Aegeus.getInstance().getMonster(cInfo.getVictim());
				info.setDmgMultiplier(5);
				info.setName("&c&lViktor the Chaotic");
			}

			@Override
			public boolean canActivate(CombatInfo info) {
				LivingEntity entity = info.getVictim();
				return entity.getHealth() < entity.getMaxHealth() * 0.35;
			}
		});

		setAbils(Arrays.asList(new AbilityTackle(10), new AbilityResolve(6)));
		setAbilCount(new IntPoss(2));

		getDefArmor().rarity = Rarity.UNIQUE;
		getDefArmor().hpRegen = new IntPoss(80, 120);
		getDefArmor().physRes = new Chance<>(new FloatPoss(0.07f, 0.12f), 0.78f);
		getDefArmor().block = new Chance<>(new FloatPoss(0.06f, 0.11f), 0.67f);

		getHelmets().clear();
		ArmorPoss helmet = new ArmorPoss();
		helmet.material = Material.JACK_O_LANTERN;
		helmet.name = "&eViktor's Bloody Mask";
		helmet.hp = new IntPoss(2115, 2760);
		getHelmets().add(helmet);

		ArmorPoss chestplate = new ArmorPoss();
		chestplate.material = Material.GOLD_CHESTPLATE;
		chestplate.name = "&eViktor's Chestplate";
		chestplate.hp = new IntPoss(3490, 4140);
		getChestplates().add(chestplate);

		ArmorPoss leggings = new ArmorPoss();
		leggings.material = Material.GOLD_LEGGINGS;
		leggings.name = "&eViktor's Leggings";
		leggings.hp = new IntPoss(3490, 4140);
		getAllLeggings().add(leggings);

		ArmorPoss boots = new ArmorPoss();
		boots.material = Material.GOLD_BOOTS;
		boots.name = "&eViktor's Boots";
		boots.hp = new IntPoss(2115, 2760);
		getAllBoots().add(boots);

		WeaponPoss axe = new WeaponPoss();
		axe.material = Material.GOLD_AXE;
		axe.name = "&eViktor's Axe";
		axe.rarity = Rarity.UNIQUE;
		axe.dmg = new IntPoss(280, 340);
		axe.range = new IntPoss(40, 65);
		axe.pen = new Chance<>(new FloatPoss(0.12f, 0.24f), 0.89f);
		axe.pureDmg = new Chance<>(new IntPoss(6, 25), 0.75f);
		axe.trueHearts = new Chance<>(new FloatPoss(0.01f, 0.07f), 0.47f);
		getWeapons().add(axe);
	}

	@Override
	public String getId() {
		return "viktor";
	}
}
