package com.aegeus.game.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Created by Silvre on 7/1/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public enum ProfessionTier   {
    BASIC("Basic Pickaxe", "Basic Fishing Rod", Material.WOOD_PICKAXE, ChatColor.RED),
    IMPROVED("Improved Pickaxe", "Improved Fishing Rod", Material.STONE_PICKAXE, ChatColor.GREEN),
    REINFORCED("Reinforced Pickaxe", "Reinforced Fishing Rod", Material.IRON_PICKAXE, ChatColor.AQUA),
    ELITE("Elite Pickaxe", "Elite Fishing Rod", Material.DIAMOND_PICKAXE, ChatColor.LIGHT_PURPLE),
    ULTIMATE("Ultimate Pickaxe", "Ultimate Fishing Rod", Material.GOLD_PICKAXE, ChatColor.YELLOW),
    TRANSCENDENT("Transcendent Pickaxe", "Transcendent Fishing Rod", Material.GOLD_PICKAXE, ChatColor.YELLOW);

    private static final ProfessionTier[] vals = values();

    private final Material pickMat;
    private final ChatColor color;
    private final String pickName;
    private final String rodName;

    ProfessionTier(String pickName, String rodName, Material pickMat, ChatColor color) {
        this.pickName = pickName;
        this.rodName = rodName;
        this.pickMat = pickMat;
        this.color = color;
    }

    public static ProfessionTier getTierByLevel(int level)  {
        if(level >= 100)    {
            return TRANSCENDENT;
        }
        if(level >= 80) {
            return ULTIMATE;
        }
        if(level >= 60) {
            return ELITE;
        }
        if(level >= 40) {
            return REINFORCED;
        }
        if(level >= 20) {
            return IMPROVED;
        }
        return BASIC;
    }

    public ProfessionTier next()   {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public ChatColor getColor()  {
        return color;
    }

    public String getPickaxeName()  {
        return pickName;
    }

    public Material getPickaxeMaterial()   {
        return pickMat;
    }

    public String getRodName()  {
        return rodName;
    }

    public Material getRodMaterial()    {
        return Material.FISHING_ROD;
    }
}
