package com.aegeus.game;

import org.bukkit.event.Listener;

public class Mobs implements Listener {

//	private JavaPlugin parent;
//
//	public Mobs(JavaPlugin parent) {
//		this.parent = parent;
//	}
	
//	public static void create(JavaPlugin parent, World world, Location loc, String tier, String type) {
//		LivingEntity entity = (LivingEntity) world.spawnEntity(loc, getEntityType(tier, type));
//		if(entity!=null){
//			Random random = new Random();
//			entity.setCustomName(getName(tier, type));
//			entity.setCustomNameVisible(true);
//			entity.getEquipment().setHelmet(getArmor(tier, "helmet", random.nextFloat()));
//			entity.getEquipment().setChestplate(getArmor(tier, "chestplate", random.nextFloat()));
//			entity.getEquipment().setLeggings(getArmor(tier, "leggings", random.nextFloat()));
//			entity.getEquipment().setBoots(getArmor(tier, "boots", random.nextFloat()));
//			Statistics.updateStats(entity);
//			entity.setHealth(entity.getMaxHealth());
//		}
//	}
	
//	public static void create(JavaPlugin parent, World world, Location loc, String type) {
//		ItemStack helmet = new ItemStack(Material.AIR);
//		ItemStack chestplate = new ItemStack(Material.AIR);
//		ItemStack leggings = new ItemStack(Material.AIR);
//		ItemStack boots = new ItemStack(Material.AIR);
//		ItemStack weapon = new ItemStack(Material.AIR);
//		LivingEntity entity = null;
//		if (type.equalsIgnoreCase("t1bandit")) {
//			EntityType[] types = { EntityType.ZOMBIE, EntityType.SKELETON };
//			Random random = new Random();
//			entity = (LivingEntity) world.spawnEntity(loc, types[random.nextInt(types.length)]);
//			// Helmet (is always true)
//			if (true) {
//				helmet = new ItemStack(Material.LEATHER_HELMET);
//				ItemMeta meta = helmet.getItemMeta();
//				meta.setLore(getArmorValue(random.nextFloat(), "T1Helmet"));
//				helmet.setItemMeta(meta);
//			}
//			// Chestplate
//			if (random.nextInt(2) == 0) {
//				chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
//				ItemMeta meta = chestplate.getItemMeta();
//				meta.setLore(getArmorValue(random.nextFloat(), "T1Chestplate"));
//				chestplate.setItemMeta(meta);
//			}
//			// Leggings
//			if (random.nextInt(2) == 0) {
//				leggings = new ItemStack(Material.LEATHER_LEGGINGS);
//				ItemMeta meta = leggings.getItemMeta();
//				meta.setLore(getArmorValue(random.nextFloat(), "T1Leggings"));
//				leggings.setItemMeta(meta);
//			}
//			// Boots
//			if (random.nextInt(2) == 0) {
//				boots = new ItemStack(Material.LEATHER_BOOTS);
//				ItemMeta meta = boots.getItemMeta();
//				meta.setLore(getArmorValue(random.nextFloat(), "T1Boots"));
//				boots.setItemMeta(meta);
//			}
//			// Weapon
//			int randWeapon = random.nextInt(2);
//			ItemMeta wepmeta = weapon.getItemMeta();
//			if (randWeapon == 0) {
//				weapon = new ItemStack(Material.WOOD_SWORD);
//				wepmeta = weapon.getItemMeta();
//				wepmeta.setLore(getWeaponValue(random.nextFloat(), "T1Sword"));
//			} else if (randWeapon == 1) {
//				weapon = new ItemStack(Material.WOOD_AXE);
//				wepmeta = weapon.getItemMeta();
//				wepmeta.setLore(getWeaponValue(random.nextFloat(), "T1Axe"));
//			}
//			weapon.setItemMeta(wepmeta);
//		}
//		if (entity != null) {
//			entity.getEquipment().setHelmet(helmet);
//			entity.getEquipment().setChestplate(chestplate);
//			entity.getEquipment().setLeggings(leggings);
//			entity.getEquipment().setBoots(boots);
//			entity.getEquipment().setItemInMainHand(weapon);
//			Statistics.updateStats(parent, entity);
//			entity.setHealth(entity.getMaxHealth());
//		}
//	}
//
//	public static List<String> getArmorValue(float f, String tag) {
//		int hp = (int) MathHelper.easeInCirc(f, Float.parseFloat(armorMap.getProperty(tag + "_Min")),
//				Float.parseFloat(armorMap.getProperty(tag + "_Max")), 1);
//		LoreBuilder lore = new LoreBuilder();
//		lore.addBoldStat("HP", "+" + hp, ChatColor.RED);
//		return lore.buildLore();
//	}
//
//	public static List<String> getWeaponValue(float f, String tag) {
//		int min = (int) Float.parseFloat(weaponMap.getProperty(tag + "_Min"));
//		int max = (int) Float.parseFloat(weaponMap.getProperty(tag + "_Max"));
//		int range = (max - min) / 2;
//		int thismin = (int) MathHelper.easeInCirc(f, min, min + range, 1);
//		int thismax = (int) MathHelper.easeInCirc(f, min + range, max, 1);
//		LoreBuilder lore = new LoreBuilder();
//		lore.addBoldStat("DMG", thismin + " - " + thismax, ChatColor.RED);
//		return lore.buildLore();
//	}

}
