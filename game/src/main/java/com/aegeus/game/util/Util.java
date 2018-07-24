package com.aegeus.game.util;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
	private static final Random random = new Random();

	@SafeVarargs
	public static <T> List<T> union(List<T>... lists) {
		return Stream.of(lists).flatMap(List::stream).collect(Collectors.toList());
	}

	public static int clamp(int val, int min, int max) {
		return Math.min(max, Math.max(min, val));
	}

	public static float clamp(float val, float min, float max) {
		return Math.min(max, Math.max(min, val));
	}

	public static int randInt(int max) {
		return random.nextInt(max);
	}

	public static int randInt(int min, int max) {
		return min + random.nextInt((max + 1) - min);
	}

	public static float randFloat() {
		return random.nextFloat();
	}

	public static boolean randBool() {
		return random.nextBoolean();
	}

	public static float rarity(float f) {

		/*
		Common: 0 - 72: 36%
		Uncommon: 73 - 92: 27%
		Rare: 93 - 98: 23%
		Unique: 99 - 100: 14%
		 */

		if (f <= 0.72) return Util.randFloat() * 0.36f;
		else if (f <= 0.92) return (Util.randFloat() * 0.27f) + 0.36f;
		else if (f <= 0.98) return (Util.randFloat() * 0.23f) + 0.63f;
		else return (Util.randFloat() * 0.14f) + 0.86f;
	}

	public static String colorCodes(String s) {
		return s == null ? "" : ChatColor.translateAlternateColorCodes('&', s);
	}

	public static String[] colorCodes(String... strings) {
		return Arrays.stream(strings).map(Util::colorCodes).toArray(String[]::new);
	}

	public static List<Player> getNearbyPlayers(Location loc, double radius) {
		return getNearbyPlayers(loc, radius, radius, radius);
	}

	public static List<Player> getNearbyPlayers(Location loc, double rx, double ry, double rz) {
		return loc.getWorld().getNearbyEntities(loc, rx, ry, rz).stream()
				.filter(e -> e instanceof Player)
				.map(e -> (Player) e).collect(Collectors.toList());
	}

	public static void sendActionBar(Player p, String msg) {
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
				Util.colorCodes(msg) + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}
}