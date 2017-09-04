package com.aegeus.game.item;

import org.bukkit.ChatColor;

/**
 * Created by Silvre on 9/1/2017.
 */
public enum EnumMaterialRarity {
    COMMON(ChatColor.WHITE, "Common"),
    UNCOMMON(ChatColor.GREEN, "Uncommon"),
    RARE(ChatColor.BLUE, "Rare"),
    ARCANE(ChatColor.LIGHT_PURPLE,"Arcane"),
    UNIQUE(ChatColor.GOLD, "Unique"),
    DIVINE(ChatColor.AQUA, "Divine"),
    CELESTIAL(ChatColor.RED, "Celestial"),
    MYTHICAL(ChatColor.DARK_BLUE, "Mythical"),
    LEGENDARY(ChatColor.YELLOW, "Legendary");

    private ChatColor color;
    private String name;

    EnumMaterialRarity(ChatColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getNameAndColor() {
        return color + name;
    }
}
