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
import org.bukkit.entity.Zombie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Stats {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	private final Stats parent;

	private float chance = 1;
	private int tier = 1;
	private int forcedHp = -1;
	private float hpMultiplier = 1;
	private float dmgMultiplier = 1;
	private FloatPossible rarity = new FloatPossible(0, 1);
    private boolean genName = false;

	// Randomizers
	private List<String> names = new ArrayList<>();
	private List<EntityType> types = new ArrayList<>();
	private List<ArmorPossible> helmets = new ArrayList<>();
	private List<ArmorPossible> chestplates = new ArrayList<>();
	private List<ArmorPossible> leggings = new ArrayList<>();
	private List<ArmorPossible> boots = new ArrayList<>();
	private List<WeaponPossible> weapons = new ArrayList<>();

	// Defaults
	private ArmorPossible defArmor = new ArmorPossible(true);
	private WeaponPossible defWeapon = new WeaponPossible(true);

	// Conditions
	private List<Condition<LivingEntity>> hitConds = new ArrayList<>();
	private List<Condition<LivingEntity>> deathConds = new ArrayList<>();
	private List<Condition<LivingEntity>> spawnConds = new ArrayList<>();

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
	public Stats(Stats parent) {
		this.parent = parent;
		if (parent != null) {
			parent.prepare();
			copy(parent);
		}
		prepare();
	}

	/**
	 * Prepares for usage.
	 */
	public abstract void prepare();

	/**
	 * Copies information from another Stats object.
	 * @param other The stats to copy from.
	 */
	public void copy(Stats other) {
		this.chance = other.chance;
		this.tier = other.tier;
		this.forcedHp = other.forcedHp;
		this.hpMultiplier = other.hpMultiplier;
		this.dmgMultiplier = other.dmgMultiplier;
		this.rarity = other.rarity;
        this.genName = other.genName;

		this.names = other.names;
		this.types = other.types;
		this.hitConds = other.hitConds;
		this.helmets = other.helmets;
		this.chestplates = other.chestplates;
		this.leggings = other.leggings;
		this.boots = other.boots;
		this.weapons = other.weapons;

		this.defArmor = other.defArmor;
		this.defWeapon = other.defWeapon;

		this.hitConds = other.hitConds;
		this.deathConds = other.deathConds;
		this.spawnConds = other.spawnConds;
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

	public FloatPossible getRarity() {
		return rarity;
	}

	public void setRarity(FloatPossible rarity) {
		this.rarity = rarity;
	}

    public boolean getGenName() {
        return genName;
    }

    public void setGenName(boolean genName) {
        this.genName = genName;
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

	public boolean hasHelmet() {
		return true;
	}

	public ArmorPossible getChestplate() {
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

	public boolean hasBoots() {
		return true;
	}

	public WeaponPossible getWeapon() {
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

	public List<WeaponPossible> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<WeaponPossible> weapons) {
		this.weapons = weapons;
	}

	// Defaults

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

	// Conditions

	public List<Condition<LivingEntity>> getHitConds() {
		return hitConds;
	}

	public void setHitConds(List<Condition<LivingEntity>> hitConds) {
		this.hitConds = hitConds;
	}

	public List<Condition<LivingEntity>> getDeathConds() {
		return deathConds;
	}

	public void setDeathConds(List<Condition<LivingEntity>> deathConds) {
		this.deathConds = deathConds;
	}

	public List<Condition<LivingEntity>> getSpawnConds() {
		return spawnConds;
	}

	public void setSpawnConds(List<Condition<LivingEntity>> spawnConds) {
		this.spawnConds = spawnConds;
	}

	// Generation methods

	public void spawn(Location location) {
		LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, getType());

		if (entity.getType().equals(EntityType.ZOMBIE)) {
			((Zombie) entity).setBaby(false);
			((Zombie) entity).setVillagerProfession(null);
		}

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

		entity.getEquipment().setItemInMainHand(getWeapon().get(Util.rarity(rarity.get())).build());

		if (hasHelmet())
			entity.getEquipment().setHelmet(getHelmet().get(Util.rarity(rarity.get())).build());
		if (hasChestplate())
			entity.getEquipment().setChestplate(getChestplate().get(Util.rarity(rarity.get())).build());
		if (hasLeggings())
			entity.getEquipment().setLeggings(getLeggings().get(Util.rarity(rarity.get())).build());
		if (hasBoots())
			entity.getEquipment().setBoots(getBoots().get(Util.rarity(rarity.get())).build());

		Util.updateStats(entity);
		entity.setHealth(entity.getMaxHealth());

        for (int i = getSpawnConds().size() - 1; i >= 0; i--) {
            Condition<LivingEntity> c = getSpawnConds().get(i);
            if (c.isComplete(entity)) {
                c.onComplete(entity);
                if (c.addOnComplete() != null)
                    getSpawnConds().addAll(c.addOnComplete());
            }
        }
    }

	public class WeaponPossible {
		public Material material = Material.WOOD_SWORD;
		public Rarity rarity = null;
		public String name = "";
		public Rune rune = null;

		public IntPossible dmg = new IntPossible(1);
		public IntPossible range = new IntPossible(0);

		public float statChance = 0.08f;
		public int statMax = 3;
		public IntPossible strength = new IntPossible(0);
		public IntPossible dexterity = new IntPossible(0);
		public IntPossible intellect = new IntPossible(0);
		public IntPossible vitality = new IntPossible(0);

		public float attChance = 0.08f;
		public int attMax = 3;
		public FloatPossible pen = new FloatPossible(0);
		public IntPossible fireDmg = new IntPossible(0);
		public IntPossible iceDmg = new IntPossible(0);
		public IntPossible poisonDmg = new IntPossible(0);
		public IntPossible pureDmg = new IntPossible(0);
		public FloatPossible lifeSteal = new FloatPossible(0);
		public FloatPossible trueHearts = new FloatPossible(0);
		public FloatPossible blind = new FloatPossible(0);

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
				statMax = defWeapon.attMax;
				pen = defWeapon.pen;
				strength = defWeapon.strength;
				dexterity = defWeapon.dexterity;
				intellect = defWeapon.intellect;
				vitality = defWeapon.vitality;

				attChance = defWeapon.attChance;
				attMax = defWeapon.attMax;
				fireDmg = defWeapon.fireDmg;
				iceDmg = defWeapon.iceDmg;
				poisonDmg = defWeapon.poisonDmg;
				pureDmg = defWeapon.pureDmg;
				lifeSteal = defWeapon.lifeSteal;
				trueHearts = defWeapon.trueHearts;
				blind = defWeapon.blind;
			}
		}

		public Weapon get(float f) {
			Weapon weapon = new Weapon(material);
			weapon.setTier(tier);
			weapon.setRarity(rarity != null ? rarity : Rarity.fromValue(f));

            if (name == null || name.isEmpty())
                name = Util.generateName(weapon);
            weapon.setName(Util.colorCodes(name));

			if (random.nextFloat() <= statChance)
				weapon.setStrength(strength.get());
			if (random.nextFloat() < statChance)
				weapon.setDexterity(dexterity.get());
			if (random.nextFloat() <= statChance)
				weapon.setIntellect(intellect.get());
			if (random.nextFloat() <= statChance)
				weapon.setVitality(vitality.get());

			int min = Math.round((dmg.getDiff() * f) + dmg.getMin());
			int max = min + range.get();
			weapon.setDmg(min, max);

			if (random.nextFloat() <= attChance)
				weapon.setPen(pen.get());
			if (random.nextFloat() <= attChance)
				weapon.setFireDmg(fireDmg.get());
			if (random.nextFloat() <= attChance)
				weapon.setIceDmg(iceDmg.get());
			if (random.nextFloat() <= attChance)
				weapon.setPoisonDmg(poisonDmg.get());
			if (random.nextFloat() <= attChance)
				weapon.setPureDmg(pureDmg.get());
			if (random.nextFloat() <= attChance)
				weapon.setLifeSteal(lifeSteal.get());
			if (random.nextFloat() <= attChance)
				weapon.setTrueHearts(trueHearts.get());
			if (random.nextFloat() <= attChance)
				weapon.setBlind(blind.get());

			return weapon;
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

		public float statChance = 0.08f;
		public int statMax = 3;
		public IntPossible strength = new IntPossible(0);
		public IntPossible dexterity = new IntPossible(0);
		public IntPossible intellect = new IntPossible(0);
		public IntPossible vitality = new IntPossible(0);

		public float attChance = 0.08f;
		public int attMax = 3;
		public FloatPossible physRes = new FloatPossible(0);
		public FloatPossible magRes = new FloatPossible(0);
		public FloatPossible block = new FloatPossible(0);
		public FloatPossible dodge = new FloatPossible(0);

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
				statMax = defArmor.statMax;
				strength = defArmor.strength;
				dexterity = defArmor.dexterity;
				intellect = defArmor.intellect;
				vitality = defArmor.vitality;

				attChance = defArmor.attChance;
				attMax = defArmor.attMax;
				physRes = defArmor.physRes;
				magRes = defArmor.magRes;
				block = defArmor.block;
				dodge = defArmor.dodge;
			}
		}

		public Armor get(float f) {
			Armor armor = new Armor(material);
			armor.setTier(tier);
			armor.setRarity(rarity != null ? rarity : Rarity.fromValue(f));

            if (name == null || name.isEmpty())
                name = Util.generateName(armor);
            armor.setName(Util.colorCodes(name));

			if (random.nextFloat() <= statChance)
				armor.setStrength(strength.get());
			if (random.nextFloat() <= statChance)
				armor.setDexterity(dexterity.get());
			if (random.nextFloat() <= statChance)
				armor.setIntellect(intellect.get());
			if (random.nextFloat() <= statChance)
				armor.setVitality(vitality.get());

			armor.setHp(Math.round((hp.getDiff() * f) + hp.getMin()));

			if (hpRegen.getMin() > 0 && energyRegen.getMin() > 0) {
				if (random.nextBoolean()) armor.setHpRegen(hpRegen.get());
				else armor.setEnergyRegen(energyRegen.get());
			} else if (hpRegen.getMin() > 0)
				armor.setHpRegen(hpRegen.get());
			else if (energyRegen.getMin() > 0)
				armor.setEnergyRegen(energyRegen.get());

			if (random.nextFloat() <= attChance)
				if (physRes.getMin() > 0 && magRes.getMin() > 0) {
					if (random.nextBoolean()) armor.setPhysRes(physRes.get());
					else armor.setMagRes(magRes.get());
				} else if (physRes.getMin() > 0)
					armor.setPhysRes(physRes.get());
				else if (magRes.getMin() > 0)
					armor.setMagRes(magRes.get());

			if (random.nextFloat() <= attChance)
				armor.setBlock(block.get());
			if (random.nextFloat() <= attChance)
				armor.setDodge(dodge.get());

			return armor;
		}
	}

    public class StatFloatPossible extends FloatPossible {

    }
}
