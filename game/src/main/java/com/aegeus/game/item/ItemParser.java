package com.aegeus.game.item;

import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import org.bukkit.Material;

public class ItemParser {
	public static AegeusItem parseItem(AegeusItem item, String... args) {
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
			try {
				String[] pair = args[i].split("=");
				String key = pair[0];
				String value = pair[1];
				switch(key.toLowerCase()) {
					case "dmg":
						String[] vals = value.split(";");
						int minDmg = Integer.parseInt(vals[0]);
						int maxDmg = Integer.parseInt(vals[1]);
						weapon.setDmg(minDmg, maxDmg);
						break;
					case "firedmg":
						int fireDmg = Integer.parseInt(value);
						weapon.setFireDmg(fireDmg);
						break;
					case "icedmg":
						int iceDmg = Integer.parseInt(value);
						weapon.setIceDmg(iceDmg);
						break;
					case "lifesteal":
						float lifeSteal = Float.parseFloat(value);
						weapon.setLifeSteal(lifeSteal);
						break;
					default: break;
				}
			} catch (Exception e) { }
		}
		return weapon;
	}
	
	public static Armor parseArmor(Armor armor, String... args) {
		armor = (Armor) parseItem(armor, args);
		for(int i = 1; i < args.length; i++) {
			try {
				String[] pair = args[i].split("=");
				String key = pair[0];
				String value = pair[1];
				switch(key.toLowerCase()) {
					case "hp":
						int hp = Integer.parseInt(value);
						armor.setHp(hp);
						break;
					case "hpregen":
						int hpRegen = Integer.parseInt(value);
						armor.setHpRegen(hpRegen);
						break;
					default: break;
				}
			} catch (Exception e) { }
		}
		return armor;
	}
	
}
