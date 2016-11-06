package com.aegeus.game;

import com.aegeus.game.data.AegeusEntity;
import com.aegeus.game.data.AegeusMonster;
import com.aegeus.game.data.Data;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Combat implements Listener {
	private final Aegeus PARENT;
	private final Random RANDOM = new Random();

	public Combat(Aegeus parent) {
		this.PARENT = parent;
	}

	@EventHandler
	private void onDeath(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		AegeusEntity data = Data.get(entity);

		if (data instanceof AegeusMonster) {
			Monster monster = (Monster) entity;
			AegeusMonster md = (AegeusMonster) data;
			if (md.getDropChance() > 0) {
				float c = RANDOM.nextFloat();
				if (c <= md.getDropChance()) {
					c = RANDOM.nextFloat();
					if (c <= 0.45) {
						List<ItemStack> items = new ArrayList<>();
						for (ItemStack i : monster.getEquipment().getArmorContents())
							if (i != null && i.getType() != Material.AIR) items.add(i);
						if (items.size() >= 1) {
							int r = RANDOM.nextInt(items.size());
							monster.getWorld().dropItem(monster.getLocation(), items.get(r));
						}
					} else {
						ItemStack i = monster.getEquipment().getItemInMainHand();
						if (i != null && i.getType() != Material.AIR)
							monster.getWorld().dropItem(monster.getLocation(), i);
					}
				}
			}
		}

		if (data.getLastHitBy() != null) {
			LivingEntity lastHitBy = data.getLastHitBy();
			if (lastHitBy instanceof Player) {
				Player player = (Player) lastHitBy;
				if (player.getEquipment().getItemInMainHand() != null
						&& !player.getEquipment().getItemInMainHand().getType().equals(Material.AIR)) {
					Weapon weapon = new Weapon(player.getEquipment().getItemInMainHand());
					if (weapon.getLevelInfo().addXp(
							weapon.getMinDmg() / weapon.getEquipmentInfo().getTier()))
						player.sendMessage(Utility.colorCodes(
								"&6Your weapon has reached &lLevel " + weapon.getLevelInfo().getLevel() + "&6."));
					player.getEquipment().setItemInMainHand(weapon.build());
				}
			}
		}

		// Clear entity's data if not player
		if (!(entity instanceof Player))
			Data.remove(entity);

	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent e) {
		EntityDamageByEntityEvent ee = null;
		if (e instanceof EntityDamageByEntityEvent) // Created DamageByEntity event incase needed
			ee = (EntityDamageByEntityEvent) e;

		if (e.getEntity() instanceof Player)
			Statistics.updateDisplay((Player) e.getEntity());

		if (ee != null && ee.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) ee.getDamager();
			LivingEntity entity = (LivingEntity) e.getEntity();
			if (damager.getEquipment().getItemInMainHand() != null
					&& damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
				Weapon weapon = new Weapon(damager.getEquipment().getItemInMainHand());
				if (weapon.getMinDmg() > 0 || weapon.getMaxDmg() > 0)
					if (weapon.getMaxDmg() > weapon.getMinDmg()) {
						e.setDamage(RANDOM.nextInt(weapon.getMaxDmg() - weapon.getMinDmg()) + weapon.getMinDmg());
					} else
						e.setDamage(weapon.getMinDmg());
				if (weapon.getIceDmg() > 0) {
					e.setDamage(e.getDamage() + (weapon.getIceDmg() * 0.5));
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 + (3 * weapon.getEquipmentInfo().getTier()), 2));
				}
				if (weapon.getFireDmg() > 0) {
					e.setDamage(e.getDamage() + (weapon.getFireDmg() * 0.5));
					entity.setFireTicks(16 + (3 * weapon.getEquipmentInfo().getTier()));
				}
				if (weapon.getLifeSteal() > 0 && damager.getHealth() < damager.getMaxHealth()) {
					double heal = e.getDamage() * weapon.getLifeSteal();
					double hp = damager.getHealth() + heal;
					damager.setHealth((hp > damager.getMaxHealth()) ? damager.getMaxHealth() : hp);
					if (damager instanceof Player) {
						notifyHeal((Player) damager, heal);
						Statistics.updateDisplay((Player) damager);
					}
				}
				e.setDamage(e.getDamage() + (weapon.getEquipmentInfo().getStrength() * 0.2));
			}
		}

		if (e.getDamage() < 1.0f) e.setDamage(1.0f); // Set minimum damage to 1

		switch (e.getCause()) {
			case PROJECTILE:
				assert ee != null;
				// TODO Discuss this as this may not be final
				ee.getDamager().remove();
				break;
			case VOID:
				// TODO When alignments are done, only teleport player to the terminal if lawful
				if (e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					p.teleport(Planet.TERMINAL.getWorld().getSpawnLocation());
				}
				break;
			case ENTITY_ATTACK:
				if (ee != null && ee.getEntity() instanceof LivingEntity && ee.getDamager() instanceof LivingEntity) {
					LivingEntity victim = (LivingEntity) ee.getEntity();
					LivingEntity attacker = (LivingEntity) ee.getDamager();

					/* Since 1.9 fucks up combat, we need to use
					   EXTRA CODE to cancel that! */
					ee.setCancelled(true);
					victim.playEffect(EntityEffect.HURT);
					victim.setLastDamageCause(ee);
					victim.setHealth(
							(victim.getHealth() - ee.getDamage() > 0)
									? victim.getHealth() - ee.getDamage() : 0);
					Vector vec = attacker.getLocation().getDirection().multiply(0.5);
					victim.setVelocity(vec.setY(vec.getY() - 1));

					AegeusEntity data = Data.get(victim);
					data.setLastHitBy(attacker);

					if (victim instanceof Player) {
						notifyAttacked((Player) victim, e.getDamage());
					}
					if (attacker instanceof Player) {
						notifyAttack((Player) attacker, victim, e.getDamage());
					}
				}
				break;
			default:
				if (e.getEntity() instanceof Player) {
					Player player = (Player) e.getEntity();
					notifyAttacked(player, e.getDamage());
				}
				break;
		}
	}

	@EventHandler
	private void onEntityHeal(EntityRegainHealthEvent event) {
		if (event.getEntityType().equals(EntityType.PLAYER)) {
			Statistics.updateDisplay((Player) event.getEntity());
		}
	}

	private void notifyAttack(Player player, LivingEntity attacked, double damage) {
		Bukkit.getScheduler().runTaskLater(PARENT, () -> {
			String name = (attacked.getCustomName() != null) ? attacked.getCustomName() : attacked.getName();
			long hp = Math.round(Math.ceil(attacked.getHealth()));
			long maxHp = Math.round(Math.ceil(attacked.getMaxHealth()));
			long dmg = Math.round(Math.ceil(damage));
			player.sendMessage(Utility.colorCodes("          &e" + dmg + " &l>&f " + name + "&7 [" + hp + " / " + maxHp + "]"));
		}, 1);
	}

	private void notifyAttacked(Player player, double damage) {
		Bukkit.getScheduler().runTaskLater(PARENT, () -> {
			long hp = Math.round(Math.ceil(player.getHealth()));
			long maxHp = Math.round(Math.ceil(player.getMaxHealth()));
			long dmg = Math.round(Math.ceil(damage));
			player.sendMessage(Utility.colorCodes("            &c-" + dmg + "&7 [" + hp + " / " + maxHp + "]"));
		}, 1);
	}

	private void notifyHeal(Player player, double health) {
		Bukkit.getScheduler().runTaskLater(PARENT, () -> {
			long hp = Math.round(Math.ceil(player.getHealth()));
			long maxHp = Math.round(Math.ceil(player.getMaxHealth()));
			long heal = Math.round(Math.ceil(health));
			player.sendMessage(Utility.colorCodes("            &a+" + heal + "&7 [" + hp + " / " + maxHp + "]"));
		}, 1);
	}


}
