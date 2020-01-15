package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.ItemManager;
import com.aegeus.game.item.wrapper.ArmorItem;
import com.aegeus.game.util.EntityMap;
import com.aegeus.game.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Handles all player-related events.
 */
public class PlayerListener implements Listener {
    private final EntityMap entities = Aegeus.getInstance().getEntities();

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setHealthScale(20);
        if (!player.isDead())
            Util.delay(() -> entities.getPlayer(player).update());
    }

    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        Util.delay(() -> entities.getPlayer(event.getPlayer()).update());
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (ItemManager.id(event.getItem()).equals(ArmorItem.IDENTITY))
            Util.delay(() -> entities.getPlayer(event.getPlayer()).update());
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (ItemManager.id(event.getCursor()).equals(ArmorItem.IDENTITY)
                || ItemManager.id(event.getCurrentItem()).equals(ArmorItem.IDENTITY))
            Util.delay(() -> entities.getPlayer((Player) event.getWhoClicked()).update());
    }
}
