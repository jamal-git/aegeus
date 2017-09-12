package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.item.tool.CraftingCompendium;
import com.aegeus.game.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
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
            if(stack != null && stack.isSimilar(Util.getCraftingCompendium()))  {
                CraftingCompendium.getInventoryBuilder(e.getPlayer()).show(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        ItemStack stack = e.getItem().getItemStack();
        EnumCraftingMaterial item = null;
        for(EnumCraftingMaterial i : EnumCraftingMaterial.values()) {
            System.out.println(i.name() + " " + i.getItem().isSimilar(stack));
            if(i.getItem().isSimilar(stack)) {
                item = i;
                break;
            }
        }
        if(item != null) {
            AgPlayer player = Aegeus.getInstance().getPlayer(e.getPlayer());
            player.getCraftingCompendium().add(item, stack.getAmount());
            player.sendMessage(Util.colorCodes("&6+" + stack.getAmount() + " " + item.getName()));
            e.setCancelled(true);
            e.getItem().remove();
        }
    }
}