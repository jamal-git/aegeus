package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Random;

public class Util {
    public static final Random RANDOM = new Random();

    public static void delay(Runnable r) {
        Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), r, 1);
    }

    public static void heal(LivingEntity e) {
        e.setHealth(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
    }

    public static int limit(int val, int min, int max) {
        return (int) limit((float) val, min, max);
    }

    public static float limit(float val, float min, float max) {
        return Math.min(max, Math.max(min, val));
    }

    public static int nextInt(int min, int max) {
        return min + RANDOM.nextInt((max - min) + 1);
    }

    /**
     * Color a string using & symbols.
     *
     * @param s The string to color.
     * @return The colored string.
     */
    public static String colorCodes(String s) {
        return s == null ? "" : ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Checks whether or not a string is null or empty.
     *
     * @param s The string to check.
     * @return Whether or not the string is empty.
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Returns the correct name of an entity in order of priority.
     *
     * @return The entity's name.
     */
    public static String getName(Entity e) {
        AgEntity data = Aegeus.getInstance().getEntities().get(e);
        if (data != null && !Util.isEmpty(data.getGameName())) return Util.colorCodes(data.getGameName());
        else if (!Util.isEmpty(e.getCustomName())) return e.getCustomName();
        else return e.getName();
    }

    public static double getMaxHealth(LivingEntity e) {
        return e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }
}