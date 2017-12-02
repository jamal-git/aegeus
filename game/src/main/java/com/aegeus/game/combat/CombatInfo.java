package com.aegeus.game.combat;

import com.aegeus.game.listener.CombatListener;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the necessary information of two entities combating each other.
 * An object of CombatInfo is created in {@link CombatListener}.
 */
public class CombatInfo {
	private final LivingEntity victim;
	private final LivingEntity attacker;
	private LivingEntity target;

	private double physDmg = 0;
	private double magDmg = 0;
	private double healing = 0;
	private double knockback = 0.17;

	private List<Runnable> effects = new ArrayList<>();
	private List<CombatSound> sounds = new ArrayList<>();

	public CombatInfo(LivingEntity victim, LivingEntity attacker) {
		this.victim = victim;
		this.attacker = attacker;
		target = victim;
	}

	public LivingEntity getVictim() {
		return victim;
	}

	public LivingEntity getAttacker() {
		return attacker;
	}

	public LivingEntity getTarget() {
		return target;
	}

	public void setTarget(LivingEntity target) {
		this.target = target;
	}

	public double getPhysDmg() {
		return physDmg;
	}

	public void setPhysDmg(double physDmg) {
		this.physDmg = physDmg;
	}

	public void addPhysDmg(double d) {
		setPhysDmg(getPhysDmg() + d);
	}

	public void multPhysDmg(double d) {
		setPhysDmg(Math.round(getPhysDmg() * d));
	}

	public double getMagDmg() {
		return magDmg;
	}

	public void setMagDmg(double magDmg) {
		this.magDmg = magDmg;
	}

	public void addMagDmg(double d) {
		setMagDmg(getMagDmg() + d);
	}

	public void multMagDmg(double d) {
		setMagDmg(Math.round(getMagDmg() * d));
	}

	public double getHealing() {
		return healing;
	}

	public void setHealing(double healing) {
		this.healing = healing;
	}

	public void addHealing(double d) {
		setHealing(getHealing() + d);
	}

	public double getKnockback() {
		return knockback;
	}

	public void setKnockback(double knockback) {
		this.knockback = knockback;
	}

	public void multKnockback(double d) {
		setKnockback(getKnockback() * d);
	}

	public List<Runnable> getEffects() {
		return effects;
	}

	public void addEffect(Runnable r) {
		getEffects().add(r);
	}

	public List<CombatSound> getSounds() {
		return sounds;
	}

	public void addSound(Location loc, Sound s, float vol, float pitch) {
		addSound(new CombatSound(loc, s, vol, pitch));
	}

	public void addSound(CombatSound s) {
		for (int i = 0; i < sounds.size(); i++) {
			CombatSound a = sounds.get(i);
			if (a.loc.equals(s.loc) && a.sound.equals(s.sound)) {
				sounds.set(i, s);
				return;
			}
		}

		sounds.add(s);
	}

	public class CombatSound {
		public final Location loc;
		public final Sound sound;
		public final float vol;
		public final float pitch;

		public CombatSound(Location loc, Sound sound, float vol, float pitch) {
			this.loc = loc;
			this.sound = sound;
			this.vol = vol;
			this.pitch = pitch;
		}
	}
}
