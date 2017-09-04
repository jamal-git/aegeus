package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.EnumMaterialRarity;
import com.aegeus.game.util.InventoryBuilder;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Team Tower Defense on 9/1/2017.
 */
public class CraftingCompendiumListener implements Listener {

    private Aegeus parent;

    public CraftingCompendiumListener(Aegeus parent)    {
        this.parent = parent;
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)    {
            ItemStack stack = e.getItem();
            if(stack.isSimilar(Util.getCraftingCompendium()))  {
                InventoryBuilder b = new InventoryBuilder(e.getPlayer(), 9, "Crafting Compendium");
                ItemStack common = new ItemStack(Material.STAINED_GLASS, 1, (byte) 0);
                Util.setDisplayName(common, EnumMaterialRarity.COMMON.getNameAndColor());
                b.setItem(0, common);
                ItemStack uncommon = new ItemStack(Material.STAINED_GLASS, 1, (byte) 5);
                Util.setDisplayName(uncommon, EnumMaterialRarity.UNCOMMON.getNameAndColor());
                b.setItem(1, uncommon);
                ItemStack rare = new ItemStack(Material.STAINED_GLASS, 1, (byte) 3);
                Util.setDisplayName(rare, EnumMaterialRarity.RARE.getNameAndColor());
                b.setItem(2, rare);
                ItemStack arcane = new ItemStack(Material.STAINED_GLASS, 1, (byte) 2);
                Util.setDisplayName(arcane, EnumMaterialRarity.ARCANE.getNameAndColor());
                b.setItem(3, arcane);
                ItemStack unique = new ItemStack(Material.STAINED_GLASS, 1, (byte) 1);
                Util.setDisplayName(unique, EnumMaterialRarity.UNIQUE.getNameAndColor());
                b.setItem(4, unique);
                ItemStack divine = new ItemStack(Material.STAINED_GLASS, 1, (byte) 9);
                Util.setDisplayName(divine, EnumMaterialRarity.DIVINE.getNameAndColor());
                b.setItem(5, divine);
                ItemStack celestial = new ItemStack(Material.STAINED_GLASS, 1, (byte) 14);
                Util.setDisplayName(celestial, EnumMaterialRarity.CELESTIAL.getNameAndColor());
                b.setItem(6, celestial);
                ItemStack mythical = new ItemStack(Material.STAINED_GLASS, 1, (byte) 11);
                Util.setDisplayName(mythical, EnumMaterialRarity.MYTHICAL.getNameAndColor());
                b.setItem(7, mythical);
                ItemStack legendary = new ItemStack(Material.STAINED_GLASS, 1, (byte) 4);
                Util.setDisplayName(legendary, EnumMaterialRarity.LEGENDARY.getNameAndColor());
                b.setItem(8, legendary);
                b.show(e.getPlayer());
            }
        }
    }
}
