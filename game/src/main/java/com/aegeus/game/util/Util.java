package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.stats.impl.Tier;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Util {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	public static int rInt(int max) {
		return random.nextInt(max);
	}

	public static int rInt(int min, int max) {
		return random.nextInt(min, max);
	}

	public static float rFloat() {
		return random.nextFloat();
	}

	public static float rFloat(float max) {
		return random.nextFloat() * max;
	}

	public static float rFloat(float min, float max) {
		return (random.nextFloat() * max) + min;
	}

	public static double rDouble() {
		return random.nextDouble();
	}

	public static boolean rBool() {
		return random.nextBoolean();
	}

	public static float rarity(float f) {

		/*
		0 - 72: 33%
		73 - 92: 27%
		93 - 98: 23%
		99 - 100: 17%
		 */

		if (f < 0.73) return Util.rFloat() * 0.33f;
		else if (f < 0.93) return (Util.rFloat() * 0.27f) + 0.33f;
		else if (f < 0.99) return (Util.rFloat() * 0.23f) + 0.6f;
		else return (Util.rFloat() * 0.17f) + 0.83f;
	}

	public static String colorCodes(String s) {
		return colorCodes(s, '&');
	}

	public static String colorCodes(String s, char c) {
		return s == null ? "" : ChatColor.translateAlternateColorCodes(c, s);
	}

	public static List<String> colorCodes(String... s) {
		List<String> arr = new ArrayList<>();
		for (String value : s) {
			arr.add(colorCodes(value, '&'));
		}
		return arr;
	}

	public static long calcMaxXP(int level, int tier) {
		return Math.round(Math.pow(386 * level, 1.17) * (tier * 0.75));
	}

	public static void updateStats(LivingEntity entity) {
		int hp = 0;
		int hpRegen = 0;
		float physRes = 0;
		float magRes = 0;
		float block = 0;
		float dodge = 0;
		float reflect = 0;

		AgLiving info = Aegeus.getInstance().getLiving(entity);

		for (ItemStack i : entity.getEquipment().getArmorContents()) {
			if (i != null && !i.getType().equals(Material.AIR)) {
				Armor armor = new Armor(i);
				hp += armor.getHp();
				hpRegen += armor.getHpRegen();
				physRes += armor.getPhysRes();
				magRes += armor.getMagRes();
				block += armor.getBlock();
				dodge += armor.getDodge();
				reflect += armor.getReflect();
			}
		}

		if (entity instanceof Player) {
			AgPlayer pInfo = Aegeus.getInstance().getPlayer((Player) entity);
			hp += 100;
			if (pInfo.getLegion() != null)
				switch (pInfo.getLegion()) {
					case FEROCIOUS: // Counter: Cryptic
						// strength += (strength * 0.2);
						break;
					case NIMBLE: // Counter: Divine
						// dexterity += (dexterity * 0.2);
						break;
					case CRYPTIC: // Counter: Nimble
						// intellect += (intellect * 0.2);
						break;
					case DIVINE: // Counter: Ferocious
						// vitality += (vitality * 0.2);
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
		info.setPhysRes(physRes);
		info.setMagRes(magRes);
		info.setBlock(block);
		info.setDodge(dodge);
		info.setReflect(reflect);

		if (entity instanceof Player) updateDisplay((Player) entity);
	}

	public static void updateDisplay(Player player) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);

		if (info.getHpBar() == null) {
			// Create a new Hp BossBar for this player
			info.setHpBar(Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SEGMENTED_10));
			info.getHpBar().addPlayer(player);
			info.getHpBar().setVisible(true);
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
		info.getHpBar().setProgress(player.getHealth() / player.getMaxHealth());
		info.getHpBar().setTitle(Util.colorCodes(
				"&a" + Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()) + " &lHP"));

		// Update Party Glow
		if (info.getParty() != null) info.getParty().update(player);
	}

	public static void heal(LivingEntity entity, double amount) {
		amount = Math.max(1, amount);
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
		AgLiving info = Aegeus.getInstance().getLiving(victim);
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

	public static void setBiteTime(FishHook hook, int time) {
		try {
			EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();
			Field fishCatchTime = EntityFishingHook.class.getDeclaredField("aw");
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
		Arrays.stream(others).filter(l -> l != null && !l.isEmpty()).forEach(t::addAll);
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

	public static List<Player> getPlayersInRadius(Location center, double rx, double ry, double rz) {
		return center.getWorld().getNearbyEntities(center, rx, ry, rz).stream()
				.filter(e -> e instanceof Player).map(e -> (Player) e).collect(Collectors.toList());
	}

	public static void setSpeed(Entity e, double speed) {
		((EntityLiving) ((CraftEntity) e).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
	}

	public static boolean isSword(Material material) {
		return material.toString().contains("_SWORD");
	}

	public static boolean isAxe(Material material) {
		return material.toString().contains("_AXE");
	}

	public static boolean isSpade(Material material) {
		return material.toString().contains("_SPADE");
	}

	public static boolean isHoe(Material material) {
		return material.toString().contains("_HOE");
	}

	public static boolean isHelmet(Material material) {
		return material.toString().contains("_HELMET");
	}

	public static boolean isChestplate(Material material) {
		return material.toString().contains("_CHESTPLATE");
	}

	public static boolean isLeggings(Material material) {
		return material.toString().contains("_LEGGINGS");
	}

	public static boolean isBoots(Material material) {
		return material.toString().contains("_BOOTS");
	}

	public static String generateName(Weapon weapon) {
		List<String> prefix = new ArrayList<>();
		List<String> suffix = new ArrayList<>();
		String name = "Custom Weapon";

		Tier t = Tier.get(weapon.getTier());
		String color = t.getColor();
		Material m = weapon.getMaterial();

		if (isSword(m)) name = t.getName(Tier.SWORD_MED);
		else if (isAxe(m)) name = t.getName(Tier.AXE_MED);
		else if (m == Material.BOW) name = t.getName(Tier.BOW_MED);

		return color + (prefix.isEmpty() ? "" : String.join(" ", prefix) + " ")
				+ name + (suffix.isEmpty() ? "" : " of " + String.join(" ", suffix));
	}

	public static String generateName(Armor armor) {
		List<String> prefix = new ArrayList<>();
		List<String> suffix = new ArrayList<>();
		String name = "Custom Armor";

		Tier t = Tier.get(armor.getTier());
		String color = t.getColor();
		Material m = armor.getMaterial();

		if (armor.getHpRegen() > 0)
			prefix.add("Mending");
		if (armor.getDodge() > 0)
			prefix.add("Agile");

		if (isHelmet(m)) name = t.getName() + " Helmet";
		else if (isChestplate(m)) name = t.getName() + " Chestplate";
		else if (isLeggings(m)) name = t.getName() + " Leggings";
		else if (isBoots(m)) name = t.getName() + " Boots";

		return color + (prefix.isEmpty() ? "" : String.join(" ", prefix) + " ")
				+ name + (suffix.isEmpty() ? "" : " of " + String.join(" ", suffix));
	}

	public static ItemStack getCraftingCompendium() {
		ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Util.colorCodes("&dCrafting Compendium"));
		meta.setLore(Arrays.asList(Util.colorCodes("&7Store all of your crafting items"), Util.colorCodes("&7in one convenient item.")));
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack getGoBack() {
		ItemStack stack = new ItemStack(Material.BARRIER);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Util.colorCodes("&cGo Back"));
		stack.setItemMeta(meta);
		return stack;
	}

	public static void setDisplayName(ItemStack stack, String name) {
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		stack.setItemMeta(meta);
	}

	public static Tier findTier(Object[] args) {
		if (args.length > 0) {
			if (args[0] instanceof Tier)
				return (Tier) args[0];
			else if (args[0] instanceof Integer)
				return Tier.get((int) args[0]);
			else if (args[0] instanceof String)
				return Tier.get(Integer.parseInt((String) args[0]));
		}
		return Tier.get(0);
	}

	public static void sendActionbar(Player p, String message) {
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
				Util.colorCodes(message) + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	}
}