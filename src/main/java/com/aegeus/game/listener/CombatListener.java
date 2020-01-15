package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.ItemManager;
import com.aegeus.game.item.wrapper.WeaponItem;
import com.aegeus.game.util.EntityMap;
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
import org.bukkit.util.Vector;

/**
 * Handles all combat-related events.
 */
public class CombatListener implements Listener {
    private static final String indent = "            ";

    private final EntityMap entities = Aegeus.getInstance().getEntities();

    @EventHandler
    private void onDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player) && entities.contains(entity)) {
            AgLiving data = entities.getLiving(entity);
            data.getDrops().forEach(i -> entity.getWorld().dropItem(entity.getLocation(), i));
            entities.remove(data);
        }
    }

    // TODO CLEAN THIS PLEASE
    @EventHandler
    private void onDamage(EntityDamageEvent ede) {
        Entity victim = ede.getEntity();
        int damage = (int) Math.ceil(ede.getDamage());

        // Check for entity vs. entity
        if (ede instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) ede;
            if (victim instanceof LivingEntity) {
                // Cancel the event, use custom damage when dealing with E vs E
                ede.setCancelled(true);
                LivingEntity lVictim = (LivingEntity) edbee.getEntity();

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
                            ? entities.getProjectile(projectile).getItemShotFrom()
                            : attacker.getEquipment().getItemInMainHand();
                }

                if (!victim.isDead()) {
                    AgLiving attackerData = entities.getOrCreateLiving(attacker);
                    if (attacker != null && !attacker.isDead() && attackerData.attack()) {
                        // If the attacker is a player, make sure they can attack, otherwise continue
                        if (ItemManager.id(tool).equals(WeaponItem.IDENTITY)) {
                            WeaponItem weapon = (WeaponItem) ItemManager.get(tool);
                            damage = weapon.getDamage().get();
                        }

                        // Shows a damage effect
                        victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation(),
                                100, victim.getWidth() / 2, victim.getHeight() / 2, victim.getWidth() / 2,
                                Material.REDSTONE_WIRE.createBlockData());
                        // Apply the damage and mimic what the event changes
                        lVictim.damage(damage);
                        lVictim.setLastDamage(damage);
                        lVictim.setLastDamageCause(edbee);
                        // Set the victim's no damage ticks (different from hit timers)
                        lVictim.setMaximumNoDamageTicks(8);
                        lVictim.setNoDamageTicks(8);

                        // Apply knock back to the victim
                        Vector vec = attacker.getLocation().getDirection().multiply(0.1);
                        if (victim.isOnGround()) vec.setY(victim.getVelocity().getY() + 0.33);
                        victim.setVelocity(vec);

                        // Send attacker game text to attacker
                        if (attacker instanceof Player) {
                            AgPlayer pAttackerData = entities.getPlayer((Player) attacker);
                            pAttackerData.displayHealthBar();
                            pAttackerData.displayTargetBar(lVictim);
                        }
                    }
                }
            }
        }

        // Send victim game text to victim
        if ((!ede.isCancelled() || damage > 0) && victim instanceof Player)
            entities.getPlayer((Player) victim).displayHealthBar();
    }
}
