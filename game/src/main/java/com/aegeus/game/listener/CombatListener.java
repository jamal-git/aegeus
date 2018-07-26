package com.aegeus.game.listener;

import com.aegeus.game.entity.util.EntityUtils;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class CombatListener implements Listener {
	private static final String indent = "            ";

	@EventHandler
	private void onDamage(EntityDamageEvent ede) {
		Entity entity = ede.getEntity();
		int damage = (int) Math.ceil(ede.getDamage());

		// Check for entity vs. entity
		if (ede instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) ede;
			if (entity instanceof LivingEntity && edbee.getDamager() instanceof LivingEntity) {
				// Cancel the event, use custom damage when dealing with E vs E
				ede.setCancelled(true);
				LivingEntity victim = (LivingEntity) edbee.getEntity();
				LivingEntity attacker = (LivingEntity) edbee.getDamager();

				// Shows a damage effect
				victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation(),
						110, 0.25, 0.8, 0.25, new MaterialData(Material.REDSTONE_WIRE));
				// Apply the damage and mimic what the event changes
				victim.damage(damage);
				victim.setLastDamage(damage);
				victim.setLastDamageCause(edbee);
				// Set the victim's no damage ticks
				victim.setMaximumNoDamageTicks(9);
				victim.setNoDamageTicks(0);

				// Apply knockback to the victim
				Vector vec = attacker.getLocation().getDirection().multiply(0.09);
				if (victim.isOnGround()) vec.setY(victim.getVelocity().getY() + 0.4);
				victim.setVelocity(vec);

				// Send attacker game text to attacker
				if (attacker instanceof Player)
					attacker.sendMessage(attacker((Player) attacker, victim, damage));
			}
		}

		// Send victim game text to victim
		if (entity instanceof Player)
			entity.sendMessage(victim((Player) entity, damage));
	}

	private String victim(Player victim, int damage) {
		return Util.colorCodes(indent + "&c-" + damage + " &lHP" + "&8 [&7"
				+ (int) Math.ceil(victim.getHealth()) + "&8]");
	}

	private String attacker(Player attacker, LivingEntity victim, int damage) {
		return Util.colorCodes(indent + "&e" + damage + " &lDMG&8 >> "
				+ "&f" + EntityUtils.getName(victim) + "&8 [&7"
				+ (int) Math.ceil(victim.getHealth()) + "&8]");
	}
}
