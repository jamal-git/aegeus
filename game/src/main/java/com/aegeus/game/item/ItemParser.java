package com.aegeus.game.item;

import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.item.tool.Weapon;
import org.bukkit.Material;

public class ItemParser {
	public static AgItem parseItem(AgItem item, String... args) {
		for (int i = 1; i < args.length; i++) {
			String[] pair = args[i].split("=");
			String key = pair[0];
			String value = pair[1];
			switch (key.toLowerCase()) {
				case "name":
					try {
						item.setName(value.replace("_", " "));
						break;
					} catch (Exception ignored) {
						break;
					}
				case "lore":
					try {
						for (String line : value.split("||"))
							item.addLore(line.replace("__", " "));
						break;
					} catch (Exception ignored) {
						break;
					}
				case "material":
					try {
						item.setMaterial(Material.getMaterial(value));
					} catch (Exception ignored) {
						break;
					}
				default:
					break;
			}
		}
		return item;
	}

	public static Weapon parseWeapon(Weapon weapon, String... args) {
		weapon = (Weapon) parseItem(weapon, args);
		for (int i = 1; i < args.length; i++) {
			try {
				String[] pair = args[i].split("=");
				String key = pair[0];
				String value = pair[1];
				switch (key.toLowerCase()) {
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
					default:
						break;
				}
			} catch (Exception ignored) {
			}
		}
		return weapon;
	}

	public static Armor parseArmor(Armor armor, String... args) {
		armor = (Armor) parseItem(armor, args);
		for (int i = 1; i < args.length; i++) {
			try {
				String[] pair = args[i].split("=");
				String key = pair[0];
				String value = pair[1];
				switch (key.toLowerCase()) {
					case "hp":
						int hp = Integer.parseInt(value);
						armor.setHp(hp);
						break;
					case "hpregen":
						int hpRegen = Integer.parseInt(value);
						armor.setHpRegen(hpRegen);
						break;
					default:
						break;
				}
			} catch (Exception ignored) {
			}
		}
		return armor;
	}

	public static Pickaxe parsePick(Pickaxe p, String... args)  {
	    p = (Pickaxe) parseItem(p, args);
        for(int i = 0; i < args.length; i++)    {
            try {
                String[] pair = args[i].split("=");
                String key = pair[0];
                String value = pair[1];
                switch(key.toLowerCase())   {
                    case "level":
                        p.setLevel(Integer.valueOf(value));
                    case "xp":
                        p.setXp(Integer.valueOf(value));
                        break;
                    case "miningsuccess":
                        p.setMiningSuccess(Float.valueOf(value));
                        break;
                    case "densefind":
                        p.setDenseFind(Float.valueOf(value));
                        break;
                    case "densemultiplier":
                        p.setDenseMultiplier(Integer.valueOf(value));
                        break;
                    case "doubleore":
                        p.setDoubleOre(Float.valueOf(value));
                        break;
                    case "tripleore":
                        p.setTripleOre(Float.valueOf(value));
                        break;
                    case "gemfind":
                        p.setGemFind(Float.valueOf(value));
                        break;
                    case "durability":
                        p.setDurability(Float.valueOf(value));
                }
            }
            catch(Exception fuckexceptions){
                //nope
            }
        }
        return p;
    }
}
