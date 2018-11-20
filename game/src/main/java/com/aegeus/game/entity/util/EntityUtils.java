package com.aegeus.game.entity.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.item.impl.ItemArmor;
import com.aegeus.game.item.util.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityUtils {
	public static String getName(Entity entity) {
		EntityBox entities = Aegeus.getInstance().getEntities();
		if (entities.contains(entity) && !entities.get(entity).getName().isEmpty())
			return entities.get(entity).getName();
		return entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
	}

	public static void update(LivingEntity entity) {
		// Run a tick later to wait for armor changes
		Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () -> {
			int hp = 0;

			// Load stats from armor
			for (ItemStack i : entity.getEquipment().getArmorContents()) {
				if (ItemManager.is(i, ItemArmor.IDENTITY)) {
					ItemArmor armor = (ItemArmor) ItemManager.get(i);
					hp += armor.getHP();
				}
			}

			// Bonus player HP
			if (entity instanceof Player) hp += 100;

			entity.setMaxHealth(hp);
		}, 1);
	}

	/*
	This method is run every second on all Aegeus entities
	to allow for custom fire, poison, shock, etc. potion effect behaviors.
	 */
	public static void updateEffects(AgLiving info) {
		LivingEntity entity = info.getEntity();
		if (info.isTrapped()) {
			Location loc = entity.getLocation().getBlock().getLocation();
			if (loc.distance(info.getTrap().getLoc()) > 1.1)
				entity.teleport(info.getTrap().getLoc());
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 10), true);
			info.subtractTrapTime();
		}
	}

}
