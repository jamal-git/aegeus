package com.aegeus.game.stats.impl;

import com.aegeus.game.Aegeus;
import com.aegeus.game.ability.Ability;
import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.Weight;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Stats {
	private float chance = 1;
	private int tier = 0;
	private int forcedHp = -1;
	private float hpMultiplier = 1;
	private float dmgMultiplier = 1;
	private FloatPoss rarity = new FloatPoss(0, 1);
	private boolean glowing = false;
	private boolean genName = false;

	// Actions
	private List<Action<LivingEntity>> spawnActs = new ArrayList<>();
	private List<Action<CombatInfo>> hitActs = new ArrayList<>();
	private List<Action<CombatInfo>> damagedActs = new ArrayList<>();

	// Randomizers
	private List<String> names = new ArrayList<>();
	private List<EntityType> types = new ArrayList<>();
	private List<Ability> abils = new ArrayList<>();
	private IntPoss abilCount = new IntPoss();
	private Supplier<Boolean> hasHelmet = () -> true;
	private Supplier<Boolean> hasChestplate = () -> true;
	private Supplier<Boolean> hasLeggings = () -> true;
	private Supplier<Boolean> hasBoots = () -> true;
	private List<ArmorPoss> helmets = new ArrayList<>();
	private List<ArmorPoss> chestplates = new ArrayList<>();
	private List<ArmorPoss> leggings = new ArrayList<>();
	private List<ArmorPoss> boots = new ArrayList<>();
	private List<WeaponPoss> weapons = new ArrayList<>();
	private Map<EnumCraftingMaterial, Chance<IntPoss>> drops = new HashMap<>();

	// Defaults
	private ArmorPoss defArmor = new ArmorPoss(null);
	private WeaponPoss defWeapon = new WeaponPoss(null);

	public void setup(Object... args) {

	}

	/**
	 * Copies information from another Stats object.
	 *
	 * @param other The stats to copy from.
	 */
	public void copy(Stats other) {
		this.chance = other.chance;
		this.tier = other.tier;
		this.forcedHp = other.forcedHp;
		this.hpMultiplier = other.hpMultiplier;
		this.dmgMultiplier = other.dmgMultiplier;
		this.rarity = other.rarity;
		this.glowing = other.glowing;
		this.genName = other.genName;
		this.spawnActs = other.spawnActs;
		this.hitActs = other.hitActs;
		this.damagedActs = other.damagedActs;
		this.names = other.names;
		this.types = other.types;
		this.abils = other.abils;
		this.abilCount = other.abilCount;
		this.hasHelmet = other.hasHelmet;
		this.hasChestplate = other.hasChestplate;
		this.hasLeggings = other.hasLeggings;
		this.hasBoots = other.hasBoots;
		this.helmets = other.helmets;
		this.chestplates = other.chestplates;
		this.leggings = other.leggings;
		this.boots = other.boots;
		this.weapons = other.weapons;
		this.defArmor = other.defArmor;
		this.defWeapon = other.defWeapon;
		this.drops.putAll(other.drops);
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = chance;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public int getForcedHp() {
		return forcedHp;
	}

	public float getHpMultiplier() {
		return hpMultiplier;
	}

	public void setHpMultiplier(float hpMultiplier) {
		this.hpMultiplier = hpMultiplier;
	}

	public float getDmgMultiplier() {
		return dmgMultiplier;
	}

	public void setDmgMultiplier(float dmgMultiplier) {
		this.dmgMultiplier = dmgMultiplier;
	}

	public FloatPoss getRarity() {
		return rarity;
	}

	public void setRarity(FloatPoss rarity) {
		this.rarity = rarity;
	}

	public boolean getGenName() {
		return genName;
	}

	public void setGenName(boolean genName) {
		this.genName = genName;
	}

	public boolean getGlowing() {
		return glowing;
	}

	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	// Actions

	public List<Action<LivingEntity>> getSpawnActs() {
		return spawnActs;
	}

	public void setSpawnActs(List<Action<LivingEntity>> acts) {
		spawnActs = acts;
	}

	public List<Action<CombatInfo>> getHitActs() {
		return hitActs;
	}

	public void setHitActs(List<Action<CombatInfo>> acts) {
		hitActs = acts;
	}

	public List<Action<CombatInfo>> getDamagedActs() {
		return damagedActs;
	}

	public void setDamagedActs(List<Action<CombatInfo>> acts) {
		damagedActs = acts;
	}

	// Randomizers

	public String getName() {
		return names.isEmpty() ? null : names.get(Util.rInt(names.size()));
	}

	public EntityType getType() {
		return types.isEmpty() ? EntityType.ZOMBIE : types.get(Util.rInt(types.size()));
	}

	public ArmorPoss getHelmet() {
		return helmets.isEmpty() ? defArmor : helmets.get(Util.rInt(helmets.size()));
	}

	public ArmorPoss getHelmet(EntityType type) {
		List<ArmorPoss> helmets = this.helmets.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type)).
				collect(Collectors.toList());
		return helmets.isEmpty() ? defArmor : helmets.get(Util.rInt(helmets.size()));
	}

	public Supplier<Boolean> getHasHelmet() {
		return hasHelmet;
	}

	public void setHasHelmet(Supplier<Boolean> s) {
		hasHelmet = s;
	}

	public boolean hasHelmet() {
		return getHasHelmet().get();
	}

	public ArmorPoss getChestplate() {
		return chestplates.isEmpty() ? defArmor : chestplates.size() == 1 ? chestplates.get(0) :
				chestplates.get(Util.rInt(chestplates.size()));
	}

	public ArmorPoss getChestplate(EntityType type) {
		List<ArmorPoss> chestplates = this.chestplates.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return chestplates.isEmpty() ? defArmor : chestplates.size() == 1 ? chestplates.get(0) :
				chestplates.get(Util.rInt(chestplates.size()));
	}

	public Supplier<Boolean> getHasChestplate() {
		return hasChestplate;
	}

	public void setHasChestplate(Supplier<Boolean> s) {
		hasChestplate = s;
	}

	public boolean hasChestplate() {
		return getHasChestplate().get();
	}

	public ArmorPoss getLeggings() {
		return leggings.isEmpty() ? defArmor : leggings.size() == 1 ? leggings.get(0) :
				leggings.get(Util.rInt(leggings.size()));
	}

	public void setLeggings(List<ArmorPoss> leggings) {
		this.leggings = leggings;
	}

	public ArmorPoss getLeggings(EntityType type) {
		List<ArmorPoss> leggings = this.leggings.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return leggings.isEmpty() ? defArmor : leggings.size() == 1 ? leggings.get(0) :
				leggings.get(Util.rInt(leggings.size()));
	}

	public Supplier<Boolean> getHasLeggings() {
		return () -> true;
	}

	public void setHasLeggings(Supplier<Boolean> s) {
		hasLeggings = s;
	}

	public boolean hasLeggings() {
		return hasLeggings.get();
	}

	public ArmorPoss getBoots() {
		return boots.isEmpty() ? defArmor : boots.size() == 1 ? boots.get(0) :
				boots.get(Util.rInt(boots.size()));
	}

	public void setBoots(List<ArmorPoss> boots) {
		this.boots = boots;
	}

	public ArmorPoss getBoots(EntityType type) {
		List<ArmorPoss> boots = this.boots.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return boots.isEmpty() ? defArmor : boots.size() == 1 ? boots.get(0) :
				boots.get(Util.rInt(boots.size()));
	}

	public Supplier<Boolean> getHasBoots() {
		return () -> true;
	}

	public void setHasBoots(Supplier<Boolean> s) {
		hasBoots = s;
	}

	public boolean hasBoots() {
		return hasBoots.get();
	}

	public WeaponPoss getWeapon() {
		return weapons.isEmpty() ? defWeapon : weapons.size() == 1 ? weapons.get(0) :
				weapons.get(Util.rInt(weapons.size()));
	}

	public WeaponPoss getWeapon(EntityType type) {
		List<WeaponPoss> weapons = this.weapons.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return weapons.isEmpty() ? defWeapon : weapons.size() == 1 ? weapons.get(0) :
				weapons.get(Util.rInt(weapons.size()));
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<EntityType> getTypes() {
		return types;
	}

	public void setTypes(List<EntityType> types) {
		this.types = types;
	}

	public List<Ability> getAbils() {
		return abils;
	}

	public void setAbils(List<Ability> abils) {
		this.abils = abils;
	}

	public IntPoss getAbilCount() {
		return abilCount;
	}

	public void setAbilCount(IntPoss abilCount) {
		this.abilCount = abilCount;
	}

	public List<ArmorPoss> getHelmets() {
		return helmets;
	}

	public void setHelmets(List<ArmorPoss> helmets) {
		this.helmets = helmets;
	}

	public List<ArmorPoss> getChestplates() {
		return chestplates;
	}

	public void setChestplates(List<ArmorPoss> chestplates) {
		this.chestplates = chestplates;
	}

	public List<ArmorPoss> getAllLeggings() {
		return leggings;
	}

	public List<ArmorPoss> getAllBoots() {
		return boots;
	}

	// Defaults

	public List<WeaponPoss> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<WeaponPoss> weapons) {
		this.weapons = weapons;
	}

	public ArmorPoss getDefArmor() {
		return defArmor;
	}

	public void setDefArmor(ArmorPoss defArmor) {
		this.defArmor = defArmor;
	}

	public WeaponPoss getDefWeapon() {
		return defWeapon;
	}

	public void setDefWeapon(WeaponPoss defWeapon) {
		this.defWeapon = defWeapon;
	}

	public Map<EnumCraftingMaterial, Chance<IntPoss>> getDrops() {
		return drops;
	}

	public void setDrops(Map<EnumCraftingMaterial, Chance<IntPoss>> drops) {
		this.drops = drops;
	}

	// Generation methods

	public ItemStack applyGlow(ItemStack item) {
		if (item != null && !item.getType().equals(Material.AIR))
			item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return item;
	}

	public LivingEntity spawn(Location location) {
		LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, getType());
		entity.setRemoveWhenFarAway(false);
		if (entity.getType().equals(EntityType.ZOMBIE)) {
			((Zombie) entity).setBaby(false);
			((Zombie) entity).setVillagerProfession(null);
		}

		AgMonster info = Aegeus.getInstance().getMonster(entity);
		info.setName(Tier.get(tier).getColor() + getName());
		info.setOnHit(hitActs);
		info.setOnDamaged(damagedActs);

		entity.setCustomName(info.getName());
		entity.setCustomNameVisible(true);

		info.setTier(getTier());
		info.setChance(getChance());
		info.setForcedHp(getForcedHp());
		info.setHpMultiplier(getHpMultiplier());
		info.setDmgMultiplier(getDmgMultiplier());
		info.setDrops(drops);

		if (!abils.isEmpty()) {
			Collections.shuffle(abils);
			if (abilCount == null)
				info.setAbils(new ArrayList<>(abils));
			else info.setAbils(abils.stream().filter(Objects::nonNull)
					.limit(getAbilCount().get()).collect(Collectors.toList()));
		}

		entity.getEquipment().setItemInMainHand(getWeapon(entity.getType())
				.get(Util.rarity(rarity.get())).build());

		if (hasHelmet()) entity.getEquipment().setHelmet(getHelmet(entity.getType())
				.get(Util.rarity(rarity.get())).build());
		if (hasChestplate()) entity.getEquipment().setChestplate(getChestplate(entity.getType())
				.get(Util.rarity(rarity.get())).build());
		if (hasLeggings()) entity.getEquipment().setLeggings(getLeggings(entity.getType())
				.get(Util.rarity(rarity.get())).build());
		if (hasBoots()) entity.getEquipment().setBoots(getBoots(entity.getType())
				.get(Util.rarity(rarity.get())).build());

		if (getGlowing()) {
			entity.getEquipment().setItemInMainHand(applyGlow(entity.getEquipment().getItemInMainHand()));
			entity.getEquipment().setHelmet(applyGlow(entity.getEquipment().getHelmet()));
			entity.getEquipment().setChestplate(applyGlow(entity.getEquipment().getChestplate()));
			entity.getEquipment().setLeggings(applyGlow(entity.getEquipment().getLeggings()));
			entity.getEquipment().setBoots(applyGlow(entity.getEquipment().getBoots()));
		}

		Util.updateStats(entity);
		entity.setHealth(entity.getMaxHealth());

		spawnActs.forEach(a -> a.activate(entity));

		return entity;
	}

	public class WeaponPoss {
		public Material material = Material.WOOD_SWORD;
		public EntityType[] allowedTypes = null;
		public String name = "";
		public Rarity rarity = null;
		public int weight = Weight.MEDIUM;

		public IntPoss dmg = new IntPoss(1);
		public IntPoss range = new IntPoss();

		public Chance<FloatPoss> pen = new Chance<>();
		public Chance<IntPoss> fireDmg = new Chance<>();
		public Chance<IntPoss> iceDmg = new Chance<>();
		public Chance<IntPoss> poisonDmg = new Chance<>();
		public Chance<IntPoss> pureDmg = new Chance<>();
		public Chance<FloatPoss> lifeSteal = new Chance<>();
		public Chance<FloatPoss> trueHearts = new Chance<>();
		public Chance<FloatPoss> blind = new Chance<>();

		public WeaponPoss() {
			this(defWeapon);
		}

		public WeaponPoss(WeaponPoss other) {
			if (other != null) {
				this.material = other.material;
				this.allowedTypes = other.allowedTypes;
				this.name = other.name;
				this.rarity = other.rarity;
				this.weight = other.weight;
				this.dmg = other.dmg;
				this.range = other.range;
				this.pen = other.pen;
				this.fireDmg = other.fireDmg;
				this.iceDmg = other.iceDmg;
				this.poisonDmg = other.poisonDmg;
				this.pureDmg = other.pureDmg;
				this.lifeSteal = other.lifeSteal;
				this.trueHearts = other.trueHearts;
				this.blind = other.blind;
			}
		}

		public Weapon get(float f) {
			Weapon weapon = new Weapon(material);
			weapon.setTier(tier);
			weapon.setRarity(rarity != null ? rarity : Rarity.fromValue(f));
			weapon.setWeight(weight);

			int min = Math.round((dmg.getDiff() * f) + dmg.getMin());
			int max = min + range.get();
			weapon.setDmg(min, max);

			FloatPoss pen = this.pen.get();
			if (pen != null) weapon.setPen(pen.get());

			IntPoss fireDmg = this.fireDmg.get();
			if (fireDmg != null) weapon.setFireDmg(fireDmg.get());

			IntPoss iceDmg = this.iceDmg.get();
			if (iceDmg != null) weapon.setIceDmg(iceDmg.get());

			IntPoss poisonDmg = this.poisonDmg.get();
			if (poisonDmg != null) weapon.setPoisonDmg(poisonDmg.get());

			IntPoss pureDmg = this.pureDmg.get();
			if (pureDmg != null) weapon.setPureDmg(pureDmg.get());

			FloatPoss lifeSteal = this.lifeSteal.get();
			if (lifeSteal != null) weapon.setLifeSteal(lifeSteal.get());

			FloatPoss trueHearts = this.trueHearts.get();
			if (trueHearts != null) weapon.setTrueHearts(trueHearts.get());

			FloatPoss blind = this.blind.get();
			if (blind != null) weapon.setBlind(blind.get());

			if (name == null || name.isEmpty())
				name = Util.generateName(weapon);
			weapon.setName(Util.colorCodes(name));

			return weapon;
		}
	}

	public class ArmorPoss {
		public Material material = Material.LEATHER_HELMET;
		public EntityType[] allowedTypes = null;
		public String name = "";
		public Rarity rarity = null;

		public IntPoss hp = new IntPoss(1);
		public IntPoss hpRegen = new IntPoss(1);

		public Chance<FloatPoss> physRes = new Chance<>();
		public Chance<FloatPoss> magRes = new Chance<>();
		public Chance<FloatPoss> block = new Chance<>();
		public Chance<FloatPoss> dodge = new Chance<>();
		public Chance<FloatPoss> reflect = new Chance<>();

		public ArmorPoss() {
			this(defArmor);
		}

		public ArmorPoss(ArmorPoss other) {
			if (other != null) {
				this.material = other.material;
				this.allowedTypes = other.allowedTypes;
				this.name = other.name;
				this.rarity = other.rarity;
				this.hp = other.hp;
				this.hpRegen = other.hpRegen;
				this.physRes = other.physRes;
				this.magRes = other.magRes;
				this.block = other.block;
				this.dodge = other.dodge;
				this.reflect = other.reflect;
			}
		}

		public Armor get(float f) {
			Armor armor = new Armor(material);
			armor.setTier(tier);
			armor.setRarity(rarity != null ? rarity : Rarity.fromValue(f));

			armor.setHp(Math.round((hp.getDiff() * f) + hp.getMin()));
			armor.setHpRegen(hpRegen.get());

			FloatPoss physRes = this.physRes.get();
			FloatPoss magRes = this.magRes.get();
			if (physRes != null && magRes != null) {
				if (Util.rBool()) armor.setPhysRes(physRes.get());
				else armor.setMagRes(magRes.get());
			} else if (physRes != null)
				armor.setPhysRes(physRes.get());
			else if (magRes != null)
				armor.setMagRes(magRes.get());

			FloatPoss block = this.block.get();
			if (block != null) armor.setBlock(block.get());
			FloatPoss dodge = this.dodge.get();
			if (dodge != null) armor.setDodge(dodge.get());
			FloatPoss reflect = this.reflect.get();
			if (reflect != null) armor.setReflect(reflect.get());

			if (name == null || name.isEmpty())
				name = Util.generateName(armor);
			armor.setName(Util.colorCodes(name));

			return armor;
		}
	}
}