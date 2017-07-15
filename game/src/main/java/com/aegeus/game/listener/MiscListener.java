package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

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
}
