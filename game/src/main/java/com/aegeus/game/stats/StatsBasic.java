package com.aegeus.game.stats;

import java.util.Random;

import org.bukkit.Material;

public class StatsBasic implements Stats {

	private static final String name = "Basic";
	private final Random random = new Random();
	private final StatsContainer container = new StatsContainer();
	
	@Override public String getName() { return name; }
	@Override public StatsContainer getContainer() { return container; }
	@Override public boolean hasHelmet() { return true; }
	@Override public boolean hasChestplate() { return random.nextBoolean(); }
	@Override public boolean hasLeggings() { return random.nextBoolean(); }
	@Override public boolean hasBoots() { return random.nextBoolean(); }
	@Override public boolean hasWeapon() { return true; }
	
	public StatsBasic() {
		container.addHelmets(
			container.new ArmorPossibility(
				Material.LEATHER_HELMET, 1,
				5, 40
		));
		container.addChestplates(
			container.new ArmorPossibility(
				Material.LEATHER_CHESTPLATE, 1,
				5, 40
		));
		container.addLeggings(
			container.new ArmorPossibility(
				Material.LEATHER_LEGGINGS, 1,
				5, 40
		));
		container.addBoots(
			container.new ArmorPossibility(
				Material.LEATHER_BOOTS, 1,
				5, 40
		));
		container.addWeapons(
			container.new WeaponPossibility(
				Material.WOOD_SWORD, 1,
				1, 25, 6
		));
	}
	
}
