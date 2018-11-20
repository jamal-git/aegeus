package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.entity.util.EntityBox;
import com.aegeus.game.entity.util.EntityUtils;
import com.aegeus.game.item.impl.ItemWeapon;
import com.aegeus.game.item.util.ItemManager;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class CombatListener implements Listener {
	private static final String indent = "            ";

	private final EntityBox entities = Aegeus.getInstance().getEntities();

	@EventHandler
	private void onDeath(EntityDeathEvent ede) {
		if (ede.getEntity() instanceof Player) return;
		AgLiving info = entities.getLiving(ede.getEntity());
		if (info.getDrops().isEmpty()) return;

		info.die();
		Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () ->
				Aegeus.getInstance().getEntities().remove(info), 600);
	}

	@EventHandler
	private void onDamage(EntityDamageEvent ede) {
		Entity entity = ede.getEntity();
		int damage = (int) Math.ceil(ede.getDamage());

		// Check for entity vs. entity
		if (ede instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) ede;
			if (entity instanceof LivingEntity) {
				// Cancel the event, use custom damage when dealing with E vs E
				ede.setCancelled(true);
				LivingEntity victim = (LivingEntity) edbee.getEntity();

				// Determine whether or not the attacker was a projectile
				LivingEntity attacker = null;
				ItemStack tool = null;
				if (edbee.getDamager() instanceof LivingEntity) {
					// Set the var to the attacker
					attacker = (LivingEntity) edbee.getDamager();
					tool = attacker.getEquipment().getItemInMainHand();
				} else if (edbee.getCause() == EntityDamageEvent.DamageCause.PROJECTILE
						&& ((Projectile) edbee.getDamager()).getShooter() instanceof LivingEntity) {
					// Set the var to the projectile's shooter
					Projectile projectile = (Projectile) edbee.getDamager();
					attacker = (LivingEntity) projectile.getShooter();
					tool = entities.contains(projectile)
							? entities.getProjectile(projectile).getItem()
							: attacker.getEquipment().getItemInMainHand();
				}

				AgLiving aInfo = entities.getLiving(attacker);
				if (attacker != null && !aInfo.isDead() && !aInfo.isTrapped()) {
					// If the attacker is a player, make sure they can attack, otherwise continue
					if (!(attacker instanceof Player) || entities.getPlayer((Player) attacker).attack()) {
						if (ItemManager.is(tool, ItemWeapon.IDENTITY)) {
							ItemWeapon weapon = (ItemWeapon) ItemManager.get(tool);
							damage = Util.randInt(weapon.getMinDMG(), weapon.getMaxDMG());
						}

						// Shows a damage effect
						victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation(),
								100, victim.getWidth() / 2, victim.getHeight() / 2, victim.getWidth() / 2,
								new MaterialData(Material.REDSTONE_BLOCK));
						// Apply the damage and mimic what the event changes
						victim.damage(damage);
						victim.setLastDamage(damage);
						victim.setLastDamageCause(edbee);
						victim.setHealth(Math.max(0, victim.getHealth()));
						// Set the victim's no damage ticks (different from player hit ticks)
						victim.setMaximumNoDamageTicks(5);
						victim.setNoDamageTicks(5);

						// Apply knock back to the victim
						if (!entities.getLiving(victim).isTrapped()) {
							Vector vec = attacker.getLocation().getDirection().multiply(0.09);
							if (victim.isOnGround()) vec.setY(victim.getVelocity().getY() + 0.33);
							victim.setVelocity(vec);
						}

						// Send attacker game text to attacker
						if (attacker instanceof Player)
							attacker.sendMessage(attacker((Player) attacker, victim, damage));
					}
				}
			}
		}

		if (ede.isCancelled()) damage = 0;

		// Send victim game text to victim
		if (damage > 0 && entity.getLastDamageCause() != ede && entity instanceof Player)
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
