package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.entity.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {
	public EntityListener() {
		Bukkit.getScheduler().runTaskTimer(Aegeus.getInstance(), () -> Aegeus.getInstance().getEntities()
				.clean().stream()
				.filter(e -> e instanceof AgLiving)
				.forEach(e -> EntityUtils.updateEffects((AgLiving) e)), 20, 20);
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent evt) {
		Entity e = evt.getRightClicked();
		if (e instanceof LivingEntity) {
			AgLiving info = Aegeus.getInstance().getEntities().getLiving((LivingEntity) e);
			if (info.isDead()) for (ItemStack i : info.getDrops())
				e.getWorld().dropItem(e.getLocation(), i);
			Aegeus.getInstance().getEntities().remove(info);
		}
	}
}
