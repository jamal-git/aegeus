package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import net.minecraft.server.v1_9_R1.EntityFishingHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	public static float rarity(float a) {

		/*
        0 - 72: 33%
		73 - 92: 27%
		93 - 98: 23%
		99 - 100: 17%
		 */

        if (a < 0.73) return random.nextFloat() * 0.33f;
        else if (a < 0.93) return (random.nextFloat() * 0.27f) + 0.33f;
        else if (a < 0.99) return (random.nextFloat() * 0.23f) + 0.6f;
        else return (random.nextFloat() * 0.17f) + 0.83f;
	}

	public static String colorCodes(String s) {
		return colorCodes(s, '&');
	}

	public static String colorCodes(String s, char c) {
		return ChatColor.translateAlternateColorCodes(c, s);
	}

	public static long calcMaxXP(int level) {
		return 300 + Math.round(Math.pow(74 * level, 1.28));
	}

	public static int calcLevelBuff(int value, int level) {
		return Math.round((value * level) / 65);
	}

	public static void updateStats(LivingEntity entity) {
		int hp = 0;
		int hpRegen = 0;
		float energyRegen = 0;
		float physRes = 0;
		float magRes = 0;
		float block = 0;
		int strength = 0;
		int intellect = 0;
		int vitality = 0;
		int dexterity = 0;

		AgEntity info = Aegeus.getInstance().getEntity(entity);

		for (ItemStack i : entity.getEquipment().getArmorContents()) {
			if (i != null && !i.getType().equals(Material.AIR)) {
				Armor armor = new Armor(i);
				hp += armor.getHp();
				hpRegen = armor.getHpRegen();
				energyRegen += armor.getEnergyRegen();
				physRes += armor.getPhysRes();
				magRes += armor.getMagRes();
				block += armor.getBlock();
				strength += armor.getStrength();
				dexterity += armor.getDexterity();
				intellect += armor.getIntellect();
				vitality += armor.getVitality();
			}
		}

		if (entity instanceof Player) {
			AgPlayer pInfo = Aegeus.getInstance().getPlayer((Player) entity);
			hp += 100;
			if (pInfo.getLegion() != null)
				switch (pInfo.getLegion()) {
					case FEROCIOUS: // Counter: Cryptic
						strength += (strength * 0.2);
						// Critical strikes with a heavy melee weapon deal 20% bonus magic damage and heal for 20% of physical damage.
						break;
					case NIMBLE: // Counter: Divine
						dexterity += (dexterity * 0.2);
						// After 4 attacks with a light melee weapon, the next attack deals bonus magic damage, equal to 30% of total damage.
						break;
					case CRYPTIC: // Counter: Nimble
						intellect += (intellect * 0.2);
						// Attacking with a ranged weapon deals bonus magic damage equal to 0%-30% of damage dealt, increasing with distance.
						break;
					case DIVINE: // Counter: Ferocious
						vitality += (vitality * 0.2);
						// Blocking attacks creates stacks of divinity, capping at 4. Each stack blocks 25% of magic damage. At 4 stacks, 25% of magic damage is reflected.
						break;
					default:
						break;
				}
		}

		if (info instanceof AgMonster) {
			AgMonster mInfo = (AgMonster) info;
			hp = mInfo.getForcedHp() == -1 ? hp : mInfo.getForcedHp();
			hp *= mInfo.getHpMultiplier();
		}

		entity.setMaxHealth(Math.max(1, hp));
		info.setHpRegen(hpRegen);
		info.setEnergyRegen(energyRegen);
		info.setPhysRes(physRes);
		info.setMagRes(magRes);
		info.setBlock(block);
		info.setStrength(strength);
		info.setIntellect(intellect);
		info.setVitality(vitality);
		info.setDexterity(dexterity);

		if (entity instanceof Player) updateDisplay((Player) entity);
	}

	public static void updateDisplay(Player player) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		updateEnergy(player);
		if (info.getBossBarHp() == null) {
			// Create a new Hp BossBar for this player
			info.setBossBarHp(Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SEGMENTED_20));
			info.getBossBarHp().addPlayer(player);
			info.getBossBarHp().setVisible(true);
		}
		if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp") == null) {
			// Create an objective for BelowNameHP
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective("hp", "health");
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp")
					.setDisplayName(Util.colorCodes("&c&lHP&c"));
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		// Set BelowNameHP
		Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").getScore(player.getName())
				.setScore((int) Math.round(player.getHealth()));
		// Set HP BossBar
		info.getBossBarHp().setProgress(player.getHealth() / player.getMaxHealth());
		info.getBossBarHp().setTitle(Util.colorCodes(
				"&a" + Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()) + " &lHP"));
	}

	public static void updateEnergy(Player player) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		player.setLevel(Math.max(1, Math.round(info.getEnergy())));
		player.setExp(Math.max(0, (info.getEnergy() / 100)));
	}

	public static void heal(LivingEntity entity, double amount) {
		if (entity.getHealth() < entity.getMaxHealth()) {
			entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + amount));
			if (entity instanceof Player) {
				notifyHeal((Player) entity, amount);
				updateDisplay((Player) entity);
			}
		}
	}

	public static void notifyHeal(Player player, double health) {
		long hp = Math.round(Math.ceil(player.getHealth()));
		long maxHp = Math.round(Math.ceil(player.getMaxHealth()));
		long heal = Math.round(Math.ceil(health));
		player.sendMessage(Util.colorCodes("              &a+" + heal + " &lHP&7 [" + hp + " / " + maxHp + "]"));
	}

	public static void notifyAttack(Player attacker, LivingEntity victim, double damage) {
		AgEntity info = Aegeus.getInstance().getEntity(victim);
		String name = (info instanceof AgMonster) ? ((AgMonster) info).getName() : victim.getName();
		long hp = Math.round(Math.ceil(victim.getHealth()));
		long maxHp = Math.round(Math.ceil(victim.getMaxHealth()));
		long dmg = Math.round(Math.ceil(damage));
		attacker.sendMessage(Util.colorCodes("            &c" + dmg + " &lDMG&c >>&f " + name + "&7 [" + hp + " &7/ " + maxHp + "&7]"));
	}

	public static void notifyAttacked(Player victim, double damage) {
		long hp = Math.round(Math.ceil(victim.getHealth()));
		long maxHp = Math.round(Math.ceil(victim.getMaxHealth()));
		long dmg = Math.round(Math.ceil(damage));
		victim.sendMessage(Util.colorCodes("              &4-" + dmg + " &lHP&7 [" + hp + " / " + maxHp + "]"));
	}

	public static void notifyProfXp(Player player, int xp, int totalXp, int maxXp) {
		player.sendMessage(Util.colorCodes("              &e+" + xp + " &lXP&7 [" + totalXp + " / " + maxXp + "]"));
	}

	public static void notifyProfLevel(Player player, int level) {
		player.sendMessage(Util.colorCodes("              &6&l** LEVEL UP!&6 [&l" + level + "&6] &l**"));
	}

	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Math.cbrt(Math.pow((x1 - x2), 3) + Math.pow((y1 - y2), 3) + Math.pow((z1 - z2), 3));
	}

	public static double distance(Location loc1, Location loc2) {
		return distance(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
	}

	public static void setBiteTime(FishHook hook, int time) {
		try {
			net.minecraft.server.v1_9_R1.EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();
			Field fishCatchTime = net.minecraft.server.v1_9_R1.EntityFishingHook.class.getDeclaredField("aw");
			fishCatchTime.setAccessible(true);
			fishCatchTime.setInt(hookCopy, time);
			fishCatchTime.setAccessible(false);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static String getName(ItemStack item) {
		return item == null || item.getType().equals(Material.AIR)
				? "Nothing" : CraftItemStack.asNMSCopy(item).getName();
	}

	public static String getName(Material material) {
		return material == null || material.equals(Material.AIR)
				? "Nothing" : CraftItemStack.asNMSCopy(new ItemStack(material)).getName();
	}

	public static List<String> getLore(ItemStack item) {
		return item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null
				? new ArrayList<>() : item.getItemMeta().getLore();
	}

	@SafeVarargs
	public static <T> List<T> union(List<T> t, List<T>... others) {
		Arrays.stream(others).forEach(t::addAll);
		return t;
	}

	public static Entity getTargetEntity(Entity entity) {
		return getTarget(entity, entity.getWorld().getEntities());
	}

	public static <T extends Entity> T getTarget(Entity entity, Iterable<T> entities) {
		T target = null;
		double threshold = 1;
		for (T other : entities) {
			Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
			if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
				if (target == null || target.getLocation().distanceSquared(entity.getLocation()) > other.getLocation().distanceSquared(entity.getLocation()))
					target = other;
			}
		}
		return target;
	}

    public static boolean isSword(Material material) {
        switch (material) {
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case DIAMOND_SWORD:
            case GOLD_SWORD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAxe(Material material) {
        switch (material) {
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case DIAMOND_AXE:
            case GOLD_AXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSpade(Material material) {
        switch (material) {
            case WOOD_SPADE:
            case STONE_SPADE:
            case IRON_SPADE:
            case DIAMOND_SPADE:
            case GOLD_SPADE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isHoe(Material material) {
        switch (material) {
            case WOOD_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case DIAMOND_HOE:
            case GOLD_HOE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isHelmet(Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case DIAMOND_HELMET:
            case GOLD_HELMET:
                return true;
            default:
                return false;
        }
    }

    public static boolean isChestplate(Material material) {
        switch (material) {
            case LEATHER_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLeggings(Material material) {
        switch (material) {
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBoots(Material material) {
        switch (material) {
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
                return true;
            default:
                return false;
        }
    }

    public static String generateName(Weapon weapon) {
        if (isSword(weapon.getMaterial())) switch (weapon.getTier()) {
            case 1:
                return Util.colorCodes("&fWood Sword");
            case 2:
                return Util.colorCodes("&aStone Sword");
            case 3:
                return Util.colorCodes("&bMagic Sword");
            case 4:
                return Util.colorCodes("&dAncient Sword");
            case 5:
                return Util.colorCodes("&eLegendary Sword");
            default:
                return Util.colorCodes("&fCustom Sword");
        }
        else if (isAxe(weapon.getMaterial())) switch (weapon.getTier()) {
            case 1:
                return Util.colorCodes("&fWood Axe");
            case 2:
                return Util.colorCodes("&aStone Axe");
            case 3:
                return Util.colorCodes("&bMagic Axe");
            case 4:
                return Util.colorCodes("&dAncient Axe");
            case 5:
                return Util.colorCodes("&eLegendary Axe");
            default:
                return Util.colorCodes("&fCustom Axe");
        }
        else if (isSpade(weapon.getMaterial())) switch (weapon.getTier()) {
            case 1:
                return Util.colorCodes("&fBasic Polearm");
            case 2:
                return Util.colorCodes("&aAdvanced Polearm");
            case 3:
                return Util.colorCodes("&bMagic Polearm");
            case 4:
                return Util.colorCodes("&dAncient Polearm");
            case 5:
                return Util.colorCodes("&eLegendary Polearm");
            default:
                return Util.colorCodes("&fCustom Polearm");
        }
        else if (isHoe(weapon.getMaterial())) switch (weapon.getTier()) {
            case 1:
                return Util.colorCodes("&fBasic Staff");
            case 2:
                return Util.colorCodes("&aAdvanced Staff");
            case 3:
                return Util.colorCodes("&bMagic Staff");
            case 4:
                return Util.colorCodes("&dAncient Staff");
            case 5:
                return Util.colorCodes("&eLegendary Staff");
            default:
                return Util.colorCodes("&fCustom Staff");
        }
        else if (weapon.getMaterial().equals(Material.BOW)) switch (weapon.getTier()) {
            case 1:
                return Util.colorCodes("&fBasic Bow");
            case 2:
                return Util.colorCodes("&aAdvanced Bow");
            case 3:
                return Util.colorCodes("&bMagic Bow");
            case 4:
                return Util.colorCodes("&dAncient Bow");
            case 5:
                return Util.colorCodes("&eLegendary Bow");
            default:
                return Util.colorCodes("&fCustom Bow");
        }
        else return Util.colorCodes("&fCustom Item");
    }

    public static String generateName(Armor armor) {
        if (isHelmet(armor.getMaterial())) switch (armor.getTier()) {
            case 1:
                return Util.colorCodes("&fLeather Helmet");
            case 2:
                return Util.colorCodes("&aChainmail Helmet");
            case 3:
                return Util.colorCodes("&bMagic Helmet");
            case 4:
                return Util.colorCodes("&dAncient Helmet");
            case 5:
                return Util.colorCodes("&eLegendary Helmet");
            default:
                return Util.colorCodes("&fCustom Helmet");
        }
        else if (isChestplate(armor.getMaterial())) switch (armor.getTier()) {
            case 1:
                return Util.colorCodes("&fLeather Chestplate");
            case 2:
                return Util.colorCodes("&aChainmail Chestplate");
            case 3:
                return Util.colorCodes("&bMagic Chestplate");
            case 4:
                return Util.colorCodes("&dAncient Chestplate");
            case 5:
                return Util.colorCodes("&eLegendary Chestplate");
            default:
                return Util.colorCodes("&fCustom Chestplate");
        }
        else if (isLeggings(armor.getMaterial())) switch (armor.getTier()) {
            case 1:
                return Util.colorCodes("&fLeather Leggings");
            case 2:
                return Util.colorCodes("&aChainmail Leggings");
            case 3:
                return Util.colorCodes("&bMagic Leggings");
            case 4:
                return Util.colorCodes("&dAncient Leggings");
            case 5:
                return Util.colorCodes("&eLegendary Leggings");
            default:
                return Util.colorCodes("&fCustom Leggings");
        }
        else if (isBoots(armor.getMaterial())) switch (armor.getTier()) {
            case 1:
                return Util.colorCodes("&fLeather Boots");
            case 2:
                return Util.colorCodes("&aChainmail Boots");
            case 3:
                return Util.colorCodes("&bMagic Boots");
            case 4:
                return Util.colorCodes("&dAncient Boots");
            case 5:
                return Util.colorCodes("&eLegendary Boots");
            default:
                return Util.colorCodes("&fCustom Boots");
        }
        else return Util.colorCodes("&fCustom Item");
    }
}
