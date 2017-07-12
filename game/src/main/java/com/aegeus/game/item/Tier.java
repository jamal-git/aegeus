package com.aegeus.game.item;

import com.aegeus.game.ability.AbilityConcuss;
import com.aegeus.game.ability.AbilityDetonate;
import com.aegeus.game.ability.AbilityResolve;
import com.aegeus.game.ability.AbilityTackle;
import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.stats.Stats;
import com.aegeus.game.util.*;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public enum Tier {
	NONE(0, 0, "&f", "Custom", "Custom", "Custom Sword", "Custom Axe",
			"Custom Bow", "Custom Staff", "Custom Polearm"),
	TIER_1(675, 750, "&f", "Leather", "Wood", "Shortsword", "Hatchet",
			"Basic Bow", "Basic Staff", "Basic Polearm") {
		@Override
		public Stats getStats() {
			return new Stats() {
				@Override
				public void prepare() {
					setTier(1);
					setChance(0.4f);
					setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
							new AbilityResolve(), new AbilityTackle()));
					setAbilCount(new IntPoss(1));
					setGenName(true);

					getSpawnActions().add(new Action<LivingEntity>() {
						@Override
						public void activate(LivingEntity entity) {
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999, 1));
						}
					});

					getDefArmor().hpRegen = new IntPoss(5, 10);
					getDefArmor().physRes = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
					getDefArmor().magRes = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
					getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.02f), 0.02f);
					getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.02f), 0.02f);
					getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.02f), 0.02f);

					ArmorPossible helmet = new ArmorPossible();
					helmet.material = Material.LEATHER_HELMET;
					helmet.hp = new IntPoss(16, 64);
					getHelmets().add(helmet);

					ArmorPossible chestplate = new ArmorPossible();
					chestplate.material = Material.LEATHER_CHESTPLATE;
					chestplate.hp = new IntPoss(24, 96);
					getChestplates().add(chestplate);

					ArmorPossible leggings = new ArmorPossible();
					leggings.material = Material.LEATHER_LEGGINGS;
					leggings.hp = new IntPoss(24, 96);
					getAllLeggings().add(leggings);

					ArmorPossible boots = new ArmorPossible();
					boots.material = Material.LEATHER_BOOTS;
					boots.hp = new IntPoss(16, 64);
					getAllBoots().add(boots);

					getDefWeapon().pen = new Chance<>(new FloatPoss(0.01f, 0.05f), 0.05f);
					getDefWeapon().fireDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
					getDefWeapon().iceDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
					getDefWeapon().poisonDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
					getDefWeapon().pureDmg = new Chance<>(new IntPoss(1, 4), 0.05f);
					getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
					getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.05f);
					getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.12f), 0.05f);

					WeaponPossible sword = new WeaponPossible();
					sword.material = Material.WOOD_SWORD;
					sword.dmg = new IntPoss(1, 12);
					sword.range = new IntPoss(0, 5);
					getWeapons().add(sword);

					WeaponPossible axe = new WeaponPossible();
					axe.material = Material.WOOD_AXE;
					axe.dmg = new IntPoss(1, 13);
					axe.range = new IntPoss(0, 4);
					getWeapons().add(axe);

					WeaponPossible bow = new WeaponPossible();
					bow.material = Material.BOW;
					bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
					bow.dmg = new IntPoss(1, 13);
					bow.range = new IntPoss(0, 3);
					getWeapons().add(bow);
				}
			};
		}
	},
	TIER_2(1215, 1350, "&a", "Chainmail", "Stone", "Longsword", "Great Axe",
			"Advanced Bow", "Advanced Staff", "Advanced Polearm") {
		@Override
		public Stats getStats() {
			return new Stats() {
				@Override
				public void prepare() {
					setTier(2);
					setChance(0.2f);
					setHpMultiplier(1.1f);
					setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
							new AbilityResolve(), new AbilityTackle()));
					setAbilCount(new IntPoss(1));
					setGenName(true);

					getSpawnActions().add(new Action<LivingEntity>() {
						@Override
						public void activate(LivingEntity entity) {
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999, 1));
						}
					});

					getDefArmor().hpRegen = new IntPoss(10, 20);
					getDefArmor().physRes = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.08f);
					getDefArmor().magRes = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.08f);
					getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
					getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
					getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);

					ArmorPossible helmet = new ArmorPossible();
					helmet.material = Material.CHAINMAIL_HELMET;
					helmet.hp = new IntPoss(46, 170);
					getHelmets().add(helmet);

					ArmorPossible chestplate = new ArmorPossible();
					chestplate.material = Material.CHAINMAIL_CHESTPLATE;
					chestplate.hp = new IntPoss(69, 255);
					getChestplates().add(chestplate);

					ArmorPossible leggings = new ArmorPossible();
					leggings.material = Material.CHAINMAIL_LEGGINGS;
					leggings.hp = new IntPoss(69, 255);
					getAllLeggings().add(leggings);

					ArmorPossible boots = new ArmorPossible();
					boots.material = Material.CHAINMAIL_BOOTS;
					boots.hp = new IntPoss(46, 170);
					getAllBoots().add(boots);

					getDefWeapon().pen = new Chance<>(new FloatPoss(0.01f, 0.07f), 0.05f);
					getDefWeapon().fireDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
					getDefWeapon().iceDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
					getDefWeapon().poisonDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
					getDefWeapon().pureDmg = new Chance<>(new IntPoss(1, 6), 0.05f);
					getDefWeapon().lifeSteal = new Chance<>(new FloatPoss(0.01f, 0.2f), 0.05f);
					getDefWeapon().trueHearts = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.05f);
					getDefWeapon().blind = new Chance<>(new FloatPoss(0.01f, 0.14f), 0.05f);

					WeaponPossible sword = new WeaponPossible();
					sword.material = Material.STONE_SWORD;
					sword.dmg = new IntPoss(10, 30);
					sword.range = new IntPoss(0, 10);
					getWeapons().add(sword);

					WeaponPossible axe = new WeaponPossible();
					axe.material = Material.STONE_AXE;
					axe.dmg = new IntPoss(11, 35);
					axe.range = new IntPoss(0, 8);
					getWeapons().add(axe);

					WeaponPossible bow = new WeaponPossible();
					bow.material = Material.BOW;
					bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
					bow.dmg = new IntPoss(11, 35);
					bow.range = new IntPoss(0, 6);
					getWeapons().add(bow);
				}
			};
		}
	},
	TIER_3(1830, 2030, "&b", "Magic", "Iron", "Magic Sword", "Magic Axe",
			"Magic Bow", "Magic Staff", "Magic Polearm") {
		@Override
		public Stats getStats() {
			return new Stats() {
				@Override
				public void prepare() {
					setTier(3);
					setChance(0.1f);
					setHpMultiplier(1.2f);
					setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
							new AbilityResolve(), new AbilityTackle()));
					setAbilCount(new IntPoss(1, 2));
					setGenName(true);

					getSpawnActions().add(new Action<LivingEntity>() {
						@Override
						public void activate(LivingEntity entity) {
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
						}
					});

					getDefArmor().rune = new Chance<>(new ListPoss<>(Rune.RuneType.ARCANE_MIGHT), 0.01f);

					getDefArmor().hpRegen = new IntPoss(15, 40);
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
			};
		}
	},
	TIER_4(2745, 3050, "&d", "Ancient", "Diamond", "Ancient Sword", "Ancient Axe",
			"Ancient Bow", "Ancient Staff", "Ancient Polearm") {
		@Override
		public Stats getStats() {
			return new Stats() {
				@Override
				public void prepare() {
					setTier(4);
					setChance(0.048f);
					setHpMultiplier(1.3f);
					setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
							new AbilityResolve(), new AbilityTackle()));
					setAbilCount(new IntPoss(2, 3));
					setGenName(true);

					getSpawnActions().add(new Action<LivingEntity>() {
						@Override
						public void activate(LivingEntity entity) {
							if (entity.getType().equals(EntityType.SKELETON))
								entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
							else
								entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
						}
					});

					getDefArmor().rune = new Chance<>(new ListPoss<>(Rune.RuneType.ARCANE_MIGHT), 0.02f);

					getDefArmor().hpRegen = new IntPoss(50, 90);
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
			};
		}
	},
	TIER_5(4120, 4575, "&e", "Legendary", "Gold", "Legendary Sword", "Legendary Axe",
			"Legendary Bow", "Legendary Staff", "Legendary Polearm") {
		@Override
		public Stats getStats() {
			return new Stats() {
				@Override
				public void prepare() {
					setTier(5);
					setChance(0.036f);
					setHpMultiplier(1.5f);
					setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
							new AbilityResolve(), new AbilityTackle()));
					setAbilCount(new IntPoss(3));
					setGenName(true);

					getSpawnActions().add(new Action<LivingEntity>() {
						@Override
						public void activate(LivingEntity entity) {
							if (entity.getType().equals(EntityType.SKELETON))
								entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
							else
								entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
						}
					});

					getDefArmor().rune = new Chance<>(new ListPoss<>(Rune.RuneType.ARCANE_MIGHT), 0.03f);
					getDefArmor().hpRegen = new IntPoss(80, 140);
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
					sword.dmg = new IntPoss(120, 330);
					sword.range = new IntPoss(0, 80);
					getWeapons().add(sword);

					WeaponPossible axe = new WeaponPossible();
					axe.material = Material.GOLD_AXE;
					axe.dmg = new IntPoss(130, 340);
					axe.range = new IntPoss(0, 75);
					getWeapons().add(axe);

					WeaponPossible bow = new WeaponPossible();
					bow.material = Material.BOW;
					bow.allowedTypes = new EntityType[]{EntityType.SKELETON};
					bow.dmg = new IntPoss(130, 340);
					bow.range = new IntPoss(0, 70);
					getWeapons().add(bow);
				}
			};
		}
	};

	private static final Tier[] values = values();

	private final int wepDura;
	private final int armorDura;
	private final String color;
	private final String armor;
	private final String weapon;
	private final String sword;
	private final String axe;
	private final String bow;
	private final String staff;
	private final String polearm;

	Tier(int wepDura, int armorDura, String color, String armor, String weapon,
		 String sword, String axe, String bow, String staff, String polearm) {
		this.wepDura = wepDura;
		this.armorDura = armorDura;
		this.color = color;
		this.armor = armor;
		this.weapon = weapon;
		this.sword = sword;
		this.axe = axe;
		this.bow = bow;
		this.staff = staff;
		this.polearm = polearm;
	}

	public static Tier fromTier(int tier) {
		if (tier > 5 || tier < 0) return NONE;
		return values[tier];
	}

	public int getArmorDura() {
		return armorDura;
	}

	public int getWepDura() {
		return wepDura;
	}

	public String getColor() {
		return color;
	}

	public String getArmor() {
		return armor;
	}

	public String getWeapon() {
		return weapon;
	}

	public String getSword() {
		return sword;
	}

	public String getAxe() {
		return axe;
	}

	public String getBow() {
		return bow;
	}

	public String getStaff() {
		return staff;
	}

	public String getPolearm() {
		return polearm;
	}

	public Stats getStats() {
		return new Stats() {
			@Override
			public void prepare() {

			}
		};
	}
}
