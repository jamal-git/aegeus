package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.Armor;
import net.minecraft.server.v1_9_R1.EntityFishingHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	public static float rarity(float a) {

		/*
		0 - 62: 40%
		63 - 86: 27%
		87 - 96: 23%
		97 - 100: 10%
		 */

		if (a <= 0.62) return random.nextFloat() * 0.40f;
		else if (a <= 0.86) return (random.nextFloat() * 0.27f) + 0.40f;
		else if (a <= 0.96) return (random.nextFloat() * 0.23f) + 0.67f;
		else return (random.nextFloat() * 0.10f) + 0.90f;
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
		float defense = 0;
		float magicRes = 0;
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
				defense += armor.getDefense();
				magicRes += armor.getMagicRes();
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
		info.setDefense(defense);
		info.setMagicRes(magicRes);
		info.setBlock(block);
		info.setStrength(strength);
		info.setIntellect(intellect);
		info.setVitality(vitality);
		info.setDexterity(dexterity);

		if (entity instanceof Player) updateDisplay((Player) entity);
	}

	public static void updateDisplay(Player player) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
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
		// Set Energy Bar
		player.setTotalExperience(1);
		player.setExp(info.getEnergy() / 100);
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
		net.minecraft.server.v1_9_R1.EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();

		Field fishCatchTime = null;

		try {
			fishCatchTime = net.minecraft.server.v1_9_R1.EntityFishingHook.class.getDeclaredField("aw");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		fishCatchTime.setAccessible(true);

		try {
			fishCatchTime.setInt(hookCopy, time);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		fishCatchTime.setAccessible(false);
	}
}
