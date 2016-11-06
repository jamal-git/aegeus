package com.aegeus.game.stats;

import org.bukkit.Material;

import java.util.Random;

public class StatsBasic implements Stats {

	public static final String NAME = "Basic";
	public static final float CHANCE = 0.16f;
	private final Random RANDOM = new Random();
	private final StatsContainer CONTAINER = new StatsContainer();

	public StatsBasic() {
		CONTAINER.addHelmets(
				CONTAINER.new ArmorPossibility(
						Material.LEATHER_HELMET, 1,
						5, 40
				));
		CONTAINER.addChestplates(
				CONTAINER.new ArmorPossibility(
						Material.LEATHER_CHESTPLATE, 1,
						5, 40
				));
		CONTAINER.addLeggings(
				CONTAINER.new ArmorPossibility(
						Material.LEATHER_LEGGINGS, 1,
						5, 40
				));
		CONTAINER.addBoots(
				CONTAINER.new ArmorPossibility(
						Material.LEATHER_BOOTS, 1,
						5, 40
				));
		CONTAINER.addWeapons(
				CONTAINER.new WeaponPossibility(
						Material.WOOD_SWORD, 1,
						1, 25, 6
				));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public StatsContainer getContainer() {
		return CONTAINER;
	}

	@Override
	public boolean hasHelmet() {
		return true;
	}

	@Override
	public boolean hasChestplate() {
		return RANDOM.nextBoolean();
	}

	@Override
	public boolean hasLeggings() {
		return RANDOM.nextBoolean();
	}

	@Override
	public boolean hasBoots() {
		return RANDOM.nextBoolean();
	}

	@Override
	public boolean hasWeapon() {
		return true;
	}

	@Override
	public float getChance() {
		return CHANCE;
	}

}
