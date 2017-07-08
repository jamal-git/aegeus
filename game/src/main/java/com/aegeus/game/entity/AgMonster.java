package com.aegeus.game.entity;

import com.aegeus.game.item.tool.Rune;
import com.aegeus.game.util.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class AgMonster extends AgEntity {
	private String name;
	private List<Condition<EntityDamageEvent>> hitConds = new ArrayList<>();
	private List<Condition<EntityDeathEvent>> deathConds = new ArrayList<>();
	private List<Rune> runes = new ArrayList<>();
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

	public AgMonster(AgMonster other) {
		super(other);
		this.name = other.name;
		this.hitConds = other.hitConds;
		this.deathConds = other.deathConds;
		this.runes = other.runes;
		this.tier = other.tier;
		this.chance = other.chance;
		this.gold = other.gold;
		this.forcedHp = other.forcedHp;
		this.hpMultiplier = other.hpMultiplier;
		this.dmgMultiplier = other.dmgMultiplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Condition<EntityDamageEvent>> getHitConds() {
		return hitConds;
	}

	public void setHitConds(List<Condition<EntityDamageEvent>> hitConds) {
		this.hitConds = hitConds;
	}

	public List<Condition<EntityDeathEvent>> getDeathConds() {
		return deathConds;
	}

	public void setDeathConds(List<Condition<EntityDeathEvent>> deathConds) {
		this.deathConds = deathConds;
	}

	public List<Rune> getRunes() {
		return runes;
	}

	public void setRunes(List<Rune> runes) {
		this.runes = runes;
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
}
