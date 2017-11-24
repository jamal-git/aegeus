package com.aegeus.game.stats;

import com.aegeus.game.Aegeus;
import com.aegeus.game.ability.Ability;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.Spawner;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.Tier;
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

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	private final Stats parent;

	private float chance = 1;
	private int tier = 0;
	private int forcedHp = -1;
	private float hpMultiplier = 1;
	private float dmgMultiplier = 1;
	private FloatPoss rarity = new FloatPoss(0, 1);
	private boolean glowing = false;
	private boolean genName = false;

	// Actions
	private List<Action<LivingEntity>> spawnActions = new ArrayList<>();

	// Randomizers
	private List<String> names = new ArrayList<>();
	private List<EntityType> types = new ArrayList<>();
	private List<Ability> abils = new ArrayList<>();
	private IntPoss abilCount = null;
	private List<ArmorPossible> helmets = new ArrayList<>();
	private List<ArmorPossible> chestplates = new ArrayList<>();
	private List<ArmorPossible> leggings = new ArrayList<>();
	private List<ArmorPossible> boots = new ArrayList<>();
	private List<WeaponPossible> weapons = new ArrayList<>();
	private Map<EnumCraftingMaterial, Chance<IntPoss>> drops = new HashMap<>();

	// Defaults
	private ArmorPossible defArmor = new ArmorPossible(null);
	private WeaponPossible defWeapon = new WeaponPossible(null);

	/**
	 * Creates an instance of Stats without a parent.
	 */
	public Stats() {
		this(null);
	}

	/**
	 * Creates an instance of Stats that copies a parent.
	 *
	 * @param parent The parent.
	 */
	@SuppressWarnings("IncompleteCopyConstructor")
	public Stats(Stats parent) {
		this.parent = parent;
		if (parent != null) {
			parent.prepare();
			copy(parent);
		}
		prepare();
	}

	public static Stats fromName(String name) {
		try {
			return (Stats) Class.forName("com.aegeus.game.stats." + name).newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			return null;
		}
	}

	public static Stats fromName(String name, String parentName) {
		try {
			Class stats = Class.forName("com.aegeus.game.stats." + name);
			Class parent = Class.forName("com.aegeus.game.stats." + parentName);
			return (Stats) stats.getConstructor(Stats.class).newInstance((Stats) parent.newInstance());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| NoSuchMethodException | InvocationTargetException e) {
			return null;
		}
	}

	/**
	 * Prepares for usage.
	 */
	public abstract void prepare();

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
		this.spawnActions = other.spawnActions;
		this.names = other.names;
		this.types = other.types;
		this.abils = other.abils;
		this.abilCount = other.abilCount;
		this.helmets = other.helmets;
		this.chestplates = other.chestplates;
		this.leggings = other.leggings;
		this.boots = other.boots;
		this.weapons = other.weapons;
		this.defArmor = other.defArmor;
		this.defWeapon = other.defWeapon;
		this.drops.putAll(other.drops);
	}

	public Stats getParent() {
		return parent;
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

	public void setForcedHp(int forcedHp) {
		this.forcedHp = forcedHp;
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

	public List<Action<LivingEntity>> getSpawnActions() {
		return spawnActions;
	}

	public void setSpawnActions(List<Action<LivingEntity>> spawnActions) {
		this.spawnActions = spawnActions;
	}

	// Randomizers

	public String getName() {
		return names.isEmpty() ? null : names.size() == 1 ? names.get(0) :
				names.get(random.nextInt(names.size()));
	}

	public EntityType getType() {
		return types.isEmpty() ? EntityType.ZOMBIE : types.size() == 1 ? types.get(0) :
				types.get(random.nextInt(types.size()));
	}

	public ArmorPossible getHelmet() {
		return helmets.isEmpty() ? defArmor : helmets.size() == 1 ? helmets.get(0) :
				helmets.get(random.nextInt(helmets.size()));
	}

	public ArmorPossible getHelmet(EntityType type) {
		List<ArmorPossible> helmets = this.helmets.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type)).
				collect(Collectors.toList());
		return helmets.isEmpty() ? defArmor : helmets.size() == 1 ? helmets.get(0) :
				helmets.get(random.nextInt(helmets.size()));
	}

	public boolean hasHelmet() {
		return true;
	}

	public ArmorPossible getChestplate() {
		return chestplates.isEmpty() ? defArmor : chestplates.size() == 1 ? chestplates.get(0) :
				chestplates.get(random.nextInt(chestplates.size()));
	}

	public ArmorPossible getChestplate(EntityType type) {
		List<ArmorPossible> chestplates = this.chestplates.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return chestplates.isEmpty() ? defArmor : chestplates.size() == 1 ? chestplates.get(0) :
				chestplates.get(random.nextInt(chestplates.size()));
	}

	public boolean hasChestplate() {
		return true;
	}

	public ArmorPossible getLeggings() {
		return leggings.isEmpty() ? defArmor : leggings.size() == 1 ? leggings.get(0) :
				leggings.get(random.nextInt(leggings.size()));
	}

	public void setLeggings(List<ArmorPossible> leggings) {
		this.leggings = leggings;
	}

	public ArmorPossible getLeggings(EntityType type) {
		List<ArmorPossible> leggings = this.leggings.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return leggings.isEmpty() ? defArmor : leggings.size() == 1 ? leggings.get(0) :
				leggings.get(random.nextInt(leggings.size()));
	}

	public boolean hasLeggings() {
		return true;
	}

	public ArmorPossible getBoots() {
		return boots.isEmpty() ? defArmor : boots.size() == 1 ? boots.get(0) :
				boots.get(random.nextInt(boots.size()));
	}

	public void setBoots(List<ArmorPossible> boots) {
		this.boots = boots;
	}

	public ArmorPossible getBoots(EntityType type) {
		List<ArmorPossible> boots = this.boots.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return boots.isEmpty() ? defArmor : boots.size() == 1 ? boots.get(0) :
				boots.get(random.nextInt(boots.size()));
	}

	public boolean hasBoots() {
		return true;
	}

	public WeaponPossible getWeapon() {
		return weapons.isEmpty() ? defWeapon : weapons.size() == 1 ? weapons.get(0) :
				weapons.get(random.nextInt(weapons.size()));
	}

	public WeaponPossible getWeapon(EntityType type) {
		List<WeaponPossible> weapons = this.weapons.stream().filter(i ->
				i.allowedTypes == null || Arrays.asList(i.allowedTypes).contains(type))
				.collect(Collectors.toList());
		return weapons.isEmpty() ? defWeapon : weapons.size() == 1 ? weapons.get(0) :
				weapons.get(random.nextInt(weapons.size()));
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

	public List<ArmorPossible> getHelmets() {
		return helmets;
	}

	public void setHelmets(List<ArmorPossible> helmets) {
		this.helmets = helmets;
	}

	public List<ArmorPossible> getChestplates() {
		return chestplates;
	}

	public void setChestplates(List<ArmorPossible> chestplates) {
		this.chestplates = chestplates;
	}

	public List<ArmorPossible> getAllLeggings() {
		return leggings;
	}

	public List<ArmorPossible> getAllBoots() {
		return boots;
	}

	// Defaults

	public List<WeaponPossible> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<WeaponPossible> weapons) {
		this.weapons = weapons;
	}

	public ArmorPossible getDefArmor() {
		return defArmor;
	}

	public void setDefArmor(ArmorPossible defArmor) {
		this.defArmor = defArmor;
	}

	public WeaponPossible getDefWeapon() {
		return defWeapon;
	}

	public void setDefWeapon(WeaponPossible defWeapon) {
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
		return spawn(location, null);
	}

	public LivingEntity spawn(Location location, Spawner origin) {
		LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, getType());
		entity.setRemoveWhenFarAway(false);
		if (entity.getType().equals(EntityType.ZOMBIE)) {
			((Zombie) entity).setBaby(false);
			((Zombie) entity).setVillagerProfession(null);
		}

		AgMonster info = Aegeus.getInstance().getMonster(entity);
		info.setName(Tier.fromTier(tier).getColor() + Util.colorCodes(getName()));
		entity.setCustomName(info.getName());
		entity.setCustomNameVisible(true);

		info.setOrigin(origin);
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

		spawnActions.forEach(a -> a.activate(entity));

		return entity;
	}

	public class WeaponPossible {
		public Material material = Material.WOOD_SWORD;
		public EntityType[] allowedTypes = null;
		public String name = "";
		public Rarity rarity = null;

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

		public WeaponPossible() {
			this(defWeapon);
		}

		public WeaponPossible(WeaponPossible other) {
			if (other != null) {
				this.material = other.material;
				this.allowedTypes = other.allowedTypes;
				this.name = other.name;
				this.rarity = other.rarity;
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

	public class ArmorPossible {
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

		public ArmorPossible() {
			this(defArmor);
		}

		public ArmorPossible(ArmorPossible other) {
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
				if (random.nextBoolean()) armor.setPhysRes(physRes.get());
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