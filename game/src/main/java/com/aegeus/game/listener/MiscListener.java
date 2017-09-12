package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.util.InventoryBuilder;
import com.aegeus.game.util.InventoryMenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.HorseInventory;

/**
 * Created by Silvre on 7/14/2017.
 */
public class MiscListener implements Listener {
    private final Aegeus parent;
    public MiscListener(Aegeus p)   {
        parent = p;
    }

    /**
     * Natural mob spawning is disabled.  Nothing can spawn unless it is from a plugin.
     * Sorry minecraft.
     * @param e
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobSpawn(CreatureSpawnEvent e)    {
        if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) e.setCancelled(true);
    }

    @EventHandler
    public void onDismount(VehicleExitEvent e)   {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Aegeus.getInstance(), () ->   {
            if(e.getVehicle() instanceof Horse) {
                e.getVehicle().teleport(e.getVehicle().getLocation().add(0, -1000, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                ((Horse) e.getVehicle()).setHealth(0);
            }
        }, 1L);
    }

    @EventHandler
    public void onHorseClick(InventoryClickEvent e) {
        if(e.getClickedInventory() instanceof HorseInventory) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null || e.getClickedInventory().getType() != InventoryType.CHEST) return;
        InventoryBuilder builder = null;
        if((builder = InventoryMenuManager.getBuilderFromInventory(e.getClickedInventory())) != null && builder.getInventory().getItem(e.getSlot()) != null)   {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            InventoryMenuManager.removeInventory(builder);
            builder.click(e.getSlot(), e);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getInventory().getType() != InventoryType.CHEST) return;
        InventoryBuilder b;
        if((b = InventoryMenuManager.getBuilderFromInventory(e.getInventory())) != null)    {
            InventoryMenuManager.removeInventory(b);
        }
    }
}
