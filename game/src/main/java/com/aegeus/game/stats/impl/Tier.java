package com.aegeus.game.stats.impl;

import com.aegeus.game.ability.AbilityConcuss;
import com.aegeus.game.ability.AbilityDetonate;
import com.aegeus.game.ability.AbilityResolve;
import com.aegeus.game.ability.AbilityTackle;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.item.Weight;
import com.aegeus.game.util.*;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Tier extends Stats {
	public static final String SWORD_LIGHT = "sword_light";
	public static final String SWORD_MED = "sword_med";
	public static final String SWORD_HEAVY = "sword_heavy";
	public static final String AXE_LIGHT = "axe_light";
	public static final String AXE_MED = "axe_med";
	public static final String AXE_HEAVY = "axe_heavy";
	public static final String BOW_LIGHT = "bow_light";
	public static final String BOW_MED = "bow_med";
	public static final String BOW_HEAVY = "bow_heavy";

	public static final String HELMET = "helmet";
	public static final String CHESTPLATE = "chestplate";
	public static final String LEGGINGS = "leggings";
	public static final String BOOTS = "boots";

	public static Map<Integer, Tier> tiers = new HashMap<>();

	static {
		tiers.put(0, new Tier() {
			@Override
			public String getName() {
				return "Custom";
			}

			@Override
			public String getColor() {
				return "&f";
			}

			@Override
			public int getWepDura() {
				return 0;
			}

			@Override
			public int getArmorDura() {
				return 0;
			}

			@Override
			public int getReserves() {
				return 0;
			}

			@Override
			public Map<String, String> getNameMap() {
				return new HashMap<>();
			}
		});
		tiers.put(1, new Tier() {
			private final Map<String, String> nameMap = new HashMap<>();

			public void init() {
				nameMap.put("sword_light", "Dirk");
				nameMap.put("sword_med", "Bokken");
				nameMap.put("sword_heavy", "Hunter's Sword");
				nameMap.put("axe_light", "Hatchet");
				nameMap.put("axe_med", "Broadaxe");
				nameMap.put("axe_heavy", "Hunter's Axe");
				nameMap.put("bow_light", "Crossbow");
				nameMap.put("bow_med", "Recurve Bow");
				nameMap.put("bow_heavy", "Longbow");

				setTier(1);
				setChance(0.4f);
				setAbils(Arrays.asList(new AbilityConcuss(), new AbilityTackle(),
						new AbilityResolve()));
				setAbilCount(new IntPoss(1));
				setGenName(true);
				setHasHelmet(() -> true);
				setHasChestplate(Util::rBool);
				setHasLeggings(Util::rBool);
				setHasBoots(Util::rBool);

				getSpawnActs().add(new Action<LivingEntity>() {
					@Override
					public void activate(LivingEntity entity) {
						if (entity.getType().equals(EntityType.SKELETON))
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
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

				getDefWeapon().weight = Weight.MEDIUM;
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

			@Override
			public String getName() {
				return "Basic";
			}

			@Override
			public String getColor() {
				return "&f";
			}

			@Override
			public int getWepDura() {
				return 675;
			}

			@Override
			public int getArmorDura() {
				return 750;
			}

			@Override
			public int getReserves() {
				return 100;
			}

			@Override
			public Map<String, String> getNameMap() {
				return nameMap;
			}
		});
		tiers.put(2, new Tier() {
			private final Map<String, String> nameMap = new HashMap<>();

			public void init() {
				nameMap.put("sword_light", "Dagger");
				nameMap.put("sword_med", "Shortsword");
				nameMap.put("sword_heavy", "Greatsword");
				nameMap.put("axe_light", "Adze");
				nameMap.put("axe_med", "Bardiche");
				nameMap.put("axe_heavy", "Battleaxe");
				nameMap.put("bow_light", "Crossbow");
				nameMap.put("bow_med", "Compound Bow");
				nameMap.put("bow_heavy", "Longbow");

				setTier(2);
				setChance(0.2f);
				setHpMultiplier(1.1f);
				setAbils(Arrays.asList(new AbilityConcuss(), new AbilityTackle(),
						new AbilityResolve()));
				setAbilCount(new IntPoss(1, 2));
				setGenName(true);
				setHasHelmet(() -> true);
				setHasChestplate(Util::rBool);
				setHasLeggings(Util::rBool);
				setHasBoots(() -> true);

				getSpawnActs().add(new Action<LivingEntity>() {
					@Override
					public void activate(LivingEntity entity) {
						if (entity.getType().equals(EntityType.SKELETON))
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
					}
				});

				getDefArmor().hpRegen = new IntPoss(10, 20);
				getDefArmor().physRes = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.08f);
				getDefArmor().magRes = new Chance<>(new FloatPoss(0.01f, 0.04f), 0.08f);
				getDefArmor().block = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
				getDefArmor().dodge = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);
				getDefArmor().reflect = new Chance<>(new FloatPoss(0.01f, 0.03f), 0.04f);

				Map<EnumCraftingMaterial, Chance<IntPoss>> drops = getDrops() == null ? new HashMap<>() : getDrops();
				drops.put(EnumCraftingMaterial.SUN_INGOT, new Chance<>(new IntPoss(1), 1));
				drops.put(EnumCraftingMaterial.MOON_INGOT, new Chance<>(new IntPoss(1), 1));
				setDrops(drops);

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

				getDefWeapon().weight = Weight.MEDIUM;
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

			@Override
			public String getName() {
				return "Advanced";
			}

			@Override
			public String getColor() {
				return "&a";
			}

			@Override
			public int getWepDura() {
				return 1350;
			}

			@Override
			public int getArmorDura() {
				return 1215;
			}

			@Override
			public int getReserves() {
				return 200;
			}

			@Override
			public Map<String, String> getNameMap() {
				return nameMap;
			}
		});
		tiers.put(3, new Tier() {
			private final Map<String, String> nameMap = new HashMap<>();

			public void init() {
				nameMap.put("sword_light", "Dagger");
				nameMap.put("sword_med", "Shortsword");
				nameMap.put("sword_heavy", "Greatsword");
				nameMap.put("axe_light", "Adze");
				nameMap.put("axe_med", "Bardiche");
				nameMap.put("axe_heavy", "Battleaxe");
				nameMap.put("bow_light", "Crossbow");
				nameMap.put("bow_med", "Reflex Bow");
				nameMap.put("bow_heavy", "Longbow");

				setTier(3);
				setChance(0.1f);
				setHpMultiplier(1.2f);
				setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
						new AbilityTackle(), new AbilityResolve()));
				setAbilCount(new IntPoss(2));
				setGenName(true);
				setHasHelmet(() -> true);
				setHasChestplate(() -> true);
				setHasLeggings(() -> true);
				setHasBoots(Util::rBool);

				getSpawnActs().add(new Action<LivingEntity>() {
					@Override
					public void activate(LivingEntity entity) {
						if (!entity.getType().equals(EntityType.SKELETON))
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
					}
				});

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

				getDefWeapon().weight = Weight.MEDIUM;
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
			public String getName() {
				return "Moderate";
			}

			@Override
			public String getColor() {
				return "&b";
			}

			@Override
			public int getWepDura() {
				return 2030;
			}

			@Override
			public int getArmorDura() {
				return 1830;
			}

			@Override
			public int getReserves() {
				return 300;
			}

			@Override
			public Map<String, String> getNameMap() {
				return nameMap;
			}
		});
		tiers.put(4, new Tier() {
			private final Map<String, String> nameMap = new HashMap<>();

			public void init() {
				nameMap.put("sword_light", "Dagger");
				nameMap.put("sword_med", "Shortsword");
				nameMap.put("sword_heavy", "Greatsword");
				nameMap.put("axe_light", "Adze");
				nameMap.put("axe_med", "Bardiche");
				nameMap.put("axe_heavy", "Battleaxe");
				nameMap.put("bow_light", "Crossbow");
				nameMap.put("bow_med", "Intricate Bow");
				nameMap.put("bow_heavy", "Longbow");

				setTier(4);
				setChance(0.048f);
				setHpMultiplier(1.3f);
				setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
						new AbilityTackle(), new AbilityResolve()));
				setAbilCount(new IntPoss(2, 3));
				setGenName(true);
				setHasHelmet(() -> true);
				setHasChestplate(() -> true);
				setHasLeggings(() -> true);
				setHasBoots(() -> true);

				getSpawnActs().add(new Action<LivingEntity>() {
					@Override
					public void activate(LivingEntity entity) {
						if (entity.getType().equals(EntityType.SKELETON))
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						else
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
					}
				});

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

				getDefWeapon().weight = Weight.MEDIUM;
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

			@Override
			public String getName() {
				return "Composite";
			}

			@Override
			public String getColor() {
				return "&d";
			}

			@Override
			public int getWepDura() {
				return 3050;
			}

			@Override
			public int getArmorDura() {
				return 2745;
			}

			@Override
			public int getReserves() {
				return 400;
			}

			@Override
			public Map<String, String> getNameMap() {
				return nameMap;
			}
		});
		tiers.put(5, new Tier() {
			private final Map<String, String> nameMap = new HashMap<>();

			public void init() {
				nameMap.put("sword_light", "Dagger");
				nameMap.put("sword_med", "Shortsword");
				nameMap.put("sword_heavy", "Greatsword");
				nameMap.put("axe_light", "Adze");
				nameMap.put("axe_med", "Bardiche");
				nameMap.put("axe_heavy", "Battleaxe");
				nameMap.put("bow_light", "Crossbow");
				nameMap.put("bow_med", "Intricate Bow");
				nameMap.put("bow_heavy", "Longbow");

				setTier(5);
				setChance(0.036f);
				setHpMultiplier(1.5f);
				setAbils(Arrays.asList(new AbilityConcuss(), new AbilityDetonate(),
						new AbilityTackle(), new AbilityResolve()));
				setAbilCount(new IntPoss(2, 3));
				setGenName(true);
				setHasHelmet(() -> true);
				setHasChestplate(() -> true);
				setHasLeggings(() -> true);
				setHasBoots(() -> true);

				getSpawnActs().add(new Action<LivingEntity>() {
					@Override
					public void activate(LivingEntity entity) {
						if (entity.getType().equals(EntityType.SKELETON))
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						else
							entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
					}
				});

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

				getDefWeapon().weight = Weight.MEDIUM;
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

			@Override
			public String getName() {
				return "Riotous";
			}

			@Override
			public String getColor() {
				return "&e";
			}

			@Override
			public int getWepDura() {
				return 4575;
			}

			@Override
			public int getArmorDura() {
				return 4120;
			}

			@Override
			public int getReserves() {
				return 500;
			}

			@Override
			public Map<String, String> getNameMap() {
				return nameMap;
			}
		});

		for (Tier t : tiers.values()) t.init();
	}

	public static Tier get(int level) {
		return tiers.getOrDefault(level, tiers.get(0));
	}

	public void init() {

	}

	public abstract String getName();

	public abstract String getColor();

	public abstract int getWepDura();

	public abstract int getArmorDura();

	public abstract int getReserves();

	public abstract Map<String, String> getNameMap();

	public String getName(String id) {
		return getName() + " " + getNameMap().getOrDefault(id,
				get(-1).getNameMap().getOrDefault(id, "Item"));
	}
}
