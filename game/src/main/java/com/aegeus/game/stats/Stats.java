package com.aegeus.game.stats;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Condition;
import com.aegeus.game.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	private final List<String> names = new ArrayList<>();
	private final List<EntityType> types = new ArrayList<>();
	private final List<Condition<LivingEntity>> hitConds = new ArrayList<>();
	private final List<ArmorPossible> helmets = new ArrayList<>();
	private final List<ArmorPossible> chestplates = new ArrayList<>();
	private final List<ArmorPossible> leggings = new ArrayList<>();
	private final List<ArmorPossible> boots = new ArrayList<>();
	private final List<WeaponPossible> weapons = new ArrayList<>();

	private float chance = 0.1f;
	private int tier = 1;
	private int forcedHp = -1;
	private float hpMultiplier = 1;
	private float dmgMultiplier = 1;
	private FloatPossible rarity = new FloatPossible(0, 1);

	private ArmorPossible defArmor = new ArmorPossible(true);
	private WeaponPossible defWeapon = new WeaponPossible(true);

	public Stats() {
		prepare();
	}

	public abstract void prepare();

	public boolean hasHelmet() {
		return true;
	}

	public boolean hasChestplate() {
		return true;
	}

	public boolean hasLeggings() {
		return true;
	}

	public boolean hasBoots() {
		return true;
	}

	public boolean hasWeapon() {
		return true;
	}

	public void addName(String name) {
		this.names.add(name);
	}

	public void addType(EntityType type) {
		this.types.add(type);
	}

	public void addHelmet(ArmorPossible possible) {
		this.helmets.add(possible);
	}

	public void addChestplate(ArmorPossible possible) {
		this.chestplates.add(possible);
	}

	public void addLeggings(ArmorPossible possible) {
		this.leggings.add(possible);
	}

	public void addBoots(ArmorPossible possible) {
		this.boots.add(possible);
	}

	public void addWeapon(WeaponPossible possible) {
		this.weapons.add(possible);
	}

	public String getName() {
		return !names.isEmpty() ? names.get(random.nextInt(names.size())) : null;
	}

	public EntityType getType() {
		return !types.isEmpty() ? types.get(random.nextInt(types.size())) : null;
	}

	public ArmorPossible getHelmet() {
		return !helmets.isEmpty() ? helmets.get(random.nextInt(helmets.size())) : null;
	}

	public ArmorPossible getChestplate() {
		return !chestplates.isEmpty() ? chestplates.get(random.nextInt(chestplates.size())) : null;
	}

	public ArmorPossible getLeggings() {
		return !leggings.isEmpty() ? leggings.get(random.nextInt(leggings.size())) : null;
	}

	public ArmorPossible getBoots() {
		return !boots.isEmpty() ? boots.get(random.nextInt(boots.size())) : null;
	}

	public WeaponPossible getWeapon() {
		return !weapons.isEmpty() ? weapons.get(random.nextInt(weapons.size())) : null;
	}

	public ArmorPossible getDefaultArmor() {
		return defArmor;
	}

	public void addHitCond(Condition<LivingEntity> condition) {
		this.hitConds.add(condition);
	}

	public List<Condition<LivingEntity>> getHitConds() {
		return hitConds;
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

	public FloatPossible getRarity() {
		return rarity;
	}

	public void setRarity(FloatPossible rarity) {
		this.rarity = rarity;
	}

	public Weapon get(WeaponPossible p, float f) {
		Weapon weapon = new Weapon(p.material);
		if (!p.name.isEmpty()) weapon.setName(Util.colorCodes(p.name));
		weapon.getEquipmentInfo().setTier(tier);
		weapon.getEquipmentInfo().setRarity(p.rarity != null ? p.rarity : Rarity.fromValue(f));

		if (random.nextFloat() <= p.statChance)
			weapon.getEquipmentInfo().setStrength(p.strength.get());
		if (random.nextFloat() < p.statChance)
			weapon.getEquipmentInfo().setDexterity(p.dexterity.get());
		if (random.nextFloat() <= p.statChance)
			weapon.getEquipmentInfo().setIntellect(p.intellect.get());
		if (random.nextFloat() <= p.statChance)
			weapon.getEquipmentInfo().setVitality(p.vitality.get());

		int min = Math.round(f * (p.dmg.getMax() - p.dmg.getMin())) + p.dmg.getMin();
		int max = min + p.range.get();
		weapon.setDmg(min, max);

		if (random.nextFloat() <= p.attChance)
			weapon.setFireDmg(p.fireDmg.get());
		if (random.nextFloat() <= p.attChance)
			weapon.setIceDmg(p.iceDmg.get());
		if (random.nextFloat() <= p.attChance)
			weapon.setPoisonDmg(p.poisonDmg.get());
		if (random.nextFloat() <= p.attChance)
			weapon.setPureDmg(p.pureDmg.get());
		if (random.nextFloat() <= p.attChance)
			weapon.setLifeSteal(p.lifeSteal.get());
		if (random.nextFloat() <= p.attChance)
			weapon.setTrueHearts(p.trueHearts.get());
		if (random.nextFloat() <= p.attChance)
			weapon.setBlindness(p.blindness.get());

		return weapon;
	}

	public Armor get(ArmorPossible p, float f) {
		Armor armor = new Armor(p.material);
		if (!p.name.isEmpty()) armor.setName(Util.colorCodes(p.name));
		armor.getEquipmentInfo().setTier(tier);
		armor.getEquipmentInfo().setRarity(p.rarity != null ? p.rarity : Rarity.fromValue(f));

		if (random.nextFloat() <= p.statChance)
			armor.getEquipmentInfo().setStrength(p.strength.get());
		if (random.nextFloat() <= p.statChance)
			armor.getEquipmentInfo().setDexterity(p.dexterity.get());
		if (random.nextFloat() <= p.statChance)
			armor.getEquipmentInfo().setIntellect(p.intellect.get());
		if (random.nextFloat() <= p.statChance)
			armor.getEquipmentInfo().setVitality(p.vitality.get());

		armor.setHp(Math.round(f * (p.hp.getMax() - p.hp.getMin())) + p.hp.getMin());

		if (p.hpRegen.getMin() > 0 && p.energyRegen.getMin() > 0) {
			if (random.nextBoolean()) armor.setHpRegen(p.hpRegen.get());
			else armor.setEnergyRegen(p.energyRegen.get());
		} else if (p.hpRegen.getMin() > 0)
			armor.setHpRegen(p.hpRegen.get());
		else if (p.energyRegen.getMin() > 0)
			armor.setEnergyRegen(p.energyRegen.get());

		if (random.nextFloat() <= p.attChance)
			armor.setDefense(p.defense.get());
		if (random.nextFloat() <= p.attChance)
			armor.setMagicRes(p.magicRes.get());
		if (random.nextFloat() <= p.attChance)
			armor.setBlock(p.block.get());
		if (random.nextFloat() <= p.attChance)
			armor.setThorns(p.thorns.get());

		return armor;
	}

	public void spawn(Location location) {
		LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, getType());
		AgMonster info = Aegeus.getInstance().getMonster(entity);
		info.setName(Util.colorCodes(getName()));
		entity.setCustomName(info.getName());
		entity.setCustomNameVisible(true);

		info.setHitConds(getHitConds());
		info.setTier(getTier());
		info.setChance(getChance());
		info.setForcedHp(getForcedHp());
		info.setHpMultiplier(getHpMultiplier());
		info.setDmgMultiplier(getDmgMultiplier());

		if (hasWeapon())
			entity.getEquipment().setItemInMainHand(get(getWeapon(), Util.rarity(rarity.get())).build());

		if (hasHelmet())
			entity.getEquipment().setHelmet(get(getHelmet(), Util.rarity(rarity.get())).build());
		if (hasChestplate())
			entity.getEquipment().setChestplate(get(getChestplate(), Util.rarity(rarity.get())).build());
		if (hasLeggings())
			entity.getEquipment().setLeggings(get(getLeggings(), Util.rarity(rarity.get())).build());
		if (hasBoots())
			entity.getEquipment().setBoots(get(getBoots(), Util.rarity(rarity.get())).build());

		Util.updateStats(entity);
		entity.setHealth(entity.getMaxHealth());
	}

	public class WeaponPossible {
		public Material material = Material.WOOD_SWORD;
		public Rarity rarity = null;
		public String name = "";
		public Rune rune = null;

		public IntPossible dmg = new IntPossible(1);
		public IntPossible range = new IntPossible(0);

		public float statChance = 0.05f;
		public IntPossible strength = new IntPossible(0);
		public IntPossible dexterity = new IntPossible(0);
		public IntPossible intellect = new IntPossible(0);
		public IntPossible vitality = new IntPossible(0);

		public float attChance = 0.05f;
		public IntPossible fireDmg = new IntPossible(0);
		public IntPossible iceDmg = new IntPossible(0);
		public IntPossible poisonDmg = new IntPossible(0);
		public IntPossible pureDmg = new IntPossible(0);
		public FloatPossible lifeSteal = new FloatPossible(0);
		public FloatPossible trueHearts = new FloatPossible(0);
		public FloatPossible blindness = new FloatPossible(0);

		public WeaponPossible() {
			this(false);
		}

		public WeaponPossible(boolean def) {
			if (!def) {
				material = defWeapon.material;
				rarity = defWeapon.rarity;
				name = defWeapon.name;
				rune = defWeapon.rune;

				dmg = defWeapon.dmg;
				range = defWeapon.range;

				statChance = defWeapon.statChance;
				strength = defWeapon.strength;
				dexterity = defWeapon.dexterity;
				intellect = defWeapon.intellect;
				vitality = defWeapon.vitality;

				attChance = defWeapon.attChance;
				fireDmg = defWeapon.fireDmg;
				iceDmg = defWeapon.iceDmg;
				poisonDmg = defWeapon.poisonDmg;
				pureDmg = defWeapon.pureDmg;
				lifeSteal = defWeapon.lifeSteal;
				trueHearts = defWeapon.trueHearts;
				blindness = defWeapon.blindness;
			}
		}
	}

	public class ArmorPossible {
		public Material material = Material.LEATHER_HELMET;
		public Rarity rarity = null;
		public String name = "";
		public Rune rune = null;

		public IntPossible hp = new IntPossible(1);
		public IntPossible hpRegen = new IntPossible(0);
		public FloatPossible energyRegen = new FloatPossible(0);

		public float statChance = 0.05f;
		public IntPossible strength = new IntPossible(0);
		public IntPossible dexterity = new IntPossible(0);
		public IntPossible intellect = new IntPossible(0);
		public IntPossible vitality = new IntPossible(0);

		public float attChance = 0.05f;
		public FloatPossible defense = new FloatPossible(0);
		public FloatPossible magicRes = new FloatPossible(0);
		public FloatPossible block = new FloatPossible(0);
		public FloatPossible thorns = new FloatPossible(0);

		public ArmorPossible() {
			this(false);
		}

		public ArmorPossible(boolean def) {
			if (!def) {
				material = defArmor.material;
				rarity = defArmor.rarity;
				name = defArmor.name;
				rune = defArmor.rune;

				hp = defArmor.hp;
				hpRegen = defArmor.hpRegen;
				energyRegen = defArmor.energyRegen;

				statChance = defArmor.statChance;
				strength = defArmor.strength;
				dexterity = defArmor.dexterity;
				intellect = defArmor.intellect;
				vitality = defArmor.vitality;

				attChance = defArmor.attChance;
				defense = defArmor.defense;
				magicRes = defArmor.magicRes;
				block = defArmor.block;
				thorns = defArmor.thorns;
			}
		}
	}
}
