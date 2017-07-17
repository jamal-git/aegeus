package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.dungeon.Dungeon;
import com.aegeus.game.dungeon.DungeonManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Silvre on 7/16/2017.
 */
public class DungeonListener implements Listener {
    private Aegeus parent;

    public DungeonListener(Aegeus parent)   {
        this.parent = parent;
    }

    @EventHandler
    public void onKeyMove(InventoryMoveItemEvent e) {
        ItemStack item = e.getItem();
        ItemMeta meta = item.getItemMeta();
        if(e.getDestination().getType() == InventoryType.DROPPER && e.getDestination().getName().contains("Key") &&
                e.getSource().getType() == InventoryType.HOPPER && meta.getDisplayName().contains("Dungeon Key") && item.getType() == Material.TRIPWIRE_HOOK)    {
            Dungeon d = DungeonManager.getInstance().getDungeonFromWorld(e.getDestination().getLocation().getWorld());
            if(d != null) d.useKey();
        }
    }

}
