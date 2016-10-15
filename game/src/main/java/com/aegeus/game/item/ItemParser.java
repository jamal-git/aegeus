package com.aegeus.game.item;

import org.bukkit.Material;

public class ItemParser {

	public static Item parseItem(Item item, String... args) {
		for(int i = 1; i < args.length; i++) {
			String[] pair = args[i].split("=");
			String key = pair[0];
			String value = pair[1];
			switch(key.toLowerCase()) {
				case "name":
					try {
						item.setName(value.replace("__", " ")); 
						break;
					} catch (Exception e) { break; }
				case "lore":
					try {
						for(String line : value.split("||"))
							item.addLore(line.replace("__", " "));
						break;
					} catch (Exception e) { break; }
				case "material":
					try {
						item.setMaterial(Material.getMaterial(value));
					} catch (Exception e) { break; }
				default: break;
			}
		}
		return item;
	}
	
	public static Weapon parseWeapon(Weapon weapon, String... args) {
		weapon = (Weapon) parseItem(weapon, args);
		for(int i = 1; i < args.length; i++) {
			String[] pair = args[i].split("=");
			String key = pair[0];
			String value = pair[1];
			switch(key.toLowerCase()) {
				case "tier":
					try {
						int tier = Integer.parseInt(value);
						weapon.setTier(tier);
						break;
					} catch (Exception e) { break; }
				case "dmg":
					try {
						String[] vals = value.split(";");
						int minDmg = Integer.parseInt(vals[0]);
						int maxDmg = Integer.parseInt(vals[1]);
						weapon.setDmg(minDmg, maxDmg);
						break;
					} catch (Exception e) { break; }
				case "firedmg":
					try {
						int fireDmg = Integer.parseInt(value);
						weapon.setFireDmg(fireDmg);
						break;
					} catch (Exception e) { break; }
				case "icedmg":
					try {
						int iceDmg = Integer.parseInt(value);
						weapon.setIceDmg(iceDmg);
						break;
					} catch (Exception e) { break; }
				case "lifesteal":
					try {
						double lifeSteal = Double.parseDouble(value);
						weapon.setLifeSteal(lifeSteal);
						break;
					} catch (Exception e) { break; }
				default: break;
			}
		}
		return weapon;
	}
	
	public static Armor parseArmor(Armor armor, String... args) {
		armor = (Armor) parseItem(armor, args);
		for(int i = 1; i < args.length; i++) {
			String[] pair = args[i].split("=");
			String key = pair[0];
			String value = pair[1];
			switch(key.toLowerCase()) {
				case "tier":
					try {
						int tier = Integer.parseInt(value);
						armor.setTier(tier);
						break;
					} catch (Exception e) { break; }
				case "hp":
					try {
						int hp = Integer.parseInt(value);
						armor.setHp(hp);
						break;
					} catch (Exception e) { break; }
				case "hpregen":
					try {
						int hpRegen = Integer.parseInt(value);
						armor.setHpRegen(hpRegen);
						break;
					} catch (Exception e) { break; }
				default: break;
			}
		}
		return armor;
	}
	
}
