package com.aegeus.game.entity;

import com.aegeus.game.ability.Ability;
import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.util.Action;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.IntPoss;
import com.aegeus.game.util.Util;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgMonster extends AgLiving {
	private String name;

	private List<Ability> abils = new ArrayList<>();
	private Ability activeAbil = null;
	private float abilChance = 0.2f;

	private List<Action<CombatInfo>> onHit = new ArrayList<>();
	private List<Action<CombatInfo>> onDamaged = new ArrayList<>();
	private Map<EnumCraftingMaterial, Chance<IntPoss>> drops = new HashMap<>();

	private Spawner origin = null;
	private int tier = 0;
	private float chance = 0;
	private int gold = 0;
	private float goldChance = 0;
	private int forcedHp = -1;
	private float hpMultiplier = 1;
	private float dmgMultiplier = 1;

	public AgMonster(LivingEntity entity) {
		super(entity);
	}

	public AgMonster(AgEntity info) {
		super(info);
	}

	public AgMonster(AgLiving info) {
		super(info);
	}

	public AgMonster(AgMonster other) {
		super(other);
		this.name = other.name;
		this.abils = other.abils;
		this.activeAbil = other.activeAbil;
		this.abilChance = other.abilChance;
		this.onHit = other.onHit;
		this.onDamaged = other.onDamaged;
		this.origin = other.origin;
		this.tier = other.tier;
		this.chance = other.chance;
		this.gold = other.gold;
		this.goldChance = other.goldChance;
		this.forcedHp = other.forcedHp;
		this.hpMultiplier = other.hpMultiplier;
		this.dmgMultiplier = other.dmgMultiplier;
        this.drops.putAll(other.drops);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Util.colorCodes(name);
	}

	public List<Ability> getAbils() {
		return abils;
	}

	public void setAbils(List<Ability> abils) {
		this.abils = abils;
	}

	public Ability getActiveAbil() {
		return activeAbil;
	}

	public void setActiveAbil(Ability activeAbil) {
		this.activeAbil = activeAbil;
	}

	public float getAbilChance() {
		return abilChance;
	}

	public void setAbilChance(float abilChance) {
		this.abilChance = abilChance;
	}

	public List<Action<CombatInfo>> getOnHit() {
		return onHit;
	}

	public void setOnHit(List<Action<CombatInfo>> onHit) {
		this.onHit = onHit;
	}

	public List<Action<CombatInfo>> getOnDamaged() {
		return onDamaged;
	}

	public void setOnDamaged(List<Action<CombatInfo>> onDamaged) {
		this.onDamaged = onDamaged;
	}

	public Spawner getOrigin() {
		return origin;
	}

	public void setOrigin(Spawner origin) {
		this.origin = origin;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = chance;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public float getGoldChance() {
		return goldChance;
	}

	public void setGoldChance(float goldChance) {
		this.goldChance = goldChance;
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

    public Map<EnumCraftingMaterial, Chance<IntPoss>> getDrops() {
        return drops;
    }

    public void setDrops(Map<EnumCraftingMaterial, Chance<IntPoss>> drops) {
        this.drops = drops;
    }
}
